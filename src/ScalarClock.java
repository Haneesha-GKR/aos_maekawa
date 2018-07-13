public class ScalarClock {
	private long clock;

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
}
