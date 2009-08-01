package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.ImagePool;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.step.input.OpenCloseNodeState;
import hu.cubussapiens.debugvisualisation.internal.step.input.StackFrameRootedGraphContentProvider;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * Generates labels and images for nodes in a GraphViewer. This object also
 * needs to be given the input of the viewer to work properly (this input has to
 * be an IDebugContextInput instance)
 */
public class VariablesLabelProvider extends LabelProvider implements
		IConnectionStyleProvider, IEntityStyleProvider {

	StackFrameContextInput input;

	/**
	 * Give the currently used input object this this label provider
	 * 
	 * @param input
	 */
	public void setInput(StackFrameContextInput input) {
		this.input = input;
	}

	/**
	 * Processes the string value by trimming the middle of the string if the
	 * string is too long, and escapes \n sequences.
	 * 
	 * @param string
	 *            The string to process.
	 * @return The trimmed and escaped string.
	 */
	private String getProcessedValue(String string) {
		String newString = string;
		int length = newString.length();
		if (length > 20) {
			newString = string.substring(0, 8) + "..."
					+ string.substring(length - 9, length - 1);
		}
		return newString.replace("\n", "\\n");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Image getImage(Object element) {
		// if (element instanceof Integer) {
			// Integer node = (Integer) element;
			if (StackFrameRootedGraphContentProvider.root.equals(element))
				return ImagePool.image(ISharedImages.IMG_DEF_VIEW);
		if (element instanceof IValue) {
			Object o = input.getNodeState(element, OpenCloseNodeState.class);
			if (o == null)
				return null;
			switch ((OpenCloseNodeState) o) {
			case Root:
				return ImagePool.image(ISharedImages.IMG_DEF_VIEW);
			case ChildLess:
				return ImagePool.image(ISharedImages.IMG_OBJ_ELEMENT);
			case Open:
				return ImagePool.image(ImagePool.icon_node_open);
			case Close:
				return ImagePool.image(ImagePool.icon_node_closed);
			}
		}
		return null;
	}

	private String getRawText(Object element) {
		if (StackFrameRootedGraphContentProvider.root.equals(element))
			return "Local context";
		if (element instanceof IValue) {
			IValue node = (IValue) element;

			String name = "v";
			/*
			 * TODO: references for (IVariable v :
			 * input.getReferencesForNode(node)) { try { name += v.getName() +
			 * " "; } catch (DebugException e) {
			 * Activator.getDefault().logError(e,
			 * "Internal error in Debug Visualisation"); } }
			 */
			String type = ValueUtils.getValueString(node);// input.getValue(node).getReferenceTypeName();
			name += ": " + type;
			/*
			 * TODO: parameters if (input.isOpen(node)) { for (String param :
			 * input.getParams(node)) name += "\n" + param; }
			 */
			return name;
		}
		return element.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(Object element) {
		return getProcessedValue(getRawText(element));
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
