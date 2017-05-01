public class ChatMessage {
	private String message;
	private String userName;
	private int type;
	
	public ChatMessage(){
		this.message = "";
		this.userName = "";
		type = 0;
	}
	
	public ChatMessage(String username, String message, int type){
		this.message = message;
		this.userName = username;
		this.type = type;
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
	
	public int getType(){
		return type;
	}
}
