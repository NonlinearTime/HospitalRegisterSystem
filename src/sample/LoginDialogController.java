package sample;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URL;
import java.security.PublicKey;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginDialogController implements ControlledStage, Initializable {
    private StageController loginController;
    private Statement statement;
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
        try {
            statement = Main.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    private void onLoginButtonClicked() throws SQLException {
        JFXAlert alert = new JFXAlert((Stage) loginButton.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);

        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("Warning"));
        layout.setBody(new Label("Password is wrong!"));

        JFXButton closeButton = new JFXButton("ACCEPT");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(closeButton);
        alert.setContent(layout);

        if (patientCheckBox.isSelected()) {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM T_BRXX" +
                            " WHERE BRBH = " +
                            userText.getText().trim());
            if (rs.wasNull()) {
                System.out.println("null");
                return;
            }
            if (!rs.next()) {
                System.out.println("next");
                return;
            }
            String pwd = rs.getString("DLKL");
            Double mon = rs.getDouble("YCJE");
            Main.YCJE.setValue(mon);
            System.out.println(mon);
            Main.hasMoney.setValue(mon != 0d);
            if (pwdText.getText().trim().equals(pwd)) {
                System.out.println("log in patient program.");
                loginController.setStage(Main.patientStageID,Main.loginStageID);
            } else alert.show();

        } else if (doctorCheckBox.isSelected()) {
            ResultSet rs = statement.executeQuery(
                    "SELECT * FROM T_KSYS" +
                            " WHERE YSBH = " +
                            userText.getText().trim());
            if (rs.wasNull()) {
                System.out.println("null");
                return;
            }
            if (!rs.next()) {
                System.out.println("next");
                return;
            }
            String pwd = rs.getString("DLKL");
            if (pwdText.getText().trim().equals(pwd)) {
                System.out.println("log in doctor program.");
                loginController.setStage(Main.doctorStageID, Main.loginStageID);
            } else alert.show();
        }
        Main.userID = userText.getText().trim();
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
