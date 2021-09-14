import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;

public class primaryStage {

    public static boolean isNumeric(String string) {
        try {
            double d = Double.parseDouble(string);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    primaryStage(Stage loginStage, Account user, ArrayList<Account> accounts) {

        Tab welcome = new Tab("Welcome");

        //GridPane welcome tab
        GridPane welcomeLayout = new GridPane();
        welcomeLayout.setAlignment(Pos.CENTER);
        welcomeLayout.setPadding(new Insets(0, 0, 220, 0));
        Label welcomeLabel = new Label("Welcome to");
        welcomeLabel.setFont(new Font(55));

        try {
            FileInputStream i = new FileInputStream("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\transparentMidFake.PNG");
            Image image = new Image(i);
            ImageView iv1 = new ImageView();
            iv1.setImage(image);
            iv1.setFitHeight(350);
            iv1.setFitWidth(350);
            welcomeLayout.add(iv1, 1, 0);
        }
        catch (Exception f) {

        }
        //add to layout
        welcomeLayout.add(welcomeLabel, 0, 0);
        welcome.setContent(welcomeLayout);

        //Money Changing Tab
        Tab account = new Tab("Deposit/Withdraw");
        Label action = new Label("What would you like to do? ");
        action.setFont(new Font(16));
        ComboBox selection = new ComboBox();
        selection.getItems().add("Deposit");
        selection.getItems().add("Withdraw");
        selection.getItems().add("Transfer");
        HBox selectionHolder = new HBox();
        selectionHolder.getChildren().addAll(action, selection);

        Button submit = new Button("Submit");
        Label error = new Label("");
        error.setTextFill(Color.RED);

        Label finalBalance = new Label("");
        finalBalance.setFont(new Font(16));

        Label label = new Label();
        label.setFont(new Font(16));
        Label label2 = new Label("Who would you like to transfer to? ");
        label2.setFont(new Font(16));
        TextArea amount = new TextArea();
        amount.setMinSize(150, 25);
        amount.setMaxSize(150, 25);
        ComboBox allUsers = new ComboBox();
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber() != user.getAccountNumber()) {
                allUsers.getItems().add(accounts.get(i).getName());
            }
        }

        HBox input = new HBox();
        input.getChildren().addAll(label, amount);
        input.setVisible(false);
        HBox recipient = new HBox();
        recipient.getChildren().addAll(label2, allUsers);
        recipient.setVisible(false);


        selection.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue ov, String old_val, String new_val) {
                input.setVisible(true);
                if (new_val.equals("Deposit")) {
                    label.setText("How much would you like to deposit? ");
                    recipient.setVisible(false);
                }
                else if (new_val.equals("Withdraw")) {
                    label.setText("How much would you like to withdraw? ");
                    recipient.setVisible(false);
                }
                else {
                    label.setText("How much would you like to transfer? ");
                    recipient.setVisible(true);
                }

            }
        });


        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    if (selection.getSelectionModel().getSelectedItem().toString().equals("Deposit")) {
                        if (isNumeric(amount.getText())) {
                            if (Double.parseDouble(amount.getText()) > 0) {
                                user.deposit(Double.parseDouble(amount.getText()));
                                error.setText("");
                                finalBalance.setText("Your current balance is $" + user.getBalance());
                                finalBalance.setVisible(true);
                            } else {
                                error.setText("Please input a number greater than 0");
                            }
                        } else error.setText("Please input a number greater than 0");
                    } else if (selection.getSelectionModel().getSelectedItem().toString().equals("Withdraw")) {
                        if (isNumeric(amount.getText())) {
                            if (Double.parseDouble(amount.getText()) > user.getBalance()) {
                                error.setText("Please enter a number less than your current account balance.");
                                finalBalance.setText("Your current balance is $" + user.getBalance());
                                finalBalance.setVisible(true);
                            } else if (Double.parseDouble(amount.getText()) <= 0) {
                                error.setText("Please input a number greater than 0");
                            } else {
                                user.withdraw(Double.parseDouble(amount.getText()));
                                error.setText("");
                                finalBalance.setText("Your current balance is $" + user.getBalance());
                                finalBalance.setVisible(true);
                            }
                        } else error.setText("Please input a number greater than 0");
                    } else {
                        try {
                            error.setText("Please select a recipient");
                            if (isNumeric(amount.getText())) {
                                if (Double.parseDouble(amount.getText()) > user.getBalance()) {
                                    error.setText("Please enter a number less than your current account balance.");
                                    finalBalance.setText("Your current balance is $" + user.getBalance());
                                    finalBalance.setVisible(true);
                                } else if (Double.parseDouble(amount.getText()) <= 0) {
                                    error.setText("Please input a number greater than 0");
                                } else {
                                    for (int i = 0; i < accounts.size(); i++) {
                                        if (accounts.get(i).getName().equals(allUsers.getSelectionModel().getSelectedItem().toString())) {
                                            Account selectedAccount = accounts.get(i);
                                            user.transfer(selectedAccount, Double.parseDouble(amount.getText()));
                                            error.setText("");
                                            finalBalance.setText("Your current balance is $" + user.getBalance());
                                            finalBalance.setVisible(true);
                                            break;
                                        }
                                    }

                                }
                            }
                        } catch (Exception f) {
                            error.setText("Please select a recipient");
                        }
                    }
                }catch (Exception f) {
                    error.setText("Please select what you want to do");
                }
            }
        });

        VBox accountLayout = new VBox();
        accountLayout.setAlignment(Pos.TOP_CENTER);
        accountLayout.setPadding(new Insets(100, 100, 100, 100));
        accountLayout.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 50;"
                + "-fx-border-color: darkgray;");


        accountLayout.getChildren().addAll(selectionHolder, input, recipient, submit, error, finalBalance);
        account.setContent(accountLayout);



        //Profile Tab
        Tab profile = new Tab("Profile");
        Label name = new Label(user.getName());
        Label nameLabel = new Label("Name: ");
        Label balance = new Label("$" + user.getBalance());
        Label balanceLabel = new Label("Balance: ");
        Label accountNumber = new Label("" + user.getAccountNumber());
        Label accountNumberLabel = new Label("Account Number: ");
        name.setFont(new Font(15));
        nameLabel.setFont(new Font(15));
        balance.setFont(new Font(15));
        balanceLabel.setFont(new Font(15));
        accountNumber.setFont(new Font(15));
        accountNumberLabel.setFont(new Font(15));
        GridPane infoHolder = new GridPane();
        infoHolder.add(name, 1, 0);
        infoHolder.add(nameLabel, 0, 0);
        infoHolder.add(balance, 1, 1);
        infoHolder.add(balanceLabel, 0, 1);
        infoHolder.add(accountNumber, 1, 2);
        infoHolder.add(accountNumberLabel, 0, 2);

        VBox profileLayout = new VBox();
        profileLayout.setAlignment(Pos.CENTER);
        profileLayout.setPadding(new Insets(30, 30, 30, 30));
        profileLayout.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 50;"
                + "-fx-border-color: lightgray;");
        profileLayout.setMaxSize(400, 200);
        profileLayout.setMinSize(400, 200);
        profileLayout.getChildren().add(infoHolder);

        //Because spacing
        VBox profileLayoutHolder = new VBox();
        profileLayoutHolder.setPadding(new Insets(100, 0, 0, 0));
        profileLayoutHolder.getChildren().add(profileLayout);

        Button logoutButton = new Button("Log out");
        logoutButton.setMinHeight(50);
        logoutButton.setMinWidth(750);

        VBox logout = new VBox();
        logout.getChildren().add(logoutButton);
        logout.setPadding(new Insets(0, 0, 0, -30));

        BorderPane profileHolder = new BorderPane();
        profileHolder.setPadding(new Insets(20, 0, 0, 20));
        profileHolder.setCenter(profileLayoutHolder);
        profileHolder.setBottom(logout);

        try {
            FileInputStream i = new FileInputStream("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\BlankProfile.PNG");
            Image image = new Image(i);
            ImageView iv1 = new ImageView();
            iv1.setImage(image);
            iv1.setFitHeight(150);
            iv1.setFitWidth(150);
            profileHolder.setLeft(iv1);
        }
        catch (Exception f) {

        }

        profile.setContent(profileHolder);

        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().add(welcome);
        tabPane.getTabs().add(account);
        tabPane.getTabs().add(profile);



        Stage loggedInStage = new Stage();
        try {
            FileInputStream logo = new FileInputStream("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\Logo.PNG");
            Image logoImage = new Image(logo);
            loginStage.getIcons().add(logoImage);
        }
        catch (Exception f) {

        }

        logoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                loggedInStage.hide();
                loginStage.show();
            }
        });

        loggedInStage.setTitle("MidFake Bank - Authenticated");
        loggedInStage.setMinHeight(500);
        loggedInStage.setMaxHeight(500);
        loggedInStage.setMinWidth(750);
        loggedInStage.setMaxWidth(750);
        Scene scene = new Scene(tabPane, 500, 750);
        loggedInStage.setScene(scene);
        loggedInStage.show();
    }
} //Complete
