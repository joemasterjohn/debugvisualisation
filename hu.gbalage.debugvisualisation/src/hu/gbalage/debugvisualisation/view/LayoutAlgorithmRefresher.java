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
	private final IContinuableLayoutAlgorithm a;
	private boolean end = false;
	
	/**
	 * Call this if you want to stop executing this thread. The thread
	 * will end its run at the end of the current iteration.
	 */
	public void end(){
		end = true;
	}
	
	public LayoutAlgorithmRefresher(Graph graph,IContinuableLayoutAlgorithm alg){
		super();
		g = graph;
		a = alg;
	}
	
	@Override
	public void run() {
		super.run();
		//System.out.println("thread start!");
		do{
			g.applyLayout();
			try {
				sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}while((!end)&&a.needsRecall());
		//System.out.println("thread stop.");
	}
	
}
