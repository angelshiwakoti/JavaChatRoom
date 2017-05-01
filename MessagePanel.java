import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MessagePanel extends JPanel {
	protected JPanel mainPanel;
	protected JPanel inputsPanel;
	
	public MessagePanel(){
		super();
		setBorder(new EmptyBorder(0, 0, 0, 0));
		setLayout(new BorderLayout());
		repaint();  

		mainPanel = new JPanel(new BorderLayout());
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		inputsPanel = new JPanel(new GridLayout(0, 1, 2, 2));

		panel.add(inputsPanel);
		JScrollPane scroll = new JScrollPane(panel);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			
		mainPanel.add(scroll, BorderLayout.CENTER);
		add(mainPanel);

		addLabel(new ChatMessage("Angel", "Here is the text.", 2));
	}
	
	public void addLabel(ChatMessage message){
		int rowNum = ((GridLayout)inputsPanel.getLayout()).getRows() + 1;
		((GridLayout)inputsPanel.getLayout()).setRows(rowNum); 
		MessageForm field = new MessageForm(rowNum + 1, message);
		inputsPanel.add(field);
		refresh();
	}
	
	public void refresh() {
		validate();
		repaint();
	}
	

	public class MessageForm extends JPanel {
		public JLabel imageLabel;
		public JLabel usernameLabel;
		public JLabel messageLabel;
		
		public MessageForm(int rowNum, ChatMessage message){
			GridBagLayout gbl = new GridBagLayout();
			setLayout(gbl);
			setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
			setOpaque(true);

			GridBagConstraints gbc = new GridBagConstraints();

			try{
				BufferedImage myPicture = ImageIO.read(new File("avatar.png"));
				imageLabel = new JLabel(new ImageIcon(myPicture));
			}
			catch (IOException e) {

			}

			gbc = createGbc(0, 0, 2);
			add(imageLabel, gbc);
			
			
			usernameLabel = new JLabel("");
			gbc = createGbc(1, 0, 1);
			gbc.anchor = GridBagConstraints.SOUTHWEST;
			add(usernameLabel, gbc);
			
			messageLabel = new JLabel("");
			gbc = createGbc(1, 1, 1);
			gbc.anchor = GridBagConstraints.NORTHWEST;
			add(messageLabel, gbc);

			usernameLabel.setText("<HTML><strong>" + message.getUserName() + "</strong></HTML>");
			messageLabel.setText(message.getMessage());
			if(message.getType() == 1){
				setBackground(Color.GREEN);
				usernameLabel.setText("<HTML><strong>" + message.getUserName() + "</strong> joined the Chat Room :)</HTML>");
				messageLabel.setText(""); 
			}

			inputsPanel.setPreferredSize(new Dimension(500, rowNum * 40));

		}

		private GridBagConstraints createGbc(int x, int y, int h) {
      		GridBagConstraints gbc = new GridBagConstraints();
      		gbc.gridx = x;
      		gbc.gridy = y;
      		gbc.gridheight = h;
      		gbc.fill = GridBagConstraints.HORIZONTAL;

      		gbc.weightx = (x == 0) ? 0.1 : 1.0;
      		gbc.weighty = 1.0;
      		return gbc;
   		}

	}
}