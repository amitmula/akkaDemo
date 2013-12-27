package akka.multiSystem;

import java.util.Random;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.multiSystem.WorkProtocol.*;

public class MultiSystem {

	/*public static class Producer extends UntypedActor {
		Random randomGenerator = new Random();
		
		ActorRef remoteActor;
		
		@SuppressWarnings("deprecation")
		@Override
		public void preStart() {
			//Get a reference to the remote actor
			remoteActor = getContext().actorFor("akka.tcp://consumer@127.0.0.1:2662/user/consumerActor");
		}
		
		
		@Override
		public void onReceive(Object msg) throws Exception {
			if (msg instanceof WorkRequest) {
				remoteActor.tell(new Work(randomGenerator.nextInt(100)), getSender());
			}
		}
	}
	
	
	public static class Consumer extends UntypedActor {
		
		public void preStart() {
			System.out.println("Consumer is up now.");
		}
		
		@Override
		public void onReceive(Object msg) {
			if(msg instanceof Work) {
				Work work = (Work)msg;
				System.out.println("work is " + work.getN() + "*" + work.getN());
				getSender().tell(new WorkResult(Math.pow(((Work) msg).getN(), 2)), getSelf());
			}else if (msg instanceof WorkResult) {
				WorkResult result = (WorkResult) msg;
				System.out.println("Work result is " + result.getResult());
			}
		}
	}*/
	
	public static class Worker extends UntypedActor {

		ActorRef remoteActor;
		Random randomGenerator = new Random();
		
		@SuppressWarnings("deprecation")
		@Override
		public void preStart() {
			//Get a reference to the remote actor
			remoteActor = getContext().actorFor("akka.tcp://consumer@127.0.0.1:2772/user/consumerActor");
		}
		
		@Override
		public void onReceive(Object msg) throws Exception {
			
			if (msg instanceof WorkRequest) {
				remoteActor.tell(new Work(randomGenerator.nextInt(100)), getSelf());
			}else if(msg instanceof Work) {
				Work work = (Work)msg;
				System.out.println("work is " + work.getN() + "*" + work.getN());
				getSender().tell(new WorkResult(Math.pow(((Work) msg).getN(), 2)), getSelf());
			}else if (msg instanceof WorkResult) {
				WorkResult result = (WorkResult) msg;
				System.out.println("Work result is " + result.getResult());
			}
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		ActorSystem producer_system = ActorSystem.create("producer", ConfigFactory.load().getConfig("producer"));
		ActorSystem consumer_system = ActorSystem.create("consumer", ConfigFactory.load().getConfig("consumer"));

		consumer_system.actorOf(Props.create(Worker.class), "consumerActor");
//		ActorRef consumerActorLocal = producer_system.actorOf(Props.create(Worker.class));
		Thread.sleep(3000);
		ActorRef producerActor = producer_system.actorOf(Props.create(Worker.class));
		
		for(int i=0; i<10; i++) {
			producerActor.tell(new WorkRequest(), ActorRef.noSender());
//			producerActor.tell(new WorkRequest(), consumerActorLocal);
			Thread.sleep(1000);
		}
		
		producer_system.shutdown();
		consumer_system.shutdown();
		
	}
}
