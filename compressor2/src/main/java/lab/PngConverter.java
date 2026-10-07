package lab;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class PngConverter {
    public static void BmpToPng(String filename) {
        try {
            File input = new File(filename + ".bmp");

            BufferedImage image = ImageIO.read(input);

            File output = new File(filename + ".png");

            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            System.err.println("Error during conversion: " + e.getMessage());
        }
    }
    public static void PngToBmp(String filename) throws IOException {
        BufferedImage image = ImageIO.read(new File(filename + ".png"));

        ImageIO.write(image, "bmp", new File(filename + ".bmp"));
    }
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int Choice = 1;
        String FileName;
        while (Choice != 0) {
            System.out.println("0 - выход\n1 - из bmp в png\n2 - из png в bmp");
            Choice = in.nextInt();
            if (Choice == 1) {
                System.out.println("имя файла без расширения ");
                in.nextLine();
                FileName = in.nextLine();
                BmpToPng(FileName);
            }
            if (Choice == 2) {
                System.out.println("имя файла без расширения ");
                in.nextLine();
                FileName = in.nextLine();
                PngToBmp(FileName);
            }
        }
    }
}