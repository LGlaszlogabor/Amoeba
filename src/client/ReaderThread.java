package client;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import gui.AmobaPanel;

public class ReaderThread extends Thread{
	private BufferedReader reader;
	
	public ReaderThread(BufferedReader in){
		this.reader= in;
	}
	
	public void run(){
		String tmp;
		try {
			while(!"END".equals(tmp = reader.readLine())){
				System.out.println(tmp);
				JSONObject obj = new JSONObject();
				obj = (JSONObject)new JSONParser().parse(tmp);
				if(AmobaPanel.isServer){
					if(Server.type == 1)
						AmobaPanel.set(Integer.parseInt(obj.get("p1").toString()), Integer.parseInt(obj.get("p2").toString()), 0);
					else
						AmobaPanel.set(Integer.parseInt(obj.get("p1").toString()), Integer.parseInt(obj.get("p2").toString()), 1);
					Server.canPut = true;
				}else{
					if(Client.type == 1)
						AmobaPanel.set(Integer.parseInt(obj.get("p1").toString()), Integer.parseInt(obj.get("p2").toString()), 0);
					else
						AmobaPanel.set(Integer.parseInt(obj.get("p1").toString()), Integer.parseInt(obj.get("p2").toString()), 1);
					Client.canPut = true;
				}
			}		
			if(AmobaPanel.isServer) Server.ss.close();
			else Client.s.close();
		}catch (IOException | ParseException e) {e.printStackTrace();}
	}
}