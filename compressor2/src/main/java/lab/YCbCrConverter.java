package lab;

import java.io.IOException;

import static lab.byteReader.readBytes;
import static lab.byteReader.writeBytes;

public class YCbCrConverter {
    public static int Clamp (int val) {
        return Math.max(0, Math.min(val, 255));
    }
    public static String toYCbCr(String filename) throws IOException {
        byte[] Text = readBytes(filename + ".raw");
        StringBuilder Done = new StringBuilder();
        int Shade;
        char Color = '?';
        int i = 0;
        for (; i < Text.length; i++) {
            if (Text[i] == 'R') {
                Color = 'R';
                break;
            }
            else if (Text[i] == 'G') {
                Color = 'G';
                break;
            }
            else if (Text[i] == 'B') {
                Color = 'B';
                break;
            }
            Done.append((char)(Text[i] & 0xFF));
        }
        i++;
        if (Color == 'R') {
            Done.append('Y');
            for (; i < Text.length; i += 3) {
                Shade = Clamp((int)(0.299 * (Text[i + 2] & 0xFF)
                        + 0.587 * (Text[i + 1] & 0xFF)
                        + 0.114 * (Text[i] & 0xFF)));
                Done.append((char)Shade);
                Shade = Clamp((int)(-0.168736 * (Text[i + 2] & 0xFF)
                        - 0.331264 * (Text[i + 1] & 0xFF)
                        + 0.5 * (Text[i] & 0xFF) + 128));
                Done.append((char)Shade);
                Shade = Clamp((int)(0.5 * (Text[i + 2] & 0xFF)
                        - 0.418688 * (Text[i + 1] & 0xFF)
                        - 0.081312 * (Text[i] & 0xFF) + 128));
                Done.append((char)Shade);
            }
        }
        else if (Color == 'G' || Color == 'B') {
            Done.append(Color == 'G' ? 'y' : 'b');
            for (; i < Text.length; i++) {
                Shade = Clamp((int)(0.299 * (Text[i] & 0xFF)
                        + 0.587 * (Text[i] & 0xFF)
                        + 0.114 * (Text[i] & 0xFF)));
                Done.append((char)Shade);
                Shade = Clamp((int)(-0.168736 * (Text[i] & 0xFF)
                        - 0.331264 * (Text[i] & 0xFF)
                        + 0.5 * (Text[i] & 0xFF) + 128));
                Done.append((char)Shade);
                Shade = Clamp((int)(0.5 * (Text[i] & 0xFF)
                        - 0.418688 * (Text[i] & 0xFF)
                        - 0.081312 * (Text[i] & 0xFF) + 128));
                Done.append((char)Shade);
            }
        }
        else {
            System.out.println("Неверный формат файла");
        }
        writeBytes(filename + "YCbCr.raw", Done.toString());
        return filename + "YCbCr";
    }

    public static String fromYCbCr(String filename) throws IOException {
        byte[] Text = readBytes(filename + ".raw");
        StringBuilder Done = new StringBuilder();
        int Shade;
        char Color = '?';
        int i = 0;
        for (; i < Text.length; i++) {
            if (Text[i] == 'Y') {
                Color = 'R';
                break;
            }
            else if (Text[i] == 'y') {
                Color = 'G';
                break;
            }
            else if (Text[i] == 'b') {
                Color = 'B';
                break;
            }
            Done.append((char)(Text[i] & 0xFF));
        }
        i++;
        if (Color == 'R') {
            Done.append('R');
            for (; i < Text.length; i += 3) {
                Shade = Clamp((int)((Text[i] & 0xFF)
                        + 1.772 * ((Text[i + 1] & 0xFF) - 128)));
                Done.append((char)Shade);
                Shade = Clamp((int)((Text[i] & 0xFF)
                        - 0.34414 * ((Text[i + 1] & 0xFF) - 128)
                        - 0.71414 * ((Text[i + 2] & 0xFF) - 128)));
                Done.append((char)Shade);
                Shade = Clamp((int)((Text[i] & 0xFF)
                        + 1.402 * ((Text[i + 2] & 0xFF) - 128)));
                Done.append((char)Shade);
            }
        }
        else if (Color == 'G') {
            Done.append('G');
            for (; i < Text.length; i += 3) {
                Shade = Clamp((int)((Text[i] & 0xFF)
                        + 1.772 * ((Text[i + 1] & 0xFF) - 128)));
                Done.append((char)Shade);
            }
        }
        else if (Color == 'B') {
            Done.append('B');
            for (; i < Text.length; i += 3) {
                Shade = Clamp((int)((Text[i] & 0xFF)
                        + 1.772 * ((Text[i + 1] & 0xFF) - 128)));
                Shade = Shade > 128 ? (char)255 : (char)0;
                Done.append((char)Shade);
            }
        }
        else {
            System.out.println("Неверный формат файла");
        }
        writeBytes(filename + "FromYCbCr.raw", Done.toString());
        return filename + "FromYCbCr";
    }
}
