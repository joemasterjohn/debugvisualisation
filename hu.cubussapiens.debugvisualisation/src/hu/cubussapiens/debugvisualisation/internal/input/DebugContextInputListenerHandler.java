/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.input;

import java.util.HashSet;
import java.util.Set;

/**
 * @author balage
 *
 */
abstract class DebugContextInputListenerHandler implements IDebugContextInput {

	private Set<IDCInputChangeListener> listeners = new HashSet<IDCInputChangeListener>();
	
	/* (non-Javadoc)
	 * @see hu.cubussapiens.debugvisualisation.internal.input.IDebugContextInput#addDCInputChangeListener(hu.cubussapiens.debugvisualisation.internal.input.IDCInputChangeListener)
	 */
	public void addDCInputChangeListener(IDCInputChangeListener listener) {
		listeners.add(listener);
	}

	public void removeDCInputChangeListener(IDCInputChangeListener listener) {
		listeners.remove(listener);
	}
	
	private final IDCInputChangeListener triggerer = new IDCInputChangeListener(){
		public void openStateChanged(Integer node, boolean open) {
			for(IDCInputChangeListener l : listeners)
				l.openStateChanged(node, open);
		}
		public void refreshed() {
			for(IDCInputChangeListener l : listeners)
				l.refreshed();
		}
		public void visibilityChanged(Integer node, boolean visible) {
			for(IDCInputChangeListener l : listeners)
				l.visibilityChanged(node, visible);
		}
	};

	/**
	 * This method can be used to trigger an event to the registered listeners. If any method
	 * is called in the listener given by this method, the appropriate method is called
	 * in every registered listener.
	 * @return
	 */
	protected IDCInputChangeListener trigger(){
		return triggerer;
	}
	
}
