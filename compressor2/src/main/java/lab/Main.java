package lab;

import java.io.IOException;
import java.util.Scanner;

import static lab.Write_Read_Jpeg.readJpg;
import static lab.Write_Read_Jpeg.writeJpg;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int Choice = 1;
        while (Choice != 0) {
            System.out.println("Введите:\n0, чтобы выйти\n1, чтобы закодировать в Jpeg\n2, чтобы расшифровать Jpeg");
            Choice = in.nextInt();
            if (Choice == 1) {
                writeJpg();
            }
            else if (Choice == 2) {
                readJpg();
            }
        }
    }
}