package Admin;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Un modelo de JTable de tipo AbstractTableModel.
 * 
 * @author Runnstein Team
 */
public class TableModelBasic extends AbstractTableModel {	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	ColumnTableModelBasic columns;
	Data objData = null;
	
	/**
	 * Crea el modelo de tabla básico.
	 * 
	 * @param columns
	 * @param objData
	 */
	public TableModelBasic(ColumnTableModelBasic columns, Data objData){
		super();
		this.columns = columns;
		this.objData = objData;
	}
	
	/**
	 * Insertar un dato en la lista de datos del objeto 'Data'.
	 * 
	 * @param newData
	 */
	public void insertData(ArrayList<String> newData) {
		objData.addData(newData);
		this.fireTableDataChanged();
	}
	
	/**
	 * Borra un dato en la lista de datos del objeto 'Data'.
	 * 
	 * @param index
	 */
	public void deleteData(int index) {
		objData.deleteData(index);
		this.fireTableDataChanged();
	}
	
	@Override
	public int getColumnCount() {
		return columns.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return objData.data.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		return objData.getSpecificData((row * getColumnCount()) + column);
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		/*
		 * Hacemos "return true" a las columnas/filas que sí y "return false" a las que no...
		 */
		return false;
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0,columnIndex).getClass();
	}
}
