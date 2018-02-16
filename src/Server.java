import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

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
            //BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
           // BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            DataInputStream in = new DataInputStream(clientSocket.getInputStream());

            HttpParser httpParser = new HttpParser(clientSocket.getInputStream());
            httpParser.parseRequest();

            ResourceHandler resourceHandler = new ResourceHandler(httpParser.getRequestURL());


            //out.write(response);
            if(resourceHandler.isResourceValid())
            {
                //BufferedReader fileInput = new BufferedReader(new InputStreamReader(resourceHandler.getInputStream()));

                DataInputStream fileInput = new DataInputStream(resourceHandler.getInputStream());

                ResponseHandler responseHandler = new ResponseHandler(200,resourceHandler);
                String startLine=responseHandler.getStartLine();
                out.writeBytes(startLine);


                // write headers
                HashMap<String,String> headers = responseHandler.getHeaders();
                for(String key: headers.keySet())
                {
                    String line = key+":"+headers.get(key);
                    out.writeBytes("\n");
                    out.writeBytes(line);
                }
                out.writeBytes("\n\n");

                byte[] output = new byte[(int)resourceHandler.getResourceSize()];
                fileInput.read(output,0,(int)resourceHandler.getResourceSize());
                out.write(output,0,(int)resourceHandler.getResourceSize());

                fileInput.close();
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
