/**
 * 
 */
package hu.cubussapiens.debugvisualisation.views;

import hu.cubussapiens.debugvisualisation.internal.input.StackFrameContextInput;
import hu.cubussapiens.debugvisualisation.internal.model.IDVValue;
import hu.cubussapiens.debugvisualisation.internal.model.IDVVariable;
import hu.cubussapiens.debugvisualisation.internal.model.ViewModelFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.debug.core.DebugException;
import org.eclipse.debug.core.model.IVariable;
import org.eclipse.debug.ui.IDebugUIConstants;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
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

	/**
	 * Converts the local selection to a remote one and loads it in the remote
	 * view.
	 * 
	 * @param selection
	 *            the selection to convert
	 */
	public void convertSelectionToRemote(ISelection selection) {
		if (remoteLock || localLock)
			return;
		try {
			remoteLock = true;
			GraphViewer gViewer = (GraphViewer) localSite
					.getSelectionProvider();
			Object[] arcs = gViewer.getConnectionElements();
			Iterator<?> iterator = ((IStructuredSelection) selection)
					.iterator();
			IStructuredSelection remoteSelection;
			if (selection.isEmpty()) {
				remoteSelection = (IStructuredSelection) selection;
			} else {
				List<IVariable> remoteElements = new ArrayList<IVariable>();
				while (iterator.hasNext()) {
					Object _val = iterator.next();
					if (_val instanceof IDVValue) {
						IDVValue val = (IDVValue) _val;
						if (val.getParent() != null) {
							remoteElements.add(val.getParent()
									.getRelatedVariable());
						} else if (val.getContainer() != null) {
							remoteElements.add(val.getContainer());
						}
						// TODO this search can be quite slow
						/*
						 * for (Object arc : arcs) { try { if (arc instanceof
						 * IDVVariable && ((IDVVariable) arc)
						 * .getRelatedVariable() .getValue().equals(_val)) {
						 * remoteElements.add(((IDVVariable) arc)
						 * .getRelatedVariable()); break;// only the first
						 * result will be // considered here } } catch
						 * (DebugException e) { // TODO Auto-generated catch
						 * block e.printStackTrace(); } }
						 */
					} else if (_val instanceof IDVVariable) {
						remoteElements.add(((IDVVariable) _val)
								.getRelatedVariable());
					}
				}
				remoteSelection = new StructuredSelection(remoteElements);
			}
			if (oldRemoteSelection == null
					|| !oldRemoteSelection.equals(remoteSelection)) {
				ISelectionProvider remoteProvider = remoteSite
						.getSelectionProvider();
				remoteProvider.setSelection(remoteSelection);
				oldRemoteSelection = remoteProvider.getSelection();

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
		if (remoteLock || localLock)
			return;
		try {
			GraphViewer graphViewer = (GraphViewer) localSite
					.getSelectionProvider();
			ViewModelFactory factory = ((StackFrameContextInput) graphViewer
					.getInput()).getFactory();
			localLock = true;
			// ArrayList<IValue> variables = new ArrayList<IValue>();
			ArrayList<IDVValue> variables = new ArrayList<IDVValue>();
			Iterator<?> iterator = ((IStructuredSelection) selection)
					.iterator();
			while (iterator.hasNext()) {
				Object _var = iterator.next();
				if (_var instanceof IVariable) {
					// variables.add(((IVariable) _var).getValue());
					IVariable var = (IVariable) _var;
					variables.add(factory.getValue(var.getValue()));
				}
			}
			IStructuredSelection localSelection = new StructuredSelection(
					variables);
			if (oldRemoteSelection == null
					|| !oldRemoteSelection.equals(selection)) {
				graphViewer.setSelection(localSelection);
				graphViewer.refresh();
				oldLocalSelection = selection;
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
		localSite.getPage().addSelectionListener(localID, localListener);
		IViewPart remoteView = activePage.findView(remoteID);
		remoteSite = remoteView.getSite();
		remoteSite.getPage().addSelectionListener(remoteID, remoteListener);
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
