package cmeditor.desenho.elemento;

import java.awt.Point;

import org.jgraph.graph.GraphConstants;

public class SetaContinua extends Seta {
	
	public SetaContinua() {
		super();
		GraphConstants.setOffset(getAttributes(),
				new Point(0, GraphConstants.PERMILLE/2));
	}
	
}
