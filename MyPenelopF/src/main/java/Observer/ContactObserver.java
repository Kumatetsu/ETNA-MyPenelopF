package Observer;

public interface ContactObserver {
	void addContactListener(ContactListener listener);
	void removeContactListener(ContactListener listener);
	void triggerContactChange();
}
