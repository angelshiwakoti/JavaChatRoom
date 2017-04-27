import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

	static private ArrayList<ClientTask> clients;
	private int portNumber;
	private ServerSocket serverSocket;
	
	public ChatServer(int portNumber){
		this.setClients(new ArrayList<ClientTask>());
		this.portNumber = portNumber;
	}
	
	public void startServer() {
		final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

		Runnable serverTask = new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(portNumber);
					System.out.println("Waiting for clients to connect...");
					while (true) {
						Socket clientSocket = serverSocket.accept();
						BufferedReader inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						DataOutputStream outToClient = new DataOutputStream(clientSocket.getOutputStream());
						clientProcessingPool.submit(new ClientTask(clientSocket, inFromClient, outToClient));
					}
				} catch (IOException e) {
					System.err.println("Unable to process client request");
					e.printStackTrace();
				}
			}
		};
		Thread serverThread = new Thread(serverTask);
		serverThread.start();

	}

	private class ClientTask implements Runnable{
		private final Socket clientSocket;
		private BufferedReader inFromClient;
		private DataOutputStream outToClient;

		private ClientTask(Socket clientSocket, BufferedReader inFromClient, DataOutputStream outToClient) {
			this.clientSocket = clientSocket;
			this.inFromClient = inFromClient;
			this.outToClient = outToClient;
			getClients().add(this);
		}

		@Override
		public void run(){
			System.out.println("Got a client !");

			while(true){
				String clientSentence = "";
				try {
					clientSentence = this.inFromClient.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				System.out.println("RECEIVED FROM CLIENT: " + clientSentence);
				System.out.println("here " + clients.size());
				ChatMessage message = new ChatMessage("", clientSentence);

				String capitalizedSentence = clientSentence.toUpperCase() + "\n"; 

				try {
					for(ClientTask task: getClients()){
							task.outToClient.writeBytes(capitalizedSentence);
					}
					//outToClient.writeBytes();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}	
		}
	}
	
	public void closeServer() throws IOException{
		serverSocket.close();
	}

	public static ArrayList<ClientTask> getClients() {
		return clients;
	}

	public static void setClients(ArrayList<ClientTask> clients) {
		ChatServer.clients = clients;
	}
	
}
