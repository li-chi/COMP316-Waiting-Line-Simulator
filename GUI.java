import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;

/*
 * @author LI Chi 11808749d
 * This is Waiting Line Simulator
 */

public class GUI implements UI{
	Launcher launcher;
	HashMap <String, TextField> textTable;
	TextArea output;
	Frame frame;
	Button start;
	final String [] labelNames = {"Arrival Rate","Service Rate","Num of Counter","output"};
	static private GUI instance;

	private GUI(Launcher launcher_) {
		this.launcher = launcher_;
		textTable = new HashMap <String, TextField>();
		frame = new Frame("Waiting Line Simulator");
		frame.addWindowListener(new WindowAdapter(){
		      public void windowClosed(WindowEvent e){ System.exit(0); }
		      public void windowClosing(WindowEvent e){ 
		    	  frame.setVisible(false);
		    	  frame.dispose();
		      }
		    });
		createElements();
		frame.setSize(300, 400);
		frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 10));
		frame.setVisible(true);
	}
	
	public static GUI getInstance(Launcher launcher_) {
	      if(instance == null) {
	         instance = new GUI(launcher_);
	      }
	      return instance;
	}
	
	void createElements() {
		Panel p = new Panel();
		for(int i=0;i<3;i++) {
			Label l = new Label(labelNames[i]);
			TextField t = new TextField(10);
			p.add(l);
			p.add(t);
			textTable.put(labelNames[i], t);
		}
		p.setLayout(new GridLayout(3,2));
		p.setSize(250, 100);
		frame.add(p);
		
		p = new Panel();
		start = new Button("Start");
		Button end = new Button("End");
		start.addActionListener(new StartListener());
		end.addActionListener(new EndListener());
		p.add(start);
		p.add(end);
		frame.add(p);
		
		output = new TextArea("",13,25,TextArea.SCROLLBARS_VERTICAL_ONLY);
		frame.add(output);
	}
	
	public void start(String msg) {
		this.display(msg);
	}
	
	public void display(String msg) {
		output.append(msg+'\n');
	}
	
	public String getArrivalRate() {
		return textTable.get(labelNames[0]).getText();
	}
	
	public String getServieRate() {
		return textTable.get(labelNames[1]).getText();
	}
	
	public String getNumCounter() {
		return textTable.get(labelNames[2]).getText();
	}
	
	class StartListener implements ActionListener{
		  public void actionPerformed(ActionEvent e){
			  if(launcher.validateData(getArrivalRate(),
					  getServieRate(),getNumCounter())) {
				  start.setEnabled(false);
			  }
		  }
	}
	
	class EndListener implements ActionListener{
		  public void actionPerformed(ActionEvent e){
			  System.exit(0);
		  }
	}
}


