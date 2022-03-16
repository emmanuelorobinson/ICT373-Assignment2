/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
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
    
    @Override
    public void start(Stage primaryStage) {
        /* Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        }); */

        //add nav bar for different mode

        Button btnManageSupp, btnAssCus, btnPayCus, btnUpdateCus;
        CheckBox chkCusType;
        ComboBox selCus;
        TextField txtCusName, txtCusAddress, txtCusPhone, txtCusEmail, txtCusType;
        
        BorderPane root = new BorderPane();
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public GridPane createCustomerPaneBox() {
       /*  GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        
        TextField txtCusName = new TextField();
        txtCusName.setPromptText("Customer Name");
        gridPane.add(txtCusName, 0, 0);
        
        TextField txtCusAddress = new TextField();
        txtCusAddress.setPromptText("Customer Address");
        gridPane.add(txtCusAddress, 0, 1);
        
        TextField txtCusPhone = new TextField();
        txtCusPhone.setPromptText("Customer Phone");
        gridPane.add(txtCusPhone, 0, 2);
        
        TextField txtCusEmail = new TextField();
        txtCusEmail.setPromptText("Customer Email");
        gridPane.add(txtCusEmail, 0, 3);
        
        TextField txtCusType = new TextField();
        txtCusType.setPromptText("Customer Type");
        gridPane.add(txtCusType, 0, 4);
        
        Button btnAddCus = new Button("Add Customer");
        gridPane.add(btnAddCus, 0, 5); */
        
        //return gridPane;

        GridPane grid = new GridPane();

        Label lblCusName = new Label("Customer Name");
        grid.add(lblCusName, 0, 0);

        Label lblCusAddress = new Label("Customer Address");
        grid.add(lblCusAddress, 0, 1);

        Label lblEmail = new Label("Customer Email");
        grid.add(lblEmail, 0, 2);

        Label lblSupplements = new Label("Customer Supplements");
        grid.add(lblSupplements, 0, 3);

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
        /* Magazine mag = new Magazine("The Daily Express", 10.0f);
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

        MangazineService ms = new MangazineService(mag, sub);

        try {
        File kd = new File("kd.dat");

        FileOutputStream fos = new FileOutputStream(kd);
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        writeSerializedObject(oos, ms);

        FileInputStream fis = new FileInputStream(kd);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Magazine newMag = new Magazine();
        Subscription newSub = new Subscription();

        MangazineService newMagService = readSerializedObject(ois);
        newMag = newMagService.getMagazine();
        newSub = newMagService.getSubscription();

        System.out.println(newMag.getTitle());
        }
        catch (Exception e) {
            System.out.println("Error: " + e);
        } */
    }
    
    public static void writeSerializedObject(ObjectOutputStream oos, MangazineService mag) {
        try {
            oos.writeObject(mag);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public static MangazineService readSerializedObject(ObjectInputStream ois) {
        MangazineService mag = null;
        try {
            mag = (MangazineService) ois.readObject();
            ois.close();
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return mag;
    }
}
