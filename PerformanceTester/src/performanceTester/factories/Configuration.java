package performanceTester.factories;

import performanceTester.Operationalization;

public class Configuration {
	public Configuration(int modelsize, int deltasize) {
		super();
		this.modelsize = modelsize;
		this.deltasize = deltasize;
	}

	public final int modelsize;
	public final int deltasize;
}