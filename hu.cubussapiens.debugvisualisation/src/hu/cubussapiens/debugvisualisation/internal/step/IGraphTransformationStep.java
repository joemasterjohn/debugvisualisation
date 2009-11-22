/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

/**
 * A rooted graph content provider, which can execute a transformation on the
 * graph. A parent of the transformation step should be a rooted graph content
 * provider. The step executes its transformation on the graph provided by the
 * parent, and provides the content of the transformed graph.
 */
public interface IGraphTransformationStep extends IRootedGraphContentProvider {

	/**
	 * Get the provider of the transformed graph.
	 * 
	 * @return parent of the transformation step
	 */
	public IRootedGraphContentProvider getParent();

	/**
	 * Add a listener to catch graph change events
	 * 
	 * @param listener
	 */
	public void addListener(IGraphChangeListener listener);

	/**
	 * Remove a listener
	 * 
	 * @param listener
	 */
	public void removeListener(IGraphChangeListener listener);

}
