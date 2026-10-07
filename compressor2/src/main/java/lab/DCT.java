package lab;

import java.util.ArrayList;
import java.util.List;

import static lab.YCbCrConverter.Clamp;

public class DCT {
    public static double normFactor(int n) {
        if (n == 0) {
            return 1 / Math.sqrt(2);
        }
        else {
            return 1;
        }
    }

    public static List<int[]> Dct(BlocksForDCT Blocks) {
        int[] DCTBlock;
        double DCTElemTemp;
        List<int[]> DCTBlocks = new ArrayList<>();
        for (byte[] block : Blocks.blocks) {
            DCTBlock = new int[64];
            for (int v = 0; v < 8; v++) {
                for (int u = 0; u < 8; u++) {
                    DCTElemTemp = 0;
                    for (int y = 0; y < 8; y++) {
                        for (int x = 0; x < 8; x++) {
                            DCTElemTemp += (block[8*y+x] & 0xFF - 128) * Math.cos((2*x+1)*u*Math.PI/16) * Math.cos((2*y+1)*v*Math.PI/16);
                        }
                    }
                    DCTBlock[8*v+u] = (int)(DCTElemTemp * normFactor(v) * normFactor(u)) / 4;
                }
            }
            DCTBlocks.add(DCTBlock);
        }
        return DCTBlocks;
    }

    public static BlocksForDCT iDct(List<int[]> DCTBlocks, int w, int h) {
        byte[] Block;
        double ElemTemp;
        List<byte[]> Blocks = new ArrayList<>();
        for (int[] DCTBlock : DCTBlocks) {
            Block = new byte[64];
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    ElemTemp = 0;
                    for (int v = 0; v < 8; v++) {
                        for (int u = 0; u < 8; u++) {
                            ElemTemp += normFactor(u) * normFactor(v) * DCTBlock[8*v+u] * Math.cos((2*x+1)*u*Math.PI/16) * Math.cos((2*y+1)*v*Math.PI/16);
                        }
                    }
                    Block[8*y+x] = (byte)(ElemTemp / 4 + 128);
                }
            }
            Blocks.add(Block);
        }
        return new BlocksForDCT(w, h, Blocks);
    }

    public static List<int[]> DctFaster(BlocksForDCT Blocks) {
        int[] DCTBlock;
        double DCTElemTemp;
        List<int[]> DCTBlocks = new ArrayList<>();
        double[] C = new double[64];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                C[8 * i + j] = Math.cos((2 * i + 1) * j * Math.PI / 16);
            }
        }
        for (byte[] block : Blocks.blocks) {
            DCTBlock = new int[64];
            for (int v = 0; v < 8; v++) {
                for (int u = 0; u < 8; u++) {
                    DCTElemTemp = 0;
                    for (int y = 0; y < 8; y++) {
                        for (int x = 0; x < 8; x++) {
                            DCTElemTemp += ((block[8*y+x] & 0xFF) - 128) * C[8*x + u] * C[8*y + v];
                        }   //мб -128 еще для центровки
                    }
                    DCTBlock[8*v+u] = (int)(DCTElemTemp * normFactor(v) * normFactor(u) / 4);
                }
            }
            DCTBlocks.add(DCTBlock);
        }
        return DCTBlocks;
    }

    public static BlocksForDCT iDctFaster(List<int[]> DCTBlocks, int w, int h) {
        byte[] Block;
        double ElemTemp;
        List<byte[]> Blocks = new ArrayList<>();
        double[] C = new double[64];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                C[8 * i + j] = Math.cos((2 * i + 1) * j * Math.PI / 16);
            }
        }
        for (int[] DCTBlock : DCTBlocks) {
            Block = new byte[64];
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    ElemTemp = 0;
                    for (int v = 0; v < 8; v++) {
                        for (int u = 0; u < 8; u++) {
                            ElemTemp += normFactor(u) * normFactor(v) * DCTBlock[8*v+u] * C[8*x + u] * C[8*y + v];
                        }
                    }
                    Block[8*y+x] = (byte)(Clamp((int)(ElemTemp / 4 + 128)));
                }
            }
            Blocks.add(Block);
        }
        return new BlocksForDCT(w, h, Blocks);
    }
}
