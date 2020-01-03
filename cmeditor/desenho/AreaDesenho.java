package cmeditor.desenho;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import javax.swing.JTextField;
import javax.swing.event.UndoableEditEvent;

import org.jgraph.JGraph;
import org.jgraph.event.GraphSelectionEvent;
import org.jgraph.event.GraphSelectionListener;
import org.jgraph.graph.AttributeMap;
import org.jgraph.graph.CellView;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphUndoManager;
import org.jgraph.graph.Port;

import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.Exemplo;
import cmeditor.desenho.elemento.FraseContextualizada;
import cmeditor.desenho.elemento.Vertice;
import cmeditor.desenho.metodo.CriadorViews;
import cmeditor.desenho.metodo.TratamentoManuseio;
import cmeditor.gui.Acoes;
import cmeditor.gui.BarrasRolagem;

public class AreaDesenho
extends JGraph
implements GraphSelectionListener {
	
	public static Color corLinha;
	public static Color corBorda;
	public static Color corPreenchimento;
	public static Color corLetra;
	public static Font fonte;
	public static int estiloLinha;
	Acoes acoes;
	public GraphUndoManager gerenciaDesfazer;
	Vector areaTransferencia;
	BarrasRolagem barrasRolagem;
	public JTextField nomeArquivo;
	String caminhoArquivo;
	Vector conceitosOntologia;
	int tipoMapa;
	
	public AreaDesenho() {
		super(new DefaultGraphModel());
		this.setBackground(Color.WHITE);
		this.setEditable(false);
		this.setAntiAliased(true);
		this.setDisconnectable(false);
		this.setMarqueeHandler(new TratamentoManuseio(this));
		this.getGraphLayoutCache().setHidesDanglingConnections(true);
		this.getGraphLayoutCache().setSelectsAllInsertedCells(true);
		this.getGraphLayoutCache().setFactory(new CriadorViews(this));
		gerenciaDesfazer = new GraphUndoManager() {
			
			public void undoableEditHappened(UndoableEditEvent e) {
				super.undoableEditHappened(e);
				atualizaBotoesDesfazer();
			}
			
		};
		this.getModel().addUndoableEditListener(gerenciaDesfazer);
		areaTransferencia = new Vector();
		barrasRolagem = new BarrasRolagem(this);
		this.nomeArquivo = new JTextField();
		this.conceitosOntologia = new Vector();
		this.addGraphSelectionListener(this);
	}
	
	public void atualizaBotoesDesfazer() {
		acoes.desfazer.setEnabled(
				gerenciaDesfazer.canUndo(getGraphLayoutCache()));
		acoes.refazer.setEnabled(
				gerenciaDesfazer.canRedo(getGraphLayoutCache()));
		acoes.salvar.setEnabled(acoes.desfazer.isEnabled() |
				acoes.refazer.isEnabled());
		acoes.salvarComo.setEnabled(acoes.salvar.isEnabled());
	}
	
	public boolean testaConexaoJaExistente(
			Port origem, Port alvo) {
		boolean retorno = false;
		Object[] arcos = getGraphLayoutCache().getCells(
				false, false, false, true);
		for (int i = 0; i < arcos.length; i++) {
			DefaultEdge arco = (DefaultEdge)arcos[i];
			if (arco.getSource() == origem &&
					arco.getTarget() == alvo) {
				retorno = true;
				break;
			}
		}
		return retorno;
	}
	
	public void mudaAcoes(Acoes acoes) {
		this.acoes = acoes;
	}
	
	public Acoes pegaAcoes() {
		return acoes;
	}
	
	public BarrasRolagem pegaBarrasRolagem() {
		return barrasRolagem;
	}
	
	public void mudaNomeArquivo(String s) {
		nomeArquivo.setText(s);
		nomeArquivo.setFocusable(!nomeArquivo.isFocusable());
	}
	
	public String pegaNomeArquivo() {
		return this.nomeArquivo.getText();
	}
	
	public void mudaCaminhoArquivo(String s) {
		this.caminhoArquivo = s;
	}
	
	public String pegaCaminhoArquivo() {
		return this.caminhoArquivo;
	}
	
	public void mudaTipoMapa(int i) {
		this.tipoMapa = i;
	}
	
	public int pegaTipoMapa() {
		return this.tipoMapa;
	}
	
	public void botaConceitoOntologia(String s) {
		if (!this.conceitosOntologia.contains(s))
			this.conceitosOntologia.add(s);
	}
	
	public void tiraConceitoOntologia(String s) {
		if (this.conceitosOntologia.contains(s))
			this.conceitosOntologia.remove(s);
	}
	
	public Vector pegaConceitosOntologia() {
		return this.conceitosOntologia;
	}
	
	public void limpaConceitosOntologia() {
		conceitosOntologia.removeAllElements();
	}
	
	public void limpaAreaTransferencia() {
		areaTransferencia.removeAllElements();
	}
	
	public void mudaAreaTransferencia(Vector v) {
		areaTransferencia = v;
	}
	
	public Vector pegaAreaTransferencia() {
		return areaTransferencia;
	}
	
	public void updateAutoSize(CellView view) {
		if (view != null && !isEditing()) {
			Rectangle2D bounds = (view.getAttributes() != null) ? GraphConstants
					.getBounds(view.getAttributes())
					: null;
			AttributeMap attrs = getModel().getAttributes(view.getCell());
			if (bounds == null)
				bounds = GraphConstants.getBounds(attrs);
			if (bounds != null) {
				boolean autosize = GraphConstants.isAutoSize(view
						.getAllAttributes());
				boolean resize = GraphConstants.isResize(view
						.getAllAttributes());
				if (autosize || resize) {
					Dimension2D d = getUI().getPreferredSize(this, view);
					bounds.setFrame(bounds.getX(), bounds.getY(),
							d.getWidth() + 15, d.getHeight() + 9);

					snap(bounds);
					if (resize) {
						if (view.getAttributes() != null)
							view.getAttributes().remove(GraphConstants.RESIZE);
						attrs.remove(GraphConstants.RESIZE);
					}
					view.refresh(getModel(), getGraphLayoutCache(), false);
				}
			}
		}
	}
	
	public void valueChanged(GraphSelectionEvent e) {
		boolean selecao = false;
		boolean arcos = false;
		boolean vertices = false;
		boolean conceitos = false;
		if (!isSelectionEmpty()) {
			selecao = true;
			Object[] selecionados = getSelectionCells();
			for (int i = 0; i < selecionados.length; i++) {
				if (!arcos)
					arcos = (selecionados[i] instanceof DefaultEdge);
				if (!vertices)
					vertices = (!(selecionados[i] instanceof DefaultEdge));
				if (!conceitos)
					conceitos = (selecionados[i] instanceof Conceito ||
							selecionados[i] instanceof Exemplo);
			}
		}
		acoes.recortar.setEnabled(vertices);
		acoes.copiar.setEnabled(vertices);
		acoes.excluir.setEnabled(selecao);
		acoes.corLetra.setEnabled(vertices);
		acoes.fonte.setEnabled(vertices);
		acoes.corPreenchimento.setEnabled(conceitos);
		acoes.corBorda.setEnabled(conceitos);
		acoes.corLinha.setEnabled(arcos);
		acoes.estiloLinha.setEnabled(arcos);

	}
	
	public String getToolTipText(MouseEvent evt) {
		Object cell = getFirstCellForLocation(evt.getX(),
				evt.getY());
		if (cell == null) {
			int length = getGraphLayoutCache().getCells(true,
					true,
					true,
					true).length;
			if (length > 0)
				return "<html>Posicione o cursor do mouse sobre um elemento.<br>" +
						"Arraste e solte uma das setas que aparecer\u00e3o,<br>" +
						"para criar novos elementos.</html>";
			else
				return "<html>Clique duas vezes para criar um conceito<br>" +
						"ou outro elemento.</html>";
		}
		if (cell instanceof Vertice) {
			String retorno = "<html><p>";
			if (cell instanceof FraseContextualizada)

				retorno += "Supertipo: " +
						((FraseContextualizada)cell).pegaSupertipo() +
						"</p><p><br>";
			retorno += "Clique duas vezes para editar este elemento." +
					"</p></html>";
			return retorno;
		}
		return null;
	}
	
}
