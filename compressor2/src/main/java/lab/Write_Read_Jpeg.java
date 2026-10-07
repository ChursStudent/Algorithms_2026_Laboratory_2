package lab;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static lab.AnyBmpToRaw.BTR;
import static lab.AnyRawToBmp.RTB;
import static lab.BlocksForDCT.fromBadBlocksToBytes2;
import static lab.CompileYBlocksAndCbCrChannels.YPlusCbCrToRaw;
import static lab.CompileYBlocksAndCbCrChannels.getCbCrFromEncoded;
import static lab.DCT.DctFaster;
import static lab.DCT.iDctFaster;
import static lab.Huffman.*;
import static lab.Quant.*;
import static lab.RLE.AcRleD;
import static lab.RLE.AcRleE;
import static lab.YCbCrConverter.fromYCbCr;
import static lab.YCbCrConverter.toYCbCr;
import static lab.byteReader.readBytes;
import static lab.diffEnc.DcDiffD;
import static lab.diffEnc.DcDiffE;
import static lab.downSample.downSampling;
import static lab.downSample.upSampling;
import static lab.getDC_AC.*;
import static lab.readMetaForYCbCr.readMetaYCC;
import static lab.renameFile.rename;
import static lab.varLenEnc.*;

public class Write_Read_Jpeg {
    public static String writeJpg () throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя bmp файла без расширения ");
        String FileName = in.nextLine();
        String RawFileName = BTR(FileName);
        String RawYCbCr = toYCbCr(RawFileName);

        byte[] Meta = readMetaYCC(RawYCbCr);

        downSample.Pair<String, byte[]> DsResult = downSampling(RawYCbCr);
        String RawYCbCrDS = DsResult.filenameWithY();
        byte[] CbCr = DsResult.cbcr();

        BlocksForDCT Blocks = new BlocksForDCT();
        Blocks.getBadBlocksForDCT2(RawYCbCrDS);

        List<int[]> BlocksDCTed = DctFaster(Blocks);

        Quant QMatrix = new Quant();
        System.out.println("Введите коэффициент качества от 1 до 99 (стандарт - 50) ");
        int Quality = in.nextInt();
        QMatrix.changeQuality(Quality);
        List<int[]> BlocksDCTedQed = Quantize(BlocksDCTed, QMatrix.QTable);
        byte[] QM = QMatrix.returnQMatrix();

        int[] DCs = getDCs (BlocksDCTedQed);

        List<int[]> ACs = getACs (BlocksDCTedQed);

        int[] diffEncoded = DcDiffE (DCs);
        List<List<Integer>> rleEncoded = AcRleE (ACs);

        DCVarLen[] varDCs = DcVarLenE (diffEncoded);
        List<List<ACVarLen>> varACs = AcVarLenE (rleEncoded);

        Huffman HTables = new Huffman();
        byte[] DcHuEncoded = DCHuE(varDCs, HTables.DCHuTable);

        byte[] AcHuEncoded = ACHuE(varACs, HTables.ACHuTable);

        byte[] DCHuT = HTables.returnDCHuTable();
        byte[] ACHuT = HTables.returnACHuTable();

