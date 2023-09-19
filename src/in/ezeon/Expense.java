package in.ezeon;

import java.util.Date;

/**
 * This is a domain class represents Expense.
 * @author sheik
 *
 */
public class Expense {
    
	/**
	 * A unique expense id, here its auto-generated as current milliseconds, 
	 * but should use some standard algorithms for primary key generation.
	 */
	private Long expenseId = System.currentTimeMillis();
	
	/**
	 * Represents a category of this expense
	 */
	private Long categoryId;  //Foreign Key
	private Float amount;
	private Date date;
	private String remark;
	
	public Expense() {
		//Default Constructor
	}
	
	
    //Parameterized constructor
	//Expense enter krne ke liye requirement
	public Expense(Long categoryId, Float amount, Date date, String remark) {
		this.categoryId = categoryId;
		this.amount = amount;
		this.date = date;
		this.remark = remark;
	}

	public Long getExpenseId() {
		return expenseId;
	}

	public void setExpenseId(Long expenseId) {
		this.expenseId = expenseId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
