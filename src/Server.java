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
        while(true)
        {
            handleRequest();
        }

    }

    private static void handleRequest()
    {
        String response = "HTTP/1.1 200 OK\n" +
                "Content-type: text/html\n" +
                "Content-length: 10000\n" +
                "\n";
        try
        {
            ServerSocket serverSocket = new ServerSocket(9090);
            Socket clientSocket = serverSocket.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            HttpParser httpParser = new HttpParser(clientSocket.getInputStream());
            httpParser.parseRequest();

            ResourceHandler resourceHandler = new ResourceHandler(httpParser.getRequestURL());

            out.write(response);
            if(resourceHandler.isResourceValid())
            {
                BufferedReader fileInput = new BufferedReader(new InputStreamReader(resourceHandler.getInputStream()));
                String str;

                while((str=fileInput.readLine())!=null)
                {
                    out.write(str);
                }
            }
            out.flush();
            out.close();
            in.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
}
