package preformanceTester.eMoflon;

import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EPackage;
import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;

import common.delta.DeltaGenerator;

public class ModelHelper {
	private EPackage ePackage;
	private String path;
	private Supplier<AbstractModelGenerationController> controller;
	private Supplier<preformanceTester.eMoflon.DeltaGenerator> deltaGenerator;

	public ModelHelper(EPackage ePackage, String path, Supplier<AbstractModelGenerationController> controller,
			Supplier<preformanceTester.eMoflon.DeltaGenerator> function) {
		this.ePackage = ePackage;
		this.path = path;
		this.controller = controller;
		this.deltaGenerator = function;
	}

	public EPackage getePackage() {
		return ePackage;
	}

	public String getPath() {
		return path;
	}

	public Supplier<AbstractModelGenerationController> getControllerSupplier() {
		return controller;
	}

	public Supplier<preformanceTester.eMoflon.DeltaGenerator> getDeltaGenerator() {
		return deltaGenerator;
	}

}
