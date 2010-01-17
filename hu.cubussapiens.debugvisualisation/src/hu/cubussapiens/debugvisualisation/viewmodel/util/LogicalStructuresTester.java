/**
 * 
 */
package hu.cubussapiens.debugvisualisation.viewmodel.util;

import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILogicalStructureType;

/**
 *
 */
public class LogicalStructuresTester extends PropertyTester {

	/**
	 * 
	 */
	public LogicalStructuresTester() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.expressions.IPropertyTester#test(java.lang.Object, java.lang.String, java.lang.Object[], java.lang.Object)
	 */
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		if (receiver instanceof IDVValue) {
			ILogicalStructureType[] types = DebugPlugin
					.getLogicalStructureTypes(((IDVValue) receiver)
							.getRelatedValue());
			return types.length != 0;
		}
		return false;
	}

}
