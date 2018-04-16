package sample;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.net.URL;
import java.security.PublicKey;
import java.util.ResourceBundle;

public class LoginDialogController implements ControlledStage, Initializable {
    private StageController loginController;

    @FXML
    private AnchorPane loginPane;

    @FXML
    private JFXCheckBox patientCheckBox;

    @FXML
    private JFXCheckBox doctorCheckBox;

    @Override
    public void setStageController(StageController stageController) {
        loginController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        doctorCheckBox.setSelected(false);
        patientCheckBox.setSelected(false);
    }

    public void goToMain() {
        loginController.setStage(Main.loginStageID);
    }

    @FXML
    private JFXButton loginButton;

    @FXML
    private JFXPasswordField pwdText;

    @FXML
    private JFXTextField userText;

    @FXML
    private void onLoginButtonClicked() {
        if (userText.getText().trim().equals("Haines") && pwdText.getText().trim().equals("123456")) {
            if (patientCheckBox.isSelected()) {
                System.out.println("log in patient program.");
                loginController.setStage(Main.patientStageID,Main.loginStageID);
            } else if (doctorCheckBox.isSelected()) {
                System.out.println("log in doctor program.");
                loginController.setStage(Main.doctorStageID,Main.loginStageID);
            }

            System.out.println(userText.getText());
            System.out.println(pwdText.getText());
        }
    }

    @FXML
    private void patientCheckBoxChanged() {
        if (patientCheckBox.isSelected()) doctorCheckBox.setSelected(false);
    }

    @FXML
    private void doctorCheckBoxChanged() {
        if (doctorCheckBox.isSelected()) patientCheckBox.setSelected(false);
    }
}
