/**
 * 
 */
package hu.gbalage.debugvisualisation.model;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class VariableEdge implements Edge {

	protected final Model model;
	
	protected final Node from;
	
	protected final Node to;
	
	protected final String name;
	
	public VariableEdge(Model model,Node from, Node to,String name){
		this.model = model;
		this.from = from;
		this.to = to;
		this.name = name;
		
		from.addOut(this);
		to.addIn(this);
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.model.Edge#dispose()
	 */
	public void dispose() {
		from.removeOut(this);
		to.removeIn(this);
		model.removeEdge(this);
		
		//if to has no more edges, it needs to be removed
		if (to.isDisposable()) to.dispose();
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Edge#getFrom()
	 */
	public Node getFrom() {
		return from;
	}

	/**
	 * @see hu.gbalage.debugvisualisation.model.Edge#getTo()
	 */
	public Node getTo() {
		return to;
	}

	public String getName() {
		return name;
	}

}
