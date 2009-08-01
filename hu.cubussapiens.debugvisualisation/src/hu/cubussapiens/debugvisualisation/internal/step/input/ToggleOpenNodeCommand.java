/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step.input;

import hu.cubussapiens.debugvisualisation.internal.step.AbstractGraphCommand;

/**
 *
 */
public class ToggleOpenNodeCommand extends AbstractGraphCommand {

	/**
	 * Create a command on the given targets
	 * 
	 * @param target
	 */
	public ToggleOpenNodeCommand(Object... target) {
		super(target);
	}

}
