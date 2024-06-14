package com.example.quiz_9;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Quiz9ApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void test3() {
		List<String> list = List.of("A", "B", "C", "D", "E");
		String str = "AABBBCDDAEEEAACCDD";
		Map<String,Integer> map = new HashMap<>();
		for (String item : list) {
			String newStr = str.replace(item, "");
			int count = str.length() - newStr.length();
			map.put(item, count);
		}
		System.out.println(map);
	}
	
	@Test
	public void test4() {
		String str = "ºñ¯ù";
		String str1 = "ºñ¯ù;¬õ¯ù";
	}
}
