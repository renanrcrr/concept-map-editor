package cmeditor.desenho.metodo;

import java.util.Vector;

import org.jgraph.graph.GraphConstants;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.ArcoContinuo;
import cmeditor.desenho.elemento.ArcoTracejado;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.FraseLivre;
import cmeditor.desenho.elemento.Legenda;
import cmeditor.desenho.elemento.Vertice;

public class AreaTransferencia {
	
	private static Vector pegaDados(AreaDesenho area) {
		Vector dados = new Vector();
		Object[] selecionados = area.getSelectionCells();
		for (int i = 0; i < selecionados.length; i++) {
			if (selecionados[i] instanceof Conceito) {
				String s = (String)((Conceito)selecionados[i]).getUserObject();
				Conceito obj = new Conceito(s, 0, 0);

				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((Conceito)selecionados[i]).getAttributes()));

				GraphConstants.setBorderColor(obj.getAttributes(),
						GraphConstants.getBorderColor(
								((Conceito)selecionados[i]).getAttributes()));

				GraphConstants.setBackground(obj.getAttributes(),
						GraphConstants.getBackground(
								((Conceito)selecionados[i]).getAttributes()));

				obj.mudaFonte(((Conceito)selecionados[i]).pegaFonte());

				obj.mudaFonteCor(((Conceito)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else if (selecionados[i] instanceof Exemplo) {
				String s = (String)((Exemplo)selecionados[i]).getUserObject();
				Exemplo obj = new Exemplo(s, 0, 0);
				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((Exemplo)selecionados[i]).getAttributes()));

				GraphConstants.setBorderColor(obj.getAttributes(),
						GraphConstants.getBorderColor(
								((Exemplo)selecionados[i]).getAttributes()));

				GraphConstants.setBackground(obj.getAttributes(),
						GraphConstants.getBackground(
								((Exemplo)selecionados[i]).getAttributes()));
				obj.mudaFonte(((Exemplo)selecionados[i]).pegaFonte());
				obj.mudaFonteCor(((Exemplo)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else if (selecionados[i] instanceof FraseLivre) {
				String s = (String)((FraseLivre)selecionados[i]).getUserObject();
				int tipo = 0;
				if (((FraseLivre)selecionados[i]).testaContinua())
					tipo = FraseLivre.SETA_CONTINUA;
				else
					tipo = FraseLivre.SETA_TRACEJADA;
				FraseLivre obj = new FraseLivre(s, tipo, 0, 0);
				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((FraseLivre)selecionados[i]).getAttributes()));
				obj.mudaFonte(((FraseLivre)selecionados[i]).pegaFonte());
				obj.mudaFonteCor(((FraseLivre)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else if (selecionados[i] instanceof FraseContextualizada) {
				String s = (String)((FraseContextualizada)selecionados[i]).getUserObject();
				String sup = ((FraseContextualizada)selecionados[i]).pegaSupertipo();
				int tipo = 0;
				if (((FraseContextualizada)selecionados[i]).testaContinua())
					tipo = FraseContextualizada.SETA_CONTINUA;
				else
					tipo = FraseContextualizada.SETA_TRACEJADA;
				FraseContextualizada obj = new FraseContextualizada(s, sup, tipo, 0, 0);
				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((FraseContextualizada)selecionados[i]).getAttributes()));

				obj.mudaFonte(((FraseContextualizada)selecionados[i]).pegaFonte());

				obj.mudaFonteCor(((FraseContextualizada)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else if (selecionados[i] instanceof Legenda) {
				String s = (String)((Legenda)selecionados[i]).getUserObject();
				Legenda obj = new Legenda(s, 0, 0);
				GraphConstants.setBounds(obj.getAttributes(),
						GraphConstants.getBounds(
								((Legenda)selecionados[i]).getAttributes()));
				obj.mudaFonte(((Legenda)selecionados[i]).pegaFonte());
				obj.mudaFonteCor(((Legenda)selecionados[i]).pegaFonteCor());
				dados.add(obj);
			}
			else
				dados.add(null);
		}
		for (int i = 0; i < selecionados.length; i++) {
			if (selecionados[i] instanceof ArcoContinuo &&
					((ArcoContinuo)selecionados[i]).getSource() != null &&
					((ArcoContinuo)selecionados[i]).getTarget() != null) {

				int j = 1, origem = 0, alvo = 0;
				while (j < selecionados.length) {
					if (j == i || !(selecionados[j] instanceof Vertice))
						j++;
					else if (((ArcoContinuo)selecionados[i]).getSource()
							== ((Vertice)selecionados[j]).pegaPortPadrao()) {
						origem = j;
						j++;
					} else if (((ArcoContinuo)selecionados[i]).getTarget()
							== ((Vertice)selecionados[j]).pegaPortPadrao()) {
						alvo = j;
						j++;
					} else
						j++;
				}
				ArcoContinuo obj = new ArcoContinuo();

				GraphConstants.setLineColor(obj.getAttributes(),
						GraphConstants.getLineColor(
								((ArcoContinuo)selecionados[i]).getAttributes()));

				GraphConstants.setRouting(obj.getAttributes(),
						GraphConstants.getRouting(
								((ArcoContinuo)selecionados[i]).getAttributes()));
				GraphConstants.setLineStyle(obj.getAttributes(),
						GraphConstants.getLineStyle(
								((ArcoContinuo)selecionados[i]).getAttributes()));

				obj.setSource(((Vertice)dados.get(origem)).pegaPortPadrao());
				obj.setTarget(((Vertice)dados.get(alvo)).pegaPortPadrao());

				dados.set(i, obj);
			}
			else if (selecionados[i] instanceof ArcoTracejado &&
					((ArcoTracejado)selecionados[i]).getSource() != null &&
					((ArcoTracejado)selecionados[i]).getTarget() != null) {

				int j = 1, origem = 0, alvo = 0;
				while (j < selecionados.length) {
					if (j == i || !(selecionados[j] instanceof Vertice))
						j++;
					else if (((ArcoTracejado)selecionados[i]).getSource()
							== ((Vertice)selecionados[j]).pegaPortPadrao()) {
						origem = j;
						j++;
					} else if (((ArcoTracejado)selecionados[i]).getTarget()
							== ((Vertice)selecionados[j]).pegaPortPadrao()) {
						alvo = j;
						j++;
					} else
						j++;
				}
				ArcoTracejado obj = new ArcoTracejado();

				GraphConstants.setLineColor(obj.getAttributes(),
						GraphConstants.getLineColor(
								((ArcoTracejado)selecionados[i]).getAttributes()));

				GraphConstants.setRouting(obj.getAttributes(),
						GraphConstants.getRouting(
								((ArcoTracejado)selecionados[i]).getAttributes()));
				GraphConstants.setLineStyle(obj.getAttributes(),
						GraphConstants.getLineStyle(
								((ArcoTracejado)selecionados[i]).getAttributes()));

				obj.setSource(((Vertice)dados.get(origem)).pegaPortPadrao());
				obj.setTarget(((Vertice)dados.get(alvo)).pegaPortPadrao());

				dados.set(i, obj);
			}
		}
		return dados;
	}
	
	public static void recortar(AreaDesenho area) {
		copiar(area);
		EdicaoElementos.excluirSelecionados(area);
	}
	
	public static void copiar(AreaDesenho area) {
		area.mudaAreaTransferencia(pegaDados(area));
	}
	
	public static void colar(AreaDesenho area) {
		area.getGraphLayoutCache().insert(area.pegaAreaTransferencia().toArray());
		copiar(area);
	}
	
}
