package hu.cubussapiens.zestlayouts;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * This class loads the extensions given to the LayoutAlgorithm
 * extension point.
 * @author Grill Balazs (balage.g@gmail.com)
 *
 */
public class LayoutRegistry {

	public static class LayoutEntry{
		
		protected final IConfigurationElement element;
		
		protected final String name;
		
		protected final String id;
		
		public LayoutEntry(IConfigurationElement element){
			this.element = element;
			this.id = element.getAttribute(ExtensionConstants.EPA_Layout_id);
			this.name = element.getAttribute(ExtensionConstants.EPA_Layout_name);
		}
		
		public String getName(){
			return name;
		}
		
		public String getID(){
			return id;
		}
		
		public ILayoutAlgorithmCreator getLayoutCreator(){
			try {
				return (ILayoutAlgorithmCreator)element.createExecutableExtension(ExtensionConstants.EPA_Layout_class);
			} catch (CoreException e) {
				e.printStackTrace();
				return null;
			}
		}
		
	}
	
	protected Set<LayoutEntry> entries = new HashSet<LayoutEntry>();
	
	public LayoutRegistry(){
		IExtensionRegistry registry = Platform.getExtensionRegistry();  
		IExtensionPoint point = registry.getExtensionPoint(ExtensionConstants.EP_LayoutAlgorithm);  
		for (IExtension extension : point.getExtensions()){  
			for (IConfigurationElement element : extension.getConfigurationElements()){  
				entries.add(new LayoutEntry(element));  
			}
		}
	}	  
	
	public Set<LayoutEntry> getEntries() {
		return entries;
	}
	
}
