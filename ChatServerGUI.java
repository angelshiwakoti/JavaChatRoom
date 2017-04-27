import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

public class ChatServerGUI extends JPanel {

	private static final long serialVersionUID = 1L;
    private static JTextField portTextField;


    public ChatServerGUI() {
        super(new BorderLayout());
        
        // Panel for the labels
        JPanel labelPanel = new JPanel(new GridLayout(2, 10)); // 2 rows 1 column
        add(labelPanel, BorderLayout.WEST);

        // Panel for the fields
        JPanel fieldPanel = new JPanel(new GridLayout(2, 10)); // 2 rows 1 column
        add(fieldPanel, BorderLayout.CENTER);

        // Textfield
        JLabel portLabel= new JLabel("  Server Port Number  ");
        portTextField = new JTextField(10);

        // Add labels
        labelPanel.add(portLabel);

        // Add fields
        fieldPanel.add(portTextField);
    }

    public static void main(String[] args) {
        final ChatServerGUI form = new ChatServerGUI();

        // Button submit
        JButton submit = new JButton("Start Server");
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	ChatServer server = null;
            	if(((JButton)e.getSource()).getText().equals("Start Server")){
            		server = new ChatServer(Integer.parseInt(portTextField.getText()));
                    server.startServer();
                    ((JButton)e.getSource()).setText("Close Server");
            	}
            	else if(((JButton)e.getSource()).getText().equals("Close Server")){
            		((JButton)e.getSource()).setText("Start Server");
            	}
            }
        });

        // Frame for our test
        JFrame f = new JFrame("ChatRoom Server");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(form, BorderLayout.NORTH);

        // Panel with the button
        JPanel p = new JPanel();
        p.add(submit);
        f.getContentPane().add(p, BorderLayout.SOUTH);

        // Show the frame
        f.pack();
        f.setVisible(true);
    }
}
