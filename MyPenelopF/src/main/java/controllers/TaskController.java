package controllers;

import DAO.TaskDAO;
import Observer.TaskListener;
import classes.Task;
import ihm.BaseFrame;
import ihm.dashboardPanel;
import utils.PenelopDevLogger;

public class TaskController implements PenelopeController, TaskListener {
	/**
	 * Logger
	 */
	final static PenelopDevLogger log = PenelopDevLogger.get();
	/**
	 * Data accessor 
	 */
	private TaskDAO tDAO = null;
	/**
	 * Views
	 */
	private dashboardPanel dashboard;
	
	public TaskController(TaskDAO tDAO) {
		this.tDAO = tDAO;
	}

	public void init() {
		this.tDAO.addTaskListener(this);
	}
	
	public TaskDAO getDAO() {
		return this.tDAO;
	}

	public void testCtrl() {
		log._("INIT TaskController");
	}
	
	public void ShowUpdateTriggered(Task t) {
    	new BaseFrame(this, t);
	}

	public void DeleteTaskTriggered(Task dTask) {
		this.tDAO.remove(dTask);
	}

	public void CreateTaskTriggered(Task nTask) {
		this.tDAO.add(nTask);
	}

	public void UpdateTaskTriggered(Task uTask) {
		this.tDAO.update(uTask);
	}

	public void TaskChangeTriggered() {
		this.refreshTasks();
	}
	
	private void refreshTasks() {
        this.dashboard.displayTaskPanel();
	}
	
	public void setDashboard(dashboardPanel dashboard) {
		this.dashboard = dashboard;
	}

	public dashboardPanel getDashboard() {
		return this.dashboard;
	}
}
