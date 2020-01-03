package cmeditor.desenho.elemento;

public class FraseContextualizada extends FraseEnlace {
	
	String supertipo;
	
	public FraseContextualizada(String texto,
			String supertipo,
			int tipoSeta,
			double x,
			double y) {
		super(texto, tipoSeta, x, y);
		this.supertipo = supertipo; 
	}
	
	public void mudaSupertipo(String s) {
		supertipo = s;
	}
	
	public String pegaSupertipo() {
		return supertipo;
	}
	
}
