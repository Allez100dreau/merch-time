package unice;

import java.util.Scanner;

public class MetaMerch {

    private static int[] subtractSet(int[] i, int[] j) {
        return i;
    }

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        int W = scanner.nextInt();
        int n = scanner.nextInt();
        int[] I = new int[n];

        for (int i = 0; i < n; i++) {
            I[i] = scanner.nextInt();
        }

        Method method = new MethodZ();
        int[][] S = method.solve(I, W);

        for (int[] J : S) {
            int[] K = subtractSet(I,J);
            int[][] T = method.solve(K, W);
            if (T.length == 0) {
                System.out.println("OUI");
                System.exit(0);
            }
        }
        System.out.println("NON");
    }
}
