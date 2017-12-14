package performanceTester;

import java.util.List;
import java.util.stream.Collectors;

public class PerformanceTestUtil {
	

	/**
	 * Returns those TestDataPoints from the testData which fit to the
	 * specified parameters tgg, op and modelSize. When null is
	 * passed for any parameter, then that parameter is not used for filtering.
	 */
	public List<TestDataPoint> filterTestResults(List<TestDataPoint> testData, String tgg, Operationalization op, Integer modelSize) {
		if (testData == null)
			return null;
		return testData.stream()
				  	   .filter(t -> t != null)
					   .filter(t -> tgg==null || t.testCase.tgg().equals(tgg))
					   .filter(t -> op==null || t.testCase.operationalization() == op)
					   .filter(t -> modelSize==null || t.testCase.modelSize() == modelSize)
					   .collect(Collectors.toList());
	}
		
}
