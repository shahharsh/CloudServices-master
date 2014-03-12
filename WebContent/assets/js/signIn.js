function LogInfun(){
	//alert("sign in clicked");
	var URL = "http://localhost:8080/CloudServices/rest/users/signin";
	//alert("signin button clicked"+formToJSON());
	var username = document.forms["login"]["userName"].value;
	var passwd = document.forms["login"]["passwd"].value;
	if((username=="" || username==null) && ( passwd=="" || passwd==null)){
		document.getElementById("h6").innerHTML="Please enter username and password !";
	}
	else if(passwd =="" || passwd == null){
		document.getElementById("h6").innerHTML="Please Enter Password !";
	}
	else if(username=="" || username== null){
		document.getElementById("h6").innerHTML="Please Enter Username !";
	}
	else{
	$.ajax({
			type: "POST",
			url: URL,
			contentType: "application/json",
			dataType: 'json',
			data : formToJSON(),
				//success: function () { //success(data); }
			success: function(data, textStatus, jqXHR){
				if(data){
					//alert("you are logged in");
					//window.location="/CloudServices/view/products.html";
					window.location="/CloudServices/rest/users/products";
				}
				else{
					document.getElementById("h6").innerHTML="Wrong username or password !";
				}
				},
			error: function(textStatus, jqXHR,errorThrown){
				alert(textStatus+" "+jqXHR);
			}

		});
}

function formToJSON() {
    return JSON.stringify({
    "email": $('#userName').val(),
    "passwd": $('#passwd').val(),
    });
}
}