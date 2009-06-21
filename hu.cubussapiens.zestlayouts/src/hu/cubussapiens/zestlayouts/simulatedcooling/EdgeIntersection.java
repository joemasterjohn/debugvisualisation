package hu.cubussapiens.zestlayouts.simulatedcooling;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * This criteria check every connection in the graph, and counts the
 * intersections between them. The value of this criteria will be the number of
 * intersections * the given factor. This criteria is intended to punish
 * intersections in graph, therefore the simulated cooling algorithm will try to
 * avoid these.
 */
public class EdgeIntersection implements Criteria {

	private final double factor;

	/**
	 * Creates an criteria to punish edge intersection
	 * 
	 * @param factor punishment for one intersection
	 */
	public EdgeIntersection(double factor) {
		this.factor = factor;
	}

	/**
	 * Decides whether the two given segments intersects each other. From:
	 * http://thirdpartyninjas.com/blog/2008/10/07/line-segment-intersection/
	 * 
	 * Segments given by: (x1,y1)->(x2,y2) and (x3,y3)->(x4,y4)
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return true if the two segments are intersects. False otherwise
	 */
	public boolean intersect(double x1, double y1, double x2, double y2,
			double x3, double y3, double x4, double y4) {

		double den = (y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1);

		//System.out.println("den:"+den);

		if (Math.abs(den) < 0.1) {

			return false;
		}

		double u1 = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / den;

		//System.out.println("u1:"+u1);

		if (!((u1 > 0) && (u1 < 1)))
			return false;

		double u2 = ((x2 - x1) * (y1 - y3) - (y2 - y1) - (x1 - x3)) / den;

		//System.out.println("u2:"+u2);

		return ((u2 > 0) && (u2 < 1));
	}

	/**
	 * {@inheritDoc}
	 */
	public double apply(LayoutEntity[] entities,
			LayoutRelationship[] relationships, double x, double y, double w,
			double h) {

		double result = 0;

		for (LayoutRelationship r1 : relationships) {
			LayoutEntity e11 = r1.getSourceInLayout();
			LayoutEntity e12 = r1.getDestinationInLayout();
			double x11 = e11.getXInLayout() + e11.getWidthInLayout() / 2;
			double y11 = e11.getYInLayout() + e11.getHeightInLayout() / 2;
			double x12 = e12.getXInLayout() + e12.getWidthInLayout() / 2;
			double y12 = e12.getYInLayout() + e12.getHeightInLayout() / 2;
			for (LayoutRelationship r2 : relationships)
				if (!r2.equals(r1)) {
					LayoutEntity e21 = r2.getSourceInLayout();
					LayoutEntity e22 = r2.getDestinationInLayout();
					if ((!e11.equals(e21)) && (!e11.equals(e22)) && (!e12.equals(e21)) && (!e12.equals(e22))) {
						double x21 = e21.getXInLayout() + e21.getWidthInLayout() / 2;
						double y21 = e21.getYInLayout() + e21.getHeightInLayout() / 2;
						double x22 = e22.getXInLayout() + e22.getWidthInLayout() / 2;
						double y22 = e22.getYInLayout() + e22.getHeightInLayout() / 2;

						if (intersect(x11, y11, x12, y12, x21, y21, x22, y22)) {
							result += factor;
						}
					}

				}
		}

		return result;
	}

}
