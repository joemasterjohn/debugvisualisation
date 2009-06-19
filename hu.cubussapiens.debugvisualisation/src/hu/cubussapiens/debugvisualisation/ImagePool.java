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
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.Bundle;

/**
 * This class is used to handle icons bundled within the plugin. Icons are cached lazily, and
 * disposed on platform shutdown. 
 * 
 * A singleton instance can be retrieved globally with ImagePool.getInstance(). 
 * 
 * To retrieve an image, use the getImage(String) method, or the getDescriptor() method, which
 * returns an ImageDescriptor usable with actions. Do NOT dispose images got by getImage(). 
 * Available image IDs are accepted via public static fields:
 * 
 * 
 */
public class ImagePool{

	/**
	 * Refresh icon
	 */
	public static final String icon_refresh = "/icons/view-refresh.gif";
	
	/**
	 * Icon for an open node
	 */
	public static final String icon_node_open = "/icons/node-open.gif";
	
	/**
	 * Icon for a closed node
	 */
	public static final String icon_node_closed = "/icons/node-closed.gif";
	
	/**
	 * Icon for layout selection
	 */
	public static final String icon_select_layout = "/icons/layout.gif";
	
	private ImageRegistry registry = new ImageRegistry();
	
	private Map<ImageDescriptor, String> cache = new HashMap<ImageDescriptor, String>();
	
	/**
	 * Get an image descriptor, which can be used with a JFace action. Note that if you
	 * retrieve the image by createImage() method via the result of this method, that image
	 * won't be under the ImagePool's control, therefore you must dispose it manually.
	 * 
	 * If you want to retrieve an image from the ImageDescriptor which is handled by 
	 * ImagePool, use the getImage(ImageDescriptor) method of the ImagePool instance.
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
	 * Load shared image from PlatformUI
	 * @param imageid
	 * @return
	 */
	private Image getSharedImage(String imageid){
		return PlatformUI.getWorkbench().getSharedImages().getImage(imageid);
	}
	
	/**
	 * Get an image. This image is cached within this object. Never dispose images returned by
	 * this method manually.
	 * 
	 * This method can handle images which are shared in the platform, and identified by
	 * org.eclipse.ui.ISharedImages.
	 * 
	 * @param path
	 * @return the Image identified by the given path
	 */
	public Image getImage(String path){
		Image shared = getSharedImage(path);
		if (shared != null) return shared;
		
		return getImage(getDescriptor(path));
	}
	
	/**
	 * Retrieves an image from the image pool.
	 * Valid identifiers can 
	 * be found as public static fields in org.eclipse.ui.ISharedImages or 
	 * hu.cubussapiens.debugvisualisation.ImagePool.
	 * 
	 * This method is equivalent with calling ImagePool.getInstance().getImage(id);
	 *  
	 * @param id An identifier for an image. 
	 * @return The image identified by the given id.
	 */
	public static Image image(String id){
		return getInstance().getImage(id);
	}
	
	/**
	 * Retrieve an image from an ImageDescriptor. This method returns a cached image, which is
	 * handled by the ImagePool instance. Never dispose images returned by this method
	 * manually. 
	 * 
	 * Note that only descriptors created by the ImagePool instance is usable with
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
	
	private static ImagePool instance = null;
	
	protected ImagePool(Bundle bundle) {
		if (instance == null) instance = this;
		this.bundle = bundle;
	} 
	
	/**
	 * Get singleton instance of ImagePool
	 * @return instance of ImagePool
	 */
	public static ImagePool getInstance(){
		return instance;
	}
	
	/**
	 * Dispose the ImagePool objects and all cached images.
	 */
	public void dispose(){
		registry.dispose();
	}
	
}
