package cmeditor.gui.dialogo;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PainelElementoContextualizado
extends JPanel
implements ActionListener {
	
	public JRadioButton[] botoes;
	public JComboBox cbConceito;
	
	public PainelElementoContextualizado(Vector conceitos,
			boolean inserirLegenda) {
		super();
		this.setBorder(BorderFactory.createTitledBorder(
				"Objeto a ser inserido:"));
		this.setLayout(new GridLayout(3, 1));
		
		ButtonGroup grupo = new ButtonGroup();
		botoes = new JRadioButton[3];
		
		FlowLayout l = new FlowLayout(FlowLayout.LEFT);
		l.setHgap(0);
		l.setVgap(0);
		JPanel p = new JPanel(l);
		
		botoes[0] = new JRadioButton("Conceito: ");
		botoes[0].setMnemonic('C');
		botoes[0].setSelected(true);
		botoes[0].addActionListener(this);
		grupo.add(botoes[0]);
		p.add(botoes[0]);
		cbConceito = new JComboBox(conceitos);
		p.add(cbConceito);
		this.add(p);
		botoes[1] = new JRadioButton("Exemplo");
		botoes[1].setMnemonic('E');
		botoes[1].addActionListener(this);
		grupo.add(botoes[1]);
		this.add(botoes[1]);
		if (inserirLegenda) {
			botoes[2] = new JRadioButton("Legenda");
			botoes[2].setMnemonic('L');
			botoes[2].addActionListener(this);
			grupo.add(botoes[2]);
			this.add(botoes[2]);
		}
		if (cbConceito.getModel().getSize() == 0) {
			botoes[0].setEnabled(false);
			cbConceito.setEnabled(false);
			botoes[1].setSelected(true);
		}
	}

	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == botoes[0] &&
				botoes[0].isEnabled())
			cbConceito.setEnabled(true);
		else
			cbConceito.setEnabled(false);
	}
	
	
	
}
