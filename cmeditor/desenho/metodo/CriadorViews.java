package cmeditor.desenho.metodo;

import java.awt.event.MouseEvent;

import org.jgraph.graph.CellHandle;
import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.EdgeView;
import org.jgraph.graph.GraphContext;
import org.jgraph.graph.PortView;
import org.jgraph.graph.VertexView;

import cmeditor.desenho.AreaDesenho;
import cmeditor.desenho.elemento.Conceito;
import cmeditor.desenho.elemento.SetaContinua;
import cmeditor.desenho.elemento.SetaTracejada;
import cmeditor.desenho.view.ViewConceito;
import cmeditor.desenho.view.ViewPortPadrao;
import cmeditor.desenho.view.ViewSetaContinua;
import cmeditor.desenho.view.ViewSetaTracejada;
import cmeditor.desenho.view.ViewVertice;

public class CriadorViews extends DefaultCellViewFactory {
	
	AreaDesenho area;
	
	public CriadorViews(AreaDesenho area) {
		super();
		this.area = area;
	}
	
	public EdgeView createEdgeView(Object obj) {
		return new EdgeView(obj) {
			
			public CellHandle getHandle(GraphContext context) {
                return new EdgeView.EdgeHandle(this, context) {
                	
                    public boolean isAddPointEvent(MouseEvent evt) {
                        return false;
                    }
                    
                    public boolean isRemovePointEvent(MouseEvent evt) {
                        return false;
                    }
                    
                };
            } 
			
		};
	}
	
	public VertexView createVertexView(Object obj) {
		if (obj instanceof Conceito)
			return new ViewConceito(obj);
		else
			return new ViewVertice(obj);
	}
	
	protected PortView createPortView(Object obj) {
		if (obj instanceof SetaContinua)
			return new ViewSetaContinua(obj);
		else if (obj instanceof SetaTracejada)
			return new ViewSetaTracejada(obj);
		else
			return new ViewPortPadrao(obj);
	}
	
}
