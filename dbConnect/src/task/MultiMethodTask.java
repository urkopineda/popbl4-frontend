package task;

import java.lang.reflect.InvocationTargetException;

/**
 * Esta clase abstracta permite ejecutar cualquier método en un SwingWorker.
 * 
 * @author Runnstein Team
 */
public abstract class MultiMethodTask {
	/**
	 * A esta clase le pasamos el objeto de la clase del método y el nombre del método sin ().
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
