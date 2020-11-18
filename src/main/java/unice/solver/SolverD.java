package unice.solver;

import java.util.Scanner;

public class SolverD {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int sackMax = input.nextInt();

        int[] weights = new int[input.nextInt()];
        for (int i = 0; i < weights.length; i++) {
            weights[i] = input.nextInt();
        }
        //Total capacity of the knapsack sackMax
        //list of all weights weights[]
        fillSack(sackMax, weights);
    }

    //return max to be put in sack
    public static void fillSack(int sackMax, int[] weights) {
        int numberOfItems = weights.length;
        int item, itemWeight;
        int[][] matrix = new int[numberOfItems + 1][sackMax + 1];

        for (item = 0; item <= numberOfItems; item++) {
            for (itemWeight = 0; itemWeight <= sackMax; itemWeight++) {
                if (item == 0 || itemWeight == 0) {
                    matrix[item][itemWeight] = 0;
                } else if (weights[item - 1] <= itemWeight) {
                    matrix[item][itemWeight] = Math.max(weights[item - 1] + matrix[item - 1][itemWeight - weights[item - 1]], matrix[item - 1][itemWeight]);
                } else {
                    matrix[item][itemWeight] = matrix[item - 1][itemWeight];
                }
            }
        }

        System.out.println();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println();

        if (matrix[numberOfItems][sackMax] == sackMax) {
            System.out.println("POSSIBLE");
        } else {
            System.out.println("IMPOSSIBLE");
        }
    }
}