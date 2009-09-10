package hu.cubussapiens.zestlayouts.simulatedcooling.criteria;

import hu.cubussapiens.zestlayouts.simulatedcooling.ICriteria;

import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.zest.core.widgets.GraphNode;
import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * This criteria is similar to {@link PointDistribution}, but takes the sizes of
 * nodes into consideration, and punishes a graph layout more if the nodes are
 * overlapping. Every node pair is punished by a value of factor/d, where d is
 * the distance between the nodes.
 */
public class DistinctNodes implements ICriteria {

	private final double factor;

	/**
	 * Creates a criteria which punishes nodes which are too close to each
	 * other.
	 * @param factor
	 *            a linear factor to determine punishment, which is factor/d,
	 *            where d is the distance between the nodes.
	 */
	public DistinctNodes(double factor) {
		this.factor = factor;
	}

	/**
	 * {@inheritDoc}
	 */
	public double apply(LayoutEntity[] entities,
			LayoutRelationship[] relationships, double x, double y, double w,
			double h) {

		double result = 0;

		for (LayoutEntity i : entities)
			for (LayoutEntity j : entities)
				if (!i.equals(j)) {
					GraphNode iData = (GraphNode) i.getGraphData();
					Point pi = iData.getLocation();
					Dimension di = iData.getSize();
					GraphNode jData = (GraphNode) j.getGraphData();
					Point pj = jData.getLocation();
					Dimension dj = iData.getSize();
					double dist = Math.pow(pi.x + di.width / 2 - pj.x
							- dj.width / 2, 2)
							+ Math.pow(pi.y + di.height / 2 - pj.y - dj.height
									/ 2, 2);
					if (dist == 0) dist = 0.00001;

					result += factor / dist;
				}

		return result;
	}

}
