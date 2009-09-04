/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering;

/**
 * A filter provider is used to retrieve filters for values. A filter is
 * identified by the textual representation of the type of the values on which
 * the filter is applicable.
 */
public interface IVariableFilterProvider {

	/**
	 * Returns the filter to apply on values of the given type, or null if there
	 * is no available filter for the type.
	 * 
	 * @param typename
	 * @return the filter for the type or null
	 */
	public IVariableFilter getFilter(String typename);

}
