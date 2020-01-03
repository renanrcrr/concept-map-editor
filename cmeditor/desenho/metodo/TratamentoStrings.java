package cmeditor.desenho.metodo;

import java.awt.Color;
import java.awt.Font;

public class TratamentoStrings {
	
	public static final int tamanhosFontes[] = {
			8, 10, 12, 14, 18, 24, 36};
	
	public static String converteStringParaJGraph(String s,
			String fonteNome,
			int fonteEstilo,
			int fonteTamanho,
			Color fonteCor) {
		String texto = tratarEspacos(s);
		if (texto.length() == 0)
			texto = "????";
		String retorno = "<html><p align=center>";
		retorno += "<font face=\"" + fonteNome + "\" ";
		retorno += "color=\"#";
		String var = Integer.toHexString(fonteCor.getRed());
		while (var.length() < 2)
			var = "0" + var;
		retorno += var;
		var = Integer.toHexString(fonteCor.getGreen());
		while (var.length() < 2)
			var = "0" + var;
		retorno += var;
		var = Integer.toHexString(fonteCor.getBlue());
		while (var.length() < 2)
			var = "0" + var;
		retorno += var + "\"";

		for (int i = 0; i < tamanhosFontes.length; i++)
			if (fonteTamanho == tamanhosFontes[i]) {
				retorno += " size=\"" + (i + 1) + "\"";
				break;
			}
		retorno += ">";
		
		if (fonteEstilo == Font.BOLD)
			retorno += "<strong>";
		else if (fonteEstilo == Font.ITALIC)
			retorno += "<i>";
		for (int i = 0; i < texto.length(); i++) {
			if (texto.charAt(i) == '\n')
				retorno += "<br>";
			else
				retorno += texto.charAt(i);
		}
		if (fonteEstilo == Font.BOLD)
			retorno += "</strong>";
		else if (fonteEstilo == Font.ITALIC)
			retorno += "</i>";
		retorno += "</font></p></html>";
		return retorno;
	}
	
	public static String converteStringParaJava(String s) {
	
		String retorno = s.replace("<br>", "\n");
		return retorno.replaceAll("[<][^<>]*[>]", "");
	}
	
	private static String tratarEspacos(String s) {
		String retorno = s.replaceAll("\\s\\s+", " ");
		if (retorno.length() > 0 && Character.isWhitespace(retorno.charAt(0)))
			retorno = retorno.substring(1);
		if (retorno.length() > 0 && Character.isWhitespace(retorno.charAt(retorno.length() - 1)))
			retorno = retorno.substring(0, retorno.length() - 1);
		return retorno;
	}
	
}
