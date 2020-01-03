	
package cmeditor.gui.dialogo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import java.io.IOException;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import javax.swing.text.Position;

import org.jgraph.graph.DefaultGraphModel;

import on_tool.io.LeitorXML2;
import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.ArcoContinuo;
import cmeditor.desenho.elemento.ArcoTracejado;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.metodo.LeituraMapa;
import cmeditor.gui.Acoes;
import cmeditor.io.Mapa;

public class Dialogos {
	
	public static boolean mostrarDialogoBoasVindas(Component c, Acoes a) {
		JPanel painel = new JPanel(new BorderLayout());
		painel.add(new JLabel(new ImageIcon(
				"cmeditor/imagens/Welcome.gif")), BorderLayout.NORTH);
		ButtonGroup grupo = new ButtonGroup();
		JRadioButton[] botoes = new JRadioButton[3];
		botoes[0] = new JRadioButton("Desejo criar um mapa conceitual livre de contexto");
		botoes[0].setMnemonic('L');
		botoes[0].setSelected(true);
		botoes[1] = new JRadioButton("Desejo criar um mapa conceitual contextualizado");
		botoes[1].setMnemonic('C');
		botoes[2] = new JRadioButton("Desejo abrir um mapa conceitual existente");
		botoes[2].setMnemonic('A');
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Selecione uma op\u00e7\u00e3o"));
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		for (int i = 0; i < botoes.length; i++) {
			grupo.add(botoes[i]);
			p.add(botoes[i]);
		}
		painel.add(p, BorderLayout.SOUTH);
		if (JOptionPane.showOptionDialog(c, painel, "Bem-vindo ao CMEditor",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
				null, new Object[] {"OK", "Sair do CMEditor"}, null)
				== JOptionPane.OK_OPTION) {
			if (botoes[0].isSelected()) {
				((AreaDesenho)c).mudaCaminhoArquivo("Sem t\u00edtulo");
				((AreaDesenho)c).mudaNomeArquivo("Sem t\u00edtulo");
				return true;
			}
			else if (botoes[1].isSelected()) {
				SeletorArquivo seletor = new SeletorArquivo("Selecionar ontologia");
				int i = seletor.showOpenDialog(c);
				if (i == SeletorArquivo.APPROVE_OPTION) {
					boolean b = LeitorXML2.lerMapa(seletor.getSelectedFile(),
							(AreaDesenho)c);
					if (b == false) {
						Dialogos.mostrarDialogoErroAbrir(c);
						return false;
					}
					else {
						((AreaDesenho)c).mudaTipoMapa(Mapa.CONTEXTUALIZADO);
						((AreaDesenho)c).mudaCaminhoArquivo("Sem t\u00edtulo");
						((AreaDesenho)c).mudaNomeArquivo("Sem t\u00edtulo");
						return true;
					}
				}
				else
					return false;
			}
			else if (botoes[2].isSelected())
				return a.abrir();
			else
				return false;
		}
		else
			return false;
	}
	
	public static boolean mostrarDialogoNovoMapa(Component c) {
		Object[] possibilidades = {"Livre de contexto",
				"Contextualizado"};
		int n = JOptionPane.showOptionDialog(c,
				"Clique no tipo de mapa conceitual que deseja criar.\n" +
				"Se voc\u00ea fechar este di\u00e1logo, ser\u00e1 criado\n" +
				"um mapa conceitual livre de contexto.",
				"Novo mapa conceitual",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				possibilidades,
				possibilidades[0]);
		if (n == JOptionPane.YES_OPTION) {
			((AreaDesenho)c).mudaTipoMapa(Mapa.LIVRE_CONTEXTO);
			return true;
		}
		else if (n == JOptionPane.NO_OPTION) {
			((AreaDesenho)c).limpaConceitosOntologia();
			SeletorArquivo seletor = new SeletorArquivo("Selecionar ontologia");
			int i = seletor.showOpenDialog(c);
			if (i == SeletorArquivo.APPROVE_OPTION) {
				boolean retorno = LeitorXML2.lerMapa(seletor.getSelectedFile(),
						(AreaDesenho)c);
				if (retorno == false) {
					Dialogos.mostrarDialogoErroAbrir(c);
					return false;
				}
				else {
					((AreaDesenho)c).mudaTipoMapa(Mapa.CONTEXTUALIZADO);
					return true;
				}
			}
			else
				return false;
		}
		else {
			((AreaDesenho)c).mudaTipoMapa(Mapa.LIVRE_CONTEXTO);
			JOptionPane.showMessageDialog(c,
					"Foi criado um mapa conceitual livre de contexto.");
			return true;
		}
	}
	
	
	public static int mostrarDialogoSalvar(Component c,
			String arquivo) {
		String mensagem = "Deseja salvar as altera\u00e7\u00f5oes em \""
				+ arquivo + "\"?";
		return JOptionPane.showConfirmDialog(c,
				mensagem,
				"Confirma\u00e7\u00e3o",
				JOptionPane.YES_NO_CANCEL_OPTION);
	}
	
