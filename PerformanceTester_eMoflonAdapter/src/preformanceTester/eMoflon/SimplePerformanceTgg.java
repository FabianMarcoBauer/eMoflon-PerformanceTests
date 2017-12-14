package preformanceTester.eMoflon;

import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;
import org.moflon.tgg.algorithm.modelgenerator.controller.MaxModelSizeController;
import org.moflon.tgg.algorithm.synchronization.SynchronizationHelper;
import org.moflon.tgg.language.DomainType;

import MoDiscoTGG.MoDiscoTGGPackage;
import performanceTester.factories.Configuration;
import performanceTester.factories.PerformanceTgg;

public class SimplePerformanceTgg
		extends PerformanceTgg<SaveableModelGenerator, SynchronizationHelper, SynchronizationHelper> {

	private ModelHelper helper;

	public SimplePerformanceTgg(String name, ModelHelper helper) {
		super(name);
		this.helper = helper;
	}

	@Override
	public SaveableModelGenerator prepareGen(performanceTester.factories.Configuration c) {
		AbstractModelGenerationController controller = helper.getControllerSupplier().get();
		controller.addContinuationController(new MaxModelSizeController(c.modelsize, DomainType.SOURCE));

		return new SaveableModelGenerator(MoDiscoTGGPackage.eINSTANCE, controller);
	}

	@Override
	public void executeGen(SaveableModelGenerator tgg, performanceTester.factories.Configuration c) {
		tgg.generate();
	}

	@Override
	public SynchronizationHelper prepareCC(performanceTester.factories.Configuration c) {
		SynchronizationHelper synchronizationHelper = new SynchronizationHelper(helper.getePackage(), helper.getPath());
		synchronizationHelper.loadSrc(helper.getPath() + "instances/" + c.modelsize + "Element/src.xmi");
		synchronizationHelper.loadTrg(helper.getPath() + "instances/" + c.modelsize + "Element/trg.xmi");

		return synchronizationHelper;
	}

	@Override
	public void executeCC(SynchronizationHelper tgg, performanceTester.factories.Configuration c) {
		tgg.createCorrespondences(false);
	}

	@Override
	public void saveModels(SaveableModelGenerator tgg, performanceTester.factories.Configuration c) {
		tgg.save(helper.getPath() + "instances/" +c.modelsize + "Element");
	}

	@Override
	public SynchronizationHelper prepareFwd(performanceTester.factories.Configuration c) {
		SynchronizationHelper synchronizationHelper = new SynchronizationHelper(helper.getePackage(), helper.getPath());
		synchronizationHelper.loadSrc(helper.getPath() + "instances/" + c.modelsize + "Element/src.xmi");

		return synchronizationHelper;
	}

	@Override
	public void executeFwd(SynchronizationHelper tgg, performanceTester.factories.Configuration c) {
		tgg.integrateForward();
	}

	@Override
	public void applyFwdDelta(SynchronizationHelper tgg, Configuration c) {
		tgg.setChangeSrc(trg -> helper.getDeltaGenerator().get().applyForward(tgg.getSrc(), c.deltasize));
		tgg.integrateForward();
	}

	@Override
	public SynchronizationHelper prepareBwd(performanceTester.factories.Configuration c) {
		SynchronizationHelper synchronizationHelper = new SynchronizationHelper(helper.getePackage(), helper.getPath());
		synchronizationHelper.loadTrg(helper.getPath() + "/instances/" + c.modelsize + "Element/trg.xmi");

		return synchronizationHelper;
	}

	@Override
	public void executeBwd(SynchronizationHelper tgg, performanceTester.factories.Configuration c) {
		tgg.integrateBackward();
	}

	@Override
	public void applyBwdDelta(SynchronizationHelper tgg, Configuration c) {
		tgg.setChangeTrg(trg -> helper.getDeltaGenerator().get().applyBackward(tgg.getTrg(), c.deltasize));
		tgg.integrateBackward();
	}

}
