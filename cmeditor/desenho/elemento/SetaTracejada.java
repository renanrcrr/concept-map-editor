package cmeditor.desenho.elemento;

import java.awt.Point;

import org.jgraph.graph.GraphConstants;

public class SetaTracejada extends Seta {
	
	public SetaTracejada() {
		super();
		GraphConstants.setOffset(getAttributes(), new Point(
				GraphConstants.PERMILLE, GraphConstants.PERMILLE/2));
	}
	
}
