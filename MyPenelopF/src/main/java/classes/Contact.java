/**
 *
 */
package classes;

import java.util.ArrayList;

import DAO.ContactDAO;
import DataInterface.FileSystemManager;
import classes.Contact;
import classes.Group;

/**
 * @author aurelien
 *
 */
public class Contact extends Item {

	// contact id
	private Integer id;

	// contact email
	private String email;

	// contact surname
	private String surname;

	// contact name
	private String name;


	// contact groups
	private transient ArrayList<Group> groups = new ArrayList<Group>();
	private ArrayList<Integer> gIds = new ArrayList<Integer>();


	// contact projects
	private transient ArrayList<Project> projects = new ArrayList<Project>();
	private ArrayList<Integer> pIds = new ArrayList<Integer>();

	// Database auto-increment id simulation, must be enhanced by a "retrieveLastId" method
	static private int autoincrement = 0;
	static private int increment() {
		return ++Contact.autoincrement;
	}
	private int lastId() {
		ArrayList<Contact> users = ContactDAO.getInstance(FileSystemManager.get()).get();
		int id = 0;
		if (users != null) {
			for (int iterator = 0; iterator < users.size(); iterator++) {
				Contact c = users.get(iterator);
				if (c.getId() > id)
					id = c.getId();
				}
		}
		return id;
	}
	// default constructor
	public Contact() { super(); }
	// surcharged minimal constructor
	public Contact(String email, String surname, String name) {
		super();
		Contact.autoincrement = this.lastId();
		this.id = Contact.increment();
		this.email = email;
		this.name = name;
		this.surname = surname;
	}
	// surcharged 'existing user' constructor
	public Contact(int id, String email, String surname, String name) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
		// Logic to retrieve groups is in ContactDAO
	}
	// surcharged 'no-project' constructor
	public Contact(String email, String surname, String name, ArrayList<Group> groups, ArrayList<Project> projects, ArrayList<Msgs> messages) {
		super(messages);
		Contact.autoincrement = this.lastId();
		this.id = Contact.increment();
		this.email = email;
		this.surname = surname;
		this.name = name;
		this.setGroups(groups);
		this.setProjects(projects);
		this.setMessages(messages);
	}
	// surcharged 'full' constructor
	public Contact(Integer id, String email, String surname, String name, ArrayList<Group> groups, ArrayList<Project>projects, ArrayList<Msgs> messages) {
		super(messages);
		this.id = id;
		this.email = email;
		this.surname = surname;
		this.name = name;
		this.setProjects(projects);
		this.setGroups(groups);
		this.setMessages(messages);
	}

	@Override
	public String toString() {
	    return name;
	}

	// accessors
	public Integer getId() {
		return id;
	}
	public String getEmail() {
		return this.email;
	}
	public String getSurname() {
		return this.surname;
	}
	public String getName() {
		return this.name;
	}

	public ArrayList<Group> getGroups() {
		return this.groups;
	}
	public ArrayList<Integer> getGIds() {
		return this.gIds;
	}


	public ArrayList<Project> getProjects() {
		return this.projects;
	}
	public ArrayList<Integer> getPIds() {
		return this.pIds;
	}

	// mutators
	public void setId(Integer id) {
		this.id = id;
	}
	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
		for (int iterator = 0; iterator < this.groups.size(); iterator++) {
			Integer id = this.groups.get(iterator).getId();
			if (!this.gIds.contains(id))
				this.gIds.add(id);
		}
	}
	public void addGroup(Group g) {
		if (!this.groups.contains(g)) {
			this.groups.add(g);
			if (!this.gIds.contains(g.getId()))
				this.gIds.add(g.getId());
		}
	}
	public void deleteGroup(Group g) {
		if (this.groups.contains(g)) {
			this.groups.remove(g);
			Integer id = g.getId();
			if (this.gIds.contains(id))
				this.gIds.remove(id);
		}
	}
	public void setProjects(ArrayList<Project> projects) {
		this.projects = projects;
		for (int iterator = 0; iterator < this.projects.size(); iterator++) {
			Integer id = this.projects.get(iterator).getId();
			if (!this.pIds.contains(id))
				this.pIds.add(id);
		}
	}
	public void addProject(Project p) {
		if (!this.projects.contains(p)) {
			this.projects.add(p);
			Integer id = p.getId();
			if (!this.pIds.contains(id))
				this.pIds.add(id);
		}
	}
	public void deleteProject(Project p) {
		if (this.projects.contains(p)) {
			this.projects.remove(p);
			Integer id = p.getId();
			if (this.pIds.contains(id))
				this.pIds.remove(id);
		}
	}
}
