package cmeditor.gui.dialogo;

import javax.swing.JFileChooser;

import cmeditor.io.FiltroArquivo;

public class SeletorArquivo extends JFileChooser {
	
	FiltroArquivo filtro;
	
	public SeletorArquivo(String titulo) {
		super();
		if (titulo == "Exportar")
			filtro = new FiltroArquivo("jpg", "Imagem JPEG");
		else
			filtro = new FiltroArquivo("xml", "Arquivo XML");
		setFileFilter(filtro);
		setMultiSelectionEnabled(false);
		setDialogTitle(titulo);
		if (titulo == "Abrir" ||
				titulo == "Abrir ontologia")
			setDialogType(OPEN_DIALOG);
		else
			setDialogType(SAVE_DIALOG);
		setApproveButtonText(titulo);
	}
	
}
