package akka.remote.serializer;

import com.esotericsoftware.kryo.Kryo;

public class KryoCustomSerializer {
	public void customize(Kryo kryo) {
		
		
		
		
		/*By default, each appearance of an object in the graph after the first
		 * is stored as an integer ordinal. This allows multiple references to the 
		 * same object and cyclic graphs to be serialized. This has a small amount of
		 * overhead and can be disabled to save space if it is not needed: 
		 */
		kryo.setReferences(false);
	}
}
