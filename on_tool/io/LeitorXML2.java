package on_tool.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import cmeditor.desenho.AreaDesenho;
import org.xml.sax.SAXException;


public class LeitorXML2 {
	
	private static boolean analisar(File f,
			ManipuladorXML2 h) {
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
		ManipuladorXML2 h = new ManipuladorXML2();
		boolean retorno = true;
		File temp = new File(criarArquivoTemporario(f));
		if (analisar(temp, h)) {

			for (int i = 0; i < h.conceitos.size(); i++)
				area.botaConceitoOntologia(((String)h.conceitos.get(i)).replace("\\n", " "));
		}
		else
			retorno = false;
		temp.delete();
		return retorno;
	}
	
}
