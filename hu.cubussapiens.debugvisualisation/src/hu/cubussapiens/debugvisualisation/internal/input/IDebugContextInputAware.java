/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

/**
 * Interface for any object, whose operation is affected by the currently 
 * used IDebugContextInput
 * @author balage
 *
 */
public interface IDebugContextInputAware {

	/**
	 * Set the input to be used by this object
	 * @param input
	 */
	public void setInput(IDebugContextInput input);
	
}
