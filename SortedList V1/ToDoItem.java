import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Class to create a new ToDoItem
 */
public class ToDoItem {

	private ToDoItem next; // next ToDoItem in the list from this item
	private ToDoItem prev; // previous ToDoItem in the list from this item
	private String name; // name of the event
	private String dueDate; // the due date of the project (in number form)
	private int priority; // high medium or low priority for sorting
	private String notes; // any notes related to the item
	private boolean isLate; // true if the item is late and false otherwise
	private boolean isDone; // true if the item is done and false otherwise
	private int sortNum; // used for sorting

	/**
	 * Constructor for the ToDoItem. All fields will be filled or required by the
	 * front end
	 * 
	 * @param name:     the name of the ToDoItem
	 * @param dueDate:  the date the item is due
	 * @param priority: high (1), medium (2), or low (3) priority
	 * @param notes:    the description for the item
	 */
	public ToDoItem(String name, String dueDate, int priority, String notes) {
		next = null;
		this.name = name;
		this.dueDate = dueDate;
		this.priority = priority;
		this.notes = notes;
		this.isDone = false; // automatically initialized to false because the item is new
		this.sortNum = findSortNum(dueDate); // findSortNum creates the variable using the due date
		this.isLate = setIsLate(sortNum); // automatically marks the item late if it is past the due date
	}

	/**
	 * Function that returns a string containing a formatted version of an item's
	 * data
	 * 
	 * @return a string containing the item's stored values
	 */
	public String getAllInfo() {
		String currPriority;
		String late;
		String done;
		switch (priority) { // convert priority to a word for better readability
		case 1:
			currPriority = "High";
			break;
		case 2:
			currPriority = "Medium";
			break;
		case 3:
			currPriority = "Low";
			break;
		default:
			currPriority = "Could not get priority";
			break;
		}
		late = (isLate) ? "Yes" : "No"; // sets late to Yes if isLate is true and No otherwise
		done = (isDone) ? "Yes" : "No"; // sets done to Yes if isDone is true and No otherwise

		return "Name: " + name + "\nDue Date: " + dueDate + "\nPriority: " + currPriority + "\nNotes: " + notes
				+ "\nLate: " + late + "\nItem Complete: " + done;
	}

	/**
	 * Function that takes in a string containing the date in the format "MM/dd/yy" and
	 * converts it to the sortNum value. The resulting number is in the form yyMMddp where
	 * p stands for it's priority status.
	 * 
	 * @param date: the date to be converted to a sorting number
	 * @return the correctly formatted sorting number
	 */
	public int findSortNum(String date) {
		String[] dateSplit = date.split("/");
		// checks if the date was entered "M/dd/yy" and adjusts the number if needed
		if (dateSplit[0].length() == 1) {
			dateSplit[0] = 0 + dateSplit[0];
		}
		// checks if the date was entered "MM/d/yy" and adjusts the number if needed
		if (dateSplit[1].length() == 1) {
			dateSplit[1] = 0 + dateSplit[1];
		}

		// moves the year to the beginning of the string to allow for sorting across years
		String temp = "" + dateSplit[2] + dateSplit[0] + dateSplit[1] + priority;
		System.out.println(temp);
		
		return Integer.parseInt(temp);
	}

	/**
	 * Function that compares a due date against the current date to check if the item
	 * is late and should be marked late
	 * 
	 * @param num
	 * @return
	 */
	public boolean setIsLate(int day) {
		try {
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
			String dateString = dateFormat.format(date);		
			int today = findSortNum(dateString); // uses the findSortNum to format the date for comparison

			// if today's date, when formatted, is less than the due date, then the item is not late
			if (today < day) {
				return false;
			}
		} catch (Exception e) { // if the date can't be retrieved
			return false;
		}
		return true;
	}

	/**
	 * Retrieves the next item in the list from this item
	 * 
	 * @return the next ToDoItem
	 */
	public ToDoItem getNext() {
		return next;
	}

	/**
	 * Sets the next item in the list from this item
	 */
	public void setNext(ToDoItem toDoItem) {
		next = toDoItem;
	}

	/**
	 * Retrieves the previous item in the list from this item
	 * 
	 * @return the previous ToDoItem
	 */
	public ToDoItem getPrev() {
		return prev;
	}

	/**
	 * Sets the previous item in the list from this item
	 */
	public void setPrev(ToDoItem toDoItem) {
		prev = toDoItem;
	}

	/**
	 * Gets the name of the item
	 * 
	 * @return the previous ToDoItem
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the item
	 * 
	 * @return the previous ToDoItem
	 */
	public void setName(String newName) {
		name = newName;
	}

	/**
	 * Gets the due date of the item
	 * 
	 * @return the due date
	 */
	public String getDueDate() {
		return dueDate;
	}

	/**
	 * Sets the due date of the item
	 */
	public void setDueDate(String newDueDate) {
		dueDate = newDueDate;
	}

	/**
	 * Gets the priority of the item
	 * 
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the due date of the item
	 */
	public void setPriority(int newPriority) {
		priority = newPriority;
	}

	/**
	 * Gets the item's notes
	 * 
	 * @return the item's notes
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Sets the item's notes
	 */
	public void setNotes(String newNotes) {
		notes = newNotes;
	}

	/**
	 * Gets the isLate variable for the item
	 * 
	 * @return the item's isLate variable
	 */
	public boolean getIsLate() {
		return isLate;
	}

	/**
	 * Sets the isLate variable
	 */
	public void setIsLate(boolean newIsLet) {
		isLate = newIsLet;
	}

	
	/**
	 * Gets the isDone variable for the item
	 * 
	 * @return the item's isDone variable
	 */
	public boolean getIsDone() {
		return isLate;
	}

	/**
	 * Sets the isDone variable
	 */
	public void setIsDone(boolean newIsDone) {
		isDone = newIsDone;
	}


	/**
	 * Gets the sortNum variable for the item
	 * 
	 * @return the item's sortNum variable
	 */
	public int getSortNum() {
		return sortNum;
	}

	/**
	 * Compares this ToDoItem with another specified ToDoItem.
	 *  1  if this item is greater (in the from that date future) than the other ToDoItem
	 * -1 if this item is lesser (in the past from that date) than the other ToDoItem
	 *  0  if they are equal
	 * 
	 * @param item
	 * @return
	 */
	public int compareItems(ToDoItem item) {
		if (this.sortNum > item.getSortNum()) {
			return 1;
		} else if (this.sortNum < item.getSortNum()) {
			return -1;
		} else {
			return 0;
		}
	}
}
