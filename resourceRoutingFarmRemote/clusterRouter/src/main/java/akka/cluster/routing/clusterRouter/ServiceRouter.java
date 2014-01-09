package akka.cluster.routing.clusterRouter;

import akka.actor.Address;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.Member;
import akka.cluster.MemberStatus;

public class ServiceRouter extends UntypedActor{
	String role;
	Address endPointAddress;
	
	public static Props mkProps(String role) {
	    return Props.create(ServiceRouter.class, role);
	}
	
	public ServiceRouter(String role) {
		this.role = role;
	}
	
	Cluster cluster = Cluster.get(getContext().system());
	//subscribe to cluster changes, MemberUp
	@Override
	public void preStart() {
		cluster.subscribe(getSelf(), MemberUp.class);
	}

	//re-subscribe when restart
	@Override
	public void postStop() {
		cluster.unsubscribe(getSelf());
	}
	
	@Override
	public void onReceive(Object message) throws Exception {
		//get the request processed from the resource proxy actors.
		//eg.
		if(message instanceof String) {
			getSender().tell("Resource Request : " + message  + ". Proxy Response : " + message.toString().toUpperCase(), getSelf());
		} else if (message instanceof CurrentClusterState){
			CurrentClusterState state = (CurrentClusterState) message;
		    for (Member member : state.getMembers()) {
		        if (member.status().equals(MemberStatus.up()))
		        	register(member);
		    }
	    } else if (message instanceof MemberUp) {
		      MemberUp mUp = (MemberUp) message;
		      register(mUp.member());
	    } else {
		      unhandled(message);
	    }
	}
	
	void register(Member member) {
		if(member.hasRole("endPoint")){
			System.out.println("end point address is " + member.address());
			endPointAddress = member.address(); 
			getContext().actorSelection(member.address() + "/user/endPoint").tell(new Routee(this.role, "new routee name", "version", getSelf()), getSelf());
		}else if(!member.hasRole("endPoint") && endPointAddress != null) {
			getContext().actorSelection(endPointAddress + "/user/endPoint").tell(new Routee(this.role, "new routee name", "version", getSelf()), getSelf());
		}
	}
}