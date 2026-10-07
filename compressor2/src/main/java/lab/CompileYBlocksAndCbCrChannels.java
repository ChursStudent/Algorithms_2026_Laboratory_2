package lab;


import java.io.IOException;

import static lab.byteReader.readBytes;

public class CompileYBlocksAndCbCrChannels {

    public static byte[] getCbCrFromEncoded(String filename, int startIndex, int WidXHei) throws IOException {
        byte[] Result = new byte[WidXHei / 2];
        byte[] Text = readBytes(filename + ".raw");
        for (int i = 0; i < Result.length; i++) {
            Result[i] = Text[i + startIndex];
        }
        return Result;
    }

    public static byte[] YPlusCbCrToRaw (byte[] Y, byte[] CbCr, char type) {   //новая
        byte[] Result = new byte[Y.length * 3];
        if (type == 'Y') {
            for (int i = 0; i < Result.length; i++) {
                if (i % 3 == 0) {
                    Result[i] = Y[i / 3];
                } else {
                    Result[i] = CbCr[i - i / 3 - 1];
                }
            }
        }
        else {
            for (int i = 0; i < Result.length; i++) {
                if (i % 3 == 0) {
                    Result[i] = Y[i / 3];
                } else {
                    Result[i] = (byte)128;
                }
            }
        }
        return Result;
    }
}
