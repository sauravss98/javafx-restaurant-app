package User;

import Orders.CardController;
import Orders.DriverCardController;
import Orders.Order;
import Orders.OrderDataHandler;
import cafe94.group14a2.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class DriverMainPageController {
    private static Driver activeDriver;
    private ArrayList<Driver> drivers = UserController.getDrivers();
    private ArrayList<Order> orders = OrderDataHandler.getOrders();
    private ArrayList<Customer> customers = UserController.getCustomers();
    @FXML private HBox containerBox;
    @FXML private Button logoutButton;
    @FXML private Button logTimeChangeButton;

    public DriverMainPageController(){

    }
    public DriverMainPageController(String email){
        for (Driver driver: drivers){
            if(driver.getEmail().equals(email)){
                this.activeDriver = driver;
            }
        }
    }

    public void initialize(){
        logoutButton.setOnAction(e->{
            handleLogoutButton();
        });
        try {
            addCard();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        logTimeChangeButton.setOnAction(e->{
            try {
                handleTimeLogButton();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    private void addCard() throws IOException {
        try {
            for (Order order:orders) {
                if (Objects.equals(order.getOrderStatus(), "Food Prepared") && Objects.equals(order.getOrderType(), "takeout")) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/cafe94/group14a2/deliveryCard.fxml"));
                    VBox vbox = loader.load();
                    DriverCardController controller = loader.getController();
                    controller.setOrder(order);
                    controller.setCurrentDriver(activeDriver);
                    controller.setDriverMainPageController(this);
                    controller.initialize();
                    containerBox.getChildren().add(vbox);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshCardDisplay() {
        containerBox.getChildren().clear(); // Clear existing cards
        try {
            addCard(); // Add cards again
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleLogoutButton() {
        try {
            Main.setRoot("login");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleTimeLogButton() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cafe94/group14a2/driverTimeLogger.fxml"));
        Parent root = loader.load();
        DriverTimeLogController controller = loader.getController();
        controller.setCurrentDriver(activeDriver);

        Stage timeLoggerStage = new Stage();
        controller.setStage(timeLoggerStage);
        timeLoggerStage.setTitle("Edit Time Log");
        timeLoggerStage.setScene(new Scene(root, 600, 600));
        timeLoggerStage.initModality(Modality.APPLICATION_MODAL);
        timeLoggerStage.showAndWait();
    }
}
