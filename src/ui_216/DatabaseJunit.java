import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;
import java.io.PrintStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseJunit {
	//Create a Database object for testing
	private static Database testdb;
	private static PrintStream originalStream = System.out; 
	
	/**
	 * Instantiate the Database object before running the other tests
	 */
	@BeforeClass
	static public void beforeClass() {
		testdb = new Database("jfc216", "abc123");
	}
	 
	/**
	 * Test to see if the we can connect to the MySQL Database
	 */
	@Test
	public void testisConnected() {
		assertTrue("Could not sucessfully connect to database",testdb.isConnected());
	}
	
	/**
	 * Test to see if the connections to the database are being closed properly
	 */
	@Test
	public void testcheckConnectionOpen() {
		assertFalse("Database connections are not being closed properly",testdb.checkConnectionOpen());
	}
	
	/**
	 *  Test to see if we can sucessfully authenticate registered users in the database
	 */
	@Test 
	public void testauthenticateUser() {
		assertFalse("Authentication of invalid user credentials returned true",testdb.authenticateUser("invalid username","invalid password"));
		assertTrue("Authentication of valid user credentials failed",testdb.authenticateUser("jfc216","abc123"));
	}
	
	/**
	 * Test to see if we can insert an Item, query for an Item, and remove an Item from the Database
	 */
	 @Test
	 public void testItem() {
		 //Create a test item and insert it into the database
		 Item testitem = new Item(-1,"Test_Item",99.99,5,"This is a test item");
		 testitem.setID(testdb.createNewItem(testitem,0));
		 assertTrue("Failed to insert item into database",testitem.getID() > -1);  //If the item is sucessfully inserted into the Database then the returned itemid should not be negative
		 
		 //Search for the item
		 Item returneditem = testdb.getItemInfo(testitem.getID());
		 assertNotNull("Item inserted into database could not be found",returneditem);
		 
		 if(returneditem != null)
			assertTrue("Item found in database doesn't match item inserted into database",returneditem.equals(testitem));
		 
		 //Remove the test item after testing
		 testdb.removeItem(testitem.getID());
		 
		 //Check to make sure the item was sucessfully removed
		 disableSystemOut(); //Disable System.out.println to hide all the ugly exceptions that will be thrown as the method fails
		 Item deletedItem = testdb.getItemInfo(testitem.getID());
		 assertNull("Item was not sucessfully deleted from database",deletedItem);
		 enableSystemOut();
	 }
	 
	 
	 @Test
	 public void testTransaction() {
		 
		 
		 
	 }
	 
	 /**
	  * Checks to see if we can save, get, and remove a CreditCard from the Database
	  */
	 @Test
	 public void testCreditCard() {
		//Construct two credit cards
		String cardnumber = "9876987609870987";
		String name = "Test Card";
		String month = "04";
		String year = "19";
		String cvc = "789";
		CreditCard testcard = new CreditCard(cardnumber,name,month,year,cvc);
		cardnumber = "1234123412341234";
		name = "Test Card2";
		CreditCard testcard2 = new CreditCard(cardnumber,name,month,year,cvc);
		
		 //Delete the cards to make sure they don't already exist in the database
		 assertTrue("could not remove creditcard from database",testdb.removeCreditCard(testcard));
		 assertTrue("could not remove creditcard from database",testdb.removeCreditCard(testcard2));
		 
		 //Now store the cards in the database
		 assertTrue("could not store creditcard within database",testdb.storeCreditCard(1337,testcard));
		 assertTrue("could not store creditcard within database",testdb.storeCreditCard(1337,testcard2));
		 
		 //Now pull the credit cards from the database
		 ArrayList creditcardlist = testdb.getCreditCard(1337);
		 assertTrue("error getting credit cards from database",creditcardlist.size() == 2);
		 
		 //Make sure the credit cards match the ones saved
		CreditCard newcard = (CreditCard)creditcardlist.get(0);
		CreditCard newcard2 = (CreditCard)creditcardlist.get(1);
		
		 assertTrue("creditcard pulled from database doesn't match the creditcard that was saved to the database(card1)",testcard.equals(newcard)||testcard.equals(newcard2));
		 assertTrue("creditcard pulled from database doesn't match the creditcard that was saved to the database(card2)",testcard2.equals(newcard) || testcard2.equals(newcard2));
		 
		 //Clean up by deleting the cards
		 assertTrue("could not remove creditcard from database",testdb.removeCreditCard(testcard));
		 assertTrue("could not remove creditcard from database",testdb.removeCreditCard(testcard2));
		 
		 //Check to make sure the cards were deleted
		 creditcardlist = testdb.getCreditCard(1337);
		 assertTrue("error getting credit cards from database",creditcardlist.size() == 0);
	 }
	 
	 
	 ///////////////////////////////Non-Test Methods/////////////////////////////////////////////////////////
	 
	 //Used to stop system out to avoid printing out a bunch of error lines when testing functions that are supposed to fail
	 private void disableSystemOut() {
		 PrintStream dummyStream    = new PrintStream(new OutputStream(){
			public void write(int b) {
			//NO-OP
			}
		});
		System.setOut(dummyStream);
	 }
	 
	 //Re-enables System.out.println
	 private void enableSystemOut() {
		 System.setOut(originalStream);
	 }
	
}
