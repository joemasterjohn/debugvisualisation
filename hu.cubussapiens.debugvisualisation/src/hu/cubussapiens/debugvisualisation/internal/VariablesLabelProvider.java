/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.BundleImages;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput;
import hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInputAware;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * Generates labels and images for nodes in a GraphViewer. This object also needs to be
 * given the input of the viewer to work properly (this input has to be an 
 * IDebugContextInput instance)
 */
public class VariablesLabelProvider extends LabelProvider implements IDebugContextInputAware, IConnectionStyleProvider, IEntityStyleProvider {

	IDebugContextInput input;
	
	public void setInput(IDebugContextInput input) {
		this.input = input;
	}
	
	/**
	 * Load shared image from PlatformUI
	 * @param imageid
	 * @return
	 */
	private Image getSharedImage(String imageid){
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageid);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getImage(java.lang.Object)
	 */
	@Override
	public Image getImage(Object element) {
		if (element instanceof Integer){
			Integer node = (Integer)element;
			if (node == -1) return getSharedImage(ISharedImages.IMG_DEF_VIEW);
			if (!input.canOpen(node)){
				return getSharedImage(ISharedImages.IMG_OBJ_ELEMENT);
			}
			if (input.isOpen(node))
				return BundleImages.getInstance().getImage(BundleImages.icon_folder_open);
			else
				return BundleImages.getInstance().getImage(BundleImages.icon_folder);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.ILabelProvider#getText(java.lang.Object)
	 */
	@Override
	public String getText(Object element) {
		if (element instanceof Integer){
			Integer node = (Integer)element;
			if (node == -1) return "Local context";
			String name = "";
			for(IVariable v : input.getReferencesForNode(node)){
				try {
					name += v.getName()+" ";
				} catch (DebugException e) {
					e.printStackTrace(); //TODO: log error
				}
			}
			String type = ValueUtils.getValueString(input.getValue(node));//input.getValue(node).getReferenceTypeName();
			name += ": "+type;
			if (input.isOpen(node)){
				for(String param : input.getParams(node))
					name += "\n"+param;
			}
			return name;
		}
		return element.toString();
	}

	public Color getColor(Object rel) {
		return ColorConstants.black;
	}

	public int getConnectionStyle(Object rel) {
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	public Color getHighlightColor(Object rel) {
		return ColorConstants.darkGray;
	}

	public int getLineWidth(Object rel) {
		return 1;
	}

	public IFigure getTooltip(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean fisheyeNode(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	public Color getBackgroundColour(Object entity) {
		return ColorConstants.button;
	}

	public Color getBorderColor(Object entity) {
		return ColorConstants.darkGray;
	}

	public Color getBorderHighlightColor(Object entity) {
		return ColorConstants.black;
	}

	public int getBorderWidth(Object entity) {
		return 1;
	}

	public Color getForegroundColour(Object entity) {
		return ColorConstants.black;
	}

	public Color getNodeHighlightColor(Object entity) {
		return ColorConstants.buttonLightest;
	}

}
