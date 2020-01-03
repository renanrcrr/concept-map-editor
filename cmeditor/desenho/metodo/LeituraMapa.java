package cmeditor.desenho.metodo;

import java.util.Vector;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.DefaultPort;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.ArcoContinuo;
import cmeditor.desenho.elemento.ArcoTracejado;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseEnlace;
import cmeditor.desenho.elemento.Vertice;
import cmeditor.desenho.metodo.TratamentoStrings;

public class LeituraMapa {
	
	public static String pegaDiferenciacoesProgressivas(AreaDesenho area,
			Conceito conceitoRaiz) {
		String retorno = "";
		Vector conceitosNivelAtual = new Vector();
		Vector conceitosProximoNivel = new Vector();
		boolean restamNiveis = true;
		boolean proposicaoValida;
		conceitosNivelAtual.add(conceitoRaiz);
		while (restamNiveis) {
			for (int i = 0; i < conceitosNivelAtual.size(); i++) {
				Object[] arcosConceito = DefaultGraphModel.getOutgoingEdges(area.getModel(),
						((Conceito)conceitosNivelAtual.get(i)).pegaPortPadrao());
				for (int j = 0; j < arcosConceito.length; j++) {
					if (arcosConceito[j] instanceof ArcoContinuo)
						proposicaoValida = true;
					else
						proposicaoValida = false;
					DefaultPort portAlvo = (DefaultPort)((DefaultEdge)arcosConceito[j]).getTarget();
					if (portAlvo.getParent() instanceof FraseEnlace) {
						FraseEnlace frase = (FraseEnlace)portAlvo.getParent();
						Object[] arcosFrase = DefaultGraphModel.getOutgoingEdges(area.getModel(),
								frase.pegaPortPadrao());
						for (int k = 0; k < arcosFrase.length; k++) {
							portAlvo = (DefaultPort)((DefaultEdge)arcosFrase[k]).getTarget();
							if (portAlvo.getParent() instanceof Conceito) {
								Conceito conceitoAlvo = (Conceito)portAlvo.getParent();
								if (proposicaoValida)
									retorno += "-> " + TratamentoStrings.converteStringParaJava(
													(String)((Conceito)conceitosNivelAtual.get(i)).getUserObject()).replace('\n', ' ') + " " +
											TratamentoStrings.converteStringParaJava(
													(String)frase.getUserObject()).replace('\n', ' ') + " " +
											TratamentoStrings.converteStringParaJava(
													(String)conceitoAlvo.getUserObject()).replace('\n', ' ') + "\r\n";
								conceitosProximoNivel.add(conceitoAlvo);
							}
						}
					}
				}
			}
			if (conceitosProximoNivel.size() > 0) {
				conceitosNivelAtual.removeAllElements();
				for (int i = 0; i < conceitosProximoNivel.size(); i++)
					conceitosNivelAtual.add(conceitosProximoNivel.get(i));
				conceitosProximoNivel.removeAllElements();
			}
			else
				restamNiveis = false;
		}
		return retorno;
	}
	
	public static String pegaReconciliacoesIntegrativas(AreaDesenho area,
			Conceito conceitoRaiz) {
		String retorno = "";
		Vector conceitosNivelAtual = new Vector();
		Vector conceitosProximoNivel = new Vector();
		boolean restamNiveis = true;
		boolean proposicaoValida;
		conceitosNivelAtual.add(conceitoRaiz);
		while (restamNiveis) {
			for (int i = 0; i < conceitosNivelAtual.size(); i++) {
				Object[] arcosConceito = DefaultGraphModel.getOutgoingEdges(area.getModel(),
						((Conceito)conceitosNivelAtual.get(i)).pegaPortPadrao());
				for (int j = 0; j < arcosConceito.length; j++) {
					if (arcosConceito[j] instanceof ArcoTracejado)
						proposicaoValida = true;
					else
						proposicaoValida = false;
					DefaultPort portAlvo = (DefaultPort)((DefaultEdge)arcosConceito[j]).getTarget();
					if (portAlvo.getParent() instanceof FraseEnlace) {
						FraseEnlace frase = (FraseEnlace)portAlvo.getParent();
						Object[] arcosFrase = DefaultGraphModel.getOutgoingEdges(area.getModel(),
								frase.pegaPortPadrao());
						for (int k = 0; k < arcosFrase.length; k++) {
							portAlvo = (DefaultPort)((DefaultEdge)arcosFrase[k]).getTarget();
							if (portAlvo.getParent() instanceof Conceito) {
								Conceito conceitoAlvo = (Conceito)portAlvo.getParent();
								if (proposicaoValida)
									retorno +=  "-> " + TratamentoStrings.converteStringParaJava(
													(String)((Conceito)conceitosNivelAtual.get(i)).getUserObject()).replace('\n', ' ') + " " +
											TratamentoStrings.converteStringParaJava(
													(String)frase.getUserObject()).replace('\n', ' ') + " " +
											TratamentoStrings.converteStringParaJava(
													(String)conceitoAlvo.getUserObject()).replace('\n', ' ') + "\r\n";
								conceitosProximoNivel.add(conceitoAlvo);
							}
						}
					}
				}
			}
			if (conceitosProximoNivel.size() > 0) {
				conceitosNivelAtual.removeAllElements();
				for (int i = 0; i < conceitosProximoNivel.size(); i++)
					conceitosNivelAtual.add(conceitosProximoNivel.get(i));
				conceitosProximoNivel.removeAllElements();
			}
			else
				restamNiveis = false;
		}
		return retorno;
	}
	
