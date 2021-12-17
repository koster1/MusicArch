package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * The purpose of this class is to set the language and the country of the application for localization purposes.
 * This class will change the locale of the application during runtime.
 * 
 * This is used to provide the applications elements with the equivalent language package.
 * 
 * @author Kalle, Jani
 *
 */

public class Language {
	private ResourceBundle bundle;
	private static Language INSTANCE = null;
	private Properties properties = new Properties();
	private Locale currentLocale;
	
	/**
	 * Gets the Instance of the language.
	 * @return Returns the instance of the language.
	 */
	public static Language getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new Language();
		}
		return INSTANCE;
	}
	
	private Language() {
		try {
			FileInputStream configStream = new FileInputStream("src/main/resources/MusicArch.properties");
			properties.load(configStream);
			configStream.close();
			
			String language = properties.getProperty("language");
			String country = properties.getProperty("country");
			
			currentLocale = new Locale(language, country);
			Locale.setDefault(currentLocale);
			System.out.println(Locale.getDefault() + " default locale");
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
	
	/**
	 * Setting the country and the language of the application.
	 * @param language Language of the application.
	 * @param country Country of the application.
	 */
	
	public void setLocale(String language, String country) {
		try {
			Locale.setDefault(new Locale(language, country));
			bundle = ResourceBundle.getBundle("TextResources_" + language + "_" + country);
			}catch (Exception e) {
				System.out.println(e.getMessage());
			}
	}
	/**
	 * Returning the locale (language & country).
	 * @return Returns locale.
	 */
	public Locale getLocale() {
		return this.currentLocale;
	}
	
	/**
	 * Setting the default language to Finnish.
	 * @param The applications default language.
	 */
	
	public void setDefault(Locale language) {
		this.bundle = ResourceBundle.getBundle("TextResources_fi_FI");

	}
	/**
	 * ResourseBundle will return the locale's resource bundle.
	 * @return Bundle Locale's resource bundle
	 */
	public ResourceBundle getBundle() {
		return this.bundle;
	}
	/**
	 * Saving the locale in a way that the application remembers the users decision.
	 * 
	 * For example, if you set the language to English and close the application, the next time you
	 * open it, the user interface will be in English.
	 * 
	 * @param language Language of the application
	 * @param country Country of the application
	 */
	public void saveLocale(String language, String country) {
		FileInputStream configStream;
		try {
			configStream = new FileInputStream("src/main/resources/MusicArch.properties");
			properties.load(configStream);
			configStream.close();
			properties.setProperty("language", language);
			properties.setProperty("country", country);
			
			
			currentLocale = new Locale(language, country);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (FileOutputStream output = new FileOutputStream("src/main/resources/MusicArch.properties");
				ObjectOutputStream tiedosto = new ObjectOutputStream(output); )
			{
				properties.store(output, "Description to header");
				
			} catch (Exception ex) {
				System.out.println("saveLocale -> Fail");
			}
	}

}
