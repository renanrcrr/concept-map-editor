package cmeditor.gui;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class BarraMenus extends JMenuBar{
	
	public BarraMenus(Acoes acoes) {
		super();
		JMenu menu = new JMenu("Arquivo");
		menu.setMnemonic('A');
		menu.add(acoes.novo);
		menu.add(acoes.abrir);
		menu.add(acoes.menuRecentes);
		menu.add(acoes.salvar);
		menu.add(acoes.salvarComo);
		menu.addSeparator();
		menu.add(acoes.exportar);
		menu.addSeparator();
		menu.add(acoes.imprimir);
		menu.addSeparator();
		menu.add(acoes.sair);
		this.add(menu);
		menu = new JMenu("Editar");
		menu.setMnemonic('E');
		menu.add(acoes.desfazer);
		menu.add(acoes.refazer);
		menu.addSeparator();
		menu.add(acoes.recortar);
		menu.add(acoes.copiar);
		menu.add(acoes.colar);
		menu.add(acoes.excluir);
		menu.addSeparator();
		menu.add(acoes.selecionarTudo);
		menu.addSeparator();
		menu.add(acoes.preferencias);
		this.add(menu);
		menu = new JMenu("Exibir");
		menu.setMnemonic('X');
		menu.add(acoes.aproximar);
		menu.add(acoes.afastar);
		this.add(menu);
		menu = new JMenu("Formatar");
		menu.setMnemonic('F');
		menu.add(acoes.corLetra);
		menu.add(acoes.fonte);
		menu.addSeparator();
		menu.add(acoes.corPreenchimento);
		menu.add(acoes.corBorda);
		menu.addSeparator();
		menu.add(acoes.corLinha);
		menu.add(acoes.estiloLinha);
		this.add(menu);
		menu = new JMenu("Leitura");
		menu.setMnemonic('L');
		menu.add(acoes.leituraDiferenciacoes);
		menu.add(acoes.leituraReconciliacoes);
		menu.add(acoes.leituraNiveisInclusao);
		menu.add(acoes.leituraExemplificacoes);
		this.add(menu);
		menu = new JMenu("Ajuda");
		menu.setMnemonic('J');
		menu.add(acoes.ajuda);
		menu.add(acoes.sobre);
		this.add(menu);
	}

}
