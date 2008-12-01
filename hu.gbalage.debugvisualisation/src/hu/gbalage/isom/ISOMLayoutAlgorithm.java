/**
 * 
 */
package hu.gbalage.isom;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

/**
 * This LayoutAlgorithm implements the Inverted Self-Organizing Map
 * algorithm designed by Bernd Meyer. This implementation is based
 * on the Students' Conference work of András Pálinkás. 
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class ISOMLayoutAlgorithm extends AbstractLayoutAlgorithm {

	/**
	 * Path value between unconnected nodes
	 */
	private static int nullvalue = 99999;
	
	/**
	 * Number of iterative steps
	 */
	private static int maxstep = 1000;
	
	/**
	 * Cooling factor of node movement
	 */
	private static double coolingfactor = 5;
	
	/**
	 * Minimal adapting factor
	 */
	private static double adapt_min = 1;
	
	/**
	 * Maximal adapting factor 
	 */
	private static double adapt_max = 2;
	
	/**
	 * Maximal input radius
	 */
	private static double r_max = 5;
	
	/**
	 * Return the relocation factor for the given distance 
	 * @param distance directed steps between nodes
	 * @return a value between 0 and 1
	 */
	private double distanceFactor(int distance){
		if (distance == nullvalue) return 0;
		return Math.pow(2, -distance);
		//return Math.sqrt(1/(distance+1));
	}
	
	/**
	 * Contains the number steps (on directed edges) between each 
	 * pair of nodes. 
	 */
	private Map<LayoutEntity, Map<LayoutEntity,Integer>> path = new HashMap<LayoutEntity, Map<LayoutEntity,Integer>>();
	
	/**
	 * Returns the path map beginning from the given node. This
	 * method ensures that the Map is exists before returning it.
	 * @param node
	 * @return
	 */
	private Map<LayoutEntity,Integer> getPathFromNode(LayoutEntity node){
		if (path.containsKey(node)){
			return path.get(node);
		}else{
			Map<LayoutEntity,Integer> onepath = new HashMap<LayoutEntity, Integer>();
			path.put(node, onepath);
			return onepath;
		}
	}
	
	/**
	 * Update path value between the given nodes
	 * @param e1
	 * @param e2
	 * @param p
	 */
	private void putPath(LayoutEntity e1, LayoutEntity e2, int p){
		getPathFromNode(e1).put(e2, p);
	}
	
	/**
	 * 
	 * @param e1
	 * @param e2
	 * @return return the current path value between the given nodes
	 */
	private int getPath(LayoutEntity e1, LayoutEntity e2){
		return getPathFromNode(e1).get(e2).intValue();
	}
	
	/**
	 * Initialize the path map, before executing the Floyd-algorithm
	 * @param entitiesToLayout
	 * @param relationshipsToConsider
	 */
	private void initFloyd(LayoutEntity[] entitiesToLayout,InternalRelationship[] relationshipsToConsider){
		for (LayoutEntity e1 : entitiesToLayout) 
			for (LayoutEntity e2 : entitiesToLayout)
				if (e1.equals(e2)) putPath(e1,e2,0);
				else putPath(e1,e2,nullvalue);
		
		for (InternalRelationship r1 : relationshipsToConsider){
			LayoutRelationship r = r1.getLayoutRelationship();
			putPath(r.getSourceInLayout(),r.getDestinationInLayout(),1);
			putPath(r.getDestinationInLayout(),r.getSourceInLayout(),1);
		}
	}
	
	/**
	 * Execute Floyd algorithm
	 * @param entitiesToLayout
	 */
	private void runFloyd(LayoutEntity[] entitiesToLayout){
		/*for(LayoutEntity i : entitiesToLayout){
			for(LayoutEntity j : entitiesToLayout){
				System.out.print(getPath(i, j)+", ");
			}
			System.out.println();
		}*/
		
		for(LayoutEntity k : entitiesToLayout){
			for(LayoutEntity i : entitiesToLayout)
				for(LayoutEntity j : entitiesToLayout){
					int ij = getPath(i, j);
					int ik = getPath(i, k);
					int kj = getPath(k, j);
					putPath(i, j, Math.min(ij, ik + kj));
				}
		}
		
		/*for(LayoutEntity i : entitiesToLayout){
			for(LayoutEntity j : entitiesToLayout){
				System.out.print(getPath(i, j)+", ");
			}
			System.out.println();
		}*/
	}
	
	/**
	 * Map getLayoutEntity() for an array
	 * @param nodes
	 * @return
	 */
	public LayoutEntity[] convert(InternalNode[] nodes){
		LayoutEntity[] result = new LayoutEntity[nodes.length];
		for (int i=0;i<nodes.length;i++) result[i] = nodes[i].getLayoutEntity();
		return result;
	}
	
	/**
	 * @param styles
	 */
	public ISOMLayoutAlgorithm(int styles) {
		super(styles);
	}
	
	/**
	 * Generates a random value between min and max
	 * @param min
	 * @param max
	 * @return
	 */
	private double randvalue(double min, double max){
		double interval = max-min;
		return min+Math.random()*interval;
	}
	
	/**
	 * Current number of steps
	 */
	private int currstep;
	
	
	
	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#applyLayoutInternal(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[], double, double, double, double)
	 */
	@Override
	protected void applyLayoutInternal(InternalNode[] entities,
			InternalRelationship[] relationshipsToConsider, double boundsX,
			double boundsY, double boundsWidth, double boundsHeight) {

		LayoutEntity[] entitiesToLayout = convert(entities);
		
		currstep = 0;
		
		int interval = (int)Math.round(Math.floor(maxstep/r_max));
		
		double size = Math.min(boundsHeight-boundsY,boundsWidth-boundsX); 
		
		double r = r_max;
		
		fireProgressStarted(maxstep);
		
		//initial random layout
		for(LayoutEntity e : entitiesToLayout){
			e.setLocationInLayout(randvalue(boundsX,boundsWidth), randvalue(boundsY,boundsHeight));
			//System.out.println(e.getXInLayout());
		}
		
		//start iteration
		
		for(currstep = 1;currstep<=maxstep;currstep++){
			
			double linearfactor = Math.max(adapt_min, adapt_max * Math.pow(Math.E, -coolingfactor*currstep/maxstep));
			
			//fire a random input:
			double x = randvalue(boundsX,boundsWidth);
			double y = randvalue(boundsY,boundsHeight);
			
			//find closest node from input:
			LayoutEntity e = null;
			double d = 0;
			for(LayoutEntity e1 : entitiesToLayout){
				double d1 = Math.pow(e1.getXInLayout()-x,2)+Math.pow(e1.getYInLayout()-y,2);
				if ((e==null)||(d1<d)){
					d = d1;
					e = e1;
				}
			}
			
			//apply input to all nodes(moving them towards the input point)
			for(LayoutEntity e1 : entitiesToLayout){
				
				double dist = distanceFactor(getPath(e, e1));
				double move = (dist<r) ? size*linearfactor : 0;
				
				//make move
				double newx = e1.getXInLayout() + move*Math.signum(x-e1.getXInLayout());
				double newy = e1.getYInLayout() + move*Math.signum(y-e1.getYInLayout());
				
				//System.out.println(x+"-"+y+" | "+e1.getXInLayout()+"-"+e1.getYInLayout()+" => "+newx+"-"+newy+" | "+move);
				
				e1.setLocationInLayout(newx, newy);
			}
			
			if (currstep % interval == 0) r--;
			
			fireProgressEvent(currstep, maxstep);
			
		}
		
		fireProgressEnded(maxstep);
		
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#getCurrentLayoutStep()
	 */
	@Override
	protected int getCurrentLayoutStep() {
		return currstep;
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#getTotalNumberOfLayoutSteps()
	 */
	@Override
	protected int getTotalNumberOfLayoutSteps() {
		return maxstep;
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#isValidConfiguration(boolean, boolean)
	 */
	@Override
	protected boolean isValidConfiguration(boolean asynchronous,
			boolean continuous) {
		return true;
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#postLayoutAlgorithm(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[])
	 */
	@Override
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider) {
		//nothing to do
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#preLayoutAlgorithm(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[], double, double, double, double)
	 */
	@Override
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double x, double y,
			double width, double height) {
		
		path = new HashMap<LayoutEntity, Map<LayoutEntity,Integer>>();
		
		LayoutEntity[] ents = convert(entitiesToLayout);
		
		initFloyd(ents, relationshipsToConsider);
		
		runFloyd(ents);
	}

	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#setLayoutArea(double, double, double, double)
	 */
	@Override
	public void setLayoutArea(double x, double y, double width, double height) {
		//it is given in each call
	}

}
