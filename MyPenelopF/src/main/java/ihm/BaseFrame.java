package ihm;

import ihm.contact.ContactForm;
import ihm.contact.ContactPanel;
import ihm.group.GroupForm;
import ihm.group.GroupPanel;
import ihm.project.ProjectForm;
import ihm.task.TaskForm;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import classes.Contact;
import classes.Group;
import classes.Project;
import classes.Task;
import controllers.ContactController;
import controllers.GroupController;
import controllers.PenelopeController;
import controllers.ProjectController;
import controllers.TaskController;

/**
 *
 * @author aurelien
 * This classe allow us to declare base views for each entities
 * The main window is define here as a suits of update forms for
 * each entity.
 */
public class BaseFrame extends JFrame {
	
	/**
	 * JFrame implementation requirement
	 */
	private static final long serialVersionUID = 4648369832000775054L;
	/**
	 * Controllers
	 */
	ContactController cCtrl;
	GroupController gCtrl;
	ProjectController pCtrl;
	TaskController tCtrl;
	/**
	 * Tools
	 */
	ViewBuilder _fb = new ViewBuilder();

	/**
	 * Panels, layouts and view parts
	 */
	private menuPanel mPan;
	private dashboardPanel dPan = null;
	private CardLayout cl = new CardLayout();
	private JSplitPane split;
	private ContactPanel contactPanel;
	private ContactForm createContact;
	private ContactForm updateContact;
	private ProjectForm updateProject;
	private TaskForm updateTask;
	private GroupPanel groupPanel;
	private GroupForm createGroup;
	private GroupForm updateGroup;
	private JPanel buttonPane;

	/**
	 * Main BaseFrame, root view for Penelope
	 * @param ctrls
	 */
	public BaseFrame(HashMap<String, PenelopeController> ctrls) {
		this.cCtrl = (ContactController)ctrls.get("contact");
        this.gCtrl = (GroupController)ctrls.get("group");
        this.pCtrl = (ProjectController)ctrls.get("project");
        this.tCtrl = (TaskController)ctrls.get("task");
        this.setTitle("MyPenelopF");
        this.setResizable(true);
        this.getContentPane().setLayout(new BorderLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(250, 250);

        this.mPan = new menuPanel();
        this.dPan = new dashboardPanel(this.cCtrl, this.pCtrl, this.gCtrl, this.tCtrl);
        this.mPan.addViewListener(dPan);
        split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mPan.getPan(), dPan.getPanel());
        this.getContentPane().add(split, BorderLayout.CENTER);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setVisible(true);
    }

	/**
	 * Allow to retrieve the menu
	 * @return menuPanel
	 */
	public menuPanel getMenuPanel() {
		return this.mPan;
	}

	/**
	 * Allow to retrieve the main displayer JPanel => dashboardPanel
	 * @return
	 */
	public dashboardPanel getDashboardPanel() {
		return this.dPan;
	}

	/**
	 *
	 * @param ContactController cCtrl, Contact c
	 * BaseFrame for Contact Update
	 * Display ContactForm with c
	 * Allow to update a Contact
	 */
	public BaseFrame(ContactController cCtrl, Contact c) {
		this.cCtrl = cCtrl;
        JFrame frame = new JFrame("Contacts");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
        frame.setTitle("Update Contact: ");
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(250, 250);
        this.updateContact = new ContactForm(new JPanel(), cCtrl, c);
        this.getContentPane().setLayout(gl);
        this.getContentPane().add(this.updateContact.getPan());
        this.setVisible(true);
	}

	/**
	 *
	 * @param ProjectController pCtrl, Project p
	 * BaseFrame for Project Update
	 * Display ProjectForm with p
	 * Allow to update a Project
	 */
	public BaseFrame(ProjectController pCtrl, Project p) {

		this.pCtrl = pCtrl;
		JFrame frame = new JFrame("Projects");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
		frame.setTitle("Update Project: ");
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(250, 250);
		this.updateProject = new ProjectForm(new JPanel(), pCtrl, p);
		this.getContentPane().setLayout(gl);
		this.getContentPane().add(this.updateProject.getPan());
		this.setVisible(true);
	}

	/**
	 *
	 * @param GroupController gCtrl, Group group
	 * BaseFrame for Group Update
	 * Display GroupForm with p
	 * Allow to update a Group
	 */
	public BaseFrame(GroupController gCtrl, Group group) {
		this.gCtrl = gCtrl;
        JFrame frame = new JFrame("Groups");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
        frame.setTitle("Update Group: ");
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(250, 250);
        this.updateGroup = new GroupForm(new JPanel(), this.gCtrl, group);
        this.setSize(800, 800);
        getContentPane().setLayout(gl);
        this.getContentPane().add(this.updateGroup.getPan());
        this.setVisible(true);
	}

	/**
	 *
	 * @param TaskController tCtrl, Task t
	 * BaseFrame for Task Update
	 * Display TaskForm with t
	 * Allow to update a Task
	 */
	public BaseFrame(TaskController tCtrl, Task t) {

		this.tCtrl = tCtrl;
		JFrame frame = new JFrame("Tasks");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
		frame.setTitle("Update Task: ");
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(250, 250);
		this.updateTask = new TaskForm(new JPanel(), tCtrl, t);
		this.setSize(800, 800);
		getContentPane().setLayout(gl);
		this.getContentPane().add(this.updateTask.getPan());
		this.setVisible(true);
	}

	/**
	 *
	 * @param contacts
	 * recreate the content of the ContactPanel view
	 * remove and add createContact to preserve order.
	 * See for a non-destroying solution...
	 */
	public void refreshContactPanel(ArrayList<Contact> contacts) {
		this.getContentPane().remove(this.buttonPane);
		this.getContentPane().remove(this.contactPanel.getRootPan());
		this.getContentPane().remove(this.createContact.getPan());
		this.cl = new CardLayout();
		this.contactPanel = new ContactPanel(new JPanel(),
											 this.cCtrl,
											 this.gCtrl,
											 this.pCtrl,
											 this.tCtrl,
											 this.cl,
											 contacts,
											 true
											);
        this.getContentPane().add(this.buttonPane);
        this.getContentPane().add(this.contactPanel.getRootPan());
		this.getContentPane().add(this.createContact.getPan());
        this.setVisible(true);
	}

	public void refreshGroupPanel(ArrayList<Group> groups) {
		this.getContentPane().remove(this.buttonPane);
		this.getContentPane().remove(this.groupPanel.getRootPan());
		this.getContentPane().remove(this.createGroup.getPan());
		this.cl = new CardLayout();
		this.groupPanel = new GroupPanel(new JPanel(),
										 this.gCtrl,
										 this.cCtrl,
										 this.pCtrl,
										 this.tCtrl,
										 this.cl,
										 groups,
										 true);
        this.getContentPane().add(this.buttonPane);
        this.getContentPane().add(this.groupPanel.getRootPan());
		this.getContentPane().add(this.createGroup.getPan());
        this.setVisible(true);
	}
}
