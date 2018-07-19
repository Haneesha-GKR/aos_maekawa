package Maekawa;
import java.io.Serializable;

public class ScalarClock implements Serializable, Comparable<ScalarClock>, Cloneable{
	private static final long serialVersionUID = 1L;
	
	private long clock = 0L;

	public long getClock() {
		return clock;
	}

	public void setClock(long clock) {
		this.clock = clock;
	}
	
	public void tickClock() {
		this.clock++;
	}
	
	public void mergeClock(ScalarClock another_clock) {
		if(this.clock < another_clock.getClock()) {
			this.setClock(another_clock.getClock());
		}
		
		this.tickClock();
	}

	@Override
	public int compareTo(ScalarClock that) {
		// TODO Auto-generated method stub
		if(this.clock > that.clock) return 1;
		else if(this.clock < that.clock) return -1;
		else return 0;
	}
	
	public ScalarClock clone() {
		ScalarClock clk = new ScalarClock();
		clk.setClock(this.clock);
		return clk;
	}
}
