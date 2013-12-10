package akka.remote.client;

import java.util.ArrayList;
import java.util.Iterator;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.remote.message.Message;

import com.typesafe.config.ConfigFactory;

public class AkkaRemoteClient {
	
	static class LocalActor extends UntypedActor {		
		ActorRef remoteActor;
		
		@SuppressWarnings("deprecation")
		@Override
		public void preStart() {
			//Get a reference to the remote actor
			remoteActor = getContext().actorFor("akka.tcp://RemoteSys@127.0.0.1:2662/user/remoteActor");
		}
				
		@Override
		public void onReceive(Object message) throws Exception {			
			/*if(obj instanceof Message) {
				Message msg = (Message) obj;
				if(msg.getMessageSender() == null) {
					msg.setMessageSender(getSelf());
					remoteActor.tell(msg, getSelf());
					System.out.println("Me - " + msg.getMessageContents());
				}else {
					System.out.println(msg.getMessageSender() + " - " + msg.getMessageContents());
				}				
			}else
				System.out.println("Alien message");*/
			
//			Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
			
			if(message instanceof Message) {
				Message msg = (Message) message;
				if(msg.getType() == 0) {					
					remoteActor.tell(msg, getSelf());
				}else {
					System.out.println(msg.getMessageContents());
					System.out.println("ArrayList conntents : ");
					ArrayList<String> messages = msg.getMessages();
					Iterator<String> itr = messages.iterator();
					while(itr.hasNext()) {
						System.out.println(itr.next());
					}
				}
				/*Future<Object> future = Patterns.ask(remoteActor, message.toString(),timeout);
				String result = (String) Await.result(future, timeout.duration());
				System.out.println("Message received from Server ->" + result);*/
			}
		}
	}
	
    public static void main( String[] args ) throws InterruptedException {
    	
    	ActorSystem _system = ActorSystem.create("LocalNodeApp",ConfigFactory.load().getConfig("LocalSys"));
		@SuppressWarnings("deprecation")
		ActorRef LocalActor = _system.actorOf(new Props(LocalActor.class));
//		ArrayList<String> msgList = new ArrayList<String>();
		
		
		
		for(int i =0;i<1000;i++) {
//			msgList.add("this is message no " + i);
//			if(i%10!=0 || i==0)
//				continue;
			LocalActor.tell(new Message("this is message no. " + i,0),ActorRef.noSender());
			Thread.sleep(2000);
		}
		_system.shutdown();
    	/*
    	Scanner input = new Scanner(System.in);
    	ActorSystem _system = ActorSystem.create("LocalNodeApp", ConfigFactory.load().getConfig("LocalSys"));
		ActorRef LocalActor = _system.actorOf(new Props(LocalActor.class));
		boolean stop = false;
		String message = null;
		while(!stop) {
			//System.out.print("Me (send '_quit' to stop) - ");
			message = input.nextLine();
			if(message.equalsIgnoreCase("_quit"))
				stop = true;
			LocalActor.tell(new Message(message,ActorRef.noSender()), ActorRef.noSender());
		}
		*/
    }
}
