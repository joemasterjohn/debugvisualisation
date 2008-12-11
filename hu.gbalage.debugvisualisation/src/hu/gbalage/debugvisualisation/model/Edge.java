package hu.gbalage.debugvisualisation.model;

/**
 * This class defines a directed edge in the variable graph. An 
 * edge means that the value represented by the parent node (from) 
 * has a child value (to) through a variable (edge). In other words,
 * an edge wraps a variable as a reference to a value. 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public interface Edge {

	/**
	 * @return the name of the variable represented by this edge.
	 */
	public String getName();
	
	/**
	 * @return the Node which from this edge begins.
	 */
	public Node getFrom();
	
	/**
	 * @return the Node where this edge ends.
	 */
	public Node getTo();
	
	/**
	 * Dispose this edge from the graph. This method automatically
	 * tells the referenced nodes (from and to), that this edge is
	 * disposed by calling from.removeOut() and to.removeIn()
	 */
	public void dispose();
	
	/**
	 * Set a listener to listen caption display changes.
	 * @param listener
	 */
	public void setDisplayCaptionListener(EdgeCaptionListener listener);
	
	/**
	 * Hides the caption of the edge
	 */
	public void showCaption();
	
	/**
	 * Shows the caption of the edge
	 */
	public void hideCaption();
}
