package sample;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    public static String patientStageID = "PatientStage";
    public static String patientStageRes = "sample.fxml";

    public static String loginStageID = "LoginStage";
    public static String loginStageRes = "LoginDialog.fxml";

    public static String doctorStageID = "DoctorStage";
    public static String doctorStageRes = "DoctorView.fxml";

    private StageController stageController;

    private String dburl = "jdbc:mysql://localhost:3306/HospitalDataBase";
    public static Connection connection;
    public static String userID;
    public static BooleanProperty hasMoney = new SimpleBooleanProperty(false);
    public static DoubleProperty YCJE = new SimpleDoubleProperty(0d);

    @Override
    public void start(Stage primaryStage) throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(dburl,"root","lhm199710");

        stageController = new StageController();
        stageController.setPrimaryStage("primaryStage", primaryStage);

        stageController.loadStage(loginStageID, loginStageRes);
        stageController.getStage(loginStageID).setTitle("登录");
        stageController.getStage(loginStageID).setResizable(false);
        stageController.getStage(loginStageID).getIcons().add(new Image("sample/pic/hospital.png"));
//        stageController.getStage(loginStageID).initStyle(StageStyle.UNDECORATED);
        stageController.loadStage(patientStageID, patientStageRes);
        stageController.getStage(patientStageID).setTitle("医院挂号管理系统");
        stageController.getStage(patientStageID).initStyle(StageStyle.UNDECORATED);
        stageController.getStage(patientStageID).getIcons().add(new Image("sample/pic/hospital.png"));
        stageController.loadStage(doctorStageID,doctorStageRes);
        stageController.getStage(doctorStageID).setTitle("医院挂号管理系统");
        stageController.getStage(doctorStageID).initStyle(StageStyle.UNDECORATED);
        stageController.getStage(doctorStageID).getIcons().add(new Image("sample/pic/hospital.png"));

        stageController.setStage(loginStageID);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
