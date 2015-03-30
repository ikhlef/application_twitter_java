//objet Commentaire  6.4 
function Tweet(id, auteur, text, date, score, like,listcomm,etat){
	this.id = id;
	this.auteur=auteur; 
	this.text=text; 
	this.like = like;
	this.date= date;  // la date du tweet
	//this.listcomm = []; // le tableau qui contiendra ts les commentaire du tweet , definir un fichier js, qui contiendra l objet commentaire
	this.listcomm =listcomm;
	if(score === undefined){
	this.score=0;
	}else{
	this.score=score;
	}
 this.etat=etat;	
}

Tweet.prototype.getHtml = function(){
	var tw='<div class="vtweet" id="tweet-'+this.id+'">';
	tw += '<div class="un"><img class="sugg"src="fennec1.jpg"/><strong>@'+this.auteur.login+'</strong>';
	if(environnement.active && environnement.id==this.auteur.id){	
		tw+='<button style="margin-left:20%; color:blue;vertical-align: top; width : 20px;height : 20px;"title="deletetweet" id="add_friend" class="fa fa-trash-o" onclick="delete_tweet(\''+this.id+'\')"></button>';		
	}	
	
	tw += '<p style ="width:100%;height :auto;">'+this.text+'</p>';
	var d = new Date(this.date);
	
	tw += '<p style="margin-left:1%; color:blue"class=" fa fa-bell-o">&nbsp;'+d.toLocaleString();+'</p>';	
	tw+='<ul id="">'+
	 '<li style="margin-left:40px; color:blue;"><button class="fa fa-pencil-square-o" title="commenter" onclick="javascript:new_comment(\''+this.id+'\')"></button></li>';
	 if(this.etat==0){
	tw+= '<li style="margin-left:50px; color:blue;"><button id="like-'+this.id+'" class=" fa fa-thumbs-o-up" title="like" onclick="modifier_choix_like(\''+this.id+'\')"></button><span id="likebis-'+this.id+'">'+this.like+'</span></li>'+
	 '</ul></div>';
	}else{
	
	tw+= '<li style="margin-left:50px; color:blue;"><button id="like-'+this.id+'" class=" fa fa-thumbs-o-down" title="dislike" onclick="modifier_choix_unlike(\''+this.id+'\')"></button><span id="likebis-'+this.id+'">'+this.like+'</span></li>'+
		 '</ul></div>';
	}
	tw+='<div id="idcomm-'+this.id+'" style="margin:0.5% 0.5;margin-buttom: 1%; border:blue;display:none;">'+
	'<textarea name="nvcomm" id="nvcomm" placeholder="new comment..." class="nvcomm"> </textarea >'+
	'<button type="button" class="valitwet" onclick="javascript:ajouter_commentaire(\''+this.id+'\')"><i class="fa fa-comments-o"></i></button>';
	
	tw+=f'<div id="listcomment-'+this.id+'">';
	//tw+=construire(this.listcomm);
	var comm="";
	if(this.listcomm.length!==undefined){
		for(var i=0; i<this.listcomm.length;i++){
			comm+='<div id="nvtaire-'+this.listcomm[i]._id+'style="border: 5px solid red; margin: 5px 5px;background-color:green;">';
			comm+='<span style="color:blue">'+this.listcomm[i].login+'</span>';
			comm+='<span style="margin-left:2%">'+this.listcomm[i].text+'</span>';
			comm+='<span style="margin-left:2%; color:blue;">'+new Date(this.listcomm[i].date).toLocaleString()+'</span></div>';
	}
	}
	tw+=comm;
	tw += '</div></div></div>';
	return tw;
};

function new_comment(a){
	$("#idcomm-"+a).css("display","block");
}


