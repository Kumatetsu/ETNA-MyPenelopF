package Observer;

public interface ProjectObserver {
	void addProjectListener(ProjectListener listener);
	void removeProjectListener(ProjectListener listener);
	void triggerProjectChange();
}
