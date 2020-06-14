package org.tain.test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.tain.utils.Flag;

public class TestFileDemo {

	public static void main(String[] args) {
		if (Flag.flag) {
			//initialize Path object
			Path path = Paths.get("D:file.txt");
			//create file
			try {
				Path createdFilePath = Files.createFile(path);
				System.out.println("Created a file at : "+createdFilePath);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (Flag.flag) {
			Path sourceFile = Paths.get("D:file.txt");
			Path targetFile = Paths.get("D:fileCopy.txt");
			try {
				Files.copy(sourceFile, targetFile,
				StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException ex) {
				System.err.format("I/O Error when copying file");
			}
			Path wiki_path = Paths.get("D:fileCopy.txt");
			Charset charset = Charset.forName("ISO-8859-1");
			try {
				List<String> lines = Files.readAllLines(wiki_path, charset);
				for (String line : lines) {
					System.out.println(line);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
		if (Flag.flag) {
			Path wiki_path = Paths.get("D:file.txt");
			Charset charset = Charset.forName("ISO-8859-1");
			try {
				List<String> lines = Files.readAllLines(wiki_path, charset);
				for (String line : lines) {
					System.out.println(line);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
		
		if (Flag.flag) {
			Path path = Paths.get("D:file.txt");
			String question = "To be or not to be?";
			Charset charset = Charset.forName("ISO-8859-1");
			try {
				Files.write(path, question.getBytes());
				List<String> lines = Files.readAllLines(path, charset);
				for (String line : lines) {
					System.out.println(line);
				}
			} catch (IOException e) {
				System.out.println(e);
			}
		}
	}
}
