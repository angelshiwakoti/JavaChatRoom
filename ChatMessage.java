public class ChatMessage {
	private String message;
	private String userName;
	
	public ChatMessage(){
		this.message = "";
		this.userName = "";
	}
	
	public ChatMessage(String username, String message){
		this.message = message;
		this.userName = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
