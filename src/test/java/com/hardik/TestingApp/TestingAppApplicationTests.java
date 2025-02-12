package com.hardik.TestingApp;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.assertj.core.data.Offset;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@Slf4j
//@SpringBootTest
class TestingAppApplicationTests {

	@BeforeEach
	void setUp(){
		log.info("Starting the method,setting up the config");
		log.info("https://assertj.github.io/doc/");
	}

	@BeforeAll
    static void setUpOnce(){
		log.info("installing it onces");
	}

	@AfterEach
	void setDown(){
		log.info("clossing the method and decommisioning the resources");
	}

	@AfterAll
    static void setDownOnce(){
		log.info("deleting it onces");
	}

	@Test
//	@Disabled
	void contextLoads() {
		log.info("contextload message is shown and run");
	}

	@Test
//	@DisplayName("displayTestNumber2")
	void testNumber2(){
		log.info("test one is run");
		int a=5, b=0;
		int result=addTwoNumber(a,b);
//		Assertions.assertEquals(8,result);
//		assertThat(result).isBetween(6,8).isCloseTo(10, Offset.offset(1));
		assertThat("Apple").isEqualTo("Apple").endsWith("le").startsWith("A");

	}
	@Test
	void testNumber3(){
		int a=3;
		int b=0;
//		assertThat(divideTwoNumber(a,b)).isCloseTo(1, Percentage.withPercentage(98));
		assertThatThrownBy(()->divideTwoNumber(a,b)).isInstanceOf(RuntimeException.class).hasMessage("uhh");

	}


	int addTwoNumber(int a,int b){
		return a+b;
	}

	double divideTwoNumber(int a,int b){
		try {
			return a/b;
		}catch (ArithmeticException ar){
			log.error("Arithmetic Excetption occured:"+ar.getLocalizedMessage());
			throw new ArithmeticException("uhh");
		}
	}

}
