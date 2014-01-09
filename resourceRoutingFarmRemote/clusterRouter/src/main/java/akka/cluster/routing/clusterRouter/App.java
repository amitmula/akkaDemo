package akka.cluster.routing.clusterRouter;

import static akka.pattern.Patterns.ask;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.cluster.routing.clusterRouter.EndPointActors;
import akka.cluster.routing.clusterRouter.ServiceRouter;
import akka.dispatch.OnSuccess;
import akka.util.Timeout;

public class App {
	
	final static String systemName = "serviceCluster";
	static ActorRef endPointactor;
	
	public static void startCluster(String role) throws InterruptedException {
		
		Config conf = ConfigFactory.parseString("akka.cluster.roles=[" + role + "]").withFallback(ConfigFactory.load());	//load it from application.conf
		ActorSystem clusterSystem = ActorSystem.create(systemName, conf);
		if(role.equalsIgnoreCase("endPoint"))
			endPointactor = clusterSystem.actorOf(EndPointActors.mkProps(role), role);
		else
			clusterSystem.actorOf(ServiceRouter.mkProps(role), role);
		
		if(role.equalsIgnoreCase("endPoint")){
			final ExecutionContext ec = clusterSystem.dispatcher();
			Timeout timeout = new Timeout(Duration.create(5, TimeUnit.SECONDS));
			SecureRandom random = new SecureRandom();
	        String type = "";
	    	for(int i=0; i<10000; i++) {
	    		type = type.startsWith("balance")?"account " + i:"balance " + i;
	    		ask(endPointactor, type + "  " + new BigInteger(130, random).toString(32), timeout)
		    		.onSuccess(new OnSuccess<Object>() {
		    			public void onSuccess(Object result) {
		    				System.out.println(result);
		    			}
		    		}, ec);
	    		// wait a while until next request,
	    		// to avoid flooding the console with output
	    		Thread.sleep(1000);
	    	}
			
			/*SecureRandom random = new SecureRandom();
			String type = null;
			for(int i=0; i<10000; i++) {
	    		type = type.startsWith("balance")?"account " + i:"balance " + i;
	    		endPointactor.tell(type + "  " + new BigInteger(130, random).toString(32), endPointactor);		    		
	    		Thread.sleep(1000);
	    	}*/
			
		}
	 	
		/*int totalInstances = 100;
		int maxInstancesPerNode = 3;
		boolean allowLocalRoutees = false;
		String useRole = "compute";
		
		ActorRef workerRouter = getContext().actorOf(
		    Props.create(StatsWorker.class).withRouter(new ClusterRouterConfig(
		        new ConsistentHashingRouter(0), new ClusterRouterSettings(
		            totalInstances, maxInstancesPerNode, allowLocalRoutees, useRole))),
		    "workerRouter3");*/
		
		/*system.actorOf(ClusterSingletonManager.defaultProps("active",
			PoisonPill.getInstance(), role,
			new ClusterSingletonPropsFactory() {
				public Props create(Object handOverData) {
					return Master.props(workTimeout);
				}
			}), "master");*/
	}
	
	public static void main( String[] args ) throws InterruptedException {

		if (args.length < 2)
		    System.out.println("insufficient command line arguments.");  
		else {
			System.setProperty("akka.remote.netty.tcp.port", args[0]);
			startCluster(args[1]); 
		}
		
//		System.out.println("Cluster is up!!!");
//    	System.out.println("Cluster Address :" + serviceClusterAddress.toString());
//    	joinCluster(serviceClusterAddress, "account");
//    	joinCluster(serviceClusterAddress, "balance");
//    	
    	
/*    	ActorSystem farmSystem = farm.getActorSystem();
    	accountsRouter = farmSystem.actorSelection("akka://resourceFarmSystem/user/accountRouter");
    	balanceRouter = farmSystem.actorSelection("akka://resourceFarmSystem/user/balanceRouter");

    	ActorSystem endPointActorSystem = ActorSystem.create("endPointActorSystem");
        ActorRef endPointActor = endPointActorSystem.actorOf(Props.create(EndPointRouter.class));
        
        SecureRandom random = new SecureRandom();
        String type = "";
    	for(int i=0; i<10000; i++) {
    		type = type.startsWith("balance")?"account " + i:"balance " + i;
    		endPointActor.tell(type + "  " + new BigInteger(130, random).toString(32), ActorRef.noSender());
    		Thread.sleep(100);
    	}
    	farmSystem.shutdown();
    	endPointActorSystem.shutdown();*/
    }
}