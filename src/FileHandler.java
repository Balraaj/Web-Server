import java.io.File;

/**
 * Created by Balraj on 03-Feb-18.
 * This class will manage all the file related activities.
 */
public class FileHandler
{
    private static String rootPath;
    private String currentFilePath;

    public FileHandler(File filePath)
    {
        currentFilePath=rootPath+filePath;
    }

}
