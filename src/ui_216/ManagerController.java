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
    
    public void updateProduct(){
//        System.out.println("balls");
        MsUI.spawnUpdateProductWindow();
        UpdateProductUI up = new UpdateProductUI(this);
        up.setVisible(true);
        upUI = up;
    }
    
    // balls balls balls
    
    
}
