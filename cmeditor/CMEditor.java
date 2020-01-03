package cmeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JFrame;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import cmeditor.desenho.AreaDesenho;
import cmeditor.gui.Acoes;
import cmeditor.gui.BarraFerramentas;
import cmeditor.gui.BarraMenus;
import cmeditor.gui.dialogo.Dialogos;
import cmeditor.gui.dialogo.PainelPreferencias;

public class CMEditor extends JFrame
implements PropertyChangeListener, WindowListener {
	
	Acoes acoes;
	AreaDesenho areaDesenho;
	BarraFerramentas barraFerramentas;
	BarraMenus barraMenus;
	
	public CMEditor() throws HeadlessException {
		super();
		this.setTitle("CMEditor");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		Dimension dimensao = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(dimensao.width, dimensao.height - 30);
		this.getContentPane().setLayout(new BorderLayout());
		areaDesenho = new AreaDesenho();
		this.getContentPane().add(areaDesenho.pegaBarrasRolagem());
		acoes = new Acoes(areaDesenho);
		areaDesenho.mudaAcoes(acoes);
		barraMenus = new BarraMenus(acoes);
		this.setJMenuBar(barraMenus);
		barraFerramentas = new BarraFerramentas(acoes);
		this.getContentPane().add(barraFerramentas, BorderLayout.PAGE_START);
		this.addWindowListener(this);
		this.areaDesenho.nomeArquivo.addPropertyChangeListener(this);
		PainelPreferencias.carregarPreferencias(areaDesenho);
		ToolTipManager.sharedInstance().registerComponent(areaDesenho);
		ToolTipManager.sharedInstance().setInitialDelay(1500);
		ToolTipManager.sharedInstance().setDismissDelay(20000);
	}
	
	public void propertyChange(PropertyChangeEvent evt) {
		this.setTitle("CMEditor - " +
				areaDesenho.pegaCaminhoArquivo());
	}
	
	public void windowOpened(WindowEvent evt) {
	}
	
	public void windowClosing(WindowEvent evt) {
		if (acoes.sair())
			System.exit(0);
	}
	
	public void windowClosed(WindowEvent evt) {
	}
	
	public void windowIconified(WindowEvent evt) {
	}
	
	public void windowDeiconified(WindowEvent evt) {
	}
	
	public void windowActivated(WindowEvent evt) {
	}
	
	public void windowDeactivated(WindowEvent evt) {
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
					UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		UIManager.put("OptionPane.yesButtonText", "Sim");
		UIManager.put("OptionPane.yesButtonMnemonic",
				String.valueOf(KeyEvent.VK_S));
		UIManager.put("OptionPane.noButtonText", "N\u00e3o");
		UIManager.put("OptionPane.noButtonMnemonic",
				String.valueOf(KeyEvent.VK_N));
		UIManager.put("OptionPane.okButtonText", "OK");
		UIManager.put("OptionPane.okButtonMnemonic",
				String.valueOf(KeyEvent.VK_O));
		UIManager.put("OptionPane.cancelButtonText", "Cancelar");
		UIManager.put("OptionPane.cancelButtonMnemonic",
				String.valueOf(KeyEvent.VK_C));
		UIManager.put("FileChooser.cancelButtonText", "Cancelar");
		UIManager.put("FileChooser.cancelButtonMnemonic",
				String.valueOf(KeyEvent.VK_C));
		CMEditor f = new CMEditor();
		f.setVisible(true);
		f.setEnabled(false);
		if (Dialogos.mostrarDialogoBoasVindas(f.areaDesenho, f.acoes))
			f.setEnabled(true);
		else
			System.exit(0);
	}
	
}
