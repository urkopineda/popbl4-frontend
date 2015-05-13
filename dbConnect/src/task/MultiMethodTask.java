package task;

import java.lang.reflect.InvocationTargetException;

/**
 * Esta clase abstracta permite ejecutar cualquier m�todo en un SwingWorker.
 * 
 * @author Runnstein Team
 */
public abstract class MultiMethodTask {
	/**
	 * A esta clase le pasamos el objeto de la clase del m�todo y el nombre del m�todo sin ().
	 * 
	 * @param classObject
	 * @param methodName
	 * @return Task
	 */
	public Task executeMethodInTask(Object classObject, String methodName) {
		try {
			Task newTask = new Task(classObject, methodName);
			return newTask;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} return null;
	}
}
