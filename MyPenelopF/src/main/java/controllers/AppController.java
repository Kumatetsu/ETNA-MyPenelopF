package controllers;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import DAO.ContactDAO;
import DAO.DAOFactory;
import DAO.GroupDAO;
import DAO.ProjectDAO;
import DAO.TaskDAO;
import DataInterface.DataInterface;
import ihm.BaseFrame;
import ihm.dashboardPanel;
import utils.DocumentLooker;
import utils.PenelopDevLogger;

public class AppController {
	private static final PenelopDevLogger log = PenelopDevLogger.get();
	private ContactController cCtrl;
	private ProjectController pCtrl;
	private GroupController gCtrl;
	private TaskController tCtrl;
	private HashMap<String, PenelopeController> ctrls = new HashMap<String, PenelopeController>();
	private ContactDAO cDAO = null;
	private ProjectDAO pDAO = null;
	private GroupDAO gDAO = null;
	private TaskDAO tDAO = null;
	private BaseFrame Dashboard = null;
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	public AppController(DataInterface di) {
		this.cDAO = (ContactDAO)DAOFactory.getContactDAO(di);
		this.pDAO = (ProjectDAO)DAOFactory.getProjectDAO(di);
		this.gDAO = (GroupDAO)DAOFactory.getGroupDAO(di);
		this.tDAO = (TaskDAO)DAOFactory.getTaskDAO(di);
		// controllers instaciation adding DAO
		this.cCtrl = new ContactController(this.cDAO);
		this.pCtrl = new ProjectController(this.pDAO);
		this.gCtrl = new GroupController(this.gDAO);
		this.tCtrl = new TaskController(this.tDAO);
		this.ctrls.put("group", this.gCtrl);
		this.ctrls.put("contact", this.cCtrl);
		this.ctrls.put("project", this.pCtrl);
		this.ctrls.put("task", this.tCtrl);
		for (String key: this.ctrls.keySet())
			this.ctrls.get(key).init();
		this.setBaseFrame(new BaseFrame(ctrls));
		if (this.viewIsInit()) {
			this.cCtrl.setDashboard(this.getDashboard());
			this.pCtrl.setDashboard(this.getDashboard());
			this.gCtrl.setDashboard(this.getDashboard());
			this.tCtrl.setDashboard(this.getDashboard());
		}
		final DocumentLooker bigBrother = new DocumentLooker((ProjectController)ctrls.get("project"));
		final ScheduledFuture<?> beeperHandle =
			scheduler.scheduleAtFixedRate((Runnable)bigBrother, 5, 30, TimeUnit.SECONDS);
		log._("APP PENELOPE INITIALISED");
	}
	
	public ContactDAO getContactDAO() {
		return this.cDAO;
	}

	public GroupDAO getGroupDAO() {
		return this.gDAO;
	}

	public void testCtrl() {
		log._("TEST App Controller");
	}

	public boolean viewIsInit() {
		return this.Dashboard != null && this.Dashboard.getDashboardPanel() != null;
	}

	public dashboardPanel getDashboard() {
		return this.Dashboard.getDashboardPanel();
	}

	public void setBaseFrame(BaseFrame dashboard) {
		this.Dashboard = dashboard;
	}
}
