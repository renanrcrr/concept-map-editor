package cmeditor.gui.dialogo;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cmeditor.desenho.AreaDesenho;

public class PainelPreferencias
extends JPanel
implements ActionListener {
	
	JButton btnCorLinha;
	JButton btnCorBorda;
	JButton btnCorPreenchimento;
	JButton	btnCorLetra;
	JButton btnFonte;
	JComboBox estiloLinha;
	JTextField txCorLinha;
	JTextField txCorBorda;
	JTextField txCorPreenchimento;
	JTextField txCorLetra;
	JTextField txFonte;
	Font fonte;
	public String[] nomesEstilosLinha = {"Normal", "Ortogonal", "Curva"};
	public int[] estilosLinha = {0, 1, 2};
	
	public PainelPreferencias() {
		super();
		construir();
		this.txCorLinha.setBackground(Color.black);
		this.txCorBorda.setBackground(Color.black);
		this.txCorPreenchimento.setBackground(Color.white);
		this.txCorLetra.setBackground(Color.black);
		this.txFonte.setText("Dialog, normal, 12");
		this.fonte = new Font("Dialog", Font.PLAIN, 12);
		this.estiloLinha.setSelectedIndex(0);
	}
	
	public PainelPreferencias(Color corLinha,
			Color corBorda,
			Color corPreenchimento,
			Color corLetra,
			Font fonte,
			int pagina) {
		super();
		construir();
		this.txCorLinha.setBackground(corLinha);
		this.txCorBorda.setBackground(corBorda);
		this.txCorPreenchimento.setBackground(corPreenchimento);
		this.txCorLetra.setBackground(corLetra);
		this.fonte = fonte;
		
		String s = fonte.getName() + ", ";
		switch (fonte.getStyle()) {
			case Font.BOLD: s += "negrito, "; break;
			case Font.ITALIC: s += "it\u00e1lico, "; break;
			default: s += "normal, "; break;
		}
		s += fonte.getSize();
		this.txFonte.setText(s);
		
		
		for (int i = 0; i < this.estilosLinha.length; i++) {
			if (this.estilosLinha[i] == pagina) {
				this.estiloLinha.setSelectedIndex(i);
				break;
			}
		}
	}
	
	public void construir() {
		setLayout(new GridLayout(6, 1));
		JPanel p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p.add(new JLabel("Cor padr\u00e3o de linhas:"));
		txCorLinha = criarCampoTexto(3);
		p.add(txCorLinha);
		btnCorLinha = new JButton("Alterar...");
		btnCorLinha.addActionListener(this);
		p.add(btnCorLinha);
		add(p);
		p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p.add(new JLabel("Estilo padr\u00e3o de linhas:"));
		estiloLinha = new JComboBox(nomesEstilosLinha);
		p.add(estiloLinha);
		add(p);
		p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p.add(new JLabel("Cor padr\u00e3o de bordas:"));
		txCorBorda = criarCampoTexto(3);
		p.add(txCorBorda);
		btnCorBorda = new JButton("Alterar...");
		btnCorBorda.addActionListener(this);
		p.add(btnCorBorda);
		add(p);
		p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p.add(new JLabel("Cor padr\u00e3o de preenchimento:"));
		txCorPreenchimento = criarCampoTexto(3);
		p.add(txCorPreenchimento);
		btnCorPreenchimento = new JButton("Alterar...");
		btnCorPreenchimento.addActionListener(this);
		p.add(btnCorPreenchimento);
		add(p);
		p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p.add(new JLabel("Cor padr\u00e3o de letras:"));
		txCorLetra = criarCampoTexto(3);
		p.add(txCorLetra);
		btnCorLetra = new JButton("Alterar...");
		btnCorLetra.addActionListener(this);
		p.add(btnCorLetra);
		add(p);
		p = new JPanel(new FlowLayout(FlowLayout.LEADING));
		p.add(new JLabel("Fonte padr\u00e3o:"));
		txFonte = criarCampoTexto(15);
		txFonte.setEnabled(true);
		p.add(txFonte);
		btnFonte = new JButton("Alterar...");
		btnFonte.addActionListener(this);
		p.add(btnFonte);
		add(p);
	}
	
	protected JTextField criarCampoTexto(int cols) {
		JTextField campo = new JTextField(cols);
		campo.setEnabled(false);
		campo.setEditable(false);
		return campo;
	}
	
	public static void carregarPreferencias(AreaDesenho area) {
		File arq = new File("preferencias.dat");
		if (arq.exists()) {
			BufferedReader entrada;
			try {
				entrada = new BufferedReader(new InputStreamReader(
						new FileInputStream(arq)));
				String linha = entrada.readLine();
				AreaDesenho.corLinha = new Color(
						Integer.parseInt(linha));
				linha = entrada.readLine();
				AreaDesenho.corBorda = new Color(
						Integer.parseInt(linha));
				linha = entrada.readLine();
				AreaDesenho.corPreenchimento = new Color(
						Integer.parseInt(linha));
				linha = entrada.readLine();
				AreaDesenho.corLetra = new Color(
					Integer.parseInt(linha));
				linha = entrada.readLine();
				String nome = linha;
				linha = entrada.readLine();
				int estilo = Integer.parseInt(linha);
				linha = entrada.readLine();
				int tamanho = Integer.parseInt(linha);
				AreaDesenho.fonte = new Font(nome, estilo, tamanho);
				linha = entrada.readLine();
				AreaDesenho.estiloLinha = Integer.parseInt(linha);
				entrada.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		else {
			AreaDesenho.corLinha = Color.black;
			AreaDesenho.corBorda = Color.black;
			AreaDesenho.corPreenchimento = Color.white;
			AreaDesenho.corLetra = Color.black;
			AreaDesenho.fonte = new Font("Dialog",
					Font.PLAIN,
					12);
			AreaDesenho.estiloLinha = 0;
		}
	}
	
	public void salvarPreferencias() throws IOException {
		File arq = new File("preferencias.dat");
		String stream = "";
		BufferedWriter saida = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(arq)));
		saida.write(String.valueOf(txCorLinha.getBackground().getRGB()));
		saida.newLine();
		saida.write(String.valueOf(txCorBorda.getBackground().getRGB()));
		saida.newLine();
		saida.write(String.valueOf(txCorPreenchimento.getBackground().getRGB()));
		saida.newLine();
		saida.write(String.valueOf(txCorLetra.getBackground().getRGB()));
		saida.newLine();
		saida.write(fonte.getName());
		saida.newLine();
		saida.write(String.valueOf(fonte.getStyle()));
		saida.newLine();
		saida.write(String.valueOf(fonte.getSize()));
		saida.newLine();
		saida.write(String.valueOf(estilosLinha[estiloLinha.getSelectedIndex()]));
		saida.flush();
		saida.close();
	}
	
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == btnCorLinha) {
			Color cor = Dialogos.mostraDialogoCor(this, txCorLinha.getBackground());
			if (cor != null)
				txCorLinha.setBackground(cor);
		}
		else if (evt.getSource() == btnCorBorda) {
			Color cor = Dialogos.mostraDialogoCor(this, txCorBorda.getBackground());
			if (cor != null)
				txCorLinha.setBackground(cor);
		}
		else if (evt.getSource() == btnCorPreenchimento) {
			Color cor = Dialogos.mostraDialogoCor(this, txCorPreenchimento.getBackground());
			if (cor != null)
				txCorLinha.setBackground(cor);
		}
		else if (evt.getSource() == btnCorLetra) {
			Color cor = Dialogos.mostraDialogoCor(this, txCorLetra.getBackground());
			if (cor != null)
				txCorLinha.setBackground(cor);
		}
		else if (evt.getSource() == btnFonte) {
			Font f = Dialogos.mostraDialogoFonte(this, fonte);
			if (f != null) {
				String s = f.getName() + ", ";
				switch (f.getStyle()) {
					case Font.BOLD: s += "negrito, "; break;
					case Font.ITALIC: s += "it\u00e1lico, "; break;
					default: s += "normal, "; break;
				}
				s += f.getSize();
				txFonte.setText(s);
				fonte = f;
			}
		}
	}

}
