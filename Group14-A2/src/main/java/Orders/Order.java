package Orders;

import Items.Item;

import java.util.ArrayList;

public class Order {
    private int orderId;
    private String orderType;
    private ArrayList<Integer> items = new ArrayList<>();
    private ArrayList<Item> itemsObjects = new ArrayList<>();
    private boolean isCompleted = false;

    public void setItems(ArrayList<Integer> items) {
        this.items = items;
    }

    public ArrayList<Item> getItemsObjects() {
        return itemsObjects;
    }

    public void setItemsObjects(ArrayList<Item> itemsObjects) {
        this.itemsObjects = itemsObjects;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public ArrayList<Integer> getItems() {
        return items;
    }

    public void setItems(int itemId, Item item) {
        items.add(itemId);
        if (itemsObjects.contains(item)) {
            int index = itemsObjects.indexOf(item);
            itemsObjects.get(index).addQuantity();
        } else {
            itemsObjects.add(item);
        }
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Order(int orderId, String orderType, ArrayList<Integer> items, boolean isCompleted) {
        this.orderId = orderId;
        this.orderType = orderType;
        this.items = items;
        this.isCompleted = isCompleted;
    }

//    @Override
//    public String toString() {
//        return "Order{" +
//                "orderId=" + orderId +
//                ", orderType='" + orderType + '\'' +
//                ", items=" + items +
//                ", items=" + itemsObjects +
//                ", isCompleted=" + isCompleted +
//                '}';
//    }
@Override
public String toString() {
    StringBuilder sb = new StringBuilder("Order{");
    sb.append("orderId=").append(orderId).append(", ");
    sb.append("orderType='").append(orderType).append("', ");
    sb.append("items=").append(items).append(", ");
    sb.append("itemsObjects=[");
    for (Item item : itemsObjects) {
        sb.append("{").append(item.getItemName()).append(", Quantity=").append(item.getQuantity()).append("}, ");
    }
    sb.delete(sb.length() - 2, sb.length()); // Remove the trailing ", "
    sb.append("], ");
    sb.append("isCompleted=").append(isCompleted);
    sb.append("}");
    return sb.toString();
}
}