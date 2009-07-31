/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

/**
 * Any command that can modify the graph
 */
public interface IGraphCommand {

	/**
	 * Get the array of nodes on which this operation should be executed
	 * 
	 * @return the target nodes of the command
	 */
	public Object[] getTarget();

}
