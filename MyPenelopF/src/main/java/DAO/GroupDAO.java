package DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import DataInterface.DataInterface;
import Observer.GroupListener;
import Observer.GroupObserver;
import classes.Contact;
import classes.Group;

public class GroupDAO extends DAO<Group> implements GroupDAOReceipe, GroupObserver {

	private final Collection<GroupListener> groupListeners = new ArrayList<GroupListener>();

	public GroupDAO(DataInterface di) {
		super(di);
	}

	 private static class SingletonHolder
	    {
	        /** Instance unique non préinitialisée
	         * la classe interne ne sera chargée en mémoire
	         * que lorsque l'on y fera référence pour la première fois
	         * Permet de protéger d'un double appel en environnement multithreadé.
	         * C'est overkill pour le moment, mais c'est une bonne pratique.
	         * Dans le soucis de pouvoir faire évoluer l'app, cette implémentation
	         * anticipe les problèmes.
	         * Ici on appelle le singleton de la DataInterface FileSystemManager
	         * Si on passait sur une BDD classique, on enverrait ici un singleton
	         * différent mais respectant la DataInterface, contrat du bon déroulement
	         * du programme.
	        */
	        private static GroupDAO instance = null;
	        private final static GroupDAO get(DataInterface di) {
	        		instance = (instance == null) ? new GroupDAO(di) : instance;
	        		return instance;
	        }
	    }
	    public static GroupDAO getInstance(DataInterface di) {
	    	return SingletonHolder.get(di);
		}

		@Override
		public boolean add(Group g) {
			ArrayList<Group> groups;
			groups = this.get();
			groups.add(g);
			this.di.writeGroups(groups);
			this.triggerGroupChange();
			return true;
		}

		@Override
		public boolean remove(Group g) {
			ArrayList<Group> groups = new ArrayList<Group>();
			groups = this.get();
			for (int iterator = 0; iterator < groups.size(); iterator++) {
				Group group = groups.get(iterator);
				if (group.getId() == g.getId()) {
					groups.remove(group);
					this.di.writeGroups(groups);
					this.triggerGroupChange();
					return true;
				}
			}
			return false;
		}

		@Override
		public boolean update(Group g) {
			ArrayList<Group> groups = new ArrayList<Group>();
			groups = this.get();
			for (int iterator = 0; iterator < groups.size(); iterator++) {
				Group group = groups.get(iterator);
				if (group.getId() == g.getId()) {
					int i = groups.indexOf(group);
					groups.set(i, g);
					this.di.writeGroups(groups);
					this.triggerGroupChange();
					return true;
				}
			}
			return false;
		}

		@Override
		public ArrayList<Group> get() {
			ArrayList<Group> groups = this.di.readGroups();
			for (int iterator = 0; iterator < groups.size(); iterator++) {
				Group g = groups.get(iterator);
				g.setUsers(this.getContacts(g));
			}
			return groups;
		}

		public ArrayList<Contact> getContacts(Group g) {
			ArrayList<Contact> contacts = this.di.readContacts();
			ArrayList<Contact> gContacts = new ArrayList<Contact>();
			for (int iterator = 0; iterator < contacts.size(); iterator++) {
				Contact c = contacts.get(iterator);
				List<Integer>ids = c.getGIds();
				for (int it = 0; it < ids.size(); it++) {
					if (ids.get(it) == g.getId()) {
						gContacts.add(c);
					}
				}
			}
			return gContacts;
		}

		public void triggerGroupChange() {
			for (GroupListener listener: this.groupListeners) {
				listener.GroupChangeTriggered();
			}
		}
		public void addGroupListener(GroupListener listener) {
			this.groupListeners.add(listener);
		}
		public void removeGroupListener(GroupListener listener) {
			this.groupListeners.remove(listener);

		}
		public void triggerCreateGroup(Group group) {}
		public void triggerUpdateGroup(Group group) {}
		public void triggerDeleteGroup(Group group) {}

		public void triggerShowUpdate(Group group) {
		}

}
