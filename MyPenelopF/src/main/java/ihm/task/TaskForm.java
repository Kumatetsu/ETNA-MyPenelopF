package ihm.task;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import classes.Project;
import classes.Task;
import controllers.TaskController;
import ihm.ViewBuilder;

public class TaskForm extends JPanel {

	/**
	 * JPanel requirement
	 */
	private static final long serialVersionUID = -7385022145032316437L;
	private ViewBuilder _vb = new ViewBuilder();
	private JPanel pan;
	private JLabel title = new JLabel("Create New Task");
	private JButton createButton = new JButton("Create");
	private JButton updateButton = new JButton("Update");
	private JPanel description = this._vb.getTextField("Content Description");
	
	public TaskForm(JPanel pan, final TaskController tCtrl) {
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.pan.add(this.title);
		this.pan.add(this.description);
		final TaskForm self = this;
		this.createButton.setPreferredSize(this._vb.getButtonSize());
		this.createButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Task t = new Task(
	     					self.getDescriptionInput().getText()
	     				);
	     		tCtrl.getDAO().add(t);
	     	}
		});
		this.pan.add(this.createButton);

	}

	public TaskForm(JPanel pan, final TaskController tCtrl, Task task) {
		GridLayout gl = new GridLayout(5, 1, 5, 5);
		this.pan = pan;
		this.pan.setLayout(gl);
		this.title = new JLabel("Update task n°" + task.getId().toString());
		this.pan.add(this.title);
		this.pan.add(this.description);
		this.getDescriptionInput().setText(task.getDescription());
		final TaskForm self = this;
		final Integer id = task.getId();
		final Project project = task.getProject();
		this.updateButton.setPreferredSize(this._vb.getButtonSize());
		this.updateButton.addActionListener(new ActionListener() {
	     	public void actionPerformed(ActionEvent event) {
	     		Task t = new Task(
	     					id,
	     					self.getDescriptionInput().getText(),
	     					project
	     				);
	     		tCtrl.getDAO().update(t);
	     	}
		});
		this.pan.add(this.updateButton);
	}

	public JTextField getDescriptionInput() {
		return (JTextField)this.description.getComponent(1);
	}

	public JPanel getPan() {
		return this.pan;
	}
}
