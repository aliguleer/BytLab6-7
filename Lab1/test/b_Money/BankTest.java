package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {

		assertEquals("SweBank",SweBank.getName());
		assertEquals("Nordea",Nordea.getName());
		assertEquals("DanskeBank",DanskeBank.getName());

		assertNotEquals("Nordea",SweBank.getName());


	}

	@Test
	public void testGetCurrency() {

		assertEquals(SEK, SweBank.getCurrency());
		assertEquals(DKK, DanskeBank.getCurrency());

	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {

		SweBank.openAccount("Ali");

		DanskeBank.openAccount("Alis");

	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {

		SweBank.deposit("Bob",new Money(1000,SEK));

		long balance = SweBank.getBalance("Bob");
		assertEquals(1000,balance);


		try{

		SweBank.deposit("Ali",new Money(1000,SEK));

		long balancex = SweBank.getBalance("Ali");
		assertEquals(1000,balancex);

		}
		catch (AccountDoesNotExistException e ){
			System.out.println("Account does not exist");
		}


	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {

		// there was a bug on withdraw
		SweBank.deposit("Bob", new Money(10000, SEK));
		SweBank.withdraw("Bob", new Money(5000, SEK));
		long Balance = SweBank.getBalance("Bob");

		assertEquals(5000,Balance);


		try
		{
			SweBank.withdraw("alis", new Money(10000, SEK));

		}
		catch (AccountDoesNotExistException e)
		{
			System.out.println("Account does not exist");
		}


	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
		SweBank.deposit("Bob", new Money(10000, SEK));
		long balance = SweBank.getBalance("Bob");
		assertEquals(10000, balance);

		try
		{
			SweBank.getBalance("alis");
					}
		catch (AccountDoesNotExistException e)
		{
			System.out.println("Account does not exist");
		}
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
		long testblance;


		testblance = SweBank.getBalance("Ulrika");
		assertEquals(0, testblance);

		SweBank.deposit("Ulrika", new Money(10000, SEK));
		testblance = SweBank.getBalance("Ulrika");
		assertEquals(10000, testblance);

		//there was a bug on transfer
		SweBank.transfer("Ulrika", "Bob", new Money(5000, SEK));
		testblance = SweBank.getBalance("Ulrika");
		assertEquals(5000, testblance);

		testblance = SweBank.getBalance("Bob");
		assertEquals(5000, testblance);

		SweBank.transfer("Ulrika", DanskeBank, "Gertrud", new Money(5000, SEK));
		testblance = SweBank.getBalance("Ulrika");
		assertEquals(0, testblance);

		testblance = DanskeBank.getBalance("Gertrud");
		assertEquals(3750, testblance);


	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {

		SweBank.addTimedPayment("Bob", "rent", 30, 100, new Money(5000, SEK), DanskeBank, "Gertrud");
		SweBank.removeTimedPayment("Bob", "rent");

		try {
			SweBank.addTimedPayment("Alis", "rent", 30, 100, new Money(5000, SEK), DanskeBank, "Gertrud");
		}catch (AccountDoesNotExistException e){

			System.out.println("Acount does not exists");
		}


		try {
		SweBank.removeTimedPayment("Bobbbb","rent");
	}catch (AccountDoesNotExistException e){

		System.out.println("Acount does not exists");
	}

	}
}
