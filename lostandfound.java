package code;

import java.io.*;
import java.util.*;

// ============================
// Item Class
// ============================
class Item implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
    private String description;
    private String date;
    private String location;
    private String status; // Lost or Found

    public Item(int id, String description, String date, String location, String status) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.location = location;
        this.status = status;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
    public String getDate() { return date; }
    public String getLocation() { return location; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return "ID: " + id + ", Description: " + description +
               ", Date: " + date + ", Location: " + location +
               ", Status: " + status;
    }
}

// ============================
// File Utility Class
// ============================
class FileUtil {
    private static final String FILE_NAME = "items.txt";

    // Save items to file
    public static void saveItems(List<Item> items) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(items);
        } catch (IOException e) {
            System.err.println("Error saving items: " + e.getMessage());
        }
    }

    // Load items from file
    @SuppressWarnings("unchecked")
    public static List<Item> loadItems() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Item>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>(); // return empty list if file not found
        }
    }
}

// ============================
// Lost & Found Service Class
// ============================
class LostFoundService {
    private List<Item> items;
    private int idCounter = 1;

    public LostFoundService() {
        items = FileUtil.loadItems();
        for (Item item : items) {
            idCounter = Math.max(idCounter, item.getId() + 1);
        }
    }

    // Add new item
    public void addItem(String desc, String date, String loc, String status) {
        try {
            if (desc.isEmpty() || date.isEmpty() || loc.isEmpty()) {
                throw new IllegalArgumentException("All fields are required.");
            }
            if (!status.equalsIgnoreCase("Lost") && !status.equalsIgnoreCase("Found")) {
                throw new IllegalArgumentException("Status must be Lost or Found.");
            }

            Item newItem = new Item(idCounter++, desc, date, loc, status);
            items.add(newItem);
            FileUtil.saveItems(items);
            System.out.println("✅ Item added successfully: " + newItem);

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Search by keyword, date, or location
    public void searchItems(String keyword) {
        keyword = keyword.toLowerCase();
        boolean found = false;

        for (Item item : items) {
            if (item.getDescription().toLowerCase().contains(keyword) ||
                item.getDate().equalsIgnoreCase(keyword) ||
                item.getLocation().toLowerCase().contains(keyword)) {
                System.out.println(item);
                found = true;
            }
        }

        if (!found) {
            System.out.println("❌ No items found matching: " + keyword);
        }
    }

    // Claim item by ID and verify description
    public void claimItem(int id, String desc) {
        Item target = null;
        for (Item item : items) {
            if (item.getId() == id) {
                target = item;
                break;
            }
        }

        if (target == null) {
            System.out.println("❌ Item not found with ID: " + id);
            return;
        }

        if (target.getDescription().equalsIgnoreCase(desc)) {
            items.remove(target);
            FileUtil.saveItems(items);
            System.out.println("✅ Item claimed successfully!");
        } else {
            System.out.println("❌ Description mismatch. Claim failed.");
        }
    }

    // View all items
    public void viewAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items recorded yet.");
            return;
        }
        for (Item item : items) {
            System.out.println(item);
        }
    }
}

// ============================
// Main Class
// ============================
public class lost {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LostFoundService service = new LostFoundService();
        int choice = 0;

        while (true) {
            try {
                System.out.println("\n===== Lost & Found Menu =====");
                System.out.println("1. Add Item");
                System.out.println("2. Search Item");
                System.out.println("3. Claim Item");
                System.out.println("4. View All Items");
                System.out.println("5. Exit");
                System.out.print("Enter choice: ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter Date (DD-MM-YYYY): ");
                        String date = sc.nextLine();
                        System.out.print("Enter Location: ");
                        String loc = sc.nextLine();
                        System.out.print("Enter Status (Lost/Found): ");
                        String status = sc.nextLine();
                        service.addItem(desc, date, loc, status);
                    }

                    case 2 -> {
                        System.out.print("Enter keyword/date/location to search: ");
                        service.searchItems(sc.nextLine());
                    }

                    case 3 -> {
                        System.out.print("Enter Item ID: ");
                        int id = Integer.parseInt(sc.nextLine());
                        System.out.print("Enter Description to verify: ");
                        service.claimItem(id, sc.nextLine());
                    }

                    case 4 -> service.viewAllItems();

                    case 5 -> {
                        System.out.println("Exiting and saving data...");
                        sc.close();
                        return;
                    }

                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}

