package cmeditor.desenho.view;

import java.awt.Graphics;
import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.PortRenderer;
import org.jgraph.graph.PortView;

public class ViewPortPadrao extends PortView {
	
	Renderer renderer;
	
	public ViewPortPadrao(Object cell) {
		super(cell);
		renderer = new Renderer();
	}
	
	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	class Renderer extends PortRenderer implements CellViewRenderer {
		
		public Renderer() {
			super();
		}
		
		public void paint(Graphics g) {

		}
	}
	
}
