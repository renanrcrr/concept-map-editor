package cmeditor.desenho.metodo;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.FraseLivre;
import cmeditor.desenho.elemento.Legenda;
import cmeditor.gui.dialogo.Dialogos;
import cmeditor.io.Mapa;

public class InsercaoElementos {
	
	public static Object inserirConceitoExemploLegenda(AreaDesenho area,
			double x,
			double y) {
		if (area.pegaTipoMapa() == Mapa.CONTEXTUALIZADO) {
			Object obj = Dialogos.mostraDialogoInsercaoContextualizado(area, true);
			if (obj instanceof String) {
				Conceito con = new Conceito(
						TratamentoStrings.converteStringParaJGraph((String)obj,
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(con);
				area.tiraConceitoOntologia((String)obj);
				return con;
			}
			else if (obj instanceof Integer &&
					((Integer)obj).intValue() == 1) {
				Exemplo exe = new Exemplo(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(exe);
				return exe;
			}
			else if (obj instanceof Integer &&
					((Integer)obj).intValue() == 2) {
				Legenda leg = new Legenda(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(leg);
				return leg;
			}
		}
		else switch (Dialogos.mostraDialogoInsercaoElemento(area, true)) {
			case 0: {
				Conceito con = new Conceito(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(con);
				return con;
			}
			case 1: {
				Exemplo exe = new Exemplo(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(exe);
				return exe;
			}
			case 2:  {
				Legenda leg = new Legenda(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(leg);
				return leg;
			}
		}
		return null;
	}
	
	public static Object inserirConceitoExemplo(AreaDesenho area,
			double x,
			double y) {
		if (area.pegaTipoMapa() == Mapa.CONTEXTUALIZADO) {
			Object obj = Dialogos.mostraDialogoInsercaoContextualizado(area, false);
			if (obj instanceof String) {
				Conceito con = new Conceito(
						TratamentoStrings.converteStringParaJGraph((String)obj,
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(con);
				area.tiraConceitoOntologia((String)obj);
				return con;
			}
			else if (obj instanceof Integer &&
					((Integer)obj).intValue() == 1) {
				Exemplo exe = new Exemplo(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(exe);
				return exe;
			}
		}
		else switch (Dialogos.mostraDialogoInsercaoElemento(area, false)) {
			case 0: {
				Conceito con = new Conceito(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(con);
				return con;
			}
			case 1: {
				Exemplo exe = new Exemplo(
						TratamentoStrings.converteStringParaJGraph("????",
								AreaDesenho.fonte.getName(),
								AreaDesenho.fonte.getStyle(),
								AreaDesenho.fonte.getSize(),
								AreaDesenho.corLetra),
						x, y);
				area.getGraphLayoutCache().insert(exe);
				return exe;
			}
		}
		return null;
	}
	
	public static Object inserirFraseEnlace(AreaDesenho area,
			int tipoSeta,
			double x,
			double y) {
		if (area.pegaTipoMapa() == Mapa.LIVRE_CONTEXTO) {
			FraseLivre fli = new FraseLivre(
					TratamentoStrings.converteStringParaJGraph("????",
							AreaDesenho.fonte.getName(),
							AreaDesenho.fonte.getStyle(),
							AreaDesenho.fonte.getSize(),
							AreaDesenho.corLetra),
					tipoSeta, x, y);
			area.getGraphLayoutCache().insert(fli);
			return fli;
		}
		else {
			FraseContextualizada fco = new FraseContextualizada(
					TratamentoStrings.converteStringParaJGraph("????",
							AreaDesenho.fonte.getName(),
							AreaDesenho.fonte.getStyle(),
							AreaDesenho.fonte.getSize(),
							AreaDesenho.corLetra),
					"", tipoSeta, x, y);
			area.getGraphLayoutCache().insert(fco);
			return fco;
		}
	}
	
}
