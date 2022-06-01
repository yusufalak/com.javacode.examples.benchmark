package com.javacode.examples.benchmark;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	private static final Integer MEASUREMENT_ITERATIONS = 5;
	private static final Integer WARMUP_ITERATIONS = 2;

	public static void main(String[] args) throws Exception {

		Path path = Path.of("Benchmark");
		String spe = path.getFileSystem().getSeparator();
		String resultFileName = path.toAbsolutePath().toString() + spe + "benchmark-results.json";

		File file = new File(resultFileName);

		file.deleteOnExit();
		Files.deleteIfExists(path.toAbsolutePath());
		Files.createDirectory(path.toAbsolutePath());

		Options jmhRunnerOptions = new OptionsBuilder()
				// set the class name regex for benchmarks to search for to the current class
				.include(FibonacciBechmark.class.getSimpleName())
				.warmupIterations(WARMUP_ITERATIONS)
				.measurementIterations(MEASUREMENT_ITERATIONS)
				// do not use forking or the benchmark methods will not see references stored within its class
				.forks(0)
				// do not use multiple threads
				.threads(1)
				.shouldDoGC(true)
				.shouldFailOnError(true)
				.resultFormat(ResultFormatType.JSON)
				.result(resultFileName) // set this to a valid filename if you want reports
				.shouldFailOnError(true)
				// maximum memory usage
				// .addProfiler(MaxMemoryProfiler.class)
				// 1GB Memory Allocacted
				.jvmArgs("-server", "-Xms1024m", "-Xmx1024m")
				.build();

		Runner r = new Runner(jmhRunnerOptions);
		r.run();
	}

}
