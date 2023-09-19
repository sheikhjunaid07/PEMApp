package in.ezeon;

import java.util.ArrayList;
import java.util.List;

/**
 * The class is used as Database/Repository and its a "Singleton".
 * @author sheik
 *
 */
public class Repository {
	
	/**
	 * The list holds all expense added by user.
	 */
	public List<Expense> expList = new ArrayList();
	
	/**
	 * The list holds all expense-categories added by user.
	 */
	public List<Category> catList = new ArrayList();
	
	/**
	 * A "singleton" reference of repository.
	 */
	private static Repository repository;
	
	/**
	 * "Private Constructor" to restrict object creation from outside.
	 * With this, multiple objects of the repository will not be created, 
	 * when the number of objects created, it will start maintaining the list at different places.
	 */
	private Repository() { 
	}
	
	/**
	 * The method provides a "Singleton" object of Repository.
	 * private constructor ko call krne ki technique.
	 * ye Repository object aapko khud provide krega or ek hi object banaega bhale hi aap kitni hi baar call kro
	 * @return
	 */
	public static Repository getRepository() {
		if(repository == null) {
			repository = new Repository(); //jb koi object available nhi hoga tb ye object create krega 
		}                       		   //otherwise available object return krega
		return repository;
	}
}
