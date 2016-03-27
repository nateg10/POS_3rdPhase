

class Test_Database {

	public static void main(String [] args) {
		Database testdb = new Database("jfc216", "abc123");
		System.out.println(testdb.authenticateUser("jfc216", "abc123"));
		System.out.println("Authenticate User:" + testdb.authenticateUser("jfc216", "abc123"));
		
		//Create new Users
		Address addr1 = new Address("123 Sunshine Lane","","Townville","123456");
		Address addr2 = new Address("51 Montclair","","Townville","123456");
		CreditCard credcard = new CreditCard();
		
		User u1 = new User("TestCustomer","5553141234","5551239876","adfsd@gmail.com",addr1,addr1,credcard,11,1);
		User u2 = new User("TestCashier","5554969745","5554969745","cashier@gmail.com",addr1,addr1,credcard,21,1);
		
		Transaction my_trans = new Transaction(u1,u2);
		
		//Valid Item ids
		//1
		//11
		//21
		//31
		//41
		//Pull some Items from the database
		Item i1 = testdb.getItemInfo(11);
		Item i2 = testdb.getItemInfo(21);
		Item i3 = testdb.getItemInfo(31);
		//public void addItem(Triplet<Item, Integer, Integer> t){
		//Package the items in triplets
		Triplet<Item,Integer,Integer> trip1 = new Triplet(i1,4,0);
		Triplet<Item,Integer,Integer> trip2 = new Triplet(i2,2,0);
		Triplet<Item,Integer,Integer> trip3 = new Triplet(i3,1,0);
		
		my_trans.addItem(trip1);
		my_trans.addItem(trip2);
		my_trans.addItem(trip3);
		
		//Ok save the transaction
		testdb.saveTransaction(my_trans);
		
		
		//System.out.println(testdb.checkConnectionOpen());
		//3rd party tax calc test code
		//Tax_Calc_Interface test = new Tax_Calc_Interface();
		//System.out.println(test.calculateTax());

	}
};
