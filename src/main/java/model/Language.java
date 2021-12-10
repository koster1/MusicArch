package model;

import java.io.FileInputStream;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

public class Language {
	private ResourceBundle bundle;
	private static Language INSTANCE = null;
	private Properties properties = new Properties();
	private Locale currentLocale;
	
	
	public static Language getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Language();
		}
		return INSTANCE;
	}
	
	private Language() {
		
		try {
			properties.load(new FileInputStream("src/main/resources/MusicArch.properties"));
			String language = properties.getProperty("language");
			String country = properties.getProperty("country");
			currentLocale = new Locale(language, country);
			Locale.setDefault(currentLocale);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {
			bundle = ResourceBundle.getBundle("TextResources");
			System.out.println("tämä on bundle testi " + bundle.getString("AlbumYearLabel"));
			
		} catch (Exception x) {
			
			System.out.println(x.getMessage());
		}
	}
	
	
//	public ResourceBundle getDefault() {
//		return this.bundle;
//	}
	
	public void setLocale(String language, String country) {
		try {
			Locale.setDefault(new Locale(language, country));
			bundle = ResourceBundle.getBundle("TextResources_" + language + "_" + country);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
			
	}
	
	public void setDefault(Locale language) {
		this.bundle = ResourceBundle.getBundle("TextResources_en_FI");

	}
	
	public ResourceBundle getBundle() {
		return this.bundle;
	}

}
