import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.*;


/** Test JTextField, JPasswordField, JFormattedTextField, JTextArea */
@SuppressWarnings("serial")
public class ChatClientGUI extends JFrame implements Observer{

	// Private variables of the GUI components
	JTextField hostNameField;
	JTextField portNumberField;
	JTextArea tArea;
	JTextField nickNameField;
	JButton joinChatButton;
	JPanel tfPanel;
	MessagePanel messagePanel;
	static ChatClient controller;
	JLabel messageArea;

	/** Constructor to set up all the GUI components */
	public ChatClientGUI() {

		messagePanel = new MessagePanel();

		renderLoginScreen();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chat Room");
		setSize(500, 700);
		setVisible(true);
	}

	public void renderLoginScreen(){
		// JPanel for the text fields
		tfPanel = new JPanel(new GridLayout(4, 2, 10, 2));
		tfPanel.setBorder(BorderFactory.createTitledBorder("Chat Room Credentials: "));

		// Regular text field (Row 1)
		tfPanel.add(new JLabel("  Host Name: "));
		hostNameField = new JTextField(10);
		hostNameField.setText("127.0.0.1");
		hostNameField.setMaximumSize(new Dimension(30, 30));
		tfPanel.add(hostNameField);

		// Password field (Row 2)
		tfPanel.add(new JLabel("  Port Number: "));
		portNumberField = new JTextField(10);
		portNumberField.setText("6789");
		tfPanel.add(portNumberField);

		// Formatted text field (Row 3)
		tfPanel.add(new JLabel("  Chat Nickname: "));
		nickNameField = new JTextField(10);
		nickNameField.setText("Aditi");
		tfPanel.add(nickNameField);

		joinChatButton = new JButton("Join Chat");
		tfPanel.add(joinChatButton);
		joinChatButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						renderChatScreen();
					}
				});

				try {
					controller.startClient(hostNameField.getText(), Integer.parseInt(portNumberField.getText()), 
							nickNameField.getText());
				} catch (NumberFormatException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout(5, 5));
		cp.add(tfPanel, BorderLayout.NORTH);
	}

	public void renderChatScreen(){
		this.getContentPane().remove(tfPanel);

		tfPanel = new JPanel(new GridLayout(4, 2, 10, 2));
		tfPanel.setBorder(BorderFactory.createTitledBorder("Type Message Below: "));

		// Regular text field (Row 1)
		final JTextField messageField = new JTextField(10);
		tfPanel.add(messageField);
		JButton submitButton = new JButton("Submit");
		tfPanel.add(submitButton);
		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(messageField.getText().length() != 0){
					try {
						controller.sendMessageToServer(messageField.getText());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});



		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout(0, 0));
		cp.add(messagePanel, BorderLayout.CENTER);
		cp.add(tfPanel, BorderLayout.SOUTH);

		getRootPane().revalidate();
		getRootPane().repaint();
	}

	/** The entry main() method */
	public static void main(String[] args) {
		// Run GUI codes in Event-Dispatching thread for thread safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ChatClientGUI gui= new ChatClientGUI();  // Let the constructor do the job
				controller = new ChatClient();
				controller.addObserver(gui);
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				messagePanel.addLabel(controller.getMessageList().get(controller.getMessageList().size() - 1));
			}
		});
	}
}
