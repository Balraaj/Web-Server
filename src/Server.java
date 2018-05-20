import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Balraj on 03-Feb-18.
 */

public class Server
{
    private static int flag=0;
    public static void main(String[] args) throws IOException
    {
        int threadLimit=100;
        int temp=0;
        if(args.length>=1)
        {
            temp=Integer.parseInt(args[0]);
        }
        if((temp>0)&&(temp<100))
        {
            threadLimit=temp;
        }

        State.threadLimit=threadLimit;
        State.port=9090;
        State.setSystemConfigured();
        ServerSocket serverSocket = new ServerSocket(9090);
        ExecutorService pool = Executors.newFixedThreadPool(threadLimit);
        new Thread(new UI()).start();

        while(true)
        {
            try
            {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Got connection : "+flag++);
                pool.execute(new Task(clientSocket));
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
