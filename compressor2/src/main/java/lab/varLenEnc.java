package lab;

import java.util.ArrayList;
import java.util.List;

public class varLenEnc {
    public static DCVarLen[] DcVarLenE (int[] diffEncoded) {
        DCVarLen[] Encoded = new DCVarLen[diffEncoded.length];
        String Code;
        for (int i = 0; i < Encoded.length; i++) {
            Code = Integer.toBinaryString(Math.abs(diffEncoded[i]));
            if (diffEncoded[i] < 0) {
                Code = Code.replace('1', '2');
                Code = Code.replace('0', '1');
                Code = Code.replace('2', '0');
                Encoded[i] = new DCVarLen(Code.length(), Code);
            }
            else if (diffEncoded[i] == 0) {
                Encoded[i] = new DCVarLen(0, "0");
            }
            else {
                Encoded[i] = new DCVarLen(Code.length(), Code);
            }
        }
        return Encoded;
    }

    public static int[] DcVarLenD (DCVarLen[] Encoded) {
        int[] Decoded = new int[Encoded.length];
        String Code;
        for (int i = 0; i < Decoded.length; i++) {
            if (Encoded[i].codeLen == 0) {
                Decoded[i] = 0;
            }
            else if (Encoded[i].code.charAt(0) == '0') {
                Code = Encoded[i].code;
                Code = Code.replace('1', '2');
                Code = Code.replace('0', '1');
                Code = Code.replace('2', '0');
                Decoded[i] = -Integer.parseInt(Code, 2);
            }
            else {
                Code = Encoded[i].code;
                Decoded[i] = Integer.parseInt(Code, 2);
            }
        }
        return Decoded;
    }

    public static List<List<ACVarLen>> AcVarLenE (List<List<Integer>> rleEncoded) {
        List<List<ACVarLen>> Encoded = new ArrayList<>();
        List<ACVarLen> EncBlock;
        String Code;
        for (List<Integer> Block : rleEncoded) {
            EncBlock = new ArrayList<>();
            for (int i = 0; i < Block.size(); i += 2) {
                Code = Integer.toBinaryString(Math.abs(Block.get(i + 1)));
                if (Block.get(i + 1) < 0) {
                    Code = Code.replace('1', '2');
                    Code = Code.replace('0', '1');
                    Code = Code.replace('2', '0');
                    EncBlock.add(new ACVarLen(Block.get(i), Code.length(), Code));
                } else if (Block.get(i + 1) == 0) {
                    EncBlock.add(new ACVarLen(Block.get(i), 0, "0"));
                } else {
                    EncBlock.add(new ACVarLen(Block.get(i), Code.length(), Code));
                }
            }
            Encoded.add(EncBlock);
        }
        return Encoded;
    }

    public static List<List<Integer>> AcVarLenD (List<List<ACVarLen>> Encoded) {
        List<List<Integer>> Decoded = new ArrayList<>();
        List<Integer> Block;
        String Code;
        for (List<ACVarLen> EncBlock : Encoded) {
            Block = new ArrayList<>();
            for (ACVarLen acVarLen : EncBlock) {
                if (acVarLen.codeLen == 0) {
                    Block.add(acVarLen.zeros);
                    Block.add(0);
                } else if (acVarLen.code.charAt(0) == '0') {
                    Code = acVarLen.code;
                    Code = Code.replace('1', '2');
                    Code = Code.replace('0', '1');
                    Code = Code.replace('2', '0');
                    Block.add(acVarLen.zeros);
                    Block.add(-Integer.parseInt(Code, 2));
                } else {
                    Code = acVarLen.code;
                    Block.add(acVarLen.zeros);
                    Block.add(Integer.parseInt(Code, 2));
                }
            }
            Decoded.add(Block);
        }
        return Decoded;
    }
}
