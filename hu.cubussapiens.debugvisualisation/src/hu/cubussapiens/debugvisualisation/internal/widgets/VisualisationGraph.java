/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal.widgets;

import hu.cubussapiens.zestlayouts.IContinuableLayoutAlgorithm;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.widgets.Graph;

/**
 *
 */
public class VisualisationGraph extends Graph {
	boolean isLayoutRunning = false;
	IContinuableLayoutAlgorithm algorithm;

	private class ApplyLayoutJob extends Job {

		Graph graph;

		public ApplyLayoutJob(String name) {
			super(name);
		}

		/**
		 * @param graph
		 *            the graph to set
		 */
		public void setGraph(Graph graph) {
			this.graph = graph;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.core.runtime.jobs.Job#canceling()
		 */
		@Override
		protected void canceling() {
			super.canceling();
			((IContinuableLayoutAlgorithm) graph.getLayoutAlgorithm()).cancel();
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus status;
			IContinuableLayoutAlgorithm algorithm;
			// monitor.setTaskName();
			int iteration = 0;
			int maxIteration = 50;
			monitor
					.beginTask(
							"If layouting is cancelled the current state will be preserved.",
							maxIteration);

			algorithm = (IContinuableLayoutAlgorithm) graph
					.getLayoutAlgorithm();
			while (!monitor.isCanceled() && !graph.isDisposed()
					&& iteration < maxIteration && algorithm.needsRecall()) {
				graph.applyLayout();
				algorithm = (IContinuableLayoutAlgorithm) graph
						.getLayoutAlgorithm();
				iteration++;
				monitor.worked(1);
			}
			monitor.worked(maxIteration - iteration);
			algorithm.finishLayouting();
			if (monitor.isCanceled()) {
				status = Status.CANCEL_STATUS;
			} else {
				status = Status.OK_STATUS;
			}
			isLayoutRunning = false;
			return status;
		}

	}

	/**
	 * @param parent
	 * @param style
	 */
	public VisualisationGraph(Composite parent, int style) {
		super(parent, style);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.zest.core.widgets.Graph#applyLayout()
	 */
	@Override
	public void applyLayout() {
		if (isLayoutRunning) {
			super.applyLayout();
			return;
		} else if (getLayoutAlgorithm() instanceof IContinuableLayoutAlgorithm
				&& !isLayoutRunning && !getNodes().isEmpty()) {
			// Start a layouting job
			isLayoutRunning = true;
			algorithm = (IContinuableLayoutAlgorithm) getLayoutAlgorithm();
			algorithm.startLayouting();
			ApplyLayoutJob job = new ApplyLayoutJob("Applying layout");
			job.setGraph(this);
			job.setUser(true);
			job.schedule();
			return;
		} else {
			super.applyLayout();
		}
	}
}
