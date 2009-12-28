/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;

import java.util.Collection;

/**
 * Adapter interface to keep track of references to nodes
 */
public interface IReferenceTracker {

	/**
	 * Retrieve all known edges which target equals to the given node
	 * 
	 * @param node
	 * @return all known references to the given node
	 */
	public Collection<IDVVariable> getReferences(IDVValue node);

}