function ajouter_commentaire(ido){
	var text= $("#nvcomm").val();
	$("#nvcomm").val("");
	$.ajax({
		type:"GET",
		url:"addcomment",
		data:"key="+environnement.key+"&idobj="+ido+"&text="+text,
		dataType:"json",
		success:function(data){
 var comm='<div id="nvtaire-'+data.idco+' style="border: 5px solid red; margin: 5px 5px;background-color:green;">';
 comm+='<span style="color:blue">'+data.login+'</span>';
 comm+='<span style="margin-left:2%">'+text+'</span>';
 comm+='<span style="margin-left:2%; color:blue;">'+new Date(data.date).toLocaleString()+'</span></div>';			
			$("#listcomment-"+ido).prepend(comm);	

		},
		error:"error recupe donnees"
	});
}
// verifier like ou dislike tweet, a fin de savoir quoi afficher lors du chargement du tweet, soit le dislike si j ai deja liker, sinon like si ce n'est pas encore fait
//augmenter le nbre de like d un tweet dans la base
function verifier_like_tweet (ido){
	$.ajax({
		type:"GET",
		url:"verifierlikedislike",
		data:"key="+environnement.key+"&ido="+ido,
		dataType:"json",
		success:voir,
		error:"error recupe donnees"
	});
}
function voir(data){
	
	if (data.love===0){
		var b='<button id="like-'+this.id+'" class=" fa fa-thumbs-o-up" title="like" onclick="modifier_choix_like(\''+this.id+'\')"></button>';
		$("#like-"+this.id).replaceWith(b);	
	}else{
		var b='<button id="like-'+this.id+'" class=" fa fa-thumbs-o-down" title="dislike" onclick="modifier_choix_unlike(\''+this.id+'\')"></button>';
		$("#like-"+this.id).replaceWith(b);
	}
}

//augmenter le nbre de like d un tweet dans la base
function like_tweet (key, ido){
	$.ajax({
		type:"GET",
		url:"liketweet",
		data:"key="+environnement.key+"&idob="+ido,
		dataType:"json",
		//success:plustweet,
		error:"error recupe donnees"
	});
}
//diminuer le nbre de tweet dans la base
function delike_tweet (key, ido){
	$.ajax({
		type:"GET",
		url:"deliketweet",
		data:"key="+environnement.key+"&idob="+ido,
		dataType:"json",
		//success:moinsTweets,
		error:"error recupe donnees"
	});
}

function modifier_choix_like(a){
		var val=$("#likebis-"+a).html();
	val=parseInt(val);
	val++;
	$("#likebis-"+a).html(val);
	like_tweet (environnement.key,a);
var b='<button id="like-'+a+'" class="fa fa-thumbs-o-down" title="dislike" onclick="modifier_choix_unlike(\''+a+'\')"></button>';
	$("#like-"+a).replaceWith(b);	
}

function modifier_choix_unlike(a){
	var val=$("#likebis-"+a).html();
	val=parseInt(val);
	val--;
	$("#likebis-"+a).html(val);
	delike_tweet (environnement.key,a);
	var b='<button id="like-'+a+'" class=" fa fa-thumbs-o-up" title="like" onclick="modifier_choix_like(\''+a+'\')"></button>';
	$("#like-"+a).replaceWith(b);
}



/*function add_friend(){
	var c= '<button title="not suivre" class="fa fa-minus" onclick="javascript:noadd_friend()"></button>';
	$("#add_friend").replaceWith(c);	
}
function noadd_friend(){
	var c= '<button title="suivre" id="add_friend" class="fa fa-plus" onclick="javascript:add_friend()"></button>';
	$("#add_friend").replaceWith(c);	
}*/
// pour supprimer un tweet
function delete_tweet(tweet_id){
	$.ajax({
		type:"GET",
		url:"deletetweet",
		data:"key="+environnement.key+"&id="+tweet_id,
		dataType:"json",
		success:mettreajourespace,
		error:"Erreur de suppression du tweet"
	});
	moinsTweets();
}
// pour creer un tweet
function create_tweet(){
	var text=""+$("#nvteet").val();
	if(text==="" || !environnement.active || environnement.key===""){
		return;
	}
	$("#nvteet").val("");
	$.ajax({
		type:"GET",
		url:"createtweet",
		data:"key="+environnement.key+"&text="+text,
		dataType:"json",
		success:mettreajourespace,
		error:"Erreur de mise a jour du statut"
	});
	plusTweets();
}
//mettre a jour l espace apres insetion ou sppression d un tweet 
function mettreajourespace(){
	if(!environnement.active){
		espaceaccueil();
	}
	if(environnement.timeline==="self"){
		myspa();
	}else {
		espaceaccueil();
	}
	
}


