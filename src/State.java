import java.io.File;

/**
 * Created by Balraj on 29-Mar-18.
 */
public class State
{
    static int port;
    static int threadLimit;
    static boolean systemConfigured;

    static void setSystemConfigured()
    {
        String url = System.getProperty("user.dir")+"/Website/index.html";
        if(new File(url).exists())
        {
            systemConfigured=true;
        }
        else
        {
            systemConfigured=false;
        }
    }
}
