import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Balraj on 03-Feb-18.
 * This class will manage all the fileHandle related activities.
 */
final class ResourceHandler
{
    public static String rootPath=System.getProperty("user.dir")+"/Website";
    private Resource resource;
    private String requestURL;

    ResourceHandler(String receivedURL) throws IOException
    {
        requestURL=rootPath+receivedURL;
        if(State.systemConfigured)
        {
            if(receivedURL.equals("/"))
            {
                requestURL = rootPath + "/index.html";
            }

            resource = new Resource();
            resource.setValid(isResourceValid());
            resource.setInputStream(new FileInputStream(requestURL));
            resource.setSize(new File(requestURL).length());
            resource.setPath(requestURL);
            resource.setType(Files.probeContentType(Paths.get(requestURL)));
        }
        else
        {
            String url = System.getProperty("user.dir")+"/index.html";
            resource = new Resource();
            resource.setValid(true);
            resource.setInputStream(new FileInputStream(url));
            resource.setSize(new File(url).length());
            resource.setPath(url);
            resource.setType(Files.probeContentType(Paths.get(url)));
        }

    }
    private boolean isResourceValid()
    {
        boolean resourceValid = true;
        if(!(new File(requestURL).exists()))
        {
            System.out.println("File not found, now setting the output to 404");
            System.out.println("Requested Path: "+requestURL);
            requestURL=rootPath+ "/notFound.html";
            resourceValid=false;
            System.out.println("Current Path: "+requestURL);
        }
        return resourceValid;
    }
    Resource getResource()
    {
        return resource;
    }

}
