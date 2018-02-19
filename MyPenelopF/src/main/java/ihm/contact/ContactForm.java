package ihm.contact;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import controllers.ContactController;
import controllers.GroupController;
import controllers.ProjectController;
import ihm.ViewBuilder;
import utils.PenelopDevLogger;

public class ContactForm extends JPanel  {

	/**
	 * JPanel implementation requirement
	 */
	private static final long serialVersionUID = 4029539107222146186L;
	private static final PenelopDevLogger log = PenelopDevLogger.get();
	private ViewBuilder _vb = new ViewBuilder();
	private JPanel pan;
	private JLabel title = new JLabel("Create New Contact");
	private JButton createButton = new JButton("Create");
	private JButton updateButton = new JButton("Update");
	private JPanel email = this._vb.getTextField("Email");
	private JPanel surname = this._vb.getTextField("Surname");
	private JPanel name = this._vb.getTextField("Name");
	private JList<?> pList = null;
	private JList<?> gList = null;
	
	public ContactForm(JPanel pan, final ContactController cCtrl) {
		GridLayout gl = new GridLayout(7, 4, 20, 20);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.pan.setBorder(new EmptyBorder(100, 10, 10, 10));
		this.pan.add(this.title);
		this.pan.add(this.email);
		this.pan.add(this.surname);
		this.pan.add(this.name);
		final ContactForm self = this;
		this.createButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Contact c = new Contact(
	     					self.getEmailInput().getText(),
	     					self.getSurnameInput().getText(),
	     					self.getNameInput().getText()
	     				);
	     		cCtrl.getDAO().add(c);
	     	}
		});
		this.pan.add(this.createButton);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public ContactForm(JPanel pan, final ContactController cCtrl,  GroupController gCtrl, ProjectController pCtrl) {
		ArrayList<Project> p = pCtrl.getDAO().get();
		ArrayList<Group> g = gCtrl.getDAO().get();
		this.pList = new JList(p.toArray());
		this.gList = new JList(g.toArray());
		this.pList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.gList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.pList.setVisibleRowCount(3);
		this.gList.setVisibleRowCount(3);
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.pan.add(this.title);
		this.pan.add(this.email);
		this.pan.add(this.surname);
		this.pan.add(this.name);
		this.pan.add(new JScrollPane(this.pList));
		this.pan.add(new JScrollPane(this.gList));
		final ContactForm self = this;
		this.createButton.setPreferredSize(this._vb.getButtonSize());
		this.createButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Contact c = new Contact(
	     					self.getEmailInput().getText(),
	     					self.getSurnameInput().getText(),
	     					self.getNameInput().getText(),
	     					getGroupsFromList(),
	     					getProjectsFromList(),
	     					new ArrayList<Msgs>()
	     				);
	     		cCtrl.getDAO().add(c);
	     	}
		});
		this.pan.add(this.createButton);
	}
	
	public ContactForm(JPanel pan, final ContactController cCtrl, Contact contact) {
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.title = new JLabel("Update contact n°" + contact.getId().toString());
		this.pan.add(this.title);
		this.getEmailInput().setText(contact.getEmail());
		this.pan.add(this.email);
		this.getSurnameInput().setText(contact.getSurname());
		this.pan.add(this.surname);
		this.getNameInput().setText(contact.getName());
		this.pan.add(this.name);
		final ContactForm self = this;
		final int id = contact.getId();
		final ArrayList<Msgs> msgs = contact.getMessages();
		final ArrayList<Project> projects = contact.getProjects();
		final ArrayList<Group> groups = contact.getGroups();
		this.updateButton.setPreferredSize(this._vb.getButtonSize());
		this.updateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	    		final Contact c = new Contact(
	    					id,
	    					self.getEmailInput().getText(),
	    					self.getSurnameInput().getText(),
	    					self.getNameInput().getText(),
	    					groups,
	    					projects,
	    					msgs
	    				);
	    		if (!cCtrl.getDAO().update(c))
	    			log._("ERROR, something wrong happenned updating a contact");
	     	}
		});
		this.pan.add(this.updateButton);
	}
	
	public JPanel getPan() {
		return this.pan;
	}
	public JLabel getTitle() {
		return this.title;
	}
	public JButton getCreateButton() {
		return this.createButton;
	}
	public JButton getUpdateButton() {
		return this.updateButton;
	}
	public JTextField getEmailInput() {
		return (JTextField)this.email.getComponent(1);
	}
	public JTextField getSurnameInput() {
		return (JTextField)this.surname.getComponent(1);
	}
	public JTextField getNameInput() {
		return (JTextField)this.name.getComponent(1);
	}
	
	ArrayList<Project> getProjectsFromList() {
		List<?> projects =  this.pList.getSelectedValuesList();
		ArrayList<Project> aProjects = new ArrayList<Project>();
		for (Object project: projects) {
			Project newProject = (Project)project;
			System.out.println(newProject.getName());
			aProjects.add(newProject);
		}
		return aProjects;
	}
	
	ArrayList<Group> getGroupsFromList() {
		List<?> groups =  this.gList.getSelectedValuesList();
		ArrayList<Group> aGroups = new ArrayList<Group>();
		for (Object group: groups) {
			Group newGroup = (Group)group;
			System.out.println(newGroup.getName());
			aGroups.add(newGroup);
		}
		return aGroups;
	}
}
