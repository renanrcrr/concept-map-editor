package cmeditor.desenho.metodo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingUtilities;

import org.jgraph.graph.BasicMarqueeHandler;
import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Edge;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.PortView;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.FraseEnlace;
import cmeditor.desenho.elemento.Seta;
import cmeditor.desenho.elemento.SetaContinua;
import cmeditor.desenho.elemento.SetaTracejada;
import cmeditor.gui.MenuPopup;
import cmeditor.io.Mapa;

public class TratamentoManuseio extends BasicMarqueeHandler {
	
	AreaDesenho area;
	protected Point2D pontoInicial;
	protected Point2D pontoAtual;
	protected PortView portObjetivo;
	protected PortView portPrimeiro;
	
	public TratamentoManuseio(AreaDesenho area) {
		super();
		this.area = area;
	}
	
	public boolean isForceMarqueeEvent(MouseEvent e) {
		if (e.isShiftDown())
			return false;
		if (e.getClickCount() == 2 &&
				e.getButton() == MouseEvent.BUTTON1 &&
				area.getFirstCellForLocation(e.getX(), e.getY()) == null)
			return true;
		if (e.getClickCount() == 2 &&
				e.getButton() == MouseEvent.BUTTON1 &&
				area.getSelectionCell() != null)
			return true;
		if (e.getClickCount() == 1 &&
				SwingUtilities.isRightMouseButton(e))
			return true;
		portObjetivo = pegaPortOrigem(e.getPoint());
		if (this.testaMouseSobrePortsValidos(e.getPoint()))
			return true;
		return super.isForceMarqueeEvent(e);
	}
	
	public void mousePressed(final MouseEvent e) {
		if (portObjetivo != null && (portObjetivo.getCell() instanceof SetaContinua
				|| portObjetivo.getCell() instanceof SetaTracejada)) {
			
			pontoInicial = area.toScreen(portObjetivo.getLocation());
			
			portPrimeiro = portObjetivo;
		} else {
			super.mousePressed(e);
		}
	}

	public void mouseDragged(MouseEvent e) {
		if (pontoInicial != null) {
			Graphics g = area.getGraphics();
			PortView newPort = pegaPortAlvo(e.getPoint());
			if (newPort == null || newPort != portObjetivo) {
				pintaConector(Color.black, area.getBackground(), g);
				portObjetivo = newPort;
				if (portObjetivo != null)
					pontoAtual = area.toScreen(portObjetivo.getLocation());
				else
					pontoAtual = area.snap(e.getPoint());
				pintaConector(area.getBackground(), Color.black, g);
			}
		}
		super.mouseDragged(e);
	}

	public PortView pegaPortOrigem(Point2D point) {
		area.setJumpToDefaultPort(false);
		PortView result;
		try {
			result = area.getPortViewAt(point.getX(), point.getY());
		} finally {
			area.setJumpToDefaultPort(true);
		}
		return result;
	}

	protected PortView pegaPortAlvo(Point2D point) {
		return area.getPortViewAt(point.getX(), point.getY());
	}
	
	public void mouseReleased(MouseEvent e) {
		if (e.getClickCount() == 2 &&
				e.getButton() == MouseEvent.BUTTON1 &&
				area.getFirstCellForLocation(e.getX(), e.getY()) == null) {
			InsercaoElementos.inserirConceitoExemploLegenda(area,
					e.getX(),
					e.getY());
		}
		else if (e.getClickCount() == 2 &&
				e.getButton() == MouseEvent.BUTTON1 &&
				area.getSelectionCell() != null &&
				!(area.getSelectionCell() instanceof Edge)) {
			if (area.getSelectionCell() instanceof Conceito
					&& area.pegaTipoMapa() == Mapa.CONTEXTUALIZADO)
				EdicaoElementos.editarConceitoContextualizado(
						area.getSelectionCell(), area);
			else if (area.getSelectionCell() instanceof FraseContextualizada)
				EdicaoElementos.editarFraseContextualizada(
						(FraseContextualizada)area.getSelectionCell(), area);
			else
				EdicaoElementos.editarRotulo(area.getSelectionCell(),
						area);
		}
		else if (e.getClickCount() == 1 &&
				SwingUtilities.isRightMouseButton(e)) {
			MenuPopup popup = new MenuPopup(area.pegaAcoes());
			if (popup.getComponentCount() > 0)
				popup.show(area, e.getX(), e.getY());
		}
		else if (e != null && portObjetivo == null && portPrimeiro != null) {
			Conexoes.conectarAtingirBranco(area,
					portPrimeiro.getCell(), e.getX(), e.getY());
			e.consume();
		}
		else if (e != null && portObjetivo != null && portPrimeiro != null) {
			DefaultPort dp = (DefaultPort)portObjetivo.getCell();
			if (dp.getParent() instanceof Conceito ||
					dp.getParent() instanceof Exemplo)
				Conexoes.conectarAtingirConceitoExemplo(area,
						portPrimeiro.getCell(), portObjetivo.getCell());
			else
				Conexoes.conectarAtingirFraseEnlace(area,
						portPrimeiro.getCell(), portObjetivo.getCell());
			e.consume();
		}
		else
			area.repaint();
		portPrimeiro = portObjetivo = null;
		pontoInicial = pontoAtual = null;
		super.mouseReleased(e);
	}
	
