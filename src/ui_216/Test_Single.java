class Test_Single {

	public static void main(String[] args) {


		Database testdb = new Database("jfc216", "abc123");
		testdb.updateCache();
		StockCache.printItems();
				testdb.updateCache();

		testdb.updateCache();
		StockCache.printItems();



/*
		StockCache mycache = StockCache.getInstance();



		mycache.addItem(new Item(45, "aTestItem", 5.74, 0,
		                         "This is a test item, it is not actually sold in stores."));
		mycache.addItem(new Item(31, "AnotherTest", 2.24, 1,
		                         "This is not a test item, it will actually be sold!"));

		mycache.printItems();
		System.out.println("just testing some other shit now... \n\n");

		StockCache anotherInstance = StockCache.getInstance();
		mycache.addItem(new Item(25, "aItem", 5.74, 0,
		                         "Singleton is workingg!"));
		mycache.printItems();
		*/

	}
}