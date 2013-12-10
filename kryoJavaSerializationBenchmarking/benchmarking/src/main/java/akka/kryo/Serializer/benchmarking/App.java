package akka.kryo.Serializer.benchmarking;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import akka.remote.message.JMessage;
import akka.remote.message.KMessage;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

/**
 * Hello world!
 *
 */
public class App {
	
	static Kryo kryo;
	
	public App() {
		kryo = new Kryo();
		//kryo.register(KMessage.class);
	}
	
	static final int loopCount = 100000;

	long measureTimeKryo(Kryo kryo, Object obj) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		Output output = new Output(stream);
		long start = System.currentTimeMillis();
        kryo.writeObject(output, obj);
        output.close(); // Also calls output.flush()
        byte[] buffer = stream.toByteArray(); // Serialization done, get bytes
        KMessage deSerlialisedMessage = kryo.readObject(new Input(new ByteArrayInputStream(buffer)), KMessage.class);
        long stop = System.currentTimeMillis();
        long elapsed = stop -start;
        return elapsed;
	}
	
	long measureTimeJava(Object obj) throws ClassNotFoundException, IOException {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		ObjectOutputStream output = new ObjectOutputStream(stream);
		ObjectInputStream input;
		long start = System.currentTimeMillis();
        try{
        	output.writeObject(obj);
        	output.close();
        	byte[] buffer = stream.toByteArray();
        	input = new ObjectInputStream(new ByteArrayInputStream(buffer));
        	JMessage deSerlialisedMessage = (JMessage) input.readObject();
        	input.close();
        } catch (IOException e) {
        		
    	}
        long stop = System.currentTimeMillis();
        long elapsed = stop -start;
        return elapsed;
	}
	
    public static void main( String[] args ) throws ClassNotFoundException, IOException {
    	
    	App benchMarkingApp = new App();
    	
        List<Integer> kryoTimes = new ArrayList<Integer>();
        List<Integer> javaTimes = new ArrayList<Integer>();
        
        for(int i =0; i<loopCount; i++) {
        	kryoTimes.add((int) benchMarkingApp.measureTimeKryo(kryo, new KMessage("new message for kryo", 0)));
        }
        
        for(int i =0; i<loopCount; i++) {
        	javaTimes.add((int) benchMarkingApp.measureTimeJava(new JMessage("new message for java", 0)));
        }
        
        long jSum = 0,kSum = 0;
        Iterator<Integer> itr;
        
        itr = javaTimes.iterator();
        while(itr.hasNext())
        	jSum += (int)itr.next();
        System.out.println("Java serilizing average time : " + (float)jSum/(float)loopCount);
        
        itr = kryoTimes.iterator();
        while(itr.hasNext())
        	kSum += (int)itr.next();
        System.out.println("Kryo serilizing average time : " + (float)kSum/(float)loopCount);
    }
} 