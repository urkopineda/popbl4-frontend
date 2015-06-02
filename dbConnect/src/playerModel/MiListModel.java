package playerModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.ListIterator;

import javax.swing.DefaultListModel;

public class MiListModel<Dato> extends DefaultListModel<Dato> {
	ArrayList<Dato> lista;	
	
	public MiListModel() {
		super();
		lista = new ArrayList<>();
	}
	
	@Override
	public void addElement(Dato element) {
		super.addElement(element);
		lista.add(element);
		
	}

	@Override
	public Dato get(int index) {
		return lista.get(index);
	}

	@Override
	public boolean isEmpty() {
		return super.isEmpty();
	}
	
	public ArrayList<Dato> getArrayList() {
		return lista;
	}
	
	public ListIterator<Dato> listIterator() {
		return lista.listIterator();
	}

	@Override
	public void removeElementAt(int index) {
		super.removeElementAt(index);
	}

}
