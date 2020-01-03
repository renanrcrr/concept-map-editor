package cmeditor.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JScrollPane;
import javax.swing.RepaintManager;

import cmeditor.desenho.AreaDesenho;

public class BarrasRolagem
extends JScrollPane
implements Printable {
	
	private AreaDesenho area;
	
	public BarrasRolagem(AreaDesenho area) {
		super(area);
		this.area = area;
	}

	public int print(Graphics g,
			PageFormat printFormat,
			int page) {
		Dimension pSize = area.getPreferredSize();
		int larg = (int)(printFormat.getWidth());
		int alt = (int)(printFormat.getHeight());
		int cols = (int)Math.max(Math.ceil((double)(pSize.width - 5) / (double)larg), 1);
		int rows = (int)Math.max(Math.ceil((double)(pSize.height - 5)	/ (double)alt), 1);
		if (page < cols * rows) {
			RepaintManager manager = RepaintManager.currentManager(this);
			manager.setDoubleBufferingEnabled(false);
			double oldScale = area.getScale();
			area.setScale(1);
			int dx = (int)((page % cols) * printFormat.getWidth());
			int dy = (int)((page % rows) * printFormat.getHeight());
			g.translate(-dx, -dy);
			g.setClip(dx, dy, (int)(dx + printFormat.getWidth()),
					(int)(dy + printFormat.getHeight()));
			area.paint(g);
			g.translate(dx, dy);
			area.setScale(oldScale);
			manager.setDoubleBufferingEnabled(true);
			return PAGE_EXISTS;
		} else {
			return NO_SUCH_PAGE;
		}
	}
	
	public void imprimir() {
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable(this);
		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (PrinterException e) {
				e.printStackTrace();
			}
		}
	}
	
}
