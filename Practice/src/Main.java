import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties p = System.getProperties();  // open system properties file
        p.setProperty("myProp", "myValue");     // add an entry
        p.list(System.out);                     // list the file's contents
    }
}
