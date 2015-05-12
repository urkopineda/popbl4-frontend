package Admin;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

/**
 * Modelo de columnas de la JTable utilizada para cargar datos.
 * 
 * @author Runnstein Team
 */
public class ColumnTableModelBasic extends DefaultTableColumnModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	TrazadorTableBasic newTrazador;
	
	/**
	 * Contructor del modelo de columna, carga el nombre de las columnas del objeto 'Data'.
	 * 
	 * @param newTrazador
	 * @param data
	 */
	public ColumnTableModelBasic(TrazadorTableBasic newTrazador, Data data){
		super();
		this.newTrazador = newTrazador;
		for (int i = 0; i <= data.columns.size(); i++) {
			this.addColumn(crearColumna(data.columns.get(i), i, 100));
		}
	}

	/**
	 * Método para añadir una columna.
	 * 
	 * @param texto
	 * @param indice
	 * @param ancho
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
