package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {

		assertEquals("SEK",SEK.getName());
		assertEquals("DKK",DKK.getName());
		assertNotEquals("DKK",SEK.getName());
	}
	
	@Test
	public void testGetRate() {
		assertEquals(0.15, SEK.getRate(), 0.0001);

		assertEquals(0.20, DKK.getRate(), 0.0001);

		assertNotEquals(8, DKK.getRate(), 0.0001);
	}
	
	@Test
	public void testSetRate() {
		DKK.setRate(0.21);
		assertEquals(0.21, DKK.getRate(), 0.0001);
	}
	
	@Test
	public void testGlobalValue() {
		assertEquals((Integer)15, SEK.universalValue(100));

		assertEquals((Integer)20, DKK.universalValue(100));
	}
	
	@Test
	public void testValueInThisCurrency() {
		assertEquals(133 ,(int)SEK.valueInThisCurrency(100, DKK));

		int a =(int)SEK.valueInThisCurrency(100, EUR);

		assertEquals(1000 ,(int)SEK.valueInThisCurrency(100, EUR));
	}

}
