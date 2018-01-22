package vlookup;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.LinkedList;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class CsvLimitedRowsFile extends CsvFile {

    public static final int ROW_LIMIT = 10;
    private int limit;

    public CsvLimitedRowsFile(String path, int limit) {
        super();
        this.path = path;
        this.limit = limit;
        this.readFile();
    }

    public CsvLimitedRowsFile(String path) {
        this(path, ROW_LIMIT);
    }

    public void readFile() {
        this.csvLines = new LinkedList<>();
        try (
                Reader in = new FileReader(this.path);
                CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in)
        ) {
            this.setHeder(parser.getHeaderMap());
            int i = 0;
            for (CSVRecord record : parser.getRecords()) {
                this.csvLines.add(new CsvRow(record.toMap()));
                if (i >= this.limit) {
                    break;
                }
                i++;
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
}
