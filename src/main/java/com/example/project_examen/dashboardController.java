package com.example.project_examen;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    private ComboBox<String> role_combo_box;

    @FXML
    private AnchorPane route;

    @FXML
    private Button routeWindow;

    @FXML
    private TextField route_search;

    @FXML
    private TableView<Routes> table_route;

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
    private Button userClearBtn;

    @FXML
    private Button userDeleteBtn;

    @FXML
    private Button userUpdateBtn;

    @FXML
    private Button userWindow;

    @FXML
    private TableView<Users> user_table;

    @FXML
    private TableColumn<Users, String> user_table_col_email;

    @FXML
    private TableColumn<Users, Integer> user_table_col_id;

    @FXML
    private TableColumn<Users, String> user_table_col_role;

    @FXML
    private TableColumn<Users, String> user_table_col_username;

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
    private TableView<Cars> voiture_table;

    @FXML
    private TableColumn<Cars, String> voiture_table_col_immatriculation;

    @FXML
    private TableColumn<Cars, String> voiture_table_col_marque;

    @FXML
    private TableColumn<Cars, String> voiture_table_col_modele;

    @FXML
    private ComboBox<?> voiture_trajet_combo_box;


    @FXML
    void carAdd(ActionEvent event) {
        String sql = "INSERT INTO voitures (immatriculation, marque, model) VALUES (?, ?, ?)";
        try (Connection connect = MyJdbc.conn();
             PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, immatriculation.getText());
            prepare.setString(2, marque.getText());
            prepare.setString(3, modele.getText());
            prepare.executeUpdate();
            showCarsListData();
            carClear(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void carClear(ActionEvent event) {
        immatriculation.clear();
        marque.clear();
        modele.clear();
    }

    @FXML
    void carDelete(ActionEvent event) {
        Cars selectedCar = voiture_table.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            String sql = "DELETE FROM voitures WHERE id = ?";
            try (Connection connect = MyJdbc.conn();
                 PreparedStatement prepare = connect.prepareStatement(sql)) {
                prepare.setInt(1, selectedCar.getId());
                prepare.executeUpdate();
                showCarsListData();
                carClear(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void carUpdate(ActionEvent event) {
        Cars selectedCar = voiture_table.getSelectionModel().getSelectedItem();
        if (selectedCar != null) {
            String sql = "UPDATE voitures SET immatriculation = ?, marque = ?, modele = ? WHERE id = ?";
            try (Connection connect = MyJdbc.conn();
                 PreparedStatement prepare = connect.prepareStatement(sql)) {
                prepare.setString(1, immatriculation.getText());
                prepare.setString(2, marque.getText());
                prepare.setString(3, modele.getText());
                prepare.setInt(4, selectedCar.getId());
                prepare.executeUpdate();
                showCarsListData();
                carClear(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void close(ActionEvent event) {

    }

    @FXML
    void clearUser(ActionEvent event) {
        username_textfield.clear();
        email_textfield.clear();
        role_combo_box.getSelectionModel().clearSelection();
    }

    @FXML
    void updateUser(ActionEvent event) {
        String selectedRole = role_combo_box.getSelectionModel().getSelectedItem() != null ? role_combo_box.getSelectionModel().getSelectedItem().toString() : null;
        int role_id;
        if ("admin".equals(selectedRole)) {
            role_id = 1;
        } else if ("user".equals(selectedRole)) {
            role_id = 2;
        } else {
            role_id = 3;
        }
        if (selectedRole == null) {
            // Gestion d'erreur si le rôle n'est pas sélectionné
            System.out.println("Aucun rôle sélectionné.");
            return;
        }

        String sql = "UPDATE users SET email = ?, username = ?, role = ? WHERE id = ?";
        try (Connection connect = MyJdbc.conn();
             PreparedStatement prepare = connect.prepareStatement(sql)) {
            prepare.setString(1, email_textfield.getText());
            prepare.setString(2, username_textfield.getText());
            prepare.setInt(3, role_id);
            prepare.setInt(4, user_table.getSelectionModel().getSelectedItem().getId());
            prepare.executeUpdate();
            showUsersListData();
            clearUser(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void deleteUser(ActionEvent event) {
        Integer userId = user_table.getSelectionModel().getSelectedItem().getId();

        String deleteReservationsSql = "DELETE FROM reservations WHERE client = ?";
        String deleteUserSql = "DELETE FROM users WHERE id = ?";

        try (Connection connect = MyJdbc.conn();
             PreparedStatement deleteReservationsStmt = connect.prepareStatement(deleteReservationsSql);
             PreparedStatement deleteUserStmt = connect.prepareStatement(deleteUserSql)) {

            // Supprimer les réservations associées
            deleteReservationsStmt.setInt(1, userId);
            deleteReservationsStmt.executeUpdate();

            // Supprimer l'utilisateur
            deleteUserStmt.setInt(1, userId);
            deleteUserStmt.executeUpdate();

            showUsersListData();
            clearUser(event);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    void voiture(ActionEvent event) {

    }


    private double x = 0;
    private double y = 0;

    public ObservableList<Users> getUsersList(){
        ObservableList<Users> listUsers = FXCollections.observableArrayList();

        String sql = "SELECT id, username, email, " +
                "CASE " +
                "    WHEN role = 1 THEN 'admin' " +
                "    WHEN role = 2 THEN 'conducteur' " +
                "    ELSE 'passager' " +
                "END AS role " +
                "FROM users";


        Connection connect = MyJdbc.conn();

        try {
            Users user;
            assert connect != null;
            PreparedStatement prepare = connect.prepareStatement(sql);
            ResultSet result = prepare.executeQuery();

            while (result.next()) {
                user = new Users(
                        result.getInt("id"),
                        result.getString("email"),
                        result.getString("username"),
                        result.getString("role")
                );

                listUsers.add(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUsers;
    }

    private ObservableList<Users> showUserData;

    public void showUsersListData() {
        showUserData = getUsersList();

        user_table_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_table_col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        user_table_col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        user_table_col_role.setCellValueFactory(new PropertyValueFactory<>("role"));

        user_table.setItems(showUserData);
    }
    public void addUsersSelect() {

        Users user = user_table.getSelectionModel().getSelectedItem();
        int num = user_table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        username_textfield.setText(user.getUsername());
        email_textfield.setText(user.getEmail());
        role_combo_box.setValue(role_combo_box.getSelectionModel().getSelectedItem());


    }
    public void addCarssSelect() {

        Cars car = voiture_table.getSelectionModel().getSelectedItem();
        int num = voiture_table.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        marque.setText(car.getMarque());
        modele.setText(car.getModele());
        immatriculation.setText(car.getImmatriculation());


    }

    private void loadRoles() {
        ObservableList<String> roles = FXCollections.observableArrayList();
        roles.add("admin");
        roles.add("user");
        roles.add("passager");

        role_combo_box.setItems(roles);
    }
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

        String sql = "SELECT date_reservation, COUNT(id) FROM reservations WHERE statut = ? GROUP BY date_reservation ORDER BY TIMESTAMP(date_reservation) ASC LIMIT 5";

        try (Connection connect = MyJdbc.conn(); // Assurez-vous que MyJdbc.conn() renvoie une connexion valide
             PreparedStatement prepare = connect.prepareStatement(sql)) {

            prepare.setInt(1, 2); // Valeur du statut
            ResultSet result = prepare.executeQuery();

            XYChart.Series series = new XYChart.Series<>();
            series.setName("Reservations");

            while (result.next()) {
                String date = result.getString("date_reservation");
                int count = result.getInt("COUNT(id)");
                series.getData().add(new XYChart.Data<>(date, count));
            }

            home_totalEnrolledChart.getData().add(series);

        } catch (SQLException e) {
            e.printStackTrace(); // Loguer ou gérer l'exception comme nécessaire
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

    public void switchFormEvent(ActionEvent event) {
        if (event.getSource() == home_btn) {
            homeDisplayTotalReservationChart();
            homeDisplayTotalDrivers();
            homeDisplayTotalCars();
            homeDisplayTotalRoute();
            home_form.setVisible(true);
            users.setVisible(false);
            voiture.setVisible(false);
            route.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            userWindow.setStyle("-fx-background-color:transparent");
            carrWindow.setStyle("-fx-background-color:transparent");
            routeWindow.setStyle("-fx-background-color:transparent");

            /*
            homeDisplayTotalEnrolledStudents();
            homeDisplayMaleEnrolled();
            homeDisplayFemaleEnrolled();
            homeDisplayEnrolledMaleChart();
            homeDisplayFemaleEnrolledChart();
            homeDisplayTotalEnrolledChart();*/

        } else if (event.getSource() == userWindow) {
            showUsersListData();
            loadRoles();
            home_form.setVisible(false);
            users.setVisible(true);
            voiture.setVisible(false);
            route.setVisible(false);

            userWindow.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            home_btn.setStyle("-fx-background-color:transparent");
            carrWindow.setStyle("-fx-background-color:transparent");
            route.setStyle("-fx-background-color:transparent");

            /*
//            TO BECOME UPDATED ONCE YOU CLICK THE ADD STUDENTS BUTTON ON NAV
            addStudentsShowListData();
            addStudentsYearList();
            addStudentsGenderList();
            addStudentsStatusList();
            addStudentsCourseList();
            addStudentsSearch();*/

        } else if (event.getSource() == carrWindow) {
            showCarsListData();
            home_form.setVisible(false);
            users.setVisible(false);
            voiture.setVisible(true);
            route.setVisible(false);

            carrWindow.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            userWindow.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            routeWindow.setStyle("-fx-background-color:transparent");

            /*
            availableCourseShowListData();*/

        } else if (event.getSource() == routeWindow) {
            showRoutesListData();
            home_form.setVisible(false);
            users.setVisible(false);
            voiture.setVisible(false);
            route.setVisible(true);

            routeWindow.setStyle("-fx-background-color:linear-gradient(to bottom right, #3f82ae, #26bf7d);");
            userWindow.setStyle("-fx-background-color:transparent");
            home_btn.setStyle("-fx-background-color:transparent");
            carrWindow.setStyle("-fx-background-color:transparent");
            /*
            studentGradesShowListData();
            studentGradesSearch();*/

        }
    }
    public ObservableList<Routes> getRoutesList() {
        ObservableList<Routes> listRoutes = FXCollections.observableArrayList();
        String sql = "SELECT * FROM trajets";

        try (Connection connect = MyJdbc.conn();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {
            while (result.next()) {
                Routes route = new Routes(
                        result.getInt("id"),
                        result.getString("ville_depart"),
                        result.getString("ville_arrivee"),
                        result.getString("conducteur"),
                        result.getString("voiture"),
                        result.getString("date_depart"),
                        result.getString("date_arrivee")
                );
                listRoutes.add(route);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listRoutes;
    }

    public void showRoutesListData() {
        ObservableList<Routes> showRouteData = getRoutesList();

        table_route_col_ville_depart.setCellValueFactory(new PropertyValueFactory<>("villeDepart"));
        table_route_col_ville_arrivee.setCellValueFactory(new PropertyValueFactory<>("villeArrivee"));
        table_route_col_conducteur.setCellValueFactory(new PropertyValueFactory<>("conducteur"));
        table_route_col_car.setCellValueFactory(new PropertyValueFactory<>("voiture"));
        table_route_col_date_depart.setCellValueFactory(new PropertyValueFactory<>("dateDepart"));
        table_route_col_date_arrivee.setCellValueFactory(new PropertyValueFactory<>("dateArrivee"));

        table_route.setItems(showRouteData);
    }
    public ObservableList<Cars> getCarsList() {
        ObservableList<Cars> listCars = FXCollections.observableArrayList();
        String sql = "SELECT * FROM voitures";

        try (Connection connect = MyJdbc.conn();
             PreparedStatement prepare = connect.prepareStatement(sql);
             ResultSet result = prepare.executeQuery()) {
            while (result.next()) {
                Cars car = new Cars(
                        result.getInt("id"),
                        result.getString("immatriculation"),
                        result.getString("marque"),
                        result.getString("model")
                );
                listCars.add(car);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listCars;
    }

    public void showCarsListData() {
        ObservableList<Cars> showCarData = getCarsList();

        voiture_table_col_immatriculation.setCellValueFactory(new PropertyValueFactory<>("immatriculation"));
        voiture_table_col_marque.setCellValueFactory(new PropertyValueFactory<>("marque"));
        voiture_table_col_modele.setCellValueFactory(new PropertyValueFactory<>("modele"));

        voiture_table.setItems(showCarData);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeDisplayTotalReservationChart();
        showUsersListData();
        showRoutesListData();
        showCarsListData();
        loadRoles();
        homeDisplayTotalDrivers();
        homeDisplayTotalCars();
        homeDisplayTotalRoute();
    }
}

