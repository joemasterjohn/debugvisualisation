/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphChangeEvent;
import hu.cubussapiens.debugvisualisation.internal.step.IGraphChangeListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;

/**
 * This object accepts StackFrameContextInput as input, and provides content for
 * a Zest GraphViewer
 */
public class StackFrameGraphContentProvider implements
		IGraphEntityRelationshipContentProvider, IGraphChangeListener {

	StackFrameContextInput input = null;

	GraphViewer viewer = null;

	// final List<Object> edges = new ArrayList<Object>();

	/* (non-Javadoc)
	 * @see org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider#getRelationships(java.lang.Object, java.lang.Object)
	 */
	public Object[] getRelationships(Object source, Object dest) {
		List<Object> edges = new ArrayList<Object>();
		if (input != null) {
			edges.addAll(input.getEdge(source, dest));
		}
		return edges.toArray();
	}


	private void collectNodes(Set<Object> buffer, Object parent) {
		if ((parent != null) && (buffer.contains(parent)))
			return;
		if (parent != null)
			buffer.add(parent);
		Collection<Object> os = (parent == null) ? input.getRoots() : input
				.getChilds(parent);
		for (Object o : os)
			collectNodes(buffer, o);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object arg0) {
		List<Object> nodes = new ArrayList<Object>();
		if (input != null) {
			Set<Object> buff = new HashSet<Object>();
			collectNodes(buff, null);
			nodes.addAll(buff);
		}
		return nodes.toArray();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
		if (input != null) {
			input.removeListener(this);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer arg0, Object oldinput, Object newinput) {
		viewer = (GraphViewer) arg0;
		if (input != null){
			input.removeListener(this);
		}
		input = (StackFrameContextInput) newinput;
		if (input != null) {
			input.addListener(this);
		}
	}

	public void graphChanged(IGraphChangeEvent event) {
		System.out.println("Graph changed");// TODO println
		if (viewer != null) {
			// viewer.setInput(input);
			viewer.refresh();
			viewer.applyLayout();
		}
	}

}
