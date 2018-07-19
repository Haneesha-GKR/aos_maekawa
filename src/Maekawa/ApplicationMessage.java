package Maekawa;

public class ApplicationMessage extends Message {
	private static final long serialVersionUID = 0xaaaa0007L;
	
	private String data;
	
	public ApplicationMessage(ScalarClock clock) {
		super(clock);
		// TODO Auto-generated constructor stub
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}

}
