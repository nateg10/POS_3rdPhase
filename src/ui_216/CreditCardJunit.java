import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.junit.Assert.*;

public class CreditCardJunit {
	
	
	/**
	 *  Test to see if Creditcards are being constructed properly and that the get and set methods work
	 */
	@Test
	public void testsetname() {
		//Construct a credit card
		String cardnumber = "1234567812345678";
		String name = "Jamie Cahill";
		String month = "08";
		String year = "18";
		String cvc = "123";
		CreditCard testcard = new CreditCard(cardnumber,name,month,year,cvc);
		
		//See if the values were set properly
		assertTrue("cardnumber is not being set or get correctly",cardnumber.equals(testcard.getcardnumber()));
		assertTrue("cardholder name is not being set or get correctly",name.equals(testcard.getname()));
		assertTrue("expiration month not being set or get correctly",month.equals(testcard.getmonth()));
		assertTrue("expiration year not being set or get correctly",year.equals(testcard.getyear()));
		assertTrue("cvc number not being set or get correctly",cvc.equals(testcard.getcvc()));
	}
	
	/**
	 * Test the CreditCardAuthenticarInterface and the 3rd party CreditCardAuthenticator
	 */
	@Test
	public void testauthenticateCard() {
		//Construct a valid credit card
		String cardnumber = "9876987609870987";
		String name = "Jamie Cahill";
		String month = "04";
		String year = "19";
		String cvc = "789";
		CreditCard testcard = new CreditCard(cardnumber,name,month,year,cvc);
		
		//The credit card authenticator should accept this as a valid card
		assertTrue("a valid credit card was rejected by the credit card authenticator",testcard.authenticateCard());
		
		//Now let's create an invalid card
		name = "invalid name";
		testcard = new CreditCard(cardnumber,name,month,year,cvc);
		assertFalse("an invalid credit card was accepted by the credit card authenticator",testcard.authenticateCard());
	}
	
	@Test
	public void testEquals() {
		//Construct 2 identical cards
		String cardnumber = "9876987609870987";
		String name = "Jamie Cahill";
		String month = "04";
		String year = "19";
		String cvc = "789";
		CreditCard testcard = new CreditCard(cardnumber,name,month,year,cvc);
		CreditCard testcard2 = new CreditCard(cardnumber,name,month,year,cvc);
		assertTrue("equals method coudld not identify two identical cards",testcard.equals(testcard2));
		
		//Now create different cards and see if it catches the difference
		CreditCard testcard3 = new CreditCard("1919191919191919",name,month,year,cvc);
		CreditCard testcard4 = new CreditCard(cardnumber,"new name",month,year,cvc);
		CreditCard testcard5 = new CreditCard(cardnumber,name,"01",year,cvc);
		CreditCard testcard6 = new CreditCard(cardnumber,name,month,"20",cvc);
		CreditCard testcard7 = new CreditCard(cardnumber,name,month,year,"111");
		assertFalse("equals method thought two different cards were identical",testcard.equals(testcard3));
		assertFalse("equals method thought two different cards were identical",testcard.equals(testcard4));
		assertFalse("equals method thought two different cards were identical",testcard.equals(testcard5));
		assertFalse("equals method thought two different cards were identical",testcard.equals(testcard6));
		assertFalse("equals method thought two different cards were identical",testcard.equals(testcard7));
	}	
	
}
