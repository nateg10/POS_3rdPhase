import java.util.ArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.Timestamp;

public class Transaction {
    private int mTransactionID;
    private User mCustomer;
    private User mCashier;
    private int mTransactionType;
    private ArrayList<Triplet<Item, Integer, Integer>> mTripleList;
    private int mPaymentMethod;
    private Timestamp mTimestamp;
    //private double mSubTotal = 0.0;
    //private double mTotal = 0.0;

    public static final int SALE = 0;
    public static final int RENTAL = 1;
    public static final int RETURN = 2;
    public static final int CASH = 0;
    public static final int CREDIT = 1;
    public static final int DEBIT = 2;
    public static final int GIFTCARD = 3;

	public Transaction(User mCustomer, User mCashier) {
		setCustomer(mCustomer);
		setCashier(mCashier);
		setTransactionID(0);   //default transactionID
	}
	
    public Transaction(int mTransactionID, User mCustomer, User mCashier){
		
	setTransactionID(mTransactionID);
	setCustomer(mCustomer);
	setCashier(mCashier);
	setTransactionType(mTransactionType);
	
    }
    
    public void setTransactionID(int mTransactionID){
	this.mTransactionID = mTransactionID;
    }
    
    public void setCustomer(User mCustomer){
	this.mCustomer = mCustomer;
    }
    
    public void setCashier(User mCashier){
	this.mCashier = mCashier;
    }
        
    public void setTransactionType(int mTransactionType){
	this.mTransactionType = mTransactionType;
    }
    
   public void setTimestamp(Timestamp timestamp) {
	this.mTimestamp = timestamp;
   }

   public Timestamp getTimestamp() {
	return mTimestamp;
  }

    public int getTransactionID(){
	return this.mTransactionID;
    }
    
    public User getCustomer(){
	return this.mCustomer;
    }
    
    public User getCashier(){
	return this.mCashier;
    }
    
    public ArrayList<Triplet<Item, Integer, Integer>> getTripleList(){
	return this.mTripleList;
	}
    
    public int getTransactionType(){
	return this.mTransactionType;
    }
    
    public int getPaymentMethod(){
	return this.mPaymentMethod;
    }
    
    public double getTax(){
	Tax_Calc_Interface Calc = new Tax_Calc_Interface();
	return round(Calc.calculateTax(this));
    }
    //TODO: insert item into arraylist
    public void addItem(Triplet<Item, Integer, Integer> t){
	if(mTripleList == null){
	    mTripleList = new ArrayList<Triplet<Item,Integer, Integer>>();
	}
	mTripleList.add(t);
    }
    
    //TODO: remove item from list
    public void removeItem(Triplet<Item, Integer, Integer> t){
	for(int i =0; i < mTripleList.size(); i++){
	    if(t.getA().getID() == mTripleList.get(i).getA().getID()){
	    	mTripleList.remove(t);
	    }
	}
    }
    public void addPayment(int mPaymentMethod){
	this.mPaymentMethod = mPaymentMethod;
    }
    public double calculateSubTotal(){
	double mSubTotal = 0.0;
	for(int i = 0; i < getTripleList().size(); i++){
	    mSubTotal += getTripleList().get(i).getA().getPrice() * getTripleList().get(i).getB();
	}
	return round(mSubTotal);
    }
    public double calculateTotal(){
		double mTotal = 0.0;
		
    	return mTotal = round(this.calculateSubTotal() + this.getTax());
    }
    
    //Rounds a double to two decimal places
    private double round(double value) {
		BigDecimal bd = new BigDecimal(value);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
    
    public String toString(){
    	StringBuilder s = new StringBuilder();
   		s.append(Integer.toString(getCashier().getId()) + "\n" + "Name | Price | Qty |\n");
    	for(int i = 0; i < mTripleList.size(); i++){
    		s.append("\n" + mTripleList.get(i).getA().getName() + " " + mTripleList.get(i).getA().getPrice() 
    				+ "  x" + Integer.toString(mTripleList.get(i).getB())+ "\n\n");
    	}
    	s.append("SubTotal: $" + Double.toString(calculateSubTotal()) + "\nTax: $" + Double.toString(getTax()) 
    				+ "\nTotal: $" + Double.toString(calculateTotal()) + "\n");
    	
    	return s.toString();
    }
    
    public String deprecatedtoString(String myid){
    	StringBuilder s = new StringBuilder();
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
    	s.append("$crumDogBillionairesPOS\n\n");
    	s.append(dateFormat.format(date) + "\n\n");
   		s.append("Name | Price | Qty |\n");
    	for(int i = 0; i < mTripleList.size(); i++){
    		s.append("\n" + mTripleList.get(i).getA().getName() + " " + mTripleList.get(i).getA().getPrice() 
    				+ "  x" + Integer.toString(mTripleList.get(i).getB()));
    	}
    	s.append("\n\nSubTotal: $" + Double.toString(calculateSubTotal()) + "\nTax: $" + Double.toString(getTax()) 
    				+ "\nTotal: $" + Double.toString(calculateTotal()) + "\n\n");
    	
    	s.append("Cashier:" + myid + "\n\n");
    	s.append("HAVE  A GREAT DAY!");
    	return s.toString();
    }
 
}
