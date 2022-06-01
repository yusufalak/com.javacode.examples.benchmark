package com.javacode.examples.benchmark;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class FibonacciBechmark {

	private static final int SERIES_NUM = 1_000;

	@Benchmark
	public BigDecimal benchmarkRecursiveFibonacci() {
		BigDecimal[] array = new BigDecimal[SERIES_NUM + 1];
		return recursiveFibonacci(array, SERIES_NUM);
	}

	@Benchmark
	public BigDecimal benchmarkForLoopFibonacci() {
		return forLoopFibonacci(SERIES_NUM);
	}

	private BigDecimal recursiveFibonacci(BigDecimal[] cachedFibonacci, int n) {
		if (n <= 1)
			return BigDecimal.valueOf(n);

		if (cachedFibonacci[n] != null)
			return cachedFibonacci[n];

		BigDecimal newFibonacciNumber = recursiveFibonacci(cachedFibonacci, n - 1).add(recursiveFibonacci(cachedFibonacci, n - 2));

		cachedFibonacci[n] = newFibonacciNumber;

		return newFibonacciNumber;
	}

	private BigDecimal forLoopFibonacci(int n) {

		BigDecimal[] fibonacciNumbers = new BigDecimal[n + 1];

		for (int i = 0; i < n + 1; i++) {

			if (i == 0) {
				fibonacciNumbers[i] = BigDecimal.valueOf(0);
			} else if (i == 1) {
				fibonacciNumbers[i] = BigDecimal.valueOf(1);
			} else {
				BigDecimal prevNumber2 = fibonacciNumbers[i - 2];
				BigDecimal prevNumber1 = fibonacciNumbers[i - 1];
				BigDecimal newNumber = prevNumber1.add(prevNumber2);
				fibonacciNumbers[i] = newNumber;
			}

		}

		return fibonacciNumbers[n];
	}
}
