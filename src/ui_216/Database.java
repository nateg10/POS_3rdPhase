/**
 * @author      Jamie Cahill jfc216@lehigh.edu
 * @version     .1
 * @since       2-25-2016
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Database {

private boolean verbose = false;


	/**
	 * Holds the user type(admin, manager,...) of the user who instantiated this Database object
	 * Admin = 0, Manager = 1, Cashier = 2, Customer = 3
	 */
	private int usertype;

	/**
	 * Holds the userid of the user who instantiated this Database object
	 */
	private int userid;

	/**
	 * Connection object through which database queries are made
	 */
	private Connection conn;

	/**
	 * Objects through which SQL queries are made and returned
	 */
	private Statement stmt;
	private ResultSet rs;
	private PreparedStatement preStatement;


	/**
	* A local copy of the information stored in the Item table
	*/

	private StockCache cache;
	/**
	 * Info need to connect to the database
	 */
	private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://us-cdbr-azure-east-a.cloudapp.net:3306/pos_database";
	private static final String USER = "bb7e7b52fb7bbe";
	private static final String PASS = "b8ef43e3";

	/**
	 * Creates a Database object
	 * <p>
	 * The Database object will facilitate all reads and writes to the database.
	 * When creating a database object a username and password are required in order
	 * to the set appropriate read and write permissions of the current user
	 * (i.e. We don't want a cashier to be able to modify a manager's user profile)
	 *
	 * @param username -- username of the user accessing the database
	 * @param password -- password of the user accessing the database
	 */
	public Database(String username, String password) {
		//Load the JDBC Driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception ex) {
			System.out.println("Could not load MySQL JDBC Driver - do you have the .jar file set up correctly?");
			ex.printStackTrace();
			return;
		}

		//Connect to the database
		if (!this.connect()) {
			System.out.println("Error - connection Failed");
		} else {


			//The connection should now be established
			//Let's query for the user credentials
			try {
				stmt = conn.createStatement();
				//Query for a person with the specified username and password
				rs = stmt.executeQuery("SELECT * FROM person WHERE USERNAME = '" + username + "' AND USER_PASSWD = '" + password + "';");
				//Check to see if the user exists and set usertype accordingly
				rs.first();
				usertype = Integer.parseInt(rs.getString("USER_TYPE"));
				//Save the userid of the person who created the account for later use
				userid = Integer.parseInt(rs.getString("PERSON_ID"));
				this.disconnect();
			} catch (SQLException ex) {
				System.out.println("ERROR - Invalid username and password!!");
				System.out.println("SQLException: " + ex.getMessage());
				System.out.println("SQLState: " + ex.getSQLState());
				System.out.println("VendorError: " + ex.getErrorCode());
				this.disconnect();
			}
		}
	}


	/**
	 * Returns whether or not the Database object can connect to the database
	 *
	 * @param none
	 * @return true if the a connection can be made, false otherwise
	 */
	public boolean isConnected() {
		boolean flag = this.connect();
		this.disconnect();
		return flag;
	}


	/**
	 * Returns whether or not the Database is currently connected!
	 *
	 * @param none
	 * @return true if the a connection is open, false if it is closed
	 */
	public boolean checkConnectionOpen() {
		try {
			//flag = true if conncetion is open
			boolean flag = !(conn.isClosed());
			if(verbose) {
			if (flag) {
				System.out.println("connection is open");

			} else {
				System.out.println("connection is closed");

			}
		}
			return flag;
		} catch (SQLException ex) {
			System.out.println("ERROR - Could not check state!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return false;
	}


	/**
	 * Returns whether or not a user with the passed in username and password exists within the database
	 *
	 * @param username -- the username to be checked
	 * @param password -- the password to be checked
	 * @return true if the username and password combo exists within the database, false otherwise
	 */
	public boolean authenticateUser(String username, String password) {
		if (!this.connect()) {
			return false;
		}

		try {
			stmt = conn.createStatement();
			//Query for a person with the specified username and password
			rs = stmt.executeQuery("SELECT * FROM person WHERE USERNAME = '" + username + "' AND USER_PASSWD = '" + password + "';");
			//Try to extract data from teh query, can be anything but in this case let's get person_id
			//An exception will be thrown if the query is empty
			if(verbose)
				printResultSet(rs);


			rs.first();

			//System.out.println("Printed");
			usertype = Integer.parseInt(rs.getString("USER_TYPE"));
			//System.out.println("type set");
			this.disconnect();
			//System.out.println("disconnected...");

			return true;
		} catch (SQLException ex) {
			this.disconnect();
			return false;
		}

	}

	/**
	 * Connects the Database object to the actual database. By convention the database connection should be opened and
	 * then closed on every to this Database object to ensure there aren't any connections left open
	 *
	 * @param none
	 * @return true if a connection was established, false otherwise
	 */
	private boolean connect() {
		//Setup the connection to the server
		try {
			conn = DriverManager.getConnection(DB_URL, USER, PASS);		
			if (!conn.isClosed()) {
				return true;

			} else {
				return false;
			}
		} catch (SQLException ex) {
			System.out.println("ERROR - Could not connect to server!!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			return false;
		}
	}

	/**
	 * Disconnects the Database object from the actual database. By convention the database connection should be opened and
	 * then closed on every call to this Database object to ensure there aren't any connections left open
	 *
	 * @param none
	 */
	private void disconnect() {
		
		try {
			if(preStatement != null && !preStatement.isClosed())
				preStatement.close();
		}
		catch (SQLException ex) {
			System.out.println("Prestatement failed to close...");

		}

		try {
			if(stmt != null && !stmt.isClosed()){
			stmt.close();
			}
		} catch (SQLException ex) {
			System.out.println("Statement failed to close...");

		}


		//Close the ResultSet object

		try {
				if(!rs.isClosed()) {
			rs.close();
			}
		} catch (SQLException ex) {
			System.out.println("RS failed to close...");

		}

		//Close the Connection object

		try {
			if (!conn.isClosed()) {
				conn.close();

				//System.out.print("Database is closed?? : ");
			//	System.out.print(conn.isClosed());
		//		System.out.println();
			}
		} catch (SQLException ex) {
			System.out.println("Connection Failed to close..");


		}
	}

	/**
	 * The finalize method gets called whenever the Java garbage collector collects an object.
	 *  In this case I have overriden it to ensure that it closes any open connections before the Database gets garbage collected
	 *
	 * @param none
	 */
	@Override
	protected void finalize() {
		this.disconnect();
	}

	/**
	 * Returns information about a specified item
	 * <p>
	 * Returns an Item object which contains all the info stored by the database
	 * on the specified itemid
	 *
	 * @param itemid id of the item whose info is being returned
	 * @return itemObject
	 */
	public Item getItemInfo(int itemID) {
			StockCache cache = StockCache.getInstance();
			Item result = cache.getItem(itemID);
			if(result!=null){
				return result;
			}
			if(verbose)
				System.out.println("cache miss!");
		
		if (!this.connect()) {
			return null;
		}
		try {
			//Construct the query
			stmt = conn.createStatement();
			String query  =  "SELECT * FROM item WHERE item_id = '" + Integer.toString(itemID) + "';";
			//Run the query
			rs = stmt.executeQuery(query);
			//Parse out the info
			rs.first();
			String name = rs.getString("NAME");
			int tax_type = Integer.parseInt(rs.getString("TAX_TYPE"));
			double price = Double.parseDouble(rs.getString("PRICE"));
			String desc = rs.getString("DESCRIPTION");

			//Create an Item object with the values from the Database and return
			Item i = new Item(itemID, name, price, tax_type, desc);

			this.disconnect();
			return i;
		} catch (SQLException ex) {
			System.out.println("Error in Database.getItemInfo!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
		}
		return null;
	}
	
	
	/**
	 * Returns information about a specified item. This variant assumes that the Database object already has a connection open to the database and does not open or close any connections.
	 * As such it is intended for use only within the Database object itself
	 * <p>
	 * Returns an Item object which contains all the info stored by the database
	 * on the specified itemid
	 *
	 * @param itemid id of the item whose info is being returned
	 * @return itemObject
	 */
	//@Overload
	private Item getItemInfo(int itemID,boolean already_connected) {
			StockCache cache = StockCache.getInstance();
			Item result = cache.getItem(itemID);
			if(result!=null){
				return result;
			}
			if(verbose)
				System.out.println("cache miss!");
		
		try {
			//Construct the query
			stmt = conn.createStatement();
			String query  =  "SELECT * FROM item WHERE item_id = '" + Integer.toString(itemID) + "';";
			//Run the query
			rs = stmt.executeQuery(query);
			//Parse out the info
			rs.first();
			String name = rs.getString("NAME");
			int tax_type = Integer.parseInt(rs.getString("TAX_TYPE"));
			double price = Double.parseDouble(rs.getString("PRICE"));
			String desc = rs.getString("DESCRIPTION");

			//Create an Item object with the values from the Database and return
			Item i = new Item(itemID, name, price, tax_type, desc);

			
			return i;
		} catch (SQLException ex) {
			System.out.println("Error in Database.getItemInfo!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		}
		return null;
	}


	/**
	 * Returns an array list of credit cards ascociated with the specified user
	 * 
	 * @param userid userID of the person's whose CreditCards we are searching for
	 * @return Arraylist of CreditCard objects ascociated with the user
	 */
	 public ArrayList getCreditCard(int userid) {
		 ArrayList al = new ArrayList();
		 
		 if (!this.connect()) {
			return al;
		}
		 
		 try{ 
			//Insert the item into the database
			String query = "SELECT * FROM CREDIT_CARD WHERE USERID = ?;";
			preStatement = conn.prepareStatement(query);
			preStatement.setInt(1,userid);
			rs = preStatement.executeQuery();
			
			while(rs.next()) {
				String name = rs.getString("CARDHOLDERNAME");
				String month = rs.getString("EXPMONTH");
				String year = rs.getString("EXPYEAR");
				String cardnumber = rs.getString("CARDNUMBER");
				String cvc = rs.getString("CVC");
				CreditCard cred = new CreditCard(cardnumber,name,month,year,cvc);
				al.add(cred);
			}
			this.disconnect();
		}
			catch (SQLException ex) {
			System.out.println("Error in Database.getCreditCard!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
		}
		 return al;
	 }
	 
	 /**
	  * Stores the given creditcard on the database. The creditcard will be ascociated with the given userid
	  * 
	  * @param userid the userid of the person to whom the creditcard belongs
	  * @param cred CreditCard object to be stored
	  * @return true if the creditcard was sucessfully saved to the database, false if the creditcard already exists within the database or the method fails
	  */
	public boolean storeCreditCard(int userid, CreditCard cred) {
		//Establish a connection to the database
		 if (!this.connect()) {
			return false;
		}
		
		try{
			//First make sure the credit card isn't already saved in the database
			String query = "SELECT * FROM CREDIT_CARD WHERE CARDNUMBER=? AND CARDHOLDERNAME=? AND EXPMONTH=? AND EXPYEAR=? AND CVC=?;";
			preStatement = conn.prepareStatement(query);
			preStatement.setString(1,cred.getcardnumber());
			preStatement.setString(2,cred.getname());
			preStatement.setString(3,cred.getmonth());
			preStatement.setString(4,cred.getyear());
			preStatement.setString(5,cred.getcvc());
			rs = preStatement.executeQuery();
			if(rs.next()){
				System.out.println("Warning in Database.storeCreditCard - CreditCard already exists within Database");
				return false;
			}
	
			//Since it doesn't exist within the Database we can store it within the database
			query = "INSERT INTO CREDIT_CARD (USERID,CARDNUMBER,CARDHOLDERNAME,EXPMONTH,EXPYEAR,CVC) VALUES (?,?,?,?,?,?)";
			preStatement = conn.prepareStatement(query);
			preStatement.setInt(1,userid);
			preStatement.setString(2,cred.getcardnumber());
			preStatement.setString(3,cred.getname());
			preStatement.setString(4,cred.getmonth());
			preStatement.setString(5,cred.getyear());
			preStatement.setString(6,cred.getcvc());
			preStatement.executeUpdate();
			
			this.disconnect();
			return true;
		}
		catch (SQLException ex) {
			System.out.println("Error in Database.storeCreditCard!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
			return false;
		}
	}
	
	/**
	 * Removes the specified CreditCard from the database(NOTE: Doesn't check to see if the card exists within the Database)
	 * 
	 * @param cred CreditCard to be removed from the Database
	 * @return true if sucessfully removed, false if the method fails
	 */
public boolean removeCreditCard(CreditCard cred) {
	//Establish a connection to the database
	 if (!this.connect()) {
		return false;
	}
	
	try{
		//First make sure the credit card isn't already saved in the database
		String query = "DELETE FROM CREDIT_CARD WHERE CARDNUMBER=? AND CARDHOLDERNAME=? AND EXPMONTH=? AND EXPYEAR=? AND CVC=?;";
		preStatement = conn.prepareStatement(query);
		preStatement.setString(1,cred.getcardnumber());
		preStatement.setString(2,cred.getname());
		preStatement.setString(3,cred.getmonth());
		preStatement.setString(4,cred.getyear());
		preStatement.setString(5,cred.getcvc());
		preStatement.executeUpdate();
		this.disconnect();
		return true;
	}
	
	catch (SQLException ex) {
		System.out.println("Error in Database.removeCreditCard!!");
		System.out.println("SQLException: " + ex.getMessage());
		System.out.println("SQLState: " + ex.getSQLState());
		System.out.println("VendorError: " + ex.getErrorCode());
		this.disconnect();
		return false;
	}
}

	/**
	 * Adds a new item to the Database
	 * <p>
	 * Takes an Item object and creates a new entry in the database for that item.
	 * This method returns the unique itemid created by the database for the newly created item.
	 *
	 * @param i new Item to be stored in the databse
	 * @return unique itemid created by the database for the new item
	 */
public int createNewItem(Item i,int stock) {
	//Check to make sure the item doesn't already have a valid item ID(if it has a valid ID it probably is already in the database)
	if(i.getID() > 0) {
		System.out.println("ERROR in Database.createNewItem - specified Item already has a valid itemID!");
		return -1;
	}
	
	if (!this.connect()) {
			return -1;
		}
	
	try{ 
		//Insert the item into the database
		String query = "INSERT INTO item (PRICE,DESCRIPTION,NAME,TAX_TYPE,STOCK) VALUES (?,?,?,?,?);";
		preStatement = conn.prepareStatement(query);
		preStatement.setBigDecimal(1,BigDecimal.valueOf(i.getPrice()));
		preStatement.setString(2,i.getDescription());
		preStatement.setString(3,i.getName());
		preStatement.setInt(4,i.getTaxType());
		preStatement.setInt(5,stock);
		preStatement.executeUpdate();
		
		//Now using the same information extract the item that was just added to determine the itemid assigned to it by the database
		query = "SELECT * FROM item WHERE PRICE = ? AND DESCRIPTION = ? AND NAME = ? AND TAX_TYPE = ? AND STOCK = ?;";
		preStatement = conn.prepareStatement(query);
		preStatement.setBigDecimal(1,BigDecimal.valueOf(i.getPrice()));
		preStatement.setString(2,i.getDescription());
		preStatement.setString(3,i.getName());
		preStatement.setInt(4,i.getTaxType());
		preStatement.setInt(5,stock);
		rs = preStatement.executeQuery();
		
		//Parse out the itemid and return
		rs.first();
		int itemid = rs.getInt("ITEM_ID");
		
		this.disconnect();
		return itemid;
	}
	catch (SQLException ex) {
			System.out.println("Error in Database.createNewItem!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
		}

return -1;
}

public void removeItem(int itemid) {
	if(itemid <= 0) {
		System.out.println("ERROR in Database.removeItem - invalid itemID!");
		return;
	}
	
	if (!this.connect()) {
			return;
		}
	
	try{ 
		//Insert the item into the database
		String query = "DELETE FROM item WHERE item_id = ?";
		preStatement = conn.prepareStatement(query);
		preStatement.setInt(1,itemid);
		preStatement.executeUpdate();
		this.disconnect();
	}
	catch (SQLException ex) {
			System.out.println("Error in Database.removeItem!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
		}
		return;
}

	/**
	 * Returns information about the User who created this Database object
	 * <p>
	 * This method returns a User object containing all the information contained in the database
	 * about the user who instantiated this Database object
	 *
	 * @return User containing all the stored information about the user who created this Database object
	 *
	 */
///public User getUserInfo() {
//Query the database based on the saved userid for the following values
//Get username
//Get userpassword
//Get userid
//Get phone number
//Get billing address
//Get shipping address
//Get items currently rented by user
//Get a list of past transacton ids
//Get credit card number

//Collect all the info into a User object and return the user object

///}

	/**
	 * Returns information about the specified Transaction
	 * <p>
	 * Returns a Transaction object containing all the information about the transaction with the passed in Transaction id
	 *
	 * @param transactionid id number of the transaction being looked up
	 * @return Transcation object containing information about the the specified transaction
	 */
public Transaction getTransactionInfo(int transactionid) {
	if(transactionid < 0) {
		System.out.println("ERROR in Database.getTransactionInfo - transactionid is < 0");
		return null;
	}
	
	
	try{
		//Query the database for the transaction ascociated with the transaction id
		String query = "SELECT * FROM transaction WHERE transaction_id = ?";
		preStatement = conn.prepareStatement(query);
		preStatement.setInt(1,transactionid);
		rs = preStatement.executeQuery();
		
		//Extract the info from the query
		rs.first();
		int employee_id = rs.getInt("EMPLOYEE_ID");
		int customer_id = rs.getInt("CUSTOMER_ID");
		Timestamp time = rs.getTimestamp("trans_date");
		
		//Now we need to query the transaction _item table for all the items asociated with the transaction
		query = "SELECT * FROM transaction_item WHERE transaction_id = ?";
		preStatement = conn.prepareStatement(query);
		preStatement.setInt(1,transactionid);
		rs = preStatement.executeQuery();
		
		//TODO: Update this once User's are implemented
		Transaction t = new Transaction(transactionid,null,null);
		
		while(rs.next()) {
			int item_id = rs.getInt("ITEM_ID");
			int qty = rs.getInt("QUANTITY");
			int type = rs.getInt("TYPE");
			Item i = this.getItemInfo(item_id,true);
			Triplet<Item,Integer,Integer> trip = new Triplet(i,qty,type);
			t.addItem(trip);
		}
		
		this.disconnect();
		return t;
		
	}
	catch (SQLException ex) {
			System.out.println("Error in Database.getTransactionInfo!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
		}
		return null;
}

//Given the passed in userid ,returns a User object with info on that user
//Read permissions must be checked!!!
	/**
	 * Returns information about the specifed user
	 *
	 * @param userid of the user being looked up
	 * @return User object containing information about the specified user, if the current user does not have read permission for the specified user a null pointer is returned intead
	 */
	public void getUserInfo(int userid) {
		//If the user is looking themselves up return their own info
		///if(this.userid = userid)
		///return this.getUserInfo();

		//If they aren't looking themselves up first get the usertype of the passed in id
		int lookuptype = 0;

		//Now check if the user calling the function has the right permissions
		if (usertype >= lookuptype && usertype != 0) {
			//Throw an error - illegal lookup
			return;
		}





		//All good - return the info
		//Query the database based on the passed in userid for the following values
		//Get username
		//Get userpassword
		//Get userid
		//Get phone number
		//Get billing address
		//Get shipping address
		//Get items currently rented by user
		//Get a list of past transacton ids
		//Get credit card number

		//Collect all the info into a User object and return the user object
	}




	public int getStock(int itemid) {
		try {
			//Construct the query
			stmt = conn.createStatement();
			String query  =  "SELECT * FROM item WHERE item_id = '" + Integer.toString(itemid) + "';";
			//Run the query
			rs = stmt.executeQuery(query);
			//Parse out the info and return
			rs.first();
			this.disconnect();

			return Integer.parseInt(rs.getString("STOCK"));
		} catch (SQLException ex) {
			System.out.println("ERROR in Database.getStock()!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
		}
		return 0;
	}


	/**
	 * Updates the database's local inventory cache with currently sold items and their current prices.
	 * To be run at the start of the day.
	 * <p>
	 * Returns the number of items added, -1 for error.
	 *
	 * @return itemObject
	 */
	public int updateCache() {

		if (!this.connect()) {
			return -1;
		}

		try {
			StockCache cache = StockCache.getInstance();
			cache.emptyCache();
			stmt = conn.createStatement();
			String query  =  "SELECT * FROM item";
			//Run the query
			rs = stmt.executeQuery(query);
			int item_id;
			double price;
			String description;
			String name;
			int tax_type;
			int itemCount = 0;
			while (rs.next()) {
				item_id = rs.getInt("ITEM_ID");
				price = rs.getDouble("PRICE");
				tax_type = rs.getInt("TAX_TYPE");
				description = rs.getString("DESCRIPTION");
				name = rs.getString("NAME");
				cache.addItem(new Item(item_id, name, price, tax_type, description));
				itemCount++;
			}
			this.disconnect();

			return itemCount;
		} catch (SQLException ex) {
			System.out.println("ERROR - Error Querying Database for Items");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

			this.disconnect();
			return -1;
		}

	}



	private void printResultSet(ResultSet resultSet) {

		try {
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (resultSet.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					if (i > 1) System.out.print(",  ");
					String columnValue = resultSet.getString(i);
					System.out.print(columnValue + " " + rsmd.getColumnName(i));
				}
				System.out.println("");
			}
		} catch (SQLException ex) {
			System.out.println("ERROR - Error Printing Resultset!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());

			this.disconnect();
		}
	}

	/**
	 * Saves all the information about the passed in Transaction object to the database
	 * Returns the unique Transaction id that the database assigns to the transaction
	 *
	 * @param t Transaction object to be saved to the database
	 * @return unique id given to the Transaction by the database, returns -1 on failure
	 */
	public int saveTransaction(Transaction t) {
// First we are creating an entry in the TRANSACTION table where we will need customerID, cashierID, and the date
		int cashierid = t.getCashier().getId();
		int customerid = t.getCustomer().getId();
		long time = System.currentTimeMillis();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(time);
		int transaction_id;
		//Now perform the query to insert into the database
		preStatement = null;
		
		if (!this.connect()) {
			return -1;
		}
		
		try {
			//Construct the query
			stmt = conn.createStatement();
			String query  =  "INSERT INTO transaction (EMPLOYEE_ID,CUSTOMER_ID,TRANS_DATE) VALUES (?,?,?)";
			//Run the query
			//NOTE: We're using a preparedStaament here
			
			preStatement = conn.prepareStatement(query);
			preStatement.setInt(1,cashierid);
			preStatement.setInt(2,customerid);
			preStatement.setTimestamp(3, timestamp);
			preStatement.executeUpdate();
			//conn.commit();

			
			//Now using the exact same same info query the database to get the unique transaction id given to the transaction
			query = "SELECT * FROM transaction WHERE EMPLOYEE_ID = ? AND CUSTOMER_ID = ? AND TRANS_DATE = ?";
			preStatement = conn.prepareStatement(query);
			preStatement.setInt(1,cashierid);
			preStatement.setInt(2,customerid);
			preStatement.setTimestamp(3, timestamp);
			rs = preStatement.executeQuery();
			
			//Parse out the Transaction id
			rs.first();
			transaction_id = rs.getInt("TRANSACTION_ID");
			
			//Now referencing this Transaction ID we need to insert into the transaction_item table one time for every unique item in the transaction specifying the item_id, quantity, type, price_sold,tax_amt
			for (int i = 0; i < t.getTripleList().size(); i++) {
				//Get the info we need
				//A is an Item, B is amount, C is a boolean stating whether or not it is a rental
				int item_id = t.getTripleList().get(i).getA().getID();
				int qty = t.getTripleList().get(i).getB();
				int rent_or_buy = t.getTripleList().get(i).getC();
				double price = t.getTripleList().get(i).getA().getPrice();

				//Create a new query
				//query = "INSERT INTO transaction_item (TRANSACTION_ID,ITEM_ID,QUANTITY,TYPE,PRICE_SOLD) VALUES (" + transaction_id + "," + item_id + "," + qty + "," + rent_or_buy + "," + price + ");";
				query = "INSERT INTO transaction_item (TRANSACTION_ID,ITEM_ID,QUANTITY,TYPE,PRICE_SOLD) VALUES (?,?,?,?,?)";
				preStatement = conn.prepareStatement(query);
				preStatement.setInt(1,transaction_id);
				preStatement.setInt(2,item_id);
				preStatement.setInt(3,qty);
				preStatement.setInt(4,rent_or_buy);
				preStatement.setDouble(5,price);
				//Execute the query
				preStatement.executeUpdate();

			/*	if(!this.updateInventory(item_id,qty)) {
					System.out.println("Error in saveTransaction when updating inventory");
					preStatement.close();
					return -1;
				}*/
				
			}
			
			this.disconnect();
			return transaction_id;
		}

		catch (SQLException ex) {
			System.out.println("Error writing Transaction to database!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
			
		}

		return -1;

	}
	public boolean updateInventory(int item_ID, int amount_sold) {
		
		try{
		int current_stock_amount = 0;
		this.connect();
		//Construct the query
		stmt = conn.createStatement();
		String query  =  "SELECT STOCK FROM ITEM WHERE ITEM_ID = '" + item_ID + "';";
		//Run the query
		rs = stmt.executeQuery(query);
		rs.first();
		current_stock_amount = rs.getInt("STOCK");
		int new_stock_amount = current_stock_amount - amount_sold;


		conn.commit();

		//Now using the exact same same info query the database to get the unique transaction id given to the transaction
		rs = stmt.executeQuery(query);
		//Parse out the Transaction id
		rs.first();

		query  =  "UPDATE `pos_database`.`item` SET `STOCK`= '" + new_stock_amount + "' WHERE `ITEM_ID`='" + item_ID + "';";
		stmt.executeQuery(query);
		return true;
		}catch(SQLException ex){
			System.out.println("Error writing Transaction to database!!");
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
			this.disconnect();
			return false;
		}
	}

	/*
	 * Saves all the info about the passed in User to the database as a new User
	 * Returns the unique userid assigned by the database to the new user
	 * Throws an error if the passed in User has a non-zero userid
	 * Throws an error if the usertype of the new user is greater or equal to the current userstype (i.e cashier creating a manager
	 * */
///public int insertNewUser(User u) {
///if(u.usertype >= usertype) {
//Throw error - illegal permission
///}

//Create a new unique userid

//Save the following values
// username
// userpassword
// userid
// usertype
// phone number
// billing address
// shipping address
// items currently rented by user
// a list of past transacton ids
// credit card number
///}

	/*
	 * Updates a user in the database
	 * Looks up the user based on the userid of the passed in User, then overwrites all their data
	 * Throws an error if the userid of the passed User doesn't exist
	 * Throws an error if the current User doesn't have permssion to alter the passed in User
	 * */
///public boolean updateUser(User u) {
//First look up the usertype of the passed in User
///	int lookuptype;



///}



};
