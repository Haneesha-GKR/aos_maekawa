package Maekawa;
import java.io.Serializable;

enum MessageType{
	M_REQUEST,
	M_LOCKED,
	M_INQUIRE,
	M_FAILED,
	M_RELINQUISH,
	M_APPLICATION
}

public abstract class Message implements Serializable, Comparable<Message> {
	private static final long serialVersionUID = -1266868158640057265L;
	
	private ScalarClock clock;
	private MessageType type;
	
	public ScalarClock getClock() {
		return clock;
	}
	
	public void setClock(ScalarClock clock) {
		this.clock = clock;
	}
	
	public MessageType getType() {
		return type;
	}
	
	public void setType(MessageType type) {
		this.type = type;
	}
	
	@Override
	public int compareTo(Message that) {
		return this.getClock().compareTo(that.getClock());
	}
}

class RequestMessage extends Message{
	
}

class LockedMessage extends Message{
	
}

class InquireMessage extends Message{
	
}

class FailedMessage extends Message{
	
}

class RelinquishMessage extends Message{
	
}

class ApplicationMessage extends Message{
	
}