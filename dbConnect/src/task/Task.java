package task;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.SwingWorker;

/**
 * Esta clase crea un SwingWorker, o un proceso en otra pila, para no bloquear la interfaz gráfica.
 * 
 * @author Runnstein Team
 */
public class Task extends SwingWorker<Void, Integer> {
	Method method = null;
	Object classObject = null;
	
	public Task(Object classObject, String methodName) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.classObject = classObject;
		method = classObject.getClass().getMethod(methodName);
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		method.invoke(classObject);
		return null;
	}
	
	protected void process() {
		/*
		 * Durante el proceso...
		 */
	}

    @Override
    public void done() {
    	/*
    	 * Mensajes finales...
    	 */
    }
}
