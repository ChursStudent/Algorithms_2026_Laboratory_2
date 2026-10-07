package lab;

public class diffEnc {
    public static int[] DcDiffE (int[] DCs) {
        int[] Encoded = new int[DCs.length];
        int Previous = 0;
        for (int i = 0; i < Encoded.length; i++) {
            Encoded[i] = DCs[i] - Previous;
            Previous = DCs[i];
        }
        return Encoded;
    }

    public static int[] DcDiffD (int[] Encoded) {
        int[] DCs = new int[Encoded.length];
        int Current = 0;
        for (int i = 0; i < DCs.length; i++) {
            Current += Encoded[i];
            DCs[i] = Current;
        }
        return DCs;
    }
}
