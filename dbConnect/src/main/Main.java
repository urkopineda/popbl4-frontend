package main;

import graphicinterface.LogInUI;
import language.XMLParser;

public class Main {
	@SuppressWarnings("unused")
	public static void main(String args[]) {
		XMLParser.getLanguages();
		LogInUI login = new LogInUI();
	}
}
