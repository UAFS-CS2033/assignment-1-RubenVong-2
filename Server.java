import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
//import java.io.FileReader;

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
        //FileReader fr = new FileReader("assignment-1-RubenVong-2/docroot/home.html");

        //*** Application Protocol *****
        String buffer = in.readLine();
        String[] getRequest = buffer.split("[/ ]");
        /*while(buffer.length()!=0){
            System.out.println("Client: " + buffer);
            buffer = in.readLine();
        }*/
        
        out.printf("HTTP/1.1 200 OK\n");
        out.printf("Content-Length: 34\n");
        out.printf("Content-Type: text/html\n\n");

        if (getRequest[2].equals("home.html")) {
            out.printf("<h1>welcome to the web Server</h1>");
            String file = "assignment-1-RubenVong-2/docroot/home.html";
            //BufferedReader br = new BufferedReader(file);
            //String val;
            //while ((val = br.readLine()) != null) {
                //System.out.println(val);
            //}
            //br.close();
        } else {
            out.printf("<h1>this is not home</h1>");
        }
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
