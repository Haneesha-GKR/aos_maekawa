package Maekawa;

import java.util.ArrayList;

public class RequestQueue {
	private int count;
	private ArrayList<Message> queue;
	
	public RequestQueue() {
		super();
		this.queue = new ArrayList<Message>();
		this.setCount(0);
	}

	public ArrayList<Message> getQueue() {
		return queue;
	}

	public void setQueue(ArrayList<Message> queue) {
		this.queue = queue;
	}
	
	public void insert(Message message) {
		queue.add(message);
		queue.sort(null);
		this.setCount(this.getCount() + 1);
	}
	
	public Message next() throws ArrayIndexOutOfBoundsException{
		return queue.get(0);
	}
	
	public Message popNext() throws ArrayIndexOutOfBoundsException{
		this.setCount(this.getCount() - 1);
		return queue.remove(0);
	}

	public int getCount() {
		return count;
	}

	private void setCount(int count) {
		
	}
}
