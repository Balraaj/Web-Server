import java.util.Scanner;

/**
 * Created by Balraj on 29-Mar-18.
 */
public class UI implements Runnable
{

    public void run()
    {
        Scanner in;

        System.out.println("Multi-threaded Web Server is running on port "+State.port+". Use help for any other information");
        while(true)
        {
            in = new Scanner(System.in);
            System.out.print(">");
            String command = in.next();
            if(command.equals("tc"))
            {
                System.out.println(State.threadLimit);
            }
            else if(command.equals("rp"))
            {
                System.out.println(ResourceHandler.rootPath);
            }
            else if(command.equals("port"))
            {
                System.out.println(State.port);
            }
            else if(command.equals("stop"))
            {
                System.exit(0);
            }
            else if(command.equals("help"))
            {
                System.out.println("1. tc - (thread count) shows the size of thread pool\n2. rp -he (root path) shows the currently set root path" +
                        "\n3. port - shows the port number on which server is listening\n4. stop - stops the server");
            }
            else
            {
                System.out.println(command+" is not a valid command");
            }
        }

    }
}
