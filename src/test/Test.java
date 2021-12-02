package test;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;

import main.Main;

public class Test {

	Main prog;
	
	@Before
	public void setUp() throws Exception {
		prog = new Main();
	}

	@After
	public void tearDown() throws Exception {
	}

	@org.junit.Test
	public void test_1() {
		File FILE = new File(System.getProperty("user.dir") + "/src/main/loo.txt");
		FILE.delete();
		
		prog.main();
	}
	
	@org.junit.Test
	public void test_2() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_3() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_4() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_5() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_6() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_7() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_8() {
		prog.main();
	}
	
	@org.junit.Test
	public void test_9() {
		prog.main();
	}

}
