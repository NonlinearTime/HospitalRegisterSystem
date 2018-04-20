package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXTextField;
import com.mysql.jdbc.log.NullLogger;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements ControlledStage , Initializable {
    private StageController patientController;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private boolean hasMoney;
    private StringProperty localDeptName, localDoctorName, localRegType, localRegName;
    private StringProperty localTotMount, localRightMount, localRetMount, localRegNum;
    @FXML
    private JFXButton enterButton;

    @FXML
    private JFXButton quitButton;

    @FXML
    private JFXButton clearButton;

    @FXML
    private AnchorPane anchor_pane;

    @FXML
    private JFXComboBox deptName;

    @FXML
    private JFXComboBox doctorName;

    @FXML
    private JFXComboBox regType;

    @FXML
    private JFXComboBox regName;

    @FXML
    private JFXTextField totalMount;

    @FXML
    private JFXTextField rightMount;

    @FXML
    private JFXTextField retMount;

    @FXML
    private JFXTextField regNum;

    @FXML
    private Label lableCondition;


    @Override
    public void setStageController(StageController stageController) {
        patientController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        localDeptName = new SimpleStringProperty("");
        localDoctorName = new SimpleStringProperty("");
        localRegName = new SimpleStringProperty("");
        localRegType = new SimpleStringProperty("");
        localRegNum = new SimpleStringProperty("");
        localTotMount = new SimpleStringProperty("");
        localRetMount = new SimpleStringProperty("");
        localRightMount = new SimpleStringProperty("");

        try {
            statement = Main.connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM T_KSXX");
            while (rs.next()) {
                String tmp = rs.getString("KSBH") + " " + rs.getString("KSMC") + " " + rs.getString("PYZS");
                deptName.getItems().add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM T_KSYS");
            while (rs.next()) {
                String tmp = rs.getString("YSBH") + " " + rs.getString("YSMC") + " " + rs.getString("PYZS");
                doctorName.getItems().add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            ResultSet rs = statement.executeQuery("SELECT DISTINCT HZMC, PYZS FROM T_HZXX");
            while (rs.next()) {
                String tmp = rs.getString("HZMC") + " " + rs.getString("PYZS");
                regName.getItems().add(tmp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        deptName.getEditor().setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                deptName.show();
            }
        });

        doctorName.getEditor().setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                doctorName.show();
            }
        });

        regType.getEditor().setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                regType.show();
            }
        });

        regName.getEditor().setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent event) {
                regName.show();
            }
        });

        regType.getItems().add("专家号");
        regType.getItems().add("普通号");

//        ChangeListener<? super T> changeListener = ;
        deptName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (newValue != null) {
                    String[] res = newValue.split(" ");
                    if (res.length > 1)  {
                        localDeptName.setValue(newValue);
                        Platform.runLater(() -> deptName.getEditor().setText(newValue.substring(7,10).trim()));
                    }
                }
            }
        });

        doctorName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (newValue != null) {
                    String[] res = newValue.split(" ");
                    if (res.length > 1) {
                        localDoctorName.setValue(newValue);
                        Platform.runLater(() -> doctorName.getEditor().setText(res[1]));
                    }
                }
            }
        });

        regName.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue observable, String oldValue, String newValue) {
                if (newValue != null) {
                    String[] res = newValue.split(" ");
                    if (res.length > 1) {
                        localRegName.setValue(newValue);
                        Platform.runLater(() -> {
                            regName.getEditor().setText(newValue.substring(0,2));
                            System.out.println(localDeptName.getValue().substring(0,6));
                            String KSBH = localDeptName.getValue().substring(0,6);
                            String YSBH = localDoctorName.getValue().substring(0,6);
                            String HZLB = regType.getEditor().getText().toString();
                            String HZMC = regName.getEditor().getText().toString();
                            boolean SFZJ = HZLB.equals("专家号");
                            try {
                                ResultSet rs = statement.executeQuery("SELECT * FROM T_HZXX");
//                    ResultSet rs = statement.executeQuery("SELECT * FROM T_HZXX WHERE HZMC = '肝病'");
                                while (rs.next()) {
                                    String hzmc = rs.getString("HZMC");
                                    String ksbh = rs.getString("KSBH");
                                    boolean sfzj = rs.getBoolean("SFZJ");
                                    double tmp = rs.getDouble("GHFY");
                                    if(HZMC.equals(hzmc) && sfzj == SFZJ && KSBH.equals(ksbh)) {
                                        if (tmp > Main.YCJE.getValue()) {
                                            lableCondition.setText("您的账户余额为:" + String.valueOf(Main.YCJE.getValue()) + ", 余额不足，请补齐费用！");
                                            totalMount.setDisable(false);
                                            enterButton.setDisable(true);
                                        }
                                        rightMount.setText(String.valueOf(tmp));
                                    }
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        });
                    }
                }
            }
        });

