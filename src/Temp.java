import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * Created by Balraj on 03-Feb-18.
 */
public class Temp
{
    public static void main(String[] args)
    {
        try
        {
            String mystr = "image/";
            if(mystr.matches("(image/).*"))
            {
                System.out.println("True");
            }
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
}
