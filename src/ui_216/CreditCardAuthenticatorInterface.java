/**
 * @author      Jamie Cahill jfc216@lehigh.edu
 * @version     .1
 * @since       3-22-2016
 */

import creditcardauthenticator.CreditCardAuthenticator;

public class CreditCardAuthenticatorInterface {
	
	public CreditCardAuthenticatorInterface() {
		//do nothing
	}
	
	public boolean authenticateCard(CreditCard c) {
		CreditCardAuthenticator auth = new CreditCardAuthenticator();
		return auth.authenticateCard(c.getcardnumber(),c.getname(),c.getmonth(),c.getyear(),c.getcvc());
	}
	
}
