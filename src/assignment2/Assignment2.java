/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;

/**
 *
 * @author 33961412
 */
public class Assignment2 extends Application {

    static private MagazineService magazine = new MagazineService();

    Customer customer;

    BorderPane root;
    Scene scene;
    // Label selectedCustomer, lblName, lblAge, lblEnrolled;
    TextArea textEnrolled;
    Text lblHeading;
    Button btnManageUnits, btnSiblings, btnNewCustomer, btnUpdateCustomer, btnNewEnrolled, btnCreateNewCustomer;
    CheckBox chkCustomerType, chkNewCustomerType;
    ComboBox combo_box;
    TextField txtCustomerName, txtCustomerEmail, txtNewCustomerName, txtNewCustomerEmail;
    // ListView<String> MagSupplements;
    ListView<Supplement> MagSupplements;
    Alert alert = new Alert(AlertType.NONE);

    GridPane customerPane;

    EventHandler<ActionEvent> btnNewCustomerClicked = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {

            root.setCenter(createNewCustomerPaneVBox());

        }
    };

    EventHandler<ActionEvent> btnNewCustomerCreate = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            Customer c;

            String name = txtNewCustomerName.getText() != "" ? txtNewCustomerName.getText() : "Jon";
            String email = txtNewCustomerEmail.getText() != "" ? txtNewCustomerEmail.getText() : "email@email.com";

            ObservableList<Supplement> supplements;

            supplements = MagSupplements.getSelectionModel().getSelectedItems();
            List<Supplement> subscripedTo = supplements.subList(0, supplements.size());

            System.out.println(subscripedTo);

            c = new Customer(name, email);

            if (!magazine.getMagazine().addCustomer(c)) {

                alert.setContentText("Customer could not be added!");
                alert.setAlertType(AlertType.ERROR);
                alert.show();

            } else {

                for (Supplement s : subscripedTo) {
                    magazine.getSubscription().addSupplement(c.getCustomerId(), s, magazine.getMagazine());
                }

                GridPane emptyGrid = new GridPane();
                root.setCenter(emptyGrid);

                combo_box.setItems(FXCollections.observableArrayList(magazine.getMagazine().getCustomerNames()));
                System.out.println("Customer created with " + c.printCustomer());
            }

        }
    };

    EventHandler<ActionEvent> comboSelected = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            customer = magazine.getMagazine().getCustomerByName(combo_box.getValue() + "");

            txtCustomerName.setText("" + customer.getName());
            txtCustomerEmail.setText("" + customer.getEmail());

            textEnrolled.setText("...loading supplements...");
            textEnrolled.setText(magazine.getSupplements(customer.getCustomerId()));

            if (customer instanceof PayingCustomer) {
                chkCustomerType.setSelected(true);
            } else {
                chkCustomerType.setSelected(false);
            }

        }
    };

    public GridPane createCustomerPaneVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // column 0 labels
        Text lblCustomer = new Text("Customer");
        lblCustomer.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomer, 0, 0);

        Text lblCustomerName = new Text("Customer Name:");
        lblCustomerName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomerName, 0, 1);

        Text lblEmail = new Text("Age");
        lblEmail.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEmail, 0, 2);

        Text lblEnrolledUnits = new Text("Enrolled in:");
        lblEnrolledUnits.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEnrolledUnits, 0, 3);

        Text lblType = new Text("Type:");
        lblType.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblType, 0, 5);

        // column 1 controls
        combo_box = new ComboBox();
        combo_box.setItems(FXCollections.observableArrayList(magazine.getCustomersNames()));
        combo_box.setOnAction(comboSelected);

        grid.add(combo_box, 1, 0);

        txtCustomerName = new TextField();
        grid.add(txtCustomerName, 1, 1);

        txtCustomerEmail = new TextField();
        grid.add(txtCustomerEmail, 1, 2);

        textEnrolled = new TextArea();
        grid.add(textEnrolled, 1, 3);

        grid.add(createButtonGroupVBox(), 1, 5);

        return grid;
    }

    public GridPane createButtonGroupVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // column 0 labels
        chkCustomerType = new CheckBox("Paying Customer");
        grid.add(chkCustomerType, 0, 0);

        btnSiblings = new Button("Associate Customer");
        grid.add(btnSiblings, 0, 1);

        btnNewCustomer = new Button("New");
        btnNewCustomer.setOnAction(btnNewCustomerClicked);
        grid.add(btnNewCustomer, 1, 0);

        btnUpdateCustomer = new Button("Update");
        grid.add(btnUpdateCustomer, 1, 1);

        return grid;
    }

    public GridPane createOpenSaveGridPane(Stage primaryStage, BorderPane bp) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // column 0 labels
        File recordsDir = new File(System.getProperty("user.home"), "/uni/records");
        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(recordsDir);
        Button btnOpen = new Button("Open File");
        btnOpen.setOnAction(e -> {
            fileChooser.setTitle("Open Magazine Service File");
            fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Magazine Files", "*.dat"),
                    new ExtensionFilter("All Files", "*.*"));
            File selectedFile = fileChooser.showOpenDialog(primaryStage);
            if (selectedFile != null) {
                magazine.readMagazineService(selectedFile);
                customerPane = createCustomerPaneVBox();
                lblHeading.setText(magazine.getMagazine().getTitle());
                root.setLeft(customerPane);
                root.setCenter(new GridPane());
            }

        });

        Button btnSave = new Button("Save File");
        btnSave.setOnAction(e -> {

            File selectedFile = fileChooser.showSaveDialog(primaryStage);
            if (selectedFile != null) {
                magazine.writeMagazineService(selectedFile);
            }

        });

        grid.add(btnOpen, 0, 0);
        grid.add(btnSave, 1, 0);

        return grid;
    }

    public GridPane createNewCustomerPaneVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // column 0 labels
        Text lblCustomer = new Text("New Customer");
        lblCustomer.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblCustomer, 0, 0);

        Text lblCustomerName = new Text("Name:");
        lblCustomerName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomerName, 0, 1);

        Text lblAge = new Text("Email");
        lblAge.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAge, 0, 3);

        Text lblEnrolledUnits = new Text("Enrolled in:");
        lblEnrolledUnits.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEnrolledUnits, 0, 5);

        Text lblType = new Text("Type:");
        lblType.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblType, 0, 7);

        txtNewCustomerName = new TextField();
        grid.add(txtNewCustomerName, 0, 2);

        txtNewCustomerEmail = new TextField();
        grid.add(txtNewCustomerEmail, 0, 4);

        MagSupplements = new ListView<Supplement>();
        MagSupplements.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        // MagSupplements.setItems(FXCollections.createObservableList(magazine.getSupplementsName()));
        MagSupplements.setItems(FXCollections.observableArrayList(magazine.getSups()));
        grid.add(MagSupplements, 0, 6);

        // GridPane subGrid = new GridPane();
        // subGrid.setHgap(20);
        // chkNewCustomerType = new CheckBox("Paying");
        // subGrid.add(chkNewCustomerType, 0, 0);
        btnCreateNewCustomer = new Button("Create");
        // btnCreateNewCustomer.setDisable(true);
        btnCreateNewCustomer.setOnAction(btnNewCustomerCreate);
        grid.add(btnCreateNewCustomer, 0, 7);

        // grid.add(subGrid, 0, 7);
        return grid;
    }

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();
        customerPane = createCustomerPaneVBox();
        GridPane openSavePane = createOpenSaveGridPane(primaryStage, root);

        lblHeading = new Text("Magazine Service Information System");
        lblHeading.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text lblFooter = new Text("Think before you print - save this uni");
        lblFooter.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        root.setTop(lblHeading);
        root.setLeft(customerPane);
        root.setBottom(openSavePane);

        scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("Magazine Service");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        Magazine mag = new Magazine("The Daily Express", 10.0f);
        Subscription sub = new Subscription();

        Supplement sup1 = new Supplement("Music", 10.0f);
        Supplement sup2 = new Supplement("Sports", 20.0f);
        Supplement sup3 = new Supplement("Entertainment", 30.0f);
        Supplement sup4 = new Supplement("Art", 40.0f);

        // add supplements to magazine
        mag.addSupplement(sup1);
        mag.addSupplement(sup2);
        mag.addSupplement(sup3);
        mag.addSupplement(sup4);

        // create a paying customer and an associate customer
        PayingCustomer pc1 = new PayingCustomer("Mark",
                "mark@example.com", "c");
        sub.addSupplement(pc1.getCustomerId(), sup1, mag);
        sub.addSupplement(pc1.getCustomerId(), sup2, mag);

        PayingCustomer pc2 = new PayingCustomer("Sam",
                "sam@example.com", "d");
        sub.addSupplement(pc2.getCustomerId(), sup2, mag);
        sub.addSupplement(pc2.getCustomerId(), sup4, mag);

        AssociateCustomer ac1 = new AssociateCustomer("john",
                "john@example.com");
        sub.addSupplement(ac1.getCustomerId(), sup3, mag);
        sub.addSupplement(ac1.getCustomerId(), sup4, mag);
        ac1.setPayingCustomer(pc1);

        AssociateCustomer ac2 = new AssociateCustomer("jane",
                "jane@example.com");
        sub.addSupplement(ac2.getCustomerId(), sup1, mag);
        sub.addSupplement(ac2.getCustomerId(), sup3, mag);
        ac2.setPayingCustomer(pc1);

        AssociateCustomer ac3 = new AssociateCustomer("mary",
                "mary@example.com");
        sub.addSupplement(ac3.getCustomerId(), sup2, mag);
        sub.addSupplement(ac3.getCustomerId(), sup3, mag);
        ac3.setPayingCustomer(pc2);

        AssociateCustomer ac4 = new AssociateCustomer("joe",
                "joe@example.com");
        sub.addSupplement(ac4.getCustomerId(), sup1, mag);
        sub.addSupplement(ac4.getCustomerId(), sup2, mag);
        sub.addSupplement(ac4.getCustomerId(), sup3, mag);
        ac4.setPayingCustomer(pc2);

        // add customer to magazine
        mag.addCustomer(pc1);
        mag.addCustomer(pc2);
        mag.addCustomer(ac1);
        mag.addCustomer(ac2);
        mag.addCustomer(ac3);
        mag.addCustomer(ac4);

        MagazineService ms = new MagazineService(mag, sub);

        // save the magazine
        File selectedFile = new File("magazine.dat");
        ms.writeMagazineService(selectedFile);

        // read the magazine
        magazine = new MagazineService();

        launch(args);

    }

}
