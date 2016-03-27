/**
 * @author      Jamie Cahill jfc216@lehigh.edu
 * @version     .1
 * @since       3-2-2016
 */
 
 
 //WARNING: This class is not intended to be used directly with the POS system
 //			Rather it should be compiled into a separate .jar file and then imported into the
 //			Tax_Calculator_Interface class
 
 package taxcalculator;
 
 public class Tax_Calculator {
	private double sale_total;
	private final double tax_rate = .07;
	
	//Null constructor
	public Tax_Calculator(){
		//do nothing
		}   
	 
	public void enterSaleTotal(double sale_total){
		this.sale_total = sale_total;
	}
	 
	 public double calculateSalesTax(){
		 return sale_total*tax_rate;
	 }
 }
