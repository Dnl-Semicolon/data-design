import java.io.*;
public class InventoryDAO {
	private String fileName = "inventory.dat";
	public void save(ListInterface<Ingredient> list) {
		File file = new File(fileName);
		try {
			ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
			ooStream.writeObject(list);
			ooStream.close();
		} catch (FileNotFoundException ex) {
			System.out.println("\nFile not found");
		} catch (IOException ex) {
			System.out.println("\nCannot save to file");
		}
	}
	public ListInterface<Ingredient> retrieve() {
		File file = new File(fileName);
		ListInterface<Ingredient> list = new ArrayList<>();
		try {
			ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
			list = (ArrayList<Ingredient>) (oiStream.readObject());
			oiStream.close();
		} catch (FileNotFoundException ex) {
			System.out.println("\nNo such file.");
		} catch (IOException ex) {
			System.out.println("\nCannot read from file.");
		} catch (ClassNotFoundException ex) {
			System.out.println("\nClass not found.");
		} finally {
			return list;
		}
	}
}
