/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInputAware;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;

/**
 * @author balage
 *
 */
public class VariablesLabelProvider implements ILabelProvider, IDebugContextInputAware {

	IDebugContextInput input;
	
	public void setInput(IDebugContextInput input) {
		this.input = input;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	public Image getImage(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	public String getText(Object element) {
		if (element instanceof Integer){
			Integer node = (Integer)element;
			if (node == -1) return "Local context";
			String name = "";
			for(IVariable v : input.getReferencesForNode(node)){
				try {
					name += v.getName()+" ";
				} catch (DebugException e) {
					e.printStackTrace();
				}
			}
			if (input.isOpen(node)){
				for(String param : input.getParams(node))
					name += "\n"+param;
			}
		}
		return "";
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#addListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		//no listeners
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#isLabelProperty(java.lang.Object, java.lang.String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		//always update the viewer
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IBaseLabelProvider#removeListener(org.eclipse.jface.viewers.ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		//no listeners
	}

}
