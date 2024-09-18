import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.File;
import java.util.*;
import java.nio.*;
import java.nio.file.*;
//import java.util.stream.Stream;
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
        File fr = new File("/");
        
        //String find = "docroot";
        //final List<File> foundFiles = new ArrayList<>();
        //try (Stream<Path> walkStream = Files.walk(fr.toPath())) {
        //}
        //String filePath = "/home/student/projects/assignment-1-RubenVong-2";
        //Path path = Paths.get(filePath);
        //Path root = path.getRoot();


        if (fr.exists()) {
            String buffer = in.readLine();
            String[] getRequest = buffer.split("[/ ]");

            //*** Application Protocol *****
            /*while(buffer.length()!=0){
                System.out.println("Client: " + buffer);
                buffer = in.readLine();
            }*/

            out.println("HTTP/1.1 200 OK\n");
            //out.println("Content-Length: 34\n");
            //out.println("Content-Type: text/html\n\n");

            if (getRequest[2].equals("home.html")) {
                //out.println("<h1>welcome to the web Server</h1>");
                //String[] flist = directory.list("home.html");

                File doc = new File("docroot/home.html");

                String[] cache = doc.getAbsolutePath().split("/");

                
                //System.out.println(fr.listFiles());

                System.out.println(doc.getAbsolutePath());
                File getter = doc;
                int i = cache.length-1;
                //File getter = new File(cache[5]);
                while(i > 0) {
                    /*if (!(cache[i].equals("docroot") || cache[i].equals("home.html"))) {
                        getter = new File(cache[i]);
                        System.out.println(getter.getAbsolutePath());
                        break;
                    }*/
                    if(cache[i].equals("home.html")) {
                        getter = new File(cache[i]);
                        
                        break;
                    }
                    
                    i--;
                }

                File last = getter;
                System.out.println(last.getAbsolutePath());
                //System.out.println(last.listFiles());
                //Path folder = Paths.get(last);
                //File docet = new File(last, "home.html");
                //String folderP = "/";
                //String fileName = "docroot";

                /*File[] files = last.listFiles();
                for (File file : files) {
                    if (file.isDirectory()) {
                        System.out.println("files in here " + file.getCanonicalPath());
                    }
                }*/
                //System.out.println(fr.listFiles());

                //String file = "docroot/home.html";
                //Path folder = Paths.get(folderP);

                /*try {
                    Files.walk(folder).
                    filter(Files::isRegularFile).
                    filter(path -> path.getFileName().toString().equals(fileName)).
                    forEach(System.out::println);
                } catch (IOException e) {
                    out.println("A Error has occured: " + e.getMessage());
                }*/
                
                //Path filePath = Paths.get(file);

                /*try {
                    List<String> lines = Files.readAllLines(file);
                    for (String line : lines) {
                        out.println(line);
                    }
                } catch (IOException e) {
                    out.println("A Error has occured: " + e.getMessage());
                }*/
                BufferedReader br = new BufferedReader(new FileReader(last));
                String val = br.readLine();
                while ((val = br.readLine()) != null) {
                    out.println(val);
                }
                br.close();

            } else {
                out.printf("<h1>there is nothing here</h1>");
            }
        } else {
            System.out.printf("didn't find anything");
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
