package cmeditor.gui.dialogo;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PainelFonte
extends JPanel
implements ListSelectionListener {
	
	JList lsNome;
	JList lsTamanho;
	JList lsEstilo;
	String[] nomes;
	String[] tamanhos;
	String[] estilos;
	JTextArea txExemplo;
	public static int OK = JOptionPane.OK_OPTION;
	public static int CANCEL = JOptionPane.CANCEL_OPTION;

	public PainelFonte() {
		super();
		construir();
	}
	
	public PainelFonte(Font f) {
		super();
		construir();
		mudaFonte(f);
	}
	
	protected void construir() {
		this.setLayout(new BorderLayout());
		
		nomes = GraphicsEnvironment.getLocalGraphicsEnvironment(
				).getAvailableFontFamilyNames();
		String[] tamanhos = {"8", "10", "12", "14", "18", "24", "36"};
		this.tamanhos = tamanhos;
		String[] estilos = {"Normal", "Negrito", "It\u00e1lico"};
		this.estilos = estilos;
		
		FlowLayout layout = new FlowLayout(FlowLayout.CENTER);
		layout.setHgap(10);
		JPanel p = new JPanel(layout);
		
		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(new JLabel("Fonte:"),
				BorderLayout.PAGE_START);
		lsNome = new JList(nomes);
		lsNome.setSelectedIndex(0);
		lsNome.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsNome.setVisibleRowCount(7);
		lsNome.addListSelectionListener(this);
		p1.add(new JScrollPane(lsNome),
				BorderLayout.CENTER);
		p.add(p1);
		
		p1 = new JPanel(new BorderLayout());
		p1.add(new JLabel("Tamanho:"),
				BorderLayout.PAGE_START);
		lsTamanho = new JList(tamanhos);
		lsTamanho.setSelectedIndex(0);
		lsTamanho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsTamanho.setVisibleRowCount(7);
		lsTamanho.addListSelectionListener(this);
		p1.add(new JScrollPane(lsTamanho),
				BorderLayout.CENTER);
		p.add(p1);
		
		p1 = new JPanel(new BorderLayout());
		p1.add(new JLabel("Estilo:"),
				BorderLayout.PAGE_START);
		lsEstilo = new JList(estilos);
		lsEstilo.setSelectedIndex(0);
		lsEstilo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lsEstilo.setVisibleRowCount(7);
		lsEstilo.addListSelectionListener(this);
		p1.add(new JScrollPane(lsEstilo),
				BorderLayout.CENTER);
		p.add(p1);
		this.add(p, BorderLayout.PAGE_START);
		
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Exemplo:"));
		txExemplo = new JTextArea();
		txExemplo.setPreferredSize(new Dimension(200, 50));
		txExemplo.append("AaBbYyZz");
		p.add(txExemplo);
		this.add(p, BorderLayout.PAGE_END);
	}
	
	public void mudaFonte(Font f) {
		int i = Arrays.binarySearch(nomes, f.getName());
		if (i < 0)
			i = 0;
		lsNome.setSelectedIndex(i);
		lsNome.ensureIndexIsVisible(i);
		
		i = Arrays.binarySearch(tamanhos, String.valueOf(f.getSize()));
		if (i < 0)
			i = 0;
		lsTamanho.setSelectedIndex(i);
		lsTamanho.ensureIndexIsVisible(i);
		
		switch (f.getStyle()) {
			case Font.BOLD: lsEstilo.setSelectedIndex(1);
			break;
			case Font.ITALIC: lsEstilo.setSelectedIndex(2);
			break;
			default: lsEstilo.setSelectedIndex(0);
			break;
		}
		txExemplo.setFont(f);
	}
	
	public Font pegaFonte() {
		int estilo = 0;
		switch (lsEstilo.getSelectedIndex()) {
			case 1: estilo = Font.BOLD;
			break;
			case 2: estilo = Font.ITALIC;
			break;
			default: estilo = Font.PLAIN;
			break;
		}
		return new Font(
				(String)lsNome.getSelectedValue(),
				estilo,
				Integer.parseInt((String)lsTamanho.getSelectedValue()));
	}
	
	public void valueChanged(ListSelectionEvent evt) {
		int estilo = 0;
		switch (lsEstilo.getSelectedIndex()) {
			case 1: estilo = Font.BOLD;
			break;
			case 2: estilo = Font.ITALIC;
			break;
			default: estilo = Font.PLAIN;
			break;
		}
		txExemplo.setFont(new Font(
				(String)lsNome.getSelectedValue(),
				estilo,
				Integer.parseInt((String)lsTamanho.getSelectedValue())));
	}
	
}
