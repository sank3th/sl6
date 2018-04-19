import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		try{
			int serverPort = 7899,client_response;
			String server_response;
			Socket cs = new Socket("localhost",serverPort);
			System.out.println("Connected to Server");
			DataInputStream dis = new DataInputStream(cs.getInputStream());
			DataOutputStream dos = new DataOutputStream(cs.getOutputStream());
			
	            	server_response = dis.readUTF();
			if(server_response.equals("Query")){
				System.out.println("Please Choose An Operation:\n1:Commit\n2:Abort");
				client_response = sc.nextInt();
				dos.writeInt(client_response);
			}
		
			server_response = dis.readUTF();
		    	System.out.println("Received "+server_response+" From Server");

		    	if(server_response.equals("Global Commit")){
		    		dos.writeUTF("Commit");
			}
			else{
				dos.writeUTF("Abort");
			}

			dis.close();
			dos.close();
			cs.close();
			sc.close();
		} 
		catch (IOException e){
			System.out.println("**** ERROR ******");
		    e.printStackTrace();
		}
	}
}
