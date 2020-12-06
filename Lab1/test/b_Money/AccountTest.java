package b_Money;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class AccountTest {
	Currency SEK, DKK;
	Bank Nordea;
	Bank DanskeBank;
	Bank SweBank;
	Account testAccount;
	
	@Before
	public void setUp() throws Exception {

		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);

		SweBank = new Bank("SweBank", SEK);
		SweBank.openAccount("Alice");
		testAccount = new Account("Hans", SEK);
		testAccount.deposit(new Money(10000000, SEK));

		SweBank.deposit("Alice", new Money(1000000, SEK));
	}
	
	@Test
	public void testAddRemoveTimedPayment() {

		assertFalse(testAccount.timedPaymentExists("expensea"));

		testAccount.addTimedPayment("expensea",12,0,new Money(2000,SEK),SweBank,"Alice");
		assertTrue(testAccount.timedPaymentExists("expensea"));

		testAccount.removeTimedPayment("expensea");
		assertFalse(testAccount.timedPaymentExists("expensea"));

	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		long a;

		a=testAccount.getBalance().getAmount();

		assertEquals(10000000,a);

		a=SweBank.getBalance("Alice");

		assertEquals(1000000, a);

		testAccount.addTimedPayment("test", 0, 0, new Money(10000, SEK), SweBank, "Alice");

		for (int i = 0; i < 10; ++i)
		{
			testAccount.tick();
		}

		a = testAccount.getBalance().getAmount();

		// there is bug here double tick
		assertEquals(10000000 - 100000, a);

		a = SweBank.getBalance("Alice");
		assertEquals(1000000 + 100000, a);

		//System.out.println(a);

		testAccount.addTimedPayment("test", 0, 0, new Money(100000, SEK), SweBank, "Blabla");

		a = testAccount.getBalance().getAmount();

		//System.out.println(a);

		assertEquals(10000000 - 100000, a);



	}

	@Test
	public void testAddWithdraw() {

		long mny;

		mny=testAccount.getBalance().getAmount();
		assertEquals(10000000,mny);

		testAccount.deposit(new Money(10000,SEK));
		mny=testAccount.getBalance().getAmount();
		assertEquals(10000000+10000,mny);

		testAccount.withdraw(new Money(10000,SEK));
		mny=testAccount.getBalance().getAmount();
		assertEquals(10000000,mny);

		testAccount.withdraw(new Money(10000000,SEK));
		mny=testAccount.getBalance().getAmount();
		assertEquals(0,mny);

		testAccount.deposit(new Money(10000, DKK));
		mny = testAccount.getBalance().getAmount();

		assertEquals(13333, mny);

	}
	
	@Test
	public void testGetBalance() {
		assertTrue(
				(new Money(10000000, SEK)).equals(testAccount.getBalance())
				);

		assertFalse(
				(new Money(9999999, SEK)).equals(testAccount.getBalance())
		);


	}
}
