package ihm.contact;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import controllers.ContactController;
import controllers.GroupController;
import controllers.MsgsController;
import controllers.ProjectController;
import controllers.TaskController;
import ihm.BaseFrame;
import ihm.MsgPopUp;
import ihm.ViewBuilder;
import ihm.group.GroupPanel;
import ihm.project.ProjectPanel;
import utils.PenelopDevLogger;

/**
 *
 * @author aurelien
 * ContactPanel define the JPanel to display an
 * ArrayList of Contact
 * It take an external JPanel and CardLayout
 * to allow us to reuse those container
 * from controller to add further logic
 */
public class ContactPanel extends JPanel {

	/**
	 * JPanel implementation requirement
	 */
	private static final long serialVersionUID = 1L;
	private static final PenelopDevLogger log = PenelopDevLogger.get();
	private JPanel pan;
	private JPanel contactCards;
	private GroupPanel groupPan;
	private ProjectPanel projectPan;
	private ViewBuilder _vb;
    private CardLayout cl;
    private JPanel navigation;
    
	public ContactPanel(
						JPanel pan,
						final ContactController cCtrl,
						GroupController gCtrl,
						ProjectController pCtrl,
						TaskController tCtrl,
						final MsgsController mCtrl,
						CardLayout cl, 
						ArrayList<Contact> users,
						boolean edit
	) {
        this._vb = new ViewBuilder();
        this.pan = pan;
        this.pan.setLayout(new BorderLayout());
        this.pan.setBorder(BorderFactory.createLineBorder(Color.black));
		this.cl = cl;
		this.contactCards = new JPanel();
		this.contactCards.setLayout(this.cl);
        this.contactCards.setBorder(BorderFactory.createLineBorder(Color.black));
	    this.navigation = this._vb.getNavPanel(this.cl, this.contactCards);
	    for (final Contact user: users)
	    {
	    	// Card and Panel init + layout
	    	JPanel gridPan = new JPanel();
	    	JPanel card = new JPanel();
	        JPanel userPan = new JPanel();
			GridLayout gl = new GridLayout(2, 2, 5, 5);
			gridPan.setLayout(gl);
			// groupPanel definition
	    	ArrayList<Group> userGroups = user.getGroups();
	    	this.groupPan = new GroupPanel(new JPanel(),
	    								   gCtrl,
	    								   cCtrl,
	    								   pCtrl,
	    								   tCtrl,
	    								   mCtrl,
	    								   new CardLayout(),
	    								   userGroups,
	    								   false
	    								  );
	    	// projectPanel definition
	    	ArrayList<Project> userProjects = user.getProjects();
	    	this.projectPan = new ProjectPanel(new JPanel(),
	    									   pCtrl,
	    									   cCtrl,
	    									   gCtrl,
	    									   tCtrl,
	    									   mCtrl,
	    									   new CardLayout(),
	    									   userProjects,
	    									   false
	    									  );
	        // contact content
	        JPanel contactCard = this.buildContactDisplay(user);
	        
	        // related messages
	        ArrayList<Msgs> messages = user.getMessages();
	    	DefaultListModel<String> model = new DefaultListModel<String>();
	    	JList list = new JList(model);
	    	for (int iterator = 0; iterator < messages.size(); iterator++) {
	    		model.addElement(messages.get(iterator).getContent());
	    	}
	    	userPan.add(contactCard);
	        // Delete and Update rely on listeners
	        if (edit) {
		        JPanel boutonPane = this._vb.getTwoBtnPanel();
	        	JButton del = new JButton("Delete");
	        	del.setPreferredSize(this._vb.getButtonSize());
	        	del.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent event) {
	        			cCtrl.getDAO().remove(user);
	        		}
	        	});
	        	JButton up = new JButton("Update");
	        	up.setPreferredSize(this._vb.getButtonSize());
	        	up.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent event) {
	        			new BaseFrame(cCtrl, user);
	        		}
	        	});
	        	JButton msg = new JButton("add msg");
	        	msg.setPreferredSize(this._vb.getButtonSize());
	        	msg.addActionListener(new ActionListener() {
	        		public void actionPerformed(ActionEvent event) {
	        			System.out.println("in contractPanel add msg");
	        			new MsgPopUp(cCtrl, user, mCtrl);
	        		}
	        	});
	        	boutonPane.setLayout(new GridLayout(3,1));
	        	boutonPane.add(up);
	        	boutonPane.add(del);
	        	boutonPane.add(msg);
	        	userPan.add(boutonPane);
	        }
		JScrollPane scrollableList = new JScrollPane(list);
        	gridPan.add(userPan);
        	gridPan.add(groupPan.getRootPan());
        	gridPan.add(projectPan.getRootPan());
        	gridPan.add(scrollableList);
	    card.add(gridPan);
	    this.contactCards.add(card, user.getId().toString());
	   }
	    this.pan.add(this.navigation, BorderLayout.PAGE_START);
	    this.pan.add(this.contactCards, BorderLayout.CENTER);
	}

	/**
	 * Allow to retrieve the root point of the view
	 * @return
	 */
	public JPanel getRootPan() {
		return this.pan;
	}

	// Individual components accessors
	public JPanel getContactCardsPan() {
		return this.pan;
	}
	public CardLayout getCard() {
		return this.cl;
	}
	
	public JPanel getNavigation() {
		return this.navigation;
	}

	/**
	 * @param cs
	 * @return a setted view to display one contact
	 */
	private JPanel buildContactDisplay(Contact c) {
		JPanel contactPanel = new JPanel();
		contactPanel.setLayout(new GridLayout(8, 2, 2, 2));
		contactPanel.setSize(new Dimension(500, 500));
		contactPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel nameLab = new JLabel("Name: ");
		JLabel name = new JLabel(c.getName());
		JLabel surnameLab = new JLabel("Surname: ");
		JLabel surname = new JLabel(c.getSurname());
		JLabel emailLab = new JLabel("Email: ");
		JLabel email = new JLabel(c.getEmail());
		contactPanel.add(nameLab);
		contactPanel.add(name);
		contactPanel.add(surnameLab);
		contactPanel.add(surname);
		contactPanel.add(emailLab);
		contactPanel.add(email);
		return contactPanel;
	}
}
