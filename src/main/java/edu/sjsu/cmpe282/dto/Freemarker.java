package edu.sjsu.cmpe282.dto;

import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import edu.sjsu.cmpe282.domain.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;

public class Freemarker {
	
	private Template template;
	private Configuration cfg;
	public Freemarker() {
		/* 1. Configure FreeMarker
	    
	     You should do this ONLY ONCE, when your application starts,
	     then reuse the same Configuration object elsewhere.  */
		 cfg = new Configuration();
		    
		 // Where do we load the templates from:
		 cfg.setClassForTemplateLoading(AWS_Freemarker2.class, "templates");
		  
		 // Some other recommended settings:
		 cfg.setIncompatibleImprovements(new Version(2, 3, 20));
		 cfg.setDefaultEncoding("UTF-8");
		 cfg.setLocale(Locale.US);
		 cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		    
		
	}
	
	public void setCategoryTemplate(){
	//  Get the template to print all products of a category or home-page
			  System.out.println("Getting the category template ready...");
			  template=null;
				try {
					template = cfg.getTemplate("products_freemarker2.ftl");
					System.out.println("category template fetched...");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("Exception thrown while fetching freemarker template");
				}
			
	}
	
	public void setProductTemplate(){
	//  Get the template to print details of a product and a add-to-cart button
		  System.out.println("Getting the product template ready...");
		  template=null;
			try {
				template = cfg.getTemplate("product.ftl");
				System.out.println("product template fetched...");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Exception thrown while fetching freemarker template");
			}
	}
	
	public void writeOutput(List<Product> products, String category){
		Map<String, Object> input = new HashMap<String, Object>();
		String cat = category.replaceAll("\\s", "");
		String OUTPUT_FILE = "C:\\Program Files (x86)\\apache-tomcat-7.0.39\\webapps\\CloudServices\\view\\"+cat+".html";
		String BROWSER_OUTPUT="http://localhost:8080/CloudServices/view/"+cat+".html";
		input.put("itemslist", products);
		System.out.println("Input Ready");
		System.out.println("Writing output to the file...");
		
		System.out.println("Generating output...");
		
		/***
	    // Write output to the console
	    Writer consoleWriter = new OutputStreamWriter(System.out);
	    try {
			template.process(input, consoleWriter);
		} catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Template exception thrown");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Template exception thrown");
		}
		**/
	    Writer fileWriter=null;
		try {
			/** Trying to see current directory
			String current = new java.io.File( "." ).getCanonicalPath();
	        System.out.println("Current dir:"+current);
	        String currentDir = System.getProperty("user.dir");
	        System.out.println("Current dir using System:" +currentDir);
	        **/
			fileWriter = new FileWriter(new File(OUTPUT_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrown while writing to html output file.");
		}
	    try {
	      template.process(input, fileWriter);
	    } catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Template exception thrown while processing the template");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException thrown while processing template");
		} finally {
	      try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException thrown while closing file writer.");
		}
	    }
	    System.out.println("HTML file written!");
	 // Create Desktop object
	    Desktop d=Desktop.getDesktop();

	    // Browse a URL, say google.com
	    try {
			d.browse(new URI(BROWSER_OUTPUT));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrown while showing home page in browser.");
		}

	    
	  
	}

	public void writeOutput(Map<String, String> productMap) {
		// TODO Auto-generated method stub
		Map<String, Object> input = new HashMap<String, Object>();
		String OUTPUT_FILE = "C:\\Program Files (x86)\\apache-tomcat-7.0.39\\webapps\\CloudServices\\view\\Product.html";
		String BROWSER_OUTPUT="http://localhost:8080/CloudServices/view/Product.html";
		input.put("product", productMap);
		System.out.println("Input Ready");
		System.out.println("Writing output to the file...");
		System.out.println("Generating output...");
		Writer fileWriter=null;
		try {
			/** Trying to see current directory
			String current = new java.io.File( "." ).getCanonicalPath();
	        System.out.println("Current dir:"+current);
	        String currentDir = System.getProperty("user.dir");
	        System.out.println("Current dir using System:" +currentDir);
	        **/
			fileWriter = new FileWriter(new File(OUTPUT_FILE));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrown while writing to html output file.");
		}
	    try {
	      template.process(input, fileWriter);
	    } catch (TemplateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Template exception thrown while processing the template");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException thrown while processing template");
		} finally {
	      try {
			fileWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("IOException thrown while closing file writer.");
		}
	    }
	    System.out.println("HTML file written!");
	 // Create Desktop object
	    Desktop d=Desktop.getDesktop();

	    // Browse a URL, say google.com
	    try {
			d.browse(new URI(BROWSER_OUTPUT));
		} catch (IOException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrown while showing home page in browser.");
		}

	    
		
		
	}

}
