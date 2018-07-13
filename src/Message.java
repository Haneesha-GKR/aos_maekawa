import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

enum MaekawaMessageType{
	M_REQUEST,
	M_LOCKED,
	M_INQUIRE,
	M_FAILED,
	M_RELINQUISH
}

@SuppressWarnings("serial")
public class Message implements Serializable {
	private ScalarClock Clock;
	private MaekawaMessageType type;
	
	public ScalarClock getClock() {
		return Clock;
	}
	public void setClock(ScalarClock clock) {
		Clock = clock;
	}
	public MaekawaMessageType getType() {
		return type;
	}
	public void setType(MaekawaMessageType type) {
		this.type = type;
	}
}