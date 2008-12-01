/**
 * 
 */
package hu.gbalage.debugvisualisation.extensions.layouts;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator;
import hu.gbalage.isom.ISOMLayoutAlgorithm;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ISOMLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * @see hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		return new ISOMLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		/*return new CompositeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING,
				new LayoutAlgorithm[]{
				new ISOMLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
				new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING)
		});*/
	}

}
