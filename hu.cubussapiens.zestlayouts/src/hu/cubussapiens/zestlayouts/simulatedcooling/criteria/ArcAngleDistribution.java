package hu.cubussapiens.zestlayouts.simulatedcooling.criteria;

import hu.cubussapiens.zestlayouts.simulatedcooling.ICriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Iterator;

import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.LayoutRelationship;

/**
 * This criteria tries to avoid having lot of parallel arcs close to each other
 * by punishing pair of arcs having a small angle between.
 * 
 */
public class ArcAngleDistribution implements ICriteria {

	private final double factor;

	/**
	 * Initializes an ArcAngleDistribution criteria with a given factor. The
	 * factor is multiplied to the final result.
	 * 
	 * @param factor
	 */
	public ArcAngleDistribution(double factor) {
		this.factor = factor;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.viatra2.visualisation.layouts.simulatedcooling.ICriteria#
	 * apply(org.eclipse.zest.layouts.LayoutEntity[],
	 * org.eclipse.zest.layouts.LayoutRelationship[], double, double, double,
	 * double)
	 */
	public double apply(LayoutEntity[] entities,
			LayoutRelationship[] relationships, double x, double y, double w,
			double h) {
		double punishment = 0;
		Hashtable<LayoutEntity, RelationshipOrderedSet> relationsByEntities = new Hashtable<LayoutEntity, RelationshipOrderedSet>(
				entities.length);
		arcCache = new Hashtable<LayoutRelationship, Double>(
				relationships.length);
		for (LayoutEntity entity : entities) {
			relationsByEntities.put(entity, new RelationshipOrderedSet());
		}
		for (LayoutRelationship relationship : relationships) {
			RelationshipOrderedSet from = relationsByEntities.get(relationship
					.getSourceInLayout());
			from.add(relationship);
			RelationshipOrderedSet to = relationsByEntities.get(relationship
					.getDestinationInLayout());
			to.add(relationship);
		}
		for (LayoutEntity entity : entities) {
			RelationshipOrderedSet set = relationsByEntities.get(entity);
			Collections.sort(set, new Comparator<LayoutRelationship>() {
				public int compare(LayoutRelationship o1, LayoutRelationship o2) {
					return Double.compare(calculateArc(o1), calculateArc(o2));
				}
			});
			if (set.size() == 0 || set.size() == 1)
				continue;
			double avg = 2 * Math.PI / set.size();
			LayoutRelationship current, next;
			Iterator<LayoutRelationship> it = set.iterator();
			current = it.next();
			while (it.hasNext()) {
				next = it.next();
				double diff = calculateArc(next) - calculateArc(current);
				punishment += Math.abs(avg - diff);
				current = next;
			}
			double diff = calculateArc(current) - calculateArc(set.get(0));
			punishment += Math.abs(avg - diff);
		}
		double value = punishment * factor;
		return value;
	}

	private Hashtable<LayoutRelationship, Double> arcCache;

	private double calculateArc(LayoutRelationship rel) {
		if (arcCache.containsKey(rel)) {
			return arcCache.get(rel).doubleValue();
		}
		double x1 = rel.getSourceInLayout().getXInLayout();
		double y1 = rel.getSourceInLayout().getYInLayout();
		double x2 = rel.getDestinationInLayout().getXInLayout();
		double y2 = rel.getDestinationInLayout().getYInLayout();
		double abs = Math.sqrt((Math.pow((x2 - x1), 2) + Math.pow(y2 - y1, 2)));
		if (abs == 0)
			return 0;
		double cos = (x2 - x1) / abs;
		double arc = Math.acos(cos);
		if (y2 < y1)
			arc += Math.PI;
		arcCache.put(rel, Double.valueOf(arc));
		return arc;
	}

	/**
	 * This class is used to store a set of LayoutRelationships. Currently it
	 * acts only as a shorthand to ArrayList<LayoutRelationship>.
	 * 
	 */
	class RelationshipOrderedSet extends ArrayList<LayoutRelationship> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -505454463056693416L;
	}
}
