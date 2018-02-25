package DAO;

import java.util.ArrayList;
import DataInterface.DataInterface;
import classes.Msgs;

public class MsgsDAO extends DAO<Msgs> implements MsgsDAOReceipe {

	public MsgsDAO(DataInterface di) {
		super(di);
	}
	
	 private static class SingletonHolder {
	        /** Instance unique non préinitialisée
	         * la classe interne ne sera chargée en mémoire
	         * que lorsque l'on y fera référence pour la première fois
	         * Permet de protéger d'un double appel en environnement multithreadé.
	         * C'est overkill pour le moment, mais c'est une bonne pratique.
	         * Dans le soucis de pouvoir faire évoluer l'app, cette implémentation
	         * anticipe les problèmes.
	         * Ici on appelle le singleton de la DataInterface FileSystemManager
	         * Si on passait sur une BDD classique, on enverrait ici un singleton
	         * différent mais respectant la DataInterface, contrat du bon déroulement
	         * du programme.
	        */
		 private static MsgsDAO instance = null;
		 private final static MsgsDAO get(DataInterface di) {
			 instance = (instance == null) ? new MsgsDAO(di) : instance;
			 return instance;
		 }
	 }
	 
	 public static MsgsDAO getInstance(DataInterface di) {
	    	return SingletonHolder.get(di);
	 }

	@Override
	public boolean add(Msgs obj) {
		ArrayList<Msgs> messages;
		messages = this.get();
		messages.add(obj);
		this.di.writeMsgs(messages);
		return true;
	}

	@Override
	public boolean remove(Msgs obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Msgs obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Msgs> get() {
		ArrayList<Msgs> msgs = this.di.readMsgs();
		return msgs;
	}

}
