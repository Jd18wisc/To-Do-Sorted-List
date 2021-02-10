/**
 * Class that creates a sorted doubly linked list for a specific category
 * specified by the user.
 * 
 * @author Jacob
 */
public class SortedList {

	private String listName; // the name of the list
	private ToDoItem head; // the first item in the list
	private ToDoItem current; // the last location of the pointer in the list
	private ToDoItem tail; // last item in the list
	private int size; // the size of the list

	/**
	 * Constructor that initializes the sorted list and its fields
	 * 
	 * @param the name of the list
	 */
	public SortedList(String listName) {
		this.listName = listName;
		head = null;
		tail = null;
		size = 0;
	}

	/**
	 * Retrieves the name of the list
	 * 
	 * @return string with the name of the list
	 */
	public String getListName() {
		return listName;
	}

	/**
	 * Retrieves the head of the list
	 * 
	 * @return the ToDoItem at the head of the list
	 */
	public ToDoItem getHead() {
		return head;
	}

	/**
	 * Retrieves the tail of the list
	 * 
	 * @return the ToDoItem in the tail position
	 */
	public ToDoItem getTail() {
		return tail;
	}

	/**
	 * Retrieves the head of the list
	 * 
	 * @return the ToDoItem at the current pointer
	 */
	public ToDoItem getCurrent() {
		return current;
	}

	/**
	 * Retrieves the size of the list
	 * 
	 * @return the size of the list
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Inserts a ToDoItem into its correct position in the list. The date and the
	 * entered priority are used to correctly place items. The order entered is the
	 * tie breaker for same day and priority items. If the MM/dd is 12/26 and
	 * priority is 1 (high) the sort value with become 12261
	 * 
	 * When searching for the correct sort location, the insert method checks
	 * against the current ToDoItem's sort value to decide whether to move in the
	 * direction of the next or previous ToDoItems.
	 * 
	 * @param the ToDoItem being added to the list
	 * @return true if correctly inserted and false otherwise
	 */
	public boolean insert(ToDoItem toDoItem) {
		// the list is empty
		
		if (size == 0) {
			// set the head and the next for the head (null)
			head = toDoItem;
			System.out.println("H1" + head.getName());
			head.setNext(null);
			head.setPrev(null);
			// the current node is the toDoItem when there is only one item
			current = toDoItem;
			size++;
			return true;
		} else if (head.getNext() == null) {
			if (head.getSortNum() > toDoItem.getSortNum()) {
				tail = head;
				head = toDoItem;
				head.setNext(tail);
				head.setPrev(null);
				tail.setPrev(head);
				tail.setNext(null);
				head = toDoItem;
				size++;
				return true;
			} else {
				// case where the head is the only item in the list
				tail = toDoItem;
				head.setNext(tail);
				tail.setPrev(head);
				tail.setNext(null);
				current = head; // sets current for the first time
				size++;
				return true;
			}
		} else if (head.compareItems(toDoItem) == 1) {
			// case where the head is at a later date or priority than the item being
			// entered
			head.setPrev(toDoItem);
			head.getPrev().setNext(head);
			head.getPrev().setPrev(null);
			head = toDoItem;
			size++;
			return true;
		} else if (toDoItem.compareItems(tail) == 1 || toDoItem.compareItems(tail) == 0) { // check against tail
			// case where the ToDoItem is a further date and lower priority than the tail
			tail.setNext(toDoItem);
			tail.getNext().setPrev(tail);
			tail.getNext().setNext(null);
			tail = toDoItem;
			size++;
			return true;
		} else if (current.compareItems(toDoItem) == -1 || current.compareItems(toDoItem) == 0) {
			/*
			 * Case where the ToDoItem is greater than or equal to the current ToDoItem so
			 * search to the right until the correct location is found
			 */
			while (toDoItem.compareItems(current.getNext()) == 1) {
				current = current.getNext();
			}
			toDoItem.setNext(current.getNext());
			toDoItem.getNext().setPrev(toDoItem);
			current.setNext(toDoItem);
			current.getNext().setPrev(current);
			current = current.getNext();
			size++;
			return true;
		} else if (current.compareItems(toDoItem) == 1) {
			/*
			 * Case where the ToDoItem is less the current ToDoItem so search to the left
			 * until the correct location is found
			 */
			while (toDoItem.compareItems(current.getPrev()) == -1) {
				current = current.getPrev();
			}
			toDoItem.setPrev(current.getPrev());
			toDoItem.getPrev().setNext(toDoItem);
			current.setPrev(toDoItem);
			current.getPrev().setNext(current);
			current = current.getPrev();
			size++;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Retrieves a specified ToDoItem
	 * 
	 * @param the name of the item to be found
	 * @return the ToDoItem once found and null otherwise
	 */
	public ToDoItem get(String name) {
		ToDoItem placeHolder = head;
		for (int i = 0; i < size; i++) {
			if (placeHolder.getName().equals(name)) {
				return placeHolder;
			}
			placeHolder = placeHolder.getNext();
		}
		return null;
	}

	/**
	 * Checks if a requested to do item is in the list
	 * 
	 * @param the name of the ToDoItem
	 * @return true if found and false otherwise
	 */
	public boolean contains(String name) {
		ToDoItem placeHolder = head;
		for (int i = 0; i < size; i++) {
			if (placeHolder.getName().equals(name)) {
				return true;
			}
			placeHolder = placeHolder.getNext();
		}
		return false;
	}

	/**
	 * Removes a specified ToDoList item
	 * 
	 * @param the name of the ToDoItem to be removed
	 * @return true if the item is found and removed and false otherwise
	 */
	public boolean remove(String name) {
		if (head.equals(null)) { // list is empty
			return false;
		} else if (size == 1) { // only one item in the list
			if (head.getName().equals(name)) {
				clear();
				return true;
			}
		} else if (size == 2) { // if there are only two items in the list
			if (head.getName().equals(name)) { // head is being removed
				head = tail;
				tail = null;
				head.setNext(null);
				head.setPrev(null);
				size--;
				return true;
			} else if (tail.getName().equals(name)) { // tail is being removed
				head.setNext(null);
				tail = null;
				size--;
				return true;
			} else {
				return false;
			}
		} else if (head.getName().equals(name)) { // if the head is removed
			head.getNext().setPrev(null);
			head = head.getNext();
			size--;
			return true;
		} else if (tail.getName().equals(name)) { // if the tail is removed
			tail = tail.getPrev();
			tail.setNext(null);
			size--;
			return true;
		} else { // removes everything in between
			current = head;
			for (int i = 0; i < size; i++) {
				if (current.getName().equals(name)) {
					current.getPrev().setNext(current.getNext());
					current.getNext().setPrev(current.getPrev());
					current = current.getNext();
					size--;
					return true;
				}
				current = current.getNext();
			}
			return false;
		}
		return false;
	}

	/**
	 * Prints the name and order of the list, mainly for testing purposes
	 */
	public void printList() {
		ToDoItem printer = head;
		if (head == null) {
			System.out.println("List is empty");
		}
		for (int i = 0; i < size; i++) {
			System.out.println("To do " + (i + 1) + ". " + printer.getName());
			printer = printer.getNext();
		}
	}

	/**
	 * Clears the to-do list and resets its variables
	 */
	public void clear() {
		head = null;
		current = null;
		tail = null;
		size = 0;
	}

}
