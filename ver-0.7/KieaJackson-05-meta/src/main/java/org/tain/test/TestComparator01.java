package org.tain.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestComparator01 {

	public static void main (String[] args) throws java.lang.Exception
	{
		List<Student1> list = new ArrayList<>();

		list.add(new Student1("a", 5));
		list.add(new Student1("b", 10));
		list.add(new Student1("c", 1));
		list.add(new Student1("d", 52));
		list.add(new Student1("e", 23));

		Collections.sort(list);

		for (Student1 s : list) {
			System.out.println(s.getScore());
		}
		/*
		 * 결과
		 * 1
		 * 5
		 * 10
		 * 23
		 * 52
		 */
	}
}

class Student1 implements Comparable<Student1> {
	String name;
	int score;

	public Student1(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return this.name;
	}

	public int getScore() {
		return this.score;
	}

	@Override
	public int compareTo(Student1 s) {
		if (this.score < s.getScore()) {
			return -1;
		} else if (this.score > s.getScore()) {
			return 1;
		}
		return 0;
	}
}
