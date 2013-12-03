package akka.remote.message;
import java.io.Serializable;
import akka.actor.ActorRef;

public class Message implements Serializable {
		
	ActorRef messageSender;
	String messageContents;
	public Message(String messageContents, ActorRef messageSender) {
		this.messageSender = messageSender;
		this.messageContents = messageContents;
	}
	public ActorRef getMessageSender() {
		return messageSender;
	}
	public void setMessageSender(ActorRef messageSender) {
		this.messageSender = messageSender;
	}
	public String getMessageContents() {
		return messageContents;
	}
	public void setMessageContents(String messageContents) {
		this.messageContents = messageContents;
	}
}