	public void mouseMoved(MouseEvent e) {
		if (testaMouseSobreElementosVisiveis(
				area.getFirstCellForLocation(e.getX(), e.getY()))) {
			area.setPortsVisible(true);
			if (area.getFirstCellForLocation(e.getX(), e.getY())
					instanceof Conceito) {
					((Conceito)area.getFirstCellForLocation(
							e.getX(), e.getY())).pegaSetaContinua().mudaVisivel(
							true);
					((Conceito)area.getFirstCellForLocation(
							e.getX(), e.getY())).pegaSetaTracejada().mudaVisivel(
							true);
			}
			else if (area.getFirstCellForLocation(e.getX(), e.getY())
					instanceof FraseEnlace) {
				if (((FraseEnlace)area.getFirstCellForLocation(
						e.getX(), e.getY())).testaContinua())
					((FraseEnlace)area.getFirstCellForLocation(
							e.getX(), e.getY())).pegaSetaContinua().mudaVisivel(
							true);
				else if (((FraseEnlace)area.getFirstCellForLocation(
						e.getX(), e.getY())).testaTracejada())
					((FraseEnlace)area.getFirstCellForLocation(
							e.getX(), e.getY())).pegaSetaTracejada().mudaVisivel(
							true);
			}
		}
		else if (!(testaMouseSobreElementosVisiveis(
				area.getFirstCellForLocation(e.getX(), e.getY())))){
			area.setPortsVisible(false);
			Object[] ports = area.getGraphLayoutCache().getCells(
					false, false, true, false);
			for (int i = 0; i < ports.length; i++)
				if (ports[i] instanceof Seta) {
					double posX = area.getCellBounds(ports[i]).getX();
					double posY = area.getCellBounds(ports[i]).getY();
					((Seta)ports[i]).mudaVisivel(false);
				}
		} else
			super.mouseMoved(e);
		if (testaMouseSobrePortsValidos(e.getPoint())
				&& area.isPortsVisible()) {	
			area.setCursor(new Cursor(Cursor.HAND_CURSOR));
			e.consume();
		} else
			super.mouseMoved(e);
	}

	protected void pintaConector(Color fg, Color bg, Graphics g) {
		g.setColor(fg);
		g.setXORMode(bg);
		pintaPort(area.getGraphics());
		if (portPrimeiro != null && pontoInicial != null && pontoAtual != null)
			g.drawLine((int) pontoInicial.getX(), (int) pontoInicial.getY(),
					(int) pontoAtual.getX(), (int) pontoAtual.getY());
	}

	protected void pintaPort(Graphics g) {
		if (portObjetivo != null) {
			boolean o = (GraphConstants.getOffset(portObjetivo.getAllAttributes()) != null);
			Rectangle2D r = (o) ? portObjetivo.getBounds() : portObjetivo.getParentView()
					.getBounds();
			r = area.toScreen((Rectangle2D) r.clone());
			r.setFrame(r.getX() - 3, r.getY() - 3, r.getWidth() + 6, r
					.getHeight() + 6);
			area.getUI().paintCell(g, portObjetivo, r, true);
		}
	}
	
	protected boolean testaMouseSobreElementosVisiveis(Object obj) {
		return (obj instanceof Conceito ||
				obj instanceof FraseEnlace);
	}
	
	protected boolean testaMouseSobrePortsValidos(Point2D p) {
		return (pegaPortOrigem(p) != null && 
				pegaPortOrigem(p).getCell() instanceof Seta);
	}
	
}
