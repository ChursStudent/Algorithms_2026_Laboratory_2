package lab;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lab.AnyBmpToRaw.BTR;
import static lab.YCbCrConverter.toYCbCr;
import static lab.byteReader.readBytes;

public class BlocksForDCT {
    public int width;
    public int height;
    public List<byte[]> blocks;

    BlocksForDCT(int w, int h, List<byte[]> b) {
        width = w;
        height = h;
        blocks = b;
    }

    BlocksForDCT() {
    }

    public void getBlocksForDCT(String filename) throws IOException {
        byte[] Text = readBytes(filename + ".raw");
        StringBuilder wid = new StringBuilder();
        StringBuilder hei = new StringBuilder();
        int skip = 0;
        while (Text[skip] != (byte) 'x' && skip < Text.length - 1) {
            wid.append((char) Text[skip]);
            skip++;
        }
        skip++;
        while (Text[skip] != (byte) 'Y' && Text[skip] != (byte) 'y' && Text[skip] != (byte) 'b' && skip < Text.length - 1) {
            hei.append((char) Text[skip]);
            skip++;
        }
        int w = width = Integer.parseInt(wid.toString());
        int h = height = Integer.parseInt(hei.toString());
        blocks = new ArrayList<>();
        byte[] block;
        int pixelPos;
        skip++;
        for (int i = 0; i < w * h / 64; i++) {
            block = new byte[64];
            for (int j = 0; j < 64; j++) {
                pixelPos = skip + 3 * (8 * (i % (w / 8)) + 8 * w * (i / (w / 8)) + j % 8 + w * (j / 8));
                block[j] = Text[pixelPos];
            }
            blocks.add(block);
        }
    }

    public static byte[] fromBlocksToBytes(BlocksForDCT Blocks) {
        byte[] Y = new byte[Blocks.width * Blocks.height];
        int index = 0;
        for (int i = 0; i < Blocks.height; i++) {
            for (int j = 0; j < Blocks.width / 8; j++) {
                for (int k = 0; k < 8; k++) {
                    Y[index] = Blocks.blocks.get(i / 8 * Blocks.width / 8 + j)[(i % 8) * 8 + k];
                    index++;
                }
            }
        }
        return Y;
    }

    public void getBadBlocksForDCT2(String filename) throws IOException {
        byte[] Text = readBytes(filename + ".raw");
        StringBuilder wid = new StringBuilder();
        StringBuilder hei = new StringBuilder();
        int skip = 0;
        while (Text[skip] != (byte) 'x' && skip < Text.length - 1) {
            wid.append((char) Text[skip]);
            skip++;
        }
        skip++;
        while (Text[skip] != (byte) 'Y' && Text[skip] != (byte) 'y' && Text[skip] != (byte) 'b' && skip < Text.length - 1) {
            hei.append((char) Text[skip]);
            skip++;
        }
        int w = width = Integer.parseInt(wid.toString());
        int h = height = Integer.parseInt(hei.toString());
        blocks = new ArrayList<>();
        byte[] block;
        int pixelPos;
        skip++;
        int avg;
        int numberOfOut;
        byte[][] matrix = new byte[w + 8 - ((w % 8 == 0)?8:(w%8))][h + 8 - ((h % 8 == 0)?8:(h%8))];
        pixelPos = skip;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                matrix[i][j] = Text[pixelPos];
                pixelPos += 3;
            }
        }
        if (w % 8 != 0) {
            for (int blockNum = 0; blockNum < h / 8; blockNum++) {
                numberOfOut = 0;
                avg = 0;
                for (int j = blockNum * 8; j < blockNum * 8 + 8; j++) {
                    for (int i = w - w % 8; i < w; i++) {
                        avg += (matrix[i][j] & 0xFF);
                        numberOfOut++;
                    }
                }
                avg /= numberOfOut;
                for (int j = blockNum * 8; j < blockNum * 8 + 8; j++) {
                    for (int i = w; i < w + 8 - ((w % 8 == 0) ? 8 : (w % 8)); i++) {
                        matrix[i][j] = (byte) avg;
                    }
                }
            }
        }
        if (h % 8 != 0) {
            for (int blockNum = 0; blockNum < w / 8; blockNum++) {
                numberOfOut = 0;
                avg = 0;
                for (int i = blockNum * 8; i < blockNum * 8 + 8; i++) {
                    for (int j = h - h % 8; j < h; j++) {
                        avg += (matrix[i][j] & 0xFF);
                        numberOfOut++;
                    }
                }
                avg /= numberOfOut;
                for (int i = blockNum * 8; i < blockNum * 8 + 8; i++) {
                    for (int j = h; j < h + 8 - ((h % 8 == 0) ? 8 : (h % 8)); j++) {
                        matrix[i][j] = (byte) avg;
                    }
                }
            }
        }
        if (w % 8 != 0 && h % 8 != 0) {
            numberOfOut = 0;
            avg = 0;
            for (int i = w - w % 8; i < w; i++) {
                for (int j = h - h % 8; j < h; j++) {
                    avg += (matrix[i][j] & 0xFF);
                    numberOfOut++;
                }
            }
            avg /= numberOfOut;
            for (int i = w; i < w + 8 - ((w % 8 == 0) ? 8 : (w % 8)); i++) {
                for (int j = h; j < h + 8 - ((h % 8 == 0) ? 8 : (h % 8)); j++) {
                    matrix[i][j] = (byte) avg;
                }
            }
            for (int i = w - w % 8; i < w; i++) {
                for (int j = h; j < h + 8 - ((h % 8 == 0) ? 8 : (h % 8)); j++) {
                    matrix[i][j] = (byte) avg;
                }
            }
            for (int i = w; i < w + 8 - ((w % 8 == 0) ? 8 : (w % 8)); i++) {
                for (int j = h - h % 8; j < h; j++) {
                    matrix[i][j] = (byte) avg;
                }
            }
        }
        for (int j = 0; j < matrix[0].length; j += 8) {
            for (int i = 0; i < matrix.length; i += 8) {
                block = new byte[64];
                for (int k = 0; k < 8; k++) {
                    for (int m = 0; m < 8; m++) {
                        block[m*8+k] = matrix[i + k][j + m];
                    }
                }
                blocks.add(block);
            }
        }
    }

    public static byte[] fromBadBlocksToBytes2(BlocksForDCT Blocks) {
        byte[] Y = new byte[Blocks.width * Blocks.height];
        int w = Blocks.width;
        int h = Blocks.height;
        int index = 0;
            for (int j = 0; j < h; j++) {
                for (int i = 0; i < w; i++) {
                    Y[index] = Blocks.blocks.get(j/8 * (w/8 + ((w%8 == 0)?0:1)) + i/8)[(j%8) * 8 + i%8];
                    index++;
                }
            }
        return Y;
    }
}
