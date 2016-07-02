package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.AmobaPanel;

public class Server{
	public static ServerSocket ss;
	private static BufferedReader reader;
	private static PrintWriter writer;
	public static int type;
	public static boolean canPut = false;
	
	public Server(String ip, int port){
		try {
			ss = new ServerSocket(port);
			System.out.println("Server started! --- Date:"+  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			Socket client = ss.accept();
			InputStream in = null;
			OutputStream out = null;
			try {
				out = client.getOutputStream();
				in = client.getInputStream();
			} catch (IOException e) {
				System.out.println("IO Error!!! - ID:"+client.getInetAddress().toString() + 
						" --- Date:"+  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
			}
			reader = new BufferedReader(new InputStreamReader(in));
			writer = new PrintWriter(new OutputStreamWriter(out));
			Random r = new Random();
			double start = r.nextDouble();
			JSONObject obj = new JSONObject();
			obj.put("cmd", "start");
			obj.put("p1", start);
			obj.put("p2", 0);
			writer.println(obj.toJSONString());
			writer.flush();
			obj.put("cmd", "size");
			obj.put("p1", AmobaPanel.K);
			obj.put("p2", AmobaPanel.K);
			writer.println(obj.toJSONString());
			writer.flush();
			obj.put("cmd", "length");
			obj.put("p1", AmobaPanel.J);
			obj.put("p2", 0);
			writer.println(obj.toJSONString());
			writer.flush();
			if(start > 0.5){
				type = 0;
				canPut = true;
				JOptionPane.showMessageDialog(null, "Server:You start!");
			}else{
				type = 1;
				JOptionPane.showMessageDialog(null, "Server:Wait for oponent move!");
			}
			new ReaderThread(reader).start();
		} catch (IOException e) {
			System.out.println("Server error! --- Date:"+  new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		}
	}

	public static void notifyOponent(int x, int y){
		JSONObject obj = new JSONObject();
		obj.put("cmd", "move");
		obj.put("p1", x);
		obj.put("p2", y);
		writer.println(obj.toJSONString());
		writer.flush();		
	}
	
	public static void disconnect(){
		writer.println("END");
		writer.flush();
	}
}
