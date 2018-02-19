package ihm;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author aurelien
 * Utilitarie class giving fast
 * access to form and reusable
 * ihm components
 */
public class ViewBuilder {
	
	public Dimension getButtonSize() {
		return new Dimension(100, 30);
	}
	
	public JPanel getTwoBtnPanel() {
	     JPanel boutonPane = new JPanel();
	     Dimension panSize = new Dimension(300, 100);
	     boutonPane.setBounds(2, 2, 20, 10);
	     boutonPane.setLayout(new FlowLayout());
	     boutonPane.setSize(panSize);
	     return boutonPane;
	}
	
	/**
	 * 
	 * @param cl
	 * @param pan
	 * @return JPanel containing two buttons
	 * linked to the CardLayout/JPanel navigation
	 * those buttons call next and prev on CardLayout content
	 */
	public JPanel getNavPanel(final CardLayout cl, final JPanel pan) {
		JPanel boutonPane = this.getTwoBtnPanel();
	    JButton next = new JButton("Next");
	    next.setPreferredSize(this.getButtonSize());
	    next.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent event){
	    		cl.next(pan);
	    	}
	    });
	    JButton prev = new JButton("Previous");
	    prev.setPreferredSize(this.getButtonSize());
	    prev.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent event) {
	    		cl.previous(pan);
	    	}
	    });
	    boutonPane.add(prev);
	    boutonPane.add(next);
	    return boutonPane;
	}
	
	/**
	 * 
	 * @param item
	 * @return JPanel containing a JTextField
	 * and a label setted with item
	 */
	public JPanel getTextField(String item) {
		JPanel nPanel = new JPanel();
		JLabel nLabel = new JLabel(item);
		JTextField tf = new JTextField();
		tf.setColumns(50);
		nPanel.setLayout(new GridLayout(2, 1, 5, 5));
		nPanel.add(nLabel);
		nPanel.add(tf);
		return nPanel;
	}
}
