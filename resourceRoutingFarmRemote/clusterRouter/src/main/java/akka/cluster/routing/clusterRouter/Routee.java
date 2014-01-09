package akka.cluster.routing.clusterRouter;

import java.io.Serializable;

import akka.actor.ActorRef;

public class Routee  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String role;
	String name;
	String version;
	ActorRef routee;
	
	public Routee(String role, String name, String version, ActorRef routee) {
		this.role = role;
		this.name = name;
		this.version = version;
		this.routee = routee;
	}
	
	public String getRole() {
		return this.role;
	}
}
