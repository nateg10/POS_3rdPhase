/**
 * @author      Jamie Cahill jfc216@lehigh.edu
 * @version     .1
 * @since       3-22-2016
 */

public class CreditCard {
	private String cardnumber;
	private String name;
	private String month;
	private String year;
	private String cvc;
	
	/**
	 * Creates a CreditCard object
	 * 
	 * @param carnumber a string representation of the creditcard number. It should be 16chars long and contain only numbers.
	 * @param name a string representation of the cardholders name, should only contain letters
	 * @param a string representation of the month. It should be 2chars long and contain only numbers.
	 * @param year a string representation of the expiration year. It should be 2chars long and contain only numbers.
	 * @param cvc a string representation of the cvc number. It should be 3chars long and contain only numbers.
	 */
	public CreditCard(String cardnumber,String name, String month, String year, String cvc){
		setcardnumber(cardnumber);
		setname(name);
		setmonth(month);
		setyear(year);
		setcvc(cvc);
		}
	
	/**
	 * Returns whether or not this credit card is a valid credit card according to the third party software
	 * 
	 * @param none
	 * @return true if the 3rd party software says that this is a valid creditcard, false otherwise
	 */
	public boolean authenticateCard() {
		CreditCardAuthenticatorInterface auth_interface = new CreditCardAuthenticatorInterface();
		return auth_interface.authenticateCard(this);
	}
	
	/**
	 * Sets this Creditcards cardnumber. Prints out a warning if the number provided is not a number a valid credit card can possess but will not throw an exception or otherwise impede the progress of the program
	 *  if this happens
	 * 
	 * @param cardnumber a string representation of the creditcard number. It should be 16chars long and contain only numbers.
	 */
	private void setcardnumber(String cardnumber) {
		if (cardnumber.length() != 16)
			System.out.println("WARNING in CreditCard.setcardnumber - trying to create a credit card with a creditcard number that's too long or short");
		if(!cardnumber.matches("[0-9]+"))
			System.out.println("WARNING in CreditCard.setcardnumber - trying to create a credit card with a creditcard number that's contains non-numbers");
		this.cardnumber = cardnumber;
	}
	
	/**
	 * Sets this Creditcards cardholder name
	 * 
	 * @param name a string representation of the cardholders name, should only contain letters
	 */
	private void setname(String name) {
		//if(!name.matches("[a-zA-Z]+")) //This doesn't work, need to figure out some way to account for spaces
		//	System.out.println("WARNING in CreditCard.name - trying to create a credit card with a cardholder name that's contains non-letters");
		this.name = name;
	}
	
	/**
	 * Sets this Creditcards expiration month. Prints out a warning if the month is not a month a valid credit card can possess but will not throw an exception or otherwise impede the progress of the program
	 *  if this happens
	 * 
	 * @param a string representation of the month. It should be 2chars long and contain only numbers.
	 */
	private void setmonth(String month) {
		if (month.length() != 2)
			System.out.println("WARNING in CreditCard.setmonth - trying to create a credit card with a an invalid month(month contains more than 2 digits)");
		if(!month.matches("[0-9]+"))
			System.out.println("WARNING in CreditCard.setmonth - trying to create a credit card with a month that's contains non-numbers");
		if(Integer.parseInt(month) > 12 || Integer.parseInt(month) < 1)
			System.out.println("WARNING in CreditCard.setmonth - trying to create a credit card with a an invalid month(month is not between 01 and 12)");
		this.month = month;
	}
	
	/**
	 * Sets this Creditcards expiration year. Prints out a warning if the month is not a year a valid credit card can possess but will not throw an exception or otherwise impede the progress of the program
	 *  if this happens
	 * 
	 * @param year a string representation of the expiration year. It should be 2chars long and contain only numbers.
	 */
	private void setyear(String year) {
		if (year.length() != 2)
			System.out.println("WARNING in CreditCard.setyear - trying to create a credit card with a an invalid year(year is not 2 digits)");
		if(!year.matches("[0-9]+"))
			System.out.println("WARNING in CreditCard.setyear - trying to create a credit card with a year that's contains non-numbers");
		if(Integer.parseInt(year) < 15)
			System.out.println("WARNING in CreditCard.setyear - trying to create a credit card with a an invalid year(year is less than the current year 2016)");
		this.year = year;
	}
	
	/**
	 * Sets this Creditcards cvc number. Prints out a warning if the cvc number is not a cvc number a valid credit card can possess but will not throw an exception or otherwise impede the progress of the program
	 *  if this happens
	 * 
	 * @param cvc a string representation of the cvc number. It should be 3chars long and contain only numbers.
	 */
	private void setcvc(String cvc) {
		if (cvc.length() != 3)
			System.out.println("WARNING in CreditCard.cvc - trying to create a credit card with a an invalid cvc(doesn't contain 3 digits)");
		if(!cvc.matches("[0-9]+"))
			System.out.println("WARNING in CreditCard.cvc - trying to create a credit card with a year that's contains non-numbers");
		this.cvc = cvc;
	}
	
	public String getcardnumber() {
		return cardnumber;
	}
	
	public String getname() {
		return name;
	}
	
	public String getmonth() {
		return month;
	}
	
	public String getyear() {
		return year;
	}
	
	public String getcvc() {
		return cvc;
	}
	
	public boolean equals(CreditCard cred) {		
		return this.getcardnumber().equals(cred.getcardnumber()) && this.getname().equals(cred.getname()) && this.getmonth().equals(cred.getmonth()) && this.getyear().equals(cred.getyear()) && this.getcvc().equals(cred.getcvc());
	}
	
	public String toString() {
		return "Cardnumber:" + cardnumber + "\nCardholdername:" + name + "\nExpMonth:" + month + "\nExpYear:" + year + "\nCVC:" + cvc;
		
	}
}
