package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.AmobaDrawer;
import gui.AmobaPanel;

public class Client {
	private int hostport;
	public static Socket s;
	private static PrintWriter out;
	private static BufferedReader in;
	public static String name;
	private String ip;
	public static int type;
	public static boolean canPut = false;
	
	public Client(String ip,int hostport){
		this.hostport = hostport;
		this.ip = ip;
		connect();
	}
	
	public void connect(){
		try {
			s = new Socket(InetAddress.getByName(ip),hostport);
			out = new PrintWriter(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			String tmp = in.readLine();
			System.out.println(tmp);
			JSONObject obj;
			try {
				obj = (JSONObject)new JSONParser().parse(tmp);
				Double rand = (Double) obj.get("p1");
				tmp = in.readLine();
				obj = (JSONObject)new JSONParser().parse(tmp);
				AmobaDrawer.kField.setText(obj.get("p1").toString());
				tmp = in.readLine();
				obj = (JSONObject)new JSONParser().parse(tmp);
				AmobaDrawer.jField.setText( obj.get("p1").toString());
				AmobaDrawer.btnGenerate.doClick();	
				if(rand <0.5){
					type = 0;
					canPut = true;
					JOptionPane.showMessageDialog(null, "Client:You start!");
				}else{
					type = 1;
					JOptionPane.showMessageDialog(null, "Client:Wait for oponent move!");
				}
				new ReaderThread(in).start();
			} catch (ParseException e) {
				System.out.println("ex");
			}
		} catch (IOException e) {
			System.out.println("Client connection error!");
		}
	}
	
	public static void sendMessage(String s){
		out.println(s);
		out.flush();
	}
		
	public static void disconnect(){
		out.println("END");
		out.flush();
	}

	public static void notifyOponent(int x, int y){
		JSONObject obj = new JSONObject();
		obj.put("cmd", "move");
		obj.put("p1", x);
		obj.put("p2", y);
		out.println(obj.toJSONString());
		out.flush();
	}
}
