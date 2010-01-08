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
 * A descendant of the Zest {@link Graph} widget, handling continuable layouts.
 */
public class VisualisationGraph extends Graph {
	boolean isLayoutRunning = false;
	IContinuableLayoutAlgorithm runningAlgorithm;

	private class ApplyLayoutJob extends Job {

		Graph graph;
		boolean cancelled;

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
			cancelled = true;
			((IContinuableLayoutAlgorithm) graph.getLayoutAlgorithm()).cancel();
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			IStatus status;
			IContinuableLayoutAlgorithm algorithm;
			cancelled = false;
			// monitor.setTaskName();
			int iteration = 0;
			int maxIteration = 50;
			monitor
					.beginTask(
							"If layouting is cancelled the current state will be preserved.",
							maxIteration);
			if (!(graph.getLayoutAlgorithm() instanceof IContinuableLayoutAlgorithm)) {
				applyLayout();
				return Status.CANCEL_STATUS;
			}
			algorithm = (IContinuableLayoutAlgorithm) graph
					.getLayoutAlgorithm();
			while (!cancelled
					&& !monitor.isCanceled()
					&& !graph.isDisposed()
					&& iteration < maxIteration
					&& algorithm.needsRecall()
					&& graph.getLayoutAlgorithm() instanceof IContinuableLayoutAlgorithm) {
				graph.applyLayout();
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
	 * Initializes a visualisation graph.
	 * 
	 * @param parent
	 * @param style
	 */
	public VisualisationGraph(Composite parent, int style) {
		super(parent, style);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.zest.core.widgets.Graph#applyLayout()
	 */
	@Override
	public void applyLayout() {
		if (isLayoutRunning) {
			if (getLayoutAlgorithm().equals(runningAlgorithm)) {
				super.applyLayout();
				return;
			} else {
				runningAlgorithm.cancel();
			}
		}
		if (getLayoutAlgorithm() instanceof IContinuableLayoutAlgorithm
				&& !isLayoutRunning && !getNodes().isEmpty()) {
			// Start a layouting job
			isLayoutRunning = true;
			runningAlgorithm = (IContinuableLayoutAlgorithm) getLayoutAlgorithm();
			runningAlgorithm.startLayouting();
			ApplyLayoutJob job = new ApplyLayoutJob("Applying layout");
			job.setGraph(this);
			job.setUser(true);
			job.schedule();
			return;
		} else {
			isLayoutRunning = false;
			super.applyLayout();
		}
	}
}
