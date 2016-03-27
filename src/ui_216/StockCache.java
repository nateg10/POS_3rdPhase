
/**
 * @author      Miles Zwicky mmz216@lehigh.edu
 * @version     .1
 * @since       3-2-2016
 */

import java.util.ArrayList;
/**
* A local version of the inventory list in order to reduce number of calls to the database.
*
* Follows the singleton design pattern
*/
public class StockCache {

	private final static boolean verbose = false; 
	/**
	* instance of the StochCache, used for singlton design method.
	*/

	private static StockCache instance;
	/**
	* Stock list of items, which in turn have item description and
	*/
	private ArrayList<Item> stock = new ArrayList<Item>();

	/**
		 * Creates an Item object
		 * <p>
		 *
		 * @param connection -- connection to populate cache
		 */

	private StockCache() {}

	public static StockCache getInstance() {
		if (instance == null) {
			instance = new StockCache();
		}
		return instance;
	}

	/**
		 * Add Item
		 * <p>
		 * Takes in an item object and adds it to the arrayList
		 *
		 * @param myItem -- Item to be added.
		 */


	public static void addItem(Item myItem) {
		instance.stock.add(myItem);
	}

	/**
		 * Prints Arraylist
		 * <p>
		 * Prints out the contents of the inventory in a tidy fasion.
		 */

	public static void printItems() {
		for (Item i : instance.stock) {

			System.out.println(i);
		}

	}


	/**
	* Clears the Arraylist
	* <p>
	* Wipes the contents of the arraylist before refresh
	*/
	public static void emptyCache() {
		instance.stock.clear();

	}


	/**
	* get item
	* <p>
	* pulls the item (corresponding to given ID) from the cache, if its a miss, returns null.
		 */

	public static Item getItem(int itemID) {
		//create a fake item with teh matching id, this located the real item in the array.
		
		int i = 0;
		int index = -1;
		for (Item itemB : instance.stock) {
			if (itemB.getID()==itemID) {
				index = i;
				break;
			}
			i++;


		}
		if (verbose)
			System.out.println("index is: " + index);
		if (index < 0) {
			return null;
		}
		System.out.println("index location: " + index );
		return instance.stock.get(index);
	}

	// TODO: tostring Method
	// TODO
}
