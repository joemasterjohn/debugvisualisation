/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.widgets;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * This class extends the Zest Graph viewer to replace its internal graph
 * widget.
 */
public class VisualisationGraphViewer extends GraphViewer {

	/**
	 * Initializes the graph viewer.
	 * 
	 * @param composite
	 *            the parent {@link Composite} widget.
	 * @param style
	 *            the style bits to give to the graph widget.
	 */
	public VisualisationGraphViewer(Composite composite, int style) {
		super(composite, style);
		graph.dispose();
		graph = new VisualisationGraph(composite, style);
		setControl(graph);
	}

}
