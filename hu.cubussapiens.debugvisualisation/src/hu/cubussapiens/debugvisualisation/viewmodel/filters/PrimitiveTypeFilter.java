/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel.filters;

import hu.cubussapiens.debugvisualisation.internal.ValueUtils;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 *
 */
public class PrimitiveTypeFilter extends ViewerFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers
	 * .Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IDVValue) {
			IDVValue value = (IDVValue) element;
			return ValueUtils.getID(value.getRelatedValue()) != -1;
		} else {
			return true;
		}
	}

}
