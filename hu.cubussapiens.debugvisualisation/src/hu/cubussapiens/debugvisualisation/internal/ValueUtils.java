/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

/**
 * Some general utils on IValue objects
 *
 */
public class ValueUtils {

	/**
	 * Gets the unique ID for the value or -1, if the given value is primitive-typed and
	 * there fore does not has such ID.
	 * @param value
	 * @return
	 */
	public static int getID(IValue value){
		String s = "";
		try {
			s = value.getValueString().trim();
		} catch (DebugException e) {
			return -1;
		}
		if (!s.startsWith("(id=")) return -1;
		s = s.substring(4);
		if (!s.endsWith(")")) return -1;
		s = s.substring(0,s.length()-1);
		try{
			return Integer.parseInt(s);
		}catch(NumberFormatException e){
			return -1;
		}
	}
	
	/**
	 * Generates a string representation of the given value
	 * @param value
	 * @return
	 */
	public static String getValueString(IValue value){
		String typename;
		try {
			typename = value.getReferenceTypeName();
		
			int cut = Math.max(typename.lastIndexOf("."),typename.lastIndexOf("$"))+1;
			if ((cut>0)&&(cut<typename.length())) typename = typename.substring(cut);
			
			return typename;
		}catch(DebugException e){
			return "Error!";
		}
	}
	
}
