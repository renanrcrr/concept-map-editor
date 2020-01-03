package cmeditor.desenho.elemento;

import org.jgraph.graph.GraphConstants;

public class Legenda extends Vertice {
	
	public Legenda(String texto, double x, double y) {
		super(texto, x, y);
		GraphConstants.setAutoSize(getAttributes(),
				true);
	}
	
}
