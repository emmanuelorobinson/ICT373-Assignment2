/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import java.util.List;

import javax.swing.event.ChangeListener;

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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    TextArea textEnrolled;
    TextArea textPayingCustomers;
    Text lblHeading;

    Button btnManageUnits, btnSiblings, btnNewCustomer, btnUpdateCustomer, btnNewEnrolled, btnCreateNewCustomer,
            btnView, btnCreate, btnEdit, btnCreateNewSupplement, btnEditMag;

    CheckBox chkCustomerType, chkNewCustomerType;
    ComboBox combo_box;

    TextField txtCustomerName, txtCustomerType, txtCustomerEmail, txtCustomerAddrStNo, txtCustomerAddrStName,
            txtCustomerAddrSuburb,
            txtCustomerAddrPostcode, txtNewCustomerName, txtNewCustomerEmail, txtNewCustomerAddrStNo,
            txtNewCustomerAddrStName, txtNewCustomerAddrSuburb,
            txtNewCustomerAddrPostcode;
    TextField txtMagazineName, txtMagazineCost;
    TextField txtSupp, txtSuppName, txtSuppCost;

    RadioButton rbPaying, rbNotPaying;

    ListView<String> MagSupplements;
    ListView<String> MagPayingCustomers;
    Alert alert = new Alert(AlertType.NONE);

    GridPane customerPane;

    EventHandler<ActionEvent> btnNewCustomerClicked = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {

            root.setLeft(createNewCustomerPaneVBox());
            root.setRight(createNewSupplementPaneVBox());

        }
    };

    EventHandler<ActionEvent> btnNewSupplementCreate = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {

            String name = txtSuppName.getText();
            int cost = Integer.parseInt(txtSuppCost.getText());

            Supplement s;

            s = new Supplement(name, cost);

            // add supplement to magazine
            if (magazine.getMagazine().addSupplement(s)) {
                alert.setAlertType(AlertType.INFORMATION);
                alert.setContentText("Supplement added to magazine");
                alert.showAndWait();
            } else {
                alert.setAlertType(AlertType.ERROR);
                alert.setContentText("Supplement not added to magazine");
                alert.showAndWait();
            }

        }
    };

    EventHandler<ActionEvent> btnNewCustomerCreate = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent e) {
            Customer c;

            String name = txtNewCustomerName.getText() != "" ? txtNewCustomerName.getText() : "Jon";
            String email = txtNewCustomerEmail.getText() != "" ? txtNewCustomerEmail.getText() : "email@email.com";
            int addrStNo = txtNewCustomerAddrStNo.getText() != "" ? Integer.parseInt(txtNewCustomerAddrStNo.getText())
                    : 1;
            String addrStName = txtNewCustomerAddrStName.getText() != "" ? txtNewCustomerAddrStName.getText()
                    : "Street";
            String addrSuburb = txtNewCustomerAddrSuburb.getText() != "" ? txtNewCustomerAddrSuburb.getText()
                    : "Suburb";
            int addrPostcode = txtNewCustomerAddrPostcode.getText() != ""
                    ? Integer.parseInt(txtNewCustomerAddrPostcode.getText())
                    : 2000;

            ObservableList<String> supplements;

            supplements = MagSupplements.getSelectionModel().getSelectedItems();
            List<String> subscripedTo = supplements.subList(0, supplements.size());

            System.out.println(subscripedTo);

            if (rbNotPaying.isSelected()) {
                c = new AssociateCustomer(name, email);
            } else {
                c = new PayingCustomer(name, email, "c");

            }

            c.getAddress().setStreetNo(addrStNo);
            c.getAddress().setStreetName(addrStName);
            c.getAddress().setSuburb(addrSuburb);
            c.getAddress().setPostcode(addrPostcode);

            if (!magazine.getMagazine().addCustomer(c)) {

                alert.setContentText("Customer could not be added!");
                alert.setAlertType(AlertType.ERROR);
                alert.show();

            } else {

                for (String s : subscripedTo) {
                    Supplement tempSup = magazine.findSupplement(s);

                    magazine.getSubscription().addSupplement(c.getCustomerId(), tempSup, magazine.getMagazine());
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

            try {
                txtCustomerName.setText("" + customer.getName());
                txtCustomerEmail.setText("" + customer.getEmail());
                txtCustomerAddrStNo.setText("" + customer.getAddress().getStreetNo());
                txtCustomerAddrStName.setText("" + customer.getAddress().getStreetName());
                txtCustomerAddrSuburb.setText("" + customer.getAddress().getSuburb());
                txtCustomerAddrPostcode.setText("" + customer.getAddress().getPostcode());
            } catch (NullPointerException ex) {
                System.out.println("No customer selected");
            }

            if (customer instanceof PayingCustomer) {
                txtCustomerType.setText("Paying Customer");
            } else {
                txtCustomerType.setText("Associate Customer");
            }

            textEnrolled.setText("...loading supplements...");
            textEnrolled.setText(magazine.getSupplements(customer.getCustomerId()));

            if (customer instanceof AssociateCustomer) {
                textPayingCustomers.setText("...loading paying customers...");
                textPayingCustomers.setText(magazine.getPayingCustomerString(customer.getCustomerId()));
            }

            if (customer instanceof PayingCustomer) {
                rbPaying.setSelected(true);
            } else {
                rbNotPaying.setSelected(true);
            }

        }
    };

    EventHandler<ActionEvent> btnEditModeClicked = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            root.setLeft(createCustomerPaneVBox());
            root.setRight(editMagazineInfoPaneVBox());
        }
    };

    EventHandler<ActionEvent> btnViewModeClicked = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            root.setLeft(ViewOnlyPane());
            root.setRight(null);
        }
    };

    EventHandler<ActionEvent> btnCreateModeClicked = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            root.setLeft(createNewCustomerPaneVBox());
            root.setRight(createNewSupplementPaneVBox());

        }
    };

    public GridPane editMagazineInfoPaneVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        Text lblTitle = new Text("Edit Magazine Info");
        lblTitle.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblTitle, 0, 0);

        Text lblName = new Text("Name");
        lblName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblName, 0, 1);

        Text lblCost = new Text("Cost");
        lblCost.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCost, 0, 2);

        txtMagazineName = new TextField();
        grid.add(txtMagazineName, 1, 1);

        txtMagazineCost = new TextField();
        grid.add(txtMagazineCost, 1, 2);

        btnEditMag = new Button("Edit Magazine");
        btnEditMag.setOnAction(btnEditMagazineDetails);
        grid.add(btnEditMag, 1, 3);

        return grid;

    }

    EventHandler<ActionEvent> btnEditMagazineDetails = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            String name = txtMagazineName.getText();
            int cost = Integer.parseInt(txtMagazineCost.getText());

            magazine.getMagazine().setTitle(name);
            magazine.getMagazine().setWeeklyCost(cost);

            alert.setContentText("Magazine details updated");
            alert.setAlertType(AlertType.INFORMATION);
            alert.show();

        }
    };

    public GridPane createCustomerPaneVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 10, 10, 10));

        // column 0 labels
        Text lblCustomer = new Text("Customer");
        lblCustomer.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomer, 0, 0);

        Text lblCustomerType = new Text("Customer Type:");
        lblCustomerType.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomerType, 0, 1);

        Text lblCustomerName = new Text("Customer Name:");
        lblCustomerName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomerName, 0, 2);

        Text lblEmail = new Text("Email:");
        lblEmail.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEmail, 0, 3);

        Text lblAddrStNo = new Text("Address Street No");
        lblAddrStNo.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrStNo, 0, 4);

        Text lblAddrStName = new Text("Address Street Name");
        lblAddrStName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrStName, 0, 5);

        Text lblAddrSuburb = new Text("Address Suburb");
        lblAddrSuburb.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrSuburb, 0, 6);

        Text lblAddrPostcode = new Text("Address Postcode");
        lblAddrPostcode.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrPostcode, 0, 7);

        Text lblEnrolledUnits = new Text("Enrolled in:");
        lblEnrolledUnits.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEnrolledUnits, 0, 8);

        // column 1 controls
        combo_box = new ComboBox();
        combo_box.setItems(FXCollections.observableArrayList(magazine.getCustomersNames()));
        combo_box.setOnAction(comboSelected);

        grid.add(combo_box, 1, 0);

        txtCustomerType = new TextField();
        grid.add(txtCustomerType, 1, 1);

        txtCustomerName = new TextField();
        grid.add(txtCustomerName, 1, 2);

        txtCustomerEmail = new TextField();
        grid.add(txtCustomerEmail, 1, 3);

        txtCustomerAddrStNo = new TextField();
        grid.add(txtCustomerAddrStNo, 1, 4);

        txtCustomerAddrStName = new TextField();
        grid.add(txtCustomerAddrStName, 1, 5);

        txtCustomerAddrSuburb = new TextField();
        grid.add(txtCustomerAddrSuburb, 1, 6);

        txtCustomerAddrPostcode = new TextField();
        grid.add(txtCustomerAddrPostcode, 1, 7);

        textEnrolled = new TextArea();
        grid.add(textEnrolled, 1, 8);

        grid.add(createButtonGroupVBox(), 1, 10);

        return grid;
    }

    public GridPane createButtonGroupVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

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
        grid.setPadding(new Insets(10, 10, 10, 10));

        // get current working directory
        File recordsDir = new File(System.getProperty("user.dir"));

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

                customerPane = chooseModePane();

                lblHeading.setText(magazine.getMagazine().getTitle());

                root.setTop(headingPane(lblHeading));
                root.setLeft(null);
                root.setRight(null);
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
        grid.setPadding(new Insets(10, 10, 10, 10));

        Text lblType = new Text("Type:");
        lblType.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblType, 0, 1);

        final ToggleGroup customerType = new ToggleGroup();

        rbPaying = new RadioButton("Paying Customer");
        rbPaying.setToggleGroup(customerType);
        rbPaying.setUserData("Paying Customer");
        rbPaying.setSelected(true);

        rbNotPaying = new RadioButton("Associate Customer");
        rbNotPaying.setUserData("Associate Customer");
        rbNotPaying.setToggleGroup(customerType);

        grid.add(rbPaying, 1, 1);
        grid.add(rbNotPaying, 2, 1);

        // add event handler to radio buttons

        // column 0 labels
        Text lblCustomer = new Text("New Customer");
        lblCustomer.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblCustomer, 0, 2);

        Text lblCustomerName = new Text("Name:");
        lblCustomerName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblCustomerName, 0, 3);

        Text lblAge = new Text("Email");
        lblAge.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAge, 0, 5);

        Text lblAddrStNo = new Text("Address Street No");
        lblAddrStNo.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrStNo, 0, 7);

        Text lblAddrStName = new Text("Address Street Name");
        lblAddrStName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrStName, 0, 9);

        Text lblAddrSuburb = new Text("Address Suburb");
        lblAddrSuburb.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrSuburb, 0, 11);

        Text lblAddrPostcode = new Text("Address Postcode");
        lblAddrPostcode.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblAddrPostcode, 0, 13);

        Text lblEnrolledUnits = new Text("Enrolled in:");
        lblEnrolledUnits.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblEnrolledUnits, 0, 15);

        Text lblPayingCuss = new Text("Paying Customers:");
        lblPayingCuss.setFont(Font.font("Arial", FontWeight.NORMAL, 18));

        txtNewCustomerName = new TextField();
        grid.add(txtNewCustomerName, 0, 4);

        txtNewCustomerEmail = new TextField();
        grid.add(txtNewCustomerEmail, 0, 6);

        txtNewCustomerAddrStNo = new TextField();
        grid.add(txtNewCustomerAddrStNo, 0, 8);

        txtNewCustomerAddrStName = new TextField();
        grid.add(txtNewCustomerAddrStName, 0, 10);

        txtNewCustomerAddrSuburb = new TextField();
        grid.add(txtNewCustomerAddrSuburb, 0, 12);

        txtNewCustomerAddrPostcode = new TextField();
        grid.add(txtNewCustomerAddrPostcode, 0, 14);

        MagSupplements = new ListView<String>();
        MagSupplements.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        MagSupplements.setItems(FXCollections.observableArrayList(magazine.getSupplementsName()));

        grid.add(MagSupplements, 0, 16);

        rbNotPaying.setOnAction(e -> {
            if (rbNotPaying.isSelected()) {

                MagPayingCustomers = new ListView<String>();
                MagPayingCustomers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                MagPayingCustomers.setItems(FXCollections.observableArrayList(magazine.getPayingCusName()));

                grid.add(lblPayingCuss, 0, 18);

                grid.add(MagPayingCustomers, 1, 18);

            }
        });

        rbPaying.setOnAction(e -> {
            if (rbPaying.isSelected()) {
                // remove lbPayingCuss and MagPayingCustomers
                grid.getChildren().remove(MagPayingCustomers);
                grid.getChildren().remove(lblPayingCuss);

            }
        });

        btnCreateNewCustomer = new Button("Create");
        btnCreateNewCustomer.setOnAction(btnNewCustomerCreate);
        grid.add(btnCreateNewCustomer, 0, 20);
        return grid;
    }

    public GridPane createNewSupplementPaneVBox() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(10, 800, 10, 30));

        // column 0 labels
        Text lblSuppText = new Text("New Supplement");
        lblSuppText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblSuppText, 0, 0);

        Text lblSuppName = new Text("Name:");
        lblSuppName.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblSuppName, 0, 1);

        Text lblSuppCost = new Text(" Weekly Cost:");
        lblSuppCost.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(lblSuppCost, 0, 3);

        txtSuppName = new TextField();
        grid.add(txtSuppName, 0, 2);
        txtSuppCost = new TextField();
        grid.add(txtSuppCost, 0, 4);

        btnCreateNewSupplement = new Button("Create");

        btnCreateNewSupplement.setOnAction(btnNewSupplementCreate);
        grid.add(btnCreateNewSupplement, 0, 5);

        // grid.add(subGrid, 0, 7);
        return grid;
    }

    public GridPane chooseModePane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 0));

        // column 0 labels
        Text lblMode = new Text("Choose Mode:");
        lblMode.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblMode, 0, 0);

        btnView = new Button("View");
        btnView.setOnAction(btnViewModeClicked);
        grid.add(btnView, 1, 0);

        btnCreate = new Button("Create");
        btnCreate.setOnAction(btnCreateModeClicked);
        grid.add(btnCreate, 2, 0);

        btnEdit = new Button("Edit");
        btnEdit.setOnAction(btnEditModeClicked);
        grid.add(btnEdit, 3, 0);

        return grid;
    }

    public void viewSuppDetails(String s) {
        // get supplement details
        Supplement supp = magazine.getSupplement(s);

        // display supplement details
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle("Supplement Details");
        alert.setHeaderText("Supplement Details");
        alert.setContentText("Supplement Name: " + supp.getName() + "\n" + "Supplement Cost: " + supp.getCost() + "\n");
        alert.showAndWait();
    }

    public void viewCustomerDetails(String s) {
        // get customer details
        Customer cus = magazine.getCustomer(s);

        // display customer details
        alert.setAlertType(AlertType.INFORMATION);
        alert.setTitle("Customer Details");
        alert.setHeaderText("Customer Details");

        if (cus instanceof PayingCustomer) {
            alert.setContentText("Customer Type: Paying Customer\n" + "Customer Name: " + cus.getName() + "\n"
                    + "Customer Email: " + cus.getEmail() + "\n"
                    + "\nCustomer Address: \n" + cus.getAddress().printAddress() + "\n\n"
                    + "Customer Supplements in: \n"
                    + magazine.getSupplements(cus.getCustomerId()) + "\n"
                    + "\n" + "Customers' Associate Customers: "
                    + magazine.getAssociateCustomerByPayingCustomer(cus.getCustomerId()) + "\n"
                    + "Customers'billing information: \n" + magazine.getBillStrings(cus.getCustomerId()) + "\n");
        } else {

            AssociateCustomer newcus = (AssociateCustomer) cus;

            alert.setContentText("Customer Type: Associate Customer\n" + "Customer Name: " + cus.getName() + "\n"
                    + "Customer Email: " + cus.getEmail() + "\n"
                    + "\nCustomer Address: \n" + cus.getAddress() + "\n\n" + "Customer Supplements in: "
                    + magazine.getSupplements(cus.getCustomerId()) + "\n"
                    + "\n" + "Customers' Paying Customer: "
                    + newcus.getPayingCustomer() + "\n"
                    + "Customers'billing information: \n" + magazine.getBillStrings(cus.getCustomerId()) + "\n");

        }

        alert.showAndWait();

    }

    public GridPane ViewOnlyPane() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        Text lblSupp = new Text("List of Supplements");
        lblSupp.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblSupp, 0, 2);

        // display list of supplements in the magazine
        int count = 3;
        for (String s : magazine.getSupplementsName()) {
            Text t = new Text(s);

            t.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            grid.add(t, 0, count);

            Button btn = new Button("View");
            btn.setOnAction(e -> {
                viewSuppDetails(s);
            });

            grid.add(btn, 1, count);
            count++;
        }

        Text lblCusList = new Text("List of Customers");
        lblCusList.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblCusList, 0, count);

        count++;

        // display list of customers in the magazine
        for (String c : magazine.getCustomersNames()) {
            Text t = new Text(c);

            t.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
            grid.add(t, 0, count);

            Button btn = new Button("View");
            btn.setOnAction(e -> {
                viewCustomerDetails(c);
            });

            grid.add(btn, 1, count);

            count++;
        }

        return grid;

    }

    public GridPane headingPane(Text lblHeading) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));

        // lblHeading = new Text("Welcome to the Vouge Magazine");
        // lblHeading.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        grid.add(lblHeading, 0, 0);

        grid.add(chooseModePane(), 0, 1);

        return grid;
    }

    @Override
    public void start(Stage primaryStage) {

        root = new BorderPane();
        customerPane = createCustomerPaneVBox();
        GridPane openSavePane = createOpenSaveGridPane(primaryStage, root);

        lblHeading = new Text("Magazine Service Information System");
        lblHeading.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        magazine.getMagazine().setTitle("Vouge");

        root.setTop(headingPane(lblHeading));
        root.setBottom(openSavePane);

        scene = new Scene(root, 1200, 900);

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
