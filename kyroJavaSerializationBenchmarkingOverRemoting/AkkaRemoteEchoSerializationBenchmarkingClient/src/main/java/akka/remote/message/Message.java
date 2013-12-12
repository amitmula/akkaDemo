package akka.remote.message;

import java.util.ArrayList;

public interface Message {
	public long getStart();
	public long getEnd();
	public void setEnd(long end);
	public int getCount();
	public void setCount(int count);
	public ArrayList<String> getMessages();
	public void setMessages(ArrayList<String> messages);
	public int getType();
	public void setType(int type);
}