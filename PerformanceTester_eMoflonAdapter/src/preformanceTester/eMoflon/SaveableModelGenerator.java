package preformanceTester.eMoflon;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.moflon.core.utilities.eMoflonEMFUtil;
import org.moflon.tgg.algorithm.modelgenerator.DataContainer;
import org.moflon.tgg.algorithm.modelgenerator.ModelGenerator;
import org.moflon.tgg.algorithm.modelgenerator.controller.AbstractModelGenerationController;

public class SaveableModelGenerator extends ModelGenerator {

	public SaveableModelGenerator(EPackage tggPackage,
			AbstractModelGenerationController abstractTGGModelGenController) {
		super(tggPackage, abstractTGGModelGenController, false, false);
	}

	public void save(String folderName) {
		DataContainer state;
		Field dcField = null;
		Boolean accessible = false;
		try {
			dcField = ModelGenerator.class.getDeclaredField("dataContainer");
			accessible = dcField.isAccessible();
			dcField.setAccessible(true);
			state = (DataContainer) dcField.get(this);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		} finally {
			if (dcField != null && accessible != null)
				dcField.setAccessible(accessible);
		}

		List<EObject> src = new ArrayList<EObject>();
		src.addAll(state.getSrcTempOutputContainer().getPotentialRoots());

		List<EObject> trg = new ArrayList<EObject>();
		trg.addAll(state.getTrgTempOutputContainer().getPotentialRoots());

		folderName = folderName.endsWith("/") ? folderName : folderName + "/";

		if (src.size() > 1) {
			throw new RuntimeException("source model contains more than one element!");
		} else if (src.size() == 1) {
			EObject sourceRoot = src.get(0);
			state.getSrcTempOutputContainer().getPotentialRoots().remove(0);
			System.out.println(folderName);
			String fileName = folderName + "src.xmi";
			state.getResourceSet().createResource(eMoflonEMFUtil.createFileURI(fileName, false)).getContents()
					.add(sourceRoot);
			eMoflonEMFUtil.saveModel(state.getResourceSet(), sourceRoot, fileName);
		}

		if (trg.size() > 1) {
			throw new RuntimeException("target model contains more than one element!");
		} else if (trg.size() == 1) {
			EObject targetRoot = trg.get(0);
			state.getTrgTempOutputContainer().getPotentialRoots().remove(0);
			String fileName = folderName + "trg.xmi";
			state.getResourceSet().createResource(eMoflonEMFUtil.createFileURI(fileName, false)).getContents()
					.add(targetRoot);
			eMoflonEMFUtil.saveModel(state.getResourceSet(), targetRoot, fileName);
		}
	}

}
