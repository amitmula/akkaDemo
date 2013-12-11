package akka.remote.server;


import java.util.ArrayList;
import java.util.Iterator;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.kernel.Bootable;
import akka.remote.message.Message;

import com.typesafe.config.ConfigFactory;

public class RemoteEchoServer implements Bootable {
	
	static class RemoteActor extends UntypedActor {
		
		ActorRef remoteActor;
		
		@Override
		public void onReceive(Object obj) throws Exception {
			if(obj instanceof Message) {
				Message msg = (Message) obj;
				ArrayList<String> messages = msg.getMessages();
				System.out.println("Messages received from Client : ");
				Iterator<String> itr = messages.iterator();
				while(itr.hasNext()) {
					System.out.println(itr.next());
				}
				messages.add(0, "Server echo");
				msg.setType(1);
				msg.setMessages(messages);
				getSender().tell(msg, getSelf());
			}
		}
	}
	
	// Create an Akka actor system
	final static ActorSystem _system = ActorSystem.create("RemoteSys", ConfigFactory.load().getConfig("RemoteSys"));
	
	@SuppressWarnings("deprecation")
	public void startup() {
		_system.actorOf(new Props(RemoteActor.class), "remoteActor");
	}

	public void shutdown() {
		_system.shutdown();
	}
}