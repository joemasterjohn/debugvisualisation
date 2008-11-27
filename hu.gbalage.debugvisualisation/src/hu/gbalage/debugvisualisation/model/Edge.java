package hu.gbalage.debugvisualisation.model;

/**
 * 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface Edge {

	public String getName();
	
	public Node getFrom();
	
	public Node getTo();
	
	public void dispose();
	
}
