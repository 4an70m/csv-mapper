package gui;

import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;
import vlookup.CsvFile;
import vlookup.CsvRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 4an70m on 21.01.2018.
 */
public class MainControllerHelper {

    public String addSuffixToFileName(String filePath, String suffix) {
        return  filePath.substring(0, filePath.lastIndexOf('.')) + suffix + filePath.substring(filePath.lastIndexOf('.'));
    }

    public void setValuessToPicklist(ChoiceBox<String> picklistInputField, String[] header) {
        List<String> picklistValues = new ArrayList<String>(Arrays.asList(header));
        picklistValues.sort(String::compareTo);
        picklistInputField.setItems(new ObservableListWrapper<String>(picklistValues));
        picklistInputField.getSelectionModel().selectFirst();
    }

    public void showFirst5Rows(TableView tablePreview, CsvFile csvFile) {
        tablePreview.getColumns().clear();
        for (String header : csvFile.getHeader()) {
            TableColumn newTableColumn = new TableColumn(header);
            newTableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<CsvRow, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue call(TableColumn.CellDataFeatures<CsvRow, String> param) {
                    return new SimpleStringProperty(param.getValue().get(param.getTableColumn().getText()));
                }
            });
            tablePreview.getColumns().add(newTableColumn);
        }
        final ObservableList<CsvRow> data = FXCollections.observableArrayList();
        for (CsvRow row : csvFile.getCsvLines()) {
            data.add(row);
        }
        tablePreview.setItems(data);
    }
}
