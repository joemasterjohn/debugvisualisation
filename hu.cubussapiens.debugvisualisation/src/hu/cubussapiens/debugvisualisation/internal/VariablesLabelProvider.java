package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.internal.api.INodeParameters;
import hu.cubussapiens.debugvisualisation.internal.api.IOpenCloseNodes;
import hu.cubussapiens.debugvisualisation.internal.api.IReferenceTracker;
import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.step.input.OpenCloseNodeState;

import java.util.Collection;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;
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
		// if (StackFrameRootedGraphContentProvider.root.equals(element))
		// return Activator.getDefault().getImageRegistry().get(
		// Activator.icon_root);
		if (element instanceof IDVValue) {
			IOpenCloseNodes ocn = (IOpenCloseNodes) input
					.getAdapter(IOpenCloseNodes.class);
			Object o = ocn.getNodeState((IDVValue) element);
			if (o == null)
				return null;
			switch ((OpenCloseNodeState) o) {
			case Root:
				return Activator.getDefault().getImageRegistry().get(
						Activator.icon_root);
			case ChildLess:
				return PlatformUI.getWorkbench().getSharedImages().getImage(
						ISharedImages.IMG_OBJ_ELEMENT);
			case Open:
				return Activator.getDefault().getImageRegistry().get(
						Activator.icon_node_open);
			case Close:
				return Activator.getDefault().getImageRegistry().get(
						Activator.icon_node_closed);
			}
		}
		return null;
	}

	private String getRawText(Object element) {
		// if (StackFrameRootedGraphContentProvider.root.equals(element))
		// return "Local context";
		if (element instanceof IDVVariable) {
			IVariable var = (IVariable) ((IDVVariable) element)
					.getAdapter(IVariable.class);
			try {
				return var.getName();
			} catch (DebugException e) {
				Activator.getDefault().logError(e,
						"Error getting variable name");
			}
		}
		if (element instanceof IDVValue) {
			IValue node = (IValue) ((IDVValue) element)
					.getAdapter(IValue.class);

			IReferenceTracker reftracker = (IReferenceTracker) input
					.getAdapter(IReferenceTracker.class);
			Collection<?> refs = reftracker.getReferences((IDVValue) element);
			
			String name = "";
			for(Object ref : refs){
				try {
					String refname = (ref instanceof IVariable) ? ((IVariable) ref)
							.getName()
							: "" + ref;
					name = (name.equals("")) ? refname : name + ", " + refname;
				} catch (DebugException e) {
					Activator.getDefault().logError(e,
							"Error getting variable name");
				}

			}

			String type = ValueUtils.getValueString(node);
			name += ": " + type;

			INodeParameters nodeparameters = (INodeParameters) input
					.getAdapter(INodeParameters.class);
			Collection<IDVVariable> params = nodeparameters
					.getParameters((IDVValue) element);
			for (IDVVariable par : params) {
				IVariable param = (IVariable) par.getAdapter(IVariable.class);
				try {
					name += "\n" + param.getName() + "= "
							+ getProcessedValue(param.getValue()
									.getValueString());
				} catch (DebugException e) {
					Activator.getDefault().logError(e,
							"Error getting parameter value");
				}
			}

			return name;
		}
		return element.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getText(Object element) {
		// return getProcessedValue(getRawText(element));
		return getRawText(element);
	}

	public Color getColor(Object rel) {
		// return ColorConstants.black;
		return null;
	}

	public int getConnectionStyle(Object rel) {
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	public Color getHighlightColor(Object rel) {
		// return ColorConstants.darkGray;
		return null;
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
		// return ColorConstants.button;
		return null;
	}

	public Color getBorderColor(Object entity) {
		// return ColorConstants.darkGray;
		return null;
	}

	public Color getBorderHighlightColor(Object entity) {
		// return ColorConstants.black;
		return null;
	}

	public int getBorderWidth(Object entity) {
		return 1;
	}

	public Color getForegroundColour(Object entity) {
		// return ColorConstants.black;
		return null;
	}

	public Color getNodeHighlightColor(Object entity) {
		// return ColorConstants.buttonDarker;
		return null;
	}

}
