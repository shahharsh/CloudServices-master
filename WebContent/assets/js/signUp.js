function signUpfun(){
var fn = document.forms["signup"]["firstName"].value;
var ln = document.forms["signup"]["lastName"].value;
var email = document.forms["signup"]["email"].value;
var pass = document.forms["signup"]["passwd"].value;
var repass = document.forms["signup"]["repasswd"].value;

if(pass!=repass){
	document.getElementById("h6").innerHTML="Passwords don't match !";
}
else if(fn=="" || ln=="" || email=="" || pass==""){
	document.getElementById("h6").innerHTML="Enter all the fields to register !";
}
else{
	jQuery.ajax({
		type: "POST",
		url: "http://localhost:8080/CloudServices/rest/users/signup",
		data: formToJSON(),
		contentType: "application/json; charset=utf-8",
		dataType: "json",
		success: function (data, status, jqXHR) {
			alert("user created successfully "+formToJSON());
		},
		
		error: function (jqXHR, status) {            
			alert(jqXHR+" "+status);
		}
		});
	}
}

function formToJSON() {
    return JSON.stringify({
    "firstName": $('#firstName').val(),
    "lastName": $('#lastName').val(),
    "email": $('#email').val(),
    "passwd": $('#passwd').val(),
    });
}