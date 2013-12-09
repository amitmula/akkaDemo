package akka.remote.message;

import java.io.Serializable;

public class JMessage implements Serializable{
	
	private static final long serialVersionUID = 1L;
	int type;
	String message;
	
	int type1;
	String message1;
	
	int type2;
	String message2;
	
	int type3;
	String message3;
	
	int type4;
	String message4;
	
	int type5;
	String message5;
	
	int type6;
	String message6;
	
	int type7;
	String message7;
	
	int type8;
	String message8;
	
	int type9;
	String message9;
	
	int type10;
	String message10;
	
	int type11;
	String message11;
	
	int type12;
	String message12;
	
	int type13;
	String message13;
	
	int type14;
	String message14;
	
	int type15;
	String message15;
	
	int type16;
	String message16;
	
	int type17;
	String message17;
	
	int type18;
	String message18;
	
	int type19;
	String message19;
	
	public JMessage(String message, int type) {
		this.type = type;
		this.message = message;
		this.type1 = type;
		this.message1 = message;
		this.type2 = type;
		this.message2 = message;
		this.type3 = type;
		this.message3 = message;
		this.type4 = type;
		this.message4 = message;
		this.type5 = type;
		this.message5 = message;
		this.type6 = type;
		this.message6 = message;
		this.type7 = type;
		this.message7 = message;
		this.type8 = type;
		this.message8 = message;
		this.type9 = type;
		this.message9 = message;
		this.type10 = type;
		this.message10 = message;
		this.type11 = type;
		this.message11 = message;
		this.type12 = type;
		this.message12 = message;
		this.type13 = type;
		this.message13 = message;
		this.type14 = type;
		this.message14 = message;
		this.type15 = type;
		this.message15 = message;
		this.type16 = type;
		this.message16 = message;
		this.type17 = type;
		this.message17 = message;
		this.type18 = type;
		this.message18 = message;
		this.type19 = type;
		this.message19 = message;
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