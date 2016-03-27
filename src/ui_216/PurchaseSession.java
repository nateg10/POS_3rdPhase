
/** Class which represents a PurchaseSession. */
import java.io.PrintWriter;
public class PurchaseSession {
  
    private String mUserID, mPassword;
    private Transaction mCurrent_Transaction;
    private Transaction mFinalize_Transaction;
    private Database mDB;

    
    /** Constructor for PurchaseSession. */
    public PurchaseSession(String userID, String password) {
      mUserID = userID;
      mPassword = password;
      newTransaction(); //TODO: CHANGE THIS ONCE USERS HAVE BEEN IMPLEMENTED - Also might want to have the UI have a button to wipe the current transaction and create a new one
    }
    
        //TODO: CHANGE THIS ONCE WE HAVE USERS IMPLEMENTED
    public void newTransaction() {
		//Create new Users
		Address addr1 = new Address("123 Sunshine Lane","","Townville","123456");
		Address addr2 = new Address("51 Montclair","","Townville","123456");
		CreditCard credcard = null;
		
		User u1 = new User("TestCustomer","5553141234","5551239876","adfsd@gmail.com",addr1,addr1,credcard,11,1);
		User u2 = new User("TestCashier","5554969745","5554969745","cashier@gmail.com",addr1,addr1,credcard,21,1);
		mCurrent_Transaction = new Transaction(u1,u2);
	}
    

    /** Logs a user into the database. */
    public boolean login(String userId, String password) {
      mDB = new Database(userId, password);
      return mDB.isConnected();
    }
    
     /** Logs a user into the database using mUserId an mPassword. */
    public boolean login() {
      mDB = new Database(mUserID, mPassword);
      return mDB.isConnected();
    }
    
    /** Adds an item to the current transaction. */
    public Triplet<Item, Integer, Integer> addItem(int id){
      Item item = mDB.getItemInfo(id);
      Triplet<Item, Integer, Integer> t = new Triplet<Item, Integer, Integer>(item, 1, 1);
      mCurrent_Transaction.addItem(t);
      return t;
    }
    
    /** Gets the total of the transaction. */
    public double getTotal(){
      return mCurrent_Transaction.calculateTotal();
    }
    
    /** Gets the subtotal of the transaction. */
    public double getSubTotal(){
      return mCurrent_Transaction.calculateSubTotal();
    }
    
    /** Gets the tax of the transaction. */
    public double getTax(){
      return mCurrent_Transaction.getTax();
    }
    
    public String getCashierID(){
    	return mUserID;
    }
    
    //TODO: CHANGE THIS TO NOT ACCEPT A STRING ONCE USERS ARE IMPLEMENTED
    public void checkout(String tempID) {  
		//Save the transaction to the Database
		mDB.saveTransaction(mCurrent_Transaction);
		//Write this stuff to a text file
		//Putting it in a try statment will automatically close it after finishing(Java 7 or later)
		try(PrintWriter out = new PrintWriter("receipt.txt")) {
		out.println(mCurrent_Transaction.deprecatedtoString(tempID));
	} catch(java.io.FileNotFoundException ex) {
		System.out.println("ERROR - Could not print receipt to file!!");
	}
	}
}

