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

public class ViewConceito extends VertexView {
	
	public static transient ActivityRenderer renderer = new ActivityRenderer();

	public ViewConceito() {
		super();
	}

	public ViewConceito(Object cell) {
		super(cell);
	}
	
	public static int getArcSize(int width, int height) {
		int arcSize;


		if (width <= height) {
			arcSize = height / 5;
			if (arcSize > (width / 2)) {
				arcSize = width / 2;
			}
		} else {
			arcSize = width / 5;
			if (arcSize > (height / 2)) {
				arcSize = height / 2;
			}
		}

		return arcSize;
	}

	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	public static class ActivityRenderer extends VertexRenderer {

		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			d.width += d.height / 5;
			return d;
		}

		public void paint(Graphics g) {
			int b = borderWidth;
			Graphics2D g2 = (Graphics2D) g;
			Dimension d = getSize();
			boolean tmp = selected;
			int roundRectArc = ViewConceito.getArcSize(d.width - b,
					d.height - b);
			if (super.isOpaque()) {
				g.setColor(super.getBackground());
				if (gradientColor != null && !preview) {
					setOpaque(false);
					g2.setPaint(new GradientPaint(0, 0, getBackground(),
							getWidth(), getHeight(), gradientColor, true));
				}
				g.fillRoundRect(b / 2, b / 2, d.width - (int) (b * 1.5),
						d.height - (int) (b * 1.5), roundRectArc, roundRectArc);
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
				g2.setStroke(new BasicStroke(b));
				g.drawRoundRect(b / 2, b / 2, d.width - (int) (b * 1.5),
						d.height - (int) (b * 1.5), roundRectArc, roundRectArc);
			}
			if (selected) {
				g2.setStroke(GraphConstants.SELECTION_STROKE);
				g.setColor(highlightColor);
				g.drawRoundRect(b / 2, b / 2, d.width - (int) (b * 1.5),
						d.height - (int) (b * 1.5), roundRectArc, roundRectArc);
			}
		}
	}

}