package cmeditor.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Vector;

import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.jgraph.graph.DefaultEdge;
import org.xml.sax.SAXException;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.Vertice;

public class LeitorXML {
	
	private static boolean analisar(File f,
			ManipuladorXML h) {
		boolean retorno = true;
		try {
			SAXParserFactory.newInstance().newSAXParser().parse(f, h);
		} catch (SAXException e) {
			retorno = false;
			e.printStackTrace();
		} catch (IOException e) {
			retorno = false;
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			retorno = false;
			e.printStackTrace();
		} catch (FactoryConfigurationError e) {
			retorno = false;
			e.printStackTrace();
		}
		return retorno;
	}
	
	private static String criarArquivoTemporario(File f) {
		try {
			BufferedReader entrada = new BufferedReader(new InputStreamReader(
					new FileInputStream(f), "UTF-8"));
			String stream = "";
			String linha = entrada.readLine();
			while (linha != null) {
				if (linha.startsWith("<!DOCTYPE")) {
					String temp = "";
					for (int i = 0; i < linha.length(); i++) {
						if (linha.charAt(i) == '#')
							temp += System.getProperty("file.separator");
						else
							temp += linha.charAt(i);
					}
					stream += temp + "\n";
				}
				else
					stream += linha + "\n";
				linha = entrada.readLine();
			}
			entrada.close();
			File arquivo = new File("temp.xml");
			BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(arquivo), "UTF-8"));
			for (int i = 0; i < stream.length(); i++) {
				if (stream.charAt(i) == '\n')
					saida.newLine();
				else
					saida.write(stream.charAt(i));
			}
			saida.flush();
			saida.close();
		}
		catch (IOException exc) {
			exc.printStackTrace();
		}
		return "temp.xml";
	}
	
	public static boolean lerMapa(File f, AreaDesenho area) {
		ManipuladorXML h = new ManipuladorXML();
		boolean retorno = true;
		File temp = new File(criarArquivoTemporario(f));
		if (analisar(temp, h)) {
			area.mudaTipoMapa(h.mapa.pegarTipo());
			if (h.mapa.pegarTipo() == Mapa.CONTEXTUALIZADO) {
				for (int i = 0; i < h.mapa.conceitosOntologia.size(); i++)
					area.botaConceitoOntologia(
							(String)h.mapa.conceitosOntologia.get(i));
			}
			area.getGraphLayoutCache().insert(
					h.mapa.vertices.toArray());
			for (int i = 0; i < h.mapa.ligacoesArcos.size(); i++) {
				Vector ligacao = (Vector)h.mapa.ligacoesArcos.get(i);
				((DefaultEdge)h.mapa.arcos.get(i)).setSource(
						((Vertice)h.mapa.vertices.get(((Integer)
								ligacao.get(0)).intValue())).pegaPortPadrao());
				((DefaultEdge)h.mapa.arcos.get(i)).setTarget(
						((Vertice)h.mapa.vertices.get(((Integer)
								ligacao.get(1)).intValue())).pegaPortPadrao());
			}
			area.getGraphLayoutCache().insert(
					h.mapa.arcos.toArray());
			area.setSelectionCells(new Object[] {});
			try {
				area.mudaCaminhoArquivo(f.getCanonicalPath());
			} catch (IOException exc) {
				exc.printStackTrace();
			}
			area.mudaNomeArquivo(f.getName());
		}
		else
			retorno = false;
		temp.delete();
		return retorno;
	}
	
}
