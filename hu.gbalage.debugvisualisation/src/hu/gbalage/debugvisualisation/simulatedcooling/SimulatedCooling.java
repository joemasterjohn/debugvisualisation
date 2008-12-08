/**
 * 
 */
package hu.gbalage.debugvisualisation.simulatedcooling;

import hu.gbalage.debugvisualisation.layouts.IContinuableLayoutAlgorithm;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

/**
 * Layout graph using simulated cooling algorithm
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class SimulatedCooling extends AbstractLayoutAlgorithm implements IContinuableLayoutAlgorithm{

	private static final double coolingfactor = 0.65;
	
	private static final double begintemp = 1000; 
	
	/**
	 * Map getLayoutEntity() for an array
	 * @param nodes
	 * @return
	 */
	private LayoutEntity[] convert(InternalNode[] nodes){
		LayoutEntity[] result = new LayoutEntity[nodes.length];
		for (int i=0;i<nodes.length;i++) result[i] = nodes[i].getLayoutEntity();
		return result;
	}
	
	/**
	 * @param relations
	 * @return
	 */
	private LayoutRelationship[] convert(InternalRelationship[] relations){
		LayoutRelationship[] result = new LayoutRelationship[relations.length];
		for (int i=0;i<relations.length;i++) result[i] = relations[i].getLayoutRelationship();
		return result;
	}
	
	private Criteria[] crits;
	
	public SimulatedCooling(int styles, Criteria[] criterias) {
		super(styles);
		crits = criterias;
	}

	private double getCriteria(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double boundsX,
			double boundsY, double boundsWidth, double boundsHeight){
		
		double result = 0;
		
		for(Criteria crit : crits){
			try{
				result += crit.apply(convert(entitiesToLayout),convert(relationshipsToConsider), boundsX, boundsY, boundsWidth, boundsHeight);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private double temp;
	
	
	private LayoutEntity movedentity = null;
	private double[] oldpos = null;
	
	private void applyRandomMove(LayoutEntity entity){
		movedentity = entity;
		oldpos = new double[]{entity.getXInLayout(),entity.getYInLayout()};
		
		double angle = Math.PI*2*Math.random();
		double d = Math.random()*temp;
		
		double newx = oldpos[0] + Math.sin(angle)*d;
		double newy = oldpos[1] + Math.cos(angle)*d;
		
		entity.setLocationInLayout(newx, newy);
	}
	
	private void undomove(){
		if (movedentity != null)
		movedentity.setLocationInLayout(oldpos[0], oldpos[1]);
		
		movedentity = null;
		oldpos = null;
	}
	
	private volatile boolean f_needsRecall;
	
	public boolean needsRecall() {
		return f_needsRecall;
	}
	
	/**
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#applyLayoutInternal(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[], double, double, double, double)
	 */
	@Override
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double boundsX,
			double boundsY, double boundsWidth, double boundsHeight) {
		
		fireProgressStarted(1);
		
		f_needsRecall = true;
		double valuedelta = 0;
		
		//move outbounded nodes inbound
		for(LayoutEntity e : convert(entitiesToLayout)){
			if ((e.getXInLayout() < boundsX)||
					(e.getXInLayout()+e.getWidthInLayout() > boundsWidth)||
					(e.getYInLayout() < boundsY)||
					(e.getYInLayout()+e.getHeightInLayout() > boundsHeight)){
				e.setLocationInLayout((boundsX+boundsWidth)/2, (boundsY+boundsHeight)/2);
			}
		}
		
		temp = begintemp;
		
		int step = 0;
		
		while(temp>1){
			
			//get criteria value for current configuration:
			double value = getCriteria(entitiesToLayout, relationshipsToConsider, boundsX, boundsY, boundsWidth, boundsHeight);
			
			//select a node
			LayoutEntity entity = entitiesToLayout[step%entitiesToLayout.length].getLayoutEntity();
			
			//random move for entity
			applyRandomMove(entity);
			
			//recalculate value for new configuration
			double newvalue = getCriteria(entitiesToLayout, relationshipsToConsider, boundsX, boundsY, boundsWidth, boundsHeight);
			
			//System.out.println(value+" -> "+newvalue);
			
			//the smaller the better.
			if (newvalue<=value){
				//reduce temperature
				temp = temp*coolingfactor;
				valuedelta += value-newvalue;
			}else{
				//undo the applied move.
				undomove();
			}
			
			step++;
		}
		
		//System.out.println("valudelta: "+valuedelta);
		if (valuedelta < 0.001) f_needsRecall = false;

		fireProgressEnded(1);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#getCurrentLayoutStep()
	 */
	@Override
	protected int getCurrentLayoutStep() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#getTotalNumberOfLayoutSteps()
	 */
	@Override
	protected int getTotalNumberOfLayoutSteps() {
		return 1;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#isValidConfiguration(boolean, boolean)
	 */
	@Override
	protected boolean isValidConfiguration(boolean asynchronous,
			boolean continuous) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#postLayoutAlgorithm(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[])
	 */
	@Override
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider) {
		// 

	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#preLayoutAlgorithm(org.eclipse.zest.layouts.dataStructures.InternalNode[], org.eclipse.zest.layouts.dataStructures.InternalRelationship[], double, double, double, double)
	 */
	@Override
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double x, double y,
			double width, double height) {
		// 

	}

	/* (non-Javadoc)
	 * @see org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm#setLayoutArea(double, double, double, double)
	 */
	@Override
	public void setLayoutArea(double x, double y, double width, double height) {
		//

	}

}
