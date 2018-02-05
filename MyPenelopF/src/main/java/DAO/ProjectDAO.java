package DAO;

import java.util.ArrayList;
import java.util.Collection;

import DataInterface.DataInterface;
import Observer.ProjectListener;
import Observer.ProjectObserver;
import classes.Project;
import utils.PenelopDevLogger;

public class ProjectDAO extends DAO<Project> implements ProjectDAOReceipe, ProjectObserver  {

	private static final PenelopDevLogger log = PenelopDevLogger.get();
	private final Collection<ProjectListener> projectListeners = new ArrayList<ProjectListener>(); 
	
	public ProjectDAO(DataInterface di) {
		super(di);
	}

	@Override
	public boolean add(Project p) {
		ArrayList<Project> projects = this.di.readProjects();
		if (!projects.contains(p)) {
			projects.add(p);
			this.di.writeProjects(projects);
			log._("new project successfully added");
			this.triggerProjectChange();
			return true;
		} else {
			log._("ProjectDAO.add: project already exist");
			return false;
		}
	}

	@Override
	public boolean remove(Project p) {
		ArrayList<Project> projects = this.di.readProjects();
		for (int iterator = 0; iterator < projects.size(); iterator++) {
			Project project = projects.get(iterator); 
			if (project.getId() == p.getId()) {
				projects.remove(project);
				this.di.writeProjects(projects);
				log._("Project removed");
				this.triggerProjectChange();
				return true;
			}
		}
		log._("Project.remove: can't find project");
		return false;
	}

	@Override
	public boolean update(Project p) {
		ArrayList<Project> projects = this.di.readProjects();
		for (int iterator = 0; iterator < projects.size(); iterator++) {
			Project project = projects.get(iterator);
			if (project.getId() == p.getId()) {
				log._("ProjectDAO update project, project found!");
				int i = projects.indexOf(project);
				projects.set(i, p);
				this.di.writeProjects(projects);
				this.triggerProjectChange();
				return true;
			}
		}
		log._("ProjectDAO update project, project NOT found!");
		return false;
	}

	@Override
	public ArrayList<Project> get() {
		ArrayList<Project> projects = this.di.readProjects();
		/**
		 * association retrieving logic
		 */
		return projects;
	}

	// Observer pattern on DAO part
	public void addProjectListener(ProjectListener listener) {
		if (!this.projectListeners.contains(listener)) {
			this.projectListeners.add(listener);		
		}
	}

	public void removeProjectListener(ProjectListener listener) {
		if (this.projectListeners.contains(listener)) {
			this.projectListeners.remove(listener);
		}
	}

	public void triggerProjectChange() {
		for (ProjectListener listener: this.projectListeners) {
			listener.ProjectChangeTriggered();
		}
	}

	public void triggerShowUpdate(Project project) {
		// TODO Auto-generated method stub
		
	}

	public void triggerCreateProject(Project project) {
		// TODO Auto-generated method stub
		
	}

	public void triggerUpdateProject(Project project) {
		// TODO Auto-generated method stub
		
	}

	public void triggerDeleteProject(Project project) {
		// TODO Auto-generated method stub
		
	}
}