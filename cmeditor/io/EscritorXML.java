package cmeditor.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Vector;

import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.GraphConstants;
import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.ArcoContinuo;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.FraseEnlace;
import cmeditor.desenho.elemento.FraseLivre;
import cmeditor.desenho.elemento.Legenda;
import cmeditor.desenho.elemento.Vertice;
import cmeditor.desenho.metodo.TratamentoStrings;
import cmeditor.gui.dialogo.PainelEstiloLinha;

public class EscritorXML {
	
	public static boolean salvarArquivo(String strArquivo, AreaDesenho area) {
		boolean retorno = true;
		if (!strArquivo.endsWith(".xml"))
			strArquivo += ".xml";
		File arquivo = new File(strArquivo);
		try {
			BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(arquivo), "UTF-8"));
			String strArea = lerAreaDesenho(area);
			for (int i = 0; i < strArea.length(); i++) {
				if (strArea.charAt(i) == '\n')
					saida.newLine();
				else
					saida.write(strArea.charAt(i));
			}
			saida.flush();
			saida.close();
		}
		catch (IOException exc) {
			exc.printStackTrace();
			retorno = false;
		}
		return retorno;
	}
	
	private static String lerAreaDesenho(AreaDesenho area) {
		String retorno =
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		retorno += "<!DOCTYPE mapa SYSTEM \"cmeditor#io#mapa.dtd\">\n";
		retorno +=
				"<!--<!DOCTYPE mapa SYSTEM \"mapa.dtd\">-->\n";
		retorno += "<mapa tipomapa=\"" +
				pegaTipoMapa(area) +
				"\">\n\n";
		retorno += pegaConceitosOntologia(area) + "\n";
		
		Vector verticesOrdenados = pegaVerticesOrdenados(area);
		
		retorno += pegaConceitos(verticesOrdenados,
				area) + "\n";
		retorno += pegaExemplos(verticesOrdenados,
				area) + "\n";
		retorno += pegaLegendas(verticesOrdenados,
				area) + "\n";
		
		if (area.pegaTipoMapa() == Mapa.LIVRE_CONTEXTO)
			retorno += pegaFrasesLivres(verticesOrdenados,
					area) + "\n";
		else
			retorno += pegaFrasesContextualizadas(verticesOrdenados,
					area) + "\n";
		retorno += pegaArcos(area,
				verticesOrdenados) + "\n";
		retorno += "</mapa>";
		return retorno;
	}
	
	private static String pegaTipoMapa(AreaDesenho area) {
		return String.valueOf(area.pegaTipoMapa());
	}
	
	private static String pegaConceitosOntologia(AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < area.pegaConceitosOntologia().size(); i++) {
			retorno += "<conceitoontologia texto=\"" +
					(String)area.pegaConceitosOntologia().get(i) +
					"\"/>\n";
		}
		return retorno;
	}
	
	private static Vector pegaVerticesOrdenados(AreaDesenho area) {
		Vector retorno = new Vector();
		Object[] vertices = area.getGraphLayoutCache().getCells(false,
				true, false, false);
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] instanceof Conceito)
				retorno.add(vertices[i]);
		}
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i] instanceof Exemplo)
				retorno.add(vertices[i]);
		}
		if (area.pegaTipoMapa() == Mapa.LIVRE_CONTEXTO)
			for (int i = 0; i < vertices.length; i++) {
				if (vertices[i] instanceof FraseLivre)
					retorno.add(vertices[i]);
			}
		else
			for (int i = 0; i < vertices.length; i++) {
				if (vertices[i] instanceof FraseContextualizada)
					retorno.add(vertices[i]);
			}
		return retorno;
	}
	
	private static String pegaArcos(AreaDesenho area, Vector vertices) {
		String retorno = "";
		Object[] arcos = area.getGraphLayoutCache().getCells(
				false, false, false, true);
		for (int i = 0; i < arcos.length; i++) {
			if (arcos[i] instanceof ArcoContinuo)
				retorno += "<arco tipo=\"0\" ";
			else
				retorno += "<arco tipo=\"1\" ";
			for (int j = 0; j < vertices.size(); j++)
				if (((DefaultEdge)arcos[i]).getSource() ==
						((Vertice)vertices.get(j)).pegaPortPadrao()) {
					retorno += "origem=\"" + j + "\" ";
					break;
				}
			for (int j = 0; j < vertices.size(); j++)
				if (((DefaultEdge)arcos[i]).getTarget() ==
						((Vertice)vertices.get(j)).pegaPortPadrao()) {
					retorno += "alvo=\"" + j + "\" ";
					break;
				}
			retorno += "corlinha=\"" + GraphConstants.getLineColor(
					area.getAttributes(arcos[i])).getRGB() + "\" ";
			if (GraphConstants.getRouting(area.getAttributes(arcos[i]))
					== GraphConstants.ROUTING_SIMPLE
					&&
					GraphConstants.getLineStyle(area.getAttributes(arcos[i]))
					== GraphConstants.STYLE_ORTHOGONAL) {
				retorno += "estilo=\"" + PainelEstiloLinha.ORTOGONAL + "\" ";
			}
			else if (GraphConstants.getRouting(area.getAttributes(arcos[i]))
					== GraphConstants.ROUTING_SIMPLE
					&&
					GraphConstants.getLineStyle(area.getAttributes(arcos[i]))
					== GraphConstants.STYLE_SPLINE) {
				retorno += "estilo=\"" + PainelEstiloLinha.CURVA + "\" ";
			}
			else {
				retorno += "estilo=\"" + PainelEstiloLinha.NORMAL + "\" ";
			}
			retorno += "/>\n";
		}
		return retorno;
	}
	
	private static String pegaConceitos(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof Conceito) {
				String s = "<conceito id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "corborda=\"" + GraphConstants.getBorderColor(
						((Vertice)v.get(i)).getAttributes()).getRGB() +
						"\" ";
				s += "corpreenchimento=\"" + GraphConstants.getBackground(
						((Vertice)v.get(i)).getAttributes()).getRGB() +
						"\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}
	
	private static String pegaExemplos(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof Exemplo) {
				String s = "<exemplo id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "corborda=\"" + GraphConstants.getBorderColor(
						((Vertice)v.get(i)).getAttributes()).getRGB() +
						"\" ";
				s += "corpreenchimento=\"" + GraphConstants.getBackground(
						((Vertice)v.get(i)).getAttributes()).getRGB() +
						"\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}
	
	private static String pegaLegendas(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof Legenda) {
				String s = "<legenda id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}
	
	private static String pegaFrasesLivres(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof FraseLivre) {
				String s = "<fraselivre id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "tiposeta=\"";
				if (((FraseEnlace)v.get(i)).testaContinua())
					s += FraseEnlace.SETA_CONTINUA + "\" ";
				else
					s += FraseEnlace.SETA_TRACEJADA + "\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}
	
	private static String pegaFrasesContextualizadas(Vector v, AreaDesenho area) {
		String retorno = "";
		for (int i = 0; i < v.size(); i++)
			if (v.get(i) instanceof FraseContextualizada) {
				String s = "<frasecontextualizada id=\"" + i + "\" ";
				s += "texto=\"" +
						TratamentoStrings.converteStringParaJava((String)((Vertice)v.get(i)).getUserObject()).replace("\n", "\\n") +
						"\" ";
				s += "supertipo=\"" +
						((FraseContextualizada)v.get(i)).pegaSupertipo() + "\" ";
				s += "x=\"" +
						area.getCellBounds(v.get(i)).getX() + "\" ";
				s += "y=\"" +
						area.getCellBounds(v.get(i)).getY() + "\" ";
				s += "tiposeta=\"";
				if (((FraseEnlace)v.get(i)).testaContinua())
					s += FraseEnlace.SETA_CONTINUA + "\" ";
				else
					s += FraseEnlace.SETA_TRACEJADA + "\" ";
				s += "nomeletra=\"" + ((Vertice)v.get(i)).pegaFonte().getFontName() +
						"\" ";
				s += "estiloletra=\"" + ((Vertice)v.get(i)).pegaFonte().getStyle() +
						"\" ";
				s += "tamanholetra=\"" + ((Vertice)v.get(i)).pegaFonte().getSize() +
						"\" ";
				s += "corletra=\"" + ((Vertice)v.get(i)).pegaFonteCor().getRGB() +
						"\" ";
				s += "/>\n";
				retorno += s;
			}
		return retorno;
	}

}
