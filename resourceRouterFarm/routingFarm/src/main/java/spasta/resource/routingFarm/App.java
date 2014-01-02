package spasta.resource.routingFarm;

import java.math.BigInteger;
import java.security.SecureRandom;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;

public class App 
{
	public static ActorSelection accountsRouter;
	public static ActorSelection balanceRouter;
	
	public static class EndPointRouter extends UntypedActor{

		@Override
		public void onReceive(Object arg0) throws Exception {
			// TODO Auto-generated method stub
			if(arg0 instanceof String) {
				if(((String) arg0).startsWith("account")) {
					accountsRouter.tell(arg0,getSelf());
				} else if(((String) arg0).startsWith("balance")) {
					balanceRouter.tell(arg0,getSelf());
				} else if(((String) arg0).startsWith("response_")) {
					System.out.println(((String) arg0).substring(((String) arg0).indexOf("response_") + 9));
				}
			}
		}
	}
	
	public static void main( String[] args ) throws InterruptedException {

    	RouterFarm farm = new RouterFarm();
    	
    	ActorSystem farmSystem = farm.getActorSystem();
    	accountsRouter = farmSystem.actorSelection("akka://resourceFarmSystem/user/accountRouter");
    	balanceRouter = farmSystem.actorSelection("akka://resourceFarmSystem/user/balanceRouter");

    	ActorSystem endPointActorSystem = ActorSystem.create("endPointActorSystem");
        ActorRef endPointActor = endPointActorSystem.actorOf(Props.create(EndPointRouter.class));
        
        SecureRandom random = new SecureRandom();
        String type = "";
    	for(int i=0; i<10; i++) {
    		type = type.startsWith("balance")?"account " + i:"balance " + i;
    		endPointActor.tell(type + "  " + new BigInteger(130, random).toString(32), ActorRef.noSender());
    		Thread.sleep(1000);
    	}
    	farmSystem.shutdown();
    	endPointActorSystem.shutdown();
    }
}