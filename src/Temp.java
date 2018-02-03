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
            byte[] arr = new byte[10000];
            FileInputStream in = new FileInputStream("myfile");
            in.read(arr);
            String str = new String(arr,"UTF-8");
            System.out.print(str+" and length of str is : "+str.length());

        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
}
