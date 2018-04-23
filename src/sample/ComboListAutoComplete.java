package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Window;

import javax.swing.*;
import java.awt.*;
import java.security.Key;
import java.util.EventListener;
import java.util.stream.Stream;

public class ComboListAutoComplete<T> {
    private JFXComboBox<T> cmb;
    String filter = "";
    private ObservableList<T> originalItems;

    public ComboListAutoComplete (JFXComboBox<T> cmb) {
        this.cmb = cmb;
        originalItems = FXCollections.observableArrayList(cmb.getItems());
        cmb.setTooltip(new Tooltip());
        cmb.getEditor().setOnKeyPressed(this::handleOnKeyPressed);
        cmb.setOnHiding(this::handleOnHiding);
    }

    public void handleOnKeyPressed(KeyEvent e) {
        ObservableList<T> filteredList = FXCollections.observableArrayList();
        KeyCode code = e.getCode();

        if (code.isLetterKey()) filter += e.getText();
        if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
            filter = filter.substring(0,filter.length() - 1);
            cmb.getItems().setAll(originalItems);
        }
        if (code == KeyCode.ESCAPE) filter = "";
        if (filter.length() == 0) {
            filteredList = originalItems;
            cmb.getTooltip().hide();
        } else {
            Stream<T> items = cmb.getItems().stream();
            String txtUsr = filter.toString().toLowerCase();
            items.filter(e1 -> e1.toString().toLowerCase().contains(txtUsr)).forEach(filteredList::add);
            cmb.getTooltip().setText(txtUsr);
//            Window stage = cmb.getScene().getWindow();
//            double posX = stage.getX() + cmb.getParent().getParent().getBoundsInParent().getMinX() + cmb.getBoundsInParent().getMinX();
//            double posY = stage.getY() + cmb.getParent().getParent().getBoundsInParent().getMinY() + cmb.getBoundsInParent().getMinY();
//            cmb.getTooltip().show(stage, posX, posY);
            cmb.show();
        }
        cmb.getItems().setAll(filteredList);
//        cmb.show();
    }

    public void handleOnHiding(Event e) {
        filter = "";
        cmb.getTooltip().hide();
        T s = cmb.getSelectionModel().getSelectedItem();
        cmb.getItems().setAll(originalItems);
        cmb.getSelectionModel().select(s);
    }
}
