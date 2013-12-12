package akka.remote.message;

import java.util.ArrayList;

public class Message{
	int type;
	int count;
	ArrayList<String> messages;
	
	public Message(int count, int type) {
		this.type = type;
		this.count = count;
		this.messages = new ArrayList<String>();
		for(int i=0; i<count; i++) {
			this.messages.add("printing message " + this.count + " times.");
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}

	public ArrayList<String> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<String> messages) {
		this.messages = messages;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
}