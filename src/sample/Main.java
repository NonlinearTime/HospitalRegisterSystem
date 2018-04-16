package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static String patientStageID = "PatientStage";
    public static String patientStageRes = "sample.fxml";

    public static String loginStageID = "LoginStage";
    public static String loginStageRes = "LoginDialog.fxml";

    public static String doctorStageID = "DoctorStage";
    public static String doctorStageRes = "DoctorView.fxml";

    private StageController stageController;

    @Override
    public void start(Stage primaryStage) throws Exception{
        stageController = new StageController();
        stageController.setPrimaryStage("primaryStage", primaryStage);

        stageController.loadStage(loginStageID, loginStageRes);
        stageController.getStage(loginStageID).setTitle("登录");
        stageController.getStage(loginStageID).setResizable(false);
//        stageController.getStage(loginStageID).initStyle(StageStyle.UNDECORATED);
        stageController.loadStage(patientStageID, patientStageRes);
        stageController.getStage(patientStageID).setTitle("医院挂号管理系统");
        stageController.getStage(patientStageID).initStyle(StageStyle.DECORATED);
        stageController.loadStage(doctorStageID,doctorStageRes);
        stageController.getStage(doctorStageID).setTitle("医院挂号管理系统");

        stageController.setStage(loginStageID);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
