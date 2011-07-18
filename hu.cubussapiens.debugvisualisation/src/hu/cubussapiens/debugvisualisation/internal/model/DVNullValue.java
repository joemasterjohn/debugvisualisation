package hu.cubussapiens.debugvisualisation.internal.model;

import hu.cubussapiens.debugvisualisation.internal.step.IRootedGraphContentProvider;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVNullValue;
import hu.cubussapiens.debugvisualisation.viewmodel.IDVVariable;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.core.model.IVariable;

/**
 * Default implementation for the IDVNullValue interface.
 */
public class DVNullValue extends DVValueImpl implements IDVNullValue {

	public DVNullValue(IValue value, IRootedGraphContentProvider graph,
			IDVVariable parent, IVariable container) {
		super(value, graph, parent, container);
		// TODO Auto-generated constructor stub
	}

	public DVNullValue(IValue value, IRootedGraphContentProvider graph,
			IDVVariable parent) {
		super(value, graph, parent);
		// TODO Auto-generated constructor stub
	}

	public DVNullValue(IValue value, IRootedGraphContentProvider graph,
			IVariable container) {
		super(value, graph, container);
		// TODO Auto-generated constructor stub
	}

}
