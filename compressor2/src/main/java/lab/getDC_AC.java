package lab;

import java.util.ArrayList;
import java.util.List;

import static lab.ZigArray.toZig;
import static lab.ZigArray.fromZig;

public class getDC_AC {
    public static int[] getDCs (List<int[]> DctBlocksQ) {
        int[] DCs = new int[DctBlocksQ.size()];
        int index = 0;
        for (int[] Block : DctBlocksQ) {
            DCs[index] = Block[0];
            index++;
        }
        return DCs;
    }

    public static List<int[]> getACs (List<int[]> DctBlocksQ) {
        List<int[]> ACs = new ArrayList<>();
        int[] ACBlock;
        int[] ZigBlock;
        for (int[] Block : DctBlocksQ) {
            ACBlock = new int[63];
            ZigBlock = toZig(Block);
            System.arraycopy(ZigBlock, 1, ACBlock, 0, 63);
            ACs.add(ACBlock);
        }
        return ACs;
    }

    public static List<int[]> putDcAc (int[] DCs, List<int[]> ACs) {
        List<int[]> Blocks = new ArrayList<>();
        int[] Block;
        for (int i = 0; i < DCs.length; i++) {
            Block = new int[64];
            Block[0] = DCs[i];
            System.arraycopy(ACs.get(i), 0, Block, 1, 63);
            Blocks.add(fromZig(Block));
        }
        return Blocks;
    }
}
