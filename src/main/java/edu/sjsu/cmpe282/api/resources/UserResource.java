package edu.sjsu.cmpe282.api.resources;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import edu.sjsu.cmpe282.domain.User;
import edu.sjsu.cmpe282.dto.AWSDao;
import edu.sjsu.cmpe282.dto.UserDao;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private UserDao userdao = new UserDao();
	
	@GET
	@Path("/products")
	public void FetchHome(){
		AWSDao awsdao = new AWSDao();
		awsdao.QueryProducts();
	}
	
	
	@GET
	@Path("/games")
	public void FetchGames(){
		AWSDao awsdao = new AWSDao();
		awsdao.QueryProducts("Games");
	}
	@GET
	@Path("/compntab")
	public void FetchCompAndTab(){
		AWSDao awsdao = new AWSDao();
		awsdao.QueryProducts("Computers and Tablets");
	}
	@GET
	@Path("/movnmus")
	public void FetchMovAndMus(){
		AWSDao awsdao = new AWSDao();
		awsdao.QueryProducts("Movies and Music");
	}
	
	@GET
	@Path("/product/{productid}")
	public void FetchProduct(@PathParam("productid") String productid){
		System.out.println("Fetching product details for productid : "+productid);
		AWSDao awsdao = new AWSDao();
		awsdao.describeProduct(productid);
	}
	
	@POST
	@Path("/signup")
	public User signUp(User user) throws ClassNotFoundException {
		System.out.print("user created: "+user.getFirstName());
		userdao.addUser(user);
		return user;//Response.status(201).entity("User Created : \n"+ user.getFirstName()).build();
	}
	
	
	@POST
	@Path("/signin")
	public boolean signIn(User user)
	{
		return userdao.checkUser(user);
	}
	
	
	
	
	
}
