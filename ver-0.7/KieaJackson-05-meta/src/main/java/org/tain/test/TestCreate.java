package org.tain.test;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestCreate {

	public static void main(String[] args) throws Exception {
		/*
		Path path1 = Paths.get("src/main/resources/meta");
		Path path2 = Paths.get("src/main/resources/meta/file.txt");
		
		if (Files.notExists(path1)) {
			Files.createDirectories(path1);
		}
		
		if (Files.notExists(path2)) {
			Files.createFile(path2);
		}
		*/
		
		Path path3 = Paths.get("src/main/resources/meta"); // 디렉토리와 파일이 생성될 장소
		DirectoryStream<Path> directoryStream = Files.newDirectoryStream(path3);
		for(Path path : directoryStream) {
			if(Files.isDirectory(path)) {
				System.out.println("[[디렉토리]] " + path.getFileName());
			} else {
				System.out.println("[[파일]] " + path.getFileName() + " (크기:" + Files.size(path) + ") " + Files.getFileStore(path));
			}
		}
	}
}
