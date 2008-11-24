/**
 * 
 */
package hu.gbalage.debugvisualisation;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IValue;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ValueUtils {

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
	
}
