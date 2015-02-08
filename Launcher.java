import java.util.Scanner;

/*
 * @author LI Chi 11808749d
 * This is Waiting Line Simulator
 */

public class Launcher {
	UI ui;
	private double arrivalRate;
	private double serviceRate;
	private int counterNum;
	private final String WELCOME_MSG = "This is a Wainting Line Simulator.\n"
			+ "Please input a float number in Arrival rate.\n"
			+ "Please input a float number in Service rate.\n"
			+ "Please input a integer in num of counter.\n";
	private final String NO_SERVICE_RATE_ERROR = "Service Rate can not be empty!";
	private final String NO_ARRIVAL_RATE_ERROR = "Arrival Rate can not be empty!";
	private final String NO_COUNTER_NUM_ERROR = "Number of Counter can not be empty!";
	private final String ARRIVAL_RATE_FORMAT_ERROR =
			"Please enter a float number in Arrival Rate!";
	private final String SERVICE_RATE_FORMAT_ERROR =
			"Please enter a float number in Service Rate!";
	private final String COUNTER_NUM_FORMAT_ERROR =
			"Please enter a float number in Number of Counter!";
	private static final String NO_UI_ERROR = 
			"Please input UI type in arguments:\n1.GUI\n2.CLI";
	private static final String WRONG_UI_ERROR = 
			"Please correct input UI type\n1.GUI\n2.CLI";
	
	// 1:GUI, 2:CLI
	public static void main(String [] args) {
		if(args.length == 0) {
			System.out.println(NO_UI_ERROR);
		} else {
			if(args[0].equals("1")) {
				Launcher launcher = new Launcher(1);
				launcher.run();
			} else if(args[0].equals("2")){
				Launcher launcher = new Launcher(2);
				launcher.run();
			} else {
				System.out.println(WRONG_UI_ERROR);
			}
		}
	}
	
	private Launcher(int choice) {
		if(choice == 1) {
			this.ui = GUI.getInstance(this);
		} else if (choice == 2) {
			this.ui = CLI.getInstance(this);
		}
	}
	
	void run() {	
		ui.start(this.WELCOME_MSG);
	}
	
	public boolean validateData(String arrivalRate_, 
			String serviceRate_, String counterNum_) {
		boolean readyToStart = true;
		if(arrivalRate_.equals("")) {
			ui.display(this.NO_ARRIVAL_RATE_ERROR);
			readyToStart = false;
		} else {
			try {
				this.arrivalRate = Double.parseDouble(arrivalRate_);
			} catch (NumberFormatException e) {
				ui.display(this.ARRIVAL_RATE_FORMAT_ERROR);
				readyToStart = false;
			}
		}
		
		if(serviceRate_.equals("")) {
			ui.display(this.NO_SERVICE_RATE_ERROR);
			readyToStart = false;
		} else {
			try {
				this.serviceRate = Double.parseDouble(serviceRate_);
			} catch (NumberFormatException e) {
				ui.display(this.SERVICE_RATE_FORMAT_ERROR);
				readyToStart = false;
			}
		}
		
		if(counterNum_.equals("")) {
			ui.display(this.NO_COUNTER_NUM_ERROR);
			readyToStart = false;
		} else {
			try {
				this.counterNum = Integer.parseInt(counterNum_);
			} catch (NumberFormatException e) {
				ui.display(this.COUNTER_NUM_FORMAT_ERROR);
				readyToStart = false;
			}
		}
		
		if(readyToStart) {
			start();
			return true;
		} else {
			return false;
		}
	}
	
	void start() {
		Simulator simulator = Simulator.getInstance(ui);
		Source source = Source.getInstance(simulator, this.arrivalRate);
		for (int i=0;i<this.counterNum;i++) {
			Counter counter= new Counter(ui ,simulator, i+1, this.serviceRate);
			simulator.registerCounter(counter);
			counter.start();
		}
		simulator.registerSource(source);
		source.start();
		simulator.start();
	}
}
