import java.io.InputStream;

/**
 * Created by Balraj on 16-Mar-18.
 * This class represents the resource that is requested by the client. The resource handler class returns
 * an object of Resource(this class) type which can be used to determine properties of resource such as size,type etc.
 */
final class Resource
{
    private InputStream inputStream;
    private String type;
    private String path;
    private long size;
    private boolean valid;

    void setInputStream(InputStream inputStream)
    {
        this.inputStream = inputStream;
    }
    void setType(String type)
    {
        this.type = type;
    }
    void setPath(String path)
    {
        this.path = path;
    }
    void setSize(long size)
    {
        this.size = size;
    }
    void setValid(boolean valid)
    {
        this.valid = valid;
    }

    boolean isValid()
    {
        return valid;
    }
    String getType()
    {
        return type;
    }
    String getPath()
    {
        return path;
    }
    long getSize()
    {
        return size;
    }
    InputStream getInputStream()

    {
        return inputStream;
    }
}
