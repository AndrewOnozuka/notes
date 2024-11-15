package cse12pa1student;

import java.util.Collection;
import java.util.Arrays;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class BasketTest {

	public static Collection<Object[]> BAGNUMS =
			Arrays.asList(new Object[][] { {0}, {1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}, {11}, {12} });
	private int bagType;

	public BasketTest(int bagType) {
		super();
		this.bagType = bagType;
	}

	@Parameterized.Parameters(name = "Basket{index}")
	public static Collection<Object[]> bags() {
		return BAGNUMS;
	}
	
	private Basket makeBasket() {
		switch(this.bagType) {
			case 0: return new Basket0();
			case 1: return new Basket1();
			case 2: return new Basket2();
			case 3: return new Basket3();
			case 4: return new Basket4();
			case 5: return new Basket5();
			case 6: return new Basket6();
			case 7: return new Basket7();
			case 8: return new Basket8();
			case 9: return new Basket9();
			case 10: return new Basket10();
			case 11: return new Basket11();
			case 12: return new Basket12();
		}
		return null;
	}
	
	@Test // Basket 0 failed this test.
	public void addedHasCount1() { // Adding 1 to empty basket
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		assertEquals(1, basketToTest.count());
	}
	@Test // Basket 0 failed this test.
	public void addedHasCount2() { // Adding duplicate to empty basket
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		assertEquals(2, basketToTest.count());
	}
	@Test // Basket 0, 4 failed this test.
	public void addedHasCount11() { // Adding 11 items to empty basket
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		assertEquals(11, basketToTest.count());
	}
	@Test // Basket 0 failed this test.
	public void addedUnique4() { // Adding 4 items to empty basket
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		Item j = new Item("Conditioner", 4);
		basketToTest.addToBasket(j);
		Item k = new Item("Shoes", 100);
		basketToTest.addToBasket(k);
		Item l = new Item("Jeans", 60);
		basketToTest.addToBasket(l);
		assertEquals(4, basketToTest.count());
	}
	@Test // No baskets failed this test.
	public void checkEmpty() { // Check if basket is empty
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.empty();
		assertEquals(0, basketToTest.count());
	}
	@Test // 
	public void checkEmpty2() { // Check if basket has 1 after empty()
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.empty();
		basketToTest.addToBasket(i);
		assertEquals(1, basketToTest.count());
	}
	@Test // Basket 2, 4 failed.
	public void checkRemove0() { // Check if basket can remove null
		Basket basketToTest = makeBasket();

		assertEquals(false, basketToTest.removeFromBasket(null));
	}
	@Test // Basket 0, 2, 4, 9 failed.
	public void checkAddNull() { // Check if basket can add null
		Basket basketToTest = makeBasket();

		basketToTest.addToBasket(null);
		assertEquals(1, basketToTest.count());
	}
	@Test // Basket 0, 2, 3, 4, 6, 9 failed.
	public void checkAddRemoveNull() { // Check if basket can add and remove null
		Basket basketToTest = makeBasket();

		basketToTest.addToBasket(null);
		basketToTest.addToBasket(null);
		basketToTest.removeFromBasket(null);
		assertEquals(1, basketToTest.count());
	}
	@Test // Basket 0 failed this test.
	public void checkCountRemove1() { // Check if basket can remove 1
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.removeFromBasket(i);
		assertEquals(1, basketToTest.count());
	}
	@Test // Basket 0 failed this test.
	public void checkRemove1() { // Check if basket can remove 1
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.removeFromBasket(i);
		assertEquals(true, basketToTest.removeFromBasket(i));
	}
	@Test // All tests passed.
	public void checkRemoveAll0() { // Check if basket can remove all
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.removeAllFromBasket(i);
		assertEquals(false, basketToTest.removeAllFromBasket(i));
	}
	@Test // Baskets 3, 5, 10 all failed this test.
	public void checkCountRemoveAll() { // Check if basket count is 0 after removeAll
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		basketToTest.removeAllFromBasket(i);
		assertEquals(0, basketToTest.count());
	}
	@Test // Baskets 0, 8, 12 all failed this test.
	public void checkRemoveAll() { // Check if basket can remove all
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		assertEquals(true, basketToTest.removeAllFromBasket(i));
	}
	@Test // Baskets 0, 3, 5, 10, 12 all failed this test.
	public void checkRemoveAll2() { // Check if basket can remove all while keeping 1
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		Item j = new Item("Conditioner", 4);
		basketToTest.addToBasket(j);
		basketToTest.removeAllFromBasket(i);
		assertEquals(1, basketToTest.count());
	}
	@Test // Baskets 5, 11 failed.
	public void checkCountItem0() { // Check if count is 0
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		Item j = new Item("Conditioner", 4);
		basketToTest.addToBasket(j);
		Item k = new Item("Shoes", 100);
		basketToTest.addToBasket(k);
		Item l = new Item("Jeans", 60);
		basketToTest.addToBasket(l);
		assertEquals(0, basketToTest.countItem(i));
	}
	@Test // Baskets 0, 3 failed.
	public void checkCountItem2() { // Check if count is 2
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		Item j = new Item("Conditioner", 4);
		basketToTest.addToBasket(j);
		Item k = new Item("Shoes", 100);
		basketToTest.addToBasket(k);
		Item l = new Item("Jeans", 60);
		basketToTest.addToBasket(l);
		assertEquals(2, basketToTest.countItem(i));
	}
	@Test // Baskets 0, 3, 4, 11 failed.
	public void checkTotalCost() { // Check if cost is 174
		Basket basketToTest = makeBasket();

		Item i = new Item("Shampoo", 5);
		basketToTest.addToBasket(i);
		basketToTest.addToBasket(i);
		Item j = new Item("Conditioner", 4);
		basketToTest.addToBasket(j);
		Item k = new Item("Shoes", 100);
		basketToTest.addToBasket(k);
		Item l = new Item("Jeans", 60);
		basketToTest.addToBasket(l);
		assertEquals(174, basketToTest.totalCost());
	}
	@Test // Basket 0, 3, 4 failed.
	public void checkNegTotalCost() { // Check if cost is negative
		Basket basketToTest = makeBasket();

		Item i = new Item("Debt", -1000);
		basketToTest.addToBasket(i);
		assertEquals(-1000, basketToTest.totalCost());
	}
	@Test // Basket 1 failed.
	public void checkNullTotalCost() { // Check adding null and finding total cost of null.
		Basket basketToTest = makeBasket();

		Item i = null;
		basketToTest.addToBasket(i);
		assertEquals(null, basketToTest.totalCost());
	}
	@Test // Basket 0, 3, 4, 7 failed.
	public void checkNullNegTotalCost() { // Check if cost is negative with null.
		Basket basketToTest = makeBasket();

		Item i = new Item(null, 5);
		basketToTest.addToBasket(i);
		Item j = new Item("Debt", -1000);
		basketToTest.addToBasket(j);
		assertEquals(-995, basketToTest.totalCost());
	}

}
