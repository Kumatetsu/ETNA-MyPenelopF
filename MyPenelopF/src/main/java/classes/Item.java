package classes;

import java.util.ArrayList;

public abstract class Item {
	// abstract messages
	private transient ArrayList<Msgs> messages = new ArrayList<Msgs>();
	private ArrayList<Integer> mIds = new ArrayList<Integer>();

	public Item() {}
	public Item(ArrayList<Msgs> msgs) {
		if (msgs != null && !msgs.isEmpty()) {
			this.setMessages(msgs);	
		}
	}
	public ArrayList<Msgs> getMessages() {
		return this.messages;
	}
	public ArrayList<Integer> getMIds() {
		return this.mIds;
	}
	public void setMessages(ArrayList<Msgs> messages) {
		this.messages = messages;
		for (int iterator = 0; iterator < this.messages.size(); iterator++) {
			Msgs msg = this.messages.get(iterator);
			this.mIds.add(msg.getId());
		}
	}
	public void addMessage(Msgs m) {
		if (!this.messages.contains(m)) {
			this.messages.add(m);
		if (!this.mIds.contains(m.getId()))
			this.mIds.add(m.getId());
		}
	}
	public void deleteMessage(Msgs m) {
		if (this.messages.contains(m)) {
			this.messages.remove(m);
		if (this.mIds.contains(m.getId()))
			this.mIds.remove(m.getId());
		}
	}
}
