/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel;

import java.util.Collection;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * An adaptable view-model interface for a value
 */
public interface IDVValue extends IAdaptable, IDVProperties {

	/**
	 * Get the variables of this value
	 * 
	 * @return the variables of this value
	 */
	public IDVVariable[] getVariables();

	/**
	 * Tells if this value is accessible from the local context
	 * 
	 * @return true if this value is accessible from the local context
	 */
	public boolean isLocalContext();

	/**
	 * Marks this value as accessible from the local context.
	 */
	public void setLocalContext();

	/**
	 * <p>
	 * Get the parent variable, which contains this value. If the node is
	 * accessible directly from the local context, this will return null. It is
	 * important to note that only the first parent will be returned using this
	 * method.
	 * </p>
	 * 
	 * <p>
	 * It is important to note that a local context element can have a parent
	 * (if there is a reference circle present), but it will not be given back
	 * using this method. To get those parent use the {@link #getAllParents()}
	 * method.
	 * </p>
	 * 
	 * @see #getAllParents()
	 * @return the parent variable, or null if none
	 */
	public IDVVariable getParent();

	/**
	 * Return a collection of all the parents of the selected value. If no
	 * parent is available (local context variable without reference circles),
	 * an empty collection is returned.
	 * 
	 * @return A collection of the parent variables of the selected value
	 */
	public Collection<IDVVariable> getAllParents();

	/**
	 * Adds a parent to the list of parents. It does not change the return value
	 * of the {@link #getParent()} method.
	 * 
	 * @param parent
	 *            the parent variable to add
	 */
	public void addParent(IDVVariable parent);

	/**
	 * Removes a parent from the parent list. It does not change the return
	 * value of the {@link #getParent()} method.
	 * 
	 * @param parent
	 *            the parent variable to remove
	 */
	public void removeParent(IDVVariable parent);

	/**
	 * Get the container variable of the value contained in this node
	 * 
	 * @return the containing variable
	 */
	public IVariable getContainer();

	/**
	 * Returns the debug model element this element is based on
	 * 
	 * @return the debug model value
	 */
	public IValue getRelatedValue();

}
