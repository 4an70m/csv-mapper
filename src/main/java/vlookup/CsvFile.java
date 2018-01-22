package vlookup;

import org.apache.commons.csv.*;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class CsvFile {

    protected String path;
    protected List<CsvRow> csvLines;
    private String[] header;

    protected CsvFile() {
    }

    public CsvFile(String path) {
        this.path = path;
        this.readFile();
    }

    public void readFile() {
        this.csvLines = new LinkedList<>();
        try (
                Reader in = new FileReader(this.path);
                CSVParser parser = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in)
        ) {
            this.setHeder(parser.getHeaderMap());
            for (CSVRecord record : parser.getRecords()) {
                this.csvLines.add(new CsvRow(record.toMap()));
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void processLines(LineProcessor lineProcessor) {
        List<LineProcessor> lineProcessors = new LinkedList<>();
        lineProcessors.add(lineProcessor);
        this.processLines(lineProcessors);
    }

    public void processLines(List<LineProcessor> lineProcessors) {
        for (CsvRow csvRow : this.csvLines) {
            for (LineProcessor processor : lineProcessors) {
                processor.processLine(csvRow);
            }
        }
    }

    public void writeFile() {
        this.writeFile(this.path);
    }

    public void writeFile(String path) {
        CSVFormat format = CSVFormat.RFC4180.withQuoteMode(QuoteMode.ALL).
                withDelimiter(',').withHeader(this.getHeader());
        try (
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));
                CSVPrinter csvPrinter = new CSVPrinter(writer,format)
        ) {
            for (CsvRow row : this.csvLines) {
                csvPrinter.printRecord((Object[]) row.getValues(this.getHeader()));
            }
            csvPrinter.flush();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public List<CsvRow> getCsvLines() {
        return this.csvLines;
    }

    public String[] getHeader() {
        return this.header;
    }

    public void setHeder(Map<String,Integer> header) {
        this.header = header.keySet().toArray(new String[header.keySet().size()]);
    }
}
