package controllers;

import DAO.DAO;
import DAO.MsgsDAO;
import ihm.dashboardPanel;

public class MsgsController implements PenelopeController {
	
	private MsgsDAO mDAO = null;

	public MsgsController(MsgsDAO mDAO) {
		this.mDAO = mDAO;
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public dashboardPanel getDashboard() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDashboard(dashboardPanel dashboard) {
		// TODO Auto-generated method stub
		
	}

	public MsgsDAO getDAO() {
		return this.mDAO;
	}

}
