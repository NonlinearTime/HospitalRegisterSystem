package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements ControlledStage , Initializable {
    private StageController patientController;
    @FXML
    private JFXButton enterButton;

    @FXML
    private void onMousePressed() {
        System.out.println("hello world!");
        System.out.println(enterButton.getButtonType());
    }

    @FXML
    private JFXButton quitButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private JFXComboBox deptName;

    @FXML
    private JFXTextField doctorName;

    @FXML
    private JFXTextField regType;

    @FXML
    private JFXTextField regName;

    @FXML
    private JFXTextField totalMount;

    @FXML
    private JFXTextField rightMount;

    @FXML
    private JFXTextField retMount;

    @FXML
    private JFXTextField regNum;


    @Override
    public void setStageController(StageController stageController) {
        patientController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deptName.getItems().add("hello world!");
        deptName.getEditor().setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                deptName.show();
            }
        });
//        ChangeListener<? super T> changeListener = ;
        deptName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
//                System.out.println(oldValue);
//                System.out.println(newValue);
                if (newValue != null) deptName.getEditor().setText(newValue.substring(0,2));
            }
        });
        new ComboListAutoComplete<String>(deptName);
    }

    public void goToMain() {
        patientController.setStage(Main.loginStageID);
    }

    @FXML
    private void onQuitButtonClicked() {
        patientController.closedStage(Main.patientStageID);
        System.out.println("quit");
    }

    @FXML
    private void onClearButtonClicked() {
        System.out.println("clear");
        doctorName.clear();
        regType.clear();
        regName.clear();
        totalMount.clear();
        rightMount.clear();
        retMount.clear();
        regNum.clear();
        deptName.getEditor().clear();
    }

    @FXML
    void onDeptNameMouseClicked(MouseEvent event) {
        deptName.show();
    }


}

