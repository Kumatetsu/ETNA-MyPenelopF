package ihm.group;

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
import controllers.ContactController;
import controllers.GroupController;
import controllers.ProjectController;
import ihm.ViewBuilder;

public class GroupForm extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private ViewBuilder _vb = new ViewBuilder();
	private JPanel pan;
	private JLabel title = new JLabel("Create New Group");
	private JButton submit = new JButton("Create");
	private JPanel name = this._vb.getTextField("Name");
	private JButton updateButton = new JButton("Update");
	private JList<?> pList = null;
	private JList<?> cList = null;
	
	public GroupForm(JPanel pan,
				     final GroupController gCtrl,
				     ProjectController pCtrl,
				     ContactController cCtrl
				    ) {
		ArrayList<Project> p = pCtrl.getDAO().get();
		ArrayList<Contact> c = cCtrl.getDAO().get();
		this.pList = new JList(p.toArray());
		this.cList = new JList(c.toArray());
		this.pList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.cList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.pList.setVisibleRowCount(3);
		this.cList.setVisibleRowCount(3);
		GridLayout gl = new GridLayout(7, 4, 5, 5);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.pan.add(this.title);
		this.pan.add(this.name);
		this.pan.add(new JScrollPane(this.pList));
		this.pan.add(new JScrollPane(this.cList));
		final GroupForm self = this;
		this.submit.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Group g = new Group(self.getNameInput().getText());
	     		ArrayList<Project> projects = getProjectsFromList();
	     		ArrayList<Contact> users = getUsersFromList();
	    		
	     		if (!projects.isEmpty()) {
	     			g.linkProjects(projects);
	     		}
	     		if (!users.isEmpty()) {
	     			g.linkUsers(users);
	     		}
	     		gCtrl.getDAO().add(g);
	     	}
		});
		this.pan.add(this.submit);
	}
	
	public GroupForm(JPanel jPanel, final GroupController gCtrl, Group group) {
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		this.pan = jPanel;
		this.pan.setLayout(gl);
		this.title = new JLabel("Update contact n°" + group.getId().toString());
		this.pan.add(this.title);
		this.getNameInput().setText(group.getName());
		this.pan.add(this.name);
		final GroupForm self = this;
		final int id = group.getId();
		this.updateButton.setPreferredSize(this._vb.getButtonSize());
		this.updateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	    		final Group g = new Group(
	    					id,
	    					self.getNameInput().getText()
	    				);
	    		gCtrl.getDAO().update(g);
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
	public JButton getSubmit() {
		return this.submit;
	}
	public JTextField getNameInput() {
		return (JTextField)this.name.getComponent(1);
	}
	
	ArrayList<Project> getProjectsFromList() {
		List<?> projects =  this.pList.getSelectedValuesList();
		ArrayList<Project> aProjects = new ArrayList<Project>();
		for (Object project: projects) 
			aProjects.add((Project)project);
		return aProjects;
	}
	
	ArrayList<Contact> getUsersFromList() {
		List<?> contacts =  this.cList.getSelectedValuesList();
		ArrayList<Contact> aUsers = new ArrayList<Contact>();
		for (Object contact: contacts)
			aUsers.add((Contact)contact);
		return aUsers;
	}
}
