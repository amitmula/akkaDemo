package akka.remote.message;

import java.util.ArrayList;

public class KMessage implements Message{
	int type;
	int count;
	long start,end;
	ArrayList<String> messages;
	
	public KMessage(int count, int type, long start) {
		this.type = type;
		this.count = count;
		this.start = start;
		this.messages = new ArrayList<String>();
		while(count !=0) {
			this.messages.add("printing message " + count + " times.");
			count--;
		}
	}
	
	public long getStart() {
		return start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
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