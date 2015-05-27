package main;

import language.XMLParser;
import file.FileUtils;
import graphicinterface.LogInUI;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		FileUtils.readConfFile();
		XMLParser.getLanguages();
		LogInUI login = new LogInUI();
	}
}
