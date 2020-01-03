package cmeditor.desenho.metodo;

import java.awt.geom.Point2D;

import org.jgraph.graph.DefaultPort;
import org.jgraph.graph.Port;
import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.ArcoContinuo;
import cmeditor.desenho.elemento.ArcoTracejado;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.FraseEnlace;
import cmeditor.desenho.elemento.SetaContinua;
import cmeditor.desenho.elemento.SetaTracejada;
import cmeditor.desenho.elemento.Vertice;
import cmeditor.gui.dialogo.Dialogos;

public class Conexoes {
	
	public static void conectar(AreaDesenho area, Port origem, Port alvo) {
		if (origem instanceof SetaContinua) {
			SetaContinua sc = (SetaContinua)origem;
			origem = ((Vertice)sc.getParent()).pegaPortPadrao();
			ArcoContinuo arco = new ArcoContinuo();
			if (alvo instanceof SetaContinua ||
					alvo instanceof SetaTracejada) {
				DefaultPort dp = (DefaultPort)alvo;
				alvo = ((Vertice)dp.getParent()).pegaPortPadrao();
			}
			if (area.getModel().acceptsSource(arco, origem) &&
					area.getModel().acceptsTarget(arco, alvo) &&
					!area.testaConexaoJaExistente(origem, alvo)) {
				area.getGraphLayoutCache().insertEdge(arco,
						origem, alvo);
			}
		}
		else if (origem instanceof SetaTracejada) {
			SetaTracejada st = (SetaTracejada)origem;
			origem = ((Vertice)st.getParent()).pegaPortPadrao();
			ArcoTracejado arco = new ArcoTracejado();
			if (alvo instanceof SetaContinua ||
					alvo instanceof SetaTracejada) {
				DefaultPort dp = (DefaultPort)alvo;
				alvo = ((Vertice)dp.getParent()).pegaPortPadrao();
			}
			if (area.getModel().acceptsSource(arco, origem) &&
					area.getModel().acceptsTarget(arco, alvo) &&
					!area.testaConexaoJaExistente(origem, alvo)) {
				area.getGraphLayoutCache().insertEdge(arco, origem, alvo);
			}
		}
	}
	
	public static void conectarAtingirBranco(AreaDesenho area,
			Object objPortOrigem, double x, double y) {
		DefaultPort portOrigem = (DefaultPort)objPortOrigem, portAlvo = null;
		Object objTemp = InsercaoElementos.inserirConceitoExemplo(area,
				x / area.getScale(),
				y / area.getScale());
		if (objTemp != null) {
			area.getGraphLayoutCache().insert(objTemp);
			portAlvo =((Vertice)objTemp).pegaPortPadrao();
			if (portOrigem.getParent() instanceof Conceito) {
				inserirFraseEnlaceEntreElementos(area, portOrigem, portAlvo);
			}
			else
				conectar(area, portOrigem, portAlvo);
		}
	}
	
	public static void conectarAtingirConceitoExemplo(AreaDesenho area,
			Object objPortOrigem, Object objPortAlvo) {
		DefaultPort portOrigem = (DefaultPort)objPortOrigem;
		DefaultPort portAlvo = (DefaultPort)objPortAlvo;
		if (portOrigem.getParent() == portAlvo.getParent())
			Dialogos.mostrarDialogoErroConexao(area);	
		else if (portOrigem.getParent() instanceof Conceito) {
			inserirFraseEnlaceEntreElementos(area, portOrigem, portAlvo);
		}
		else
			conectar(area, portOrigem, portAlvo);
	}
	
	public static void conectarAtingirFraseEnlace(AreaDesenho area,
			Object objPortOrigem, Object objPortAlvo) {
		DefaultPort portOrigem = (DefaultPort)objPortOrigem;
		DefaultPort portAlvo = (DefaultPort)objPortAlvo;
		if (portOrigem.getParent() instanceof Conceito &&
				((portOrigem instanceof SetaContinua &&
				((FraseEnlace)portAlvo.getParent()).testaContinua())
				|| (portOrigem instanceof SetaTracejada &&
				((FraseEnlace)portAlvo.getParent()).testaTracejada())))
			conectar(area, portOrigem, portAlvo);
		else
			Dialogos.mostrarDialogoErroConexao2(area);
	}
	
	private static void inserirFraseEnlaceEntreElementos(AreaDesenho area,
			DefaultPort portOrigem, DefaultPort portAlvo) {
		FraseEnlace fra;
		Point2D medio = calcularPontoMedio(
				area.getCellBounds(((Vertice)portOrigem.getParent()).pegaPortPadrao()).getX(),
				area.getCellBounds(((Vertice)portOrigem.getParent()).pegaPortPadrao()).getY(),
				area.getCellBounds(((Vertice)portAlvo.getParent()).pegaPortPadrao()).getX(),
				area.getCellBounds(((Vertice)portAlvo.getParent()).pegaPortPadrao()).getY());
		if (portOrigem instanceof SetaContinua) {
			fra = (FraseEnlace)InsercaoElementos.inserirFraseEnlace(area,
					FraseEnlace.SETA_CONTINUA,
					medio.getX(), medio.getY());
			area.getGraphLayoutCache().insert(fra);
			conectar(area, fra.pegaSetaContinua(), portAlvo);
		}
		else {
			fra = (FraseEnlace)InsercaoElementos.inserirFraseEnlace(area,
					FraseEnlace.SETA_TRACEJADA,
					medio.getX(), medio.getY());
			area.getGraphLayoutCache().insert(fra);
			conectar(area, fra.pegaSetaTracejada(), portAlvo);
		}
		conectar(area, portOrigem, fra.pegaPortPadrao());
	}
	
	private static Point2D calcularPontoMedio(double x1,
			double y1, double x2, double y2) {
		return new Point2D.Double((x1 + x2) / 2, (y1 + y2) / 2);
	}
	
}
