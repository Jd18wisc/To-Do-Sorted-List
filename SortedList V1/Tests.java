import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Class that tests that ToDoItems and SortedLists are created correctly and that all functions
 * work as intended
 * 
 * @author Jacob
 */
class Tests {
	
	// a set of ToDoItem variables to use across functions to avoid constantly creating objects
	public ToDoItem toDoItem1 = new ToDoItem("Item 1", "2/6/21", 1, "Body text 1");
	public ToDoItem toDoItem2 = new ToDoItem("Item 2", "2/8/21", 2, "Body text 2");
	public ToDoItem toDoItem3 = new ToDoItem("Item 3", "2/21/21", 2, "Body text 3");
	public ToDoItem toDoItem4 = new ToDoItem("Item 4", "3/26/21", 2, "Body text 4");
	public ToDoItem toDoItem5 = new ToDoItem("Item 5", "4/8/21", 3, "Body text 5");
	public ToDoItem toDoItem6 = new ToDoItem("Item 6", "4/16/21", 3, "Body text 6");

	/**
	 * Tests that ToDoItem's are created correctly and that the correct values are
	 * stored in each variable
	 */
	@Test
	void testCreateToDoItem() {
		ToDoItem newItem = new ToDoItem("Item 1", "2/6/21", 1, "Body text 1");
		String correctInfo = "Name: Item 1\nDue Date: 2/6/21\nPriority: High\nNotes: Body text 1\nLate: Yes\nItem Complete: No";

		if (!newItem.getAllInfo().equals(correctInfo)) {
			fail("Error creating ToDoItem");
		}

	}

	/**
	 * Tests that all editable values of the ToDoItem can be changed and retrieved
	 */
	@Test
	void testChangeItems() {
		ToDoItem newItem = new ToDoItem("Item 1", "2/6/21", 1, "Body text 1");
		String correctInfo = "Name: Item 2\nDue Date: 3/16/21\nPriority: Medium\nNotes: New notes\nLate: Yes\nItem Complete: Yes";
		newItem.setName("Item 2");
		newItem.setDueDate("3/16/21");
		newItem.setPriority(2);
		newItem.setNotes("New notes");
		newItem.setIsLate(true);
		newItem.setIsDone(true);

		if (!newItem.getAllInfo().equals(correctInfo)) {
			fail("Error changing ToDoItem values");
		}
	}

	/**
	 * Tests that an instance of the SortedList is created and that the correct
	 * starting values are stored in it
	 */
	@Test
	void testCreateList() {
		SortedList list = new SortedList("New List");

		if (!list.getListName().equals("New List")) {
			fail("List name does not match input");
		}
		if (list.getSize() != 0) {
			fail("List size incorrect");
		}
		if (list.getHead() != null) {
			fail("Head was set incorrectly");
		}
	}

	/**
	 * Tests that items are correctly added to the to-do list in their sorted
	 * position
	 */
	@Test
	void testAddToDoItem() {
		SortedList list = new SortedList("List");
		// case 1: insert one ToDoItem
		list.insert(toDoItem4);
		if (!list.getHead().getName().equals("Item 4")) { // check the head is set correctly
			fail("Head set incorrectly for: " + toDoItem4.getName() + ", should be \"Item 4\"");
		}
		if (list.getSize() != 1) { // check that size is incremented
			fail("List size is incorrect: should be 1");
		}

		// case 2: second item is insert
		list.insert(toDoItem1);
		if (list.getSize() != 2) { // check size incremented correctly
			fail("Size incorrect: should be 2");
		}
		if (!list.contains("Item 1")) { // check the item is in the list
			fail("Item 1 is not in the list");
		}
		if (!list.getHead().getName().equals("Item 1")) { // check the position of Item 1
			fail("Head set incorrectly: should be \"Item 1\"");
		}
		// check that the current pointer is in the right location
		if (!list.getCurrent().getName().equals("Item 4")) {
			fail("Current set incorrectly: should be \"Item 4\"");
		}
		// check that tail is set correctly
		if (!list.getTail().getName().equals("Item 4")) {
			fail("Tail set incorrectly: should be \"Item 4\"");
		}

		// case 3: third item is inserted
		list.insert(toDoItem5);
		if (list.getSize() != 3) {
			fail("Size incorrect: should be 3");
		}
		if (!list.contains("Item 5")) {
			fail("Item 5 is not in the list");
		}
		if (!list.getHead().getName().equals("Item 1")) { // check for correct position
			fail("Head set incorrectly: should be \"Item 1\"");
		}
		if (!list.getTail().getName().equals("Item 5")) { // check for correct position
			fail("Head set incorrectly: should be \"Item 5\"");
		}

		// insert several more and test to make sure they are ordered correctly
		list.insert(toDoItem2);
		list.insert(toDoItem6);
		list.insert(toDoItem3);

		ToDoItem tempHead = list.getHead();
		String currList = "|";
		String correctList = "|Item 1|Item 2|Item 3|Item 4|Item 5|Item 6|";

		while (tempHead != null) {
			currList += tempHead.getName() + "|";
			tempHead = tempHead.getNext();
		}

		if (!currList.equals(correctList)) {
			fail("The list was ordered incorrectly after inserting all items");
		}
	}

