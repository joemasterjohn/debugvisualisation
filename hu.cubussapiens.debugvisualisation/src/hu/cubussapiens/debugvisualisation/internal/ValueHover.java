/**
 * 
 */
package hu.cubussapiens.debugvisualisation.internal;

import hu.cubussapiens.debugvisualisation.viewmodel.IDVValue;

import org.eclipse.debug.core.model.IValue;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.IDebugModelPresentation;
import org.eclipse.debug.ui.IValueDetailListener;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class ValueHover extends Panel {

	private static final FontData FONT_DATA = Display.getDefault()
			.getSystemFont().getFontData()[0];

	private static final Font CAPTION_FONT = new Font(Display.getDefault(),
			FONT_DATA.getName(), FONT_DATA.getHeight(), SWT.BOLD);

	private static final Font VALUE_FONT = new Font(Display.getDefault(),
			FONT_DATA);

	private IDebugModelPresentation modelPresentation;

	public ValueHover(IDVValue value) {
		super();

		setLayoutManager(new ToolbarLayout(false));
		setBorder(new MarginBorder(2));

		Label caption = new Label("Value:");
		caption.setFont(CAPTION_FONT);
		add(caption);

		final Label valueLabel = new Label("computing...");
		valueLabel.setFont(VALUE_FONT);
		add(valueLabel);

		modelPresentation = DebugUITools.newDebugModelPresentation();
		modelPresentation.computeDetail(value.getRelatedValue(),
				new IValueDetailListener() {
					public void detailComputed(IValue value, String result) {
						valueLabel.setText(result);
					}
				});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.draw2d.Figure#erase()
	 */
	@Override
	public void erase() {
		modelPresentation.dispose();
		super.erase();
	}

}
