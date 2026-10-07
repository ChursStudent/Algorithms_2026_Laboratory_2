package lab;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Quant {
    public int[] QTable;
    Quant() {
        QTable = new int[]{16, 11, 10, 16, 24, 40, 51, 61, 12, 12, 14, 19, 26, 58, 60, 55, 14, 13, 16, 24, 40, 57, 69, 56, 14, 17, 22, 29, 51, 87, 80, 62, 18, 22, 37, 56, 68, 109, 103, 77, 24, 35, 55, 64, 81, 104, 113, 92, 49, 64, 78, 87, 103, 121, 120, 101, 72, 92, 95, 98, 112, 100, 103, 99};
    }
    public static List<int[]> Quantize(List<int[]> DctBlocks, int[] Q) {
        int[] DctBlockQ;
        List<int[]> DctBlocksQ = new ArrayList<>();
        for (int[] DctBlock : DctBlocks) {
            DctBlockQ = new int[64];
            for (int i = 0; i < 64; i++) {
                DctBlockQ[i] = DctBlock[i] / Q[i];
            }
            DctBlocksQ.add(DctBlockQ);
        }
        return DctBlocksQ;
    }

    public static List<int[]> DeQuantize(List<int[]> DctBlocksQ, int[] Q) {
        int[] DctBlock;
        List<int[]> DctBlocks = new ArrayList<>();
        for (int[] DctBlockQ : DctBlocksQ) {
            DctBlock = new int[64];
            for (int i = 0; i < 64; i++) {
                DctBlock[i] = DctBlockQ[i] * Q[i];
            }
            DctBlocks.add(DctBlock);
        }
        return DctBlocks;
    }

    public void changeQuality(int Quality) {
        int S;
        if (Quality >= 1 && Quality < 50) {
            S = 5000 / Quality;
        }
        else if (Quality >= 50 && Quality < 100) {
            S = 200 - 2 * Quality;
        }
        else {
            return;
        }
        for (int i = 0; i < QTable.length; i++) {
            QTable[i] = (QTable[i] * S) / 100;
        }
    }

    public byte[] returnQMatrix () {
        StringBuilder Result = new StringBuilder();
        for (int i = 0; i < QTable.length; i++) {
            Result.append(QTable[i]);
            if (i == QTable.length - 1) {
                Result.append('.');
            }
            else {
                Result.append(',');
            }
        }
        return Result.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public static int[] readQMatrix (byte[] Text, int startIndex) {
        int[] Matrix = new int[64];
        StringBuilder Number;
        int i = startIndex;
        for (int j = 0; j < Matrix.length; j++) {
            Number = new StringBuilder();
            while (Text[i] != ',' && Text[i] != '.') {
                Number.append((char)(Text[i] & 0xFF));
                i++;
            }
            Matrix[j] = Integer.parseInt(Number.toString());
            if (Text[i] == '.') {
                break;
            }
            i++;
        }
        return Matrix;
    }
}
