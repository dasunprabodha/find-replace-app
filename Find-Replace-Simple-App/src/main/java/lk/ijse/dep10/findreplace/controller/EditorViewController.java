package lk.ijse.dep10.findreplace.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lk.ijse.dep10.findreplace.util.SearchResults;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorViewController {
    private final ArrayList<SearchResults> SearchResultList = new ArrayList<>();

    @FXML
    public Button btnDown;

    @FXML
    public Button btnReplace;

    @FXML
    public Button btnReplaceAll;

    @FXML
    public Button btnUp;

    @FXML
    public Label lblResults;

    @FXML
    public TextArea txtEditor;

    @FXML
    public TextField txtFind;

    @FXML
    private TextField txtReplace;

    private int pos = 0;

    public void initialize() {

        txtFind.textProperty().addListener((ov, previous, current) -> findResultCount());
        txtEditor.textProperty().addListener((ov, previous, current) -> findResultCount());
    }

    private void findResultCount() {
        String query = txtFind.getText();
        SearchResultList.clear();
        pos = 0;
        txtEditor.deselect();
        Pattern pattern;
        try {
            pattern = Pattern.compile(query);
        } catch (RuntimeException e) {
            return;
        }
        Matcher matcher = pattern.matcher(txtEditor.getText());

        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
//            System.out.printf("Start %d End %d \n", start, end);
            SearchResults result = new SearchResults(start, end);
            SearchResultList.add(result);


        }
        lblResults.setText(String.format("%d Results ", SearchResultList.size()));


        select();

    }

    @FXML
    public void btnDownOnAction(ActionEvent event) {
        pos++;
        if (pos == SearchResultList.size()) {
            pos = -1;
            return;
        }
        select();
    }

    private void select() {

        if (SearchResultList.isEmpty()) return;
        SearchResults searchResults = SearchResultList.get(pos);
        txtEditor.selectRange(searchResults.getStart(), searchResults.getEnd());
        lblResults.setText(String.format("%d/%d Results", (pos + 1), SearchResultList.size()));

    }

    @FXML
    public void btnReplaceAllOnAction(ActionEvent event) {
        replaceall();

    }

    private void replaceall() {
        if (SearchResultList.isEmpty()) return;
        int i = 0;
        for (i = 0; i < SearchResultList.size() + 1; i++) {
            SearchResults searchResults = SearchResultList.get(pos);
            txtEditor.selectRange(searchResults.getStart(), searchResults.getEnd());
            txtEditor.replaceSelection(txtReplace.getText());
        }
    }

    @FXML
    public void btnReplaceOnAction(ActionEvent event) {
        replace();
        txtReplace.clear();
        txtFind.clear();

    }

    private void replace() {
        if (SearchResultList.isEmpty()) return;
        SearchResults searchResults = SearchResultList.get(pos);
        txtEditor.selectRange(searchResults.getStart(), searchResults.getEnd());
        txtEditor.replaceSelection(txtReplace.getText());


    }

    @FXML
    public void btnUpOnAction(ActionEvent event) {
        pos--;
        if (pos < 0) {
            pos = SearchResultList.size();
            return;
        }
        select();

    }

}
