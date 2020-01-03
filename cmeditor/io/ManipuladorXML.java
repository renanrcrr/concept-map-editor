package cmeditor.io;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import org.jgraph.graph.GraphConstants;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import cmeditor.desenho.elemento.ArcoContinuo;
import cmeditor.desenho.elemento.ArcoTracejado;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.FraseEnlace;
import cmeditor.desenho.elemento.FraseLivre;
import cmeditor.desenho.elemento.Legenda;
import cmeditor.desenho.metodo.TratamentoStrings;
import cmeditor.gui.dialogo.PainelEstiloLinha;

public class ManipuladorXML extends DefaultHandler {
	
	public final int ARCO_CONTINUO = 0;
	public final int ARCO_TRACEJADO = 1;
	
	Mapa mapa;
	
	public ManipuladorXML() {
		super();
	}
	
	public void startDocument() {
		mapa = new Mapa();
	}
	
	public void startElement(String uri,
			String localName,
			String qName,
			Attributes attributes) {
		if (qName == "mapa")
			tratarAtributosMapa(attributes);
		else if (qName == "conceitoontologia")
			tratarConceitoOntologia(attributes);
		else if (qName == "conceito") {
			mapa.vertices.add(
					tratarConceito(attributes));
		}
		else if (qName == "exemplo") {
			mapa.vertices.add(
					tratarExemplo(attributes));
		}
		else if (qName == "legenda") {
			mapa.vertices.add(
					tratarConceito(attributes));
		}
		else if (qName == "fraselivre") {
			mapa.vertices.add(
					tratarFraseLivre(attributes));
		}
		else if (qName == "frasecontextualizada") {
			mapa.vertices.add(
					tratarFraseContextualizada(attributes));
		}
		else if (qName == "arco") {
			if (Integer.parseInt(attributes.getValue("tipo"))
					== ARCO_CONTINUO)
				mapa.arcos.add(tratarArcoContinuo(attributes));
			else
				mapa.arcos.add(tratarArcoTracejado(attributes));
		}
	}
	
	private void tratarAtributosMapa(Attributes atribs) {
		mapa.mudarTipo(Integer.parseInt(atribs.getValue("tipomapa")));
	}
	
	private void tratarConceitoOntologia(Attributes atribs) {
		mapa.conceitosOntologia.add(atribs.getValue("texto"));
	}
	
