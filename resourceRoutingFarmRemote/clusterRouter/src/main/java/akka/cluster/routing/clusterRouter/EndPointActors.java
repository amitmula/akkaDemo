package akka.cluster.routing.clusterRouter;

import akka.actor.Props;
import akka.actor.Terminated;
import akka.actor.UntypedActor;
import akka.cluster.routing.clusterRouter.Routee;

public class EndPointActors extends UntypedActor {

	RouteeList routees = new RouteeList();
	String role;
	
	public static Props mkProps(String role) {
	    return Props.create(EndPointActors.class, role);
	}
	
	public EndPointActors(String role) {
		this.role = role;
	}
	
	@Override
	public void onReceive(Object message) {
		if (message instanceof String) {
			if (routees.isEmpty()) {
				getSender().tell("No service routees are up.", getSender());
			}else {
				String msg = (String) message;
				if (msg.startsWith("account")) {
					Routee routee = routees.getRandomByRole(routees, "account");
					if(routee != null)
						routees.getRandomByRole(routees, "account").routee.forward(message, getContext());
					else
						System.out.println("No account routees found.");
				} else if (msg.startsWith("balance")) {
					Routee routee = routees.getRandomByRole(routees, "balance");
					if(routee != null)
						routees.getRandomByRole(routees, "balance").routee.forward(message, getContext());
					else
						System.out.println("No balance routees found.");
				} else if(msg.startsWith("response_"))
					System.out.println(msg.substring(msg.indexOf("response_") + "response_".length()));
			}
		} else if (message instanceof Routee) {
			
			if(!routees.contains(((Routee)message).routee)){
				routees.add((Routee)message);
				getContext().watch(getSender());

				////////////////////verbose code////////////////////
				
				Routee routee = (Routee)message;
				if(routee.getRole().contains("account"))
					System.out.println("account routee added");
				else if(routee.getRole().contains("balance"))
					System.out.println("balance routee added");
				else
					System.out.println("unknown routee added");
				
				////////////////////verbose code////////////////////
			}
			
		} else if (message instanceof Terminated) {
			Terminated terminated = (Terminated) message;
			routees.remove(terminated.getActor());
		} else {
			unhandled(message);
		}
	}
}