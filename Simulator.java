import java.util.LinkedList;
import java.util.Queue;

/*
 * @author LI Chi 11808749d
 * This is Waiting Line Simulator
 */

public class Simulator extends Thread {
	private Queue <Customer> waitingLine;
	private LinkedList<Counter> counterList;
	private Source source;
	private UI ui;
	private final String CUS_ARR_MSG = " added to waiting line.";
	private final String SHOW_LENGTH_MSG = "Waiting line length is ";
	private final String INTERRUPT_MSG = "Interrupt!";
	
	static private Simulator instance = null;
	
	private Simulator(UI ui_) {
		this.ui = ui_;
		waitingLine = new LinkedList <Customer>();
		counterList = new LinkedList<Counter>();
	}
	
	public static Simulator getInstance(UI ui_) {
	      if(instance == null) {
	         instance = new Simulator(ui_);
	      }
	      return instance;
	}
	
	public void run() {
		this.serveCustomer();
	}
	
	public void registerSource(Source source_) {
		this.source = source_;
	}
	
	public void registerCounter(Counter counter) {
		counterList.add(counter);
	}
	
	public void arriveCustomer(Customer cus) {
		this.waitingLine.offer(cus);
		ui.display(cus + this.CUS_ARR_MSG);
		ui.display(this.SHOW_LENGTH_MSG + waitingLine.size());
	}
	
	public void serveCustomer() {
		if (waitingLine.size() == 0) {
			try {
				Thread.sleep(100);
				this.serveCustomer();
			} catch (InterruptedException e) {
				ui.display(this.INTERRUPT_MSG);
			}
		} else {
			for(Counter counter : counterList) {
				if(counter.isEmpty()) {
					Customer temp = this.waitingLine.poll();
					counter.serveCustomer(temp);
					ui.display(this.SHOW_LENGTH_MSG + waitingLine.size());
					break;
				}
			}
		}
	}
}
