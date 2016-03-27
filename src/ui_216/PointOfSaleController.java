/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Billy
 */
 

 
public class PointOfSaleController {
    
    private PointOfSaleUI pos;          //data fields to be passed to the session
    private String userID;
    private String password; 
    private PurchaseSession model;
    
    public PointOfSaleController(String id, String pass){
        userID = id;
        password = pass;
        pos = new PointOfSaleUI(this);
       
        pos.setVisible(true);            //start POS ui
         pos.setID(id);  //TODO: GET RID OF THIS ONCE USERS ARE PROPERLY IMPLEMENTED
    }
    
    public PointOfSaleController(String id, String pass, PurchaseSession ses){
        userID = id;
        password = pass;
        pos = new PointOfSaleUI(this);
        pos.setVisible(true);            //start POS ui
        setModel(ses);
         pos.setID(id);  //TODO: GET RID OF THIS ONCE USERS ARE PROPERLY IMPLEMENTED
    }
    
    public void setModel(PurchaseSession ses){
        model = ses;
    }
    

    
/*
    public void addItem(String itemIDString){
        int id;
        Triplet<Item,Integer,Integer> itemInfo = null;
        Item item = null;
        int quantity = 0;
        if((id = Integer.getInteger(itemIDString)) == null){
            pos.displayError("Item ID invalid, not a number");
        }else{
            //tripple a = item b = in quant c = purchase type
            itemInfo = model.addItem();
            item = itemInfo.getA();
            quantity = item.Info.getB();
            // get item purchase type
            pos.addLineItem(item.getID().toString(), item.getName(), item.getPrice().toString(), quantity.toString());
        }
        
        
        
    }
  */


public Triplet addItem(String itemIDString) {
	return model.addItem(Integer.parseInt(itemIDString));
}  
    public double getTotal(){
        return model.getTotal();
    }
    
    public double getSubTotal(){
       return model.getSubTotal();
    }
    
    public double getTax(){
        return model.getTax();
    }
    
    public void checkout() {
		model.checkout(userID); //TODO: CHANGE THIS NOT TO ACCEPT A STRING ONCE USERS ARE IMPLEMENTED
		//Create a new POS Contrller to wipe the screen - WARNING: THIS IS A TERRIBLE MEMORY LEAK RIGHT NOW
		PurchaseSession newSession = new PurchaseSession(userID,password);
		pos.setVisible(false);
		PointOfSaleController newController = new PointOfSaleController(userID,password,newSession);
	}
    public void removeItem(int itemID){
        //idk man
    }
    
}
