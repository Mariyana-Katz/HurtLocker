import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        //get text from the rawDataFile
        String output = (new Main()).readRawDataToString();
        System.out.println(output);
        String parsedJSON = (new Main()).parseToJsonString(output);
        System.out.println("The Parsed JSON: " + parsedJSON);
    }

    public String parseToJsonString(String input)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"items\": [");

        //split into group of items
        Pattern patternGroups = Pattern.compile("(.*?)##");
        Matcher matcherGroups = patternGroups.matcher(input);

        while (matcherGroups.find()) {
            sb.append("{");
            String group = matcherGroups.group(1);
            //System.out.println("Group: " + group);

            //parse each key-value pair
            Pattern pattern = Pattern.compile("\\b(\\w+):([a-zA-Z_0-9/.]+)\\b");
            Matcher matcher = pattern.matcher(group);

            while (matcher.find()) {
                String key = matcher.group(1);
                String value = matcher.group(2);
                sb.append("\"").append(key).append("\":\"").append(value).append("\",");

                //System.out.println(key + ':' + value);
            }
            sb.append("},");
        }
        sb.append("]");
        sb.append("}");
        return sb.toString();
    }
}




