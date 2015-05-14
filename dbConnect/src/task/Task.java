package task;

import graphicinterface.CronometerUI;
import graphicinterface.TrainingUI;

import javax.swing.SwingWorker;

/**
 * Esta clase crea un SwingWorker, o un proceso en otra pila, para no bloquear la interfaz gráfica.
 * 
 * @author Runnstein Team
 */
public class Task extends SwingWorker<Void, Integer> {
	TrainingUI ui = null;
	CronometerUI cronometer = null;
	
	public Task(TrainingUI ui, CronometerUI cronometer) {
		this.ui = ui;
		this.cronometer = cronometer;
		cronometer.start();
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		System.out.println(cronometer.getHours()+" "+cronometer.getMinutes()+" "+cronometer.getSeconds());
		cronometer.setJLabelTime(cronometer.getHours(), cronometer.getMinutes(), cronometer.getSeconds());
		ui.refreshUI();
		return null;
	}
	
	protected void process() {
		/*
		 * 
		 */
	}

    @Override
    public void done() {
    	cronometer.stop();
    }
}
