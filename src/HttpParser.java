/**
 * Created by Balraj on 04-Feb-18.
 */

import java.io.*;
import java.util.*;
import java.text.*;
import java.net.URLDecoder;

public class HttpParser
{
    private static final String[][] HttpReplies = HttpCodes.getCodes();

    private BufferedReader reader;
    private String method, url;
    private LinkedHashMap <String,String> headers, params;
    private int[] version;

    public HttpParser(InputStream is)
    {
        reader = new BufferedReader(new InputStreamReader(is));
        method = "";
        url = "";
        headers = new LinkedHashMap<> ();
        params = new LinkedHashMap<> ();
        version = new int[2];
    }

    public int parseRequest() throws IOException
    {
        String initial, prms[], cmd[], temp[];
        int returnCode, idx, i;
        returnCode = 200; // default is OK now

        initial = reader.readLine();

        if (initial == null || initial.length() == 0) return 0;
        if (Character.isWhitespace(initial.charAt(0))) return 400;

        cmd = initial.split("\\s");

        if (cmd.length != 3)   return 400; // If start line doesn't contain <method> <URL> <version>

        if (cmd[2].indexOf("HTTP/") == 0 && cmd[2].indexOf('.') > 5)
        {
            temp = cmd[2].substring(5).split("\\."); // extract HTTP version numbers
            try
            {
                version[0] = Integer.parseInt(temp[0]);
                version[1] = Integer.parseInt(temp[1]);
            }
            catch (NumberFormatException nfe)
            {
                returnCode = 400;
            }
        }
        else
        {
            returnCode = 400;
        }

        if (cmd[0].equals("GET") || cmd[0].equals("HEAD"))
        {
            method = cmd[0];
            idx = cmd[1].indexOf('?');
            if (idx < 0) url = cmd[1]; // URL doesn't contain parameters
            else
            {
                //URL contains parameters following code parses the parameters.
                url = URLDecoder.decode(cmd[1].substring(0, idx), "ISO-8859-1");
                prms = cmd[1].substring(idx+1).split("&");
                params = new LinkedHashMap<>();
                for (i=0; i<prms.length; i++)
                {
                    temp = prms[i].split("=");
                    if (temp.length == 2)
                    {
                        // we use ISO-8859-1 as temporary charset and then
                        // String.getBytes("ISO-8859-1") to get the data
                        params.put(URLDecoder.decode(temp[0], "ISO-8859-1"),
                                URLDecoder.decode(temp[1], "ISO-8859-1"));
                    }
                    else if(temp.length == 1 && prms[i].indexOf('=') == prms[i].length()-1)
                    {
                        // handle empty string separatedly
                        params.put(URLDecoder.decode(temp[0], "ISO-8859-1"), "");
                    }
                }
            }

            parseHeaders();
            if (headers == null) returnCode = 400;
        }
        else if (version[0] == 1 && version[1] >= 1)
        {
            if (cmd[0].equals("OPTIONS") )
            {
                method="OPTIONS";
                returnCode=400;
            }
        }
        else if (version[0] == 1 && version[1] >= 1)
        {
            if (cmd[0].equals("POST")|| cmd[0].equals("PUT") ||
                    cmd[0].equals("DELETE") || cmd[0].equals("TRACE") ||
                    cmd[0].equals("CONNECT"))
            {
                returnCode = 501; // not implemented
            }
        }
        else
        {
            // meh not understand, bad request
            returnCode = 400;
        }

        if (version[0] == 1 && version[1] >= 1 && getHeader("Host") == null)
        {
            returnCode = 400;
        }
        return returnCode;
    }

    private void parseHeaders() throws IOException
    {
        String line;
        int idx;

        // that fscking rfc822 allows multiple lines, we don't care now
        line = reader.readLine();
        while (!line.equals(""))
        {
            idx = line.indexOf(':');
            if (idx < 0)
            {
                headers = null;
                break;
            }
            else
            {
                headers.put(line.substring(0, idx).toLowerCase(), line.substring(idx+1).trim());
            }

            line = reader.readLine();
        }
    }

    public String getMethod()
    {
        return method;
    }

    public String getHeader(String key) {
        if (headers != null)
            return (String) headers.get(key.toLowerCase());
        else return null;
    }

    public LinkedHashMap<String, String> getHeaders() {
        return headers;
    }

    public String getRequestURL() {
        return url;
    }

    public String getParam(String key) {
        return (String) params.get(key);
    }

    public LinkedHashMap<String, String> getParams() {
        return params;
    }

    public String getVersion() {
        return version[0] + "." + version[1];
    }

    public int compareVersion(int major, int minor) {
        if (major < version[0]) return -1;
        else if (major > version[0]) return 1;
        else if (minor < version[1]) return -1;
        else if (minor > version[1]) return 1;
        else return 0;
    }

    public static String getHttpReply(int codevalue) {
        String key, ret;
        int i;

        ret = null;
        key = "" + codevalue;
        for (i=0; i<HttpReplies.length; i++) {
            if (HttpReplies[i][0].equals(key)) {
                ret = codevalue + " " + HttpReplies[i][1];
                break;
            }
        }

        return ret;
    }

    public static String getDateHeader() {
        SimpleDateFormat format;
        String ret;

        format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        ret = "Date: " + format.format(new Date()) + " GMT";

        return ret;
    }
}