//        regName.getEditor().setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent event) {
//                System.out.println(localDeptName.getValue().substring(0,6));
//                String KSBH = localDeptName.getValue().substring(0,6);
//                String YSBH = localDoctorName.getValue().substring(0,6);
//                String HZLB = regType.getEditor().getText().toString();
//                String HZMC = regName.getEditor().getText().toString();
//                boolean SFZJ = HZLB.equals("专家号");
//                try {
//                    ResultSet rs = statement.executeQuery("SELECT * FROM T_HZXX");
////                    ResultSet rs = statement.executeQuery("SELECT * FROM T_HZXX WHERE HZMC = '肝病'");
//                    while (rs.next()) {
//                        String hzmc = rs.getString("HZMC");
//                        String ksbh = rs.getString("KSBH");
//                        boolean sfzj = rs.getBoolean("SFZJ");
//                        double tmp = rs.getDouble("GHFY");
//                        if(HZMC.equals(hzmc) && sfzj == SFZJ && KSBH.equals(ksbh)) {
//                            if (tmp > Main.YCJE.getValue()) {
//                                lableCondition.setText("您的账户余额为:" + String.valueOf(Main.YCJE.getValue()) + ", 余额不足，请补齐费用！");
//                                totalMount.setDisable(false);
//                            }
//                            rightMount.setText(String.valueOf(tmp));
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


        totalMount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (totalMount.getText().length() > 0) {
                    double ret = Main.YCJE.getValue() + Double.parseDouble(totalMount.getText().toString()) - Double.parseDouble(rightMount.getText().toString());
                    if (ret < 0) {
                        lableCondition.setText("缴费金额不足！");
                        enterButton.setDisable(true);
                        return;
                    }
                    retMount.setText(String.valueOf(Main.YCJE.getValue() + Double.parseDouble(totalMount.getText().toString()) - Double.parseDouble(rightMount.getText().toString())));
                    lableCondition.setText("");
                    enterButton.setDisable(false);
                }

            }
        });

//        totalMount.setOnMouseExited(new EventHandler<javafx.scene.input.MouseEvent>() {
//            @Override
//            public void handle(javafx.scene.input.MouseEvent event) {
//                if (totalMount.getText().length() > 0) retMount.setText(String.valueOf(Main.YCJE.getValue() + Double.parseDouble(totalMount.getText().toString()) - Double.parseDouble(rightMount.getText().toString())));
//            }
//        });

        new ComboListAutoComplete<String>(deptName);
        new ComboListAutoComplete<String>(doctorName);
        new ComboListAutoComplete<String>(regName);


        Main.hasMoney.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    totalMount.setDisable(newValue);
                    retMount.setDisable(newValue);
                }
            }
        });
//        totalMount.disableProperty().bind(Main.hasMoney);
//        retMount.disableProperty().bind(Main.hasMoney);
        rightMount.setEditable(false);
        regNum.setEditable(false);
        retMount.setEditable(false);
        Main.YCJE.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                lableCondition.setText("账户余额: " + String.valueOf(Main.YCJE.getValue()));
            }
        });
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
        doctorName.getEditor().clear();
        regType.getEditor().clear();
        regName.getEditor().clear();
        totalMount.clear();
        rightMount.clear();
        retMount.clear();
        regNum.clear();
        deptName.getEditor().clear();
    }

    @FXML
    private void onEnterButtonClicked() throws SQLException {
        String HZBH = "";
        String KSMC = deptName.getEditor().getText().toString();
        String YSMC = doctorName.getEditor().getText().toString();
        String YSBH = localDoctorName.getValue().toString().substring(0,6);
        String BRBH = Main.userID;
        String HZMC = regName.getEditor().getText().toString();
        String HZLB = regType.getEditor().getText().toString();
        boolean SFZJ = HZLB.equals("专家号");
        int GHRS = 0;
        ResultSet rs = statement.executeQuery("SELECT * FROM T_HZXX");
        while (rs.next()) {
            String hzmc = rs.getString("HZMC");
            boolean sfzj = rs.getBoolean("SFZJ");
            if(HZMC.equals(hzmc) && sfzj == SFZJ) {
                HZBH = rs.getString("HZBH");
                GHRS = rs.getInt("GHRS");
            }
        }

        rs = statement.executeQuery("SELECT COUNT(*) FROM T_GHXX WHERE HZBH = '" + HZBH + "'");
        rs.next();
        int GHRC = rs.getInt(1) + 1;
        if (GHRC > GHRS) {
            lableCondition.setText("今日该号种挂号人数已满！");
            return;
        }
        double GHFY = Double.parseDouble(rightMount.getText().toString());
        double ZHYE = Main.YCJE.getValue() - GHFY;
        if (ZHYE < 0) ZHYE = 0;

        preparedStatement = Main.connection.prepareStatement(
                "INSERT INTO T_GHXX (HZBH, YSBH, BRBH, GHRC, THBZ, GHFY) " +
                "VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
        System.out.println(BRBH);
        preparedStatement.setString(1,HZBH);
        preparedStatement.setString(2,YSBH);
        preparedStatement.setString(3,BRBH);
        preparedStatement.setInt(4,GHRC);
        preparedStatement.setBoolean(5,false);
        preparedStatement.setDouble(6,GHFY);
        preparedStatement.executeUpdate();

        rs = preparedStatement.getGeneratedKeys();
        rs.next();
        int GHBH = rs.getInt(1);
        System.out.println(GHBH);
        regNum.setText(String.valueOf(GHBH));
        lableCondition.setText("挂号成功！");
        enterButton.setDisable(true);

        preparedStatement = Main.connection.prepareStatement("UPDATE T_BRXX " +
                "SET YCJE = ? WHERE BRBH = ?");
        preparedStatement.setDouble(1,ZHYE);
        preparedStatement.setString(2,Main.userID);
        preparedStatement.executeUpdate();
    }

    @FXML
    void onDeptNameMouseClicked(MouseEvent event) {
        deptName.show();
    }
}

