/**
 * @author      Jamie Cahill jfc216@lehigh.edu
 * @version     .1
 * @since       3-22-2016
 */
 
 
 //WARNING: This class is not intended to be used directly with the POS system
 //Rather it should be compiled into a separate .jar file and then 
//imported into the CreditCardAuthenticator_Interface class
 
 package creditcardauthenticator;
 
//This class contains a bunch of hard coded credit cards and checks to see if the passed in credit
//card info matches any of the hard coded cards
public class CreditCardAuthenticator {
	//Note that each index represents a valid card i.e. valid_credit_card_numbers[0],valid_months[0],valid_years[0]... is all one card
	private final String[] valid_credit_card_numbers = {"0001000200030004","1234123412341234","9876987609870987","1000200030004000","1234567812345678"};

	private final String[] valid_months = {"01","12","04","06","08"};

	private final String[] valid_years = {"17","18","19","20","21"};

	private final String[] valid_cvc = {"123","456","789","234","345"};

	private String[] valid_names = {"Billy DeLucia","Eric Stahl","Jamie Cahill","Miles Zwicky","Nate Gyory"};

	public CreditCardAuthenticator() {
	//do nothing
	}

	public boolean authenticateCard(String card_number,String name, String month, String year, String cvc) {
	int index = -1;
	//Search to see if the card number exists
	for(int i = 0; i < valid_credit_card_numbers.length; i++) {
		if(valid_credit_card_numbers[i].equals(card_number)){
		index = i;
		break;
		}
	}
	if(index < 0)
		return false;
	
	return valid_names[index].equals(name) && valid_months[index].equals(month) && valid_years[index].equals(year) && valid_cvc[index].equals(cvc);	

	}

}

