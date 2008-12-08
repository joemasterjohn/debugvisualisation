/**
 * 
 */
package hu.gbalage.debugvisualisation.view;

import hu.gbalage.debugvisualisation.layouts.IContinuableLayoutAlgorithm;

import org.eclipse.zest.core.widgets.Graph;

/**
 * A thread managing a LayoutAlgorithm, that can be continued if needed.
 * This thread asks the algorithm if it should be continued and forces
 * the Graph to recall it if needed.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class LayoutAlgorithmRefresher extends Thread {

	private final Graph g;
	private IContinuableLayoutAlgorithm a;
	private boolean end = false;
	
	/**
	 * Call this if you want to stop executing this thread. The thread
	 * will end its run at the end of the current iteration.
	 */
	public void end(){
		end = true;
		this.interrupt();
		System.out.println("Thread interrupted.");
	}
	
	public LayoutAlgorithmRefresher(Graph graph,IContinuableLayoutAlgorithm alg){
		super();
		g = graph;
		a = alg;
	}
	
	public void waitLayout(){
		try {
			do{
				sleep(100);
			}while(g.getLayoutAlgorithm().isRunning());
		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
	}
	
	@Override
	public void run() {
		super.run();

		//System.out.println("thread start!");
		do{
			if (g.getLayoutAlgorithm() instanceof IContinuableLayoutAlgorithm)
				a = (IContinuableLayoutAlgorithm)g.getLayoutAlgorithm();
			if (end) return;
			//System.out.println("layout start");
			g.applyLayout();
			waitLayout();
			waitLayout();
			//System.out.println("layout end");
			if (end) return;
		}while((!end)&&a.needsRecall());
		//System.out.println("thread stop.");
	}
	
}
