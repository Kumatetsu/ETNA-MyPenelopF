package controllers;

import DAO.ProjectDAO;
import Observer.ProjectListener;
import classes.Project;
import ihm.BaseFrame;
import ihm.dashboardPanel;
import utils.PenelopDevLogger;

public class ProjectController implements PenelopeController, ProjectListener {
	/**
	 * Logger
	 */
	final static PenelopDevLogger log = PenelopDevLogger.get();
	/**
	 * Data accessor
	 */
	private ProjectDAO pDAO = null;
	/**
	 * Views
	 */
	private dashboardPanel dashboard;

	public ProjectController(ProjectDAO pDAO) {
		this.pDAO = pDAO;
	}

	public void init() {
		this.pDAO.addProjectListener(this);
	}

	public ProjectDAO getDAO() {
		return this.pDAO;
	}

	public dashboardPanel getDashboard() {
		return this.dashboard;
	}

	public void setDashboard(dashboardPanel dashboard) {
		this.dashboard = dashboard;
	}

	public void ProjectChangeTriggered() {
		this.refreshProject();
	}

	public void ShowUpdateTriggered(Project p) {
		new BaseFrame(this, p);
	}

	public void DeleteProjectTriggered(Project dProject) {
		this.pDAO.remove(dProject);
	}

	public void CreateProjectTriggered(Project nProject) {
		this.pDAO.add(nProject);
	}

	public void UpdateProjectTriggered(Project uProject) {
		this.pDAO.update(uProject);
	}
	
	private void refreshProject() {
		this.dashboard.displayProjectPanel();
	}

	public void testCtrl() {
		log._("INIT ProjectController");
	}
}
