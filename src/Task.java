import java.io.IOException;
import java.net.Socket;

/**
 * Created by Balraj on 24-Mar-18.
 * This class represents the task that is to be executed for each request.
 * It implements the Runnable so that object of this class can be passed to executor.
 */
public class Task implements Runnable
{
    private Socket clientSocket;

    Task(Socket clientSocket)
    {
        this.clientSocket=clientSocket;
    }


    @Override
    public void run()
    {
        try
        {
            HttpParser httpParser = new HttpParser(clientSocket.getInputStream());
            httpParser.parseRequest();
            ResourceHandler resourceHandler = new ResourceHandler(httpParser.getRequestURL());
            ResponseHandler responseHandler = new ResponseHandler(resourceHandler.getResource(),clientSocket.getOutputStream());

            responseHandler.writeResponse();
            clientSocket.close();
        }
        catch(IOException e)
        {
            System.out.println(e);
        }

    }
}
