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

public class ManipuladorRecentes {
	
	public static Vector carregaRecentes() {
		Vector pilha = new Vector();
		File arq = new File("recentes.dat");
		if (arq.exists()) {
			try {
				BufferedReader entrada = new BufferedReader(new InputStreamReader(
						new FileInputStream(arq)));
				String linha = entrada.readLine();
				int i = 0;
				while (linha != null) {
					pilha.add(linha);
					linha = entrada.readLine();
				}
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return pilha;
	}
	
	public static void salvaRecentes(Vector recentes) {
		File arq = new File("recentes.dat");
		try {
			BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(arq), "UTF-8"));
			for (int i = 0; i < recentes.size(); i++) {
				saida.write((String)recentes.get(i));
				saida.newLine();
			}
			saida.flush();
			saida.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
