/**
 * 
 */
package hu.cubussapiens.zestlayouts.creators;



import hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class TreeLayoutCreator implements ILayoutAlgorithmCreator {

	/**
	 * @see hu.cubussapiens.zestlayouts.ILayoutAlgorithmCreator#create()
	 */
	public LayoutAlgorithm create() {
		return new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
	}

}
