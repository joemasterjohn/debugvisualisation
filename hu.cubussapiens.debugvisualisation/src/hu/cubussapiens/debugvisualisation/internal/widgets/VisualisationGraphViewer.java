/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.widgets;

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;

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

	/**
	 * Saves the graph as an image file. The supported formats are the ones that
	 * is supported by the SWT ImageLoader class.
	 * 
	 * @param filename
	 *            the filename to save the file into
	 * @param format
	 *            the image format to use
	 * @see ImageLoader
	 */
	public void saveImage(String filename, int format) {
		Graph g = (Graph) getControl();
		Rectangle bounds = g.getContents().getBounds();
		Point size = new Point(g.getContents().getSize().width, g.getContents()
				.getSize().height);
		org.eclipse.draw2d.geometry.Point viewLocation = g.getViewport()
				.getViewLocation();
		final Image image = new Image(null, size.x, size.y);
		GC gc = new GC(image);
		SWTGraphics swtGraphics = new SWTGraphics(gc);

		swtGraphics.translate(-1 * bounds.x + viewLocation.x, -1 * bounds.y
				+ viewLocation.y);
		g.getViewport().paint(swtGraphics);
		gc.copyArea(image, 0, 0);
		gc.dispose();
		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { image.getImageData() };
		loader.save(filename, format);
	}
}
