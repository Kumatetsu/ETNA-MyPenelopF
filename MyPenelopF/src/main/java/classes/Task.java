package classes;

public class Task {
	private Integer id;
	
	private String description;
	
	private transient Project project;
	private Integer pId;

	static private int autoincrement = 0;
	static private int increment() {
		return ++Task.autoincrement;
	}
	
	public Task() {}
	
	public Task(Integer id) {
		this.id = id;
	}

	public Task(Integer id, String description) {
		this.id = id;
		this.description = description;
	}

	public Task(String description) {
		this.id = Task.increment();
		this.description = description;
	}
	
	public Task(Integer id, String description, Project project) {
		this.id = id;
		this.description = description;
		if (project != null && project.getId() > 0)
			this.setProject(project);
	}

	@Override
	public String toString() {
	    return this.description;
	}

	// Accessors 
	public Integer getId() {
		return this.id;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public Project getProject() {
		return this.project;
	}
	
	public Integer getPId() {
		return this.pId;
	}
	
	// Mutators
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setProject(Project p) {
		this.project = p;
		this.pId = p.getId();
	}
}
