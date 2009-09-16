package hu.cubussapiens.debugvisualisation.views.actions;

import hu.cubussapiens.debugvisualisation.internal.widgets.VisualisationGraphViewer;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

/**
 * An action responsible for saving the graph as a png image.
 */
public class SaveImageAction extends GraphAction {

	/**
	 * Initializes a Save Image action. It saves the graph as a png image.
	 * 
	 * @param viewer
	 */
	public SaveImageAction(VisualisationGraphViewer viewer) {
		super(viewer);
		setText("Save Image");
		setToolTipText("Saves the graph as a png image");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_ETOOL_SAVE_EDIT));
	}

	@Override
	public void run() {
		FileDialog save = new FileDialog(PlatformUI.getWorkbench().getDisplay()
				.getActiveShell(), SWT.SAVE);
		save.setFilterExtensions(new String[] { "*.png" });
		save.setFilterNames(new String[] { "PNG File" });
		DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date date = new Date();

		save.setFileName("Debug context (" + dateFormat.format(date) + ").png");
		String filename = save.open();
		((VisualisationGraphViewer) getViewer()).saveImage(filename,
				SWT.IMAGE_PNG);
	}
}
