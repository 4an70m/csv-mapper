package vlookup;

import com.sun.istack.internal.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class VlookupProcessor implements LineProcessor {

    private static final String DEFAULT_VALUE = "#N/A";
    private Map<String, String> lookupMap;
    private CsvFile csvFile;
    private String hKey;
    private String hValue;
    private String targetSearchHeaderName;
    private String targetSearchResultHeaderName;
    private String defaultFoundValue;

    public VlookupProcessor(@NotNull String pathToCsv) {
        this.csvFile = new CsvFile(pathToCsv);
        this.defaultFoundValue = DEFAULT_VALUE;
    }
    
    public VlookupProcessor setKeyHeaderValueHeader(@NotNull String hKey, @NotNull String hValue) {
        this.hKey = hKey;
        this.hValue = hValue;
        return this;
    }

    public VlookupProcessor setTargetSearchHeaderName(@NotNull String targetSearchHeaderName) {
        this.targetSearchHeaderName = targetSearchHeaderName;
        if (null == this.targetSearchResultHeaderName) {
            this.targetSearchResultHeaderName = targetSearchHeaderName;
        }
        return this;
    }

    public VlookupProcessor setTargetSearchResultHeaderName(@NotNull String targetSearchResultHeaderName) {
        this.targetSearchResultHeaderName = targetSearchResultHeaderName;
        return this;
    }

    public VlookupProcessor setDefaultFoundValue(String defaultFoundValue) {
        this.defaultFoundValue = defaultFoundValue;
        return this;
    }

    public VlookupProcessor buildLookup() {
        this.lookupMap = new HashMap<>();
        for(CsvRow row : this.csvFile.getCsvLines()) {
            String key = row.get(this.hKey);
            String value = row.get(this.hValue);
            this.lookupMap.put(key, value);
        }
        return this;
    }

    @Override
    public void processLine(CsvRow csvRow) {
        String targetSearchValue = this.lookupMap.getOrDefault(csvRow.get(this.targetSearchHeaderName), this.defaultFoundValue);
        csvRow.put(this.targetSearchResultHeaderName, targetSearchValue);
    }
}
