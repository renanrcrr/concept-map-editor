package cmeditor.desenho.metodo;

import java.awt.Color;
import java.awt.Font;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphConstants;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.FraseEnlace;
import cmeditor.desenho.elemento.Vertice;
import cmeditor.gui.dialogo.Dialogos;
import cmeditor.gui.dialogo.PainelEstiloLinha;
import cmeditor.io.Mapa;

public class EdicaoElementos {
	
	public static void editarCorLinha(Object objeto,
			AreaDesenho area) {
		Color cor = GraphConstants.getLineColor(
				((Edge)objeto).getAttributes());
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Map atribs = new Hashtable();
			GraphConstants.setLineColor(atribs, cor);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarEstiloLinha(Object objeto,
			AreaDesenho area) {
		int estilo = 0;
		if (GraphConstants.getRouting(((Edge)objeto).getAttributes())
				== GraphConstants.ROUTING_DEFAULT)
			estilo = PainelEstiloLinha.NORMAL;
		else if (GraphConstants.getLineStyle(((Edge)objeto).getAttributes())
				== GraphConstants.STYLE_ORTHOGONAL)
			estilo = PainelEstiloLinha.ORTOGONAL;
		else if (GraphConstants.getLineStyle(((Edge)objeto).getAttributes())
				== GraphConstants.STYLE_SPLINE)
			estilo = PainelEstiloLinha.CURVA;
		int estiloNovo = Dialogos.mostraDialogoEstiloLinha(
				area, estilo);
		if (estiloNovo == PainelEstiloLinha.NORMAL) {
			Map atribs = new Hashtable();
			GraphConstants.setRouting(atribs,
					GraphConstants.ROUTING_DEFAULT);
			GraphConstants.setLineStyle(atribs,
					GraphConstants.STYLE_ORTHOGONAL);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
		else if (estiloNovo == PainelEstiloLinha.ORTOGONAL) {
			Map atribs = new Hashtable();
			GraphConstants.setRouting(atribs,
					GraphConstants.ROUTING_SIMPLE);
			GraphConstants.setLineStyle(atribs,
					GraphConstants.STYLE_ORTHOGONAL);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
		else if (estiloNovo == PainelEstiloLinha.CURVA) {
			Map atribs = new Hashtable();
			GraphConstants.setRouting(atribs,
					GraphConstants.ROUTING_SIMPLE);
			GraphConstants.setLineStyle(atribs,
					GraphConstants.STYLE_SPLINE);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Edge)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarCorBorda(Object objeto,
			AreaDesenho area) {
		Color cor = GraphConstants.getBorderColor(
				((Vertice)objeto).getAttributes());
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Map atribs = new Hashtable();
			GraphConstants.setBorderColor(atribs, cor);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Conceito ||
						objetos[i] instanceof Exemplo)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarCorPreenchimento(Object objeto,
			AreaDesenho area) {
		Color cor = GraphConstants.getBackground(
				((Vertice)objeto).getAttributes());
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Map atribs = new Hashtable();
			GraphConstants.setBackground(atribs, cor);
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Conceito ||
						objetos[i] instanceof Exemplo)
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
		}
	}
	
	public static void editarCorLetra(Object objeto,
			AreaDesenho area) {
		Color cor = ((Vertice)objeto).pegaFonteCor();
		cor = Dialogos.mostraDialogoCor(area,
				cor);
		if (cor != null) {
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)				
				if (objetos[i] instanceof Vertice) {
					((Vertice)objetos[i]).mudaFonteCor(cor);
					String rotulo = (String)((Vertice)objetos[i]).getUserObject();
					rotulo = TratamentoStrings.converteStringParaJava(rotulo);
					Map atribs = new Hashtable();
					GraphConstants.setValue(atribs,
							TratamentoStrings.converteStringParaJGraph(rotulo,
									((Vertice)objetos[i]).pegaFonte().getName(),
									((Vertice)objetos[i]).pegaFonte().getStyle(),
									((Vertice)objetos[i]).pegaFonte().getSize(),
									((Vertice)objetos[i]).pegaFonteCor()));
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
				}
		}
	}
	
	public static void editarFonte(Object objeto,
			AreaDesenho area) {
		Font fonte = ((Vertice)objeto).pegaFonte();
		fonte = Dialogos.mostraDialogoFonte(area,
				fonte);
		if (fonte != null) {
			Object[] objetos = area.getSelectionCells();
			for (int i = 0; i < objetos.length; i++)
				if (objetos[i] instanceof Vertice) {
					((Vertice)objetos[i]).mudaFonte(fonte);
					String rotulo = (String)((Vertice)objetos[i]).getUserObject();
					rotulo = TratamentoStrings.converteStringParaJava(rotulo);
					Map atribs = new Hashtable();
					GraphConstants.setValue(atribs,
							TratamentoStrings.converteStringParaJGraph(rotulo,
									((Vertice)objetos[i]).pegaFonte().getName(),
									((Vertice)objetos[i]).pegaFonte().getStyle(),
									((Vertice)objetos[i]).pegaFonte().getSize(),
									((Vertice)objetos[i]).pegaFonteCor()));
					area.getGraphLayoutCache().editCell(objetos[i], atribs);
				}
		}
	}
	
	public static void editarRotulo(Object objeto,
			AreaDesenho area) {
		String rotulo = (String)((Vertice)objeto).getUserObject();
		rotulo = TratamentoStrings.converteStringParaJava(rotulo);
		String resposta = Dialogos.mostraDialogoEdicaoRotulo(area,
				rotulo);
		if (resposta != null) {
			Map atribs = new Hashtable();
			GraphConstants.setValue(atribs,
					TratamentoStrings.converteStringParaJGraph(resposta,
							((Vertice)objeto).pegaFonte().getName(),
							((Vertice)objeto).pegaFonte().getStyle(),
							((Vertice)objeto).pegaFonte().getSize(),
							((Vertice)objeto).pegaFonteCor()));
			area.getGraphLayoutCache().editCell(objeto, atribs);
		}
	}
	
	public static void editarConceitoContextualizado(Object objeto,
			AreaDesenho area) {
		String rotulo = (String)((Vertice)objeto).getUserObject();
		rotulo = TratamentoStrings.converteStringParaJava(rotulo);
		String resposta = "";
		area.botaConceitoOntologia(rotulo);
		resposta = Dialogos.mostraDialogoConceitoContextualizado(area,
				area.pegaConceitosOntologia().toArray(), rotulo);
		if (resposta != null) {
			Map atribs = new Hashtable();
			GraphConstants.setValue(atribs,
					TratamentoStrings.converteStringParaJGraph(resposta,
							((Vertice)objeto).pegaFonte().getName(),
							((Vertice)objeto).pegaFonte().getStyle(),
							((Vertice)objeto).pegaFonte().getSize(),
							((Vertice)objeto).pegaFonteCor()));
			area.getGraphLayoutCache().editCell(objeto, atribs);
			area.tiraConceitoOntologia(resposta);
		}
		else
			area.tiraConceitoOntologia(rotulo);
	}
	
	public static void editarFraseContextualizada(FraseContextualizada objeto,
			AreaDesenho area) {
		Vector v = Dialogos.mostrarDialogoFraseContextualizada(
				area, objeto);
		if (v != null) {
			objeto.mudaSupertipo((String)v.get(0));
			Map atribs = new Hashtable();
			GraphConstants.setValue(atribs,
					TratamentoStrings.converteStringParaJGraph((String)v.get(1),
							((Vertice)objeto).pegaFonte().getName(),
							((Vertice)objeto).pegaFonte().getStyle(),
							((Vertice)objeto).pegaFonte().getSize(),
							((Vertice)objeto).pegaFonteCor()));
			area.getGraphLayoutCache().editCell(objeto, atribs);
		}
	}
	
	public static void excluirSelecionados(AreaDesenho area) {
		if (!area.isSelectionEmpty()) {
			Object[] selecionados = area.getSelectionCells();
			if (area.pegaTipoMapa() == Mapa.CONTEXTUALIZADO) {
				for (int i = 0; i < selecionados.length; i++)
					if (selecionados[i] instanceof Conceito)
						area.botaConceitoOntologia(TratamentoStrings.converteStringParaJava(
								(String)((Conceito)selecionados[i]).getUserObject()));
			}
			area.getGraphLayoutCache().remove(
					selecionados, true, true);
			area.getGraphLayoutCache().remove(
					pegaFrasesInuteis(area), true, true);
		}
	}
	
	public static Object[] pegaFrasesInuteis(AreaDesenho area) {
		Object[] cells = area.getGraphLayoutCache().getCells(
				false, true, false, false);
		Vector v = new Vector();
		for (int i = 0; i < cells.length; i++) {
			if (cells[i] instanceof FraseEnlace) {
				if (DefaultGraphModel.getIncomingEdges(area.getModel(),
						((Vertice)cells[i]).pegaPortPadrao()).length == 0 &&
						DefaultGraphModel.getOutgoingEdges(area.getModel(),
						((Vertice)cells[i]).pegaPortPadrao()).length == 0)
					v.add(cells[i]);
			}
		}
		return v.toArray();
	}
	
}
