package cmeditor.desenho.elemento;

import org.jgraph.graph.GraphConstants;

public class FraseEnlace extends Vertice {
	
	public static final int SETA_CONTINUA = 0, SETA_TRACEJADA = 1;
	private SetaContinua setaContinua;
	private SetaTracejada setaTracejada;
	
	public FraseEnlace(String texto, int tipoSeta, double x, double y) {
		super(texto, x, y);
		GraphConstants.setAutoSize(getAttributes(),
				true);
		setaContinua = new SetaContinua();
		setaTracejada = new SetaTracejada();
		if (tipoSeta == SETA_CONTINUA)
			this.add(setaContinua);
		else
			this.add(setaTracejada);
	}
	
	public SetaContinua pegaSetaContinua() {
		return setaContinua;
	}
	
	public SetaTracejada pegaSetaTracejada() {
		return setaTracejada;
	}
	
	public void mudaContinua() {
		this.remove(setaTracejada);
		this.add(setaContinua);
	}
	
	public void mudaTracejada() {
		this.remove(setaContinua);
		this.add(setaTracejada);
	}
	
	public boolean testaContinua() {
		return this.isNodeChild(setaContinua);
	}
	
	public boolean testaTracejada() {
		return this.isNodeChild(setaTracejada);
	}
	
}
