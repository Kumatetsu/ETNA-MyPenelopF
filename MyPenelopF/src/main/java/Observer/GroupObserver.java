package Observer;

public interface GroupObserver {
	void addGroupListener(GroupListener listener);
	void removeGroupListener(GroupListener listener);
	void triggerGroupChange();
}
