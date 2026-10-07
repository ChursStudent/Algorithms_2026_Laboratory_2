package lab;

public class ZigArray {
    public static int[] toZig(int[] Array) {
        int[] Zig = new int[64];
        boolean up = true;
        boolean down = false;
        int indexX = 0;
        int indexY = 0;
        Zig[0] = Array[0];
        for (int i = 0; i < 63; i++) {
            if (up) {
                if (indexX + 1 < 8 && indexY - 1 >= 0) {
                    indexX++;
                    indexY--;
                }
                else {
                    up = false;
                    down = true;
                    if (indexX + 1 == 8) {
                        indexY++;
                    }
                    else {
                        indexX++;
                    }
                }
            }
            else {
                if (indexX - 1 >= 0 && indexY + 1 < 8) {
                    indexX--;
                    indexY++;
                }
                else {
                    up = true;
                    down = false;
                    if (indexY + 1 == 8) {
                        indexX++;
                    }
                    else {
                        indexY++;
                    }
                }
            }
            Zig[i + 1] = Array[8*indexY + indexX];
        }
        return Zig;
    }

    public static int[] fromZig(int[] Zig) {
        int[] Array = new int[64];
        boolean up = true;
        boolean down = false;
        int indexX = 0;
        int indexY = 0;
        Array[0] = Zig[0];
        for (int i = 0; i < 63; i++) {
            if (up) {
                if (indexX + 1 < 8 && indexY - 1 >= 0) {
                    indexX++;
                    indexY--;
                }
                else {
                    up = false;
                    down = true;
                    if (indexX + 1 == 8) {
                        indexY++;
                    }
                    else {
                        indexX++;
                    }
                }
            }
            else {
                if (indexX - 1 >= 0 && indexY + 1 < 8) {
                    indexX--;
                    indexY++;
                }
                else {
                    up = true;
                    down = false;
                    if (indexY + 1 == 8) {
                        indexX++;
                    }
                    else {
                        indexY++;
                    }
                }
            }
            Array[8*indexY + indexX] = Zig[i + 1];
        }
        return Array;
    }

public static int[] MNToZig(int[] Array, int M, int N) {
        int[] Zig = new int[M * N];
        boolean up = true;
        boolean down = false;
        int indexX = 0;
        int indexY = 0;
        Zig[0] = Array[0];
        for (int i = 0; i < M * N - 1; i++) {
            if (up) {
                if (indexX + 1 < N && indexY - 1 >= 0) {
                    indexX++;
                    indexY--;
                }
                else {
                    up = false;
                    down = true;
                    if (indexX + 1 == N) {
                        indexY++;
                    }
                    else {
                        indexX++;
                    }
                }
            }
            else {
                if (indexX - 1 >= 0 && indexY + 1 < M) {
                    indexX--;
                    indexY++;
                }
                else {
                    up = true;
                    down = false;
                    if (indexY + 1 == M) {
                        indexX++;
                    }
                    else {
                        indexY++;
                    }
                }
            }
            Zig[i + 1] = Array[N*indexY + indexX];
        }
        return Zig;
    }

    public static int[] MNfromZig(int[] Zig, int M, int N) {
        int[] Array = new int[M * N];
        boolean up = true;
        boolean down = false;
        int indexX = 0;
        int indexY = 0;
        Array[0] = Zig[0];
        for (int i = 0; i < M * N - 1; i++) {
            if (up) {
                if (indexX + 1 < N && indexY - 1 >= 0) {
                    indexX++;
                    indexY--;
                }
                else {
                    up = false;
                    down = true;
                    if (indexX + 1 == N) {
                        indexY++;
                    }
                    else {
                        indexX++;
                    }
                }
            }
            else {
                if (indexX - 1 >= 0 && indexY + 1 < M) {
                    indexX--;
                    indexY++;
                }
                else {
                    up = true;
                    down = false;
                    if (indexY + 1 == M) {
                        indexX++;
                    }
                    else {
                        indexY++;
                    }
                }
            }
            Array[N*indexY + indexX] = Zig[i + 1];
        }
        return Array;
    }
}
