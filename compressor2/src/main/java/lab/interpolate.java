package lab;

public class interpolate {
    public static int interpolateLin(int x, int x1, int x2, int y1, int y2) { //x E (x1; x2)
        if (x2 > x1) {
            return ((x - x1) * y2 + (x2 - x) * y1)/(x2 - x1);
        } else {
            return ((x - x2) * y1 + (x1 - x) * y2)/(x1 - x2);
        }
    }

    public static int interpolateBilin(double x, double y, double x1, double x2, double y1, double y2, int z11, int z12, int z21, int z22) {
        return (int) ((((x - x1) * z12 + (x2 - x) * z11) / (x2 - x1) * (y2 - y) + ((x - x1) * z22 + (x2 - x) * z21) / (x2 - x1) * (y - y1)) / (y2 - y1));
    }

    public static int[] getPixel(byte[] YCC, double x, double y, int w, int h) {
        int[] pixelBlock = new int[12];
        int p11 = (int) (x - x % 1);
        int p12 = p11 + 1;
        if (p12 >= w) {
            p11--;
            p12--;
        }
        int p21 = (int) (y - y % 1);
        int p22 = p21 + 1;
        if (p22 >= h) {
            p21--;
            p22--;
        }
        pixelBlock[0] = YCC[3 * (p21 * w + p11)] & 0xFF;
        pixelBlock[1] = YCC[3 * (p21 * w + p11) + 1] & 0xFF;
        pixelBlock[2] = YCC[3 * (p21 * w + p11) + 2] & 0xFF;
        pixelBlock[3] = YCC[3 * (p21 * w + p12)] & 0xFF;
        pixelBlock[4] = YCC[3 * (p21 * w + p12) + 1] & 0xFF;
        pixelBlock[5] = YCC[3 * (p21 * w + p12) + 2] & 0xFF;
        pixelBlock[6] = YCC[3 * (p22 * w + p11)] & 0xFF;
        pixelBlock[7] = YCC[3 * (p22 * w + p11) + 1] & 0xFF;
        pixelBlock[8] = YCC[3 * (p22 * w + p11) + 2] & 0xFF;
        pixelBlock[9] = YCC[3 * (p22 * w + p12)] & 0xFF;
        pixelBlock[10] = YCC[3 * (p22 * w + p12) + 1] & 0xFF;
        pixelBlock[11] = YCC[3 * (p22 * w + p12) + 2] & 0xFF;
        return pixelBlock;
    }

    public static byte[] resizeImg(byte[] YCbCr, int oldW, int oldH, int newW, int newH) {
        byte[] newYCbCr = new byte[3 * newW * newH];
        double KW = (double) (oldW - 1) / (newW - 1);
        double KH = (double) (oldH - 1) / (newH - 1);
        int index = 0;
        double curX;
        double curY = 0;
        int[] bilinPixel;
        for (int h = 0; h < newH; h++) {
            curX = 0;
            for (int w = 0; w < newW; w++) {
                int[] PixelBlock = getPixel(YCbCr, curX, curY, oldW, oldH); //12
                bilinPixel = new int[3];
                for (int i = 0; i < 3; i++) {
                    bilinPixel[i] = interpolateBilin(curX - (int)curX, curY - (int)curY, 0, 1, 0, 1, PixelBlock[i], PixelBlock[3+i], PixelBlock[6+i], PixelBlock[9+i]);
                }
                newYCbCr[index] = (byte)bilinPixel[0];
                index++;
                newYCbCr[index] = (byte)bilinPixel[1];
                index++;
                newYCbCr[index] = (byte)bilinPixel[2];
                index++;
                curX += KW;
            }
            curY += KH;
        }
        return newYCbCr;
    }
}