	public static String pegaNiveisInclusao(AreaDesenho area,
			Conceito conceitoRaiz) {
		String retorno = "N\u00cdVEL 1:\r\n-> " +
				TratamentoStrings.converteStringParaJava(
						(String)conceitoRaiz.getUserObject()) + "\r\n\r\n";
		Vector conceitosNivelAtual = new Vector();
		Vector conceitosProximoNivel = new Vector();
		boolean restamNiveis = true;
		int contNivel = 2;
		boolean proposicaoValida;
		conceitosNivelAtual.add(conceitoRaiz);
		while (restamNiveis) {
			for (int i = 0; i < conceitosNivelAtual.size(); i++) {
				Object[] arcosConceito = DefaultGraphModel.getOutgoingEdges(area.getModel(),
						((Conceito)conceitosNivelAtual.get(i)).pegaPortPadrao());
				for (int j = 0; j < arcosConceito.length; j++) {
					if (arcosConceito[j] instanceof ArcoContinuo)
						proposicaoValida = true;
					else
						proposicaoValida = false;
					DefaultPort portAlvo = (DefaultPort)((DefaultEdge)arcosConceito[j]).getTarget();
					if (portAlvo.getParent() instanceof FraseEnlace) {
						FraseEnlace frase = (FraseEnlace)portAlvo.getParent();
						Object[] arcosFrase = DefaultGraphModel.getOutgoingEdges(area.getModel(),
								frase.pegaPortPadrao());
						for (int k = 0; k < arcosFrase.length; k++) {
							portAlvo = (DefaultPort)((DefaultEdge)arcosFrase[k]).getTarget();
							if (portAlvo.getParent() instanceof Conceito ||
									portAlvo.getParent() instanceof Exemplo) {
								if (k == 0 && proposicaoValida)
									retorno += "N\u00cdVEL " + contNivel + ":\r\n";
								Vertice verticeAlvo = (Vertice)portAlvo.getParent();
								if (proposicaoValida)
									retorno += "-> " + TratamentoStrings.converteStringParaJava(
											(String)verticeAlvo.getUserObject()).replace('\n', ' ') + "\r\n";
							}
							if (portAlvo.getParent() instanceof Conceito) {
								Conceito conceitoAlvo = (Conceito)portAlvo.getParent();
								conceitosProximoNivel.add(conceitoAlvo);
							}
						}
					}
				}
			}
			if (conceitosProximoNivel.size() > 0) {
				conceitosNivelAtual.removeAllElements();
				for (int i = 0; i < conceitosProximoNivel.size(); i++)
					conceitosNivelAtual.add(conceitosProximoNivel.get(i));
				conceitosProximoNivel.removeAllElements();
				retorno += "\r\n";
				contNivel++;
			}
			else
				restamNiveis = false;
		}
		return retorno;
	}
	
	public static String pegaExemplificacoes(AreaDesenho area,
			Conceito conceitoRaiz) {
		String retorno = "";
		Vector conceitosNivelAtual = new Vector();
		Vector conceitosProximoNivel = new Vector();
		boolean restamNiveis = true;
		conceitosNivelAtual.add(conceitoRaiz);
		while (restamNiveis) {
			for (int i = 0; i < conceitosNivelAtual.size(); i++) {
				Object[] arcosConceito = DefaultGraphModel.getOutgoingEdges(area.getModel(),
						((Conceito)conceitosNivelAtual.get(i)).pegaPortPadrao());
				for (int j = 0; j < arcosConceito.length; j++) {
					DefaultPort portAlvo = (DefaultPort)((DefaultEdge)arcosConceito[j]).getTarget();
					if (portAlvo.getParent() instanceof FraseEnlace) {
						FraseEnlace frase = (FraseEnlace)portAlvo.getParent();
						Object[] arcosFrase = DefaultGraphModel.getOutgoingEdges(area.getModel(),
								frase.pegaPortPadrao());
						for (int k = 0; k < arcosFrase.length; k++) {
							portAlvo = (DefaultPort)((DefaultEdge)arcosFrase[k]).getTarget();
							if (portAlvo.getParent() instanceof Exemplo) {
								Exemplo exemploAlvo = (Exemplo)portAlvo.getParent();
								retorno +=  "-> " + TratamentoStrings.converteStringParaJava(
												(String)((Conceito)conceitosNivelAtual.get(i)).getUserObject()).replace('\n', ' ') + " " +
										TratamentoStrings.converteStringParaJava(
												(String)frase.getUserObject()).replace('\n', ' ') + " " +
										TratamentoStrings.converteStringParaJava(
												(String)exemploAlvo.getUserObject()).replace('\n', ' ') + "\r\n";
							}
							else if (portAlvo.getParent() instanceof Conceito) {
								Conceito conceitoAlvo = (Conceito)portAlvo.getParent();
								conceitosProximoNivel.add(conceitoAlvo);
							}
						}
					}
				}
			}
			if (conceitosProximoNivel.size() > 0) {
				conceitosNivelAtual.removeAllElements();
				for (int i = 0; i < conceitosProximoNivel.size(); i++)
					conceitosNivelAtual.add(conceitosProximoNivel.get(i));
				conceitosProximoNivel.removeAllElements();
			}
			else
				restamNiveis = false;
		}
		return retorno;
	}
	
}
