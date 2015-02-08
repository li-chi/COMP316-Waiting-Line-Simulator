
/*
 * @author LI Chi 11808749d
 * This is Waiting Line Simulator
 */

public class Counter extends Thread{
	private Simulator simulator;
	private int num;
	private boolean isEmpty;
	private double serviceRate;
	private int serviceInterval;
	private UI ui;
	private final String SERVE_MSG = " arrived at ";
	private final String INTERRUPT_MSG = "Interrupt!";
	
	public Counter(UI ui_, Simulator simulator_, int num_, double serviceRate_) {
		this.ui = ui_;
		this.isEmpty = true;
		this.simulator = simulator_;
		this.num = num_;
		this.serviceRate = serviceRate_;
		this.serviceInterval = (int) (60000.0 / serviceRate_);
	}
	
	public void serveCustomer(Customer cus) {
		this.isEmpty = false;
		ui.display(cus + this.SERVE_MSG + this);
		int interval = (int) (Math.random() * 2 * serviceInterval);
		try {
			Thread.sleep(interval);
		} catch (InterruptedException e) {
			ui.display(this.INTERRUPT_MSG);
		}
		this.isEmpty = true;
		notifySimulator();
	}
	
	public void run() {
		notifySimulator();
	}
	
	void notifySimulator() {
		while(isEmpty) {
			this.simulator.serveCustomer();
			try {
				sleep(100); 
			} catch (InterruptedException e) {
				System.out.println(this.INTERRUPT_MSG);
			}
		}	
	}
	
	public boolean isEmpty() {
		return this.isEmpty;	
	}
	
	public String toString() {
		return "Counter "+num;
	}
}
