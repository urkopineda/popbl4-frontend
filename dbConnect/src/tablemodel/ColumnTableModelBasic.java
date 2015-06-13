package tablemodel;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Modelo de columna del modelo de la tabla.
 * 
 * @author Urko
 *
 */
public class ColumnTableModelBasic extends DefaultTableColumnModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1704151100993018553L;
	TrazadorTableBasic newTrazador;
	
	public ColumnTableModelBasic(TrazadorTableBasic newTrazador){
		super();
		this.newTrazador = newTrazador;
		this.addColumn(crearColumna("Entrenamiento", 1, 300));
		this.addColumn(crearColumna("Inicio", 2, 200));
		this.addColumn(crearColumna("Duración", 3, 200));
		this.addColumn(crearColumna("Media Pulsaciones", 4, 50));
		this.addColumn(crearColumna("Máximo Pulsaciones", 5, 50));
		this.addColumn(crearColumna("Nivel de Estabilidad", 6, 50));
	}

	private TableColumn crearColumna(String texto, int indice, int ancho) {
		TableColumn columna = new TableColumn(indice, ancho);
		columna.setHeaderValue(texto);
		columna.setPreferredWidth(ancho);
		columna.setCellRenderer(newTrazador);
		/*
		 * Aplicar, si se necesita, un cell renderer con "columna.setCellEditor(new DefaultCellEditor(COMBOBOX));"
		 */
		return columna;
	}
}
