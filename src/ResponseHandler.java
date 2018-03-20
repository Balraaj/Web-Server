import java.io.*;
import java.util.HashMap;

/**
 * Created by Balraj on 07-Feb-18.
 * This class stores information associated with the response.
 * some of this information is static while some other information must be generated dynamically.
 * type of data must be determined at run time.
 */
final class ResponseHandler
{
    private int statusCode;
    private String reasonPhrase;
    private String startLine;
    private HashMap<String,String> headers;
    private Resource resource;
    private BufferedOutputStream out;
    private BufferedInputStream in;


    ResponseHandler(Resource resource, OutputStream outputStream)
    {
        this.resource = resource;
        out = new BufferedOutputStream(outputStream);
        in = new BufferedInputStream(resource.getInputStream());
        setStatusCode();
        setReasonPhrase();
        setStartLine();
        setHeaders();
    }
    private void setStatusCode(){
        if(resource.isValid())
        {
            statusCode=200;
        }
        else
        {
            statusCode=404;
        }
    }
    private void setReasonPhrase()
    {
        reasonPhrase=HttpCodes.getReasonPhrase(statusCode);
    }
    private void setHeaders()
    {
        headers=new HashMap<String,String>();
        headers.put("content-length",String.valueOf(resource.getSize()));
        headers.put("content-type",String.valueOf(resource.getType()));
    }
    private void setStartLine()
    {
        startLine="http/1.1"+" "+ statusCode +" "+reasonPhrase+"\n";
    }

    private String getStartLine()
    {
        return startLine;
    }
    private String getHeaders()
    {
        StringBuilder line = new StringBuilder();
        for(String key: headers.keySet())
        {
           String temp = key+":"+headers.get(key)+"\n";
           line.append(temp);
        }
        line.append("\n");
        return line.toString();
    }

    private void writeStartLine() throws IOException
    {
        out.write(getStartLine().getBytes());
    }
    private void writeHeaders() throws IOException
    {
        out.write(getHeaders().getBytes());
    }
    private void writeBody() throws IOException
    {
        byte[] arr = new byte[(int)resource.getSize()];
        in.read(arr);
        out.write(arr);
    }

    void writeResponse() throws IOException
    {
        writeStartLine();
        writeHeaders();
        writeBody();
        in.close();
        out.close();
    }
}
