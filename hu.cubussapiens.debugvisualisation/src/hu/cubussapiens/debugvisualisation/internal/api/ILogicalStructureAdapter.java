/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.api;

import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;

import java.util.Collection;

/**
 *
 */
public interface ILogicalStructureAdapter {

	public void setLogicalStructure(Collection<IDVValue> nodes,
			String logicalStructure);
}
