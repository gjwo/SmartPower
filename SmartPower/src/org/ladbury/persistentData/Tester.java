package org.ladbury.persistentData;


public class Tester {
/**
 * This class is an alternative entry point for testing or populating data structures
 * and processing methods
 * 
 * @author GJWood
 * @version 1.0 2012/11/29
 */
	private static TestDataGenerator	testData;
	private static PersistentData 		data; 

	public static void main(String[] args) {
		//Device d;
		data = new PersistentData();
		data.getActivity().outputCSV();
		data.getEntityManager().close();
	}
	protected void generateTestData(){
		//**********
		// check DB before calling this
		//**********
		testData = new TestDataGenerator();		
		data.getEntityManager().getTransaction().begin();
		testData.defineAbode(data.getEntityManager());
		testData.defineRooms(data.getEntityManager());
		testData.definePatterns(data.getEntityManager());
		testData.defineCatalogue(data.getEntityManager());
		testData.defineDevices(data.getEntityManager());
		testData.placeDevices(data.getEntityManager());
		
		testData.generateEvents(data.getEntityManager());
		data.getEntityManager().getTransaction().commit();
	
	}

}
