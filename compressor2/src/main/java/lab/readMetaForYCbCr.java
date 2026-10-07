package lab;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static lab.byteReader.readBytes;

public class readMetaForYCbCr {
    public static byte[] readMetaYCC (String filename) throws IOException {
        byte[] Text = readBytes(filename + ".raw");
        StringBuilder Meta = new StringBuilder();
        for (int i = 0; i < Text.length; i++) {
            if (Text[i] == 'Y' || Text[i] == 'y' || Text[i] == 'b') {
                Meta.append((char)(Text[i] & 0xFF));
                break;
            }
            else {
                Meta.append((char)(Text[i] & 0xFF));
            }
        }
        return Meta.toString().getBytes(StandardCharsets.ISO_8859_1);
    }
}
