package DataInterface;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import classes.Task;
import utils.PenelopDevLogger;

/**
 *
 * @author jean
 *
 */
public class FileSystemManager implements DataInterface {

	private static final PenelopDevLogger log = PenelopDevLogger.get();
	private String project_path = System.getProperty("user.dir");
	private String contactFile = "/contact.json";
	private String groupFile = "/group.json";
	private String projectFile = "/project.json";
	private String taskFile = "/task.json";
	private String msgFile = "/msg.json";
	private FileSystemManager() {}

	private static class SingletonHolder
	{
		private final static FileSystemManager instance = new FileSystemManager();
	}
	public static FileSystemManager get() {
		return SingletonHolder.instance;
	}

	public ArrayList<Contact>readContacts() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + this.contactFile));
			Contact[] json = new Gson().fromJson(bufferedReader, Contact[].class);
			ArrayList<Contact> contacts = json != null ? new ArrayList<Contact>(Arrays.asList(json)) : new ArrayList<Contact>();
			return contacts;
		} catch (IOException e) {
			log._("Throwed in readContacts: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Group>readGroups() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + this.groupFile));
			Group[] json = new Gson().fromJson(bufferedReader, Group[].class);
			ArrayList<Group> groups = json != null ? new ArrayList<Group>(Arrays.asList(json)) : new ArrayList<Group>();
			return groups;
		} catch (IOException e) {
			log._("Throwed in readGroups: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Project>readProjects() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + this.projectFile));
			Project[] json = new Gson().fromJson(bufferedReader, Project[].class);
			ArrayList<Project> projects = json != null ? new ArrayList<Project>(Arrays.asList(json)) : new ArrayList<Project>();
			return projects;
		} catch (IOException e) {
			log._("Throwed in readGroups: " + e.getMessage());
			return null;
		}
	}

	public ArrayList<Task>readTasks() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + this.taskFile));
			Task[] json = new Gson().fromJson(bufferedReader, Task[].class);
			ArrayList<Task> tasks = json != null ? new ArrayList<Task>(Arrays.asList(json)) : new ArrayList<Task>();
			return tasks;
		} catch (IOException e) {
			log._("Throwed in readGroups: " + e.getMessage());
			return null;
		}
	}
	public ArrayList<Msgs>readMsgs() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + this.msgFile));
			Msgs[] json = new Gson().fromJson(bufferedReader, Msgs[].class);
			ArrayList<Msgs> msgs = json != null ? new ArrayList<Msgs>(Arrays.asList(json)) : new ArrayList<Msgs>();
			return msgs;
		} catch (IOException e) {
			log._("Throwed in readGroups: " + e.getMessage());
			return null;
		}
	}
	/**
	 * Wrtie the new contacts List in the file groups
	 * @param users
	 */
	public void writeContacts(ArrayList<Contact> users) {
		Gson gson = new Gson();
		String json = gson.toJson(users);
		try {
			FileWriter fileWriter = new FileWriter(this.project_path + this.contactFile);
    		fileWriter.write(json);
            fileWriter.close();
		} catch (IOException e) {
			log._("Exception trhowed in writeContacts(): " + e.getMessage());
		}
	}

	public ArrayList<Contact> getContacts() {
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/users.json"));
			Contact[] json = new Gson().fromJson(bufferedReader, Contact[].class);
			ArrayList<Contact> contacts = json != null ? new ArrayList<Contact>(Arrays.asList(json)) : new ArrayList<Contact>();
			return contacts;
		} catch (IOException e) {
			log._("Throwed in get Contacts: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Write the new groups List in the file groups.
	 * @param groups
	 */
	public void writeGroups(ArrayList<Group> groups) {
		Gson gson = new Gson();
    	String json = gson.toJson(groups);
		try {
			FileWriter fileWriter = new FileWriter(this.project_path + this.groupFile);
    		fileWriter.write(json);
            fileWriter.close();
		} catch (IOException e) {
			log._("Exception trhowed in writeContacts(): " + e.getMessage());
		}
	}

	/**
	 * Write the new projects List in the file projects.json.
	 * @param projects
	 */
	public void writeProjects(ArrayList<Project> projects) {
		Gson gson = new Gson();
    		String json = gson.toJson(projects);
		try {
			FileWriter fileWriter = new FileWriter(this.project_path + this.projectFile);
    			fileWriter.write(json);
            fileWriter.close();
		} catch (IOException e) {
			log._("Exception trhowed in writeContacts(): " + e.getMessage());
		}
	}

	/**
	 * Write the new projects List in the file projects.json.
	 * @param projects
	 */
	public void writeTasks(ArrayList<Task> tasks) {
		Gson gson = new Gson();
    	String json = gson.toJson(tasks);
		try {
			FileWriter fileWriter = new FileWriter(this.project_path + this.taskFile);
    		fileWriter.write(json);
            fileWriter.close();
		} catch (IOException e) {
			log._("Exception trhowed in writeContacts(): " + e.getMessage());
		}
	}
	/**
	 * Write the new projects List in the file projects.json.
	 * @param projects
	 */
	public void writeMsgs(ArrayList<Msgs> msgs) {
		Gson gson = new Gson();
    	String json = gson.toJson(msgs);
		try {
			FileWriter fileWriter = new FileWriter(this.project_path + this.msgFile);
    		fileWriter.write(json);
            fileWriter.close();
		} catch (IOException e) {
			log._("Exception trhowed in writeContacts(): " + e.getMessage());
		}
	}
}
