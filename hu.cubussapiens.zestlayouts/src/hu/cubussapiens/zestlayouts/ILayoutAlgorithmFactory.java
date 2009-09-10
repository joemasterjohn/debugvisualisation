package hu.cubussapiens.zestlayouts;

import org.eclipse.zest.layouts.LayoutAlgorithm;

/**
 * A wrapper interface which can create a LayoutAlgorithm and set the correct
 * parameters.
 * 
 */
public interface ILayoutAlgorithmFactory {

	/**
	 * 
	 * @return a newly created LayoutAlgorithm
	 */
	public LayoutAlgorithm create();

}
