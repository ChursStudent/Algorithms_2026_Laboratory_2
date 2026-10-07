package lab;

import java.util.ArrayList;
import java.util.List;

public class RLE {
    public static List<List<Integer>> AcRleE (List<int[]> ACs) {
        int Count = 0;
        List<List<Integer>> Encoded = new ArrayList<>();
        List<Integer> EncBlock;
        for (int[] Block : ACs) {
            EncBlock = new ArrayList<>();
            for (int i = 0; i < 63; i++) {
                while (i < 62 && Block[i] == 0 && Count < 15) {
                    Count++;
                    i++;
                }
                if (Block[i] == 0) {
                    if (i == 62) {
                        EncBlock.add(0);
                        EncBlock.add(0);
                    }
                    else {
                        EncBlock.add(Count);
                        EncBlock.add(0);
                    }
                }
                else {
                    EncBlock.add(Count);
                    EncBlock.add(Block[i]);
                }
                Count = 0;
            }
            Encoded.add(EncBlock);
        }
        return Encoded;
    }

    public static List<int[]> AcRleD (List<List<Integer>> Encoded) {
        List<int[]> ACs = new ArrayList<>();
        int[] Block;
        int index;
        for (List<Integer> EncBlock : Encoded) {
            Block = new int[63];
            index = 0;
            for (int i = 0; i < EncBlock.size(); i += 2) {
                if (EncBlock.get(i) == 0 && EncBlock.get(i + 1) == 0) {
                    while (index <= 62) {
                        Block[index] = 0;
                        index++;
                    }
                }
                else if (EncBlock.get(i) != 0) {
                    for (int j = EncBlock.get(i); j > 0; j--) {
                        Block[index] = 0;
                        index++;
                    }
                    Block[index] = EncBlock.get(i + 1);
                    index++;
                }
                else {
                    Block[index] = EncBlock.get(i + 1);
                    index++;
                }
            }
            ACs.add(Block);
        }
        return ACs;
    }
}
