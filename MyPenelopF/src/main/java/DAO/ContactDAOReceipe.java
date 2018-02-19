package DAO;

import java.util.ArrayList;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;

/**
 * 
 * @author aurelien
 * Permet de définir ce que DOIT implémenter la classe ContactDAO
 * Dans le cas d'une refonte, la classe abstraite DAO et cette interface
 * décrivent comment doit se comporter ContactDAO. 
 */
public interface ContactDAOReceipe {
	/**
	 *  retrieve groups where c.id appear
	 *  don't use Contact.gIds but Group.uIds
	 * @param c
	 * @return ArrayList<Group> 
	 */
	public ArrayList<Group> getGroups(Contact c);
	/**
	 *  retrieve projects where c.id appear
	 *  don't use Contact.pIds but Project.uIds
	 * @param c
	 * @return ArrayList<Project>
	 */
	public ArrayList<Project> getProjects(Contact c);
	/**
	 * retrieve messages where c.[Item].mIds contain Msg.id
	 * use Contact.mIds from contact.json to retrieve abstracted messages
	 * @param c
	 * @return ArrayList<Msg>
	 */
	public ArrayList<Msgs> getMsgs(Contact c);
}
