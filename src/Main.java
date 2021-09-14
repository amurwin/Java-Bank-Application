import java.util.*;
import java.io.*;
import java.lang.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;


public class Main extends Application {
    ArrayList<Account> accounts = new ArrayList<>();
    @Override
    public void start(Stage loginStage) throws Exception {

        //Load in accounts
        accounts = FileRead();

        //GridPane for login boxes
        GridPane layout = new GridPane();
        layout.setVgap(10);

        //User's username
        Label userLabel = new Label("Username: ");
        TextField loginUser = new TextField();
        userLabel.setAlignment(Pos.CENTER_RIGHT);
        userLabel.setMinSize(75, 25);
        userLabel.setMaxSize(75, 25);
        loginUser.setMaxSize(400, 25);
        loginUser.setMinSize(400, 25);

        layout.add(userLabel, 0, 0);
        layout.add(loginUser, 1, 0);

        //User's password
        Label passLabel = new Label("Password: ");
        PasswordField loginPass = new PasswordField();
        passLabel.setAlignment(Pos.CENTER_RIGHT);
        passLabel.setMinSize(75, 25);
        passLabel.setMaxSize(75, 25);
        loginPass.setMaxSize(400, 25);
        loginPass.setMinSize(400, 25);

        layout.add(passLabel, 0, 1);
        layout.add(loginPass, 1, 1);

        //Login Button
        Button loginButton = new Button("Login");
        loginButton.setMaxSize(400, 25);
        loginButton.setMinSize(400, 25);

        layout.add(loginButton, 1, 2);

        //Create account
        Button createAccount = new Button("Create Account");
        createAccount.setMaxSize(400, 25);
        createAccount.setMinSize(400, 25);

        layout.add(createAccount, 1, 10);


        //Box holding everything
        VBox login = new VBox();
        login.setAlignment(Pos.TOP_CENTER);
        login.setPadding(new Insets(40, 40, 40, 40));
        Label loginLabel = new Label();
        loginLabel.setGraphic(new Label(" Login "));
        loginLabel.getGraphic().setStyle("-fx-background-color: #f4f4f4;");
        loginLabel.setPadding(new Insets(-80, 0, 0, -250));
        login.setStyle("-fx-border-style: solid;"
                + "-fx-border-width: 1;"
                + "-fx-border-insets: 5;"
                + "-fx-border-color: darkgray;");


        login.getChildren().addAll(loginLabel, layout);

        //Box holding the box holding everything because spacing
        VBox outerVBox = new VBox();
        outerVBox.getChildren().add(login);
        outerVBox.setPadding(new Insets(75, 75, 0, 75));

        loginButton.setOnAction(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                // do authentication here, if successful show the next window
                for (int i = 0; i < accounts.size(); i++) {
                    if (accounts.get(i).getLogin().getUsername().equals(loginUser.getText()) && accounts.get(i).getLogin().getPassword().equals(loginPass.getText())) {
                        System.out.println("success");
                        new primaryStage(loginStage, accounts.get(i), accounts);
                        loginUser.setText("");
                        loginPass.setText("");
                        loginStage.hide();
                        break;
                    }
                }
                System.out.println("failed");


            }
        }); // Logs in

        createAccount.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                new createAccountStage(accounts, loginStage);
                loginStage.hide();
            }
        }); //Go to create account scene

        try {
            FileInputStream logo = new FileInputStream("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\Logo.PNG");
            Image logoImage = new Image(logo);
            loginStage.getIcons().add(logoImage);
        }
        catch (Exception f) {

        }

        loginStage.setScene(new Scene(outerVBox, 750, 500));
        loginStage.setTitle("MidFake Bank - Login");
        loginStage.setMinHeight(500);
        loginStage.setMaxHeight(500);
        loginStage.setMinWidth(750);
        loginStage.setMaxWidth(750);
        loginStage.show();
    } // Read in file

    @Override
    public void stop() {
        FileWrite(accounts);
    } // Save file

    public static void main(String args[]){



        //accounts.add(new Account("Bryce", 1524.25, new LoginInfo("bbooze", "anotherPassword")));
        //FileWrite(accounts);
        launch(args);

    } // yep

    public static ArrayList<Account> FileRead() {
        ArrayList<Account> allAccounts = new ArrayList<Account>();
        // Construct the Scanner and PrintWriter objects for reading and writing
        try {
            File inputFile = new File("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\Accounts.txt");
            Scanner in = new Scanner(inputFile);
            String line = "";

            // Read the input and write the output

            double total = 0;

            while (in.hasNextLine()) {
                line = in.nextLine();
                String[] tempAccount = line.split(", ");
                //System.out.println(line);
                allAccounts.add(new Account(tempAccount[2], Double.parseDouble(tempAccount[3]), new LoginInfo(tempAccount[0], tempAccount[1])));
            }
            in.close();
            // Now fullData = file
        } catch (Exception e) {
            System.out.println("No input file found");
        }

        return allAccounts;
    } //Reads in the file

    public static void FileWrite(ArrayList<Account> accounts) {
        try (FileWriter writer = new FileWriter("C:\\Users\\drewr\\IdeaProjects\\BankHonorsContract\\src\\Accounts.txt", false)) {
            for (int i = 0; i < accounts.size(); i++) {
                writer.write(accounts.get(i).getLogin().getUsername() + ", " + accounts.get(i).getLogin().getPassword() + ", " + accounts.get(i).getName() + ", " + accounts.get(i).getBalance() + "\n");
            }
            //writer.write();

        }
        catch(IOException e){
            System.out.println("Unable to create/edit file");
        }
    } //Saves the file


} //Complete