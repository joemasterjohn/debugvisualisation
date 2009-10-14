/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views;

import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.services.IDisposable;

/**
 *
 */
public class VariableSelectionSynchronizer implements IDisposable {

	String localID;
	String remoteID;
	IWorkbenchPartSite localSite, remoteSite;

	private ISelectionListener remoteListener = new ISelectionListener() {

		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			System.out.println("Variables view: " + selection.toString());
			convertSelectionToLocal(selection);

		}
	};
	private ISelectionListener localListener = new ISelectionListener() {

		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			System.out.println("DV View: " + selection.toString());
			convertSelectionToRemote(selection);

		}

	};
	ISelection oldRemoteSelection = null;
	boolean remoteLock = false;

	public void convertSelectionToRemote(ISelection selection) {
		if (remoteLock)
			return;
		remoteLock = true;
		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (oldRemoteSelection == null || !oldRemoteSelection.equals(selection)) {
			IWorkbenchPart activePart = activePage.getActivePart();
			activePage.activate(activePage.findView(remoteID));
			ISelectionProvider remoteProvider = remoteSite
					.getSelectionProvider();
			remoteProvider.setSelection(selection);
			oldRemoteSelection = selection;
			activePage.activate(activePart);
		}
		remoteLock = false;
	}

	ISelection oldLocalSelection = null;

	public void convertSelectionToLocal(ISelection selection) {

		IWorkbenchPage activePage = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		if (oldLocalSelection != null && !oldRemoteSelection.equals(selection)) {
			IWorkbenchPart activePart = activePage.getActivePart();
			activePage.activate(activePage.findView(localID));
			localSite.getSelectionProvider().setSelection(selection);
			oldLocalSelection = selection;
			activePage.activate(activePart);
		}

	}

	public VariableSelectionSynchronizer(IWorkbenchPartSite site) {
		localID = "hu.cubussapiens.debugvisualisation.views.DebugVisualisationView";
		remoteID = IDebugUIConstants.ID_VARIABLE_VIEW;
		IWorkbenchPage activePage = site.getPage();
		localSite = site;
		localSite.getPage().addSelectionListener(localID, localListener);
		remoteSite = activePage.findView(remoteID).getSite();
		remoteSite.getPage().addPostSelectionListener(remoteID, remoteListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 */
	public void dispose() {
		localSite.getPage().removeSelectionListener(localID, localListener);
		remoteSite.getPage().removeSelectionListener(remoteID, remoteListener);

	}

}
