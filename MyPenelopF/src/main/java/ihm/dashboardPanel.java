package ihm;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;

import classes.Contact;
import classes.Group;
import classes.Project;
import classes.Task;
import controllers.ContactController;
import controllers.GroupController;
import controllers.ProjectController;
import controllers.TaskController;
import ihm.contact.ContactForm;
import ihm.contact.ContactPanel;
import ihm.group.GroupForm;
import ihm.group.GroupPanel;
import ihm.project.ProjectForm;
import ihm.project.ProjectPanel;
import ihm.task.TaskForm;
import ihm.task.TaskPanel;
import ihm.PeneViewListener;
// import utils.PenelopDevLogger;

/**
 *
 * @author kumatetsu
 * Front Controller for the dashboard view
 */
public class dashboardPanel implements PeneViewListener {

	// private static final PenelopDevLogger log = PenelopDevLogger.get();

	/**
	 * Declaration du Panel Principal
	 */
	private JPanel  mPan;

	/**
	 * Panels pouvant etre appelles dans le panel parent mPan
	 */
	// contact
	private ContactForm contactForm;
	private ContactPanel contactPanel;
	// group
	private GroupPanel groupPanel;
	private GroupForm groupCreate;
	// project
	private ProjectForm projectForm;
	private ProjectPanel projectPanel;
	// task
	private TaskForm taskForm;
	private TaskPanel taskPanel;

	/**
	 * Controller permettant la gestion des Listeners;
	 */
	private ContactController cCtrl;
	private ProjectController pCtrl;
	private GroupController gCtrl;
	private TaskController tCtrl;
	private CardLayout contactCl = new CardLayout();
	private CardLayout projectCl = new CardLayout();
	private CardLayout groupCl = new CardLayout();
	private CardLayout taskCl = new CardLayout();
	public dashboardPanel(ContactController cCtrl, ProjectController pCtrl, GroupController gCtrl, TaskController tCtrl) {
		this.cCtrl = cCtrl;
		this.pCtrl = pCtrl;
		this.gCtrl = gCtrl;
		this.tCtrl = tCtrl;
		this.mPan = new JPanel();
	}
	public JPanel getPanel() {
		return this.mPan;
	}

	/**
	 * Observer Pattern used to organised entities' views display
	 */
	// Contacts
	public void showContactsTriggered() {
		this.displayContactPanel();
	}
	// Projects
	public void showProjectsTriggered() {
		this.displayProjectPanel();
	}
	// Tasks
	public void showTasksTriggered() {
		this.displayTaskPanel();
	}
	// Groups
	public void showGroupsTriggered() {
		this.displayGroupPanel();
	}
	
	/**
	 * private method displaying contact logic and associations
	 */
	public void displayContactPanel() {
		ArrayList<Contact> contacts = this.cCtrl.getDAO().get();
		if (contacts == null) {
			return;
		}
		// add contact view
		this.contactForm = new ContactForm(new JPanel(), this.cCtrl, this.gCtrl, this.pCtrl);
		// get, update and delete contact view
		this.contactPanel = new ContactPanel(new JPanel(),
											 this.cCtrl,
											 this.gCtrl,
											 this.pCtrl,
											 this.tCtrl,
											 this.contactCl,
											 contacts,
											 true
											);
        // Panel construction
		this.mPan.removeAll();
		this.mPan.add(this.contactPanel.getRootPan());
		this.mPan.add(this.contactForm.getPan());
		this.mPan.setLayout(new GridLayout(2, 1, 2, 2));
		this.mPan.revalidate();
		this.mPan.repaint();
	}

	/**
	 * private method displaying project logic and associations
	 */
	public void displayProjectPanel() {
		ArrayList<Project> projects = this.pCtrl.getDAO().get();
		if (projects == null)
			return;
		// add contact view
		this.projectForm = new ProjectForm(new JPanel(),
										   this.pCtrl,
										   this.gCtrl,
										   this.cCtrl,
										   this.tCtrl);
		// get, update, and delete project views
		this.projectPanel = new ProjectPanel(new JPanel(),
											 this.pCtrl,
											 this.cCtrl,
											 this.gCtrl,
											 this.tCtrl,
											 this.projectCl,
											 projects,
											 true);
		// Panel construction
		this.mPan.removeAll();
		this.mPan.add(this.projectPanel.getRootPan());
		this.mPan.add(this.projectForm.getPan());
		this.mPan.setLayout(new GridLayout(2, 1, 2, 2));
		this.mPan.revalidate();
		this.mPan.repaint();
	}

	/**
	 * private method displaying task logic and associations
	 */
	public void displayTaskPanel() {
		ArrayList<Task> tasks = this.tCtrl.getDAO().get();
		if (tasks == null)
			return;
		// get, update, and delete task views
		this.taskForm = new TaskForm(new JPanel(), this.tCtrl);
		this.taskPanel = new TaskPanel(new JPanel(), this.tCtrl, this.taskCl, tasks, true);
		// Panel construction
		this.mPan.removeAll();
		this.mPan.add(this.taskPanel.getRootPan());
		this.mPan.add(this.taskForm.getPan());
		this.mPan.setLayout(new GridLayout(2, 1, 2, 2));
		this.mPan.revalidate();
		this.mPan.repaint();
	}

	/**
	 * private method displaying group logic and associations
	 */
	public void displayGroupPanel() {
		ArrayList<Group> groups = this.gCtrl.getDAO().get();
		if (groups == null)
			return;
		this.groupPanel = new GroupPanel(new JPanel(),
										 this.gCtrl,
										 this.cCtrl,
										 this.pCtrl,
										 this.tCtrl,
										 this.groupCl,
										 groups,
										 true
										);
		this.groupCreate = new GroupForm(new JPanel(), this.gCtrl, this.pCtrl, this.cCtrl);
		this.mPan.removeAll();
		this.mPan.add(groupPanel.getRootPan());
		this.mPan.add(this.groupCreate.getPan());
		this.mPan.setLayout(new GridLayout(2, 1, 2, 2));
		this.mPan.revalidate();
		this.mPan.repaint();
	}
}
