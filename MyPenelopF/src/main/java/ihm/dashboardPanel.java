package ihm;

import java.awt.CardLayout;
import java.awt.Color;
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
// import utils.PenelopDevLogger;

/**
 *
 * @author kumatetsu
 * Front Controller for the dashboard view
 */
public class dashboardPanel implements ViewListener {

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
		this.sPan = new JScrollPane(this.mPan);
	}

	public JScrollPane getPan() {
		return this.sPan;
	}

	public void showContactsTriggered() {
		this.displayContactPanel();
	}

	public void displayContactPanel() {
		ArrayList<Contact> contacts = this.cCtrl.getContactDAO().get();
		if (contacts == null) {
			return;
		}
		// add contact view
		this.contactForm = new ContactForm(new JPanel(), this.gCtrl, this.pCtrl);
		this.contactForm.addContactListener(this.cCtrl);
		// get, update and delete contact view
		this.contactPanel = new ContactPanel(new JPanel(), this.contactCl, contacts, true);
        this.contactPanel.addContactListener(this.cCtrl);
        this.contactNavPan = this._fb.getNavPanel(this.contactPanel.getCard(), this.contactPanel.getPan());
        // Panel construction
		this.mPan.removeAll();
		this.mPan.add(this.contactNavPan);
		this.mPan.add(this.contactPanel.getPan());
		this.mPan.add(this.contactForm.getPan());
		this.mPan.setBackground(Color.gray);
		this.mPan.revalidate();
		this.mPan.repaint();
	}

	public void showProjectsTriggered() {
		this.displayProjectPanel();
	}

	public void displayProjectPanel() {
		ArrayList<Project> projects = this.pCtrl.getPDAO().get();
		if (projects == null)
			return;
		// add contact view
		this.projectForm = new ProjectForm(new JPanel(), this.gCtrl, this.cCtrl);
		this.projectForm.addProjectListener(this.pCtrl);
		// get, update, and delete project views
		this.projectPanel = new ProjectPanel(new JPanel(), this.projectCl, projects, true);
		this.projectPanel.addProjectListener(this.pCtrl);
		this.projectNavPan = this._fb.getNavPanel(this.projectPanel.getCard(), this.projectPanel.getPan());
		// Panel construction
		this.mPan.removeAll();
		this.mPan.add(this.projectNavPan);
		this.mPan.add(this.projectPanel.getPan());
		this.mPan.add(this.projectForm.getPan());
		this.mPan.setBackground(Color.blue);
		this.mPan.revalidate();
		this.mPan.repaint();
	}

	public void showTasksTriggered() {
		this.displayTaskPanel();
	}
	public void displayTaskPanel() {
		ArrayList<Task> tasks = this.tCtrl.getDAO().get();
		if (tasks == null)
			return;
		// add task view
		log.tasks(tasks);
		this.taskForm = new TaskForm(new JPanel());
		this.taskForm.addTaskListener(this.tCtrl);
		// get, update, and delete task views
		this.taskPanel = new TaskPanel(new JPanel(), this.taskCl, tasks);
		this.taskPanel.addTaskListener(this.tCtrl);
		this.taskNavPan = this._fb.getNavPanel(this.taskPanel.getCard(), this.taskPanel.getPan());
		// Panel construction
		this.mPan.removeAll();
		this.mPan.add(this.taskNavPan);
		this.mPan.add(this.taskPanel.getPan());
		this.mPan.add(this.taskForm.getPan());
		this.mPan.setBackground(Color.green);
		this.mPan.revalidate();
		this.mPan.repaint();
	}
	public void showGroupsTriggered() {
		this.displayGroupPanel();
	}

	public void displayGroupPanel() {
		ArrayList<Group> groups = this.gCtrl.getGroupDAO().get();
		if (groups == null)
			return;
		this.groupPanel         = new GroupPanel(new JPanel(), this.groupCl, groups);
		this.groupCreate        = new FormGroup(new JPanel(), this.gCtrl, this.pCtrl, this.cCtrl);

		this.groupCreate.addGroupListener(this.gCtrl);
		this.groupPanel.addGroupListener(this.gCtrl);
		this.mPan.removeAll();
		this.mPan.add(groupPanel.getPan());
		this.mPan.add(this.groupCreate.getPan());
		this.mPan.setBackground(Color.red);
		this.mPan.revalidate();
		this.mPan.repaint();
	}
}
