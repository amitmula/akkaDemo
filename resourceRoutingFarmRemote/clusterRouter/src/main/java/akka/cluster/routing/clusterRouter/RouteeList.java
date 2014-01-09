package akka.cluster.routing.clusterRouter;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import akka.actor.ActorRef;

public class RouteeList extends AbstractList<Routee>{
	
	List<Routee> routeeList;
	
	public RouteeList() {
		routeeList = new ArrayList<Routee>();
	}
	
	public boolean contains(ActorRef actorRef) {
		Iterator<Routee> itr = routeeList.iterator();
		while(itr.hasNext()) {
			if(itr.next().routee.equals(actorRef)){
				return true;
			}
		}
		return false;
	}
	
	public Routee getRandomByRole(List<Routee> routeeList, String role) {
		Routee randomRoutee, current;
		List<Routee> roleList = new ArrayList<Routee>();
		Iterator<Routee> itr = routeeList.iterator();
		while(itr.hasNext()) {
			current = itr.next();
			if(current.role.equals(role))
				roleList.add(current);
		}
		randomRoutee = getRandom(roleList);
		return randomRoutee;
	} 
	
	public Routee getRandom(List<Routee> routeeList) {
		if(routeeList.isEmpty())
			return null;
		int randomInt = (int)(routeeList.size() * Math.random());
		return routeeList.get(randomInt);
	}
	
	public Routee getByActorRef(ActorRef actorRef) {
		Iterator<Routee> itr = routeeList.iterator();
		Routee current = null;
		while(itr.hasNext()) {
			current = itr.next();
			if(current.routee.equals(actorRef))
				break;
		}
		return current;
	}
	
	@Override
	public Routee get(int index) {
		return routeeList.get(index);
	}

	@Override
	public int size() {
		return routeeList.size();
	}

	public boolean add(Routee routee) {
		return routeeList.add(routee);
	}
	
	public boolean remove(ActorRef actorRef) {
		Routee routee = getByActorRef(actorRef);
		return routeeList.remove(routee);
	}
}