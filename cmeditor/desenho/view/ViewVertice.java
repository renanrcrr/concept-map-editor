package cmeditor.desenho.view;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.VertexRenderer;
import org.jgraph.graph.VertexView;

public class ViewVertice extends VertexView {
	
	public static transient ActivityRenderer renderer = new ActivityRenderer();

	public ViewVertice() {
		super();
	}

	public ViewVertice(Object cell) {
		super(cell);
	}
	

	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	public static class ActivityRenderer extends VertexRenderer {
		
		public void paint(Graphics g) {
			int b = borderWidth;
			Dimension d = getSize();
			Graphics2D g2 = (Graphics2D) g;
			boolean tmp = selected;
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				if (gradientColor != null && !preview) {
					setOpaque(false);
					g2.setPaint(new GradientPaint(0, 0, getBackground(),
							getWidth(), getHeight(), gradientColor, true));
				}
				g.fillRect(b / 2, b / 2, d.width - (int)(b * 1.5),
							d.height - (int)(b * 1.5));
			}
			try {
				setBorder(null);
				setOpaque(false);
				selected = false;
				super.paint(g);
			} finally {
				selected = tmp;
			}
			if (bordercolor != null) {
				g.setColor(bordercolor);
				g2.setStroke(new BasicStroke(borderWidth));
				g.drawRect(b / 2, b / 2, d.width - (int)(b * 1.5),
						d.height - (int)(b * 1.5));
			}
			if (selected) {
				g2.setStroke(GraphConstants.SELECTION_STROKE);
				g.setColor(highlightColor);
				g.drawRect(b / 2, b / 2, d.width - (int)(b * 1.5),
						d.height - (int)(b * 1.5));
			}
		}
	}

}