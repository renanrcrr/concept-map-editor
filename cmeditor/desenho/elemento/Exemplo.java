package cmeditor.desenho.elemento;

import org.jgraph.graph.GraphConstants;

import cmeditor.desenho.AreaDesenho;

public class Exemplo extends Vertice {
	
	public Exemplo(String texto, double x, double y) {
		super(texto, x, y);
		GraphConstants.setAutoSize(getAttributes(),
				true);
		GraphConstants.setOpaque(getAttributes(),
				true);
		GraphConstants.setBorderColor(getAttributes(),
				AreaDesenho.corBorda);
		GraphConstants.setBackground(getAttributes(),
				AreaDesenho.corPreenchimento);
	}
	
}
