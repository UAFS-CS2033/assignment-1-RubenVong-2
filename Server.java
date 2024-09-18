import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.FileReader;

public class Server{
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private int portNo;

    public Server(int portNo){
        this.portNo=portNo;
    }

    private void processConnection() throws IOException{
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
        //*** Application Protocol *****
        buffer = in.readLine();
        while(buffer.length()!=0){
            System.out.println("Client: " + buffer);
            buffer = in.readLine();
        }
            
        String filePath = "assignment-1-RubenVong-2/docroot/home.html"; //This will retrieve the files from docroot
        out.println("HTTP/1.1 200 OK\n"); //this make sure the contents are displayed
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String val = br.readLine();
        while ((val = br.readLine()) != null) {
             out.println(val); //this will append the content to the page
        }
        br.close();
        in.close();
        out.close();
    }

    public void run() throws IOException{
        boolean running = true;
       
        serverSocket = new ServerSocket(portNo);
        System.out.printf("Listen on Port: %d\n",portNo);
        while(running){
            clientSocket = serverSocket.accept();
            //** Application Protocol
            processConnection();
            clientSocket.close();
        }
        serverSocket.close();
    }
    public static void main(String[] args0) throws IOException{
        Server server = new Server(8080);
        server.run();
    }
}
