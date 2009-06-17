/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views.layouts;

import hu.cubussapiens.zestlayouts.LayoutManager;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * @author balage
 *
 */
public class LayoutAlgorithmContentProvider implements IStructuredContentProvider {

	private LayoutManager manager = null;
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object inputElement) {
		if (manager == null) return null;
		return manager.getEntries();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof LayoutManager)
			manager = (LayoutManager)newInput;
	}

}
