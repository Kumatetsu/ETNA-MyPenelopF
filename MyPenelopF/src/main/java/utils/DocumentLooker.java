package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import classes.Document;
import classes.Project;
import controllers.ProjectController;

public class DocumentLooker implements Runnable {

	private String project_path = System.getProperty("user.dir");
	private String documents_directory = "/documents";
	private File folder = new File(project_path + documents_directory);
	private File[] listOfFiles = folder.listFiles();
	private ProjectController pCtrl = null;

	public DocumentLooker(ProjectController pCtrl) {
		this.pCtrl = pCtrl;
	}

	public void run() {
		Project currentP = null;

		for (File file : listOfFiles) {
			if (file.isFile()) {
				// System.out.println("File " + file.getName());
			} else if (file.isDirectory()) {
				if (null == (currentP = this.pCtrl.getDAO().getOneProject(file.getName())))
					createProjectFromDocuments(file, currentP);
				else
					inspectProjectDirectory(file, currentP);
			}
		}
	}

	private void createProjectFromDocuments(File folder, Project p) {
		System.out.println("In create Project Documents for " + folder.getName());

		p = new Project(folder.getName());
		File[] listOfProjectFiles = folder.listFiles();
		ArrayList<Document> d = new ArrayList<Document>();

		for (File file : listOfProjectFiles) {
			Document document = new Document(file.getName(), file.getPath());
			d.add(document);
		}

		p.setDocuments(d);
		this.pCtrl.getDAO().add(p);
		// System.out.println("Project add");
	}

	private void inspectProjectDirectory(File folder, Project p) {
		File[] listOfProjectFiles = folder.listFiles();
		Map<String, String> pDocuments = p.getDocuments();
		boolean modified = false;

		for (File file : listOfProjectFiles) {
			if (!pDocuments.containsKey(file.getName())) {
				p.addDocument(new Document(file.getName(), file.getPath()));
				modified = true;
			}
		}

		pDocuments = p.getDocuments();

		// System.out.println("check if list is real");
		try {
		for (Map.Entry<String, String> entry : pDocuments.entrySet()) {
			boolean exists = false;
			for (File file : listOfProjectFiles) {
				if (file.getName().equals(entry.getKey())) {
					exists = true;
					break;
				}
			}
			if (!exists) {
				// System.out.println("Delete because exists is false");
				Document d = new Document(entry.getKey(), entry.getValue());
				// System.out.println("doc created");
				p.deleteDocument(d);
				// System.out.println("doc deletef from project");
				modified = true;
			}
			// System.out.println(entry.getValue());
		}
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// System.out.println(modified);

		if (true == modified) {
			// System.out.println("update project");
			this.pCtrl.getDAO().update(p);
		}
	}
}
