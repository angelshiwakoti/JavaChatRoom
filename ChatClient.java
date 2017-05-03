import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.net.*;
import javax.sound.sampled.*;

public class ChatClient extends Observable{

	ArrayList<ChatMessage> messageList;
	DataOutputStream outToServer;
	BufferedReader inFromUser;
	Socket clientSocket;

	public ChatClient(){
		messageList = new ArrayList<ChatMessage>();
	}

	public ArrayList<ChatMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(ArrayList<ChatMessage> messageList) {
		this.messageList = messageList;
	}

	public void updateChatDialog(){
		setChanged();
		//notify observers for change
		System.out.println("heree");
		notifyObservers();
	}

	public void startClient(String hostName, int portNumber, String nickName) throws UnknownHostException, IOException{

		String sentence;
		String modifiedSentence;

		inFromUser = new BufferedReader(new InputStreamReader(System.in));

		clientSocket = new Socket(hostName, portNumber);


		outToServer = new DataOutputStream(clientSocket.getOutputStream());

		//outToServer.writeBytes(nickName + " joined the chat. Say Hello to " + nickName + " :)"  + "\n");
		outToServer.writeBytes(nickName + "\n");

		Runnable clientTask = new Runnable() {
			@Override
			public void run() {

				try{
					callChatRoom();
				}
				catch(Exception e){
					
				}

				// while(true){
				// 	BufferedReader inFromServer;
				// 	try {
				// 		inFromServer = new BufferedReader(new InputStreamReader(
				// 				clientSocket.getInputStream()));
				// 		String msg = inFromServer.readLine();
				// 		System.out.println(msg);
				// 		if(msg != null && msg.length() != 0){
				// 			ChatMessage message = new ChatMessage();
				// 			if(msg.contains(":")){
				// 				int index = msg.indexOf(":");
				// 				message = new ChatMessage(msg.substring(0, index).toUpperCase(), msg.substring(index + 1, msg.length()), 2);
				// 			}
				// 			else{
				// 				message = new ChatMessage(msg, "", 1);
				// 			}
				// 			messageList.add(message);
				// 			if(messageList != null){
				// 				updateChatDialog();
				// 			}
				// 		}
						
						
				// 	} 
				// 	catch (IOException e) 
				// 	{
						
				// 	}
				// }

			}

		};
		Thread clientThread = new Thread(clientTask);
		clientThread.start();
	}

	public void sendMessageToServer(String message) throws IOException{
		outToServer.writeBytes(message + "\n");
		//172.18.8.152
	}

	public void callChatRoom() throws Exception{

		AudioFormat af = new AudioFormat(8000.0f,8,1,true,false);
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, af);
        TargetDataLine microphone = (TargetDataLine)AudioSystem.getLine(info);
        microphone.open(af);
        microphone.start();

        int bytesRead = 0;
        byte[] soundData = new byte[1];
        Thread inThread = new Thread(new SoundPlayer(clientSocket));
        inThread.start();

        while(bytesRead != -1)
        {
            bytesRead = microphone.read(soundData, 0, soundData.length);
            if(bytesRead >= 0)
            {
                outToServer.write(soundData, 0, bytesRead);
            }
        }

	}
}