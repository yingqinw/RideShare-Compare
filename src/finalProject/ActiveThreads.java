package finalProject;

import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

public class ActiveThreads extends Thread{

	private Vector<CurrentLocationThread> serverThreads;
	ServerSocket ss;
	public ActiveThreads(int port) {
		ss = null;
		try {
			System.out.println("Binding to port " + port);
			ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			serverThreads = new Vector<CurrentLocationThread>();

		} catch (IOException ioe) {
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		} finally {
//			try {
//				if (ss != null)
//					ss.close();
//
//			} catch (IOException ioe) {
//
//				System.out.println("Closing ioe: " + ioe.getMessage());
//			}
		}
	}
	
	public void run() {
		while(true) {
			Socket s = null;
			try {
				s = ss.accept();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // blocking
			if (s != null)
				System.out.println("Connection from: " + s.getInetAddress());
		}
	}
	
	public void addThread(CurrentLocationThread c) {
		serverThreads.add(c);
	}
//	
	public void removeThread(CurrentLocationThread c) {
		for (int i = 0; i < serverThreads.size(); i++) {
			if (serverThreads.get(i).latitude == c.latitude && serverThreads.get(i).longitude == c.longitude) {
				serverThreads.remove(i);
				break;
			}
		}
	}
}