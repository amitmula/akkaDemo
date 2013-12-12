package akka.remote.client;


import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import scala.concurrent.Await;
import scala.concurrent.Future;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.pattern.Patterns;
import akka.remote.message.JMessage;
import akka.remote.message.KMessage;
import akka.util.Timeout;

import com.esotericsoftware.kryo.io.Output;
import com.typesafe.config.ConfigFactory;

import objectexplorer.MemoryMeasurer;

public class AkkaRemoteClient {
	
	static final long loopCount = 5000;
	static HashMap<Integer, Integer> javaTimes;
	static HashMap<Integer, Integer> kryoTimes;
	
	static class LocalActor extends UntypedActor {		
		static ActorRef remoteActor;
		
		@SuppressWarnings("deprecation")
		@Override
		public void preStart() {
			remoteActor = getContext().actorFor("akka.tcp://RemoteSys@127.0.0.1:2662/user/remoteActor");
			System.out.println("remoteActor initialized");
		}
				
		@Override
		public void onReceive(Object message) throws Exception {			
						
			
		}
	}
	
    public static void main(String[] args) throws Exception {
    	
    	ActorSystem _system = ActorSystem.create("LocalNodeApp",ConfigFactory.load().getConfig("LocalSys"));
		@SuppressWarnings("deprecation")
		ActorRef localActor = _system.actorOf(new Props(LocalActor.class));
		
		ActorRef remoteActor = LocalActor.remoteActor;
		while(remoteActor == null) {
			Thread.sleep(1000);
			remoteActor = LocalActor.remoteActor;
		}
		System.out.println("got remoteActor");
		
		Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
		kryoTimes = new HashMap<Integer, Integer>();
		javaTimes = new HashMap<Integer, Integer>();
		Future<Object> future;
		
		long jSum = 0,kSum = 0;
		int interval = 0;
		System.out.println("Benchmarking results<kryo ms,java ms>\nElements of the object are of type java.lang.String");
		
		
		
		for(int i=0; i<loopCount; i++) {
        	
			future = Patterns.ask(remoteActor, new KMessage(i,0, System.currentTimeMillis()), timeout);
        	KMessage kmsg = (KMessage) Await.result(future, timeout.duration());
        	kmsg.setEnd(System.currentTimeMillis());
        	kryoTimes.put(kmsg.getCount(), (int) (kmsg.getEnd() - kmsg.getStart()));
        	
        	future = Patterns.ask(remoteActor, new JMessage(i,0, System.currentTimeMillis()), timeout);
        	JMessage jmsg = (JMessage) Await.result(future, timeout.duration());
        	jmsg.setEnd(System.currentTimeMillis());
        	javaTimes.put(jmsg.getCount(), (int) (jmsg.getEnd() - jmsg.getStart()));
        	        	
        	jSum += javaTimes.get(i);
        	kSum += kryoTimes.get(i);
        	
        	if(i%100 == 0 && i!=0) {
        		System.out.println("Avg. serialization time for " + (interval+1) + " to " + i + " elements : <" + (float)kSum/(float)100 + "," + (float)jSum/(float)100 + ">" + " payload size in Bytes - " + MemoryMeasurer.measureBytes(kmsg));
        		jSum=0;kSum=0;interval = i;
        	}
        }
		
		System.out.println("Done.");
		_system.shutdown();
    	
    }
}
