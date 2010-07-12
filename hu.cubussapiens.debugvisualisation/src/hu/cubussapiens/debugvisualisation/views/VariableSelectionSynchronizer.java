/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;
import hu.cubussapiens.debugvisualisation.viewmodel.util.AbstractKey;
import hu.cubussapiens.debugvisualisation.viewmodel.util.ViewModelFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.internal.ui.views.variables.VariablesView;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreePath;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.services.IDisposable;
import org.eclipse.zest.core.viewers.GraphViewer;

/**
 * Synchronizes (and converts) the selection between two views: the Debug
 * Visualisation view and the Variables View.
 */
public class VariableSelectionSynchronizer implements IDisposable {

	String localID;
	String remoteID;
	IWorkbenchPartSite localSite, remoteSite;

	private final String key = "treepathproperty";
	AbstractKey<TreePath> treePathProperty = new AbstractKey<TreePath>(key);

	private ISelectionListener remoteListener = new ISelectionListener() {

		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			convertSelectionToLocal(selection);
		}
	};

	private ISelectionListener localListener = new ISelectionListener() {

		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			convertSelectionToRemote(selection);
		}

	};
	ISelection oldRemoteSelection = null;
	boolean remoteLock = false, localLock = false;

	private class PartListener implements IPartListener2 {

		String id;

		public void setViewID(String id) {
			this.id = id;
		}

		public void partVisible(IWorkbenchPartReference partRef) {
		}

		public void partOpened(IWorkbenchPartReference partRef) {
			if (partRef.getId().contentEquals(id)) {
				setRemoteSite(partRef.getPart(false).getSite());
			}
		}

		public void partInputChanged(IWorkbenchPartReference partRef) {
		}

		public void partHidden(IWorkbenchPartReference partRef) {
		}

		public void partDeactivated(IWorkbenchPartReference partRef) {
		}

		public void partClosed(IWorkbenchPartReference partRef) {
		}

		public void partBroughtToTop(IWorkbenchPartReference partRef) {
		}

		public void partActivated(IWorkbenchPartReference partRef) {
		}
	};

	private void setRemoteSite(IWorkbenchPartSite remoteSite) {
		this.remoteSite = remoteSite;
		remoteSite.getPage().addSelectionListener(remoteID, remoteListener);
	}

	private PartListener partListener;

	/**
	 * Converts the local selection to a remote one and loads it in the remote
	 * view.
	 * 
	 * @param selection
	 *            the selection to convert
	 */
	public void convertSelectionToRemote(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return;
		if (remoteLock || localLock)
			return;
		if (remoteSite == null) {
			oldLocalSelection = selection;
			return;
		}
		try {
			remoteLock = true;
			Iterator<?> iterator = ((IStructuredSelection) selection)
					.iterator();
			IStructuredSelection remoteSelection;
			if (selection.isEmpty()) {
				remoteSelection = (IStructuredSelection) selection;
			} else {
				List<TreePath> treePaths = new ArrayList<TreePath>();
				while (iterator.hasNext()) {
					Object _val = iterator.next();
					if (_val instanceof IDVValue) {
						IDVValue val = (IDVValue) _val;
						if (val.getParent() != null) {
							treePaths.add(val.getParent().getProperty(
									treePathProperty));
						} else if (val.getContainer() != null) {
							treePaths.add(new TreePath(new Object[] { val
									.getContainer() }));
						}
					} else if (_val instanceof IDVVariable) {

						treePaths.add(((IDVVariable) _val)
								.getProperty(treePathProperty));
					}
				}
				remoteSelection = new TreeSelection(
						treePaths.toArray(new TreePath[treePaths.size()]));
			}
			if (oldRemoteSelection == null
					|| !oldRemoteSelection.equals(remoteSelection)) {
				// without activating the remote site the variables view
				// selection cannot be set
				// remoteSite.getPage().activate(remoteSite.getPart());
				// TODO find a way that does not expose the VariablesView
				// internal class
				VariablesView remoteView = (VariablesView) remoteSite.getPart();
				remoteView.getViewer().setSelection(remoteSelection);
				oldRemoteSelection = remoteView.getViewer().getSelection();
				/*
				 * ISelectionProvider remoteProvider = remoteSite
				 * .getSelectionProvider();
				 * remoteProvider.setSelection(remoteSelection);
				 * oldRemoteSelection = remoteProvider.getSelection();
				 */
				// localSite.getPage().activate(localSite.getPart());
			}
		} finally {
			remoteLock = false;
		}
	}

	ISelection oldLocalSelection = null;

	/**
	 * Converts the remote selection to local and sets it.
	 * 
	 * @param selection
	 *            the selection to convert
	 */
	public void convertSelectionToLocal(ISelection selection) {
		if (!(selection instanceof IStructuredSelection))
			return;
		if (remoteLock || localLock)
			return;
		try {
			GraphViewer graphViewer = (GraphViewer) localSite
					.getSelectionProvider();
			Object input = graphViewer.getInput();
			if (input == null)
				return;
			ViewModelFactory factory = ((StackFrameContextInput) input)
					.getFactory();
			localLock = true;
			ArrayList<IDVValue> variables = new ArrayList<IDVValue>();
			Iterator<?> iterator = ((IStructuredSelection) selection)
					.iterator();
			while (iterator.hasNext()) {
				Object _var = iterator.next();
				if (_var instanceof IVariable) {
					IVariable var = (IVariable) _var;
					variables.add(factory.getValue(var.getValue()));
				}
			}
			IStructuredSelection localSelection = new StructuredSelection(
					variables);
			if (oldLocalSelection == null
					|| !oldLocalSelection.equals(localSelection)) {
				graphViewer.setSelection(localSelection);
				graphViewer.refresh();
				oldLocalSelection = graphViewer.getSelection();
			}
		} catch (DebugException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			localLock = false;
		}

	}

	/**
	 * Initializes the variable selection synchronizer between the Debug
	 * Visualisation View and the Variables View.
	 * 
	 * @param site
	 *            the workbench site managing the views
	 */
	public VariableSelectionSynchronizer(IWorkbenchPartSite site) {
		localID = "hu.cubussapiens.debugvisualisation.views.DebugVisualisationView";
		remoteID = IDebugUIConstants.ID_VARIABLE_VIEW;
		IWorkbenchPage activePage = site.getPage();
		localSite = site;
		localSite.getPage().addSelectionListener(localListener);
		IViewPart remoteView = activePage.findView(remoteID);
		if (remoteView != null) {
			setRemoteSite(remoteView.getSite());
		} else {
			partListener = new PartListener();
			partListener.setViewID(remoteID);
			site.getWorkbenchWindow().getPartService()
					.addPartListener(partListener);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.services.IDisposable#dispose()
	 */
	public void dispose() {
		localSite.getPage().removeSelectionListener(localID, localListener);
		if (remoteSite != null) {
			remoteSite.getPage().removeSelectionListener(remoteID,
					remoteListener);
		}
		if (partListener != null)
			localSite.getWorkbenchWindow().getPartService()
					.removePartListener(partListener);
	}

}
