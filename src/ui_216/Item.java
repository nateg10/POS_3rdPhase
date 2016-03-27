public class Item {
    private int mID;
    private String mName;
    private int mTax_Type;
    private String mDescription;
    private double mPrice;

    public Item(int mID, String mName, double mPrice, int mTax_Type, String mDescription){
	setID(mID);
	setName(mName);
	setTaxType(mTax_Type);
	setDescription(mDescription);
	setPrice(mPrice);
    }
	
    public void setPrice(double mPrice){
	this.mPrice = mPrice;
    }

    public void setID(int mID){
	this.mID = mID;
    }
	
    public void setName(String mName){
	this.mName = mName;
    }
	
    public void setDescription(String mDescription){
	this.mDescription = mDescription;
    }
	
    public void setTaxType(int mTax_Type){
	this.mTax_Type = mTax_Type;
    }
	
    public int getID(){
	return this.mID;
    }
	
    public String getName(){
	return this.mName;
    }
	
    public int getTaxType(){
	return this.mTax_Type;
    }
    
    public String getDescription(){
	return this.mDescription;
    }
    
    public double getPrice(){
	return this.mPrice;
    }
    
    public String toString(){
	return "Item " + mID + ": [Name: " + mName + ", Description: " + mDescription + ", TaxType: " + mTax_Type + ", Price: $" + mPrice + "]";
    }

    public boolean equals(Item i){

		//Note I am omitting price due to rounding errors and such
        return (this.mID == i.mID && this.mName.equals(i.mName) && this.mTax_Type == i.mTax_Type && this.mDescription.equals(i.mDescription));
    }

}

