package playerModel;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.DefaultListModel;

/**
 * Modelo que puede recoger objetos de tipo DataType.
 * @author unaipme
 *
 * @param <DataType> Ver DataType.
 */
public class MiListModel<DataType> extends DefaultListModel<DataType> {
	private static final long serialVersionUID = 5842442280372813083L;
	ArrayList<DataType> list;	
	
	public MiListModel() {
		super();
		list = new ArrayList<>();
	}
	
	@Override
	public void addElement(DataType element) {
		super.addElement(element);
		list.add(element);
		
	}

	@Override
	public DataType get(int index) {
		return list.get(index);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	public ArrayList<DataType> getArrayList() {
		return list;
	}
	
	public ListIterator<DataType> listIterator() {
		return list.listIterator();
	}

	@Override
	public void removeElementAt(int index) {
		super.removeElementAt(index);
	}

}
