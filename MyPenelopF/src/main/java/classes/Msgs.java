package classes;

import java.util.ArrayList;

import DAO.MsgsDAO;
import DataInterface.FileSystemManager;

public class Msgs {
	// Properties
	private Integer id;
	private String content;
	
	static private int autoincrement = 0;
	static private int increment() {
		return ++Msgs.autoincrement;
	}
	private int lastId() {
		ArrayList<Msgs> messages = MsgsDAO.getInstance(FileSystemManager.get()).get();
		int id = 0;
		if (messages != null) {
			for (int iterator = 0; iterator < messages.size(); iterator++) {
				Msgs m = messages.get(iterator);
				if (m.getId() > id)
					id = m.getId() + 1;
				}
		}
		return id;
	}
	
	// Constructors
	public Msgs() {}

	public Msgs(String content) {
		Msgs.autoincrement = this.lastId();
		this.id = Msgs.increment();
		this.content = content;
	}
	
	// Accessors
	public Integer getId() {
		return this.id;
	}
	public String getContent() {
		return this.content;
	}
	
	// Mutators
	public void setId(Integer id) {
		this.id = id;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
