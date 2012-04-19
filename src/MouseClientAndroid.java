import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.PointerInfo;

public class MouseClientAndroid {
	public static String input;
	public static int oldx = 0;
	public static int oldy = 0;
	public static int x = 0;
	public static int y = 0;
	public static int currposx = 0;
	public static int currposy = 0;
	public static Point p;
	public static void main(String[] args) throws IOException, AWTException {

		Robot robot = new Robot();
		System.out.println("EchoClient.main()");

		Socket echoSocket = null;
		PrintStream out = null;
		BufferedReader in = null;

		try {
			echoSocket = new Socket("localhost", 10911);
			out = new PrintStream(echoSocket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for "
					+ "the connection to: localhost.");
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		// String userInput;
		System.out.println("connected to phone !!");
		int counter = 0;

		// TODO monitor
		while (true) {
				try{
				input = in.readLine();
				//System.out.println(input);
				String inArr[] = input.split(",");
				Float pressure = new Float(inArr[2]);
				int type = Integer.parseInt(inArr[3]);
				x = Integer.parseInt(inArr[0]);
				y = Integer.parseInt(inArr[1]);
				
				if(type == 0)
				{
					p = new Point(MouseInfo.getPointerInfo().getLocation());
					currposx = p.x;
					currposy = p.y;
					oldx = x;
					oldy = y;
					//System.out.println(currposx+","+currposy);
				}
				if(type == 1)
				{
					p = MouseInfo.getPointerInfo().getLocation();
					//System.out.println(p.x+","+p.y);
				}
				if(type == 2)
				{	
					robot.mouseMove(currposx, currposy);
					currposx += x-oldx;
					currposy += y-oldy;
					oldx = x;
					oldy = y;
				}
				if(pressure >= 0.30)
				{
					robot.mousePress(InputEvent.BUTTON1_MASK);
		            robot.mouseRelease(InputEvent.BUTTON1_MASK);
				}
				if(type == 0 && pressure >= 0.17)
				{
					robot.mousePress(InputEvent.BUTTON1_MASK);
		            robot.mouseRelease(InputEvent.BUTTON1_MASK);
				}
				}
				catch(Exception e)
				{
					System.out.println("Something wicked has happened and the program has to quit !!! (For exact problem Look Below)");
					System.out.println(e.toString());
					System.exit(1);
				}
		}
	}
}
