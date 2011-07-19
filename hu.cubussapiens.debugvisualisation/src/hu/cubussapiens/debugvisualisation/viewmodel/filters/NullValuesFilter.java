/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel.filters;

import hu.cubussapiens.debugvisualisation.viewmodel.IDVNullValue;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * A filter that disables disabling undefined values as nodes.
 */
public class NullValuesFilter extends ViewerFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		// return true;
		return !(element instanceof IDVNullValue);
	}

}
