package cmeditor.desenho.elemento;

import org.jgraph.graph.GraphConstants;

import cmeditor.desenho.AreaDesenho;

public class Conceito extends Vertice {
	
	SetaContinua setaContinua;
	SetaTracejada setaTracejada;
	
	public Conceito(String texto, double x, double y) {
		super(texto, x, y);
		GraphConstants.setAutoSize(getAttributes(),
				true);
		GraphConstants.setOpaque(getAttributes(),
				true);
		GraphConstants.setBorderColor(getAttributes(),
				AreaDesenho.corBorda);
		GraphConstants.setBackground(getAttributes(),
				AreaDesenho.corPreenchimento);
		setaContinua = new SetaContinua();
		this.add(setaContinua);
		setaTracejada = new SetaTracejada();
		this.add(setaTracejada);
	}
	
	public SetaContinua pegaSetaContinua() {
		return setaContinua;
	}
	
	public SetaTracejada pegaSetaTracejada() {
		return setaTracejada;
	}
	
}
