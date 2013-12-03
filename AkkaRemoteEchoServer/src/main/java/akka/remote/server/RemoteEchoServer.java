package akka.remote.server;


import akka.remote.message.Message;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.kernel.Bootable;

import com.typesafe.config.ConfigFactory;

public class RemoteEchoServer implements Bootable {
	
	static class RemoteActor extends UntypedActor {
		
		ActorRef remoteActor;
		
		@Override
		public void onReceive(Object obj) throws Exception {
			/*if(obj instanceof Message) {
				Message msg = (Message) obj;
				getSender().tell(new Message("Server echo - " + msg.getMessageContents(), getSelf()), getSelf());							
			}else
				System.out.println("Alien message");*/
			if(obj instanceof Message) {
				Message msg = (Message) obj;
				System.out.println("Message received from Client : " + msg.getMessageContents());
				msg.setMessageContents("Server echo - " + msg.getMessageContents());
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
