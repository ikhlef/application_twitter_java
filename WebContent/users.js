
//objet User
function User(id, login,contact){
	this.id = id;
	this.login = login;
	 if(contact === true){
		this.contact = true;
	}else{
		this.contact = false;
}
//initialisation de la variable d'environnement	
	if(environnement === undefined){
		environnement = {};
	}
//initialisation de la variable users, contenant tous les utilisateurs	
	if(environnement.users === undefined){
		environnement.users=[];
	}
// 	sauvgarde de user  dans le tableau users � l'indice correspondant a son id
	environnement.users[id]=this;
}

// la fonction qui modifie la valeur de la variable contact, (utilisation du prototypage)
User.prototype.modifieStatus = function(){
	this.contact = !this.contact;
};

//// incrementer le nombre de tweets
function plusTweets(){
	var twet=$("#tweetnbre").html();
	var val=parseInt(twet);
		val++;
	$("#tweetnbre").html(val);
}
//decrementer le nombre de tweet
function moinsTweets(){
	var twet=$("#tweetnbre").html();
	var val=parseInt(twet);
	val--;
	$("#tweetnbre").html(val);
}

// incrementer le nombre d'abonnement
function plusAbonnements(){
var abonnement = $('#abonnenbre').html();
var plus = parseInt(abonnement);
	plus++;
$('#abonnenbre').html(plus);
}
// decrementer le nombre d'abonnement
function moinsAbonnements(){
var abonnement = $('#abonnenbre').html();
var moins=parseInt(abonnement); moins--;
$('#abonnenbre').html(moins);
}

// incrementer le nombre d'abonnes
function plusAbonnes(){
var abonnes = $('#abnbre').html();
var plus = parseInt(abonnes);
	plus++;
$('#abnbre').html(plus);
}
// decrementer le nombre d'abonnes
function moinsAbonnes(){
var abonnes = $('#abnbre').html();
var moins=parseInt(abonnes); moins--;
$('#abnbre').html(moins);
}


// etat du statut de l'utilisateur

User.etatstatut=function(json){	
	if(json.error===undefined){
		
		$("#tweetnbre").html(json.Tweets);
		$("#abonnenbre").html(json.Abonnements);// voir le nombre d utilisateur que je suis
		$("#abnbre").html(json.Abonnes); // voir le nombre d abonné qui me suivent 
		$("#identifiant").html(environnement.users[environnement.id].login);
	}else{
		alert(ob.error);
	}	
};

// recuperation des valeurs du statut de l'utilisateur depuis la base , utilisation de la servlet SeeUserStatusServlet avec la cle de l utilisateur
function getstatut(){
	$.ajax({
		type:"GET",
		url:"seeuserstatus",
		data:{key : environnement.key
			   },
		dataType:"json",
		success:User.etatstatut,
		error:"error recupe donnees"
	});
}

// ajout et suppression d un ami, 2 en 1
User.ajoutsuppContact = function(json){
	var ob = JSON.parse(JSON.stringify(json));
	if(ob.error!==undefined){
		alert(ob.error);
	}
	friends();
	suggestion();
	
};

function supp_contact(idfriend){
	//if(environnement.users[idfriend].contact){
		//supprimer contact
		$.ajax({
			type:"GET",
			url:"removefriend",
			data:{key:environnement.key,
				id:idfriend
			},
			dataType:"json",
			success:User.ajoutsuppContact,
			error:"problem "
		});
		moinsAbonnements();
	//	$("#tweet"+idfriend).children().attr("class","fa fa-plus");// pour pouvoir l ajouter
}
function ajout_contact(idfriend){
		// ajout d un contact
		$.ajax({
			type:"GET",
			url:"addfriend",
			data:{key:environnement.key,
				id:idfriend
			},
			dataType:"json",
			success:User.ajoutsuppContact,
			error:"problem"
		});
		plusAbonnements();
		//$("#commentaire"+idfriend).children().attr("class","fa fa-minus");// pour pouvoir le supprimer
}

//construction + affichage de la liste de suggestion, dans la partie sggestion
function SuggestionResponse(data){
	var users=data.users;
	var res='<div id="suggestion">';
	res+='<ul>';
	for (var i = 0; i < data.users.length; i++) {
		var usr=users[i];
		var li="";
		li+="<li>";
		li+='<div class="une"><img class="sugg" src="fennec1.jpg" style="width :30px ; height :30px;"/><strong >@'+usr.login+'<button class="fa fa-plus" style="margin-left:"50%; display:inline" onclick="ajout_contact('+usr.id+')"></button>';
		li+='</strong></div></li></br>';
		res+=li;
	}
	res+='</ul>';
	res+='</div>';
		$('#suggestion').replaceWith(res);
}
// construction + affichage de la liste des abonnes, dans la partie friends
function abonnementsResponse(data){
	var users=data.users;
	var res='<div id="friends">';
	res+='<ul>';
	for (var i = 0; i < users.length; i++) {
		var usr=users[i];
		var li="";
		li+='<li>';
		li+='<div class="une"><img class="sugg" src="fennec1.jpg" style="width :30px ; height :30px;"/><strong >@'+usr.login+'<button class="fa fa-minus" style="margin-left:"50%; display:inline" onclick="supp_contact('+usr.id+')"></button>';
		li+='</strong></div></li></br>';
		res+=li;
			
	}
	res+='</ul>';
	res+='</div>';
	$('#friends').replaceWith(res);
}

//recuperation de la liste des abonnes
function friends(){
	if(environnement.key===undefined){
		return;
	}
	$.ajax({
		type:"GET",
		url:"abonnements",
		data:"key="+environnement.key,
		dataType:"json",
		success:function(data){
			abonnementsResponse(data);
		},
		error:"Erreur lors de la suggestion d'utilisateurs"
	});
}
//recuperation de la liste a suggerer
function suggestion(){
	if(environnement.key===undefined){
		return;
	}
	$.ajax({
		type:"GET",
		url:"suggerer",
		data:"key="+environnement.key,
		dataType:"json",
		success:function(data){
			SuggestionResponse(data);
		},
		error:"Erreur lors de la suggestion d'utilisateurs"
	});
}