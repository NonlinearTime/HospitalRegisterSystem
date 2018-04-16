package sample;

import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableListValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.util.Callback;

import java.awt.event.ActionEvent;
import java.awt.geom.PathIterator;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class DoctorViewController implements ControlledStage, Initializable {
    private StageController doctorController;
    private ObservableList<Patient> patients = FXCollections.observableArrayList();
    private ObservableList<IncomeItem> incomeItems = FXCollections.observableArrayList();
    @FXML private JFXTreeTableView<Patient> patientsTable;
    @FXML private JFXTreeTableView<IncomeItem> incomeTable;
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
        patients.add(new Patient("0000001","章紫衣","2018-12-30 11:52:26","专家号"));
        patients.add(new Patient("0000003","范冰冰","2018-12-30 11:53:26","普通号"));
        patients.add(new Patient("0000004","刘德华","2018-12-30 11:54:28","普通号"));

        final TreeItem<Patient> root = new RecursiveTreeItem<Patient>(patients, RecursiveTreeObject::getChildren);
        patientsTable.setRoot(root);
        patientsTable.setShowRoot(false);

        incomeItems.add(new IncomeItem("感染科", "000001", "李时珍", "专家号", "24", "48"));
        incomeItems.add(new IncomeItem("感染科", "000001", "李时珍", "普通号", "10", "10"));
        incomeItems.add(new IncomeItem("内一科", "000002", "扁鹊", "普通号", "23", "23"));
        incomeItems.add(new IncomeItem("保健科", "000003", "华佗", "专家号", "10", "20"));

        final TreeItem<IncomeItem> root1 = new RecursiveTreeItem<IncomeItem>(incomeItems, RecursiveTreeObject::getChildren);
        incomeTable.setRoot(root1);
        incomeTable.setShowRoot(false);

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
                System.out.println(serchText1.getText());
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

}
