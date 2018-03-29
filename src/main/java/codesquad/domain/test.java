package codesquad.domain;

import java.util.Arrays;
import java.util.Scanner;

public class test {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int N = Integer.parseInt(scanner.nextLine());
		int[] resultList = new int[N];
		for (int i = 0; i < N; i++) {
			int input;
			input = scanner.nextInt();
			resultList[i] = input;
		}
		Arrays.sort(resultList);
		System.out.println(resultList[N-1]);
	}
}
