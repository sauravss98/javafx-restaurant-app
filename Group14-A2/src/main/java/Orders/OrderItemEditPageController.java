package Orders;

import Items.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.SpinnerValueFactory;

public class OrderItemEditPageController {
    @FXML private Text quantityNameText;
    @FXML private Spinner quantitySpinner;
    @FXML private Button removeItemButton;
    @FXML private Button confirmItemButton;
    @FXML private Button cancelButton;
    private Order order;
    private Item item;
    private Stage stage;
    private OrderItem currentOrderItem;

    public void initialize(){
        refreshItemText();
        refreshQuantitySpinner();
        removeItemButton.setOnAction(e -> {
            handleRemoveButton();
        });
        confirmItemButton.setOnAction(e -> {
            handleConfirmButton();
        });
        cancelButton.setOnAction(e -> {
            handleCancelButton();
        });
    }

    private void refreshQuantitySpinner() {
        if (currentOrderItem != null) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, currentOrderItem.getQuantity());
            quantitySpinner.setValueFactory(valueFactory);
        } else {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
            quantitySpinner.setValueFactory(valueFactory);
        }
    }

    private void refreshItemText(){
        if (item != null) {
            quantityNameText.setText("Item: "+item.getItemName());
        } else {
            // Handle the case where order is null
            quantityNameText.setText("Item : N/A");
        }
    }

    private void handleCancelButton(){
        if (stage != null) {
            stage.close();
        }
    }


    private void handleConfirmButton() {
        int quantity = (int) quantitySpinner.getValue();
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getItem().getItemID() == item.getItemID()) {
                orderItem.setQuantity(quantity);
                break;
            }
        }
        if (stage != null) {
            stage.close();
        }
    }

    private void handleRemoveButton() {
        order.removeItem(item.getItemID());
        if (stage != null) {
            stage.close();
        }
    }

    public OrderItemEditPageController(){}

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void setCurrentItem(OrderItem orderItem) {
        this.currentOrderItem = orderItem;
        this.item = orderItem.getItem();
        System.out.println("Item " + item.getItemName());
        initialize();
    }

    public void setCurrentOrder(Order order){
        this.order = order;
    }
}