package ihm.task;

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

import classes.Task;
import controllers.TaskController;
import ihm.BaseFrame;
import ihm.ViewBuilder;
// import utils.PenelopDevLogger;

public class TaskPanel extends JPanel {

	/**
	 * JPanel requirement
	 */
	private static final long serialVersionUID = -2146571445384665490L;
	// private static final PenelopDevLogger log = PenelopDevLogger.get();
	private TaskController tCtrl;
    private ViewBuilder _vb = new ViewBuilder();
	private JPanel pan;
	private JPanel taskCards;
	private JPanel navigation;
	private CardLayout cl;

	public TaskPanel(JPanel pan, final TaskController tCtrl, CardLayout cl, ArrayList<Task> tasks, Boolean edit) {
		this.tCtrl = tCtrl;
		this.pan = pan;
	    this.pan.setLayout(new BorderLayout());
	    this.pan.setBorder(BorderFactory.createLineBorder(Color.black));
	    this.taskCards = new JPanel();
		this.cl = cl;
		this.taskCards.setLayout(this.cl);
	    this.taskCards.setBorder(BorderFactory.createLineBorder(Color.black));
	    this.navigation = this._vb.getNavPanel(this.cl, this.taskCards);
	    if (tasks != null)
			this.buildTaskCards(tasks, edit);
		this.pan.add(this.navigation, BorderLayout.PAGE_START);
		this.pan.add(this.taskCards, BorderLayout.CENTER);
	}

	/**
	 * Allow to retrieve the root point of the view
	 * @return
	 */
	public JPanel getRootPan() {
		return this.pan;
	}
	
	public JPanel getCard() {
		return this.taskCards;
	}
	
	/**
	 * @param t
	 * @return a setted view to display one task
	 */
	private JPanel displayTask(Task t) {
		JPanel taskPanel = new JPanel();
		taskPanel.setLayout(new GridLayout(3, 2, 2, 2));
		taskPanel.setSize(new Dimension(3000, 3000));
		taskPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		JLabel idLab = new JLabel("Task n°: ");
		JLabel id = new JLabel(t.getId().toString());
		JLabel descriptionLab = new JLabel("Description: ");
		JLabel description = new JLabel(t.getDescription());
		taskPanel.add(idLab);
		taskPanel.add(id);
		taskPanel.add(descriptionLab);
		taskPanel.add(description);
		return taskPanel;
	}
	
	/**
	 * Build the cards swing component 
	 * @param tasks
	 * @param edit
	 */
	private void buildTaskCards(ArrayList<Task> tasks, Boolean edit) {
		final TaskPanel self = this;
		for (final Task task: tasks)
		{
			JPanel card = new JPanel();
			card.add(this.displayTask(task));
			if (edit) {
				JPanel btnPanel = this._vb.getTwoBtnPanel();
				JButton del = new JButton("Delete");
				del.setSize(this._vb.getButtonSize());
				del.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						self.tCtrl.getDAO().remove(task);
					}
				});
				JButton up = new JButton("Update");
				up.setSize(this._vb.getButtonSize());
				up.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent event) {
    					new BaseFrame(tCtrl, task);
					}
				});
				btnPanel.add(up);
				btnPanel.add(del);
				card.add(btnPanel);
			}
			this.taskCards.add(card, task.getId().toString());
		}
	}
}
