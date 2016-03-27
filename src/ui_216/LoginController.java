
public class LoginController {
    private LoginUI gui;            //create a LoginUI object as data field
    private PurchaseSession model;
    
    public LoginController(){
        gui = new LoginUI(this);    //pass the LoginController to the gui object
        gui.setVisible(true);       //display login ui
    }
    
    public void addModel(PurchaseSession ses){
        model = ses;
    }
    
    public void onLoginAttempt(String userID, String password){
        //recieves login information from UI and passes it on to be verified
        boolean verified = verifyLoginInfo(userID, password);
        
        if(verified){               //verifies login info to start POS ui
            gui.validLogin();
            switchToPOS(userID, password);
        } else{
            gui.invalidLogin();
        }
    }
    
    private boolean verifyLoginInfo(String userId, String password){
        //will contact session to verify
        PurchaseSession my_sess = new PurchaseSession(userId,password);
        if(my_sess.login()) {
			//On valid login save the session as the UI's model
			this.addModel(my_sess);
			return true;
		}
		return false;
    }
    
    private void switchToPOS(String id, String pass){
        gui.setVisible(false);
        PointOfSaleController pos = new PointOfSaleController(id, pass,model);
        gui = null;
    }
}
