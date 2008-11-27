package hu.gbalage.debugvisualisation.model;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Defines a node in the abstract variable graph. The variable graph
 * is a directed graph, where 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface Node {

	public String getCaption();
	
	public String getType();
	
	
	public boolean isDisposable();
	
	public void dispose();
	
	
	public void addIn(Edge edge);
	
	public void removeIn(Edge edge);
	
	public void addOut(Edge edge);
	
	public void removeOut(Edge edge);
	
	
	public boolean isUnique();
	
	public int getID();
	
	public boolean isOpen();
	
	public void toggleOpen();
	
	
	public void changeValue(IValue value);
	
	public void setVariables(IVariable[] vars);
	
	public void addNodeChangeListener(NodeChangeListener listener);
}
