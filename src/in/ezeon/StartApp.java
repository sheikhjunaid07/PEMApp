package in.ezeon;
/**
 * This class in an entry point of execution for PersonalExpenseManagerApplication (PEMApp).
 * @author sheik
 *
 */

public class StartApp {
  /**
   * This method is creating <code>PEMService</code> object 
   * and show app menu by calling showMenu() method. 
   * @param args
   */
	public static void main(String[] args) {
		PEMService service = new PEMService();
		service.showMenu();
   }
 }
