package ui_216;

public class ManagerController {
    private ManagerSessionUI MsUI;
    private AddNewProductUI anpUI;
    private UpdateProductUI upUI;
    
    //Now create a manager session object
    private boolean newProduct;

    public ManagerController(){
        MsUI = new ManagerSessionUI(this);
        MsUI.run();
    }
    
    public void newProduct(){
        AddNewProductUI np = new AddNewProductUI(this);
        np.setVisible(true);
        anpUI = np;
    }
    
    public void addNewProduct(String Name, String idString, String qtString, String ppuString, String taxIDString){
        //validate and pass to session to be added to the database
        System.out.println("Added New Product");
    }
    
    public void updateProduct(String idString){
        //validate product id
        //int id = Integer.getInteger(idString);
        //pass id to manager session to get product information
        
//        System.out.println("balls");
        //MsUI.spawnUpdateProductWindow();
        UpdateProductUI up = new UpdateProductUI(this);
        up.setVisible(true);
        upUI = up;
    }
    
    // balls balls balls
    
    
}
