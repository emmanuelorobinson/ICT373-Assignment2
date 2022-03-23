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
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    Button btnManageUnits, btnSiblings, btnNewCustomer, btnUpdateCustomer, btnNewEnrolled, btnCreateNewCustomer;
    CheckBox chkCustomerType, chkNewCustomerType;
    ComboBox combo_box;
    TextField txtCustomerName, txtCustomerAge, txtNewCustomerName, txtNewCustomerEmail;
    ListView<String> enrollUnits;

    Alert alert = new Alert(AlertType.NONE);

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
            ObservableList<String> supplements;
            supplements = enrollUnits.getSelectionModel().getSelectedItems();
            List<String> subscripedTo = supplements.subList(0, supplements.size());
            c = new Customer(name, email);

            // System.out.println("Customer created with " + s.printCustomer());
            if (!magazine.getMagazine()
                    .addCustomer(c) /* || !magazine.getSubscription().addEnrolled(c, subscripedTo) */) {
                alert.setContentText("Customer could not be added!");
                alert.setAlertType(AlertType.ERROR);
                alert.show();
            } else {
                GridPane emptyGrid = new GridPane();
                root.setCenter(emptyGrid);
                GridPane customerPane = createCustomerPaneVBox();
                root.setLeft(customerPane);
                System.out.println("Customer created with " + c.printCustomer());
            }

        }
    };

    EventHandler<ActionEvent> comboSelected = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {

            customer = magazine.getMagazine().getCustomerByName(combo_box.getValue() + "");
            txtCustomerName.setText("" + customer.getName());
            txtCustomerAge.setText("" + customer.getEmail());
            textEnrolled.setText("...loading supplements...");
            textEnrolled.setText(magazine.getAllSupplements(customer.getCustomerId()));
            if (customer instanceof PayingCustomer) {
                chkCustomerType.setSelected(true);
            } else {
                chkCustomerType.setSelected(false);
            }

        }
    };

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();
        GridPane customerPane = createCustomerPaneVBox();

        Text lblHeading = new Text("Unimanager program v2.0");
        lblHeading.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text lblFooter = new Text("Think before you print - save this uni");
        lblFooter.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        root.setTop(lblHeading);
        root.setLeft(customerPane);
        root.setBottom(lblFooter);

        scene = new Scene(root, 1000, 500);

        primaryStage.setTitle("Uni Manager");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

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

        Text lblAge = new Text("Age");
        lblAge.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAge, 0, 2);

        Text lblEnrolledUnits = new Text("Enrolled in:");
        lblEnrolledUnits.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEnrolledUnits, 0, 3);

        Text lblType = new Text("Type:");
        lblType.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblType, 0, 5);

        // column 1 controls
        combo_box = new ComboBox();
        combo_box.setItems(FXCollections.observableArrayList(magazine.getAllCustomersNames()));
        combo_box.setOnAction(comboSelected);
        grid.add(combo_box, 1, 0);

        txtCustomerName = new TextField();
        grid.add(txtCustomerName, 1, 1);

        txtCustomerAge = new TextField();
        grid.add(txtCustomerAge, 1, 2);

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
        chkCustomerType = new CheckBox("Paying");
        grid.add(chkCustomerType, 0, 0);

        btnSiblings = new Button("Siblings");
        grid.add(btnSiblings, 0, 1);

        btnNewCustomer = new Button("New");
        btnNewCustomer.setOnAction(btnNewCustomerClicked);
        grid.add(btnNewCustomer, 1, 0);

        btnUpdateCustomer = new Button("Update");
        grid.add(btnUpdateCustomer, 1, 1);

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

        Text lblAge = new Text("Age");
        lblAge.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAge, 0, 3);

        Text lblEnrolledUnits = new Text("Enrolled in:");
        lblEnrolledUnits.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEnrolledUnits, 0, 5);

        Text lblType = new Text("Type:");
        lblType.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        // grid.add(lblType, 0, 7);

        txtNewCustomerName = new TextField();
        grid.add(txtNewCustomerName, 0, 2);

        txtNewCustomerEmail = new TextField();
        grid.add(txtNewCustomerEmail, 0, 4);

        enrollUnits = new ListView<String>();
        enrollUnits.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        enrollUnits.setItems(FXCollections.observableArrayList(magazine.getSupplementsName()));
        grid.add(enrollUnits, 0, 6);

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

        try {
            File kd = new File("kd.dat");

            FileOutputStream fos = new FileOutputStream(kd);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            writeSerializedObject(oos, ms);

            FileInputStream fis = new FileInputStream(kd);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Magazine newMag = new Magazine();
            Subscription newSub = new Subscription();

            MagazineService newMagService = readSerializedObject(ois);
            newMag = newMagService.getMagazine();
            newSub = newMagService.getSubscription();

            System.out.println(newMag.getTitle());
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        launch(args);

    }

    public static void writeSerializedObject(ObjectOutputStream oos, MagazineService mag) {
        try {
            oos.writeObject(mag);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static MagazineService readSerializedObject(ObjectInputStream ois) {
        MagazineService mag = null;
        try {
            mag = (MagazineService) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return mag;
    }
}
