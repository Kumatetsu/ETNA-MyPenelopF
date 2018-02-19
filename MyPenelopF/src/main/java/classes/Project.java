package classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DAO.ProjectDAO;
import DataInterface.FileSystemManager;

public class Project extends Item {

	private Integer id;

	private String name;

	private String description;

	private transient ArrayList<Group> groups = new ArrayList<Group>();
	private List<Integer> gIds = new ArrayList<Integer>();

	private transient ArrayList<Contact> users = new ArrayList<Contact>();
	private List<Integer> uIds = new ArrayList<Integer>();

	private transient ArrayList<Task> tasks = new ArrayList<Task>();
	private List<Integer> tIds = new ArrayList<Integer>();

	private Map<String, String> documents = new HashMap<String, String>();

	static private int autoincrement = 0;

	static private int increment() {
		return ++Project.autoincrement;
	}

	private int lastId() {
		ArrayList<Project> projects = ProjectDAO.getInstance(FileSystemManager.get()).get();
		int id = 0;
		if (users != null) {
			for (int iterator = 0; iterator < users.size(); iterator++) {
				Project p = projects.get(iterator);
				if (p.getId() > id)
					id = p.getId();
			}
		}
		return id;
	}

	public Project() {
		super();
		this.id = Project.increment();
	}

	/**
	 * Constructeur appelle quand le projet est cree depuis un nouveau dossier.
	 * 
	 * @param name
	 */
	public Project(String name) {
		Project.autoincrement = this.lastId();
		this.name = name;
	}

	public Project(String name, String description) {
		Project.autoincrement = this.lastId();
		this.id = Project.increment();
		this.name = name;
		this.description = description;
	}

	public Project(Integer id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Project(String name, String description, ArrayList<Group> groups, ArrayList<Contact>contacts, ArrayList<Task> tasks) {
		Project.autoincrement = this.lastId();
		this.id = Project.increment();
		this.name = name;
		this.description = description;
		this.setTasks(tasks);
		this.setGroups(groups);
		this.setContacts(contacts);		
	}

	public Project(Integer id, String name, String description, ArrayList<Group> groups, ArrayList<Contact>contacts, ArrayList<Task> tasks) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.setTasks(tasks);
		this.setGroups(groups);
		this.setContacts(contacts);		
	}
	
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<Group> getGroups() {
		return this.groups;
	}

	public List<Integer> getGIds() {
		return this.gIds;
	}

	public void LinkGroups(ArrayList<Group> groups) {
		for (Group group: groups) {
			this.gIds.add(group.getId());
		}
	}

	public void setGroups(ArrayList<Group> groups) {
		this.groups = groups;
	}

	public ArrayList<Contact> getContacts() {
		return this.users;
	}

	public List<Integer> getUIds() {
		return this.uIds;
	}

	public void LinkUsers(ArrayList<Contact> contacts) {
		for (Contact contact: contacts) {
			this.uIds.add(contact.getId());
		}
	}

	public ArrayList<Task> getTasks() {
		return this.tasks;
	}

	public List<Integer> getTIds() {
		return this.tIds;
	}

	public Map<String, String> getDocuments() {
		return this.documents;
	}

	// Contacts mutators
	public void setContacts(ArrayList<Contact> users) {
		this.users = users;
		for (int iterator = 0; iterator < this.users.size(); iterator++) {
			Integer id = this.users.get(iterator).getId();
			if (!this.uIds.contains(id))
				this.uIds.add(id);
		}
	}

	public void addContact(Contact c) {
		if (!this.users.contains(c)) {
			this.users.add(c);
		if (!this.uIds.contains(c.getId()))
			this.uIds.add(c.getId());
		}
	}

	public void deleteContact(Contact c) {
		if (this.users.contains(c)) {
			this.users.remove(c);
		if (this.uIds.contains(c.getId()))
			this.uIds.remove(c.getId());
		}
	}

	// Documents mutators
	public void setDocuments(ArrayList<Document> documents) {
		for (Document doc : documents) {
			this.documents.put(doc.getName(), doc.getPath());
		}
	}

	public void addDocument(Document d) {
		if (!this.documents.containsKey(d.getName())) {
			this.documents.put(d.getName(), d.getPath());
		}
	}

	public void deleteDocument(Document d) {
		if (this.documents.containsKey(d.getName())) {
			this.documents.remove(d.getName());
		}
	}

	// Task mutators
	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
		for (int iterator = 0; iterator < this.tasks.size(); iterator++) {
			Integer id = this.tasks.get(iterator).getId();
			if (!this.tIds.contains(id))
				this.tIds.add(id);
		}
	}

	public void addTask(Task t) {
		if (!this.tasks.contains(t)) {
			this.tasks.add(t);
		if (!this.tIds.contains(t.getId()))
			this.tIds.add(t.getId());
		}
	}

	public void deleteTask(Task task) {
		if (this.tasks.contains(task)) {
			this.tasks.remove(task);
		if (this.tIds.contains(task.getId()))
			this.tIds.remove(task.getId());
		}
	}

	@Override
	public String toString() {
		return name;
	}
}
