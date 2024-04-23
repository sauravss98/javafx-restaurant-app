package Items;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

/**
 * Class for controlling the item edit page
 */
public class ItemEditPageController {
    @FXML private Label itemNameText;
    @FXML private Spinner priceSpinner;
    @FXML private Button removeItemButton;
    @FXML private Button confirmItemButton;
    @FXML private Button cancelButton;
    @FXML private ToggleButton specialButton;

    private Item currentItem;
    private Stage stage;

    /**
     * Constructor: default
     */
    public ItemEditPageController(){}

    /**
     * Function to set the current item in the instance of the class
     * @param item instance of item is sent
     */
     public void setCurrentItem(Item item){
        this.currentItem=item;
        initialize();
     }

    /**
     * Function to set the stage(window) in the current class instance
     * @param stage the stage instance is passed
     */
    public void setStage(Stage stage){
        this.stage=stage;
    }

    /**
     * Function to initialize the UI elements
     */
    public void initialize(){
        displayItemName();
        refreshPriceSpinner();
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

    /**
     * Function to display the price spinner
     */
    private void refreshPriceSpinner() {
        if (currentItem != null) {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, currentItem.getPrice());
            priceSpinner.setValueFactory(valueFactory);
        } else {
            SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, 1);
            priceSpinner.setValueFactory(valueFactory);
        }
    }

    /**
     * Function to handle the cancel click and close the window
     */
    private void handleCancelButton() {
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Function to handle the confirm button and save the data changes made
     */
    private void handleConfirmButton() {
        int price = (int) priceSpinner.getValue();
        boolean toggleButtonInput = specialButton.isSelected();
        System.out.println("status "+toggleButtonInput);
        currentItem.setPrice(price);
        currentItem.setSpecialItem(toggleButtonInput);
        ItemDataController.editExcelSheetData(currentItem,"edit");
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Function to handle the remove button and remove the item form
     */
    private void handleRemoveButton() {
        currentItem.setItemIsActive(false);
        ItemDataController.editExcelSheetData(currentItem,"remove");
        if (stage != null) {
            stage.close();
        }
    }

    /**
     * Function to display the item name ui content
     */
    private void displayItemName() {
        if(currentItem!=null) {
            itemNameText.setText("Item Name : "+currentItem.getItemName());
        } else{
            itemNameText.setText("Empty");
        }
    }
}
