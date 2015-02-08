import java.util.Scanner;

public class CLI implements UI{
	static private CLI instance;
	private Launcher launcher;
	Scanner stdin; 
	private CLI(Launcher launcher_) {
		this.launcher = launcher_;
		stdin = new Scanner(System.in);
	}
	
	public static CLI getInstance(Launcher launcher_) {
	      if(instance == null) {
	         instance = new CLI(launcher_);
	      }
	      return instance;
	}	
	
	public void display(String msg) {
		System.out.println(msg);
	}

	public void start(String msg) {
		System.out.println(msg);
		String serviceRate = null;
		String arrivalRate = null;
		String counterNum = null;
		do {
			display("Please input arrival rate:");
			arrivalRate = stdin.nextLine();
			display("Please input service rate:");
			serviceRate = stdin.nextLine();
			display("Please input num of counter:");
			counterNum = stdin.nextLine();
		} while(this.launcher.validateData(arrivalRate, 
				serviceRate, counterNum) == false);
	}
}
