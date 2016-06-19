package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import model.Country;

public class MainFrame extends JFrame{
	public MainFrame(ArrayList<Country> countries){
		setSize(new Dimension(800,600));
		setLocationRelativeTo(null);
		setTitle("Eurovision Prediction");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		final PanelZemlje pz = new PanelZemlje(countries);
		panel.add(pz, BorderLayout.WEST);
		ImageIcon icon = new ImageIcon("images/img.png"); 
		JLabel thumb = new JLabel();
		thumb.setIcon(icon);
		thumb.setSize(800, 200);
		thumb.setPreferredSize(new Dimension(800, 200));
		panel.add(thumb, BorderLayout.NORTH);
		JButton btn = new JButton("Predict");
		final JTextField text = new JTextField();
		btn.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  if(pz.getListCheckBox().get(0).isSelected())
				  text.setText("opaca");
		  }
		});
		panel.add(btn, BorderLayout.SOUTH);
		panel.add(text, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(panel);
		this.add(scrollPane);
	}
}
