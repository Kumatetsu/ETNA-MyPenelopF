package controllers;

import java.util.ArrayList;

import DAO.GroupDAO;
import Observer.GroupListener;
import classes.Group;
import ihm.BaseFrame;
import ihm.dashboardPanel;
import utils.PenelopDevLogger;

public class GroupController implements PenelopeController, GroupListener {

	public BaseFrame base;

	private dashboardPanel dashboard;

	final static PenelopDevLogger log = PenelopDevLogger.get();

	private GroupDAO gDAO = null;

	public BaseFrame uForm;

	public GroupController(GroupDAO gDAO) {
		this.gDAO = gDAO;
	}

	public dashboardPanel getDashboard() {
		return this.dashboard;
	}

	public void setDashboard(dashboardPanel dashboard) {
		this.dashboard = dashboard;
	}

	public void init() {
		gDAO.addGroupListener(this);
	}

	final public GroupDAO getDAO() {
		return this.gDAO;
	}

	public void CreateGroupTriggered(Group nGroup) {
		// System.out.println("in gCtrl CreateGroup");
		this.gDAO.add(nGroup);
	}

	public void GroupChangeTriggered() {
		ArrayList<Group> retrievedGroups = this.gDAO.get();
        log._("REFRESH GROUP DISCONNECTED FROM DASHBOARD");
        log.groups(retrievedGroups);
        log._("AFTER GROUP");
        this.dashboard.displayGroupPanel();
	}
}
