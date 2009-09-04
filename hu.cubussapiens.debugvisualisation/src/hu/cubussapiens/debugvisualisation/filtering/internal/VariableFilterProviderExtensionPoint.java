/**
 * 
 */
package hu.cubussapiens.debugvisualisation.filtering.internal;

import hu.cubussapiens.debugvisualisation.Activator;
import hu.cubussapiens.debugvisualisation.filtering.IVariableFilterProvider;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Platform;

/**
 * Singleton class to accept and handle extensions, which provides
 * IVariableFilterProvider implementations.
 */
public class VariableFilterProviderExtensionPoint {

	/**
	 * Extension point ID
	 */
	public static final String ID = "hu.cubussapiens.debugvisualisation.filterproviders";

	private final IVariableFilterProvider[] providers;

	private static final VariableFilterProviderExtensionPoint instance = new VariableFilterProviderExtensionPoint();

	/**
	 * Get all registered and valid IVariableFilterProviders
	 * 
	 * @return the registered and valid IVariableFilterProviders
	 */
	public static IVariableFilterProvider[] getProviders() {
		return instance.providers;
	}

	private VariableFilterProviderExtensionPoint() {
		IConfigurationElement[] config = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(ID);
		List<IVariableFilterProvider> ps = new ArrayList<IVariableFilterProvider>();
		for (IConfigurationElement c : config) {
			try {
				Object o = c.createExecutableExtension("class");
				if (o instanceof IVariableFilterProvider)
					ps.add((IVariableFilterProvider) o);
				else
					Activator
							.getDefault()
							.logError(
									null,
									"Invalid class value in extension: "
											+ c
													.getDeclaringExtension()
													.getExtensionPointUniqueIdentifier());
			} catch (CoreException e) {
				Activator.getDefault().logError(
						e,
						"Invalid extension: "
								+ c.getDeclaringExtension()
										.getExtensionPointUniqueIdentifier());
				e.printStackTrace();
			}
		}
		providers = ps.toArray(new IVariableFilterProvider[0]);
	}

}
