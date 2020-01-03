package cmeditor.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.jgraph.graph.Edge;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.Vertice;
import cmeditor.desenho.metodo.EdicaoElementos;
import cmeditor.desenho.metodo.AreaTransferencia;
import cmeditor.gui.dialogo.Dialogos;
import cmeditor.gui.dialogo.SeletorArquivo;
import cmeditor.io.EscritorXML;
import cmeditor.io.LeitorXML;
import cmeditor.io.ManipuladorRecentes;

public class Acoes {
	
	public Action novo;
	public Action abrir;
	public Action salvar;
	public Action salvarComo;
	public Action exportar;
	public Action imprimir;
	public JMenuItem[] recentes;
	public JMenu menuRecentes;
	public Action sair;
	public Action desfazer;
	public Action refazer;
	public Action recortar;
	public Action copiar;
	public Action colar;
	public Action excluir;
	public Action selecionarTudo;
	public Action preferencias;
	public Action aproximar;
	public Action afastar;
	public Action corLetra;
	public Action fonte;
	public Action corBorda;
	public Action corPreenchimento;
	public Action corLinha;
	public Action estiloLinha;
	public Action leituraDiferenciacoes;
	public Action leituraReconciliacoes;
	public Action leituraNiveisInclusao;
	public Action leituraExemplificacoes;
	public Action ajuda;
	public Action sobre;
	public Vector filaRecentes;
	AreaDesenho area;
	
	public Acoes(AreaDesenho area) {
		construir();
		this.area = area;
	}
	
