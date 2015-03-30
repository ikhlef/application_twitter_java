
function connect(form){
	var username = form.login.value;
	var password = form.mdp.value;
	
	if(verifier_login(username, password)){
	 connection(username, password);
	}	 
}

function verifier_login(username, password){
	if(!username || (username.length > 20 || username.length === 0)){
		creer_erreur("Username required");
		return false;
	}else if(!password || (password.length >20 || password.length === 0)){
		creer_erreur("Password required");
		return false;
	}
	return true;	
}
function creer_erreur(message){
  var balise='<p class="text-error" style="margin-left:15%;vertical-align:top; color:red;position:center;">'+message+'</p>';
		$("#form-sign1").prepend(balise);		
}



// fonction de verification pour l'enregistrement

function enregistre(form){
	var username = form.username.value;  //login
	var password = form.password.value; // password
	var firstname = form.firstname.value; // prenom
	var name = form.name.value;          // nom
	var mail = form.mail.value;           // mail

 if(verifier_enregistrement(username, password, firstname, name, mail)){
	 enregistrer(username, password, firstname, name, mail);
 }
}

function verifier_enregistrement(username, password, firstname, name, mail){

	 if(!firstname || firstname.length === 0 || !name || name.length === 0){
	    	creer_erreur_enregistrement("name and firstname required");
			return false;
	}else if(!username || (username.length > 20 || username.length === 0 )){
		creer_erreur_enregistrement("Username required");
		return false;
	}else if(!password || (password.length > 20 || password.length < 7)){
		creer_erreur_enregistrement("Password required");	
		return false;
	}else if(!validateEmail(mail)){
	        creer_erreur_enregistrement("maill not good");
			return false;
	}else{
	return true;
	}
	 return false;
}

function creer_erreur_enregistrement(message){
  var balise='<p  style="margin-left:15%;vertical-align:top; color:red;position:center;">'+message+'</p>';
		$("#form-sign2").prepend(balise);
}
// test mail avec une regulaire expression
function validateEmail(email) { 
     var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;   
	return re.test(email);
} 
function connection(username, password){
	$.ajax({
		type:"GET",
		url:"login",
		data:{login : username,
			   mdp: password
			   },
		dataType:"json",
		success:reponseconnection,
		error:"error connection"
	});
	
}
function enregistrer(username, password, firstname, name, mail){
	
	$.ajax({
		type:"GET",
		url:"createusers",
		data:{login : username,
			   mdp: password,
			   nom : name,
			   prenom: firstname, 
			   email : mail
			   },
		dataType:"json",
		success:function(data){
			if(data.error!==undefined){
				show_error_msg(data.error+" ("+data.code+")");	
			}else{
				connection(username,password);
			}	
		},
		error:"error enregistrement"
	});
	
}

function reponseconnection(data){
	
	if(data.error===undefined && data.login!=undefined){
		window.location.href="/ikhlef_blaszkiewicz/accueil.jsp?id="+data.id+"&login="+data.login+"&key="+data.key;
	}else{
		 creer_erreur("login or password does not exist ");
		}
}


function deconnection(){
	env.active=false;
	authentification();
}
