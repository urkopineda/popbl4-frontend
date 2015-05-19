package tablemodel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.Data;

/**
 * Un modelo de JTable de tipo AbstractTableModel.
 * 
 * @author Runnstein Team
 */
@SuppressWarnings("serial")
public class TableModelBasic extends AbstractTableModel {		
	ColumnTableModelBasic columns;
	Data objData = null;
	
	/**
	 * Crea el modelo de tabla básico.
	 * 
	 * @param ColumnTableModelBasic columns
	 * @param Data objData
	 */
	public TableModelBasic(ColumnTableModelBasic columns, Data objData){
		super();
		this.columns = columns;
		this.objData = objData;
	}
	
	/**
	 * Insertar un dato en la lista de datos del objeto 'Data'.
	 * 
	 * @param ArrayList<String> newData
	 */
	public void insertData(ArrayList<String> newData) {
		objData.addData(newData);
		this.fireTableDataChanged();
	}
	
	/**
	 * Borra un dato en la lista de datos del objeto 'Data'.
	 * 
	 * @param int index
	 */
	public void deleteData(int index) {
		objData.deleteData(index);
		this.fireTableDataChanged();
	}
	
	/**
	 * Consigue un dato especifico del ArrayList del objeto Data y devuelve su tipo (String)
	 * 
	 * @param int index
	 * @return String data
	 */
	public String getData(int index) {
		return objData.getSpecificData(index);
	}
	
	@Override
	public int getColumnCount() {
		return columns.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return objData.getRowNumber();
	}

	@Override
	public Object getValueAt(int row, int column) {
		int index = 0;
		if (row == 0) index = column;
		else index = (row * getColumnCount()) + column;
		return objData.getData().get(index);
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
