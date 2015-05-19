package tablemodel;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Modelo de columnas de la JTable utilizada para cargar datos.
 * 
 * @author Runnstein Team
 */
@SuppressWarnings("serial")
public class ColumnTableModelBasic extends DefaultTableColumnModel {
	TrazadorTableBasic newTrazador;
	
	/**
	 * Contructor del modelo de columna.
	 * 
	 * @param TrazadorTableBasic newTrazador
	 */
	public ColumnTableModelBasic(TrazadorTableBasic newTrazador){
		super();
		this.newTrazador = newTrazador;
		this.addColumn(crearColumna("Entrenamiento", 1, 30));
		this.addColumn(crearColumna("Inicio", 2, 14));
		this.addColumn(crearColumna("Duraci�n", 3, 14));
		this.addColumn(crearColumna("Media Pulsaciones", 4, 14));
		this.addColumn(crearColumna("M�ximo Pulsaciones", 5, 14));
		this.addColumn(crearColumna("Nivel de Estabilidad", 6, 14));
	}

	/**
	 * M�todo para a�adir una columna.
	 * 
	 * @param String texto
	 * @param int indice
	 * @param int ancho
	 * @return TableColumn columna
	 */
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
