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
	 * Execute the given command on the graph. If this transformation step does
	 * not recognizes the command, it provides it to its parent.
	 * 
	 * @param command
	 */
	public void execute(IGraphCommand command);

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

	/**
	 * Get the state for the given node in the specified state-domain. The
	 * state-domain tells that which aspect of node state should be returned.
	 * 
	 * @param node
	 * @param statedomain
	 * @return state of the node in the given domain
	 */
	public Object getNodeState(Object node, Object statedomain);

}
