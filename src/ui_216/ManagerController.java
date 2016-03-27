package ui_216;

public class ManagerController {
    ManagerSessionUI MsUI;
    //Now create a manager session object
    private boolean newProduct;

    public ManagerController(){
        MsUI = new ManagerSessionUI();
    }
    
    public void newProduct(){
        MsUI.spawnNewProductWindow();
    }
    
    public void updateProduct(){
        MsUI.spawnUpdateProductWindown();
    }
    
    
}
