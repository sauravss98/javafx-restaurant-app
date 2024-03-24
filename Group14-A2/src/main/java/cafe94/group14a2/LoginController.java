package cafe94.group14a2;

import User.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class LoginController {
    public String getActiveUser() {
        return activeUser;
    }

    public void setActiveUser(String activeUser) {
        this.activeUser = activeUser;
    }

//    private CustomerPageController customerPageController = new CustomerPageController();
    @FXML private TextField EmailTextField;
    @FXML private Label LoginErrorLabel;
    private String activeUser;


    public String checkValidEmail(String email){
        String type = "";
        ArrayList<Customer> customers = UserController.getCustomers();
        ArrayList<Manager> managers = UserController.getManagers();
        ArrayList<Waiter> waiters = UserController.getWaiters();
        ArrayList<Driver> drivers = UserController.getDrivers();
        ArrayList<Chef> chefs = UserController.getChefs();
        for(Customer customer:customers){
            if(Objects.equals(customer.getEmail(), email)) {
                return "Customer";
            }
        }
        for(Manager manager:managers){
            if(Objects.equals(manager.getEmail(), email)) {
                return "Manager";
            }
        }
        for(Waiter waiter:waiters){
            if(Objects.equals(waiter.getEmail(), email)) {
                return "Waiter";
            }
        }
        for(Driver driver:drivers){
            if(Objects.equals(driver.getEmail(), email)) {
                return "Driver";
            }
        }
        for(Chef chef:chefs){
            if(Objects.equals(chef.getEmail(), email)) {
                return "Chef";
            }
        }
        return type;
    }

    @FXML
    protected void onLoginClick() throws IOException {
        String email = EmailTextField.getText();
        if(!email.isEmpty()){
            String userType = "";
            userType = checkValidEmail(email);
            System.out.println(userType);
            if (userType.equals("Customer")){
                System.out.println("in Customer");
                try {
                    setActiveUser(email);
                    CustomerPageController customerPageController = new CustomerPageController(email);
                    Login.setRoot("customerMainPage");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                LoginErrorLabel.setText("Not a user. Please try again or create new user");
            }

        }
    }

    @FXML
    protected void onSignUpClick() throws IOException {
        Login.setRoot("createUser");
    }
}