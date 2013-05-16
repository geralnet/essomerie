package net.geral.essomerie.callerid.simulator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;

import net.geral.essomerie.callerid.CallerIdSubmitter;

public class CallerID_Simulator extends JFrame {
    private static final long serialVersionUID = 1L;

    public static void main(final String[] args) {
	SwingUtilities.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		new CallerID_Simulator();
	    }
	});
    }

    private final JTextField txtServer;
    private final JTextField txtType;
    private final JLabel lblLine;
    private final JTextField txtLine;
    private final JLabel lblNumber;

    private final JTextField txtNumber;

    public CallerID_Simulator() {
	setTitle("Caller ID Simulation");
	setSize(300, 300);
	setLocationRelativeTo(null);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	final SpringLayout springLayout = new SpringLayout();
	getContentPane().setLayout(springLayout);

	final JLabel lblServer = new JLabel("Server:");
	springLayout.putConstraint(SpringLayout.NORTH, lblServer, 10,
		SpringLayout.NORTH, getContentPane());
	springLayout.putConstraint(SpringLayout.WEST, lblServer, 10,
		SpringLayout.WEST, getContentPane());
	getContentPane().add(lblServer);

	txtServer = new JTextField();
	springLayout.putConstraint(SpringLayout.NORTH, txtServer, 6,
		SpringLayout.SOUTH, lblServer);
	springLayout.putConstraint(SpringLayout.WEST, txtServer, 10,
		SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, txtServer, -10,
		SpringLayout.EAST, getContentPane());
	txtServer.setText("127.0.0.1:2255");
	getContentPane().add(txtServer);
	txtServer.setColumns(10);

	final JLabel lblType = new JLabel("Type:");
	springLayout.putConstraint(SpringLayout.NORTH, lblType, 6,
		SpringLayout.SOUTH, txtServer);
	springLayout.putConstraint(SpringLayout.WEST, lblType, 0,
		SpringLayout.WEST, lblServer);
	getContentPane().add(lblType);

	txtType = new JTextField();
	springLayout.putConstraint(SpringLayout.NORTH, txtType, 6,
		SpringLayout.SOUTH, lblType);
	springLayout.putConstraint(SpringLayout.WEST, txtType, 0,
		SpringLayout.WEST, lblServer);
	springLayout.putConstraint(SpringLayout.EAST, txtType, 0,
		SpringLayout.EAST, txtServer);
	txtType.setText("IN");
	getContentPane().add(txtType);
	txtType.setColumns(10);

	lblLine = new JLabel("Line:");
	springLayout.putConstraint(SpringLayout.NORTH, lblLine, 6,
		SpringLayout.SOUTH, txtType);
	springLayout.putConstraint(SpringLayout.WEST, lblLine, 0,
		SpringLayout.WEST, lblServer);
	getContentPane().add(lblLine);

	txtLine = new JTextField();
	springLayout.putConstraint(SpringLayout.NORTH, txtLine, 6,
		SpringLayout.SOUTH, lblLine);
	springLayout.putConstraint(SpringLayout.WEST, txtLine, 10,
		SpringLayout.WEST, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, txtLine, -10,
		SpringLayout.EAST, getContentPane());
	txtLine.setText("Line1");
	getContentPane().add(txtLine);
	txtLine.setColumns(10);

	lblNumber = new JLabel("Number:");
	springLayout.putConstraint(SpringLayout.NORTH, lblNumber, 6,
		SpringLayout.SOUTH, txtLine);
	springLayout.putConstraint(SpringLayout.WEST, lblNumber, 0,
		SpringLayout.WEST, lblServer);
	getContentPane().add(lblNumber);

	txtNumber = new JTextField();
	springLayout.putConstraint(SpringLayout.EAST, txtNumber, 0,
		SpringLayout.EAST, txtServer);
	txtNumber.setText("(12) 3456-7890");
	springLayout.putConstraint(SpringLayout.NORTH, txtNumber, 6,
		SpringLayout.SOUTH, lblNumber);
	springLayout.putConstraint(SpringLayout.WEST, txtNumber, 10,
		SpringLayout.WEST, getContentPane());
	getContentPane().add(txtNumber);
	txtNumber.setColumns(10);

	final JButton btnSubmit = new JButton("Submit");
	btnSubmit.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(final ActionEvent e) {
		submit();
	    }
	});
	springLayout.putConstraint(SpringLayout.SOUTH, btnSubmit, -10,
		SpringLayout.SOUTH, getContentPane());
	springLayout.putConstraint(SpringLayout.EAST, btnSubmit, -10,
		SpringLayout.EAST, getContentPane());
	getContentPane().add(btnSubmit);
	setVisible(true);
    }

    private void submit() {
	final String server = txtServer.getText();
	final String line = txtLine.getText();
	final String type = txtType.getText();
	final String number = txtNumber.getText();
	final CallerIdSubmitter submitter = new CallerIdSubmitter(server, line,
		type, number);
	submitter.start();
    }
}
