package cmeditor.io;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.filechooser.FileFilter;

public class FiltroArquivo extends FileFilter {
	
    private Hashtable filtros = null;
    private String descricao = null;
    private String descricaoTotal = null;
    private boolean usaExtensoesNaDescricao = true;
	
    public FiltroArquivo() {
    	this.filtros = new Hashtable();
    }
    
    public FiltroArquivo(String extensao,
    		String descricao) {
    	this();
    	if (extensao != null)
    		adExtensao(extensao);
    	if (descricao != null)
    		mudaDescricao(descricao);
    }
    
    public FiltroArquivo(String[] filtros) {
    	this (filtros,
    			null);
    }
    
    public FiltroArquivo(String[] filtros, String descricao) {
    	this();
    	for (int i = 0; i < filtros.length; i++) {
    		adExtensao(filtros[i]);
    	}
    	if (descricao!=null)
    		mudaDescricao(descricao);
    }
    
	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory())
				return true;
			String extensao = pegaExtensao(f);
			if (extensao != null &&
					filtros.get(pegaExtensao(f)) != null)
				return true;
		}
		return false;
	}
	
    public void adExtensao(String extensao) {
    	if (filtros == null) {
    		filtros = new Hashtable(5);
    	}
    	filtros.put(extensao.toLowerCase(),
    			this);
    	descricaoTotal = null;
    }

	public String getDescription() {
		if (descricaoTotal == null) {
			if (descricao == null ||
					testaUsaExtensaoNaDescricao()) {
				descricaoTotal = descricao == null ? "(" : descricao + " (";
				Enumeration extensoes = filtros.keys();
				if (extensoes != null) {
					descricaoTotal += "." + (String)extensoes.nextElement();
					while (extensoes.hasMoreElements())
						descricaoTotal += ", " + (String)extensoes.nextElement();
				}
				descricaoTotal += ")";
			} else
				descricaoTotal = descricao;
		}
		return descricaoTotal;
	}
	
	public String pegaExtensao(File f) {
		if(f != null) {
			String nomeArquivo = f.getName();
			int i = nomeArquivo.lastIndexOf('.');
			if(i > 0 && i < nomeArquivo.length() - 1) {
				return nomeArquivo.substring(i+1).toLowerCase();
			}
		}
		return null;
	}
	
	public void mudaDescricao(String descricao) {
		this.descricao = descricao;
		descricaoTotal = null;
	}
	
	public void mudaUsaExtensoesNaDescricao(boolean b) {
		usaExtensoesNaDescricao = b;
		descricaoTotal = null;
	}
	
	public boolean testaUsaExtensaoNaDescricao() {
		return usaExtensoesNaDescricao;
	}
    
}
