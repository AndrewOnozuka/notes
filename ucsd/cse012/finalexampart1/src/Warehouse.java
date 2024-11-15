// Import packages
import java.util.LinkedList;

/* 
 * The Warehouse class will implement the Inventory interface and will
 * have a constructor that takes Computer[] and int[] as arguments.
 * The Computer[] will contain all the available models that the
 * warehouse carries. The int[] will contain the amount of stock for
 * each model. Corresponding indices relate to each other.
 * 
 * Ex:
 * [Macbook, Windows, Linux]
 * [   2   ,    3   ,   4  ]
 * 2 Macbooks, 3 Windows, 4 Linux
 * 
 * 0: Rejected
 * When the customer attempts to make a new order that cannot be
 * completed (model is not available, duplicate ids, garbage input).
 * Additionally, orders may be rejected if they have already been made
 * before or not in the idle state.
 * 
 * 1: Idle
 * This is the default state of orders.
 * 
 * 2: Incomplete
 * This may happen when the order was successfully made, but could not
 * be fulfilled. This may happen if there is no longer any stock
 * available by the time this order is being processed.
 * 
 * 3: Complete
 * This may happen when the order was successfully made and the order
 * was successfully fulfilled.
 */
public class Warehouse implements Inventory{

	Computer[] models;			// Array of models (see above)
	int[] stock;				// Array of stock (see above)
	LinkedList<Order> orders; 	// List of orders

	// Constructor
	public Warehouse(Computer[] models, int[] stock) {
		this.models = models;
		this.stock = stock;
		LinkedList<Order> orders = new LinkedList<Order>();
	}
	
	/* 
	 * The warehouse will recognize that a new order has been made and
	 * will attempt to add the new order to a list of pending orders.
	 * If the order is null, already exists in the list or the id is a
	 * duplicate, reject the order (i.e. return false). In this case,
	 * do not modify the status or add it to the list of pending orders
	 * (i.e. nothing needs to be done with the order). If the order
	 * cannot be completed because the warehouse does not carry the
	 * requested model or the there is no more stock of the requested
	 * model, reject the order, set the status to 0 (rejected) and add
	 * it to the list of pending orders. If there are no issues with
	 * the order, then set the status of the order to 1 (idle) and add
	 * it to the list of pending orders. Return true only if there were
	 * no issues with the order and the order was added to the list of
	 * pending orders with a status code of 1, return false otherwise.
	 * Additionally set the order status appropriately if there were
	 * issues with the order.
	 */
	@Override
	public boolean makeNewOrder(Order o) {
		// checks if order is null
		if (o == null) { return false; }
		for (int i = 0; i < orders.size(); i++) {
			// checks if o already exists or if order id is a duplicate
			if (orders.get(i).equals(o) ||
				orders.get(i).getId().equals(o.getId())) { return false; }
		}
		
		// checks if model not carried or no stock left
		for (int i = 0; i < models.length; i++) {
			if (!models[i].getModel().equals(o.getProduct().getModel())
					|| stock[i] == 0) {
				o.setStatus(0);
				orders.addLast(o);
				return false;
			}
		}
		o.setStatus(1);
		orders.addLast(o);
		return true;
	}

	/* 
	 * The warehouse will return the status code for the particular
	 * Order with the corresponding id. This order may be in any of
	 * the four states. If the order corresponding with the id cannot
	 * be found or otherwise cannot be retrieved, return -1.
	 */
	@Override
	public int trackOrder(String id) {
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getId().equals(id)) {
				return orders.get(i).getStatus();
			}
		} return -1;
	}

	/* 
	 * The warehouse will attempt to fulfill the next idle order,
	 * following FIFO order. Return true if the order was successfully
	 * fulfilled,false otherwise. The status code of the order and
	 * inventory count should be updated appropriately. However, the
	 * order should not be removed from the list.
	 */
	@Override
	public boolean fulfillNextOrder() {
		for (int i = 0; i < orders.size(); i++) {
			// if order is idle
			if (orders.get(i).getStatus() == 1) {
				// check if order can be fulfilled
				for (int j = 0; j < models.length; j++) {
					// check if computer exists but has no stock left
					if (orders.get(i).getProduct().equals(models[j])
						&& stock[j] == 0) {
						orders.get(i).setStatus(2);
						return false;
					} // check if computer exists & has stock left
					if (orders.get(i).getProduct().equals(models[j])
						&& stock[j] != 0) {
						stock[j]--;
						orders.get(i).setStatus(3);
						return true;
					}
				}
			}
		} return false;
	}
}
