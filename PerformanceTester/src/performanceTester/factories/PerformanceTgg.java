package performanceTester.factories;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import performanceTester.Operationalization;
import performanceTester.TestCaseParameters;
import performanceTester.TestDataPoint;

public abstract class PerformanceTgg<T, U, V> {
	private final String name;

	public PerformanceTgg(String name) {
		this.name = name;
	}

	public List<TestDataPoint> execute(Operationalization mode, int modelsize, int deltasize, int repetitions) {
		Configuration config = new Configuration(modelsize, deltasize);
		Function<Configuration, Object> generator = generatorFromOperationalization(mode);
		BiConsumer<Object, Configuration> executor = executorFromOperationalization(mode);

		long[] initTimes = new long[repetitions];
		long[] executionTimes = new long[repetitions];
		long[] additionalTimes = new long[repetitions];
		Operationalization additional = mode == Operationalization.FWD ? Operationalization.INCREMENTAL_FWD
				: mode == Operationalization.BWD ? Operationalization.INCREMENTAL_BWD : null;

		Object prepared = null;
		for (int i = 0; i < repetitions; i++) {
			long initStart = System.nanoTime();
			prepared = generator.apply(config);
			long initEnd = System.nanoTime();

			long exeStart = System.nanoTime();
			executor.accept(prepared, config);
			long exeEnd = System.nanoTime();

			initTimes[i] = (initEnd - initStart) / 1_000_000;
			executionTimes[i] = (exeEnd - exeStart) / 1_000_000;

			if (additional!=null) {
				long additionalStart = System.nanoTime();
				switch (additional) {
				case INCREMENTAL_FWD:
					applyFwdDelta((V) prepared, config);
					break;
				case INCREMENTAL_BWD:
					applyBwdDelta((V) prepared, config);
					break;
				}
				long additionalEnd = System.nanoTime();
				additionalTimes[i] = (additionalEnd - additionalStart) / 1_000_000;
			}

		}
		if (mode == Operationalization.MODELGEN) {
			saveModels((T) prepared, config);
		}
		TestDataPoint result = new TestDataPoint(initTimes, executionTimes);
		result.testCase = new TestCaseParameters(name, mode, modelsize);
		if (additional==null)
			return Arrays.asList(result);
		TestDataPoint addResult = new TestDataPoint(initTimes, additionalTimes);
		addResult.testCase = new TestCaseParameters(additional.name(), mode, modelsize);
		return Arrays.asList(result, addResult);
	}

	private Function<Configuration, Object> generatorFromOperationalization(Operationalization op) {
		switch (op) {
		case MODELGEN:
			return this::prepareGen;
		case CC:
			return this::prepareCC;
		case FWD:
			return this::prepareFwd;
		case BWD:
			return this::prepareBwd;
		default:
			throw new IllegalArgumentException("Invalid Operationalization: " + op);
		}
	}

	@SuppressWarnings("unchecked")
	private BiConsumer<Object, Configuration> executorFromOperationalization(Operationalization op) {
		switch (op) {
		case MODELGEN:
			return (t, c) -> this.executeGen((T) t, c);
		case CC:
			return (u, c) -> this.executeCC((U) u, c);
		case FWD:
			return (v, c) -> this.executeFwd((V) v, c);
		case BWD:
			return (v, c) -> this.executeBwd((V) v, c);
		default:
			throw new IllegalArgumentException("Invalid Operationalization: " + op);
		}
	}

	public abstract T prepareGen(Configuration c);

	public abstract void executeGen(T tgg, Configuration c);

	public abstract U prepareCC(Configuration c);

	public abstract void executeCC(U tgg, Configuration c);

	public abstract V prepareFwd(Configuration c);

	public abstract void executeFwd(V tgg, Configuration c);

	public abstract void applyFwdDelta(V tgg, Configuration c);

	public abstract V prepareBwd(Configuration c);

	public abstract void executeBwd(V tgg, Configuration c);

	public abstract void applyBwdDelta(V tgg, Configuration c);

	public abstract void saveModels(T tgg, Configuration c);
}
