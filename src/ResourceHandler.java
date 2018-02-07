import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Balraj on 03-Feb-18.
 * This class will manage all the file related activities.
 */
public class ResourceHandler
{
    private static String rootPath=System.getProperty("user.dir");
    private File file;
    private String requestURL;
    private String resourcePath;
    private String resourceType;
    private boolean resourceValid;

    public ResourceHandler(String requestURL)
    {
        this.requestURL=requestURL;
        String path=rootPath+requestURL;
        if(requestURL.equals("/")) path=rootPath+"/index.html";

        resourceValid= new File(path).exists();
        setResourceProperties();
    }

    public void setResourceProperties()
    {
        if(resourceValid)
        {
            setResourcePath();
            setFile();
            setResourceType();
        }
    }

    private void setResourcePath()
    {
        resourcePath=rootPath+requestURL;
        if(requestURL.equals("/"))
            resourcePath=rootPath+"/index.html";

    }

    private void setFile()
    {
        file=new File(resourcePath);
    }

    private void setResourceType()
    {
        if(resourceValid)
        {
            if(requestURL.equals("/"))
            {
                resourceType="text/html";
            }
            else if(requestURL.matches("(/images/).*"))
            {
                resourceType="image/jpeg";
            }
        }
        else
        {
            resourceType=null;
        }
    }

    public FileInputStream getInputStream()
    {
        try
        {
            return new FileInputStream(file);
        }
        catch(FileNotFoundException e)
        {
            return null;
        }

    }

    public boolean isResourceValid()
    {
        return resourceValid;
    }

    public String getResourceType()
    {
        return resourceType;
    }

    public String getResourcePath()
    {
        return resourcePath;
    }


}
