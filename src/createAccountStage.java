import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;

class createAccountStage {
    createAccountStage(ArrayList<Account> accounts, Stage loginStage) {
        //User's name
        GridPane nameHolder = new GridPane();
        nameHolder.setMaxHeight(5);
        nameHolder.setPadding(new Insets(0, 0, 10, 0));
        TextField firstName = new TextField();
        TextField lastName = new TextField();
        Label fName = new Label("First Name: ");
        Label lName = new Label("Last Name: ");
        fName.setAlignment(Pos.CENTER_RIGHT);
        fName.setMinSize(75, 25);
        fName.setMaxSize(75, 25);
        lName.setAlignment(Pos.CENTER_RIGHT);
        lName.setMinSize(75, 25);
        lName.setMaxSize(75, 25);
        firstName.setMaxSize(162.5, 25);
        firstName.setMinSize(162.5, 25);
        lastName.setMaxSize(162.5, 25);
        lastName.setMinSize(162.5, 25);

        nameHolder.add(fName, 0, 0);
        nameHolder.add(firstName, 1, 0);
        nameHolder.add(lName, 2, 0);
        nameHolder.add(lastName, 3, 0);


        //GridPane for rest of info
        GridPane userHolder = new GridPane();
        userHolder.setVgap(10);
        userHolder.setPadding(new Insets(0, 0, 0, -55));

        //User's username
        Label userLabel = new Label("Username: ");
        TextField username = new TextField();
        userLabel.setAlignment(Pos.CENTER_RIGHT);
        userLabel.setMinSize(130, 25);
        userLabel.setMaxSize(130, 25);
        username.setMaxSize(400, 25);
        username.setMinSize(400, 25);

        userHolder.add(userLabel, 0, 0);
        userHolder.add(username, 1, 0);

        //User's password
        Label passLabel = new Label("Password: ");
        PasswordField password = new PasswordField();
        passLabel.setAlignment(Pos.CENTER_RIGHT);
        passLabel.setMinSize(130, 25);
        passLabel.setMaxSize(130, 25);
        password.setMaxSize(400, 25);
        password.setMinSize(400, 25);

        userHolder.add(passLabel, 0, 1);
        userHolder.add(password, 1, 1);

        //Confirm User's password
        Label passConfLabel = new Label("Confirm Password: ");
        PasswordField passConfirm = new PasswordField();
        passConfLabel.setAlignment(Pos.CENTER_RIGHT);
        passConfLabel.setMinSize(130, 25);
        passConfLabel.setMaxSize(130, 25);
        passConfirm.setMaxSize(400, 25);
        passConfirm.setMinSize(400, 25);

        userHolder.add(passConfLabel, 0, 2);
        userHolder.add(passConfirm, 1, 2);

        //User's balance
        Label balanceLabel = new Label("Balance: ");
        TextField balance = new TextField();
        balanceLabel.setAlignment(Pos.CENTER_RIGHT);
        balanceLabel.setMinSize(130, 25);
        balanceLabel.setMaxSize(130, 25);
        balance.setMaxSize(400, 25);
        balance.setMinSize(400, 25);

        userHolder.add(balanceLabel, 0, 3);
        userHolder.add(balance, 1, 3);

        //Create account button
        Button createAccount = new Button("Create Account");
        createAccount.setMaxSize(400, 25);
        createAccount.setMinSize(400, 25);

        VBox buttonHolder = new VBox();
        buttonHolder.getChildren().add(createAccount);
        buttonHolder.setPadding(new Insets(10, 0, 0, 75));

        //Box holding everything
        VBox accountInfo = new VBox();
        accountInfo.setAlignment(Pos.TOP_CENTER);
        accountInfo.setPadding(new Insets(40, 40, 40, 40));
        Label creationTitle = new Label();
        creationTitle.setGraphic(new Label(" Create Account "));
        creationTitle.getGraphic().setStyle("-fx-background-color: #f4f4f4;");
        creationTitle.setPadding(new Insets(-80, 0, 0, -250));
        accountInfo.getChildren().add(creationTitle);
        accountInfo.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 5;"
                + "-fx-border-color: darkgray;");
        accountInfo.getChildren().addAll(nameHolder, userHolder, buttonHolder);

        //Box holding the box holding everything because spacing
        VBox outerVBox = new VBox();
        outerVBox.getChildren().add(accountInfo);
        outerVBox.setPadding(new Insets(75, 75, 0, 75));

        // Stage and Scene creation
        Stage creation = new Stage();
        creation.setTitle("MidFake Bank - Create a new Account");
        creation.setMinHeight(500);
        creation.setMaxHeight(500);
        creation.setMinWidth(750);
        creation.setMaxWidth(750);
        Scene scene = new Scene(outerVBox, 500, 750);
        creation.setScene(scene);
        creation.show();

        try {
            FileInputStream logo = new FileInputStream("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\Logo.PNG");
            Image logoImage = new Image(logo);
            creation.getIcons().add(logoImage);
        }
        catch (Exception f) {

        }
        createAccount.setOnAction(new EventHandler<ActionEvent>() {

            Label warning = new Label();
            //System.out.println(passConfirm.getText() + passConfLabel.getText() + passConfirm.getText().equals(passConfLabel.getText()));
            @Override
            public void handle(ActionEvent e) {
                warning.setTextFill(Color.RED);
                if (!buttonHolder.getChildren().contains(warning)) {
                    buttonHolder.getChildren().add(warning);
                }
                if (firstName.getText().equals("") || lastName.getText().equals("") || username.getText().equals("") || password.getText().equals("") || passConfirm.getText().equals("") || balance.getText().equals("")) {
                    warning.setText("One of the above fields is blank");
                } else if (!(password.getText().equals(passConfirm.getText()))) {
                    warning.setText("Your passwords do not match. Please input matching passwords");
                } else {
                    try {
                        if (Double.parseDouble(balance.getText()) < 0) {
                            warning.setText("Please input a balance of at least 0");
                        }
                        else {
                            accounts.add(new Account(firstName.getText() + " " + lastName.getText(), Double.parseDouble(balance.getText()), new LoginInfo(username.getText(), password.getText())));
                            creation.close();
                        }
                    }
                    catch(Exception error) {
                        warning.setText("Please make sure your balance is a decimal number");
                    }

                }
            }
        });
    }
} //Complete
