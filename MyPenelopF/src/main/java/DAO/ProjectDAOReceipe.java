package DAO;

import java.util.ArrayList;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import classes.Task;

public interface ProjectDAOReceipe {
	/**
	 *  retrieve groups where p.id appear
	 *  don't use Project.gIds but Group.pIds
	 * @param p Project
	 * @return ArrayList<Group> 
	 */
	public ArrayList<Group> getGroups(Project p);
	/**
	 *  retrieve contacts where p.id appear
	 *  don't use Project.uIds but Contact.pIds
	 * @param p Project
	 * @return ArrayList<Contact> 
	 */
	public ArrayList<Contact> getContacts(Project p);
	/**
	 *  retrieve tasks where Task.id into Project.tIds
	 * @param p Project
	 * @return ArrayList<Task> 
	 */
	public ArrayList<Task> getTasks(Project p);
	/**
	 * retrieve messages where p.[Item].mIds contain Msg.id
	 * use Project.mIds from project.json to retrieve abstracted messages
	 * @param p
	 * @return ArrayList<Msg>
	 */
	public ArrayList<Msgs> getMsgs(Project p);
}
