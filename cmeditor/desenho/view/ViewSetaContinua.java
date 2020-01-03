package cmeditor.desenho.view;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.ImageIcon;

import org.jgraph.graph.CellViewRenderer;
import org.jgraph.graph.PortRenderer;
import org.jgraph.graph.PortView;

import cmeditor.desenho.elemento.Seta;

public class ViewSetaContinua extends PortView {
	
	Renderer renderer;
	
	public ViewSetaContinua(Object cell) {
		super(cell);
		renderer = new Renderer();
	}
	
	public Rectangle2D getBounds() {
		Point2D p = (Point2D)getLocation().clone();
		double lar = renderer.imagem.getIconWidth();
		double alt = renderer.imagem.getIconHeight();
		return new Rectangle2D.Double(
				p.getX() - lar / 1.35,
				p.getY() - alt / 2, lar, alt);
	}
	
	public CellViewRenderer getRenderer() {
		return renderer;
	}
	
	class Renderer extends PortRenderer implements CellViewRenderer {
		
		public ImageIcon imagem;
		
		public Renderer() {
			super();
			imagem = new ImageIcon(
					this.getClass().getClassLoader().getResource("cmeditor/imagens/Continuous22.gif"));
		}
		
		public void paint(Graphics g) {
			if (((Seta)cell).testaVisivel())
				g.drawImage(imagem.getImage(),
						0, 0, this);
		}
	}
	
}
