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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;
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

	private Image getSharedImage(String imageid){
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageid);
	}
	
	private void refreshNode(final Node node){
		GraphNode n = nodes.get(node);
		n.setText(node.getCaption());
		switch(node.getState()){
		case Open:
			n.setBackgroundColor(DEFAULT_NODE_COLOR);
			n.setImage(getSharedImage(SharedImages.IMG_OBJ_PROJECT));
		break;
		case Closed:
			n.setBackgroundColor(LIGHT_BLUE_CYAN);
			n.setImage(getSharedImage(SharedImages.IMG_OBJ_PROJECT_CLOSED));
		break;
		case Root:
			n.setBackgroundColor(LIGHT_YELLOW);
			n.setImage(getSharedImage(ISharedImages.IMG_OBJS_INFO_TSK));
		break;
		case Primitive:
			n.setBackgroundColor(ColorConstants.cyan);
			n.setImage(getSharedImage(ISharedImages.IMG_OBJ_ELEMENT));
		break;
		}
	}
	
	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#addNode(hu.gbalage.debugvisualisation.model.Node)
	 */
	public void addNode(final Node node) {
		final GraphNode n = new GraphNode(this,SWT.NONE);
		nodes.put(node, n);
		n.setData(node);
		refreshNode(node);
		node.addNodeChangeListener(new NodeChangeListener(){
			public void changed() {
				refreshNode(node);	
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
