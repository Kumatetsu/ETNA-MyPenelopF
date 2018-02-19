package DataInterface;

import java.util.ArrayList;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import classes.Task;

public interface DataInterface {
	public ArrayList<Contact>readContacts();
	public ArrayList<Project>readProjects();
	public ArrayList<Task>readTasks();
	public ArrayList<Group>readGroups();
	public ArrayList<Msgs>readMsgs();
	public void writeContacts(ArrayList<Contact> users);
	public void writeProjects(ArrayList<Project> projects);
	public void writeTasks(ArrayList<Task> tasks);
	public void writeGroups(ArrayList<Group> groups);
	public void writeMsgs(ArrayList<Msgs> msgs);
}
