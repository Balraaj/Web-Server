import java.util.Scanner;

/**
 * Created by Balraj on 29-Mar-18.
 */
public class UI implements Runnable
{

    public void run()
    {
        Scanner in;

        System.out.println("Multi-threaded Web Server. Please see the documentation for available commands");
        while(true)
        {
            in = new Scanner(System.in);
            System.out.print(">");
            String command = in.next();
            if(command.equals("tc"))
            {
                System.out.println(State.threadLimit);
            }
            if(command.equals("exit"))
            {
                System.exit(0);
            }
        }

    }
}
