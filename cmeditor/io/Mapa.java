package cmeditor.io;

import java.util.Vector;

public class Mapa {
	
	public static int LIVRE_CONTEXTO = 0;
	public static int CONTEXTUALIZADO = 1;
	int tipo;
	Vector vertices;
	Vector arcos;
	Vector ligacoesArcos;
	Vector conceitosOntologia;
	
	public Mapa() {
		tipo = LIVRE_CONTEXTO;
		vertices = new Vector();
		arcos = new Vector();
		ligacoesArcos = new Vector();
		conceitosOntologia = new Vector();
	}
	
	public void inserirConceitoOntologia(String s) {
		conceitosOntologia.add(s);
	}
	
	public Vector pegarConceitosOntologia() {
		return conceitosOntologia;
	}
	
	public void mudarTipo(int i) {
		tipo = i;
	}
	
	public int pegarTipo() {
		return tipo;
	}
	
}
