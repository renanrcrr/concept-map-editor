package cmeditor.gui;

import javax.swing.JToolBar;

public class BarraFerramentas extends JToolBar {

	public BarraFerramentas(Acoes acoes) {
		super("Barra de ferramentas");
		this.add(acoes.novo);
		this.add(acoes.abrir);
		this.add(acoes.salvar);
		this.add(acoes.imprimir);
		this.addSeparator();
		this.add(acoes.desfazer);
		this.add(acoes.refazer);
		this.addSeparator();
		this.add(acoes.aproximar);
		this.add(acoes.afastar);
		this.addSeparator();
		this.add(acoes.ajuda);
	}

}