	/**
	 * Tests that items are deleted from the list correctly and that the rest of the
	 * list updates accordingly
	 */
	@Test
	void testDeleteToDoItem() {
		SortedList list = new SortedList("List");

		// case 1: deleting one item from a one-item list
		list.insert(toDoItem1);
		list.remove("Item 1");
		if (list.getSize() != 0 || list.getHead() != null) {
			fail("Removed a single item from a size 1 list incorrectly");
		}

		// case 2: deleting one item from a two-item list
		list.insert(toDoItem1);
		list.insert(toDoItem2);
		list.remove("Item 2");
		if (list.getSize() != 1) {
			fail("Incorrect size: should be 1");
		}
		if (!list.getHead().getName().equals("Item 1")) {
			fail("Head set incorrectly: should be \"Item 1\"");
		}
		if (list.getHead().getNext() != null) {
			fail("The next item for head was set incorrectly");
		}

		list.clear();

		// case 3: adding several items and removing multiple
		list.insert(toDoItem1);
		list.insert(toDoItem2);
		list.insert(toDoItem3);
		list.insert(toDoItem4);
		list.insert(toDoItem5);
		list.insert(toDoItem6);
		list.remove("Item 1");
		list.remove("Item 6");
		list.remove("Item 4");

		ToDoItem tempHead = list.getHead();
		String currList = "|";
		String correctList = "|Item 2|Item 3|Item 5|";

		while (tempHead != null) {
			currList += tempHead.getName() + "|";
			tempHead = tempHead.getNext();
		}
		if (!currList.equals(correctList)) {
			fail("The list was ordered incorrectly after inserting and deleting the case 3 items");
		}
	}

	/**
	 * Tests that the function to retrieve a ToDoItem from the list works correctly
	 */
	@Test
	void testGet() {
		SortedList list = new SortedList("List");
		list.insert(toDoItem1);
		list.insert(toDoItem2);
		list.insert(toDoItem3);
		list.insert(toDoItem4);

		if (!list.get("Item 1").getName().equals("Item 1")) {
			fail("Failed to find \"Item 1\"");
		}
		if (!list.get("Item 2").getName().equals("Item 2")) {
			fail("Failed to find \"Item 2\"");
		}
		if (!list.get("Item 3").getName().equals("Item 3")) {
			fail("Failed to find \"Item 3\"");
		}
		if (!list.get("Item 4").getName().equals("Item 4")) {
			fail("Failed to find \"Item 4\"");
		}
	}

	/**
	 * Tests that the function to check if an item is in the list works correctly.
	 */
	@Test
	void testContains() {
		SortedList list = new SortedList("List");
		list.insert(toDoItem1);
		list.insert(toDoItem2);
		list.insert(toDoItem3);
		list.insert(toDoItem4);

		if (list.contains("Item 1") != true) {
			fail("Failed to find \"Item 1\"");
		}
		if (list.contains("Item 2") != true) {
			fail("Failed to find \"Item 2\"");
		}
		if (list.contains("Item 3") != true) {
			fail("Failed to find \"Item 3\"");
		}
		if (list.contains("Item 4") != true) {
			fail("Failed to find \"Item 4\"");
		}
	}

	/**
	 * Tests that the clear function clears the list correctly
	 */
	@Test
	void testClear() {
		SortedList list = new SortedList("List");
		list.insert(toDoItem1);
		list.insert(toDoItem2);
		list.insert(toDoItem3);
		list.insert(toDoItem4);

		list.clear();
		if (list.getSize() != 0 || list.getHead() != null || list.getTail() != null) {
			fail("Clear failed");
		}
	}

	/**
	 * Tests that the function to check if a list item is late works correctly
	 */
	@Test
	void testSetIsLate() {
		SortedList list = new SortedList("List");
		ToDoItem newItem1 = new ToDoItem("Item 1", "2/6/21", 1, "Body text 1"); // late (written on 2/9/21)
		ToDoItem newItem2 = new ToDoItem("Item 2", "2/6/22", 1, "Body text 1"); // not late

		if (newItem1.getIsLate() != true) {
			fail("Is late is wrong1");
		}
		if (newItem2.getIsLate() != false) {
			fail("Is late is wrong2");
		}
	}

	/**
	 * Tests that the sort number for items are created properly
	 */
	@Test
	void testYearSort() {
		SortedList list = new SortedList("List");
		ToDoItem newItem1 = new ToDoItem("Item 1", "2/6/20", 1, "Body text 1");
		ToDoItem newItem2 = new ToDoItem("Item 2", "2/6/21", 1, "Body text 2");
		ToDoItem newItem3 = new ToDoItem("Item 3", "1/26/21", 1, "Body text 3");
		list.insert(newItem1);
		list.insert(newItem2);
		list.insert(newItem3);

		if (!list.getHead().getName().equals("Item 1")) {
			fail("Year");
		}
		if (!list.getHead().getNext().getName().equals("Item 3")) {
			fail("Year");
		}
		if (!list.getTail().getName().equals("Item 2")) {
			fail("Year");
		}
	}
}