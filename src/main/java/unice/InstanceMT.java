package unice;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class InstanceMT implements Instance {
    final Scanner scanner = new Scanner(System.in);
    final int capacity;
    final int N;
    final int[] weights;

    public InstanceMT() {
        capacity = scanner.nextInt();
        N = scanner.nextInt();
        int[] I = new int[N];

        for (int i = 0; i < N; i++) {
            I[i] = scanner.nextInt();
        }

        Collections.sort(Arrays.asList(I.clone()), Collections.<int[]>reverseOrder());
        weights = I;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getN() {
        return N;
    }

    @Override
    public int getWeight(int i) {
        return weights[i];
    }
}
