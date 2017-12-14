package preformanceTester.eMoflon.factories;

import java.util.Random;
import java.util.function.Supplier;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;
import org.moflon.tgg.algorithm.modelgenerator.controller.DefaultModelGenController;
import org.moflon.tgg.algorithm.modelgenerator.controller.LimitedRandomRuleSelector;
import org.moflon.tgg.algorithm.modelgenerator.controller.MaxRulePerformCounterController;
import org.moflon.tgg.algorithm.modelgenerator.controller.TimeoutController;

import MoDiscoTGG.MoDiscoTGGPackage;
import common.delta.DeltaApplicator;
import performanceTester.generators.Constants;
import preformanceTester.eMoflon.DeltaGenerator;
import preformanceTester.eMoflon.ModelHelper;
import preformanceTester.eMoflon.SimplePerformanceTgg;

public class EMoflonPerformanceTggFactory extends SimplePerformanceTgg {

	private static final String basePath = "../";

	public EMoflonPerformanceTggFactory(String tggName) {
		super(tggName, getModelHelper(tggName));
	}

	private static ModelHelper getModelHelper(String tggName) {
		return new ModelHelper(getEPackage(tggName), getPath(tggName), getController(tggName),
				getDeltaGenerator(tggName));
	}

	private static EPackage getEPackage(String name) {
		switch (name) {
		case Constants.moDiscoTGG:
			return MoDiscoTGGPackage.eINSTANCE;
		default:
			throw new RuntimeException("no package registered for " + name);
		}
	}

	private static String getPath(String name) {
		switch (name) {
		default:
			return basePath + name + "/";
		}
	}

	private static Supplier<AbstractModelGenerationController> getController(String name) {
		switch (name) {
		case Constants.moDiscoTGG:
			return () -> {
				AbstractModelGenerationController controller = new DefaultModelGenController();
				controller.addContinuationController(new MaxRulePerformCounterController(10));
				controller.addContinuationController(new TimeoutController(5000));
				controller.setRuleSelector(new LimitedRandomRuleSelector().addRuleLimit("JavaModel2UmlModelRule", 1));
				return controller;
			};

		default:
			throw new RuntimeException("no controller registered for " + name);
		}
	}

	private static Supplier<DeltaGenerator> getDeltaGenerator(String name) {
		switch (name) {
		case Constants.moDiscoTGG:
			return () -> {
				return new DeltaGenerator() {

					@Override
					public void applyForward(EObject source, int size) {
						DeltaApplicator da = new DeltaApplicator(size);
						da.applyForward(da.generateIndexedSourceModel(source), new Random(0));
					}

					@Override
					public void applyBackward(EObject target, int size) {
						DeltaApplicator da = new DeltaApplicator(size);
						da.applyBackward(da.generateIndexedTargetModel(target), new Random(0));
					}
				};
			};
		default:
			throw new RuntimeException("no path registered for " + name);
		}
	}
}
