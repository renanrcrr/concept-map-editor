package cmeditor.io;

import javax.swing.*;
import javax.swing.tree.*;

import java.awt.Component;
import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

import org.w3c.dom.*;
import javax.xml.parsers.*;

public class ArvoreTaxonomia extends JTree {
	
	public ArvoreTaxonomia(String filename) throws IOException {
		this(filename, new FileInputStream(new File(filename)));
	}
	
	public ArvoreTaxonomia(String filename, InputStream in) {
		super(criaNodoRaiz(in));
		this.setCellRenderer(new Renderizador());
	}
	
	private static DefaultMutableTreeNode criaNodoRaiz(InputStream in) {
		try {
			DocumentBuilderFactory builderFactory =
				DocumentBuilderFactory.newInstance();
			DocumentBuilder builder =
				builderFactory.newDocumentBuilder();
			Document document = builder.parse(in);
			document.getDocumentElement().normalize();
			Element rootElement = document.getDocumentElement();
			DefaultMutableTreeNode rootTreeNode =
				constroiArvore(rootElement);
			return(rootTreeNode);
		} catch(Exception e) {
			String errorMessage =
				"Error making root node: " + e;
			System.err.println(errorMessage);
			e.printStackTrace();
			return(new DefaultMutableTreeNode(errorMessage));
		}
	}
	
	private static DefaultMutableTreeNode constroiArvore(Element rootElement) {
		DefaultMutableTreeNode rootTreeNode =
			new DefaultMutableTreeNode("Taxonomia");
		adicFilhos(rootTreeNode, rootElement);
		return(rootTreeNode);
	}
	
	private static void adicFilhos(DefaultMutableTreeNode parentTreeNode,
			Node parentXMLElement) {
		NodeList childElements =
			parentXMLElement.getChildNodes();
		for (int i = 0; i < childElements.getLength(); i++) {
			Node childElement = childElements.item(i);
			if (childElement.getNodeName() == "supertipo") {
				DefaultMutableTreeNode childTreeNode =
					new DefaultMutableTreeNode(pegaSupertipo(childElement));
				parentTreeNode.add(childTreeNode);
				adicFilhos(childTreeNode, childElement);
			}
		}
	}
	
	private static Supertipo pegaSupertipo(Node childElement) {
		String nome = "";
		String descricao = "";
		Vector frases = new Vector();
		Node nodo = childElement.getAttributes().item(0);
		nome = nodo.getNodeValue();
		NodeList lista = childElement.getChildNodes();
		for (int i = 0; i < lista.getLength(); i++) {
			nodo = lista.item(i);
			if (nodo.getNodeName() == "desc")
				descricao = nodo.getChildNodes().item(0).getNodeValue();
			if (nodo.getNodeName() == "frase")
				frases.add(nodo.getChildNodes().item(0).getNodeValue());
		}
		Supertipo supertipo = new Supertipo(nome, descricao);
		supertipo.frases = frases;
		return supertipo;
	}
	
	public boolean testaESelecionaSupertipo(String supertipo) {
		Vector v = new Vector();
		v.add("Taxonomia");
		String temp = "";
		for (int i = 0; i < supertipo.length(); i++) {
			if (supertipo.charAt(i) == '.') {
				v.add(temp);
				temp = "";
			}
			else
				temp += supertipo.charAt(i);
		}
		if (temp.length() > 0)
			v.add(temp);
		TreePath caminhoBusca = busca(new TreePath((TreeNode)this.getModel().getRoot()),
				v,
				0);
		this.setSelectionPath(caminhoBusca);
		if (this.getSelectionCount() > 0)
			return true;
		else
			return false;
	}
	
	private TreePath busca(TreePath pai, Vector nodos, int profundidade) {
		TreeNode node = (TreeNode)pai.getLastPathComponent();
		Object o = node.toString();
		if (o.equals(nodos.get(profundidade))) {
			if (profundidade == nodos.size() - 1)
				return pai;
			if (node.getChildCount() >= 0)
				for (Enumeration e = node.children(); e.hasMoreElements();) {
					TreeNode n = (TreeNode)e.nextElement();
					TreePath path = pai.pathByAddingChild(n);
					TreePath resultado = busca(path, nodos, profundidade + 1);
					if (resultado != null)
						return resultado;
                }
		}
		return null;
	}
	
	public String pegaSupertipoComoString() {
		if (this.getSelectionCount() == 0)
			return "";
		String retorno = "";
		TreePath caminho = this.getSelectionPath();
		for (int i = 1; i < caminho.getPathCount(); i++) {
			DefaultMutableTreeNode nodoTemp =
				(DefaultMutableTreeNode)caminho.getPathComponent(i);
			if (i > 1)
				retorno += ".";
			retorno += nodoTemp.toString();
		}
		return retorno;
	}
	
	class Renderizador extends DefaultTreeCellRenderer {
		
		public Component getTreeCellRendererComponent(
				JTree tree,
				Object value,
				boolean sel,
				boolean expanded,
				boolean leaf,
				int row,
				boolean hasFocus) {
            super.getTreeCellRendererComponent(
            		tree, value, sel,
					expanded, leaf, row,
					hasFocus);
            setIcon(null);
            return this;
        }
	}
	
}