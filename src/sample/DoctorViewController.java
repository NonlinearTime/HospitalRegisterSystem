package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.PathIterator;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class DoctorViewController implements ControlledStage, Initializable {
    private StageController doctorController;
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<IncomeItem> incomeItems = FXCollections.observableArrayList();
    private Statement statement = Main.connection.createStatement();
    private double oldX, oldY;
    private java.time.LocalDate patientBeginDate;
    private java.time.LocalTime patientBeginTime;
    private java.time.LocalDate patientEndDate;
    private java.time.LocalTime patientEndTime;
    private String patientLocalBeginDateTime, patientLocalEndDateTime;
    private String incomeLocalBeginDateTime, incomeLocalEndDateTime;
    private boolean patientBeginDateTimeReady = false, patientEndDateTimeReady = false;
    private boolean incomeBeginDateTimeReady = false, incomeEndDateTimeReady = false;
    @FXML private JFXTreeTableView<Patient> patientsTable;
    @FXML private JFXTreeTableView<IncomeItem> incomeTable;

    @FXML
    private AnchorPane doctorAnchorPane;

    @FXML
    private JFXButton testButton;

    @FXML
    private JFXTextField serchText;

    @FXML
    private JFXButton serchIcon;

    @FXML
    private JFXButton shutdownButton;

    @FXML
    private JFXTextField serchText1;

    @FXML
    private JFXButton serchIcon1;

    @FXML
    private JFXButton shutdownButton1;

    @FXML
    private Label timeText, timeText1;

    @FXML
    private JFXDatePicker patientBeginDatePicker;

    @FXML
    private JFXTimePicker patientBeginTimePicker;

    @FXML
    private JFXDatePicker patientEndDatePicker;

    @FXML
    private JFXTimePicker patientEndTimePicker;

    @FXML
    private JFXDatePicker incomeEndDatePicker;

    @FXML
    private JFXDatePicker incomeBeginDatePicker;

    @FXML
    private JFXTimePicker incomeBeginTimePicker;

    @FXML
    private JFXTimePicker incomeEndTimePicker;


    public DoctorViewController() throws SQLException {
    }


    @Override
    public void setStageController(StageController stageController) {
        doctorController = stageController;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JFXTreeTableColumn<Patient, String> registNum = new JFXTreeTableColumn<>("RegistNum");
        registNum.setPrefWidth(201d);
        registNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Patient, String> param) {
                return param.getValue().getValue().registerNum;
            }
        });

        JFXTreeTableColumn<Patient, String> nameCol = new JFXTreeTableColumn<>("Name");
        nameCol.setPrefWidth(201d);
        nameCol.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Patient, String> param) {
                return param.getValue().getValue().name;
            }
        });

        JFXTreeTableColumn<Patient, String> registTime = new JFXTreeTableColumn<>("RegistTime");
        registTime.setPrefWidth(201d);
        registTime.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Patient, String> param) {
                return param.getValue().getValue().registerTime;
            }
        });

        JFXTreeTableColumn<Patient, String> registType = new JFXTreeTableColumn<>("RegistType");
        registType.setPrefWidth(201d);
        registType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Patient, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Patient, String> param) {
                return param.getValue().getValue().registerType;
            }
        });

        JFXTreeTableColumn<IncomeItem, String> deptName = new JFXTreeTableColumn<>("Department");
        deptName.setPrefWidth(133.5d);
        deptName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IncomeItem, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IncomeItem, String> param) {
                return param.getValue().getValue().department;
            }
        });

        JFXTreeTableColumn<IncomeItem, String> doctorNum = new JFXTreeTableColumn<>("DoctorNum");
        doctorNum.setPrefWidth(134.5d);
        doctorNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IncomeItem, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IncomeItem, String> param) {
                return param.getValue().getValue().doctorNum;
            }
        });

        JFXTreeTableColumn<IncomeItem, String> doctorName = new JFXTreeTableColumn<>("DoctorName");
        doctorName.setPrefWidth(133.5d);
        doctorName.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IncomeItem, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IncomeItem, String> param) {
                return param.getValue().getValue().doctorName;
            }
        });

        JFXTreeTableColumn<IncomeItem, String> regType = new JFXTreeTableColumn<>("RegistType");
        regType.setPrefWidth(133.5d);
        regType.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IncomeItem, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IncomeItem, String> param) {
                return param.getValue().getValue().regType;
            }
        });

        JFXTreeTableColumn<IncomeItem, String> regNum = new JFXTreeTableColumn<>("RegistNum");
        regNum.setPrefWidth(134.5d);
        regNum.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IncomeItem, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IncomeItem, String> param) {
                return param.getValue().getValue().regNum;
            }
        });


        JFXTreeTableColumn<IncomeItem, String> totalIncome = new JFXTreeTableColumn<>("TotalIncome");
        totalIncome.setPrefWidth(133.5d);
        totalIncome.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<IncomeItem, String>, ObservableValue<String>> () {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<IncomeItem, String> param) {
                return param.getValue().getValue().totalIncome;
            }
        });

        patientsTable.getColumns().setAll(registNum,nameCol,registTime,registType);
        incomeTable.getColumns().setAll(deptName, doctorNum, doctorName, regType, regNum, totalIncome);

