package in.ezeon;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Delayed;
/**
 * The class contains most of the operations related to PEM Application.
 * <p>
 * This class prepares a menu and various methods are present to handle the user action.
 * The class make use of <code>Repository</code> to store the data. 
 * Also using <code>reportService</code> to generate different required reports.
 * @author sheik
 *
 */
public class PEMService {
	
    /**
     * Declare a reference of repository by calling a static method which returns a "singleton" repository object.
     * 
     */
	Repository repo = Repository.getRepository(); //getRepository, repository object provide krega
	
	/**
	 * Declare a reference of ReportService to call different method to calculate reports.
	 */
	ReportService reportService = new ReportService();
	
	/**
	 * This is a scanner object to take standard input from keyboard.
	 */
	private Scanner in = new Scanner(System.in);
	
	/**
	 * This variable store the value of Menu Choice.
	 */
	private int choice;
	
	/**
	 * Call this Constructor to create PEMService object with default details.
	 */
	public PEMService() {  
		//prepareSampleData(); //Delete this method call after testing is completed
	}
	
	/**
	 * This Method prepares a PEMApp Menu using switch case and infinite loop, also ask for user choice
	 */
	public void showMenu() {
		while (true) {
			printMenu(); //Menu ko infinite time print krne ke liye
			switch(choice) {
			case 1:  onAddCategory();
			pressAnyKeyToContinue();
			break;

			case 2:  onCategoryList();
			pressAnyKeyToContinue();
			break; 

			case 3: onExpenseEntry();
			pressAnyKeyToContinue();
			break;

			case 4: onExpenseList();
			pressAnyKeyToContinue();
			break;

			case 5: onMonthlyExpenseList();
			pressAnyKeyToContinue();
			break;

			case 6: onYearlyExpenseList();
			pressAnyKeyToContinue();
			break;

			case 7: onCategorizedExpenseList();
			pressAnyKeyToContinue();
			break;

			case 8: onExit();
			break;
			}
		}
	}

	/**
	 * This method prints a Menu(CUI/CLI menu))
	 * CLI - Command Line Interface , CUI - Character User Interface. 
	 */
	public void printMenu() {
		System.out.println("----------PEM Menu----------");
		System.out.println("1. Add Category");
		System.out.println("2. Category List");
		System.out.println("3. Expense Entry");
		System.out.println("4. Expence List");
		System.out.println("5. Monthly Expense List");
		System.out.println("6. Yearly Expense List");
		System.out.println("7. Categorized Expense List");
		System.out.println("8. Exit");
		System.out.println("----------------------------");
		System.out.print("Enter Your Choice : ");
		choice = in.nextInt();
	}

