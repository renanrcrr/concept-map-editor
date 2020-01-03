package cmeditor.gui.dialogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PainelCor extends JPanel implements ActionListener {
	
	Vector botoes, cores;
	JTextField txCorEscolhida;

	public PainelCor() {
		super();
		construir();
	}
	
	public PainelCor(Color c) {
		super();
		construir();
		txCorEscolhida.setBackground(c);
	}
	
	protected void construir() {
		this.setLayout(new BorderLayout());
		JPanel p = new JPanel(new GridLayout(5, 8));
		botoes = new Vector();
		cores = new Vector();
		botoes.add(criarBotao(0, 0, 0));
		botoes.add(criarBotao(153, 51, 0));
		botoes.add(criarBotao(51, 51, 0));
		botoes.add(criarBotao(0, 51, 0));
		botoes.add(criarBotao(0, 51, 102));
		botoes.add(criarBotao(0, 0, 128));
		botoes.add(criarBotao(51, 51, 153));
		botoes.add(criarBotao(51, 51, 51));
		botoes.add(criarBotao(128, 0, 0));
		botoes.add(criarBotao(255, 102, 0));
		botoes.add(criarBotao(128, 128, 0));
		botoes.add(criarBotao(0, 128, 0));
		botoes.add(criarBotao(0, 128, 128));
		botoes.add(criarBotao(0, 0, 255));
		botoes.add(criarBotao(102, 102, 153));
		botoes.add(criarBotao(128, 128, 128));
		botoes.add(criarBotao(255, 0, 0));
		botoes.add(criarBotao(255, 153, 0));
		botoes.add(criarBotao(153, 204, 0));
		botoes.add(criarBotao(51, 153, 102));
		botoes.add(criarBotao(51, 204, 204));
		botoes.add(criarBotao(51, 102, 255));
		botoes.add(criarBotao(128, 0, 128));
		botoes.add(criarBotao(150, 150, 150));
		botoes.add(criarBotao(255, 0, 255));
		botoes.add(criarBotao(255, 204, 0));
		botoes.add(criarBotao(255, 255, 0));
		botoes.add(criarBotao(0, 255, 0));
		botoes.add(criarBotao(0, 255, 255));
		botoes.add(criarBotao(0, 204, 255));
		botoes.add(criarBotao(153, 51, 102));
		botoes.add(criarBotao(192, 192, 192));
		botoes.add(criarBotao(255, 153, 204));
		botoes.add(criarBotao(255, 204, 153));
		botoes.add(criarBotao(255, 255, 153));
		botoes.add(criarBotao(204, 255, 204));
		botoes.add(criarBotao(204, 255, 255));
		botoes.add(criarBotao(153, 204, 255));
		botoes.add(criarBotao(204, 153, 255));
		botoes.add(criarBotao(255, 255, 255));
		for (int i = 0; i < botoes.size(); i++) {
			p.add((JButton)botoes.get(i));
		}
		this.add(p, BorderLayout.CENTER);
		p = new JPanel(new FlowLayout(FlowLayout.CENTER));
		txCorEscolhida = new JTextField();
		txCorEscolhida.setPreferredSize(new Dimension(26, 26));
		txCorEscolhida.setEditable(false);
		txCorEscolhida.setBackground(Color.BLACK);
		p.add(new JLabel("Cor escolhida:"));
		p.add(txCorEscolhida);
		this.add(p, BorderLayout.SOUTH);
	}
	
	protected JButton criarBotao(int r, int g, int b) {
		final int rFinal = r, gFinal = g, bFinal = b;
		JButton botao = new JButton() {
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(new Color(rFinal, gFinal, bFinal));
				g.fillRect(4, 4,
						this.getSize().width - 8,
						this.getSize().height - 8);
				g.setColor(new Color(160, 160, 160));
				g.drawRect(4, 4,
						this.getSize().width - 8,
						this.getSize().height - 8);
			}
		};
		botao.setPreferredSize(new Dimension(28, 28));
		cores.add(new Color(r, g, b));
		botao.addActionListener(this);
		return botao;
	}
	
	public void actionPerformed(ActionEvent evt) {
		for (int i = 0; i < cores.size(); i++)
			if (evt.getSource() == botoes.get(i)) {
				txCorEscolhida.setBackground((Color)cores.get(i));
				break;
			}
	}
	
	public Color pegaCorEscolhida() {
		return txCorEscolhida.getBackground();
	}
	
}