//        ObservableList<Patient> patients = FXCollections.observableArrayList();
//        patients.add(new Patient("0000001","章紫衣","2018-12-30 11:52:26","专家号"));
//        patients.add(new Patient("0000003","范冰冰","2018-12-30 11:53:26","普通号"));
//        patients.add(new Patient("0000004","刘德华","2018-12-30 11:54:28","普通号"));

        final TreeItem<Patient> root = new RecursiveTreeItem<Patient>(patients, RecursiveTreeObject::getChildren);
        patientsTable.setRoot(root);
        patientsTable.setShowRoot(false);

//        incomeItems.add(new IncomeItem("感染科", "000001", "李时珍", "专家号", "24", "48"));
//        incomeItems.add(new IncomeItem("感染科", "000001", "李时珍", "普通号", "10", "10"));
//        incomeItems.add(new IncomeItem("内一科", "000002", "扁鹊", "普通号", "23", "23"));
//        incomeItems.add(new IncomeItem("保健科", "000003", "华佗", "专家号", "10", "20"));

        final TreeItem<IncomeItem> root1 = new RecursiveTreeItem<IncomeItem>(incomeItems, RecursiveTreeObject::getChildren);
        incomeTable.setRoot(root1);
        incomeTable.setShowRoot(false);
        onTableFresh();


//        doctorController.getStage(Main.doctorStageID).getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                double newX = doctorController.getStage(Main.doctorStageID).getX();
//                double newY = doctorController.getStage(Main.doctorStageID).getY();
//                doctorController.getStage(Main.doctorStageID).setX(newX + event.getX() - oldX);
//                doctorController.getStage(Main.doctorStageID).setY(newY + event.getY() - oldY);
//            }
//        });

