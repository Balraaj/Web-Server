import java.util.HashMap;

/**
 * Created by Balraj on 07-Feb-18.
 * This class stores information associated with the response.
 * some of this information is static while some other information must be generated dynamically.
 * type of data must be determined at run time.
 */
public class ResponseHandler
{
    private String version="http/1.1";
    private int statusCode;
    private String reasonPhrase;
    private String startLine;
    private HashMap<String,String> headers;

    private ResourceHandler resource;


    public ResponseHandler(int statusCode,ResourceHandler resourceHandler)
    {
        this.statusCode = statusCode;
        resource = resourceHandler;
        setReasonPhrase();
        setStartLine();
        setHeaders();
    }

    private void setReasonPhrase()
    {
        reasonPhrase=HttpCodes.getReasonPhrase(statusCode);
    }

    private void setHeaders()
    {
        headers=new HashMap<>();
        headers.put("content-length",String.valueOf(resource.getResourceSize()));
        headers.put("content-type",String.valueOf(resource.getResourceType()));
    }
    private void setStartLine()
    {
        startLine=version+" "+ statusCode +" "+reasonPhrase;
    }

    public String getStartLine()
    {
        return startLine;
    }

    public HashMap<String,String> getHeaders()
    {
        return headers;
    }
}
