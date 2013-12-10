package akka.remote.message;

import java.util.ArrayList;

public class Message{
	int type;
	String message;
	ArrayList<String> messages;
	
	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
	
	public Message(String message, int type) {
		this.type = type;
		this.message = message;
		this.messages = new ArrayList<String>();
		this.messages.add("new message in messasges arraylist");
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getMessageContents() {
		return message;
	}
	
	public void setMessageContents(String messageContents) {
		this.message = messageContents;
	}
}