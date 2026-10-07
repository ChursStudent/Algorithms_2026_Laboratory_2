package lab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import static lab.YCbCrConverter.Clamp;
import static lab.byteReader.readBytes;

public class downSample {
    public record Pair<File, CbCr>(File filenameWithY, CbCr cbcr) {
    }

    public static Pair<String,byte[]> downSampling(String filename) throws IOException {
        byte[] Text = readBytes(filename + ".raw");
        int i = 0;
        int j = 1;
        int avg;
        StringBuilder wid = new StringBuilder();
        while (Text[i] != (byte)'x' && i < Text.length - 1) {
            wid.append((char)Text[i]);
            i++;
        }
        int lineWidth = Integer.parseInt(wid.toString()) * 3;
        while (Text[i] != 'Y' && Text[i] != 'y' && Text[i] != 'b') {
            i++;
        }
        byte[] CbCr = new byte[(Text.length - i - 1) / 6];
        int CbCrIndex = 0;
        int start = i += 2;
        for (; i + 3 + j * lineWidth < Text.length; i += 6) {
            avg = Clamp(((Text[i + (j - 1) * lineWidth] & 0xFF)
                    + (Text[i + 3 + (j - 1) * lineWidth] & 0xFF)
                    + (Text[i + j * lineWidth] & 0xFF)
                    + (Text[i + 3 + j * lineWidth] & 0xFF)) / 4);
            Text[i + (j - 1) * lineWidth] = (byte)avg;
            Text[i + 3 + (j - 1) * lineWidth] = (byte)avg;
            Text[i + j * lineWidth] = (byte)avg;
            Text[i + 3 + j * lineWidth] = (byte)avg;
            if (i + 6 - start > lineWidth) {
                i -= lineWidth;
                j += 2;
            }

            CbCr[CbCrIndex] = (byte)avg;
            CbCrIndex += 2;
        }
        start++;
        i = start;
        j = 1;
        CbCrIndex = 1;
        for (; i + 3 + j * lineWidth < Text.length; i += 6) {
            avg = Clamp(((Text[i + (j - 1) * lineWidth] & 0xFF)
                    + (Text[i + 3 + (j - 1) * lineWidth] & 0xFF)
                    + (Text[i + j * lineWidth] & 0xFF)
                    + (Text[i + 3 + j * lineWidth] & 0xFF)) / 4);
            Text[i + (j - 1) * lineWidth] = (byte)avg;
            Text[i + 3 + (j - 1) * lineWidth] = (byte)avg;
            Text[i + j * lineWidth] = (byte)avg;
            Text[i + 3 + j * lineWidth] = (byte)avg;
            if (i + 6 - start > lineWidth) {
                i -= lineWidth;
                j += 2;
            }

            CbCr[CbCrIndex] = (byte)avg;
            CbCrIndex += 2;
        }
        Files.write(Paths.get(filename + "DS.raw"), Text);
        return new Pair<>(filename + "DS", CbCr);
    }

    public static byte[] upSampling(byte[] CbCr, int origWidth, int origHeight) {
        byte[] origCbCr = new byte[CbCr.length * 4];
        for (int i = 0; i < origHeight; i++){
            for (int j = 0; j < origWidth * 2; j+=2) {
                origCbCr[i * origWidth * 2 + j] = CbCr[((i - i % 2) / 2) * origWidth + ((j - j % 4) / 4) * 2];
                origCbCr[i * origWidth * 2 + j + 1] = CbCr[((i - i % 2) / 2) * origWidth + ((j - j % 4) / 4) * 2 + 1];
            }
        }
        return origCbCr;
    }
}
