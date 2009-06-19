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
 * This class is used to handle icons bundled within the plugin. Icons are cached lazily, and
 * disposed on platform shutdown. 
 * 
 * A singleton instance can be retrieved globally with BundleImages.getInstance(). 
 * 
 * To retrieve an image, use the getImage(String) method, or the getDescriptor() method, which
 * returns an ImageDescriptor usable with actions. Do NOT dispose images got by getImage(). 
 * Available image IDs are accepted via public static fields:
 * 
 * 
 */
public class BundleImages{

	public static final String icon_refresh = "/icons/view-refresh.gif";
	
	public static final String icon_node_open = "/icons/node-open.gif";
	
	public static final String icon_node_closed = "/icons/node-closed.gif";
	
	private ImageRegistry registry = new ImageRegistry();
	
	private Map<ImageDescriptor, String> cache = new HashMap<ImageDescriptor, String>();
	
	/**
	 * Get an image descriptor, which can be used with a JFace action. Note that if you
	 * retrieve the image by createImage() method via the result of this method, that image
	 * won't be under the BundleImages' control, therefore you must dispose it manually.
	 * 
	 * If you want to retrieve an image from the ImageDescriptor which is handled by 
	 * BundleImages, use the getImage(ImageDescriptor) method of the BundleImages instance.
	 * @param path
	 * @return ImageDescriptor for the asked image
	 */
	public ImageDescriptor getDescriptor(String path){
		ImageDescriptor id = registry.getDescriptor(path);
		if (id != null) return id;
		URL url = bundle.getEntry(path);
		id = ImageDescriptor.createFromURL(url);
		registry.put(path, id);
		cache.put(id, path);
		return id;
	}
	
	/**
	 * Get an image. This image is cached within this object. Never dispose images returned by
	 * this method manually.
	 * @param path
	 * @return the Image identified by the given path
	 */
	public Image getImage(String path){
		return getImage(getDescriptor(path));
	}
	
	/**
	 * Retrieve an image from an ImageDescriptor. This method returns a cached image, which is
	 * handled by the BundleImages instance. Never dispose images returned by this method
	 * manually. 
	 * 
	 * Note that only descriptors created by the BundleImages instance is usable with
	 * this method.
	 * 
	 * @param descriptor
	 * @return Cached image identified by the given descriptor
	 */
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
	
	/**
	 * Get singleton instance of BundleImages
	 * @return instance of BundleImages
	 */
	public static BundleImages getInstance(){
		return instance;
	}
	
	/**
	 * Dispose the BundleImages objects and all cached images.
	 */
	public void dispose(){
		registry.dispose();
	}
	
}
