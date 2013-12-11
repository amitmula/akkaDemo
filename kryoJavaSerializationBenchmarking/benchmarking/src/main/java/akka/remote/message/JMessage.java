package akka.remote.message;

import java.io.Serializable;
import java.util.ArrayList;

public class JMessage implements Serializable{

	private static final long serialVersionUID = 1L;
	ArrayList<String> messages;
	
	public JMessage(int count) {
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