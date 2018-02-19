package ihm.project;

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

import classes.Contact;
import classes.Group;
import classes.Project;
import classes.Task;
import controllers.ContactController;
import controllers.GroupController;
import controllers.ProjectController;
import controllers.TaskController;
import ihm.ViewBuilder;
// import utils.PenelopDevLogger;

public class ProjectForm extends JPanel {

	/**
	 * JPanel implementation requirement
	 */
	private static final long serialVersionUID = 7884244449386779509L;
	// private static final PenelopDevLogger log = PenelopDevLogger.get();
	private ViewBuilder _vb = new ViewBuilder();
	private JPanel pan;
	private JLabel title = new JLabel("Create New Project");
	private JButton createButton = new JButton("Create");
	private JButton updateButton = new JButton("Update");
	private JPanel name = this._vb.getTextField("Name");
	private JPanel description = this._vb.getTextField("Content Description");
	private JList<?> cList = null;
	private JList<?> gList = null;
	private JList<?> tList = null;
	
	public ProjectForm(JPanel pan,
					   final ProjectController pCtrl,
					   GroupController gCtrl,
					   ContactController cCtrl,
					   TaskController tCtrl
					  ) {
		ArrayList<Contact> c = cCtrl.getDAO().get();
		ArrayList<Group> g = gCtrl.getDAO().get();
		ArrayList<Task> t = tCtrl.getDAO().get();
		this.cList = new JList(c.toArray());
		this.gList = new JList(g.toArray());
		this.tList = new JList(t.toArray());
		this.cList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.gList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.tList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.cList.setVisibleRowCount(3);
		this.gList.setVisibleRowCount(3);
		this.tList.setVisibleRowCount(3);
		GridLayout gl = new GridLayout(7, 1, 2, 2);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.pan.add(this.title);
		this.pan.add(this.name);
		this.pan.add(this.description);
		this.pan.add(new JScrollPane(this.cList));
		this.pan.add(new JScrollPane(this.gList));
		this.pan.add(new JScrollPane(this.tList));
		final ProjectForm self = this;
		this.createButton.setPreferredSize(this._vb.getButtonSize());
		this.createButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Project p = new Project(
	     					self.getNameInput().getText(),
	     					self.getDescriptionInput().getText(),
	     					getGroupsFromList(),
	     					getUsersFromList(),
	     					getTasksFromList()
	     				);
	     		pCtrl.getDAO().add(p);
	     	}
		});
		this.pan.add(this.createButton);
	}
	
	// update Constructor
	public ProjectForm(JPanel pan, final ProjectController pCtrl, Project project) {
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.title = new JLabel("Update project n°" + project.getId().toString());
		this.pan.add(this.title);
		this.pan.add(this.name);
		this.getNameInput().setText(project.getName());
		this.pan.add(this.description);
		this.getDescriptionInput().setText(project.getDescription());
		final ProjectForm self = this;
		final Integer id = project.getId();
		this.updateButton.setPreferredSize(this._vb.getButtonSize());
		this.updateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Project p = new Project(
	     					id,
	     					self.getNameInput().getText(),
	     					self.getDescriptionInput().getText()
	     				);
	     		pCtrl.getDAO().update(p);
	     	}
		});
		this.pan.add(this.updateButton);
	}
	
	// View components accessors
	public JPanel getPan() {
		return this.pan;
	}
	public JPanel getNamePanel() {
		return this.name;
	}
	public JTextField getNameInput() {
		return (JTextField)this.name.getComponent(1);
	}
	public JPanel getDescriptionPanel() {
		return this.description;
	}
	public JTextField getDescriptionInput() {
		return (JTextField)this.description.getComponent(1);
	}
	
	ArrayList<Contact> getUsersFromList() {
		List<?> users =  this.cList.getSelectedValuesList();
		ArrayList<Contact> aUsers = new ArrayList<Contact>();
		for (Object user: users) 
			aUsers.add((Contact)user);
		return aUsers;
	}
	
	ArrayList<Group> getGroupsFromList() {
		List<?> groups =  this.gList.getSelectedValuesList();
		ArrayList<Group> aGroups = new ArrayList<Group>();
		for (Object group: groups) 
			aGroups.add((Group)group);
		return aGroups;
	}

	ArrayList<Task> getTasksFromList() {
		List<?> tasks =  this.tList.getSelectedValuesList();
		ArrayList<Task> aTasks = new ArrayList<Task>();
		for (Object task: tasks)
			aTasks.add((Task)task);
		return aTasks;
	}
}
