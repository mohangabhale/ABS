import com.wms.controller.MainController;

public class App {
    public static void main(String[] args) {
        MainController controller = new MainController();

        // Add a new item
        controller.addNewItem("Mobile", 400, "A2");

        // Show all items
        controller.showItems();
    }
}
