/**
 * @author      Jamie Cahill jfc216@lehigh.edu
 * @version     .1
 * @since       3-2-2016
 */
 
 
 /*
  * The purpose of this class is to control interaction between our POS system and the third party tax system
  * It takes in a Transaction object and returns the calculated tax
  */
 
 import taxcalculator.Tax_Calculator;

 public class Tax_Calc_Interface{
 
 
	//Null constructor
	public Tax_Calc_Interface(){}

	//Given a Transaction object the interface will call the tax calculator and return the taxes due
	public double calculateTax(Transaction t){
		double sale_cost = 0;
		Tax_Calculator Calc = new Tax_Calculator();
		
		//Pass the info into the tax calculator
		Calc.enterSaleTotal(t.calculateSubTotal());
		//Return the calculation from the Database
		return Calc.calculateSalesTax();
	}
 }
