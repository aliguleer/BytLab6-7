package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);

		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		assertEquals((Integer)10000, SEK100.getAmount());

		assertEquals((Integer)1000, EUR10.getAmount());

		assertNotEquals((Integer)8000, SEKn100.getAmount());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SEK100.getCurrency());

		assertEquals(EUR, EUR10.getCurrency());

		assertNotEquals(SEK, EUR10.getCurrency());
	}

	@Test
	public void testToString() {


		assertEquals("100.0 SEK", SEK100.toString());
		assertEquals("10.0 EUR", EUR10.toString());

		assertNotEquals("100.0 EUR", SEKn100.toString());

	}

	@Test
	public void testGlobalValue() {

		assertEquals((Integer)1500, SEK100.universalValue());

		assertEquals((Integer)1500, EUR10.universalValue());

		assertNotEquals((Integer)1200, SEKn100.universalValue());


	}

	@Test
	public void testEqualsMoney() {
		assertTrue(SEK200.equals(EUR20));
		assertTrue(SEK100.equals(EUR10));
		assertFalse(SEK100.equals(EUR20));
	}

	@Test
	public void testAdd() {

		assertTrue(SEK200.equals(SEK100.add(EUR10)));
		assertEquals(SEK200.getAmount(), SEK100.add(EUR10).getAmount());

		assertNotEquals(SEK200.getAmount(), SEK100.add(SEKn100).getAmount());

		assertEquals(SEK200.getCurrency(), SEK100.add(EUR10).getCurrency());

		assertEquals(SEK200.getCurrency(), SEK100.add(SEK200).getCurrency());

	}

	@Test
	public void testSub() {

		assertTrue(SEK100.equals(SEK200.sub(EUR10)));
		assertEquals(SEK100.getAmount(), SEK200.sub(EUR10).getAmount());
		assertNotEquals(SEK200.getAmount(), SEK200.sub(SEKn100).getAmount());

		assertEquals(SEK100.getCurrency(), SEK200.sub(EUR10).getCurrency());

		assertEquals(SEK100.getCurrency(), SEK200.sub(SEK200).getCurrency());
	}

	@Test
	public void testIsZero() {
		assertTrue(SEK0.isZero());
		assertFalse(SEK200.isZero());
	}

	@Test
	public void testNegate() {
		assertEquals(SEKn100.toString(), SEK100.negate().toString());

		assertEquals("-100.0 SEK", SEK100.negate().toString());
	}

	@Test
	public void testCompareTo() {
		assertTrue(SEK100.compareTo(SEKn100) > 0);
		assertTrue(EUR20.compareTo(SEK100) > 0);
		assertFalse(SEKn100.compareTo(SEK100) > 0);

		assertTrue(SEKn100.compareTo(SEK100) < 0);
		assertTrue(SEK100.compareTo(EUR20) < 0);
		assertFalse(SEK100.compareTo(SEKn100) < 0);

		assertTrue(SEK100.compareTo(SEK100) == 0);
		assertTrue(SEK100.compareTo(EUR10) == 0);
		assertFalse(SEK200.compareTo(EUR10) == 0);
	}
}
