package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vlookup.CsvFile;
import vlookup.CsvLimitedRowsFile;
import vlookup.VlookupProcessor;

import java.io.File;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class MainController {

    private Stage stage;
    private MainControllerHelper helper;
    private CsvFile csvFile;
    private CsvFile csvMappingFile;
    private VlookupProcessor vlookupProcessor;

    public MainController(Stage primaryStage) {
        this.stage = primaryStage;
        this.helper = new MainControllerHelper();
    }

    @FXML
    private TextField inputFilePath;

    @FXML
    private TextField outputFilePath;

    @FXML
    private ChoiceBox<String> picklistInputField;

    @FXML
    private ChoiceBox<String> picklistOutputField;

    @FXML
    private TextField textDefaultNullValue;

    @FXML
    private TableView tablePreview;

    @FXML
    private TextField inputMappingFilePath;

    @FXML
    private ChoiceBox<String> picklistMappingKey;

    @FXML
    private ChoiceBox<String> picklistMappingValue;

    @FXML
    private TableView tableMappingPreview;


    /*
     * Methods
     */
    @FXML
    public void onInputFileSelectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to map");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv file", "*.csv"));
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) {

            this.inputFilePath.setText(file.getAbsolutePath());
            if (this.outputFilePath.getText().isEmpty()) {
                this.outputFilePath.setText(this.helper.addSuffixToFileName(file.getAbsolutePath(), "_mapped"));
            }

            this.csvFile = new CsvLimitedRowsFile(file.getAbsolutePath());
            this.helper.setValuessToPicklist(this.picklistInputField, this.csvFile.getHeader());
            this.helper.setValuessToPicklist(this.picklistOutputField, this.csvFile.getHeader());
        }
    }

    @FXML
    public void onOutputFileSelectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to save mapped to");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv file", "*.csv"));
        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            this.outputFilePath.setText(file.getAbsolutePath());
        }
    }

    /*
     * Run lookup methods
     */

    @FXML
    public void runVlookup() {
        this.csvFile = new CsvFile(this.inputFilePath.getText());
        this.vlookupProcessor = new VlookupProcessor(this.inputMappingFilePath.getText())
                .setKeyHeaderValueHeader(this.picklistMappingKey.getValue(), this.picklistMappingValue.getValue())
                .setTargetSearchHeaderName(this.picklistInputField.getValue())
                .setTargetSearchResultHeaderName(this.picklistOutputField.getValue())
                .setDefaultFoundValue(this.textDefaultNullValue.getText())
                .buildLookup();
        this.csvFile.processLines(this.vlookupProcessor);
        this.csvFile.writeFile(this.outputFilePath.getText());
    }

    @FXML
    public void runPreviewVlookup() {
        this.csvFile = new CsvLimitedRowsFile(this.inputFilePath.getText());
        this.vlookupProcessor = new VlookupProcessor(this.inputMappingFilePath.getText())
                .setKeyHeaderValueHeader(this.picklistMappingKey.getValue(), this.picklistMappingValue.getValue())
                .setTargetSearchHeaderName(this.picklistInputField.getValue())
                .setTargetSearchResultHeaderName(this.picklistOutputField.getValue())
                .setDefaultFoundValue(this.textDefaultNullValue.getText())
                .buildLookup();
        this.csvFile.processLines(this.vlookupProcessor);
        String[] columnNames = new String[] {
                this.picklistInputField.getValue(),
                this.picklistOutputField.getValue()
        };
        this.helper.showFirst5RowsWithColumns(this.tablePreview,this.csvFile, columnNames);
    }

    @FXML
    public void refreshPreview() {
        String[] columnNames = new String[] {
                this.picklistInputField.getValue(),
                this.picklistOutputField.getValue()
        };
        this.helper.showFirst5RowsWithColumns(this.tablePreview, this.csvFile, columnNames);
    }

    /*
     * Mapping methods
     */
    @FXML
    public void onInputMappingFileSelectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select file with mapping");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv file", "*.csv"));
        File file = fileChooser.showOpenDialog(this.stage);
        if (file != null) {
            this.inputMappingFilePath.setText(file.getAbsolutePath());
            this.csvMappingFile = new CsvLimitedRowsFile(file.getAbsolutePath());
            this.helper.setValuessToPicklist(this.picklistMappingKey, this.csvMappingFile.getHeader());
            this.helper.setValuessToPicklist(this.picklistMappingValue, this.csvMappingFile.getHeader());
        }
    }

    @FXML
    public void showMappingKeyColumn() {
        String[] columnNames = new String[] {
            this.picklistMappingKey.getValue(),
            this.picklistMappingValue.getValue()
        };
        this.helper.showFirst5RowsWithColumns(this.tableMappingPreview, this.csvMappingFile, columnNames);

    }

    @FXML
    public void showMappingValueColumn() {
        String[] columnNames = new String[] {
                this.picklistMappingKey.getValue(),
                this.picklistMappingValue.getValue()
        };
        this.helper.showFirst5RowsWithColumns(this.tableMappingPreview, this.csvMappingFile, columnNames);
    }

    @FXML
    public void showInputFieldColumn() {
        String[] columnNames = new String[] {
                this.picklistInputField.getValue(),
                this.picklistOutputField.getValue()
        };
        this.helper.showFirst5RowsWithColumns(this.tablePreview, this.csvFile, columnNames);
        this.picklistOutputField.getSelectionModel().select(this.picklistInputField.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void showOutputFieldColumn() {
        String[] columnNames = new String[] {
                this.picklistInputField.getValue(),
                this.picklistOutputField.getValue()
        };
        this.helper.showFirst5RowsWithColumns(this.tablePreview, this.csvFile, columnNames);
    }


}
