package ui_216;

public class ManagerController {
    ManagerSessionUI MsUI;
    //Now create a manager session object
    private boolean newProduct;

    public ManagerController(){
        MsUI = new ManagerSessionUI(this);
        MsUI.run();
    }
    
    public void newProduct(){
        MsUI.spawnNewProductWindow();
    }
    
    public void updateProduct(){
        System.out.println("balls");
        MsUI.spawnUpdateProductWindow();
    }
    
    // balls balls balls
    
    
}
