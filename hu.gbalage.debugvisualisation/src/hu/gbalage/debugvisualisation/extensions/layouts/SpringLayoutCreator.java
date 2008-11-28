/**
 * 
 */
package hu.gbalage.debugvisualisation.extensions.layouts;

import hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;


/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class SpringLayoutCreator implements ILayoutAlgorithmCreator {

	/* (non-Javadoc)
	 * @see hu.gbalage.debugvisualisation.layouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		return new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