	/**
	 * This method id called to hold a output screen after processing the requested task.
	 * and wait for any char input to continue the menu.
	 * read() --> For read single character. Any type of character.
	 */
	public void pressAnyKeyToContinue() {
		try {
			System.out.println("Press Any Key To Continue...");
			System.in.read(); //Default input stream
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is taking expense category name as input to add new category in the system.
	 */
	public void onAddCategory() {
		in.nextLine(); //new line character is read here which is already in stream and its not in use for now.
		System.out.print("Enter Category Name : ");
		String catName = in.nextLine();
		Category cat = new Category(catName);
		repo.catList.add(cat);
		System.out.println("Success : Category Added");
	}

	/**
	 * Call this method to print existing category list.
	 */
	public void onCategoryList() {
		System.out.println("Category List : ");
		List<Category> cList = repo.catList;
		for(int i = 0; i<cList.size(); i++) {
			Category c = cList.get(i);
			System.out.println((i+1)+". "+c.getName()+", "+c.getCategoryId());
		}
	}

	/**
	 * Call this method to enter expense data. The entered details will be added in repository.
	 */
	public void onExpenseEntry() {
		System.out.println("Enter Details of Expense Entry...");
		onCategoryList();
		System.out.print("Choose Category : ");
		int catChoice = in.nextInt();
		Category selectedCat = repo.catList.get(catChoice-1); //-1 becouse array index starting from 0 that means agr 2nd category chahiye to 1st palce ki value milegi

		System.out.print("Enter Amount  : ");
		float amount = in.nextFloat();

		System.out.print("Enter Remark : ");
		in.nextLine(); // For Dummy Character 
		String remark = in.nextLine();

		System.out.print("Enter Date(DD/MM/YYYY) : ");
		String dateAsString = in.nextLine();  //string form me date input 
		Date date  = DateUtil.stringToDate(dateAsString);    //date object return 

		//Add Expense detail in Expense Object
		Expense exp = new Expense();
		exp.setCategoryId(selectedCat.getCategoryId());
		exp.setAmount(amount);
		exp.setRemark(remark);
		exp.setDate(date);

		//Store Expense object in Repository
		repo.expList.add(exp);
		System.out.println("Success: Expense Added");
	}

	/**
	 * The method prints all entered expenses.
	 */
	private void onExpenseList() {
		System.out.println("Expense List : ");
		List<Expense> expList = repo.expList;
		for(int i =0; i<expList.size(); i++) {
			Expense exp = expList.get(i);
			String catName = reportService.getCategoryNameById(exp.getCategoryId());
			String dateString = DateUtil.dateToString(exp.getDate());
			System.out.println((i+1)+". "+catName+", "+exp.getAmount()+", "+exp.getRemark()+", "+dateString);
		}
	}

	/**
	 * This method is called from menu to prepare monthly-wise-expense-total.
	 * Its using <code>ReportServie</code> to calculate report. 
	 * The returned result is printed by this method. 
	 * Means this method invokes a call to generate report then result is printed by this method.
	 */
	private void onMonthlyExpenseList() {
		System.out.println("Montly Expense Total...");
		Map<String, Float>  resultMap = reportService.calculateMonthlyTotal();
		Set<String> keys = resultMap.keySet(); //keySet() <-- keys ko 1 by 1 nikalne ke lie
		for (String yearMonth : keys) {
			//201
			String[] arr = yearMonth.split(", ");
//			System.out.println(yearMonth);
			String year = arr[0];
			Integer monthNo = new Integer(arr[1]);
			String monthName = DateUtil.getMonthName(monthNo);
			System.out.println(year+", "+monthName+" : "+resultMap.get(yearMonth));
		}
	}

	/**
	 * This method is called from menu to prepare yearly-wise-expense-total.
	 * Its using <code>ReportServie</code> to calculate report. 
	 * The returned result is printed by this method. 
	 * Means this method invokes a call to generate report then result is printed by this method.
	 */
	private void onYearlyExpenseList() {
		System.out.println("Yearly Expense Total...");
		Map<Integer,Float> resultMap = reportService.calculateYearlyTotal();
		Float total = 0.0F;
		Set<Integer> years = resultMap.keySet();
		for (Integer year : years) {
			Float exp = resultMap.get(year);
			total = total+exp;
			System.out.println(year+" : "+exp);
		}
		System.out.println("----------------------------l-");
		System.out.println("Total Expense(INR) : "+total);
	}

	/**
	 * This method is called from menu to prepare category-wise-expense-total.
	 * Its using <code>ReportServie</code> to calculate report. 
	 * The returned result is printed by this method. 
	 * Means this method invokes a call to generate report then result is printed by this method.
	 */
	private void onCategorizedExpenseList() {
		System.out.println("Category Wise Expense Listing...");
		Map<String,Float> resultMap = reportService.calculateCategorizedTotal();
		Set<String> categories = resultMap.keySet();
		Float netTotal = 0.0F;
		for (String categoryName : categories) {
			Float catWiseTotal = resultMap.get(categoryName);
			netTotal = netTotal + catWiseTotal;
			System.out.println(categoryName+" : "+catWiseTotal);
		}
		System.out.println("--------------------------------------");
		System.out.println("Net Total : " + netTotal);
	}

	/**
	 * This method stop a JVM. Its closing PEM Application.
	 */
	private void onExit() {
		System.exit(0);
	}
	
	/**
	 * This method is preparing sample data for testing purpose. 
	 * It should be remove once Application is tested ok.
	 */
	public void prepareSampleData() {
		Category catParty = new Category("Party");
		delay();
		Category catShopping = new Category("Shopping");
		delay();
		Category catGift = new Category("Gift");

		repo.catList.add(catParty);
		repo.catList.add(catShopping);
		repo.catList.add(catGift);
		//Jan-2016
		Expense e1 = new Expense(catParty.getCategoryId(), 1000.0F, DateUtil.stringToDate("01/01/2016"), "N/A");
		delay();

		Expense e2 = new Expense(catParty.getCategoryId(), 2000.0F, DateUtil.stringToDate("02/01/2016"), "N/A");  // Remark --> "N/A"
		delay();

		//Feb-2016
		Expense e3 = new Expense(catShopping.getCategoryId(), 200.0F, DateUtil.stringToDate("01/02/2016"), "N/A");
		delay();

		Expense e4 = new Expense(catParty.getCategoryId(), 100.0F, DateUtil.stringToDate("02/02/2016"), "N/A");
		delay();

		//Dec-2016
		Expense e5 = new Expense(catGift.getCategoryId(), 500.0F, DateUtil.stringToDate("01/12/2016"), "N/A");
		delay();

		//Jan-2017
		Expense e6 = new Expense(catParty.getCategoryId(), 700.0F, DateUtil.stringToDate("01/01/2017"), "N/A");
		delay();

		//Feb-2017
		Expense e7 = new Expense(catShopping.getCategoryId(), 100.0F, DateUtil.stringToDate("01/02/2017"),"N/A");
		delay();
		
		//Mar-2017
		Expense e8 = new Expense(catGift.getCategoryId(), 5000.0F, DateUtil.stringToDate("01/03/2017"), "N/A");

		repo.expList.add(e1);
		repo.expList.add(e2);
		repo.expList.add(e3);
		repo.expList.add(e4);
		repo.expList.add(e5);
		repo.expList.add(e6);
		repo.expList.add(e7);
		repo.expList.add(e8);
	}

	/**
	 * The method sleep a thread for 10 ms.
	 */
	private void delay() {
		try {
			Thread.sleep(10);
		} catch(InterruptedException ex) {
			ex.printStackTrace();
		}
	}
}