	public static int mostraDialogoInsercaoElemento(Component c,
			boolean inserirLegenda) {
		ButtonGroup grupo = new ButtonGroup();
		JRadioButton[] botoes = new JRadioButton[3];
		botoes[0] = new JRadioButton("Conceito");
		botoes[0].setMnemonic('C');
		botoes[0].setSelected(true);
		botoes[1] = new JRadioButton("Exemplo");
		botoes[1].setMnemonic('E');
		if (inserirLegenda) {
			botoes[2] = new JRadioButton("Legenda");
			botoes[2].setMnemonic('L');
		}
		JPanel p = new JPanel();
		p.setBorder(BorderFactory.createTitledBorder(
				"Objeto a ser inserido:"));
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		int limite = (inserirLegenda) ? botoes.length: (botoes.length - 1);
		for (int i = 0; i < limite; i++) {
			grupo.add(botoes[i]);
			p.add(botoes[i]);
		}
		int retorno = -1;
		if (JOptionPane.showConfirmDialog(c, p,
				"Inser\u00e7\u00e3o de elemento",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
			for (int i = 0; i < botoes.length; i++)
				if (botoes[i].isSelected()) {
					retorno = i;
					break;
				}
		return retorno;
	}
	
	public static Object mostraDialogoInsercaoContextualizado(AreaDesenho area,
			boolean inserirLegenda) {
		PainelElementoContextualizado p = new PainelElementoContextualizado(
				area.pegaConceitosOntologia(), inserirLegenda);
		if (JOptionPane.showConfirmDialog(area, p,
				"Inser\u00e7\u00e3o de elemento",
				JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
			if (p.botoes[0].isSelected())
				return (String)p.cbConceito.getSelectedItem();
			else if (p.botoes[1].isSelected())
				return new Integer(1);
			else if (p.botoes[2].isSelected())
				return new Integer(2);
		}
		return null;
	}
	
	public static String mostraDialogoEdicaoRotulo(Component c,
			String s) {
		final JTextArea txRotulo = new JTextArea(5, 10);
		txRotulo.setDocument(new PlainDocument() {
			
			public void insertString(int offset,
					String str,
					AttributeSet attr)
			throws BadLocationException {
				if (str == null)
					return;
				String tmp = "";
				for (int i = 0; i < str.length(); i++)
					if (str.charAt(i) != '<' &&
							str.charAt(i) != '>')
						tmp += str.charAt(i);
				str = tmp;
				super.insertString(offset, str, attr);
			}
			
		});
	
		txRotulo.addHierarchyListener(new HierarchyListener() {
			
			public void hierarchyChanged(HierarchyEvent evt) {
				if ((evt.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
					if (txRotulo.isShowing()) {
						SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								txRotulo.requestFocus();
							}
						});
					}
				}
			}
			
		});
		txRotulo.append(s);
		txRotulo.selectAll();
		JScrollPane pane = new JScrollPane(txRotulo);
		int resposta = JOptionPane.showOptionDialog(
				c,
				new Object[] {"Escreva o r\u00f3tulo do elemento:", pane},
				"Edi\u00e7\u00e3o de r\u00f3tulo",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null, null, null);
		if (resposta == JOptionPane.OK_OPTION)
			return txRotulo.getText();
		else
			return null;
	}
	
	public static String mostraDialogoConceitoContextualizado(Component c,
			Object[] conceitos,
			Object valorInicial) {
		return (String)JOptionPane.showInputDialog(c,
				"Escolha o r\u00f3tulo do conceito:",
                "Edi\u00e7\u00e3o de conceito",
                JOptionPane.QUESTION_MESSAGE,
                null, conceitos, valorInicial);
	}
	
	public static Vector mostrarDialogoFraseContextualizada(Component c,
			FraseContextualizada fc) {
		Vector retorno = new Vector();
		DialogoTaxonomia d = new DialogoTaxonomia(
				"cmeditor/io/taxonomia.xml");
		if (d.arvTaxonomia.testaESelecionaSupertipo(fc.pegaSupertipo())) {
	
			String frase = (String)fc.getUserObject();
	
			int pos = d.lstFrases.getNextMatch(frase,
					0,
					Position.Bias.Forward);
	
			d.lstFrases.setSelectedIndex(pos);
		}
		boolean acerto = true;
		int i = 0;
		do {
			i = JOptionPane.showConfirmDialog(c,
					d,
					"Editar frase de liga\u00e7\u00e3o",
					JOptionPane.OK_CANCEL_OPTION);
	
			if (i == JOptionPane.OK_OPTION &&
					d.lstFrases.getSelectedValues().length == 0) {
				JOptionPane.showMessageDialog(null,
						"Selecione uma frase de liga\u00e7\u00e3o",
						"Erro",
						JOptionPane.ERROR_MESSAGE);
				acerto = false;
			}
			else
				acerto = true;
		} while (!acerto);
		if (i == JOptionPane.OK_OPTION) {
			retorno.add(d.arvTaxonomia.pegaSupertipoComoString());
			Object[] frases = d.lstFrases.getSelectedValues();
			for (i = 0; i < frases.length; i++) {
				retorno.add((String)frases[i]);
			}
			return retorno;
		}
		return null;
	}
	
	public static Color mostraDialogoCor(Component c,
			Color cor) {
		PainelCor p = new PainelCor(cor);
		int resposta = JOptionPane.showConfirmDialog(c,
				new Object[] {"Selecione uma cor e clique em OK:", p},
				"Edi\u00e7\u00e3o de cor",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null);
		if (resposta == JOptionPane.OK_OPTION)
			return p.pegaCorEscolhida();
		else
			return null;
	}
	
	
	public static Font mostraDialogoFonte(Component c,
			Font fonte) {
		PainelFonte p = new PainelFonte(fonte);
		int resposta = JOptionPane.showConfirmDialog(
				c,
				new Object[] {"", p},
				"Edi\u00e7\u00e3o de fonte",
				JOptionPane.OK_CANCEL_OPTION,
				 JOptionPane.PLAIN_MESSAGE,
				 null);
		if (resposta == JOptionPane.OK_OPTION)
			return p.pegaFonte();
		else
			return null;
	}
	
	public static int mostraDialogoEstiloLinha(Component c,
			int estilo) {
		PainelEstiloLinha p = new PainelEstiloLinha(estilo);
		int i = JOptionPane.showConfirmDialog(c,
				new Object[] {"", p},
				"Edi\u00e7\u00e3o de estilo de linha",
				JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null);
		if (i == JOptionPane.OK_OPTION)
			return p.pegaEstiloEscolhido();
		else
			return 99;
	}
	
	public static void mostrarDialogoPreferencias(AreaDesenho area) {
		PainelPreferencias p = new PainelPreferencias(AreaDesenho.corLinha,
				AreaDesenho.corBorda,
				AreaDesenho.corPreenchimento,
				AreaDesenho.corLetra,
				AreaDesenho.fonte,
				AreaDesenho.estiloLinha);
		int i = JOptionPane.showConfirmDialog(area, p, "Prefer\u00eancias",
				JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
		if (i == JOptionPane.OK_OPTION) {
			try {
				p.salvarPreferencias();
			} catch (IOException e) {
				e.printStackTrace();
			}
			AreaDesenho.corLinha = p.txCorLinha.getBackground();
			AreaDesenho.corBorda = p.txCorBorda.getBackground();
			AreaDesenho.corPreenchimento = p.txCorPreenchimento.getBackground();
			AreaDesenho.corLetra = p.txCorLetra.getBackground();
			AreaDesenho.fonte = p.txFonte.getFont();
			AreaDesenho.estiloLinha = p.estilosLinha[p.estiloLinha.getSelectedIndex()];
		}
	}
	
	public static void mostrarDialogoLeitDiferenciacoes(AreaDesenho area) {
		Object[] elementos = area.getGraphLayoutCache().getCells(false,
				true,
				false,
				true);
		if (elementos.length < 1)
			JOptionPane.showMessageDialog(area,
					"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
					"Seu mapa conceitual n\u00e3o possui elementos suficientes.",
					"Erro",
					JOptionPane.ERROR_MESSAGE);
		else {
			boolean contemDiferenciacoes = false;
			for (int i = 0; i < elementos.length; i++) {
				if (elementos[i] instanceof ArcoContinuo)
					contemDiferenciacoes = true;
			}
			if (!contemDiferenciacoes)
				JOptionPane.showMessageDialog(area,
						"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
						"Seu mapa conceitual n\u00e3o cont\u00e9m diferencia\u00e7\u00f5es progressivas.",
						"Erro",
						JOptionPane.ERROR_MESSAGE);
			else {
				int indiceRaiz = 0;
				boolean achouRaiz = false, maisDeUmaRaiz = false;
				for (int i = 0; i < elementos.length; i++) {
					if (elementos[i] instanceof Conceito) {
						if (DefaultGraphModel.getIncomingEdges(area.getModel(),
								elementos[i]).length == 0) {
							if (achouRaiz == false) {
								indiceRaiz = i;
								achouRaiz = true;
							}
							else {
								maisDeUmaRaiz = true;
								break;
							}
						}
					}
				}
				if (maisDeUmaRaiz) {
					JOptionPane.showMessageDialog(area,
							"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
							"Seu mapa conceitual cont\u00e9m mais de uma raiz.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					JTextArea areaTexto = new JTextArea(18, 46);
					areaTexto.setEditable(false);
					areaTexto.append(LeituraMapa.pegaDiferenciacoesProgressivas(area,
							(Conceito)elementos[indiceRaiz]));
					JPanel p = new JPanel();
					p.add(new JScrollPane(areaTexto));
					JOptionPane.showMessageDialog(area,
							p,
							"Leitura de proposi\u00e7\u00f5es",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	public static void mostrarDialogoLeitReconciliacoes(AreaDesenho area) {
		Object[] elementos = area.getGraphLayoutCache().getCells(false,
				true,
				false,
				true);
		if (elementos.length < 1)
			JOptionPane.showMessageDialog(area,
					"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
					"Seu mapa conceitual n\u00e3o possui elementos suficientes.",
					"Erro",
					JOptionPane.ERROR_MESSAGE);
		else {
			boolean contemDiferenciacoes = false;
			for (int i = 0; i < elementos.length; i++) {
				if (elementos[i] instanceof ArcoTracejado)
					contemDiferenciacoes = true;
			}
			if (!contemDiferenciacoes)
				JOptionPane.showMessageDialog(area,
						"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
						"Seu mapa conceitual n\u00e3o cont\u00e9m reconcilia\u00e7\u00f5es integrativas.",
						"Erro",
						JOptionPane.ERROR_MESSAGE);
			else {
				int indiceRaiz = 0;
				boolean achouRaiz = false, maisDeUmaRaiz = false;
				for (int i = 0; i < elementos.length; i++) {
					if (elementos[i] instanceof Conceito) {
						if (DefaultGraphModel.getIncomingEdges(area.getModel(),
								elementos[i]).length == 0) {
							if (achouRaiz == false) {
								indiceRaiz = i;
								achouRaiz = true;
							}
							else {
								maisDeUmaRaiz = true;
								break;
							}
						}
					}
				}
				if (maisDeUmaRaiz) {
					JOptionPane.showMessageDialog(area,
							"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
							"Seu mapa conceitual cont\u00e9m mais de uma raiz.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					JTextArea areaTexto = new JTextArea(18, 46);
					areaTexto.setEditable(false);
					areaTexto.append(LeituraMapa.pegaReconciliacoesIntegrativas(area,
							(Conceito)elementos[indiceRaiz]));
					JPanel p = new JPanel();
					p.add(new JScrollPane(areaTexto));
					JOptionPane.showMessageDialog(area,
							p,
							"Leitura de proposi\u00e7\u00f5es",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	public static void mostrarDialogoLeitNiveisInclusao(AreaDesenho area) {
		Object[] elementos = area.getGraphLayoutCache().getCells(false,
				true,
				false,
				true);
		if (elementos.length < 1)
			JOptionPane.showMessageDialog(area,
					"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
					"Seu mapa conceitual n\u00e3o possui elementos suficientes.",
					"Erro",
					JOptionPane.ERROR_MESSAGE);
		else {
			boolean contemDiferenciacoes = false;
			for (int i = 0; i < elementos.length; i++) {
				if (elementos[i] instanceof ArcoContinuo)
					contemDiferenciacoes = true;
			}
			if (!contemDiferenciacoes)
				JOptionPane.showMessageDialog(area,
						"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
						"Seu mapa conceitual n\u00e3o cont\u00e9m diferencia\u00e7\u00f5es progressivas.",
						"Erro",
						JOptionPane.ERROR_MESSAGE);
			else {
				int indiceRaiz = 0;
				boolean achouRaiz = false, maisDeUmaRaiz = false;
				for (int i = 0; i < elementos.length; i++) {
					if (elementos[i] instanceof Conceito) {
						if (DefaultGraphModel.getIncomingEdges(area.getModel(),
								elementos[i]).length == 0) {
							if (achouRaiz == false) {
								indiceRaiz = i;
								achouRaiz = true;
							}
							else {
								maisDeUmaRaiz = true;
								break;
							}
						}
					}
				}
				if (maisDeUmaRaiz) {
					JOptionPane.showMessageDialog(area,
							"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
							"Seu mapa conceitual cont\u00e9m mais de uma raiz.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					JTextArea areaTexto = new JTextArea(18, 46);
					areaTexto.setEditable(false);
					areaTexto.append(LeituraMapa.pegaNiveisInclusao(area,
							(Conceito)elementos[indiceRaiz]));
					JPanel p = new JPanel();
					p.add(new JScrollPane(areaTexto));
						JOptionPane.showMessageDialog(area,
							p,
							"Leitura de n\u00edveis de inclus\u00e3o",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	public static void mostrarDialogoLeitExemplificacoes(AreaDesenho area) {
		Object[] elementos = area.getGraphLayoutCache().getCells(false,
				true,
				false,
				false);
		if (elementos.length < 1)
			JOptionPane.showMessageDialog(area,
					"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
					"Seu mapa conceitual n\u00e3o possui elementos suficientes.",
					"Erro",
					JOptionPane.ERROR_MESSAGE);
		else {
			boolean contemExemplos = false;
			for (int i = 0; i < elementos.length; i++) {
				if (elementos[i] instanceof Exemplo)
					contemExemplos = true;
			}
			if (!contemExemplos)
				JOptionPane.showMessageDialog(area,
						"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
						"Seu mapa conceitual n\u00e3o cont\u00e9m exemplos.",
						"Erro",
						JOptionPane.ERROR_MESSAGE);
			else {
				int indiceRaiz = 0;
				boolean achouRaiz = false, maisDeUmaRaiz = false;
				for (int i = 0; i < elementos.length; i++) {
					if (elementos[i] instanceof Conceito) {
						if (DefaultGraphModel.getIncomingEdges(area.getModel(),
								elementos[i]).length == 0) {
							if (achouRaiz == false) {
								indiceRaiz = i;
								achouRaiz = true;
							}
							else {
								maisDeUmaRaiz = true;
								break;
							}
						}
					}
				}
				if (maisDeUmaRaiz) {
					JOptionPane.showMessageDialog(area,
							"N\u00e3o foi poss\u00edvel executar a leitura.\n" +
							"Seu mapa conceitual cont\u00e9m mais de uma raiz.",
							"Erro",
							JOptionPane.ERROR_MESSAGE);
				}
				else {
					JTextArea areaTexto = new JTextArea(18, 46);
					areaTexto.setEditable(false);
					areaTexto.append(LeituraMapa.pegaExemplificacoes(area,
							(Conceito)elementos[indiceRaiz]));
					JPanel p = new JPanel();
					p.add(new JScrollPane(areaTexto));
					JOptionPane.showMessageDialog(area,
							p,
							"Leitura de exemplos",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}
	
	public static void mostrarDialogoSobre(Component c) {
		JPanel p = new JPanel();
		JLabel l = new JLabel(new ImageIcon("cmeditor/imagens/Welcome.gif"));
		l.setText(DialogoSobre.pegaMensagem());
		l.setVerticalTextPosition(JLabel.BOTTOM);
		l.setHorizontalTextPosition(JLabel.CENTER);
		p.add(l);
		JOptionPane.showMessageDialog(c, p, "Sobre o CMEditor",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void mostrarDialogoErroAusenciaConceitos(Component c) {
		JOptionPane.showMessageDialog(c,
				"N\u00e3o h\u00e1 mais conceitos. Exclua um conceito\n" +
				"do mapa conceitual para poder inserir um novo.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostrarDialogoErroConexao(Component c) {
		JOptionPane.showMessageDialog(c,
				"Uma linha (arco) n\u00e3o pode ligar um elemento a ele mesmo.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostrarDialogoErroConexao2(Component c) {
		JOptionPane.showMessageDialog(c,
				"Um conceito deve se ligar a uma frase de liga\u00e7\u00e3o\n" +
				"com o mesmo tipo de linha (arco).",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostrarDialogoErroAbrir(Component c) {
		JOptionPane.showMessageDialog(c,
				"N\u00e3o foi poss\u00edvel abrir este arquivo.\n" +
				"Ele \u00e9 inv\u00e1lido ou est\u00e1 corrompido.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
	public static void mostrarDialogoErroSalvar(Component c) {
		JOptionPane.showMessageDialog(c,
				"O arquivo n\u00e3o foi salvo.",
				"Erro", JOptionPane.ERROR_MESSAGE);
	}
	
}
