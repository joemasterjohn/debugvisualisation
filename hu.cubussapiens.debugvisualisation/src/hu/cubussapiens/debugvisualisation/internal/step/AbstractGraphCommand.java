/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.step;

/**
 *
 */
public abstract class AbstractGraphCommand implements IGraphCommand {

	private final Object[] target;

	/**
	 * @param target
	 * 
	 */
	public AbstractGraphCommand(Object... target) {
		this.target = target;
	}

	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.step.IGraphCommand#getTarget()
	 */
	public Object[] getTarget() {
		return target;
	}

}
