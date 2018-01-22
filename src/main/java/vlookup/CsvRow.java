package vlookup;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class CsvRow extends LinkedHashMap<String, String> {

    public CsvRow(Map<String, String> stringStringMap) {
        super(stringStringMap);
    }

    public String[] getValues() {
        return this.values().toArray(new String[this.values().size()]);
    }

    public String[] getValues(String[] header) {
        String[] result = new String[this.values().size()];
        int i = 0;
        for (String headerKey : header) {
            result[i++] = this.get(headerKey);
        }
        return result;
    }

    public String[] getHeader() {
        return this.keySet().toArray(new String[this.keySet().size()]);
    }
}
