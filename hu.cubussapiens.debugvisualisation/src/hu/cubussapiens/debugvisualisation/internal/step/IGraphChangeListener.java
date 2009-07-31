/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

/**
 * A listener which can react to graph changes
 */
public interface IGraphChangeListener {

	/**
	 * This method is called when a change event is triggered in the graph
	 * 
	 * @param event
	 */
	public void graphChanged(IGraphChangeEvent event);

}
