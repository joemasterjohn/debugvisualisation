/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.gbalage.debugvisualisation.model.Edge;
import hu.gbalage.debugvisualisation.model.Node;
import hu.gbalage.debugvisualisation.model.NodeChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ZestDebugTreePresentation extends Graph implements
		IDebugTreePresentation {

	Map<Node, GraphNode> nodes = new HashMap<Node, GraphNode>();
	
	Map<Edge, GraphConnection> edges = new HashMap<Edge, GraphConnection>();
	
	
	public ZestDebugTreePresentation(Composite parent, int style) {
		super(parent, style);
		
		this.addMouseListener(new MouseAdapter(){
			@SuppressWarnings("unchecked")
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				List<GraphNode> nodes = ((Graph) e.widget).getSelection();
				for(GraphNode n : nodes){
					Node node = (Node)n.getData();
					node.toggleOpen();
				}
				ZestDebugTreePresentation.this.refresh();
				//super.mouseDoubleClick(e);
			}
		});
	}

	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#addEdge(hu.gbalage.debugvisualisation.model.Edge, hu.gbalage.debugvisualisation.model.Node, hu.gbalage.debugvisualisation.model.Node)
	 */
	public void addEdge(Edge edge, Node from, Node to) {
		GraphNode n1 = nodes.get(from);
		GraphNode n2 = nodes.get(to);
		
		GraphConnection c = new GraphConnection(this,SWT.NONE,n1,n2);
		edges.put(edge, c);
		c.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		c.setLineColor(ColorConstants.black);
	}

	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#addNode(hu.gbalage.debugvisualisation.model.Node)
	 */
	public void addNode(final Node node) {
		final GraphNode n = new GraphNode(this,SWT.NONE);
		n.setText(node.getCaption());
		nodes.put(node, n);
		n.setData(node);
		node.addNodeChangeListener(new NodeChangeListener(){
			public void changed() {
				n.setText(node.getCaption());	
			}
		});
	}

	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#removeEdge(hu.gbalage.debugvisualisation.model.Edge)
	 */
	public void removeEdge(Edge edge) {
		GraphConnection c = edges.get(edge);
		c.dispose();
		edges.remove(edge);
	}

	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#removeNode(hu.gbalage.debugvisualisation.model.Node)
	 */
	public void removeNode(Node node) {
		GraphNode n = nodes.get(node);
		n.dispose();
		nodes.remove(node);
	}
	
	public void refresh() {
		this.applyLayout();		
	}

}
