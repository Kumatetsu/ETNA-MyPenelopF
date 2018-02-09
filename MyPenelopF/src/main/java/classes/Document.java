package classes;

public class Document {
	
	private String name;
	
	private String path;
	
	static private int autoincrement = 0;
	static private int increment() {
		return ++Document.autoincrement;
	}
	
	// Object new Document constructor
	public Document(String name, String path) {
		this.name = name;
		this.path = path;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return this.path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
