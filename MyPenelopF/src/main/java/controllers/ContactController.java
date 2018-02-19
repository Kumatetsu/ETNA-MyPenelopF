package controllers;

import java.util.ArrayList;

import Observer.ContactListener;
import DAO.ContactDAO;
import classes.Contact;
import ihm.BaseFrame;
import ihm.dashboardPanel;
import utils.PenelopDevLogger;

/**
 * 
 * @author aurelien
 * ContactController
 * Initialize view using ihm package,
 * manage logic and FileSystem update using ContactUtils classe
 * implement observer pattern 
 * (interface CreateContactListener work with ihm.contact.CreateContact)
 * Calls on singletons.
 */
public class ContactController implements ContactListener, PenelopeController {
	// Singletons calls on utilitarie classes
	final static PenelopDevLogger log = PenelopDevLogger.get();
	private ContactDAO cDAO = null;
	// View elements
	public dashboardPanel dashboard;
	public BaseFrame uForm;
	// Constructor
	public ContactController(ContactDAO cDAO) {
		this.cDAO = cDAO;
	}
	// Constructor
	public ContactController(ContactDAO cDAO, dashboardPanel dashboard) {
		this.cDAO = cDAO;
		this.dashboard = dashboard;
	}
	// Link DAO and Controller
	public void init() {
		this.cDAO.addContactListener(this);
	}
	// DAO accessor
	final public ContactDAO getDAO() {
		return this.cDAO;
	}
	// Global View accessor
	public dashboardPanel getDashboard() {
		return this.dashboard;
	}
	// Global View mutator
	public void setDashboard(dashboardPanel dashboard) {
		this.dashboard = dashboard;
	}
	public void ContactChangeTriggered() {
        ArrayList<Contact> retrievedContacts = this.cDAO.get();
        log._("REFRESH CONTACT");
        log.contacts(retrievedContacts);
        this.dashboard.displayContactPanel();
	}
	public void testCtrl() {
		log._("INIT ContactController");
	}
}
