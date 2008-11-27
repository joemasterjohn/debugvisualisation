/**
 * 
 */
package hu.gbalage.debugvisualisation.filters;

import java.util.HashSet;
import java.util.Set;

import hu.gbalage.debugvisualisation.ExtensionConstants;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class FilterRegistry {

	/**
	 * Stores an entry for a filter.
	 * @author Grill Balazs (balage.g@gmail.com)
	 */
	public static class FilterEntry{
		
		protected final IConfigurationElement element; 
		
		protected final String typename;
		
		public FilterEntry(IConfigurationElement element){
			this.element = element;
			this.typename = element.getAttribute(ExtensionConstants.EPA_Filter_type);
		}
		
		public String getTypename() {
			return typename;
		}

		public IFilter createFilter(){
			try {
				return (IFilter)element.createExecutableExtension(ExtensionConstants.EPA_Filter_class);
			} catch (CoreException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	
	protected final Set<FilterEntry> entries = new HashSet<FilterEntry>();
	
	public FilterRegistry(){
		IExtensionRegistry registry = Platform.getExtensionRegistry();  
		IExtensionPoint point = registry.getExtensionPoint(ExtensionConstants.EP_Filter);  
		for (IExtension extension : point.getExtensions()){  
			for (IConfigurationElement element : extension.getConfigurationElements()){  
				entries.add(new FilterEntry(element));  
			}  
		}  
	}

	public Set<FilterEntry> getEntries() {
		return entries;
	}
	
}
