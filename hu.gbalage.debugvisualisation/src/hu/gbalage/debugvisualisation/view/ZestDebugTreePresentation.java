/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hu.gbalage.debugvisualisation.layouts.IContinuableLayoutAlgorithm;
import hu.gbalage.debugvisualisation.model.Edge;
import hu.gbalage.debugvisualisation.model.EdgeCaptionListener;
import hu.gbalage.debugvisualisation.model.Node;
import hu.gbalage.debugvisualisation.model.NodeChangeListener;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE.SharedImages;
import org.eclipse.zest.core.widgets.Graph;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.core.widgets.ZestStyles;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ZestDebugTreePresentation extends Graph implements
		IDebugTreePresentation{
	
	Map<Node, GraphNode> nodes = new HashMap<Node, GraphNode>();
	
	Map<Edge, GraphConnection> edges = new HashMap<Edge, GraphConnection>();
	
	Menu menu;
	
	@SuppressWarnings("unchecked")
	/* Graph.getSelection() returns List<GraphNode>
	 */
	public List<GraphNode> getSelectedNodes(Graph graph){
		return (graph).getSelection();
	}
	
	public GraphNode getNode(Node n){
		if (!nodes.containsKey(n)) return null;
		return nodes.get(n);
	}
	
	/**
	 * Toggle open/close state of the currently selected nodes
	 * @param graph
	 */
	private void selectionToggleOpen(Graph graph){
		List<GraphNode> nodes = getSelectedNodes(graph);
		for(GraphNode n : nodes){
			Node node = (Node)n.getData();
			node.toggleOpen();
		}
		this.refresh();
	}
	
	/**
	 * Hide selected nodes
	 * @param graph
	 */
	private void selectionHide(Graph graph){
		List<GraphNode> nodes = getSelectedNodes(graph);
		for(GraphNode n : nodes){
			Node node = (Node)n.getData();
			node.toggleVisibility();
		}
		this.refresh();
	}
	
	private void selectionShowChilds(Graph graph){
		List<GraphNode> nodes = getSelectedNodes(graph);
		for(GraphNode n : nodes){
			Node node = (Node)n.getData();
			node.showHiddenChildNodes();
		}
		this.refresh();
	}
	
	private void createMenu(){
		menu = new Menu(this);
		
		MenuItem toggleOpen = new MenuItem(menu,SWT.PUSH);
		toggleOpen.setText("Open/close nodes");
		toggleOpen.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionToggleOpen(ZestDebugTreePresentation.this);
			}
		});
		
		MenuItem hide = new MenuItem(menu,SWT.PUSH);
		hide.setText("Hide nodes");
		hide.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionHide(ZestDebugTreePresentation.this);
			}
		});
		
		MenuItem showchilds = new MenuItem(menu,SWT.PUSH);
		showchilds.setText("Show hidden child nodes");
		showchilds.addSelectionListener(new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e) {
				selectionShowChilds(ZestDebugTreePresentation.this);
			}
		});
		
		this.setMenu(menu);
	}
	
	public ZestDebugTreePresentation(Composite parent, int style) {
		super(parent, style);
		createMenu();
		
		this.addMouseListener(new MouseAdapter(){
			
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				selectionToggleOpen((Graph)e.widget);
			}
		});

	}

	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#addEdge(hu.gbalage.debugvisualisation.model.Edge, hu.gbalage.debugvisualisation.model.Node, hu.gbalage.debugvisualisation.model.Node)
	 */
	public void addEdge(final Edge edge, Node from, Node to) {
		GraphNode n1 = nodes.get(from);
		GraphNode n2 = nodes.get(to);
		
		final GraphConnection c = new GraphConnection(this,SWT.NONE,n1,n2);
		
		edge.setDisplayCaptionListener(new EdgeCaptionListener(){
			public void displayCaption(boolean display) {
				if (display) c.setText(edge.getName());
				else c.setText("");
			}
		});
		
		edges.put(edge, c);
		c.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		c.setLineColor(ColorConstants.black);
		c.setData(edge);
		refreshNode(to);
	}

	/**
	 * Load shared image from PlatformUI
	 * @param imageid
	 * @return
	 */
	private Image getSharedImage(String imageid){
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageid);
	}
	
	private void refreshNode(final Node node){
		GraphNode n = nodes.get(node);
		n.setText(node.getCaption());
		n.setTooltip(new Label(node.getType()));
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
			n.setImage(getSharedImage(SharedImages.IMG_OPEN_MARKER));
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
		this.setSelection(new GraphItem[0]);
		c.dispose();
		edges.remove(edge);
	}

	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#removeNode(hu.gbalage.debugvisualisation.model.Node)
	 */
	public void removeNode(Node node) {
		GraphNode n = nodes.get(node);
		this.setSelection(new GraphItem[0]);
		n.dispose();
		nodes.remove(node);
	}
	
	private LayoutAlgorithmRefresher thread = null;
	
	/**
	 * @see hu.gbalage.debugvisualisation.view.IDebugTreePresentation#refresh()
	 */
	public synchronized void refresh() {
		if (thread != null){
			if (thread.isAlive())
				thread.end();
			thread = null;
		}
		
		if (this.getLayoutAlgorithm() instanceof IContinuableLayoutAlgorithm){	
			IContinuableLayoutAlgorithm alg = (IContinuableLayoutAlgorithm)this.getLayoutAlgorithm();
			(thread = new LayoutAlgorithmRefresher(this,alg)).start();
		}else
		this.applyLayout();		
	}

}
