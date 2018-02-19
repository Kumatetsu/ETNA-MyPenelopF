package controllers;

import DAO.DAO;
import ihm.dashboardPanel;

public interface PenelopeController {
	public void init();
	public dashboardPanel getDashboard();
	public void setDashboard(dashboardPanel dashboard);
	public DAO<?> getDAO();
}
