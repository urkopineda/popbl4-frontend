package playerModel;

import java.util.ArrayList;
import java.util.ListIterator;

import javax.swing.DefaultListModel;

public class MiListModel<Dato> extends DefaultListModel<Dato> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5842442280372813083L;
	ArrayList<Dato> list;	
	
	public MiListModel() {
		super();
		list = new ArrayList<>();
	}
	
	@Override
	public void addElement(Dato element) {
		super.addElement(element);
		list.add(element);
		
	}

	@Override
	public Dato get(int index) {
		return list.get(index);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	public ArrayList<Dato> getArrayList() {
		return list;
	}
	
	public ListIterator<Dato> listIterator() {
		return list.listIterator();
	}

	@Override
	public void removeElementAt(int index) {
		super.removeElementAt(index);
	}

}
