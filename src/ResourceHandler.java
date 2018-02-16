import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by Balraj on 03-Feb-18.
 * This class will manage all the fileHandle related activities.
 */
public class ResourceHandler
{
    private static String rootPath=System.getProperty("user.dir");
    private File fileHandle;
    private String requestURL;
    private String resourcePath;
    private String resourceType;
    private long resourceSize;
    private boolean resourceValid;

    public ResourceHandler(String requestURL)
    {
        this.requestURL=requestURL;
        String path=rootPath+requestURL;

        // if request URL only contains / then interpret it as a request for index.html
        if(requestURL.equals("/")) path=rootPath+"/index.html";

        resourceValid= new File(path).exists();
        setResourceProperties();
    }

    private void setResourceProperties()
    {
        if(resourceValid) // if resource exists then set the related properties. otherwise they will be null.
        {
            setResourcePath();
            setFileHandle();
            setResourceType();
            setResourceSize();
        }
    }

    private void setResourcePath()
    {
        resourcePath=rootPath+requestURL;
        if(requestURL.equals("/"))
            resourcePath=rootPath+"/index.html";

    }

    private void setFileHandle()
    {
        fileHandle =new File(resourcePath);
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
    }

    private void setResourceSize()
    {
        resourceSize= fileHandle.length();
    }

    public FileInputStream getInputStream()
    {
        try
        {
            return new FileInputStream(fileHandle);
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

    public long getResourceSize()
    {
        return resourceSize;
    }

}
