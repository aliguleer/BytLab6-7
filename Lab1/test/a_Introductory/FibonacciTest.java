package a_Introductory;

import static org.junit.Assert.*;

import org.junit.Test;

import a_Introductory.Fibonacci;

public class FibonacciTest {

	@Test
	public void testFib() {
		Fibonacci tester = new Fibonacci();
		assertEquals("0", 0, tester.fib(0));
		assertEquals("1", 1, tester.fib(1));
		assertEquals("2", 2, tester.fib(2));
		assertNotEquals("3", 2, tester.fib(3));
		assertEquals("4", 7, tester.fib(4));
		assertEquals("5", 12, tester.fib(5));
		assertEquals("6", 20, tester.fib(6));
		assertEquals("7", 33, tester.fib(7));
	}

}
