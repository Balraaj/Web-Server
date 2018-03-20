import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Balraj on 03-Feb-18.
 */

public class Server
{
    private static int flag=0;
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(9090);

        while(true)
        {
            try
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got connection : "+flag++);
                HttpParser httpParser = new HttpParser(clientSocket.getInputStream());
                httpParser.parseRequest();
                ResourceHandler resourceHandler = new ResourceHandler(httpParser.getRequestURL());
                ResponseHandler responseHandler = new ResponseHandler(resourceHandler.getResource(),clientSocket.getOutputStream());

                responseHandler.writeResponse();
                clientSocket.close();

            }
            catch(Exception e)
            {

                serverSocket.close();
                System.out.print(e);
                break;
            }
        }


    }

}
