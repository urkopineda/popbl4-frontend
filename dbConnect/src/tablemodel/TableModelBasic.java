package tablemodel;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import model.TableData;

@SuppressWarnings("serial")
public class TableModelBasic extends AbstractTableModel {		
	ColumnTableModelBasic columns;
	ArrayList<TableData> allData;
	
	public TableModelBasic(ColumnTableModelBasic columns, ArrayList<TableData> allData){
		super();
		this.columns = columns;
		this.allData = allData;
	}
	
	@Override
	public int getColumnCount() {
		return columns.getColumnCount();
	}

	@Override
	public int getRowCount() {
		return allData.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		TableData tempData = allData.get(row);
		switch (column) {
			case 1: return "Entrenamiento Nº"+tempData.trainingNumber;
			case 2: return tempData.dateTime;
			case 3: return tempData.duration;
			case 4: return tempData.rateMean;
			case 5: return tempData.rateMax;
			case 6: return tempData.stability;
		}		
		return null;
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
