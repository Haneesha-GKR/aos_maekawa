package Maekawa;
import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class Message implements Serializable, Comparable<Message> {	
	private ScalarClock clock;
	
	public Message(ScalarClock clock) {
		 this.clock = clock;
	}

	public ScalarClock getClock() {
		return clock;
	}
	
	public void setClock(ScalarClock clock) {
		this.clock = clock;
	}
	
	@Override
	public int compareTo(Message that) {
		return this.getClock().compareTo(that.getClock());
	}
}