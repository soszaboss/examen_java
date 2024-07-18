package com.example.project_examen;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class CovoiturageController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Hyperlink do_not_have_account_link;

    @FXML
    private Button registerBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    @FXML
    private TextField email;

    //    DATABASE TOOLS
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    // Variables pour la position de la fenêtre
    private double x = 0;
    private double y = 0;

    public void loginUsers() {
        String sql = "SELECT * FROM users WHERE username = ?";
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

        connect = MyJdbc.conn();

        try {
            Alert alert;

            assert connect != null;
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username.getText());

            result = prepare.executeQuery();

            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                if (result.next()) {
                    String storedPassword = result.getString("password");
                    boolean validPassword = encoder.matches(password.getText(), storedPassword);

                    if (validPassword) {
                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Successfully Login!");
                        alert.showAndWait();

                        // Cacher le formulaire de connexion
                        loginBtn.getScene().getWindow().hide();
                        // Charger le tableau de bord
                        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));

                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        root.setOnMousePressed((MouseEvent event) -> {
                            x = event.getSceneX();
                            y = event.getSceneY();
                        });

                        root.setOnMouseDragged((MouseEvent event) -> {
                            stage.setX(event.getScreenX() - x);
                            stage.setY(event.getScreenY() - y);
                        });

                        stage.initStyle(StageStyle.TRANSPARENT);
                        stage.setScene(scene);
                        stage.show();
                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Incorrect Password!");
                        alert.showAndWait();
                    }
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Wrong Username/Password");
                    alert.showAndWait();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        System.exit(0);
    }

    public void Do_not_have_account_link() throws IOException {
        // Cacher le formulaire de connexion
        loginBtn.getScene().getWindow().hide();
        // Charger le tableau de bord
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("register.fxml")));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        root.setOnMousePressed((MouseEvent event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });

        root.setOnMouseDragged((MouseEvent event) -> {
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
        });

        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    public void register() {
        String sqlInsert = "INSERT INTO users(username, email, password, role) VALUES (?, ?, ?, ?)";
        String sqlCheck = "SELECT COUNT(*) FROM users WHERE username = ? OR email = ?";
        Argon2PasswordEncoder encoder = new Argon2PasswordEncoder(32, 64, 1, 15 * 1024, 2);

        connect = MyJdbc.conn();

        try {
            Alert alert;

            if (connect == null) {
                throw new SQLException("Failed to connect to the database.");
            }

            // Vérifier si l'utilisateur existe déjà
            PreparedStatement checkStmt = connect.prepareStatement(sqlCheck);
            checkStmt.setString(1, username.getText());
            checkStmt.setString(2, email.getText());
            ResultSet checkResult = checkStmt.executeQuery();
            if (checkResult.next() && checkResult.getInt(1) > 0) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Username/Email already used.");
                alert.showAndWait();
                return;
            }

            // Insérer le nouvel utilisateur
            PreparedStatement insertStmt = connect.prepareStatement(sqlInsert);
            insertStmt.setString(1, username.getText());
            insertStmt.setString(2, email.getText());
            insertStmt.setString(3, encoder.encode(password.getText()));
            insertStmt.setInt(4, 3); // Role par défaut

            if (username.getText().isEmpty() || password.getText().isEmpty() || email.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                int result = insertStmt.executeUpdate();
                if (result > 0) {
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Registered!");
                    alert.showAndWait();

                    // Cacher le formulaire de connexion
                    registerBtn.getScene().getWindow().hide();

                    // Charger le tableau de bord
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                } else {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to register user.");
                    alert.showAndWait();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while interacting with the database.");
            alert.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
