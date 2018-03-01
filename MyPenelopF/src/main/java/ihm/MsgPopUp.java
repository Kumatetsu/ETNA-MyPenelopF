package ihm;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import DAO.ContactDAO;
import DAO.GroupDAO;
import DAO.MsgsDAO;
import DAO.ProjectDAO;
import classes.Contact;
import classes.Group;
import classes.Msgs;
import classes.Project;
import controllers.ContactController;
import controllers.GroupController;
import controllers.MsgsController;
import controllers.ProjectController;
import controllers.TaskController;
import ihm.contact.ContactForm;

public class MsgPopUp {
	
	/**
	 * JFrame implementation requirement
	 */
	private static final long serialVersionUID = 4648369832000775054L;
	
	private ViewBuilder _vb = new ViewBuilder();

	private JButton validateButton = new JButton("valider");
	
	/**
	 * Controllers
	 */
	private ContactController cCtrl;
	private GroupController   gCtrl;
	private ProjectController pCtrl;
	private TaskController    tCtrl;
	private MsgsController    mCtrl;
	
	
	public MsgPopUp(ContactController cCtrl, Contact c, MsgsController mCtrl) {
		this.cCtrl = cCtrl;
        JFrame frame = new JFrame("Contacts");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
        frame.setTitle("msg Contact: ");
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setLocation(250, 250);
        frame.setPreferredSize(new Dimension(400, 300));
        JPanel pan = messageContact(cCtrl, c, mCtrl, frame);
        pan.setLayout(gl);
        frame.getContentPane().add(pan, BorderLayout.CENTER);
        frame.setVisible(true);
	}
	
	public MsgPopUp(GroupController gCtrl, Group g, MsgsController mCtrl) {
		this.gCtrl = gCtrl;
        JFrame frame = new JFrame("Groups");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
        frame.setTitle("msg Group: ");
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setLocation(250, 250);
        JPanel updateGroup = messageGroup(gCtrl, g, mCtrl, frame);
        updateGroup.setLayout(gl);
        frame.add(updateGroup);
        frame.setVisible(true);
	}

	public MsgPopUp(ProjectController pCtrl, Project p, MsgsController mCtrl) {
		this.pCtrl = pCtrl;
        JFrame frame = new JFrame("Projects");
		GridLayout gl = new GridLayout(3, 2, 5, 5);
        frame.setTitle("Msg Projects: ");
        frame.setResizable(true);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setLocation(250, 250);
        JPanel updateProject = messageProject(pCtrl, p, mCtrl, frame);
        updateProject.setLayout(gl);
        frame.add(updateProject);
        frame.setVisible(true);
	}

	public JPanel messageContact (ContactController cCtrl, final Contact c, MsgsController mCtrl, final JFrame frame) {
		JPanel pan = new JPanel();
		final JTextField tf = new JTextField();
		final MsgsDAO mDAO = (MsgsDAO) mCtrl.getDAO();
		final ContactDAO cDAO = (ContactDAO)cCtrl.getDAO();
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		pan.setLayout(gl);
		pan.add(new JLabel("Ajoutez un message au user " + c.getId()));
		pan.add(tf);
		this.validateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Msgs message = new Msgs(tf.getText());
	     		mDAO.add(message);
	     		System.out.println("message add in messageContact");
	     		c.addMessage(message);
	     		cDAO.update(c);
	     		frame.dispose();
	     	}
		});
		pan.add(validateButton);
		return pan;
	}
	
	public JPanel messageGroup (GroupController gCtrl, final Group g, MsgsController mCtrl, final JFrame frame) {
		JPanel pan = new JPanel();
		final MsgsDAO mDAO = (MsgsDAO) mCtrl.getDAO();
		final GroupDAO gDAO = (GroupDAO) gCtrl.getDAO();
		final JTextField tf = new JTextField();
		this.validateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Msgs message = new Msgs(tf.getText());
	     		mDAO.add(message);
	     		System.out.println("message add in messageGroup");
	     		g.addMessage(message);
	     		gDAO.update(g);
	     		frame.dispose();
	     	}
		});
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		pan.setLayout(gl);
		pan.add(new JLabel("Ajoutez un message au group " + g.getId()));
		pan.add(tf);
		pan.add(validateButton);
		return pan;
	}
	
	public JPanel messageProject (ProjectController pCtrl, final Project p, MsgsController mCtrl, final JFrame frame) {
		JPanel pan = new JPanel();
		final MsgsDAO mDAO = (MsgsDAO) mCtrl.getDAO();
		final ProjectDAO pDAO = (ProjectDAO)pCtrl.getDAO();
		final JTextField tf = new JTextField();
		this.validateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
		     		Msgs message = new Msgs(tf.getText());
		     		mDAO.add(message);
		     		System.out.println("message add in messageGroup");
		     		p.addMessage(message);
		     		pDAO.update(p);
		     		frame.dispose();
	     	}
		});
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		pan.setLayout(gl);
		pan.add(new JLabel("Ajoutez un message au project " + p.getId()));
		pan.add(tf);
		pan.add(validateButton);
		return pan;
	}
}
