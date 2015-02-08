
/*
 * @author LI Chi 11808749d
 * This is Waiting Line Simulator
 */

public class Source extends Thread {
	private double arrivalRate;
	private boolean opening;
	private double arrivalInterval;
	private Simulator simulator;
	private int customerNum;
	private  static Source instance = null;
	
	private Source(Simulator simulator_, double arrivalRate_) {
		this.simulator = simulator_;
		this.arrivalRate = arrivalRate_;
		this.opening = false;
		this.arrivalInterval = 60000.0 / arrivalRate;
		this.customerNum = 0;
	}
	
	public static Source getInstance(Simulator simulator, double arrivalRate) {
	      if(instance == null) {
	         instance = new Source(simulator, arrivalRate);
	      }
	      return instance;
	}
	
	void startArriving() {
		int interval;
		while(opening) {
			Customer cus = new Customer(++this.customerNum);
			this.simulator.arriveCustomer(cus);
			interval = (int) (Math.random() * 2.0 * arrivalInterval);
			try {
				sleep(interval); 
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
			}
		}
	}
	
	public void run() {
		this.opening = true;
		startArriving();
	}
	
	public void end() {
		this.opening = false;
	}
}
