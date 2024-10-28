public interface Inventory {
	boolean makeNewOrder(Order o);
	int trackOrder(String id);
	boolean fulfillNextOrder();
}

// Don't have to implement since on autograder, reference only

interface Computer {
    // Returns the model name of the computer as a string
    String getModel();
}

class Order {
    // Returns the Id of this order
    String getId();

    // Returns the status code of this order
    int getStatus();

    // Sets the status code of this order
    void setStatus(int status);

    // Returns the Computer of this order
    Computer getProduct();
}
