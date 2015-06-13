package tablemodel;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Trazador de la JTable.
 * 
 * @author Urko
 *
 */
public class TrazadorTableBasic extends DefaultTableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1965251464893512824L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object valor, boolean isSelected, boolean hasFocus, int fila, int columna) {
		super.getTableCellRendererComponent(table, valor, isSelected, hasFocus, fila, columna);
		switch (columna) {
			default: super.setHorizontalAlignment(CENTER);
				break;
		}
		return this;
	}
}
