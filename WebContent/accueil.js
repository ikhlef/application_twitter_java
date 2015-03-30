function init_env(){
	environnement = {};
	environnement.users = [];
	environnement.active=false;
	environnement.key="";
	environnement.timeline="all";// ou moi ou friends// afin de priciser la page a afficher , my tweet or all tweet 
}


function modfienvironnement(){
	environnement.active=!environnement.active;
	
}

function authentification(){
	if(environnement.active===true){
		$("#signin").css("display", "none");
		$("#accueil").css("display", "block");
		$("#moi").css("display", "block");
		$("#deconnection").css("display", "block");
		getstatut();
		espaceaccueil();
		suggestion();
		friends();
	}else{
		$("#signin").css("display", "block");
		$("#accueil").css("display", "none");
		$("#moi").css("display", "none");
		$("#deconnection").css("display", "none");
	}	
}

function initialisation(id, login, key){
	environnement.users[id] = new User(id, login, false);
	environnement.active=true;
	environnement.id=id;
	environnement.key=key;
	environnement.timeline="self";
	$("#moi").click(myspa); 
	$("#deconnection").click(deconnection);
	$("#accueil").click(espaceaccueil);
	$("#frien").click(friends);
	$("#suggest").click(suggestion);
}