	private Conceito tratarConceito(Attributes atribs) {
		String texto = "";
		String x = "";
		String y = "";
		String corBorda = "";
		String corPreenchimento  = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "corborda")
				corBorda = atribs.getValue(i);
			else if (atribs.getQName(i) == "corpreenchimento")
				corPreenchimento = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				x != "" &&
				y != "" &&
				corBorda != "" &&
				corPreenchimento != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			Conceito c = new Conceito(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					Double.parseDouble(x),
					Double.parseDouble(y));
			GraphConstants.setBorderColor(c.getAttributes(),
					new Color(Integer.parseInt(corBorda)));
			GraphConstants.setBackground(c.getAttributes(),
					new Color(Integer.parseInt(corPreenchimento)));
			c.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			c.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return c;
		}
		else
			return new Conceito("", 0, 0);
	}
	
	private Exemplo tratarExemplo(Attributes atribs) {
		String texto = "";
		String x = "";
		String y = "";
		String corBorda = "";
		String corPreenchimento  = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "corborda")
				corBorda = atribs.getValue(i);
			else if (atribs.getQName(i) == "corpreenchimento")
				corPreenchimento = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				x != "" &&
				y != "" &&
				corBorda != "" &&
				corPreenchimento != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			Exemplo e = new Exemplo(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					Double.parseDouble(x),
					Double.parseDouble(y));
			GraphConstants.setBorderColor(e.getAttributes(),
					new Color(Integer.parseInt(corBorda)));
			GraphConstants.setBackground(e.getAttributes(),
					new Color(Integer.parseInt(corPreenchimento)));
			e.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			e.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return e;
		}
		else
			return new Exemplo("", 0, 0);
	}
	
	private Legenda tratarLegenda(Attributes atribs) {
		String texto = "";
		String x = "";
		String y = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				x != "" &&
				y != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			Legenda l = new Legenda(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					Double.parseDouble(x),
					Double.parseDouble(y));
			l.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			l.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return l;
		}
		else
			return new Legenda("", 0, 0);
	}
	
	private FraseLivre tratarFraseLivre(Attributes atribs) {
		String texto = "";
		String tipoSeta = "";
		String x = "";
		String y = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			if (atribs.getQName(i) == "tiposeta")
				tipoSeta = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				tipoSeta != "" &&
				x != "" &&
				y != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			FraseLivre fl = new FraseLivre(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					Integer.parseInt(tipoSeta),
					Double.parseDouble(x),
					Double.parseDouble(y));
			fl.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			fl.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return fl;
		}
		else
			return new FraseLivre("", 0, 0, 0);
	}
	
	private FraseEnlace tratarFraseContextualizada(Attributes atribs) {
		String texto = "";
		String supertipo = "";
		String tipoSeta = "";
		String x = "";
		String y = "";
		String nomeLetra  = "";
		String estiloLetra = "";
		String tamanhoLetra = "";
		String corLetra  = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "texto")
				texto = atribs.getValue(i);
			else if (atribs.getQName(i) == "supertipo")
				supertipo = atribs.getValue(i);
			else if (atribs.getQName(i) == "tiposeta")
				tipoSeta = atribs.getValue(i);
			else if (atribs.getQName(i) == "x")
				x = atribs.getValue(i);
			else if (atribs.getQName(i) == "y")
				y = atribs.getValue(i);
			else if (atribs.getQName(i) == "nomeletra")
				nomeLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "estiloletra")
				estiloLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "tamanholetra")
				tamanhoLetra = atribs.getValue(i);
			else if (atribs.getQName(i) == "corletra")
				corLetra = atribs.getValue(i);
		}
		if (texto != "" &&
				supertipo != "" &&
				tipoSeta != "" &&
				x != "" &&
				y != "" &&
				nomeLetra != "" &&
				estiloLetra != "" &&
				tamanhoLetra != "" &&
				corLetra != "") {
			texto = texto.replace("\\n", "\n");
			FraseContextualizada fc = new FraseContextualizada(TratamentoStrings.converteStringParaJGraph(
							texto, nomeLetra,
							Integer.parseInt(estiloLetra),
							Integer.parseInt(tamanhoLetra),
							new Color(Integer.parseInt(corLetra))),
					supertipo,
					Integer.parseInt(tipoSeta),
					Double.parseDouble(x),
					Double.parseDouble(y));
			fc.mudaFonte(new Font(nomeLetra,
					Integer.parseInt(estiloLetra),
					Integer.parseInt(tamanhoLetra)));
			fc.mudaFonteCor(new Color(Integer.parseInt(corLetra)));
			return fc;
		}
		else
			return new FraseContextualizada("", "", 0, 0, 0);
	}
	
	private ArcoContinuo tratarArcoContinuo(Attributes atribs) {
		String origem = "";
		String alvo = "";
		String corLinha = "";
		String estilo = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "origem")
				origem = atribs.getValue(i);
			else if (atribs.getQName(i) == "alvo")
				alvo = atribs.getValue(i);
			else if (atribs.getQName(i) == "corlinha")
				corLinha = atribs.getValue(i);
			else if (atribs.getQName(i) == "estilo")
				estilo = atribs.getValue(i);
		}
		if (origem != "" &&
				alvo != "") {
			ArcoContinuo ac = new ArcoContinuo();
			Vector ligacao = new Vector();
			ligacao.add(new Integer(origem));
			ligacao.add(new Integer(alvo));
			mapa.ligacoesArcos.add(ligacao);
			if (corLinha != "")
				GraphConstants.setLineColor(ac.getAttributes(),
						new Color(Integer.parseInt(corLinha)));
			if (Integer.parseInt(estilo) == PainelEstiloLinha.ORTOGONAL) {
				GraphConstants.setRouting(ac.getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(ac.getAttributes(),
						GraphConstants.STYLE_ORTHOGONAL);
			}
			else if (Integer.parseInt(estilo) == PainelEstiloLinha.CURVA) {
				GraphConstants.setRouting(ac.getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(ac.getAttributes(),
						GraphConstants.STYLE_SPLINE);
			}
			return ac;
		}
		else
			return new ArcoContinuo();
	}
	
	private ArcoTracejado tratarArcoTracejado(Attributes atribs) {
		String origem = "";
		String alvo = "";
		String corLinha = "";
		String estilo = "";
		for (int i = 0; i < atribs.getLength(); i++) {
			if (atribs.getQName(i) == "origem")
				origem = atribs.getValue(i);
			else if (atribs.getQName(i) == "alvo")
				alvo = atribs.getValue(i);
			else if (atribs.getQName(i) == "corlinha")
				corLinha = atribs.getValue(i);
			else if (atribs.getQName(i) == "estilo")
				estilo = atribs.getValue(i);
		}
		if (origem != "" &&
				alvo != "") {
			ArcoTracejado at = new ArcoTracejado();
			Vector ligacao = new Vector();
			ligacao.add(new Integer(origem));
			ligacao.add(new Integer(alvo));
			mapa.ligacoesArcos.add(ligacao);
			if (corLinha != "")
				GraphConstants.setLineColor(at.getAttributes(),
						new Color(Integer.parseInt(corLinha)));
			if (Integer.parseInt(estilo) == PainelEstiloLinha.ORTOGONAL) {
				GraphConstants.setRouting(at.getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(at.getAttributes(),
						GraphConstants.STYLE_ORTHOGONAL);
			}
			else if (Integer.parseInt(estilo) == PainelEstiloLinha.CURVA) {
				GraphConstants.setRouting(at.getAttributes(),
						GraphConstants.ROUTING_SIMPLE);
				GraphConstants.setLineStyle(at.getAttributes(),
						GraphConstants.STYLE_SPLINE);
			}
			return at;
		}
		else
			return new ArcoTracejado();
	}
	
}
