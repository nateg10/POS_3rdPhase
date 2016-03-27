import java.util.ArrayList;

/** Class for a User. */
public class User {
  
  private String mName;
  private String mPhone;
  private String mMobile;
  private String mEmail;
  private Address mBillingAddr;
  private Address mShippingAddr;
  private ArrayList<Item> mCurrentRentals;
  private ArrayList<Transaction> mPastTransactions;
  private CreditCard mCreditCard;
  private int mUserID;
  private int mUserType;
  

  /** Creates a User. */
  /** This should only be used by a Database to create a User object as only the Database should specify userid
   *  When creating a new User use the below one
   */
  public User(String name, String phone, String mobile, String email, 
              Address billingAddr, Address shippingAddr, CreditCard creditCard, int userId, 
              int userType) {
    mName = name;
    mPhone = phone;
    mMobile = mobile;
    mEmail = email;
    mBillingAddr = billingAddr;
    mShippingAddr = shippingAddr;
    mCreditCard = creditCard;
    mUserID = userId;
    mUserType = userType;
    mCurrentRentals = new ArrayList<Item>();
    mPastTransactions = new ArrayList<Transaction>();
  }
  

    public User(String name, String phone, String mobile, String email, 
              Address billingAddr, Address shippingAddr, CreditCard creditCard, 
              int userType) {
    mName = name;
    mPhone = phone;
    mMobile = mobile;
    mEmail = email;
    mBillingAddr = billingAddr;
    mShippingAddr = shippingAddr;
    mCreditCard = creditCard;
    mUserID = -1; //default userid
    mUserType = userType;
    mCurrentRentals = new ArrayList<Item>();
    mPastTransactions = new ArrayList<Transaction>();
  }
  
  /** Creates a user. */
  public void createUser(User usr) {
  }
  
  
  /** Adds a transaction to the user account. */
  public void addTransaction(Transaction trans) {
    mPastTransactions.add(trans);
  }
  
  /** Adds a rental to the user account. */
  public void addRental(Item item) {
    mCurrentRentals.add(item);
  }
  
  /** Deletes a user from the system. */
  public void deleteUser(User usr) {
    
  }
  
  /** Returns user's past transactions. */
  public ArrayList<Transaction> getPastTransactions() {
    return mPastTransactions;


  }
  
  /** Returns user's current rentals. */
  public ArrayList<Item> getCurrentRentals() {
    return mCurrentRentals;
  }
  
  /** Returns user's name. */
  public String getName() {
    return mName;
  }
  
  /** Sets user's name. */
  public void setName(String name) {
    mName = name;
  }
  
  /** Returns user's phone. */
  public String getPhone() {
    return mPhone;
  }
  
  /** Sets user's phone. */
  public void setPhone(String phone) {
    mPhone = phone;
  }
  
  /** Returns user's mobile. */
  public String getMobile() {
    return mMobile;
  }
  
  /** Sets user's mobile. */
  public void setMobile(String mobile) {
    mMobile = mobile;
  }
  
  /** Returns user's email. */
  public String getEmail() {
    return mEmail;
  }
  
  /** Sets user's email. */
  public void setEmail(String email) {
    mEmail = email;
  }
  
  /** Returns user's billing address. */
  public Address getBillingAddress() {
    return mBillingAddr;
  }
  
  /** Sets user's billing address. */
  public void setBillingAddress(Address billingAddress) {
    mBillingAddr = billingAddress;
  }
  
  /** Returns user's shipping address. */
  public Address getShippingAddress() {
    return mShippingAddr;
  }
  
  /** Sets user's shipping address. */
  public void setShippingAddress(Address shippingAddress) {
    mShippingAddr = shippingAddress;
  }
  
  /** Returns user's credit card. */
  public CreditCard getCreditCard() {
    return mCreditCard;
  }
  
  /** Sets user's credit card. */
  public void setCreditCard(CreditCard creditCard) {
    mCreditCard = creditCard;
  }
  
  /** Returns user's id. */
  public int getId() {
    return mUserID;
  }
  
  /** Sets user's id. */
  public void setId(int userId) {
    mUserID = userId;
  }
  
  /** Returns user's type. */
  public int getType() {
    return mUserType;
  }
  
  /** Sets user's type. */
  public void setType(int userType) {
    mUserType = userType;
  }
}
  
