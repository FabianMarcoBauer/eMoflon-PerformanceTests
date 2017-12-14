package performanceTester.generators;

import java.io.File;

public class DirectoryGenerator {
	private static final String[] dirs = { "performance/data", "performance/launchConfigs", "performance/gnuplot_scripts", "performance/plots/AllTGGs","performance/plots/AllTGGsInit","performance/plots/InitTimes","performance/plots/MemoryUsage","performance/plots/ModelSizes","performance/plots/TGGsWithoutRefinements",};
	public static void main(String[] args) {
		check();
	}
	
	public static void check() {
		for (String s : dirs) {
			File dir = new File(s);
			if (!dir.exists())
				dir.mkdirs();
		}
	}
}
