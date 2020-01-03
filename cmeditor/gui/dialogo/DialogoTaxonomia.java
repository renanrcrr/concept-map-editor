package cmeditor.gui.dialogo;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import cmeditor.io.ArvoreTaxonomia;
import cmeditor.io.Supertipo;

import java.io.*;

public class DialogoTaxonomia
extends JPanel
implements TreeSelectionListener {
	
	ArvoreTaxonomia arvTaxonomia;
	public JList lstFrases;
	JTextArea txtDescricao;
	JButton btnSelecionarTodas;
	
	public DialogoTaxonomia(String filename) {
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p.setBorder(BorderFactory.createEmptyBorder(
				2, 2, 2, 2));
		p.add(new JLabel("<html>Selecione um ramo da taxonomia.<br>" +
				"Em seguida, selecione a frase de liga\u00e7\u00e3o desejada.</html>"));
		this.add(p, BorderLayout.PAGE_START);
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Taxonomia"));
		try {
			arvTaxonomia = new ArvoreTaxonomia(filename);
		} catch(IOException ioe) {
			System.out.println("Error creating tree: " + ioe);
		}
		arvTaxonomia.getSelectionModel().setSelectionMode(
				DefaultTreeSelectionModel.SINGLE_TREE_SELECTION);
		arvTaxonomia.addTreeSelectionListener(this);
		JScrollPane rolagem = new JScrollPane(arvTaxonomia);
		rolagem.setPreferredSize(
				new Dimension(160, 180));
		p.add(rolagem);
		this.add(p, BorderLayout.WEST);
		
		p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(
				"Frases de liga\u00e7\u00e3o"));
		lstFrases = new JList();
		lstFrases.setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		rolagem = new JScrollPane(lstFrases);
		rolagem.setPreferredSize(
				new Dimension(160, 180));
		p.add(rolagem);
		this.add(p, BorderLayout.EAST);
		
		p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Descri\u00e7\u00e3o"));
		txtDescricao = new JTextArea(4, 30);
		txtDescricao.setLineWrap(true);
		txtDescricao.setWrapStyleWord(true);
		txtDescricao.setEditable(false);
		rolagem = new JScrollPane(txtDescricao);
		p.add(rolagem);
		this.add(p, BorderLayout.SOUTH);
	}
	
	public void valueChanged(TreeSelectionEvent evt) {
		TreePath caminho = evt.getPath();
		DefaultMutableTreeNode nodo =
			(DefaultMutableTreeNode)caminho.getLastPathComponent();
		if (nodo.isRoot()) {
			arvTaxonomia.setSelectionPath(null);
		}
		else if (nodo.getUserObject() instanceof Supertipo) {
			Supertipo supertipo = (Supertipo)nodo.getUserObject();
			lstFrases.setListData(supertipo.frases);
			txtDescricao.setText(supertipo.descricao);
			txtDescricao.setCaretPosition(0);
			lstFrases.setSelectionInterval(0,
					lstFrases.getModel().getSize() - 1);
		}
	}
	
}