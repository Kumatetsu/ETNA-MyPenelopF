package ihm.group;

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

import classes.Group;
import controllers.ContactController;
import controllers.GroupController;
import controllers.ProjectController;
import controllers.TaskController;
import ihm.BaseFrame;
import ihm.ViewBuilder;
import ihm.contact.ContactPanel;
import utils.PenelopDevLogger;

public class GroupPanel extends JPanel {

	/**
	 * JPanel implementation requirement
	 */
	private static final long serialVersionUID = -4633209296424981048L;
	/**
	 * Logger
	 */
	final static PenelopDevLogger log = PenelopDevLogger.get();
	/**
	 * tools
	 */
	private ViewBuilder _vb = new ViewBuilder();
	/**
	 * main views
	 */
	private JPanel pan;
	private JPanel groupCards;
	private JPanel navigation;
    private CardLayout cl;
    /**
     * association
     */
	private ContactPanel contactPan;
	
	public GroupPanel(JPanel pan,
					  final GroupController gCtrl,
					  ContactController cCtrl,
					  ProjectController pCtrl,
					  TaskController tCtrl,
					  CardLayout cl,
					  ArrayList<Group> groups,
					  Boolean edit
					 ) {
		this.pan = pan;
	    this.pan.setLayout(new BorderLayout());
	    this.pan.setBorder(BorderFactory.createLineBorder(Color.black));
	    
		this.groupCards = new JPanel();
		this.cl = cl;
		this.groupCards.setLayout(this.cl);
	    this.groupCards.setBorder(BorderFactory.createLineBorder(Color.black));
	    this.navigation = this._vb.getNavPanel(this.cl, this.groupCards);
	    for (final Group group: groups) {
    		JPanel card = new JPanel();
    		JPanel groupCard = this.getDisplayGroup(group);
    		log._("GroupPanel constructor: ");
    		log.contacts(group.getUsers());
    		if (edit) {
    			this.contactPan =  new ContactPanel(new JPanel(),
						   						    cCtrl,
						   						    gCtrl,
						   						    pCtrl,
						   						    tCtrl,
						   						    new CardLayout(),
						   						    group.getUsers(),
						   						    false
							  					   );
    			JPanel btnPanel = this._vb.getTwoBtnPanel();
    			JButton del = new JButton("Delete");
    			del.setPreferredSize(this._vb.getButtonSize());
    			del.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent event) {
    					gCtrl.getDAO().remove(group);
    				}
    			});
    			JButton up = new JButton("Update");
    			up.setPreferredSize(this._vb.getButtonSize());
    			up.addActionListener(new ActionListener() {
    				public void actionPerformed(ActionEvent event) {
    					new BaseFrame(gCtrl, group);
    				}
    			});
    			btnPanel.add(up);
    			btnPanel.add(del);
        		card.add(this.contactPan.getRootPan());
    			card.add(btnPanel);
    		}
    		card.add(groupCard);
    		this.groupCards.add(card, group.getId().toString());
	    }
	    this.pan.add(this.navigation, BorderLayout.PAGE_START);
	    this.pan.add(this.groupCards, BorderLayout.CENTER);
	}

	/**
	 * Allow to retrieve the root point of the view
	 * @return
	 */
	public JPanel getRootPan() {
		return this.pan;
	}
	public JPanel getGroupCards() {
		return this.groupCards;
	}
	public CardLayout getCard() {
		return this.cl;
	}

	/**
	 * @param g
	 * @return a setted view to display one group
	 */
	private JPanel getDisplayGroup(Group g) {
		JPanel groupPanel = new JPanel();
		groupPanel.setLayout(new GridLayout(2, 2, 2, 2));
		groupPanel.setSize(new Dimension(300, 300));
		groupPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel idLab = new JLabel("Group n°: ");
		JLabel id = new JLabel(g.getId().toString());
		JLabel nameLab = new JLabel("Name: ");
		JLabel name = new JLabel(g.getName());
		groupPanel.add(idLab);
		groupPanel.add(id);
		groupPanel.add(nameLab);
		groupPanel.add(name);
		return groupPanel;
	}
}
