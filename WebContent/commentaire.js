// objet Commentaire  6.4 
function Commentaire(id, auteur, text, date, score){
	this.id = id;
	this.auteur=auteur;
	this.text=text;
	this.date= date;
	if(score === undefined){
	this.score=0;
	}else{
	this.score=score;
	}
}