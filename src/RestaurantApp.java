import java.io.Serializable;
import java.util.Scanner;

public class RestaurantApp {
	private ListInterface<Ingredient> inventory;
	private ListInterface<MenuItem> menu;
	private InventoryDAO inventoryDAO;
	private MenuDAO menuDAO;
	private Scanner scanner;

	public RestaurantApp() {
		inventory = new ArrayList<>();
		menu = new ArrayList<>();
		inventoryDAO = new InventoryDAO();
		menuDAO = new MenuDAO();
		inventory = inventoryDAO.retrieve();
		menu = menuDAO.retrieve();
		this.scanner = new Scanner(System.in);
	}

	//Basic Operations:
	//CREATE:
	//- Add new ingredients to inventory
	public void addNewIngredient() {
		String name;
		int initialStock;

		System.out.println("\nAdd New Ingredient");
		System.out.println("------------------");
		System.out.print("Enter name: ");
		name = scanner.nextLine();
		System.out.print("\nEnter initial stock: ");
		initialStock = scanner.nextInt();
		scanner.nextLine();

		Ingredient newIngredient = new Ingredient();
		newIngredient.setName(name);
		newIngredient.setCurrentStock(initialStock);

		inventory.add(newIngredient);
		inventoryDAO.save(inventory);
		System.out.print("\n\nAdded new ingredient: ");
		System.out.print(newIngredient.getName());
		System.out.print(", with initial quantity of ");
		System.out.println(newIngredient.getCurrentStock() + ".\n");

	}
	//- Create new menu items with their recipes
	public void addNewMenuItem() {
		String name;
		double price;
		ListInterface<Ingredient> recipe = new ArrayList<>();
		int choice = 0;

		System.out.println("\nAdd New Menu Item");
		System.out.println("-----------------");
		System.out.print("Enter name: ");
		name = scanner.nextLine();
		do {
			System.out.println();
			displayInventory();
			System.out.println("Choose ingredients to add to recipe. Enter 0 to stop.");
			System.out.print("Enter Your Choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println();

			if (choice != 0) {
				recipe.add(inventory.getEntry(choice));
				System.out.println("Recipe for " + name + ":");
				for (int i = 1; i <= recipe.getNumberOfEntries(); i++) {
					Ingredient ingredient = recipe.getEntry(i);
					System.out.println(i + ". " + ingredient.getName());
				}
				System.out.println();
			}
		} while (choice != 0);
		System.out.print("Enter price: ");
		price = scanner.nextDouble();
		scanner.nextLine();

		MenuItem newMenuItem = new MenuItem();
		newMenuItem.setName(name);
		newMenuItem.setRecipe(recipe);
		newMenuItem.setPrice(price);

		menu.add(newMenuItem);
		menuDAO.save(menu);

		System.out.print("\n\nAdded new menu item: ");
		System.out.print(newMenuItem.getName());
		System.out.print(", priced at RM");
		System.out.print(newMenuItem.getPrice());
		System.out.println(", with recipe:");
		for (int i = 1; i <= recipe.getNumberOfEntries(); i++) {
			Ingredient ingredient = recipe.getEntry(i);
			System.out.println(i + ". " + ingredient.getName());
		}
		System.out.println();
	}
	//READ:
	//- List all ingredients
	public void displayInventory() {
		System.out.println("Inventory:");
		for (int i = 1; i <= inventory.getNumberOfEntries(); i++) {
			Ingredient ingredient = inventory.getEntry(i);
			System.out.println(i + ". " + ingredient.toString());
		}
		System.out.println();
	}
	//- List all menu items
	public void displayMenu() {
		System.out.println("Menu:");
		for (int i = 1; i <= menu.getNumberOfEntries(); i++) {
			MenuItem menuItem = menu.getEntry(i);
			System.out.println(menuItem.toString());
		}
		System.out.println();
	}

	public static void main(String[] args) {
		RestaurantApp app = new RestaurantApp();
		Scanner scanner = new Scanner(System.in);
//		app.displayInventory();
//		app.displayMenu();
		int choice = 0;
		do {
			System.out.println("Simple Restaurant System");
			System.out.println("1. Add New Ingredient");
			System.out.println("2. Add New Menu Item");
			System.out.println("3. Display Inventory");
			System.out.println("4. Display Menu");
			System.out.println("0. Exit");
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			System.out.println();
			switch (choice) {
				case 0:
					System.out.println("Exiting system.");
					break;
				case 1:
					app.addNewIngredient();
					break;
				case 2:
					app.addNewMenuItem();
					break;
				case 3:
					app.displayInventory();
					break;
				case 4:
					app.displayMenu();
					break;
			}
		} while (choice != 0);
	}
}

class Ingredient implements Serializable {
	private String name;
	private int currentStock;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(int currentStock) {
		this.currentStock = currentStock;
	}

	@Override
	public String toString() {
		return String.format("%-10s %5d", name, currentStock);
	}
}

class MenuItem implements Serializable {
	private String name;
	private double price;
	private ListInterface<Ingredient> recipe;

	public MenuItem() {
		this.recipe = new ArrayList<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ListInterface<Ingredient> getRecipe() {
		return recipe;
	}

	public void setRecipe(ListInterface<Ingredient> recipe) {
		this.recipe = recipe;
	}

	public String toString() {
		return String.format("%-15s %5.2f", name, price);
	}
}

class MenuItemSnapshot implements Serializable {
	private String name;
	private double price;
	private ListInterface<Ingredient> recipeSnapshot;
}

class OrderItem implements Serializable {
	private MenuItemSnapshot menuItemSnapshot;
	private int quantity;
}
