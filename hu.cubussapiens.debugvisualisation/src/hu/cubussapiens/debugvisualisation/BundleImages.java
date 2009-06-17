/**
 * 
 */
package hu.cubussapiens.debugvisualisation;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.osgi.framework.Bundle;

/**
 *
 */
public class BundleImages{

	public static final String icon_refresh = "/icons/view-refresh.png";
	
	public static final String icon_folder = "/icons/folder.png";
	
	public static final String icon_folder_open = "/icons/folder-open.png";
	
	private ImageRegistry registry = new ImageRegistry();
	
	private Map<ImageDescriptor, String> cache = new HashMap<ImageDescriptor, String>();
	
	public ImageDescriptor getDescriptor(String path){
		ImageDescriptor id = registry.getDescriptor(path);
		if (id != null) return id;
		URL url = bundle.getEntry(path);
		id = ImageDescriptor.createFromURL(url);
		registry.put(path, id);
		cache.put(id, path);
		return id;
	}
	
	public Image getImage(String path){
		return getImage(getDescriptor(path));
	}
	
	public Image getImage(ImageDescriptor descriptor){
		String id = cache.get(descriptor);
		return registry.get(id);
	}
	
	private final Bundle bundle;
	
	private static BundleImages instance = null;
	
	protected BundleImages(Bundle bundle) {
		if (instance == null) instance = this;
		this.bundle = bundle;
	} 
	
	public static BundleImages getInstance(){
		return instance;
	}
	
	public void dispose(){
		
	}
	
}
