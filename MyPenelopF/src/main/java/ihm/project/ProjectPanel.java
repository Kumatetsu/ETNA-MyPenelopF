package ihm.project;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import classes.Task;
import controllers.ContactController;
import controllers.GroupController;
import controllers.ProjectController;
import controllers.TaskController;
import ihm.BaseFrame;
import ihm.ViewBuilder;
import ihm.contact.ContactPanel;
import ihm.group.GroupPanel;
import ihm.task.TaskPanel;
// import utils.PenelopDevLogger;

public class ProjectPanel extends JPanel {

	/**
	 * JPanel implementation requirement
	 */
	private static final long serialVersionUID = -1951975065883803116L;
	/**
	 * ProjectController
	 */
	private ProjectController pCtrl;
	/**
	 * tools
	 */
	// private static final PenelopDevLogger log = PenelopDevLogger.get();
    private ViewBuilder _vb = new ViewBuilder();
    /**
     * main JPanel
     */
	private JPanel pan;
	private JPanel projectCards;
	private JPanel navigation;
	/**
	 * Associations
	 */
	private GroupPanel groupPan;
	private ContactPanel contactPan;
	private TaskPanel taskPan;
	/**
	 * Layout
	 */
    private CardLayout cl;

	public ProjectPanel(JPanel pan,
						final ProjectController pCtrl,
						ContactController cCtrl,
						GroupController gCtrl,
						TaskController tCtrl,
						CardLayout cl,
						ArrayList<Project> projects,
						boolean edit
					   ) {
		final ProjectPanel self = this;
		this.pCtrl = pCtrl;
		this.pan = pan;
	    this.pan.setLayout(new BorderLayout());
	    this.pan.setBorder(BorderFactory.createLineBorder(Color.black));
	    
		this.projectCards = new JPanel();
		this.cl = cl;
		this.projectCards.setLayout(this.cl);
	    this.projectCards.setBorder(BorderFactory.createLineBorder(Color.black));
	    this.navigation = this._vb.getNavPanel(this.cl, this.projectCards);
		for (final Project project: projects) {
			// Card and Panel init + layout
			JPanel card = new JPanel();
			JPanel projectPan = new JPanel();
			projectPan.setLayout(new GridLayout(1, 8, 2, 2));
			// project content
			projectPan.add(this.displayProject(project));
			if (edit) {
				// Associations
				ArrayList<Group> groups = project.getGroups();
				ArrayList<Contact> contacts = project.getContacts();
				ArrayList<Task> tasks = project.getTasks();
			    this.taskPan = new TaskPanel(new JPanel(),
			    							 tCtrl,
			    							 new CardLayout(),
			    							 tasks,
			    							 true
			    							);
			    this.groupPan = new GroupPanel(new JPanel(),
			    							   gCtrl,
			    							   cCtrl,
			    							   pCtrl,
			    							   tCtrl,
			    							   new CardLayout(),
			    							   groups,
			    							   false
			    							  );
			    this.contactPan = new ContactPanel(new JPanel(),
			    								   cCtrl,
			       								   gCtrl,
			       								   this.pCtrl,
			       								   tCtrl,
			       								   new CardLayout(),
			       								   contacts,
			       								   false
			       								  );
			   	
			    // related messages ===================
			    ArrayList<Msgs> messages = project.getMessages();
			   	JPanel compiledMsgs = new JPanel();
			   	if (messages.size() > 0) {
			   		compiledMsgs.add(new JLabel("Message: "));
			   	}
			   	for (int iterator = 0; iterator < messages.size(); iterator++) {
			   		compiledMsgs.add(new JLabel(messages.get(iterator).getContent()));
			   	}
			   	projectPan.add(compiledMsgs);
			    // ====================================
			   	
			   	JPanel btnPanel = this._vb.getTwoBtnPanel();
				JButton del = new JButton("Delete");
				del.setPreferredSize(this._vb.getButtonSize());
				del.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						self.pCtrl.getDAO().remove(project);
					}
				});
				JButton up = new JButton("Update");
				up.setPreferredSize(this._vb.getButtonSize());
				up.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
    					new BaseFrame(pCtrl, project);
					}
				});
				btnPanel.add(up);
				btnPanel.add(del);
			    projectPan.add(this.groupPan.getRootPan());
			    projectPan.add(this.taskPan.getRootPan());
			    projectPan.add(this.contactPan.getRootPan());
				projectPan.add(btnPanel);
			}
			card.add(projectPan);
			this.projectCards.add(card, project.getId().toString());
		}
		this.pan.add(this.navigation, BorderLayout.PAGE_START);
		this.pan.add(this.projectCards, BorderLayout.CENTER);
	}

	/**
	 * Allow to retrieve the root point of the view
	 * @return
	 */
	public JPanel getRootPan() {
		return this.pan;
	}

	public JPanel getProjectCards() {
		return this.projectCards;
	}
	public CardLayout getCard() {
		return this.cl;
	}

	/**
	 * @param p
	 * @return a setted view to display one project
	 */
	private JPanel displayProject(Project p) {
		JPanel projectPanel = new JPanel();
		projectPanel.setLayout(new GridLayout(3, 2, 2, 2));
		projectPanel.setSize(new Dimension(3000, 3000));
		projectPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel idLab = new JLabel("Project n°: ");
		JLabel id = new JLabel(p.getId().toString());
		JLabel nameLab = new JLabel("Name: ");
		JLabel name = new JLabel(p.getName());
		JLabel descriptionLab = new JLabel("Description: ");
		JLabel description = new JLabel(p.getDescription());
		projectPanel.add(idLab);
		projectPanel.add(id);
		projectPanel.add(nameLab);
		projectPanel.add(name);
		projectPanel.add(descriptionLab);
		projectPanel.add(description);
		return projectPanel;
	}
}
