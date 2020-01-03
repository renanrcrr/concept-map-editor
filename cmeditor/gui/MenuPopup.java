package cmeditor.gui;

import javax.swing.Action;
import javax.swing.JPopupMenu;

public class MenuPopup extends JPopupMenu {
	
	public MenuPopup(Acoes acoes) {
		super();
		int contador = adicionar(acoes.recortar);
		contador += adicionar(acoes.copiar);
		contador += adicionar(acoes.colar);
		contador += adicionar(acoes.excluir);
		if (contador > 0)
			addSeparator();
		contador = 0;
		contador += adicionar(acoes.corLetra);
		contador += adicionar(acoes.fonte);
		if (contador > 0)
			addSeparator();
		contador = 0;
		contador += adicionar(acoes.corPreenchimento);
		contador += adicionar(acoes.corBorda);
		if (contador > 0)
			addSeparator();
		contador = 0;
		contador += adicionar(acoes.corLinha);
		contador += adicionar(acoes.estiloLinha);
	}
	
	private int adicionar(Action acao) {
		if (acao.isEnabled()) {
			add(acao);
			return 1;
		}
		else
			return 0;
	}
	
}