        byte[] Result;
        if (Meta[Meta.length - 1] == 'Y') {
            Result = new byte[Meta.length + CbCr.length + QM.length + DCHuT.length + ACHuT.length + DcHuEncoded.length + AcHuEncoded.length];
            System.arraycopy(Meta, 0, Result, 0, Meta.length);
            System.arraycopy(CbCr, 0, Result, Meta.length, CbCr.length);
            System.arraycopy(QM, 0, Result, Meta.length + CbCr.length, QM.length);
            System.arraycopy(DCHuT, 0, Result, Meta.length + CbCr.length
                    + QM.length, DCHuT.length);
            System.arraycopy(ACHuT, 0, Result, Meta.length + CbCr.length
                    + QM.length + DCHuT.length, ACHuT.length);
            System.arraycopy(DcHuEncoded, 0, Result, Meta.length + CbCr.length
                    + QM.length + DCHuT.length + ACHuT.length, DcHuEncoded.length);
            System.arraycopy(AcHuEncoded, 0, Result, Meta.length + CbCr.length
                    + QM.length + DCHuT.length + ACHuT.length + DcHuEncoded.length, AcHuEncoded.length);
        }
        else {
            Result = new byte[Meta.length + QM.length + DCHuT.length + ACHuT.length + DcHuEncoded.length + AcHuEncoded.length];
            System.arraycopy(Meta, 0, Result, 0, Meta.length);
            System.arraycopy(QM, 0, Result, Meta.length, QM.length);
            System.arraycopy(DCHuT, 0, Result, Meta.length
                    + QM.length, DCHuT.length);
            System.arraycopy(ACHuT, 0, Result, Meta.length
                    + QM.length + DCHuT.length, ACHuT.length);
            System.arraycopy(DcHuEncoded, 0, Result, Meta.length
                    + QM.length + DCHuT.length + ACHuT.length, DcHuEncoded.length);
            System.arraycopy(AcHuEncoded, 0, Result, Meta.length
                    + QM.length + DCHuT.length + ACHuT.length + DcHuEncoded.length, AcHuEncoded.length);
        }
        Files.delete(Paths.get(RawFileName + ".raw"));
        Files.delete(Paths.get(RawYCbCr + ".raw"));
        Files.delete(Paths.get(RawYCbCrDS + ".raw"));
        Files.write(Paths.get(FileName + "JPEG.raw"), Result);
        System.out.println("JPEG записан в файл - " + FileName + "JPEG.raw");
        return FileName + "JPEG.raw";
    }

    public static String readJpg () throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("Введите имя raw файла (jpeg) без расширения ");
        String FileName = in.nextLine();

        int currentPos;
        byte[] Meta = readMetaYCC(FileName);
        currentPos = Meta.length;

        int width;
        int height;
        int i = 0;
        StringBuilder wid = new StringBuilder();
        while (Meta[i] != 'x') {
            wid.append((char)(Meta[i] & 0xFF));
            i++;
        }
        i++;
        StringBuilder hei = new StringBuilder();
        while (Meta[i] != 'Y' && Meta[i] != 'y' && Meta[i] != 'b') {
            hei.append((char)(Meta[i] & 0xFF));
            i++;
        }
        width = Integer.parseInt(wid.toString());
        height = Integer.parseInt(hei.toString());

        byte[] CbCr = null;
        int CbCrLen = 0;
        if (Meta[Meta.length - 1] == 'Y') {
            CbCr = getCbCrFromEncoded(FileName, currentPos, width * height);
            CbCrLen = CbCr.length;
        }
        currentPos += CbCrLen;

        byte[] Text = readBytes(FileName + ".raw");
        int[] QM = readQMatrix(Text, currentPos);

        for (; currentPos < Text.length; currentPos++) {
            if (Text[currentPos] == '.') {
                break;
            }
        }
        currentPos++;
        Map<Integer, String> DCHuT = readHuTable (Text, currentPos, 'D');

        for (; currentPos < Text.length; currentPos++) {
            if (Text[currentPos] == '.') {
                break;
            }
        }
        currentPos++;
        Map<Integer, String> ACHuT = readHuTable (Text, currentPos, 'A');

        for (; currentPos < Text.length; currentPos++) {
            if (Text[currentPos] == '.') {
                break;
            }
        }
        currentPos++;
        byte[] HuEncoded = new byte[Text.length - currentPos];
        System.arraycopy(Text, currentPos, HuEncoded, 0, HuEncoded.length);
        Pair <DCVarLen[], List<List<ACVarLen>>> HuDecoded = DcAcHuD(HuEncoded, width, height, DCHuT, ACHuT);

        int[] diffEncoded = DcVarLenD (HuDecoded.dcs());

        List<List<Integer>> rleEncoded = AcVarLenD (HuDecoded.acs());

        int[] DCs = DcDiffD (diffEncoded);
        List<int[]> ACs = AcRleD (rleEncoded);

        List<int[]> BlocksDCTedQed = putDcAc(DCs, ACs);

        List<int[]> BlocksDCTed = DeQuantize(BlocksDCTedQed, QM);

        BlocksForDCT Blocks = iDctFaster(BlocksDCTed, width, height);

        byte[] Y = fromBadBlocksToBytes2 (Blocks);

        byte[] origCbCr = null;
        if (CbCr != null) {
            origCbCr = upSampling(CbCr, width, height);
        }

        byte[] YCbCr = YPlusCbCrToRaw (Y, origCbCr, (char)(Meta[Meta.length - 1] & 0xFF));

        byte[] RawYCC = new byte[Meta.length + YCbCr.length];
        System.arraycopy(Meta, 0, RawYCC, 0, Meta.length);
        System.arraycopy(YCbCr, 0, RawYCC, Meta.length, YCbCr.length);
        Files.write(Paths.get(FileName + "Decompressed.raw"), RawYCC);
        String RawFileName = fromYCbCr(FileName + "Decompressed");
        Files.delete(Paths.get(FileName + "Decompressed.raw"));
        RTB(RawFileName);
        Files.delete(Paths.get(RawFileName + ".raw"));
        rename(RawFileName + "fromRaw.bmp", FileName + ".bmp");
        System.out.println("JPEG переведен в bmp - " + FileName + ".bmp");
        return FileName + ".bmp";
    }
}
