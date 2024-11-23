import java.io.*;
public class MenuDAO {
	private String fileName = "menu.dat";
	public void save(ListInterface<MenuItem> list) {
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
	public ListInterface<MenuItem> retrieve() {
		File file = new File(fileName);
		ListInterface<MenuItem> list = new ArrayList<>();
		try {
			ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
			list = (ArrayList<MenuItem>) (oiStream.readObject());
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
