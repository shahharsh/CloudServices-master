package edu.sjsu.cmpe282.dto;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.PropertiesCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodb.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodb.model.AttributeValue;
import com.amazonaws.services.dynamodb.model.ComparisonOperator;
import com.amazonaws.services.dynamodb.model.Condition;
import com.amazonaws.services.dynamodb.model.ScanRequest;
import com.amazonaws.services.dynamodb.model.ScanResult;




import edu.sjsu.cmpe282.domain.Product;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;


public class AWS_Freemarker2 {
	static AmazonDynamoDBClient client;
	static String tableName = "Product_Catalog";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// 1. Configure FreeMarker
	    //
	    // You should do this ONLY ONCE, when your application starts,
	    // then reuse the same Configuration object elsewhere.
	    
	    Configuration cfg = new Configuration();
	    
	    // Where do we load the templates from:
	    cfg.setClassForTemplateLoading(AWS_Freemarker2.class, "templates");
	    
	    // Some other recommended settings:
	    cfg.setIncompatibleImprovements(new Version(2, 3, 20));
	    cfg.setDefaultEncoding("UTF-8");
	    cfg.setLocale(Locale.US);
	    cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
	    
	  // Setup AWS Client to read DynamoDB data
	  createAWSClient();
	    
	  //Scan for items from aws dynamodb
	  HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
	  Condition condition = new Condition()
	  .withComparisonOperator(ComparisonOperator.LE.toString())
	  .withAttributeValueList(new AttributeValue().withN("200"));
	  scanFilter.put("price", condition);
	  ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
	  ScanResult scanResult = client.scan(scanRequest);
	  System.out.println("Result of scan : "+scanResult);
	  System.out.println("No. of items in scan : "+scanResult.getCount());
	  List<Map<String,AttributeValue>> result = scanResult.getItems();
	  
	  Map<String, Object> input = new HashMap<String, Object>();
	  List<Product> products = new ArrayList<Product>();
	  /*System.out.println("item1 name : "+result.get(0).get("name").getS());
	  System.out.println("item1 price : "+result.get(0).get("price").getN());
	  System.out.println("item1 image : "+result.get(0).get("image").getS());
	  System.out.println("item1 category : "+result.get(0).get("category").getS());
	  System.out.println("item1 product_id : "+result.get(0).get("product_id").getN());*/
	  
	  for(int i=0;i<result.size();i++){
		  String name = result.get(i).get("name").getS();
		  String price = result.get(i).get("price").getN();
		  String url = result.get(i).get("image").getS();
		  String category = result.get(i).get("category").getS();
		  String productid = result.get(i).get("product_id").getN();
		  products.add(new Product(name, price, url, category, productid));
	  }
	  input.put("itemslist", products);
	  System.out.println("Input Ready");
	//  Get the template
	  System.out.println("Getting the template ready...");
	    Template template=null;
		try {
			template = cfg.getTemplate("products_freemarker2.ftl");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrown while fetching freemarker template");
		}
	      
	    // 2.3. Generate the output
		System.out.println("Generating output...");
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

	    // For the sake of example, also write output into a file:
	    System.out.println("Writing output to the file...");
	    Writer fileWriter=null;
		try {
			fileWriter = new FileWriter(new File("output1.html"));
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
	    System.out.println("Done!");
	  

	}
	private static void createAWSClient() {
		// TODO Auto-generated method stub
		try {
			AWSCredentials credentials = new PropertiesCredentials(AWS_Freemarker2.class.getResourceAsStream("templates/AwsCredentials.properties"));
			System.out.println("Properties file loaded.");
			System.out.println("Access Key : "+credentials.getAWSAccessKeyId());
			System.out.println("Secret Access Key : "+credentials.getAWSSecretKey());
			client = new AmazonDynamoDBClient(credentials);
			client.setRegion(Region.getRegion(Regions.US_WEST_1));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Exception thrown in loading AWS credential file.");
		}
		
	}

}
