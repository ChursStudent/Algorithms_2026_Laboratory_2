package lab;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Huffman {
    Map<Integer, String> DCHuTable;
    Map<Integer, String> ACHuTable;
    Huffman () {
        DCHuTable = new HashMap<>();
        DCHuTable.put(0, "00");
        DCHuTable.put(1, "010");
        DCHuTable.put(2, "011");
        DCHuTable.put(3, "100");
        DCHuTable.put(4, "101");
        DCHuTable.put(5, "110");
        DCHuTable.put(6, "1110");
        DCHuTable.put(7, "11110");
        DCHuTable.put(8, "111110");
        DCHuTable.put(9, "1111110");
        DCHuTable.put(10, "11111110");
        DCHuTable.put(11, "111111110");

        ACHuTable = new HashMap<>();
        ACHuTable.put(0x00, "1010");
        ACHuTable.put(0x01, "00");
        ACHuTable.put(0x02, "01");
        ACHuTable.put(0x03, "100");
        ACHuTable.put(0x04, "1011");
        ACHuTable.put(0x05, "11010");
        ACHuTable.put(0x06, "1111000");
        ACHuTable.put(0x07, "11111000");
        ACHuTable.put(0x08, "1111110110");
        ACHuTable.put(0x09, "1111111110000010");
        ACHuTable.put(0x0A, "1111111110000011");
        ACHuTable.put(0x11, "1100");
        ACHuTable.put(0x12, "11011");
        ACHuTable.put(0x13, "1111001");
        ACHuTable.put(0x14, "111110110");
        ACHuTable.put(0x15, "11111110110");
        ACHuTable.put(0x16, "1111111110000100");
        ACHuTable.put(0x17, "1111111110000101");
        ACHuTable.put(0x18, "1111111110000110");
        ACHuTable.put(0x19, "1111111110000111");
        ACHuTable.put(0x1A, "1111111110001000");
        ACHuTable.put(0x21, "11100");
        ACHuTable.put(0x22, "11111001");
        ACHuTable.put(0x23, "1111110111");
        ACHuTable.put(0x24, "111111110100");
        ACHuTable.put(0x25, "1111111110001001");
        ACHuTable.put(0x26, "1111111110001010");
        ACHuTable.put(0x27, "1111111110001011");
        ACHuTable.put(0x28, "1111111110001100");
        ACHuTable.put(0x29, "1111111110001101");
        ACHuTable.put(0x2A, "1111111110001110");
        ACHuTable.put(0x31, "111010");
        ACHuTable.put(0x32, "111110111");
        ACHuTable.put(0x33, "111111110101");
        ACHuTable.put(0x34, "1111111110001111");
        ACHuTable.put(0x35, "1111111110010000");
        ACHuTable.put(0x36, "1111111110010001");
        ACHuTable.put(0x37, "1111111110010010");
        ACHuTable.put(0x38, "1111111110010011");
        ACHuTable.put(0x39, "1111111110010100");
        ACHuTable.put(0x3A, "1111111110010101");
        ACHuTable.put(0x41, "111011");
        ACHuTable.put(0x42, "1111111000");
        ACHuTable.put(0x43, "1111111110010110");
        ACHuTable.put(0x44, "1111111110010111");
        ACHuTable.put(0x45, "1111111110011000");
        ACHuTable.put(0x46, "1111111110011001");
        ACHuTable.put(0x47, "1111111110011010");
        ACHuTable.put(0x48, "1111111110011011");
        ACHuTable.put(0x49, "1111111110011100");
        ACHuTable.put(0x4A, "1111111110011101");
        ACHuTable.put(0x51, "1111010");
        ACHuTable.put(0x52, "11111110111");
        ACHuTable.put(0x53, "1111111110011110");
        ACHuTable.put(0x54, "1111111110011111");
        ACHuTable.put(0x55, "1111111110100000");
        ACHuTable.put(0x56, "1111111110100001");
        ACHuTable.put(0x57, "1111111110100010");
        ACHuTable.put(0x58, "1111111110100011");
        ACHuTable.put(0x59, "1111111110100100");
        ACHuTable.put(0x5A, "1111111110100101");
        ACHuTable.put(0x61, "1111011");
        ACHuTable.put(0x62, "111111110110");
        ACHuTable.put(0x63, "1111111110100110");
        ACHuTable.put(0x64, "1111111110100111");
        ACHuTable.put(0x65, "1111111110101000");
        ACHuTable.put(0x66, "1111111110101001");
        ACHuTable.put(0x67, "1111111110101010");
        ACHuTable.put(0x68, "1111111110101011");
        ACHuTable.put(0x69, "1111111110101100");
        ACHuTable.put(0x6A, "1111111110101101");
        ACHuTable.put(0x71, "11111010");
        ACHuTable.put(0x72, "111111110111");
        ACHuTable.put(0x73, "1111111110101110");
        ACHuTable.put(0x74, "1111111110101111");
        ACHuTable.put(0x75, "1111111110110000");
        ACHuTable.put(0x76, "1111111110110001");
        ACHuTable.put(0x77, "1111111110110010");
        ACHuTable.put(0x78, "1111111110110011");
        ACHuTable.put(0x79, "1111111110110100");
        ACHuTable.put(0x7A, "1111111110110101");
        ACHuTable.put(0x81, "111111000");
        ACHuTable.put(0x82, "111111111000000");
        ACHuTable.put(0x83, "1111111110110110");
        ACHuTable.put(0x84, "1111111110110111");
        ACHuTable.put(0x85, "1111111110111000");
        ACHuTable.put(0x86, "1111111110111001");
        ACHuTable.put(0x87, "1111111110111010");
        ACHuTable.put(0x88, "1111111110111011");
        ACHuTable.put(0x89, "1111111110111100");
        ACHuTable.put(0x8A, "1111111110111101");
        ACHuTable.put(0x91, "111111001");
        ACHuTable.put(0x92, "1111111110111110");
        ACHuTable.put(0x93, "1111111110111111");
        ACHuTable.put(0x94, "1111111111000000");
        ACHuTable.put(0x95, "1111111111000001");
        ACHuTable.put(0x96, "1111111111000010");
        ACHuTable.put(0x97, "1111111111000011");
        ACHuTable.put(0x98, "1111111111000100");
        ACHuTable.put(0x99, "1111111111000101");
        ACHuTable.put(0x9A, "1111111111000110");
        ACHuTable.put(0xA1, "111111010");
        ACHuTable.put(0xA2, "1111111111000111");
        ACHuTable.put(0xA3, "1111111111001000");
        ACHuTable.put(0xA4, "1111111111001001");
        ACHuTable.put(0xA5, "1111111111001010");
        ACHuTable.put(0xA6, "1111111111001011");
        ACHuTable.put(0xA7, "1111111111001100");
        ACHuTable.put(0xA8, "1111111111001101");
        ACHuTable.put(0xA9, "1111111111001110");
        ACHuTable.put(0xAA, "1111111111001111");
        ACHuTable.put(0xB1, "1111111001");
        ACHuTable.put(0xB2, "1111111111010000");
        ACHuTable.put(0xB3, "1111111111010001");
        ACHuTable.put(0xB4, "1111111111010010");
        ACHuTable.put(0xB5, "1111111111010011");
        ACHuTable.put(0xB6, "1111111111010100");
        ACHuTable.put(0xB7, "1111111111010101");
        ACHuTable.put(0xB8, "1111111111010110");
        ACHuTable.put(0xB9, "1111111111010111");
        ACHuTable.put(0xBA, "1111111111011000");
        ACHuTable.put(0xC1, "1111111010");
        ACHuTable.put(0xC2, "1111111111011001");
        ACHuTable.put(0xC3, "1111111111011010");
        ACHuTable.put(0xC4, "1111111111011011");
        ACHuTable.put(0xC5, "1111111111011100");
        ACHuTable.put(0xC6, "1111111111011101");
        ACHuTable.put(0xC7, "1111111111011110");
        ACHuTable.put(0xC8, "1111111111011111");
        ACHuTable.put(0xC9, "1111111111100000");
        ACHuTable.put(0xCA, "1111111111100001");
        ACHuTable.put(0xD1, "11111111000");
        ACHuTable.put(0xD2, "1111111111100010");
        ACHuTable.put(0xD3, "1111111111100011");
        ACHuTable.put(0xD4, "1111111111100100");
        ACHuTable.put(0xD5, "1111111111100101");
        ACHuTable.put(0xD6, "1111111111100110");
        ACHuTable.put(0xD7, "1111111111100111");
        ACHuTable.put(0xD8, "1111111111101000");
        ACHuTable.put(0xD9, "1111111111101001");
        ACHuTable.put(0xDA, "1111111111101010");
        ACHuTable.put(0xE1, "1111111111101011");
        ACHuTable.put(0xE2, "1111111111101100");
        ACHuTable.put(0xE3, "1111111111101101");
        ACHuTable.put(0xE4, "1111111111101110");
        ACHuTable.put(0xE5, "1111111111101111");
        ACHuTable.put(0xE6, "1111111111110000");
        ACHuTable.put(0xE7, "1111111111110001");
        ACHuTable.put(0xE8, "1111111111110010");
        ACHuTable.put(0xE9, "1111111111110011");
        ACHuTable.put(0xEA, "1111111111110100");
        ACHuTable.put(0xF0, "11111111001");
        ACHuTable.put(0xF1, "1111111111110101");
        ACHuTable.put(0xF2, "1111111111110110");
        ACHuTable.put(0xF3, "1111111111110111");
        ACHuTable.put(0xF4, "1111111111111000");
        ACHuTable.put(0xF5, "1111111111111001");
        ACHuTable.put(0xF6, "1111111111111010");
        ACHuTable.put(0xF7, "1111111111111011");
        ACHuTable.put(0xF8, "1111111111111100");
        ACHuTable.put(0xF9, "1111111111111101");
        ACHuTable.put(0xFA, "1111111111111110");
    }

    public record Pair<DC, AC>(DC dcs, AC acs) {
    }
    public static byte[] DCHuE(DCVarLen[] varDCs, Map<Integer, String> DCHuT) {
        StringBuilder Encoded = new StringBuilder();
        StringBuilder byteBuffer = new StringBuilder();
        for (int i = 0; i < varDCs.length; i++) {
            byteBuffer.append(DCHuT.get(varDCs[i].codeLen));
            byteBuffer.append(varDCs[i].code);
            if (byteBuffer.length() >= 8) {
                Encoded.append((char)Integer.parseInt(byteBuffer.substring(0, 8), 2));
                byteBuffer.delete(0, 8);
            }
        }
        while (!byteBuffer.isEmpty()) {
            if (byteBuffer.length() >= 8) {
                Encoded.append((char) Integer.parseInt(byteBuffer.substring(0, 8), 2));
                byteBuffer.delete(0, 8);
            }
            else {
                Encoded.append((char) Integer.parseInt(byteBuffer + "0".repeat(8 - byteBuffer.length()), 2));
                byteBuffer.delete(0, byteBuffer.length());
            }
        }
        return Encoded.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public static byte[] ACHuE(List<List<ACVarLen>> varACs, Map<Integer, String> ACHuT) {
        StringBuilder Encoded = new StringBuilder();
        StringBuilder byteBuffer = new StringBuilder();
        int zerosAndLen;
        for (List<ACVarLen> Block : varACs) {
            for (int i = 0; i < Block.size(); i++) {
                zerosAndLen = 16 * Block.get(i).zeros + Block.get(i).codeLen;
                byteBuffer.append(ACHuT.get(zerosAndLen));
                byteBuffer.append(Block.get(i).code);
                if (byteBuffer.length() >= 8) {
                    Encoded.append((char) Integer.parseInt(byteBuffer.substring(0, 8), 2));
                    byteBuffer.delete(0, 8);
                }
            }
        }
        while (!byteBuffer.isEmpty()) {
            if (byteBuffer.length() >= 8) {
                Encoded.append((char) Integer.parseInt(byteBuffer.substring(0, 8), 2));
                byteBuffer.delete(0, 8);
            }
            else {
                Encoded.append((char) Integer.parseInt(byteBuffer + "0".repeat(8 - byteBuffer.length()), 2));
                byteBuffer.delete(0, byteBuffer.length());
            }
        }
        return Encoded.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public static Pair<DCVarLen[], List<List<ACVarLen>>> DcAcHuD(byte[] Encoded, int Wid, int Hei, Map<Integer, String> DCHuT, Map<Integer, String> ACHuT) {
        Map<String, Integer> iDCHuT = new HashMap<>();
        for (Map.Entry<Integer, String> entry : DCHuT.entrySet()) {
            iDCHuT.put(entry.getValue(), entry.getKey());
        }
        int BlockNum;
        if (Wid % 8 != 0) {
            BlockNum = (Wid / 8) + 1;
        }
        else {
            BlockNum = (Wid / 8);
        }
        if (Hei % 8 != 0) {
            BlockNum *= (Hei / 8) + 1;
        }
        else {
            BlockNum *= (Hei / 8);
        }
        DCVarLen[] DecodedDC = new DCVarLen[BlockNum];
        StringBuilder byteBuffer = new StringBuilder();
        StringBuilder byteCheck = new StringBuilder();
        String symBytes;
        int ifZeroLen = 0;
        int index = 0;
        for (int i = 0 ; i < DecodedDC.length; i++) {
            while (true) {
                if (byteBuffer.isEmpty()) {
                    symBytes = Integer.toBinaryString(Encoded[index] & 0xFF);
                    index++;
                    symBytes = "0".repeat(8 - symBytes.length()) + symBytes;
                    byteBuffer.append(symBytes);
                }
                byteCheck.append(byteBuffer.charAt(0));
                byteBuffer.deleteCharAt(0);
                if (iDCHuT.containsKey(byteCheck.toString())) {
                    DecodedDC[i] = new DCVarLen(0, "");
                    DecodedDC[i].codeLen = iDCHuT.get(byteCheck.toString());
                    byteCheck.delete(0, byteCheck.length());
                    if (DecodedDC[i].codeLen == 0) {
                        ifZeroLen = 1;
                    }
                    while (byteBuffer.length() < DecodedDC[i].codeLen + ifZeroLen) {
                        symBytes = Integer.toBinaryString(Encoded[index] & 0xFF);
                        index++;
                        symBytes = "0".repeat(8 - symBytes.length()) + symBytes;
                        byteBuffer.append(symBytes);
                    }
                    DecodedDC[i].code = byteBuffer.substring(0, DecodedDC[i].codeLen + ifZeroLen);
                    byteBuffer.delete(0, DecodedDC[i].codeLen + ifZeroLen);
                    ifZeroLen = 0;
                    break;
                }
            }
        }

        Map<String, Integer> iACHuT = new HashMap<>();
        for (Map.Entry<Integer, String> entry : ACHuT.entrySet()) {
            iACHuT.put(entry.getValue(), entry.getKey());
        }
        List<List<ACVarLen>> DecodedAC = new ArrayList<>();
        List<ACVarLen> AcBlock;
        byteBuffer = new StringBuilder();
        byteCheck = new StringBuilder();
        int j;
        int zerosAndLen;
        for (int blockCount = 0; blockCount < BlockNum; blockCount++) {
            AcBlock = new ArrayList<>();
            j = 0;
            zerosAndLen = 1;
            while (j < 63 && zerosAndLen != 0) {
                while (true) {
                    if (byteBuffer.isEmpty()) {
                        symBytes = Integer.toBinaryString(Encoded[index] & 0xFF);
                        index++;
                        symBytes = "0".repeat(8 - symBytes.length()) + symBytes;
                        byteBuffer.append(symBytes);
                    }
                    byteCheck.append(byteBuffer.charAt(0));
                    byteBuffer.deleteCharAt(0);
                    if (iACHuT.containsKey(byteCheck.toString())) {
                        zerosAndLen = iACHuT.get(byteCheck.toString());
                        AcBlock.add(new ACVarLen(zerosAndLen / 16, zerosAndLen % 16, ""));
                        byteCheck.delete(0, byteCheck.length());
                        if (AcBlock.getLast().codeLen == 0) {
                            ifZeroLen = 1;
                        }
                        while (byteBuffer.length() < AcBlock.getLast().codeLen + ifZeroLen) {
                            symBytes = Integer.toBinaryString(Encoded[index] & 0xFF);
                            index++;
                            symBytes = "0".repeat(8 - symBytes.length()) + symBytes;
                            byteBuffer.append(symBytes);
                        }
                        AcBlock.getLast().code = byteBuffer.substring(0, AcBlock.getLast().codeLen + ifZeroLen);
                        byteBuffer.delete(0, AcBlock.getLast().codeLen + ifZeroLen);
                        ifZeroLen = 0;
                        j += AcBlock.getLast().zeros + 1;
                        break;
                    }
                }
            }
            DecodedAC.add(AcBlock);
        }
        return new Pair<> (DecodedDC, DecodedAC);
    }

    public byte[] returnDCHuTable () {
        StringBuilder Result = new StringBuilder();
        int counter = 0;
        for (Map.Entry<Integer, String> entry : DCHuTable.entrySet()) {
            counter++;
            Result.append(entry.getKey());
            Result.append(':');
            Result.append(entry.getValue());
            if (counter < 12) {
                Result.append(',');
            }
            else {
                Result.append('.');
            }
        }
        return Result.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public byte[] returnACHuTable () {
        StringBuilder Result = new StringBuilder();
        int counter = 0;
        for (Map.Entry<Integer, String> entry : ACHuTable.entrySet()) {
            counter++;
            Result.append(entry.getKey());
            Result.append(':');
            Result.append(entry.getValue());
            if (counter < 162) {
                Result.append(',');
            }
            else {
                Result.append('.');
            }
        }
        return Result.toString().getBytes(StandardCharsets.ISO_8859_1);
    }

    public static Map<Integer, String> readHuTable (byte[] Text, int startIndex, char type) {
        Map<Integer, String> Table = new HashMap<>();
        StringBuilder Number;
        StringBuilder Code;
        int i = startIndex;
        int mapLen;
        if (type == 'D') {
            mapLen = 12;
        }
        else {
            mapLen = 162;
        }
        for (int j = 0; j < mapLen; j++) {
            Number = new StringBuilder();
            Code = new StringBuilder();
            while (Text[i] != ':') {
                Number.append((char)(Text[i] & 0xFF));
                i++;
            }
            i++;
            while (Text[i] != ',' && Text[i] != '.') {
                Code.append((char)(Text[i] & 0xFF));
                i++;
            }
            Table.put(Integer.parseInt(Number.toString()), Code.toString());
            if (Text[i] == '.') {
                break;
            }
            i++;
        }
        return Table;
    }
}
