package edu.sjsu.cmpe282.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class AWSDao {
	static AmazonDynamoDBClient client;
	static String tableName = "Product_Catalog";
	
	// Constructor with AWS DynamoDB connection
	// Setup AWS Client to read DynamoDB data
	public AWSDao() {
		// TODO Auto-generated constructor stub
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
			System.out.println("Exception thrown in AWSDao() constructor while loading AWS credential file.");
		}
	}
	
	// This function prepares scanResult by accessing AWS DynamoDB.
	public void QueryProducts(String category){
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		  Condition condition = new Condition()
		  .withComparisonOperator(ComparisonOperator.EQ.toString())
		  .withAttributeValueList(new AttributeValue().withS(category));
		  scanFilter.put("category", condition);
		  ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
		  ScanResult scanResult = client.scan(scanRequest);
		  List<Product> products = new ArrayList<Product>();
		  /*System.out.println("item1 name : "+result.get(0).get("name").getS());
		  System.out.println("item1 price : "+result.get(0).get("price").getN());
		  System.out.println("item1 image : "+result.get(0).get("image").getS());
		  System.out.println("item1 category : "+result.get(0).get("category").getS());
		  System.out.println("item1 product_id : "+result.get(0).get("product_id").getN());*/
		  List<Map<String,AttributeValue>> result = scanResult.getItems();
		  for(int i=0;i<result.size();i++){
			  String name = result.get(i).get("name").getS();
			  String price = result.get(i).get("price").getN();
			  String url = result.get(i).get("image").getS();
			  String cat = result.get(i).get("category").getS();
			  String productid = result.get(i).get("product_id").getN();
			  products.add(new Product(name, price, url, cat, productid));
		  }
		  
		  Freemarker fm = new Freemarker();
		  fm.setCategoryTemplate();
		  fm.writeOutput(products,category);
		  
		  
	}
	// Method overloading...No argument method QueryProduct() to generate home-page without specifying category
	public void QueryProducts() {
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		  Condition condition = new Condition()
		  .withComparisonOperator(ComparisonOperator.EQ.toString())
		  .withAttributeValueList(new AttributeValue().withS("Games"));
		  scanFilter.put("category", condition);
		  ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
		  ScanResult scanResult = client.scan(scanRequest);
		  List<Product> products = new ArrayList<Product>();
		  /*System.out.println("item1 name : "+result.get(0).get("name").getS());
		  System.out.println("item1 price : "+result.get(0).get("price").getN());
		  System.out.println("item1 image : "+result.get(0).get("image").getS());
		  System.out.println("item1 category : "+result.get(0).get("category").getS());
		  System.out.println("item1 product_id : "+result.get(0).get("product_id").getN());*/
		  List<Map<String,AttributeValue>> result = scanResult.getItems();
		  for(int i=0;i<result.size();i++){
			  String name = result.get(i).get("name").getS();
			  String price = result.get(i).get("price").getN();
			  String url = result.get(i).get("image").getS();
			  String cat = result.get(i).get("category").getS();
			  String productid = result.get(i).get("product_id").getN();
			  products.add(new Product(name, price, url, cat, productid));
		  }
		  
		  Freemarker fm = new Freemarker();
		  fm.setCategoryTemplate();
		  fm.writeOutput(products,"Home");
		  
		
	}


	public void describeProduct(String productid) {
		HashMap<String, Condition> scanFilter = new HashMap<String, Condition>();
		  Condition condition = new Condition()
		  .withComparisonOperator(ComparisonOperator.EQ.toString())
		  .withAttributeValueList(new AttributeValue().withN(productid));
		  scanFilter.put("product_id", condition);
		  ScanRequest scanRequest = new ScanRequest(tableName).withScanFilter(scanFilter);
		  ScanResult scanResult = client.scan(scanRequest);
		  Map<String,String> productMap = new HashMap<String, String>();
		  List<Map<String,AttributeValue>> result = scanResult.getItems();
		  productMap.put("name", result.get(0).get("name").getS());
		  productMap.put("price", result.get(0).get("price").getN());
		  productMap.put("image", result.get(0).get("image").getS());
		  productMap.put("category", result.get(0).get("category").getS());
		  productMap.put("product_id", result.get(0).get("product_id").getN());
		  System.out.println(productMap);
		  
		  Freemarker fm = new Freemarker();
		  fm.setProductTemplate();
		  fm.writeOutput(productMap);
		  
	}
}
