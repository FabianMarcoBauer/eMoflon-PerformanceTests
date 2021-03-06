package preformanceTester.eMoflon.generators

import java.nio.file.Files
import java.nio.file.Paths
import java.util.Arrays
import performanceTester.TestCaseParameters
import performanceTester.generators.Constants

class LaunchConfiguration {

	public final static String maxMemorySize = Constants.maxMemorySize
	
	/** Path where the LaunchConfiguration is saved by default. */
	public final static String launchConfigPath = "performance/launchConfigs/"
	
	public final static String FACTORY_CLASS = "preformanceTester.eMoflon.factories.EMoflonPerformanceTggFactory"
	
	/** The test case that is performed by this configuration. */
	private TestCaseParameters params
	
	private boolean reset;
	
	public new(TestCaseParameters params, boolean reset) {
		this.params = params
		this.reset = reset
	}
	
	/** File name of the launch configuration. */
	public def getName() {
		return params.toString()
	}
	
	/** Saves the launch configuration in a file at the default location. */
	public def save() {
		save(launchConfigPath)
	}
	
	/** Saves the launch configuration in a file at the specified location. */
	public def save(String path) {
		Files.write(Paths.get(path+getName()+".launch"), Arrays.asList(getContents()))
	}
	
	/** The launch configuration template which is parameterized by the TestCaseParameters. */
	public def getContents() {
		return '''
		<?xml version="1.0" encoding="UTF-8" standalone="no"?>
		<launchConfiguration type="org.eclipse.jdt.launching.localJavaApplication">
			<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_PATHS">
				<listEntry value="/Testsuite/test/testsuite/ibex/performance/TestDataCollector.java"/>
			</listAttribute>
			<listAttribute key="org.eclipse.debug.core.MAPPED_RESOURCE_TYPES">
				<listEntry value="1"/>
			</listAttribute>
			<stringAttribute key="org.eclipse.jdt.launching.MAIN_TYPE" value="performanceTester.PerformanceTester"/>
			<stringAttribute key="org.eclipse.jdt.launching.PROJECT_ATTR" value="PerformanceTester_eMoflonAdapter"/>
			<stringAttribute key="org.eclipse.jdt.launching.PROGRAM_ARGUMENTS" value="«FACTORY_CLASS» «params.operationalization.name()» «params.modelSize» «Constants.deltaSize» «Constants.repetitions» «params.tgg»"/>
			<stringAttribute key="org.eclipse.jdt.launching.VM_ARGUMENTS" value="-Xmx«maxMemorySize»"/>
		</launchConfiguration>
		
		'''
	}
}
	