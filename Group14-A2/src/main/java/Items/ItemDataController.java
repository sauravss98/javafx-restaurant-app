package Items;

import User.Customer;
import User.Manager;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;

import java.io.*;
import java.util.ArrayList;

public final class ItemDataController {
    private static int itemIDCounter=0;
    private static ArrayList<Item> items = new ArrayList<>();
    public static ArrayList<Item> getItems() {
        return items;
    }
    public static void addItems(Item item){
        items.add(item);
    }

    public static int getItemIDCounter() {
        return itemIDCounter;
    }

    public static void setItemIDCounter(int itemid) {
        itemIDCounter = itemid;
    }

    public static Item getItemById(int itemId) {
        for (Item item : items) {
            if (item.getItemID() == itemId) {
                return item;
            }
        }
        return null;
    }

    private static final String FILE_PATH = "src/main/java/Items/ItemData.xlsx";

    public static void editExcelSheetData(Item item) {
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
            sheet = workbook.createSheet("Sheet1");
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row != null) {
                int itemId = (int) row.getCell(0).getNumericCellValue();
                if (itemId == item.getItemID()) {
                    row.getCell(2).setCellValue(item.getPrice());
                    row.getCell(3).setCellValue(item.isSpecialItem());
                    break;
                }
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(FILE_PATH)) {
            workbook.write(outputStream);
            System.out.println("Items data saved to the Excel successfully.");
        } catch (IOException e) {
            System.err.println("Error saving items to Excel: " + e.getMessage());
        }
    }

//    public static void editExcelSheetData(Item item) {
//        try (FileInputStream inputStream = new FileInputStream("src/main/java/Items/ItemData.xlsx");
//             FileOutputStream outputStream = new FileOutputStream("src/main/java/Items/ItemData.xlsx")) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            Sheet sheet = workbook.getSheetAt(0);
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                if (row != null) {
//                    int itemId = (int) row.getCell(0).getNumericCellValue();
//                    if (itemId == item.getItemID()) {
//                        row.getCell(2).setCellValue(item.getPrice());
//                        row.getCell(3).setCellValue(item.isSpecialItem());
//                        break;
//                    }
//                }
//            }
//
//            workbook.write(outputStream); // Write the changes to the file
//            System.out.println("Items data saved to the Excel successfully.");
//        } catch (IOException e) {
//            System.err.println("Error loading or saving items from/to Excel: " + e.getMessage());
//        }
//    }

    public static void loadItemsFromExcel() {
        try (FileInputStream inputStream = new FileInputStream("src/main/java/Items/ItemData.xlsx")) {
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
            System.out.println("Items loaded from Excel successfully.");
        } catch (IOException e) {
            System.err.println("Error loading items from Excel: " + e.getMessage());
        }
    }

    public static void saveItemDataToExcel(Item item){
        Workbook workbook;
        Sheet sheet;

        File file = new File("src/main/java/Items/ItemData.xlsx");
        if (file.exists()) {
            try (FileInputStream inputStream = new FileInputStream(file)) {
                workbook = new XSSFWorkbook(inputStream);
                sheet = workbook.getSheetAt(0);
            } catch (IOException e) {
                System.err.println("Error opening existing Excel file: " + e.getMessage());
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
        try (FileOutputStream outputStream = new FileOutputStream("src/main/java/Items/ItemData.xlsx")) {
            workbook.write(outputStream);
            System.out.println("Item data saved to Excel file successfully.");
        } catch (IOException e) {
            System.err.println("Error saving item data to Excel file: " + e.getMessage());
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
            }
        }
    }
}
