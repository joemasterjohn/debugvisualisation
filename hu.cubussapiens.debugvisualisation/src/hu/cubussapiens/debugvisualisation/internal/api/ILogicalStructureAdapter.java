/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;

import java.util.Collection;

/**
 * An adapter interface for selecting a possible logical structure for nodes
 */
public interface ILogicalStructureAdapter {

	/**
	 * Selects a logical structure filter for the selected nodes
	 * 
	 * @param nodes
	 * @param logicalStructure
	 */
	public void setLogicalStructure(Collection<IDVValue> nodes,
			String logicalStructure);
}
