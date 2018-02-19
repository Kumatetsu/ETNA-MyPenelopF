package DAO;

import java.util.ArrayList;

import DataInterface.DataInterface;
import Observer.TaskListener;
import Observer.TaskObserver;
import classes.Task;
import utils.PenelopDevLogger;

public class TaskDAO extends DAO<Task> implements TaskObserver, TaskDAOReceipe {
	
	private final PenelopDevLogger log = PenelopDevLogger.get();
	private final ArrayList<TaskListener> taskListeners = new ArrayList<TaskListener>();

	public TaskDAO(DataInterface di) {
		super(di);
	}

	@Override
	public boolean add(Task t) {
		ArrayList<Task> tasks = this.di.readTasks();
		if (!tasks.contains(t)) {
			tasks.add(t);
			this.di.writeTasks(tasks);
			log._("Task added");
			this.triggerTaskChange();
			return true;
		}
		log._("Task.add: task already exist");
		return false;
	}

	@Override
	public boolean remove(Task t) {
		ArrayList<Task> tasks = this.di.readTasks();
		log._("REMOVE TASK: ");
		log._("entry: ");
		log._(t);
		log._("tasks: ");
		log.tasks(tasks);
		if (tasks.contains(t)) {
			tasks.remove(t);
			this.di.writeTasks(tasks);
			log._("Task removed");
			this.triggerTaskChange();
			return true;
		}
		log._("Task.remove: can't find task");
		return false;
	}

	@Override
	public boolean update(Task t) {
		ArrayList<Task> tasks = this.di.readTasks();
		log._("update TASK: ");
		log._("entry: ");
		log._(t);
		log._("tasks: ");
		log.tasks(tasks);
		for (int iterator = 0; iterator < tasks.size(); iterator++) {
			Task task = tasks.get(iterator);
			if (task.getId() == t.getId()) {
				int i = tasks.indexOf(task);
				tasks.set(i, t);
				this.di.writeTasks(tasks);
				this.triggerTaskChange();
				log._("Task.update: task updated");
			}
		}
		return false;
	}

	@Override
	public ArrayList<Task> get() {
		ArrayList<Task> tasks = this.di.readTasks();
		return tasks;
	}

	// Observer Pattern
	public void addTaskListener(TaskListener listener) {
		if (!this.taskListeners.contains(listener)) {
			this.taskListeners.add(listener);
		}
	}

	public void removeTaskListener(TaskListener listener) {
		if (this.taskListeners.contains(listener)) {
			this.taskListeners.remove(listener);
		}
	}

	public void triggerTaskChange() {
		for (int iterator = 0; iterator < this.taskListeners.size(); iterator++) {
			this.taskListeners.get(iterator).TaskChangeTriggered();
		}
	}
}
