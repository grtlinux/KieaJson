package org.tain.test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class TestPaths {

	public static void main(String[] args) throws Exception {
		Path path = Paths.get("/Users/kangmac/TEMP");
		System.out.println("------------------------------");
		System.out.println("filename : " + path.getFileName());
		System.out.println("parent dirname : " + path.getParent().getFileName());
		System.out.println("name count : " + path.getNameCount());
		
		System.out.println("------------------------------");
		for (int i=0; i < path.getNameCount(); i++) {
			System.out.println(">>>>> " + path.getName(i));
		}
		
		System.out.println("------------------------------");
		Iterator<Path> iter = path.iterator();
		while (iter.hasNext()) {
			Path temp = iter.next();
			System.out.println("filename : " + temp.getFileName());
		}
	}
}