	public void construir() 
	{
		novo = new AbstractAction("Novo...", new ImageIcon(
				"cmeditor/imagens/New24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				if (salvar.isEnabled()) {
					int i = Dialogos.mostrarDialogoSalvar(area,
							area.pegaNomeArquivo());
					if (i == JOptionPane.YES_OPTION) {
					}
					else if (i == JOptionPane.NO_OPTION)
						if (Dialogos.mostrarDialogoNovoMapa(area)) {
							reiniciarArea();
							reiniciarBotoes();
						}
				}
				else if (Dialogos.mostrarDialogoNovoMapa(area)) {
					reiniciarArea();
					reiniciarBotoes();
				}
			}
		};
		novo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'N', KeyEvent.CTRL_DOWN_MASK));
		novo.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_N));
		novo.putValue(Action.SHORT_DESCRIPTION, "Novo mapa conceitual");
		abrir = new AbstractAction("Abrir...", new ImageIcon(
				"cmeditor/imagens/Open24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				if (salvar.isEnabled()) {
					int i = Dialogos.mostrarDialogoSalvar(area,
							area.pegaNomeArquivo());
					if (i == JOptionPane.YES_OPTION) {
					}
					else if (i == JOptionPane.NO_OPTION) {
						abrir();
					}
				}
				else {
					abrir();
				}
			}
		};
		abrir.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'O', KeyEvent.CTRL_DOWN_MASK));
		abrir.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_A));
		abrir.putValue(Action.SHORT_DESCRIPTION, "Abrir mapa conceitual");
		menuRecentes = new JMenu("Arquivos recentes");
		menuRecentes.setIcon(new ImageIcon("cmeditor/imagens/History24.gif"));
		menuRecentes.setMnemonic('N');
		menuRecentes.setEnabled(false);
		recentes = new JMenuItem[4]; 
		for (int i = 0; i < recentes.length; i++) {
			recentes[i] = new JMenuItem(new ImageIcon(
					"cmeditor/imagens/History24.gif"));
			recentes[i].setMnemonic(String.valueOf(i + 1).charAt(0));
			recentes[i].setVisible(false);
			final int n = i;
			recentes[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					abrirRecente(n);
				}
			});
			menuRecentes.add(recentes[i]);
		}
		salvar = new AbstractAction("Salvar", new ImageIcon(
				"cmeditor/imagens/Save24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				if (area.pegaCaminhoArquivo() == "Sem t\u00edtulo")
					salvarComo();
				else
					salvar(new File(area.pegaCaminhoArquivo()));
			}
		};
		salvar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'S', KeyEvent.CTRL_DOWN_MASK));
		salvar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_S));
		salvar.putValue(Action.SHORT_DESCRIPTION, "Salvar mapa conceitual");
		salvarComo = new AbstractAction("Salvar como...", new ImageIcon(
				"cmeditor/imagens/SaveAs24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				salvarComo();
		
			}
		};
		salvarComo.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_C));
		exportar = new AbstractAction("Exportar...", new ImageIcon(
				"cmeditor/imagens/Export24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				SeletorArquivo seletor = new SeletorArquivo("Exportar");
				int i = seletor.showSaveDialog(area);
				if (i == SeletorArquivo.APPROVE_OPTION) {
					if (seletor.getSelectedFile().exists()) {
						i = JOptionPane.showConfirmDialog(seletor,
								"O arquivo ja existe.\n" +
								"Deseja sobrescreve-lo?",
								"Sobrescrever",
								JOptionPane.YES_NO_OPTION);
						if (i == JOptionPane.YES_OPTION) {
							try {
								BufferedImage img = area.getImage(
										area.getBackground(), 5);
								ImageIO.write(img, "jpg",
										seletor.getSelectedFile());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					else {
						try {
							String arq = seletor.getSelectedFile().getCanonicalPath();
							if (!arq.endsWith(".jpg"))
								arq += ".jpg";
							BufferedImage img = area.getImage(
									area.getBackground(), 5);
							ImageIO.write(img, "jpg", new File(arq));
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		};
		exportar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_E));
		imprimir = new AbstractAction("Imprimir...", new ImageIcon(
				"cmeditor/imagens/Print24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				area.pegaBarrasRolagem().imprimir();
			}
		};
		imprimir.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'P', KeyEvent.CTRL_DOWN_MASK));
		imprimir.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_I));
		imprimir.putValue(Action.SHORT_DESCRIPTION, "Imprimir mapa conceitual");
		sair = new AbstractAction("Sair", new ImageIcon(
				"cmeditor/imagens/Stop24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				if (sair())
					System.exit(0);
			}
		};
		sair.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
		sair.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_R));
		desfazer = new AbstractAction("Desfazer", new ImageIcon(
				"cmeditor/imagens/Undo24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				try {
					area.gerenciaDesfazer.undo(area.getGraphLayoutCache());
				}
				catch (Exception exc) {
				}
				finally {
					area.atualizaBotoesDesfazer();
				}
			}
		};
		desfazer.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'Z', KeyEvent.CTRL_DOWN_MASK));
		desfazer.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_D));
		desfazer.putValue(Action.SHORT_DESCRIPTION, "Desfazer");
		refazer = new AbstractAction("Refazer", new ImageIcon(
				"cmeditor/imagens/Redo24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				try {
					area.gerenciaDesfazer.redo(area.getGraphLayoutCache());
				}
				catch (Exception exc) {
				}
				finally {
					area.atualizaBotoesDesfazer();
				}
			}
		};
		refazer.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'Y', KeyEvent.CTRL_DOWN_MASK));
		refazer.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_R));
		refazer.putValue(Action.SHORT_DESCRIPTION, "Refazer");
		recortar = new AbstractAction("Recortar", new ImageIcon(
				"cmeditor/imagens/Cut24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				AreaTransferencia.recortar(area);
				colar.setEnabled(true);
			}
		};
		recortar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'X', KeyEvent.CTRL_DOWN_MASK));
		recortar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_T));
		copiar = new AbstractAction("Copiar", new ImageIcon(
				"cmeditor/imagens/Copy24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				AreaTransferencia.copiar(area);
				colar.setEnabled(true);
			}
		};
		copiar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'C', KeyEvent.CTRL_DOWN_MASK));
		copiar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_C));
		colar = new AbstractAction("Colar", new ImageIcon(
				"cmeditor/imagens/Paste24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				AreaTransferencia.colar(area);
			}
		};
		colar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'V', KeyEvent.CTRL_DOWN_MASK));
		colar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_O));
		excluir = new AbstractAction("Excluir", new ImageIcon(
				"cmeditor/imagens/Delete24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				EdicaoElementos.excluirSelecionados(area);
			}
		};
		excluir.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_DELETE, 0));
		excluir.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_X));
		selecionarTudo = new AbstractAction("Selecionar tudo", new ImageIcon(
				"cmeditor/imagens/Select24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				area.setSelectionCells(area.getGraphLayoutCache().getCells(
						false, true, false, true));
			}
		};
		selecionarTudo.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'A', KeyEvent.CTRL_DOWN_MASK));
		selecionarTudo.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_S));
		preferencias = new AbstractAction("Prefer\u00eancias...", new ImageIcon(
				"cmeditor/imagens/Preferences24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Dialogos.mostrarDialogoPreferencias(area);
			}
		};
		preferencias.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				'P', KeyEvent.ALT_DOWN_MASK));
		preferencias.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_P));
		aproximar = new AbstractAction("Aproximar", new ImageIcon(
				this.getClass().getClassLoader().getResource("cmeditor/imagens/ZoomIn24.gif"))) {
			public void actionPerformed(ActionEvent evt) {
				area.setScale(area.getScale() + 0.25);
				aproximar.setEnabled(area.getScale() < 3);
				afastar.setEnabled(area.getScale() > .5);
			}
		};
		aproximar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_ADD, KeyEvent.CTRL_DOWN_MASK));
		aproximar.putValue(Action.SHORT_DESCRIPTION, "Aproximar o desenho");
		aproximar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_A));
		afastar = new AbstractAction("Afastar", new ImageIcon(
				this.getClass().getClassLoader().getResource("cmeditor/imagens/ZoomOut24.gif"))) {
			public void actionPerformed(ActionEvent evt) {
				area.setScale(area.getScale() - 0.25);
				aproximar.setEnabled(area.getScale() < 3);
				afastar.setEnabled(area.getScale() > .5);
			}
		};
		afastar.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_SUBTRACT, KeyEvent.CTRL_DOWN_MASK));
		afastar.putValue(Action.SHORT_DESCRIPTION, "Afastar o desenho");
		afastar.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_F));
		corLetra = new AbstractAction("Cor da letra...", new ImageIcon(
				"cmeditor/imagens/Font24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Object[] objetos = area.getSelectionCells();
				for (int i = 0; i < objetos.length; i++)
					if (objetos[i] instanceof Vertice) {
						EdicaoElementos.editarCorLetra(
								objetos[i], area);
						break;
					}
			}
		};
		corLetra.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_C));
		fonte = new AbstractAction("Fonte...", new ImageIcon(
				"cmeditor/imagens/Font24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Object[] objetos = area.getSelectionCells();
				for (int i = 0; i < objetos.length; i++)
					if (objetos[i] instanceof Vertice) {
						EdicaoElementos.editarFonte(
								objetos[i], area);
						break;
					}
			}
		};
		fonte.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_F));
		corBorda = new AbstractAction("Cor da borda...", new ImageIcon(
				"cmeditor/imagens/Pencil24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Object[] objetos = area.getSelectionCells();
				for (int i = 0; i < objetos.length; i++)
					if (objetos[i] instanceof Conceito ||
							objetos[i] instanceof Exemplo) {
						EdicaoElementos.editarCorBorda(
								objetos[i], area);
						break;
					}
			}
		};
		corBorda.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_B));
		corPreenchimento = new AbstractAction("Cor do preenchimento...", new ImageIcon(
				"cmeditor/imagens/Paint24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Object[] objetos = area.getSelectionCells();
				for (int i = 0; i < objetos.length; i++)
					if (objetos[i] instanceof Conceito ||
							objetos[i] instanceof Exemplo) {
						EdicaoElementos.editarCorPreenchimento(
								objetos[i], area);
						break;
					}
			}
		};
		corPreenchimento.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_P));
		corLinha = new AbstractAction("Cor da linha...", new ImageIcon(
				"cmeditor/imagens/Pencil24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Object[] objetos = area.getSelectionCells();
				for (int i = 0; i < objetos.length; i++)
					if (objetos[i] instanceof Edge) {
						EdicaoElementos.editarCorLinha(
								objetos[i], area);
						break;
					}
			}
		};
		corLinha.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_L));
		estiloLinha = new AbstractAction("Estilo de linha...", new ImageIcon(
				"cmeditor/imagens/Pencil24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Object[] objetos = area.getSelectionCells();
				for (int i = 0; i < objetos.length; i++)
					if (objetos[i] instanceof Edge) {
						EdicaoElementos.editarEstiloLinha(
								objetos[i], area);
						break;
					}
			}
		};
		estiloLinha.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_E));
		leituraDiferenciacoes = new AbstractAction("Diferencia\u00e7\u00f5es progressivas...",
				new ImageIcon("cmeditor/imagens/AlignLeft24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Dialogos.mostrarDialogoLeitDiferenciacoes(area);
			}
		};
		leituraDiferenciacoes.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_D));
		leituraReconciliacoes = new AbstractAction("Reconcilia\u00e7\u00f5es integrativas...",
				new ImageIcon("cmeditor/imagens/AlignLeft24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Dialogos.mostrarDialogoLeitReconciliacoes(area);
			}
		};
		leituraReconciliacoes.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_R));
		leituraNiveisInclusao = new AbstractAction("N\u00edveis de inclus\u00e3o...",
				new ImageIcon("cmeditor/imagens/AlignLeft24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Dialogos.mostrarDialogoLeitNiveisInclusao(area);
			}
		};
		leituraNiveisInclusao.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_N));
		leituraExemplificacoes = new AbstractAction("Exemplfica\u00e7\u00f5es...",
				new ImageIcon("cmeditor/imagens/AlignLeft24.gif")) {
			public void actionPerformed(ActionEvent evt) {
				Dialogos.mostrarDialogoLeitExemplificacoes(area);
			}
		};
		leituraExemplificacoes.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_E));
	
		ajuda = new AbstractAction("T\u00f3picos da ajuda...", new ImageIcon(
				this.getClass().getClassLoader().getResource("cmeditor/imagens/Help24.gif"))) {
			public void actionPerformed(ActionEvent evt) {
				ajuda();
			}
		};
		ajuda.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(
				KeyEvent.VK_F1, 0));
		ajuda.putValue(Action.SHORT_DESCRIPTION, "T\u00f3picos da ajuda");
		ajuda.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_T));
		sobre = new AbstractAction("Sobre o CMEditor...", new ImageIcon(
				this.getClass().getClassLoader().getResource("cmeditor/imagens/About24.gif"))) {
			public void actionPerformed(ActionEvent evt) {
				Dialogos.mostrarDialogoSobre(area);
			}
		};
		sobre.putValue(Action.MNEMONIC_KEY, new Integer(
				KeyEvent.VK_S));
	
		filaRecentes = new Vector();
		carregaRecentes();
		salvar.setEnabled(false);
		salvarComo.setEnabled(false);
		desfazer.setEnabled(false);
		refazer.setEnabled(false);
		recortar.setEnabled(false);
		copiar.setEnabled(false);
		colar.setEnabled(false);
		excluir.setEnabled(false);
		corPreenchimento.setEnabled(false);
		corLinha.setEnabled(false);
		estiloLinha.setEnabled(false);
		corBorda.setEnabled(false);
		corLetra.setEnabled(false);
		fonte.setEnabled(false);
	}
	
	private void reiniciarArea() {
		area.getGraphLayoutCache().remove(
				area.getGraphLayoutCache().getCells(false,
						true,
						true,
						true));
		area.limpaAreaTransferencia();
		area.mudaCaminhoArquivo("Sem t\u00edtulo");
		area.mudaNomeArquivo("Sem t\u00edtulo");
	}
	
	private void reiniciarBotoes() {
		area.gerenciaDesfazer.discardAllEdits();
		salvar.setEnabled(false);
		salvarComo.setEnabled(false);
		desfazer.setEnabled(false);
		refazer.setEnabled(false);
		recortar.setEnabled(false);
		copiar.setEnabled(false);
		colar.setEnabled(false);
		excluir.setEnabled(false);
		corPreenchimento.setEnabled(false);
		corLinha.setEnabled(false);
		estiloLinha.setEnabled(false);
		corBorda.setEnabled(false);
		corLetra.setEnabled(false);
		fonte.setEnabled(false);
	}
	
	public boolean sair() {
		boolean retorno = false;
		if (salvar.isEnabled()) {
			int i = Dialogos.mostrarDialogoSalvar(area,
					area.pegaNomeArquivo());
			if (i == JOptionPane.YES_OPTION) {
				if (area.pegaCaminhoArquivo() == "Sem t\u00edtulo") {
					boolean salvou = salvarComo();
					if (salvou)
						retorno =  true;
				}
				else {
					salvar(new File(area.pegaCaminhoArquivo()));
					retorno =  true;
				}
			}
			else if (i == JOptionPane.NO_OPTION)
				retorno =  true;
		}
		else
			retorno = true;
		return retorno;
	}
	
	public boolean abrir() {
		boolean retorno = true;
		SeletorArquivo seletor = new SeletorArquivo("Abrir");
		int i = seletor.showOpenDialog(area);
		if (i == SeletorArquivo.APPROVE_OPTION) {
			reiniciarArea();
			retorno = LeitorXML.lerMapa(seletor.getSelectedFile(),
					area);
			if (retorno == false)
				Dialogos.mostrarDialogoErroAbrir(area);
			else
				try {
					botaRecente(seletor.getSelectedFile().getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
			reiniciarBotoes();
			if (retorno == true)
				salvarComo.setEnabled(true);
		}
		else
			retorno = false;
		return retorno;
	}
	
	public void abrirRecente(int i) {
		reiniciarArea();
		boolean retorno = LeitorXML.lerMapa(new File((String)filaRecentes.get(i)),
				area);
		if (retorno == false) {
			Dialogos.mostrarDialogoErroAbrir(area);
			filaRecentes.remove(i);
			atualizaRecentes();
			ManipuladorRecentes.salvaRecentes(filaRecentes);
		}
		reiniciarBotoes();
		if (retorno == true)
			salvarComo.setEnabled(true);
	}
	
	public void botaRecente(String recente) {
		if (filaRecentes.contains(recente))
			filaRecentes.remove(recente);
		else if (filaRecentes.size() == 4)
			filaRecentes.remove(filaRecentes.size() - 1);
		filaRecentes.add(0, recente);
		ManipuladorRecentes.salvaRecentes(filaRecentes);
		atualizaRecentes();
	}
	
	public void carregaRecentes() {
		filaRecentes = ManipuladorRecentes.carregaRecentes();
		atualizaRecentes();
	}
	
	public void atualizaRecentes() {
		if (filaRecentes.size() == 0) {
			for (int i = 0; i < recentes.length; i++)
				recentes[i].setVisible(false);
			menuRecentes.setEnabled(false);
		}
		else {
			int n = (filaRecentes.size() <= recentes.length)?
					filaRecentes.size():
					recentes.length;
			int i = 0;
			while (i < n) {
				String s = (String)filaRecentes.get(i);
				for (int j = s.length() - 2; j >= 0; j--)
					if (s.charAt(j) == '\\' || s.charAt(j) == '/') {
						s = s.substring(j + 1);
						break;
					}
				recentes[i].setText("" + (i + 1) + " " + s);
				recentes[i].setVisible(true);
				i++;
			}
			while (i < recentes.length) {
				recentes[i].setVisible(false);
				i++;
			}
			menuRecentes.setEnabled(true);
		}
	}
	
	private boolean salvarComo() {
		boolean salvou = false;
		SeletorArquivo seletor = new SeletorArquivo("Salvar");
		int i = seletor.showSaveDialog(area);
		if (i == SeletorArquivo.APPROVE_OPTION) {
			if (seletor.getSelectedFile().exists()) {
				i = JOptionPane.showConfirmDialog(seletor,
						"O arquivo ja existe.\n" +
						"Deseja sobrescreve-lo?",
						"Sobrescrever",
						JOptionPane.YES_NO_OPTION);
				if (i == JOptionPane.YES_OPTION) {
					salvar(seletor.getSelectedFile());
					salvou = true;
				}
			}
			else {
				salvar(seletor.getSelectedFile());
				salvou = true;
			}
		}
		return salvou;
	}
	
	private void salvar(File arquivo) {
		
		try {
			String canonico = arquivo.getCanonicalPath();
			String nome = arquivo.getName();
			if (!canonico.endsWith(".xml"))
				canonico += ".xml";
			if (!nome.endsWith(".xml"))
				nome += ".xml";
			if (EscritorXML.salvarArquivo(canonico, area)) {
				area.mudaCaminhoArquivo(canonico);
				area.mudaNomeArquivo(nome);
				salvar.setEnabled(false);
				botaRecente(canonico);
			}
			else
				Dialogos.mostrarDialogoErroSalvar(area);
		}
		catch (IOException exc) {
			Dialogos.mostrarDialogoErroSalvar(area);
			exc.printStackTrace();
		}
	}
	
	private void ajuda() {
		String osName = System.getProperty("os.name");
		String url = "file:///" +
				System.getProperty("user.dir");
		if (osName.startsWith("Windows"))
			url += "\\cmeditor\\ajuda\\index.html";
		else
			url += "/cmeditor/ajuda/index.html";
		try {
			if (osName.startsWith("Mac OS")) {
				Class fileMgr = Class.forName(
						"com.apple.eio.FileManager");
				Method openURL = fileMgr.getDeclaredMethod("openURL",
						new Class[] {String.class});
				openURL.invoke(null,
						new Object[] {url});
			}
			else if (osName.startsWith("Windows"))
				Runtime.getRuntime().exec(
						"rundll32 url.dll,FileProtocolHandler " + url);
			else {
				String[] browsers = {"firefox",
						"opera",
						"konqueror",
						"epiphany",
						"mozilla",
						"netscape"};
				String browser = null;
				for (int count = 0; count < browsers.length && browser == null; count++)
					if (Runtime.getRuntime().exec(
							new String[] {"which",
									browsers[count]}).waitFor() == 0)
						browser = browsers[count];
				if (browser == null)
					throw new Exception("Navegador Web n\u00e3o encontrado");
				else Runtime.getRuntime().exec(
						new String[] {browser,
								url});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
