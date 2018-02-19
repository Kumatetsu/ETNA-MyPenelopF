package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DataInterface.DataInterface;
import DataInterface.FileSystemManager;
import Observer.ContactListener;
import Observer.ContactObserver;
import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import utils.PenelopDevLogger;

/**
 *
 * @author jean Classe permettant les opérations du CRUD sur l'entité Contact
 *         Implemente un pattern Singleton Implémente un pattern Observer
 *         Implémente un DAO de la DAOFactory implémente l'interface
 *         ContactDAOReceipe l'observer permet de déclencher un refresh du model
 *         lors d'une modification, le Controller enregistré comme listener est
 *         chargé de répercuté le model sur els vues (ContactController)
 */
public class ContactDAO extends DAO<Contact> implements ContactDAOReceipe, ContactObserver {

	private final PenelopDevLogger log = PenelopDevLogger.get();
	private final Collection<ContactListener> contactListeners = new ArrayList<ContactListener>();

	// Singleton implementation
	public ContactDAO(DataInterface di) {
		super(di);
	}

	/** Holder */
	private static class SingletonHolder {
		/**
		 * Instance unique non préinitialisée la classe interne ne sera chargée en
		 * mémoire que lorsque l'on y fera référence pour la première fois Permet de
		 * protéger d'un double appel en environnement multithreadé. C'est overkill pour
		 * le moment, mais c'est une bonne pratique. Dans le soucis de pouvoir faire
		 * évoluer l'app, cette implémentation anticipe les problèmes. Ici on appelle le
		 * singleton de la DataInterface FileSystemManager Si on passait sur une BDD
		 * classique, on enverrait ici un singleton différent mais respectant la
		 * DataInterface, contrat du bon déroulement du programme.
		 */
		private static ContactDAO instance = null;

		private final static ContactDAO get(DataInterface di) {
			instance = (instance == null) ? new ContactDAO(di) : instance;
			return instance;
		}
	}

	public static ContactDAO getInstance(DataInterface di) {
		return SingletonHolder.get(di);
	}

	public ArrayList<Contact> createDummyContacts() {
		ArrayList<Contact> al = new ArrayList<Contact>();
		Contact user1 = new Contact("test@etna-alternance.net", "Jean", "Billaud");
		GroupDAO gDAO = GroupDAO.getInstance(FileSystemManager.get());
		ArrayList<Group> groups = gDAO.get();
		user1.setGroups(groups);
		al.add(user1);
		this.di.writeContacts(al);
		Contact user2 = new Contact("test2@etna-alternance.net", "Aurel", "Castellarnau");
		al.add(user2);
		this.di.writeContacts(al);
		Contact user3 = new Contact("test3@etna-alternance.net", "Adolf", "Trump");
		al.add(user3);
		this.di.writeContacts(al);
		return al;
	}

	@Override
	public boolean add(Contact c) {
		ArrayList<Contact> users;
		users = this.get();
		users.add(c);
		this.di.writeContacts(users);
		this.triggerContactChange();
		return true;
	}

	@Override
	public boolean update(Contact c) {
		ArrayList<Contact> users = new ArrayList<Contact>();
		users = this.get();
		for (int iterator = 0; iterator < users.size(); iterator++) {
			Contact user = users.get(iterator);
			if (user.getId() == c.getId()) {
				int i = users.indexOf(user);
				users.set(i, c);
				this.di.writeContacts(users);
				this.triggerContactChange();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Contact c) {
		log._("into remove contact");
		ArrayList<Contact> users = new ArrayList<Contact>();
		users = this.get();
		log._("remove contact");
		for (int iterator = 0; iterator < users.size(); iterator++) {
			Contact user = users.get(iterator);
			if (user.getId() == c.getId()) {
				users.remove(user);
				this.di.writeContacts(users);
				this.triggerContactChange();
				return true;
			}
		}
		return false;
	}

	@Override
	public ArrayList<Contact> get() {
		ArrayList<Contact> contacts = this.di.readContacts();
		for (int iterator = 0; iterator < contacts.size(); iterator++) {
			Contact c = contacts.get(iterator);
			c.setGroups(this.getGroups(c));
			c.setProjects(this.getProjects(c));
			c.setMessages(this.getMsgs(c));
		}
		return contacts;
	}

	/**
	 * Retrieve Contact.groups from Contact.gIds
	 */
	public ArrayList<Group> getGroups(Contact c) {
		ArrayList<Group> groups = this.di.readGroups();
		ArrayList<Group> cGroups = new ArrayList<Group>();
		for (int iterator = 0; iterator < groups.size(); iterator++) {
			Group g = groups.get(iterator);
			List<Integer> ids = c.getGIds();
			for (int it = 0; it < ids.size(); it++) {
				if (ids.get(it) == g.getId()) {
					cGroups.add(g);
				}
			}
		}
		return cGroups;
	}

	/**
	 * Retrieve Contact.projects from Contact.pIds
	 */
	public ArrayList<Project> getProjects(Contact c) {
		ArrayList<Project> projects = this.di.readProjects();
		ArrayList<Project> cProjects = new ArrayList<Project>();
		for (int iterator = 0; iterator < projects.size(); iterator++) {
			Project p = projects.get(iterator);
			List<Integer> ids = c.getPIds();
			for (int it = 0; it < ids.size(); it++) {
				if (ids.get(it) == p.getId()) {
					cProjects.add(p);
				}
			}
		}
		return cProjects;
	}

	/**
	 * Retrieve Contact.messages from Contact.mIds
	 */
	public ArrayList<Msgs> getMsgs(Contact c) {
		ArrayList<Msgs> msgs = this.di.readMsgs();
		ArrayList<Msgs> cMsgs = new ArrayList<Msgs>();
		for (int iterator = 0; iterator < msgs.size(); iterator++) {
			Msgs m = msgs.get(iterator);
			List<Integer> ids = c.getMIds();
			for (int it = 0; it < ids.size(); it++) {
				if (ids.get(it) == m.getId()) {
					cMsgs.add(m);
				}
			}
		}
		return cMsgs;
	}

	// Observer pattern
	public void addContactListener(ContactListener listener) {
		this.contactListeners.add(listener);
	}

	public void removeContactListener(ContactListener listener) {
		this.contactListeners.remove(listener);
	}

	public void triggerContactChange() {
		for (ContactListener listener : this.contactListeners) {
			listener.ContactChangeTriggered();
		}
	}
}