//        doctorController.getStage(Main.doctorStageID).getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                oldX = event.getX();
//                oldY = event.getY();
//            }
//        });

        serchText.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.ENTER)
                    System.out.println("search1");
                    onSearchButtonClicked();
            }
        });

        serchText1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.K.ENTER) onSearchButton1Clicked();
            }
        });

        EventHandler<javafx.event.ActionEvent> eventHandler = e -> {
            Date date = new Date();
            SimpleDateFormat dataformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timeText.setText(dataformat.format(date));
            timeText1.setText(dataformat.format(date));
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();

        EventHandler<javafx.event.ActionEvent> eventFreshHandler = e -> {
//            System.out.println("fresh");
//            onTableFresh();
        };

        Timeline tableRefresher = new Timeline(new KeyFrame(Duration.millis(500), eventFreshHandler));
        tableRefresher.setCycleCount(Timeline.INDEFINITE);
        tableRefresher.play();

//        patientBeginDatePicker.valueProperty()
        patientBeginDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null && patientBeginTimePicker.getValue() != null) {
                    patientBeginDateTimeReady = true;
                    patientBeginDate = newValue;
                    onTableFresh();
//                    onSearchButtonClicked();
                    patientLocalBeginDateTime = newValue.toString() + " " + patientBeginTimePicker.getValue().toString() + ":00";
                    System.out.println(patientLocalBeginDateTime);
                }
            }
        });

        patientBeginTimePicker.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                if (newValue != null && patientBeginDatePicker.getValue() != null) {
                    patientLocalBeginDateTime = patientBeginDatePicker.getValue().toString() + " " + newValue.toString() + ":00";
                    System.out.println(patientLocalBeginDateTime);
                    patientBeginDateTimeReady = true;
                    onTableFresh();
//                    onSearchButtonClicked();
                }
            }
        });

        patientEndDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null && patientEndTimePicker.getValue() != null) {
                    patientEndDate = newValue;
                    patientEndDateTimeReady = true;
                    patientLocalEndDateTime = newValue.toString() + " " + patientEndTimePicker.getValue().toString() + ":00";
                    System.out.println(patientLocalEndDateTime);
                    onTableFresh();
//                    onSearchButtonClicked();
                }
            }
        });

        patientEndTimePicker.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                if (newValue != null && patientEndDatePicker.getValue() != null) {
                    patientEndDateTimeReady = true;
                    patientLocalEndDateTime = patientEndDatePicker.getValue().toString() + " " + newValue.toString() + ":00";
                    System.out.println(patientLocalEndDateTime);
                    onTableFresh();
//                    onSearchButtonClicked();
                }
            }
        });




        incomeBeginDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null && incomeBeginTimePicker.getValue() != null) {
                    incomeLocalBeginDateTime = newValue.toString() + " " + incomeBeginTimePicker.getValue().toString() + ":00";
                    incomeBeginDateTimeReady = true;
                    System.out.println(incomeLocalBeginDateTime);
                    onTableFresh();
//                    onSearchButton1Clicked();
                }
            }
        });

        incomeBeginTimePicker.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                if (newValue != null && incomeBeginDatePicker.getValue() != null) {
//                    onSearchButton1Clicked();
                    incomeBeginDateTimeReady = true;
                    onTableFresh();
                    incomeLocalBeginDateTime = incomeBeginDatePicker.getValue().toString() + " " + newValue.toString() + ":00";
                    System.out.println(incomeLocalBeginDateTime);
                }
            }
        });

        incomeEndDatePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                if (newValue != null && incomeEndTimePicker.getValue() != null) {
                    incomeEndDateTimeReady = true;
                    incomeLocalEndDateTime = newValue.toString() + " " + incomeEndTimePicker.getValue().toString() + ":00";
                    System.out.println(incomeLocalEndDateTime);
                    onTableFresh();
//                    onSearchButton1Clicked();
                }
            }
        });

        incomeEndTimePicker.valueProperty().addListener(new ChangeListener<LocalTime>() {
            @Override
            public void changed(ObservableValue<? extends LocalTime> observable, LocalTime oldValue, LocalTime newValue) {
                if (newValue != null && incomeEndDatePicker.getValue() != null) {
                    incomeEndDateTimeReady = true;
//                    onSearchButton1Clicked();
                    incomeLocalEndDateTime = incomeEndDatePicker.getValue().toString() + " " + newValue.toString() + ":00";
                    System.out.println(incomeLocalEndDateTime);
                    onTableFresh();
                }
            }
        });

        patientBeginTimePicker.setEditable(false);
        patientEndDatePicker.setEditable(false);
        patientBeginDatePicker.setEditable(false);
        patientEndTimePicker.setEditable(false);

        incomeBeginDatePicker.setEditable(false);
        incomeBeginTimePicker.setEditable(false);
        incomeEndDatePicker.setEditable(false);
        incomeEndTimePicker.setEditable(false);


    }

    public void goToMain() {
        doctorController.setStage(Main.loginStageID);
    }

    class Patient extends RecursiveTreeObject<Patient> {
        StringProperty registerNum, name, registerTime, registerType;
        public Patient (String registerNum, String name, String registerTime, String registerType) {
            this.registerNum = new SimpleStringProperty(registerNum);
            this.name = new SimpleStringProperty(name);
            this.registerTime = new SimpleStringProperty(registerTime);
            this.registerType = new SimpleStringProperty(registerType);
        }
    }

    class IncomeItem extends RecursiveTreeObject<IncomeItem> {
        StringProperty department, doctorNum, doctorName, regType, regNum, totalIncome;
        public IncomeItem (String department, String doctorNum, String doctorName, String regType, String regNum, String totalIncome) {
            this.department = new SimpleStringProperty(department);
            this.doctorName = new SimpleStringProperty(doctorName);
            this.doctorNum = new SimpleStringProperty(doctorNum);
            this.regType = new SimpleStringProperty(regType);
            this.regNum = new SimpleStringProperty(regNum);
            this.totalIncome = new SimpleStringProperty(totalIncome);
        }
    }

    @FXML
    private void onSearchButtonClicked() {
        patientsTable.setPredicate(new Predicate<TreeItem<Patient>>() {
            @Override
            public boolean test(TreeItem<Patient> patientTreeItem) {
                return patientTreeItem.getValue().registerNum.toString().contains(serchText.getText().toString())
                        || patientTreeItem.getValue().name.toString().contains(serchText.getText().toString())
                        || patientTreeItem.getValue().registerTime.toString().contains(serchText.getText().toString())
                        || patientTreeItem.getValue().registerType.toString().contains(serchText.getText().toString());
            }
        });
    }


    @FXML
    private void onSearchButton1Clicked() {
        incomeTable.setPredicate(new Predicate<TreeItem<IncomeItem>>() {
            @Override
            public boolean test(TreeItem<IncomeItem> incomeItemTreeItem) {
                return incomeItemTreeItem.getValue().totalIncome.toString().contains(serchText1.getText().toString())
                        || incomeItemTreeItem.getValue().regNum.toString().contains(serchText1.getText().toString())
                        || incomeItemTreeItem.getValue().regType.toString().contains(serchText1.getText().toString())
                        || incomeItemTreeItem.getValue().doctorName.toString().contains(serchText1.getText().toString())
                        || incomeItemTreeItem.getValue().doctorNum.toString().contains(serchText1.getText().toString())
                        || incomeItemTreeItem.getValue().department.toString().contains(serchText1.getText().toString());
            }
        });
    }

    @FXML
    private void onShutdownButtonClicked() {
        doctorController.closedStage(Main.doctorStageID);
    }

    private void onTableFresh() {
        patients.clear();
        incomeItems.clear();
        try {
            ResultSet rs = patientBeginDateTimeReady && patientEndDateTimeReady ?
                    statement.executeQuery(
                            "SELECT GHBH, BRMC, RQSJ, SFZJ FROM T_BRXX, T_HZXX, T_GHXX " +
                                    "WHERE T_BRXX.BRBH = T_GHXX.BRBH AND T_GHXX.HZBH = T_HZXX.HZBH " +
                                    "AND RQSJ >= '" + patientLocalBeginDateTime + "' " +
                                    "AND RQSJ <= '" + patientLocalEndDateTime + "'")
                    : statement.executeQuery(
                    "SELECT GHBH, BRMC, RQSJ, SFZJ FROM T_BRXX, T_HZXX, T_GHXX " +
                            "WHERE T_BRXX.BRBH = T_GHXX.BRBH AND T_GHXX.HZBH = T_HZXX.HZBH");
            while (rs.next()) {
                String GHBH = rs.getString("GHBH");
                String BRMC = rs.getString("BRMC");
                Date RQ = rs.getDate("RQSJ");
                Time SJ = rs.getTime("RQSJ");

                String HZLB = rs.getBoolean("SFZJ") ? "专家号" : "普通号";
                patients.add(new Patient(GHBH, BRMC, RQ.toString() + " " + SJ.toString(), HZLB));
            }

//            rs = statement.executeQuery(
//                    "SELECT HZBH, KSMC, T_GHXX.YSBH, YSMC, SFZJ, COUNT(GHRC), SUM(GHFY) FROM T_KSXX, T_KSYS, T_GHXX " +
//                            "WHERE T_KSXX.KSBH = T_KSYS.KSBH AND T_KSYS.YSBH = T_GHXX.YSBH " +
//                            "GROUP BY HZBH, KSMC, YSBH, YSMC, SFZJ");

            rs = incomeBeginDateTimeReady && incomeEndDateTimeReady ?
                    statement.executeQuery(
                            "SELECT HZBH, KSMC, T_GHXX.YSBH, YSMC, SFZJ, COUNT(GHRC), SUM(GHFY) FROM T_KSXX, T_KSYS, T_GHXX " +
                                    "WHERE T_KSXX.KSBH = T_KSYS.KSBH AND T_KSYS.YSBH = T_GHXX.YSBH " +
                                    "AND RQSJ >= '" + incomeLocalBeginDateTime + "' " +
                                    "AND RQSJ <= '" + incomeLocalEndDateTime + "' " +
                                    "GROUP BY HZBH, KSMC, YSBH, YSMC, SFZJ")
                    : statement.executeQuery(
                    "SELECT HZBH, KSMC, T_GHXX.YSBH, YSMC, SFZJ, COUNT(GHRC), SUM(GHFY) FROM T_KSXX, T_KSYS, T_GHXX " +
                            "WHERE T_KSXX.KSBH = T_KSYS.KSBH AND T_KSYS.YSBH = T_GHXX.YSBH " +
                            "GROUP BY HZBH, KSMC, YSBH, YSMC, SFZJ");
            while (rs.next()) {
                String KSMC = rs.getString("KSMC");
                String YSBH = rs.getString("YSBH");
                String YSMC = rs.getString("YSMC");
                String HZLB = rs.getBoolean("SFZJ") ? "专家号" : "普通号";
                int GHRS = rs.getInt(6);
                Double GHFY = rs.getDouble(7);
                incomeItems.add(new IncomeItem(KSMC, YSBH, YSMC, HZLB, String.valueOf(GHRS), String.valueOf(GHFY)));
            }

//            if (patientBeginDatePicker.getValue() != null && patientBeginTimePicker.getValue() != null) {
//                System.out.println(patientBeginDatePicker.getValue());
//                System.out.println(patientBeginTimePicker.getValue());
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