//////////////service search\\\\\\\\\\\\\\\\\\\\\\ pour un service commun, affichage des derniers tweets, 
/////ou affichage des tweets correspendants a une recherche par friends, ou par date ou par login ou...;

// 6.5 objet search tweet
function SearchTweet (resultats, query, contacts_only, auteur, date){
	this.resultats= [];
	this.query=query;
	this.date=date;
	environnement.recherche=this;
	this.contacts_only=contacts_only;
	this.auteur=auteur;
	this.resultats=[].concat(resultats);  
}
// def de gethtml()
SearchTweet.prototype.getHtml = function(){
	var chaine="";
	if(this.resultats[0]===undefined){
		return '<div class="">desoler, je ne peux rien faire pour vous! recommencer</div>';
	}
	for (var i = 0; i < this.resultats.length; i++) {
		chaine+=this.resultats[i].getHtml(); // ajouter le code html de chaque tweet, ilfaut que je pense a faire de meme a chaque commentaire d'un tweet, pour recuperer le code html
	}
	return chaine;	
};
	// 6.6 la fonction SearchTweet.traiteReponse (json_txt)

//6.6.1 def de la fonction reviver qui permet de preciser le processus de reconstruction de l'objet(twwet, user, et date)
SearchTweet.reviver = function (key,value){
	if(key.length===0){  
		if (value.error===undefined){
			return new SearchTweet(value.resultats, value.search, value.contacts_only, value.auteur, value.date);
		}else{
			return value;
		}
	//}else if(value.id !== undefined && value.auteur instanceof User){ // array results
	}else if(value.auteur instanceof User){
		return new Tweet(value.id, value.auteur, value.text, value.date, value.score, value.like, value.listcomm,value.etat);
	}else if(key==='auteur'){
		return new User(value.id, value.login, value.contact);
	}else if(key=='date'){
		return new Date(value);
	}else{
		return value;
	}
};

//6.6 la fonction SearchTweet.traiteReponse (json_txt) 
SearchTweet.TraiterResponseJSON = function (json){
	var obj = JSON.parse(JSON.stringify(json), SearchTweet.reviver); // definir la fonction reviver pour l adapter a chaque ovjet, tweet, user, searchtweet et meme commentaire
	if(obj.error===undefined){
		$("#viewtweet").html(obj.getHtml()); // inserer le code html recuperer pour la recherche construite a l aide du reviver  
	}else{
		alert(obj.error);
	}
};
//fonction qui permet de chager les tweet de l utilisateur
function myspa(){
	environnement.timeline="self";
	$.ajax({
		type:"GET",
		url:"searchmytweet",
		data:"key="+environnement.key+"&id="+environnement.id,
		dataType:"json",
		success:SearchTweet.TraiterResponseJSON,
		error:"Erreur de rappatriement des tweets"
	});
	suggestion();
	friends();
}
//espace accueil, pour tous les tweet , recherche not by friends 
function espaceaccueil(){
	environnement.timeline="all";
	$.ajax({
		type:"GET",
		url:"searchtweetaccueil",
		//data:"key="+environnement.key+"&friends=0",
		data:"key="+environnement.key,
		dataType:"json",
		success:SearchTweet.TraiterResponseJSON,
		error:"Erreur de rappatriement des tweets"
	});
	suggestion();
	friends();	
}
function rechercheTweets(){
	query=$("#search").val();
	$.ajax({
		type:"GET",
		url:"search",
		data:"query="+query+"&key="+environnement.key+"&friends=0",
		dataType:"json",
		success:SearchTweet.TraiterResponseJSON,
		error:"Erreur de rappatriement des tweets de la recherche"
	});
	suggestion();
	friends();	
}