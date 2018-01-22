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

import java.util.*;

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
        tablePreview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

    public void showFirst5RowsWithColumns(TableView tablePreview, CsvFile csvFile, String[] columnNames) {
        tablePreview.getColumns().clear();
        tablePreview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        if (columnNames[0] == columnNames[1]) {
            columnNames = new String[]{
                    columnNames[0]
            };
        }
        for (String header : columnNames) {
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
            CsvRow newRow = new CsvRow();
            for (String header : columnNames) {
                newRow.put(header, row.get(header));
            }
            data.add(newRow);
        }
        tablePreview.setItems(data);
    }
}
