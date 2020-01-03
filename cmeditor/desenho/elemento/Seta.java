package cmeditor.desenho.elemento;

import org.jgraph.graph.DefaultPort;

public class Seta extends DefaultPort {
	
	boolean visivel;
	
	public Seta() {
		super();
		visivel = false;
	}
	
	public boolean testaVisivel() {
		return visivel;
	}
	
	public void mudaVisivel(boolean b) {
		visivel = b;
	}
	
}
