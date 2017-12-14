package preformanceTester.eMoflon;

import org.eclipse.emf.ecore.EObject;

public interface DeltaGenerator {
	public void applyForward(EObject source, int size);
	public void applyBackward(EObject target, int size);
}
