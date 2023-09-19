package in.ezeon;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * The class contains various method to calculate PEM App reports.
 * @author sheik
 *
 */
public class ReportService {
	
	/**
	 * Declare a reference of singleton repository.
	 */
	private Repository repo = Repository.getRepository();

	/**
	 * The method calculate month-wise total and returns present in Map.
	 * Its preparing data in proper order. 
	 * will not merge your data in the middle so it has used "TreeMap".
	 * //(Key, Value) pair 
	 * @return
	 */
	public Map<String, Float> calculateMonthlyTotal() {
		Map<String, Float> m = new TreeMap();
		for (Expense exp : repo.expList) {
			Date expDate = exp.getDate();
			String yearMonth = DateUtil.getYearAndMonth(expDate);
			if(m.containsKey(yearMonth)) { 
				//when expense is already present for a month
				Float total = m.get(yearMonth);
				total = total + exp.getAmount();  //jo expense pehle se hue he usme add krne ke liye
				m.put(yearMonth, total); //old total ko replace krke new total add kr dega
			} else { 
				//this year is not yet added, so add here
				m.put(yearMonth, exp.getAmount());
			}
		}
		return m; //return final result as Map
	} 

	/**
	 * The method calculate year-wise total and returns present in Map.
	 * Its preparing data in proper order. 
	 * will not merge your data in the middle so it has used "TreeMap".
	 * //(Key, Value) pair 
	 * @return
	 */
	public Map<Integer, Float> calculateYearlyTotal() {
		Map<Integer, Float> m = new TreeMap();
		for (Expense exp : repo.expList) {
			Date expDate = exp.getDate();
			Integer year = DateUtil.getYear(expDate);
			if(m.containsKey(year)) { 
				//when expense is already present for a year
				Float total = m.get(year);
				total = total + exp.getAmount();  //jo expense pehle se hue he usme add krne ke liye
				m.put(year, total); //old total ko replace krke new total add kr dega
			} else { 
				//this year is not yet added, so add here
				m.put(year, exp.getAmount());
			}
		}
		return m; //return final result as Map
	} 
	 
	
	/**
	 * The method calculate category-wise total and returns present in Map.
	 * Its preparing data in proper order. 
	 * will not merge your data in the middle so it has used "TreeMap".
	 * //(Key, Value) pair 
	 * @return
	 */
	public Map<String, Float> calculateCategorizedTotal() {
		Map<String, Float> m = new TreeMap();
		for (Expense exp : repo.expList) {
			Long categoryId = exp.getCategoryId();
			String catName = this.getCategoryNameById(categoryId);
			if(m.containsKey(catName)) { 
				//when expense is already present in a category
				Float total = m.get(catName);
				total = total + exp.getAmount();  //jo expense pehle se hue he usme add krne ke liye
				m.put(catName, total); //old total ko replace krke new total add kr dega
			} else { 
				//this category is not yet added, so add here
				m.put(catName, exp.getAmount());
			}
		}
		return m; //return final result as Map
	} 
	
	/**
	 * The method returns category name for given categoryId. Returns null when wrong categoryId is supplied.
	 * @param categoryId
	 * @return
	 */
	public String getCategoryNameById(Long categoryId) {
		for (Category c : repo.catList) {  //Category List se 1 by 1 value lene ke lie
			if(c.getCategoryId().equals(categoryId)) { 
				return c.getName();
			}	
		}
		return null; //Category Id not presents
	}

}
