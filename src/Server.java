import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Balraj on 03-Feb-18.
 */
public class Server
{
    public static void main(String[] args)
    {
        try
        {
            String response = "HTTP/1.1 200 OK\n" +
                              "Content-type: text/plain\n" +
                              "Content-length: 10000\n" +
                    "\n";
            ServerSocket serverSocket = new ServerSocket(9090);
            Socket clientSocket = serverSocket.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            //DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
            String str;
            System.out.println("Got a request");

            BufferedReader file = new BufferedReader(new FileReader("myfile"));
            out.write(response);
            while((str=file.readLine())!=null)
            {
                out.write(str+"\n");
            }
            out.flush();
            out.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
}
