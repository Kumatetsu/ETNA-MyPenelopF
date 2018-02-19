package Observer;

public interface TaskObserver {
	void addTaskListener(TaskListener listener);
	void removeTaskListener(TaskListener listener);
	void triggerTaskChange();
}
