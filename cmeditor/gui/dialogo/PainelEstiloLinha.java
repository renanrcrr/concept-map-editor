package cmeditor.gui.dialogo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class PainelEstiloLinha
extends JPanel
implements ActionListener {
	
	public static final int NORMAL = 0;
	public static final int ORTOGONAL = 1;
	public static final int CURVA = 2;
	JRadioButton btNormal;
	JRadioButton btOrtogonal;
	JRadioButton btCurva;
	JLabel lbImagem;
	
	public PainelEstiloLinha() {
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel();
		BoxLayout boxLayout = new BoxLayout(p, BoxLayout.PAGE_AXIS);
		p.setLayout(boxLayout);
		ButtonGroup grupo = new ButtonGroup();
		btNormal = new JRadioButton("Normal");
		btNormal.setMnemonic('N');
		btNormal.addActionListener(this);
		grupo.add(btNormal);
		p.add(btNormal);
		btNormal.setSelected(true);
		btOrtogonal = new JRadioButton("Ortogonal");
		btOrtogonal.setMnemonic('O');
		btOrtogonal.addActionListener(this);
		grupo.add(btOrtogonal);
		p.add(btOrtogonal);
		btCurva = new JRadioButton("Curva");
		btCurva.setMnemonic('C');
		btCurva.addActionListener(this);
		grupo.add(btCurva);
		p.add(btCurva);
		this.add(p, BorderLayout.WEST);
		p = new JPanel();
		lbImagem = new JLabel(new ImageIcon(
				"cmeditor/imagens/StyleNormal.gif"));
		p.add(lbImagem);
		this.add(p, BorderLayout.EAST);
		
	}
	
	public PainelEstiloLinha(int estilo) {
		this();
		switch (estilo) {
			case ORTOGONAL: {
				btOrtogonal.setSelected(true);
				lbImagem.setIcon(new ImageIcon(
						"cmeditor/imagens/StyleOrthogonal.gif"));
				break;
			}
			case CURVA: {
				btCurva.setSelected(true);
				lbImagem.setIcon(new ImageIcon(
						"cmeditor/imagens/StyleCurve.gif"));
				break;
			}
			default: {
				btNormal.setSelected(true);
				lbImagem.setIcon(new ImageIcon(
						"cmeditor/imagens/StyleNormal.gif"));
				break;
			}
		}
	}
	
	public int pegaEstiloEscolhido() {
		if (btOrtogonal.isSelected())
			return ORTOGONAL;
		else if (btCurva.isSelected())
			return CURVA;
		else
			return NORMAL;
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (btNormal.isSelected()) {
			lbImagem.setIcon(new ImageIcon(
					"cmeditor/imagens/StyleNormal.gif"));
		}
		else if (btOrtogonal.isSelected()) {
			lbImagem.setIcon(new ImageIcon(
					"cmeditor/imagens/StyleOrthogonal.gif"));
		}
		else if (btCurva.isSelected()) {
			lbImagem.setIcon(new ImageIcon(
					"cmeditor/imagens/StyleCurve.gif"));
		}
	}

}
