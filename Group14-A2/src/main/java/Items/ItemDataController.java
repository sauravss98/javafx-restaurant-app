package Items;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to control  all the item data
 * @author Saurav
 */
public final class ItemDataController {
    private static final String FILE_PATH = "src/main/java/Items/ItemData.xlsx";
    private static final String EDIT = "edit";
    private static final String REMOVE = "remove";
    private static final String SPECIAL = "special";
    private static final String SHEET_NAME = "Sheet1";
    private static final String ITEM_SUCCESS_SAVE = "Items data saved to the Excel successfully.";
    private static final String ITEM_SUCCESS_LOAD = "Items loaded from the Excel successfully.";
    private static final String ERROR_SAVING_DATA = "Error saving item data to Excel file: ";
    private static final String ERROR_LOADING_DATA = "Error loading item data from Excel file: ";
    private static final String ERROR_OPENING = "Error opening Excel file: ";
    private static int itemIDCounter=0;
    private static final ArrayList<Item> items = new ArrayList<>();

    /**
     * Function to get the array list of items
     * @return array list of item
     * @author Saurav
     */
    public static ArrayList<Item> getItems() {
        return items;
    }

    /**
     * Function to add an item to the arraylist
     * @param item the instance of the item is sent
     * @author Saurav
     */
    public static void addItems(Item item){
        items.add(item);
    }

    /**
     * Function to get the number of item id created till then
     * @return the count  of the number of items in the arraylist
     * @author Saurav
     */
    public static int getItemIDCounter() {
        return itemIDCounter;
    }

    /**
     * Function to set the number of items in the arraylist after adding an element into the list
     * @param itemid the last item id used is sent
     * @author Saurav
     */
    public static void setItemIDCounter(int itemid) {
        itemIDCounter = itemid;
    }

    /**
     * Function to search and return the item
     * @param itemId the item id used to searched
     * @return the item searched and found is sent
     * @author Saurav
     */
    public static Item getItemById(int itemId) {
        for (Item item : items) {
            if (item.getItemID() == itemId) {
                return item;
            }
        }
        return null;
    }

    /**
     * Function to edit the data in the excel sheet containing item data
     * @param item the instance of the item required.
     * @param mode the mode at which the function has to operate. There are 2 modes, edit and remove
     * @author Saurav
     */
    public static void editExcelSheetData(Item item, String mode) {
        Workbook workbook;
        try {
            FileInputStream inputStream = new FileInputStream(FILE_PATH);
            workbook = new XSSFWorkbook(inputStream);
            inputStream.close();
        } catch (IOException e) {
                // File doesn't exist or is empty, create a new workbook
            workbook = new XSSFWorkbook();
        }

        Sheet sheet = workbook.getSheetAt(0);
        if (sheet == null) {
                // Workbook is empty, create a new sheet
            sheet = workbook.createSheet(SHEET_NAME);
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                int itemId = (int) row.getCell(0).getNumericCellValue();
                if (itemId == item.getItemID()) {
                    if(Objects.equals(mode, EDIT)) {
                        row.getCell(1).setCellValue(item.getItemName());
                        row.getCell(2).setCellValue(item.getPrice());
                        row.getCell(3).setCellValue(item.isSpecialItem());
                        break;
                    } else if (Objects.equals(mode, REMOVE)) {
                        row.getCell(4).setCellValue(item.isItemIsActive());
                        break;
                    } else if(Objects.equals(mode,SPECIAL)){
                        row.getCell(3).setCellValue(item.isSpecialItem());
                    }
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
            System.out.println(ITEM_SUCCESS_SAVE);
        } catch (IOException e) {
            System.err.println(ERROR_SAVING_DATA + e.getMessage());
        }
    }

    /**
     * Function to load item data from the excel sheet
     * @author Saurav
     */
    public static void loadItemsFromExcel() {
        try (FileInputStream inputStream = new FileInputStream(FILE_PATH)) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheetAt(0);

            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                if (row != null) {
                    int itemId = (int) row.getCell(0).getNumericCellValue();
                    String itemName = row.getCell(1).getStringCellValue();
                    int price = (int)row.getCell(2).getNumericCellValue();
                    boolean isSpecialItem = row.getCell(3).getBooleanCellValue();
                    boolean isActive = row.getCell(4).getBooleanCellValue();
                    Item item = new Item(itemId,itemName,price,isSpecialItem,isActive);
                    items.add(item);
                    itemIDCounter++;
                }
            }
            System.out.println(ITEM_SUCCESS_LOAD);
        } catch (IOException e) {
            System.err.println(ERROR_LOADING_DATA + e.getMessage());
        }
    }

    /**
     * Function to save the item data to the excel sheet
     * @param item the instance of the item to add into the sheet is sent
     * @author Saurav
     */
    public static void saveItemDataToExcel(Item item){
        Workbook workbook;
        Sheet sheet;

        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(inputStream);
                sheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                System.err.println(ERROR_OPENING + e.getMessage());
                return;
            }
        } else {
            workbook = new XSSFWorkbook();
            sheet = workbook.createSheet("Item Data");
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Item ID");
            headerRow.createCell(1).setCellValue("Item Name");
            headerRow.createCell(2).setCellValue("Price");
            headerRow.createCell(3).setCellValue("Special Item");
            headerRow.createCell(4).setCellValue("Is Active");
        }
        int rowNum = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(item.getItemID());
        row.createCell(1).setCellValue(item.getItemName());
        row.createCell(2).setCellValue(item.getPrice());
        row.createCell(3).setCellValue(item.isSpecialItem());
        row.createCell(4).setCellValue(item.isActive());

        for (int i = 0; i < 5; i++) {
            sheet.autoSizeColumn(i);
        }
        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
            System.out.println(ITEM_SUCCESS_SAVE);
        } catch (IOException e) {
            System.err.println(ERROR_SAVING_DATA + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
            }
        }
    }
}
