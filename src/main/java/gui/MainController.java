package gui;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import vlookup.CsvFile;
import vlookup.CsvLimitedRowsFile;

import java.io.File;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class MainController {

    private Stage stage;
    private MainControllerHelper helper;
    private CsvFile csvFile;

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

            inputFilePath.setText(file.getAbsolutePath());
            if (outputFilePath.getText().isEmpty()) {
                outputFilePath.setText(helper.addSuffixToFileName(file.getAbsolutePath(), "_mapped"));
            }

            this.csvFile = new CsvLimitedRowsFile(file.getAbsolutePath());
            this.helper.setValuessToPicklist(this.picklistInputField, this.csvFile.getHeader());
            this.helper.setValuessToPicklist(this.picklistOutputField, this.csvFile.getHeader());
            this.helper.showFirst5Rows(this.tablePreview, this.csvFile);
        }
    }

    @FXML
    public void onOutputFileSelectClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a file to save mapped to");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Csv file", "*.csv"));
        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            outputFilePath.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void runVlookup() {}

    @FXML
    public void runPreviewVlookup() {
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
            inputMappingFilePath.setText(file.getAbsolutePath());
            this.csvFile = new CsvLimitedRowsFile(file.getAbsolutePath());
            this.helper.setValuessToPicklist(this.picklistMappingKey, this.csvFile.getHeader());
            this.helper.setValuessToPicklist(this.picklistMappingValue, this.csvFile.getHeader());
            this.helper.showFirst5Rows(this.tableMappingPreview, this.csvFile);
        }
    }




}
