/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

/**
 * This class represents a graph event, which is triggered by a command.
 */
public class ExecutedGraphCommandEvent implements IGraphChangeEvent {

	private final IGraphCommand command;

	/**
	 * @param command
	 * 
	 */
	public ExecutedGraphCommandEvent(IGraphCommand command) {
		this.command = command;
	}

	/**
	 * Get the command which was executed on the graph, and caused this event.
	 * 
	 * @return the command, which triggered this event
	 */
	public IGraphCommand getCommand() {
		return command;
	}

}
