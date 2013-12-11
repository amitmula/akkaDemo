package akka.remote.message;

import java.util.ArrayList;

public class KMessage{
	
	ArrayList<String> messages;
	
	public KMessage() {}
	public KMessage(int count) {
		this.messages = new ArrayList<String>();
		for(int i=0; i<count; i++) {
			this.messages.add("printing message " + count + " times.");
		}
	}
	
	public ArrayList<String> getMessages() {
		return messages;
	}
	
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
}