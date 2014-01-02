package spasta.resource.routingFarm;

import com.typesafe.config.ConfigFactory;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.FromConfig;

public class RouterFarm {
	
	ActorSystem _system;
	
	public ActorSystem getActorSystem() {
		return _system;
	}
	
	RouterFarm() {
		_system = ActorSystem.create("resourceFarmSystem", ConfigFactory.load().getConfig("resourceFarmSystem"));
		ActorRef accRouter = _system.actorOf(Props.create(ResourceActor.class).withRouter(new FromConfig()), "accountRouter");
    	ActorRef balRouter = _system.actorOf(Props.create(ResourceActor.class).withRouter(new FromConfig()), "balanceRouter");
    	System.out.println("Routers created-\n" + accRouter.path().toString() + "\n" + balRouter.path().toString());
	}
	
	public static class ResourceActor extends UntypedActor{
		@Override
		public void onReceive(Object arg0) throws Exception {
			//get the request processed from the resource proxy actors.
			//eg.
			if(arg0 instanceof String) {
				getSender().tell("response_Resource Request : " + arg0  + ". Proxy Response : " + arg0.toString().toUpperCase(), getSelf());
			}
		}
	}
}
