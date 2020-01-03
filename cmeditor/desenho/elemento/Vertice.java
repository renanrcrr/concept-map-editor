package cmeditor.desenho.elemento;

import java.awt.Color;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.GraphConstants;

import cmeditor.desenho.AreaDesenho;

public class Vertice extends DefaultGraphCell {
	
	DefaultPort portPadrao;
	Font fonte = AreaDesenho.fonte;
	Color fonteCor = AreaDesenho.corLetra;

	public Vertice(String texto, double x, double y) {
		super(texto);
		GraphConstants.setBounds(getAttributes(), new Rectangle2D.Double(x, y, 0, 0));
		portPadrao = new DefaultPort();
		this.add(portPadrao);
	}
	
	public DefaultPort pegaPortPadrao() {
		return portPadrao;
	}
	
	public Font pegaFonte() {
		return fonte;
	}
	
	public Color pegaFonteCor() {
		return fonteCor;
	}
	
	public void mudaFonte(Font fonte) {
		this.fonte = fonte;
	}
	
	public void mudaFonteCor(Color fonteCor) {
		this.fonteCor = fonteCor;
	}
	
	public void mudaFonte(String fonteNome,
				int fonteEstilo,
				int fonteTamanho,
				Color fonteCor) {
		this.fonte = new Font(fonteNome,
				fonteEstilo,
				fonteTamanho);
		this.fonteCor = fonteCor;
	}

}
