package com.example.project_examen;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class dashboardController implements Initializable {

    @FXML
    private Button carAddBtn;

    @FXML
    private Button carClearBtn;

    @FXML
    private Button carDeleteBtn;

    @FXML
    private Button carUpdateBtn;

    @FXML
    private Button carrWindow;

    @FXML
    private Button close;

    @FXML
    private ComboBox<?> conducteur_trajet_comobobox;

    @FXML
    private DatePicker dateArrivee;

    @FXML
    private DatePicker dateDepart;

    @FXML
    private AnchorPane date_depart;

    @FXML
    private TextField email_textfield;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_routes;

    @FXML
    private BarChart<?, ?> home_totalEnrolledChart;

    @FXML
    private Label home_total_car;

    @FXML
    private Label home_total_drivers;

    @FXML
    private TextField immatriculation;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private TextField marque;

    @FXML
    private Button minimize;

    @FXML
    private TextField modele;

    @FXML
    private ComboBox<?> role_combo_box;

    @FXML
    private AnchorPane route;

    @FXML
    private Button routeWindow;

    @FXML
    private TextField route_search;

    @FXML
    private TableView<?> table_route;

    @FXML
    private TableColumn<?, ?> table_route_col_car;

    @FXML
    private TableColumn<?, ?> table_route_col_conducteur;

    @FXML
    private TableColumn<?, ?> table_route_col_date_arrivee;

    @FXML
    private TableColumn<?, ?> table_route_col_date_depart;

    @FXML
    private TableColumn<?, ?> table_route_col_ville_arrivee;

    @FXML
    private TableColumn<?, ?> table_route_col_ville_depart;

    @FXML
    private Button trajetAddBtn;

    @FXML
    private Button trajetClearBtn;

    @FXML
    private Button trajetDeleteBtn;

    @FXML
    private Button trajetUpdateBtn;

    @FXML
    private Button userAddBtn;

    @FXML
    private Button userClearBtn;

    @FXML
    private Button userDeleteBtn;

    @FXML
    private Button userUpdateBtn;

    @FXML
    private Button userWindow;

    @FXML
    private TableView<?> user_table;

    @FXML
    private TableColumn<?, ?> user_table_col_email;

    @FXML
    private TableColumn<?, ?> user_table_col_id;

    @FXML
    private TableColumn<?, ?> user_table_col_role;

    @FXML
    private TableColumn<?, ?> user_table_col_username;

    @FXML
    private TextField username_textfield;

    @FXML
    private AnchorPane users;

    @FXML
    private TextField users_bar_search;

    @FXML
    private Label ville_arrivee;

    @FXML
    private Label ville_depart;

    @FXML
    private AnchorPane voiture;

    @FXML
    private TableView<?> voiture_table;

    @FXML
    private TableColumn<?, ?> voiture_table_col_immatriculation;

    @FXML
    private TableColumn<?, ?> voiture_table_col_marque;

    @FXML
    private TableColumn<?, ?> voiture_table_col_modele;

    @FXML
    private ComboBox<?> voiture_trajet_combo_box;

    @FXML
    void addStudentsSearch(KeyEvent event) {

    }

    @FXML
    void addStudentsSelect(MouseEvent event) {

    }

    @FXML
    void addUser(ActionEvent event) {

    }

    @FXML
    void availableCourseSelect(MouseEvent event) {

    }

    @FXML
    void carAdd(ActionEvent event) {

    }

    @FXML
    void carClear(ActionEvent event) {

    }

    @FXML
    void carDelete(ActionEvent event) {

    }

    @FXML
    void carUpdate(ActionEvent event) {

    }

    @FXML
    void clearUser(ActionEvent event) {

    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void deleteUser(ActionEvent event) {

    }

    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event) {

    }

    @FXML
    void studentGradesSearch(KeyEvent event) {

    }

    @FXML
    void studentGradesSelect(MouseEvent event) {

    }

    @FXML
    void switchForm(ActionEvent event) {

    }

    @FXML
    void trajetAdd(ActionEvent event) {

    }

    @FXML
    void trajetClear(ActionEvent event) {

    }

    @FXML
    void trajetDelete(ActionEvent event) {

    }

    @FXML
    void trajetUpdate(ActionEvent event) {

    }

    @FXML
    void updateUser(ActionEvent event) {

    }

    @FXML
    void voiture(ActionEvent event) {

    }




    private double x = 0;
    private double y = 0;

    public void logoutUser() {

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {

                //HIDE YOUR DASHBOARD FORM
                logout.getScene().getWindow().hide();

                //LINK YOUR LOGIN FORM 
                Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // THATS IT FOR THESE VIDEOS, THANKS FOR WATCHING!! SUBSCRIBE AND TURN ON NOTIFICATION 
//    TO NOTIF YOU FOR MORE UPCOMING VIDEOS THANKS FOR THE SUPPORT! : )
    public void defaultNav(){
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
    }


    public void homeDisplayTotalDrivers() {
        String sql = "SELECT COUNT(id) FROM users WHERE role = ?";
        Connection connect = MyJdbc.conn();

        try {
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, 2); // Assuming 2 is the role ID for drivers
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                int countDrivers = result.getInt(1);
                home_total_drivers.setText(String.valueOf(countDrivers));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTotalCars() {
        String sql = "SELECT COUNT(id) FROM voitures";
        Connection connect = MyJdbc.conn();

        try {
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                int countCars = result.getInt(1);
                home_total_car.setText(String.valueOf(countCars));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTotalRoute() {
        String sql = "SELECT COUNT(id) FROM trajets";
        Connection connect = MyJdbc.conn();

        try {
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            if (result.next()) {
                int countRoutes = result.getInt(1);
                home_routes.setText(String.valueOf(countRoutes));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void homeDisplayTotalReservationChart() {
        home_totalEnrolledChart.getData().clear();

        String sql = "SELECT date_reservation, COUNT(id) FROM reservations WHERE status = ? GROUP BY date_reservation ORDER BY TIMESTAMP(date_reservation) ASC LIMIT 5";
        Connection connect = MyJdbc.conn(); // Assurez-vous que MyJdbc.conn() renvoie une connexion valide

        try {
            XYChart.Series series = new XYChart.Series<>();

            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            prepare.setInt(1, 2);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                String date = result.getString(1);
                int countEnrolled = result.getInt(2);
                series.getData().add(new XYChart.Data<>(date, countEnrolled));
            }

            home_totalEnrolledChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void close_dashboard() {
        System.exit(0);
    }

    public void minimize_dashboard() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}

