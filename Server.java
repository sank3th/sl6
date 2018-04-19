import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server{
	public static void main(String args[]){
		Scanner sc = new Scanner(System.in);
		int num_clients;
		try{
			int serverPort = 7899,i;
			String server_response;
			ServerSocket ss = new ServerSocket(serverPort);
			System.out.println("Server Started");
			
			System.out.print("Enter No. of Clients: ");
			num_clients = sc.nextInt();
			
			Socket clientSocket[] = new Socket[num_clients];
			DataInputStream dis[] = new DataInputStream[num_clients];
			DataOutputStream dos[] = new DataOutputStream[num_clients];
			int client_response[] = new int[num_clients];
			String ack[] = new String[num_clients];
			
			System.out.println("Waiting for "+num_clients+" clients to connect");
			
			for(i=0;i<num_clients;i++){
				clientSocket[i] = ss.accept();
				System.out.println("Client "+i+" connected");
				
				dis[i] = new DataInputStream(clientSocket[i].getInputStream());
				dos[i] = new DataOutputStream(clientSocket[i].getOutputStream());
				
			}
			
			server_response = "Global Commit";
			System.out.println("Sending Query to Commit to clients");
			for(i=0;i<num_clients;i++){
				dos[i].writeUTF("Query");
				dos[i].flush();
			}
			
			for(i=0;i<num_clients;i++){
				client_response[i] = dis[i].readInt();
		              	System.out.println("Response "+client_response[i]+" From Client "+i+" Received");
				if(client_response[i] == 2){
					server_response = "Global Abort";
				}
			}
			
			System.out.println("Sending Response "+server_response+" to clients");
			for(i=0;i<num_clients;i++){
				dos[i].writeUTF(server_response);
			}
			
			System.out.println("Waiting for acknowledgement");
			for(i=0;i<num_clients;i++){
				ack[i] = dis[i].readUTF();	
				System.out.println("Received "+ack[i]+" acknowledgement from client "+i);
				clientSocket[i].close();			
			}
			
			System.out.println("Received Acknowlegment from All Clients\n--- End ---");
			ss.close();
			sc.close();
		}
		catch(Exception e){
			System.out.println("Error: ");
			e.printStackTrace();
		}
	}
}	
					
