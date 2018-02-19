package classes;

public class Msgs {
	// Properties
	private Integer id;
	private String content;
	
	// Constructors
	public Msgs() {}
	public Msgs (Integer id) {
		this.id = id;
	}
	public Msgs(Integer id, String content) {
		this.id = id;
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
