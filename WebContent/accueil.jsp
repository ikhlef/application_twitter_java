<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
<!-- 	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> -->
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="font-awesome/css/font-awesome.css" rel="stylesheet">
    <link href="css/sb-admin.css" rel="stylesheet">
	
	<script src="js/jquery-1.10.2.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script src="js/plugins/metisMenu/jquery.metisMenu.js"></script>


<script src="js/sb-admin.js"></script>
<link rel="stylesheet" href =" testbis.css "/>
<link rel="stylesheet" href =" header.css "/>
<script src="tweet.js"></script>
<script src="login.js"></script>
<script src="users.js"></script>
<script src="commentaire.js"></script>
<script src="accueil.js"></script>
  
<script>
      <%
        String id = request.getParameter("id");
        String key = request.getParameter("key");
        String login = request.getParameter("login");
      %>
      window.onload=function(){
        init_env();
      <%
        if(id!=null && key!=null && login!=null){
          out.print("initialisation('"+id+"','"+login+"','"+key+"');");
        }
      %>
      authentification();
      };
    </script>

</head>


<body>

<div class="page">
	<!-- présentation de tete de page : logo + search + partie choix -->
	   <div class="tete">		
		        <div id="titre_principal">
			        <img class="fouf"src="foufannec.jpg" id="logo"/>
			       <h3 class="tit">TheSelect</h3>  
		        </div>
				<div class="sera">	
				<form class="form-search " method = "get" action="javascript:function(){}" onsubmit="javascript:rechercheTweets()">
					<input type="text" id="search" class="search-query" name="query" placeholder="Search..." />
				</form>
				</div>
				
			<input type="radio" name="contact" id="contact" style="margin-left:36%;width:13px;">contact
		
		<div class="dghf">
			  <ul>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i>
						<i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu ">
						<li><a id="accueil" href="#accueil"><i class="fa fa-home fa-fw"></i>Accueil</a></li>
						<li><a id="moi" href="#moiii"><i class="fa fa-user fa-fw"></i>User Profile</a></li>
                        <li ><a id="signin" href="index.html"><i class="fa  fa-sign-in fa-fw"></i> signin | signup</a></li>
						<li><a id="deconnection" href="index.html"><i class="fa fa-sign-out fa-fw"></i> Logout</a></li>   
                    </ul>
                </li>
            </ul> 
		  </div> 	
	    </div>	
</div>

   <div class="globale">
	<div class="p1">		
		<div class="place">	
			<div class="picture">
				<!-- avoir peut etre a rajouter, selon le temps <input type="file" name="profil" value="" class="photoprofil">                          -->
				<img class="photo" href="#" src="fennec1.jpg">
			</div>
			<div class="logg" ><h3 id="identifiant">@foufi</h3></div>					
			<div class="calcule">
				<ul>
					<li>
		  				<span class="calcul">tweets</span>
						<span class="number" id="tweetnbre">0</span>	
	  				</li>
					<li>
		  				<span class="calcul">abonnements</span>
						<span class="number" id="abonnenbre">0</span>
	  				</li>
					<li>
		  				<span class="calcul">abonnes</span>
						<span class="number" id="abnbre">0</span>			
	  				</li>
				</ul>
			</div>
		</div>
		
		<div class="proposi">
		<h5><a id="frien">Friends</a></h5>
				<div class="listesugg" id="friends"></div>
		</div>		
		
		<div class="proposi">
			<h5><a id="suggest"> Suggestions</a></h5>
				<div class="listesugg" id="suggestion"></div>
		</div>
	</div>
	
	
	<div class="p2">
		<h2 class="tt" >Tweets</h2>
		 <div class="newtweet">			
			<form method = "get" action="javascript:function(){}" onsubmit="javascript:create_tweet()"> <h5 class="leg">new tweet</h5>
				<textarea id="nvteet" name="nvtwt" placeholder="new tweet..." class="nvtwt"> </textarea >
				<button type="submit" class="valitwet" ><i class="fa fa-comment-o"></i></button>				
			</form>
		</div>
		
		
		<div class="viewTweets" id="viewtweet">
			<div class="vtweet"><p>azul felak</p></div>	
		</div>
		
  </div>

</div>

</body>
</html>