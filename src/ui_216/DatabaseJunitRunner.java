import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class DatabaseJunitRunner {
   public static void main(String[] args) {
	   System.out.println("Starting Junit Tests for Database.java");
      Result result = JUnitCore.runClasses(DatabaseJunit.class);
      for (Failure failure : result.getFailures()) {
         System.out.println("Failure in:"+failure.toString());
      }
      System.out.println("Database Junit Test Results:"+result.wasSuccessful());
   }
}  
