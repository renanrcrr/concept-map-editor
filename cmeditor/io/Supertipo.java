package cmeditor.io;
import java.util.Vector;

public class Supertipo {
	
	public String nome;
	public String descricao;
	public Vector frases;
	
	public Supertipo(String nome,
			String descricao) {
		this.nome = nome;
		this.descricao = descricao;
	}
	
	public String toString() {
		return this.nome;
	}
	
}
