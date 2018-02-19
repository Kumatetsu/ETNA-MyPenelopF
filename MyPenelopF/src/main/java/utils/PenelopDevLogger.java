package utils;

import java.util.ArrayList;
import java.util.List;

import classes.Contact;
import classes.Group;
import classes.Project;
import classes.Task;

public class PenelopDevLogger {

	private PenelopDevLogger() {
	}

	private static class SingletonHolder {
		private final static PenelopDevLogger instance = new PenelopDevLogger();
	}

	public static PenelopDevLogger get() {
		return SingletonHolder.instance;
	}

	public void _(String str) {
		System.out.println(str);
	}

	public void _(Contact c) {
		System.out.println("User n°" + c.getId());
		System.out.println("Email: " + c.getEmail());
		System.out.println("Surname: " + c.getSurname());
		System.out.println("Name: " + c.getName());
		ArrayList<Group> groups = c.getGroups();
		this._("Contact related Groups: ");
		this.groups(groups);
	}

	public void _(Group g) {
		System.out.println("Group n°" + g.getId());
		System.out.println("Name: " + g.getName());
		System.out.println("Users in group: ");
		List<Integer> ids = g.getUIds();
		for (int iterator = 0; iterator < ids.size(); iterator++) {
			Integer id = ids.get(iterator);
			System.out.println("group identifier: " + id + "\n");
		}
	}

	public void _(Project p) {
		System.out.println("Project n°" + p.getId());
		System.out.println("Name: " + p.getName());
		System.out.println("Description: " + p.getDescription());
		List<Integer> gIds = p.getGIds();
		for (int iterator = 0; iterator < gIds.size(); iterator++) {
			Integer id = gIds.get(iterator);
			System.out.println("Project associated to group n°" + id + "\n");
		}
	}

	public void _(Task t) {
		System.out.println("Task n°" + t.getId());
	}

	public void contacts(ArrayList<Contact> contacts) {
		for (Integer iterator = 0; iterator < contacts.size(); iterator++) {
			Contact c = contacts.get(iterator);
			this._(c);
		}
	}

	public void groups(ArrayList<Group> groups) {
		for (Integer iterator = 0; iterator < groups.size(); iterator++)
			this._(groups.get(iterator));
	}

	public void projects(ArrayList<Project> projects) {
		for (Integer iterator = 0; iterator < projects.size(); iterator++)
			this._(projects.get(iterator));
	}

	public void tasks(ArrayList<Task> tasks) {
		for (Integer iterator = 0; iterator < tasks.size(); iterator++)
			this._(tasks.get(iterator));
	}
}
