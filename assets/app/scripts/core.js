
var mainC = document.getElementById("mainC");

function contentDiv(){
	
	var div = document.createElement("div");
	div.className = 'contentDiv';
	mainC.appendChild(div);
	return div;
}

function contentP(div){
	
	var p = document.createElement("p");
	p.className = 'contentP';
	div.appendChild(p);
	return p;
}

function contentImgs(div){
	
	var img = document.createElement("div");
	div.appendChild(img);
	return img;
}

function contentVideo(div){
	
	var video = document.createElement("div");
	div.appendChild(video);
	return video;
}


function ImageView(div,props)
{
	
	var logo = document.createElement("img");
	logo.id="img_"+props['id'];
	logo.src = props['src'];
	logo.className = "rss-img";
	logo.align = "left";
	tid = logo.id;
	logo.onclick = function(tid) { showImgs(tid) };
	div.appendChild(logo);
	
}


function VideoView(div,props)
{

	var v = document.createElement("iframe");
	v.width = "99%";
	v.height = "auto";
	v.src=props['src'];
	v.frameBorder = 0;
	v.setAttribute('allowFullScreen', '');
	div.appendChild(v);
	
}


function RSSHeader(props)
{
	var div = document.createElement("div");
	div.className = "rssheader";
	mainC.appendChild(div);
	
	var img = document.createElement("img");
	img.className = "fav-img";
	img.src = props['fav'];
	img.id = "fav-img";
	img.align = "left";
	img.onerror = function(img) { img.srcElement.parentNode.removeChild(img.srcElement);};
	div.appendChild(img);	
	//img.srcElement.style.visibility = 'visible';console.log(img)
		
	var title = document.createElement("p");
	title.innerHTML = props['title'];
	div.appendChild(title);

}

function RSSItem(props)
{
	var div = document.createElement("div");
	div.className = "rss-item";
	mainC.appendChild(div);
	
	div.style.borderLeft="10px solid "+props['colour'];
	
	var date = document.createElement("p");
	date.className = "rss-date";
	date.innerHTML = "<span>"+props['date']+"</span>";
	div.appendChild(date);
	
	var tp = document.createElement("p");
	div.appendChild(tp);
	var a = document.createElement("a");
	a.className = "rss-topic";
	a.innerHTML = props['topic'];
	a.href = props['link'];
	var rssid = tp.id;
	//tp.onclick = function(rssid) { showPost(rssid) };
	tp.appendChild(a);

	
	if(props['img']){
		var img = document.createElement("img");
		img.className = "rss-img";
		img.src = props['img'];
		img.align = "left";
		img.id = "rss_"+props['id'];
		idd = img.id;
		img.onclick = function(idd) { showImgs(idd) };
		div.appendChild(img);
	}
	
	var c = document.createElement("p");
	c.className = "rss-content";
	c.innerHTML = props['content'] + "... <span>&raquo</span>";
	div.appendChild(c);
	

	

}

function showPost(link)
{
	var ll = link.toElement.id;
	ll = ll.substring(7);
	var linkk = ll;
	
	var myNode = document.getElementById("mainC");
	while (myNode.firstChild) {
		myNode.removeChild(myNode.firstChild);

	}
	
	createHTML(linkk);
	
}

function showImgs(idd)
{
	var it = idd.toElement.id;
	
	var imm = document.getElementById(it).style.width;
	var ml = '97%';
	if (imm === '97%'){
		ml = '120px';
	}
	
	$('#'+it).animate({
        width: ml
    });
}

function createContainer()
{
	var div = document.createElement("div");
	div.className = "main";
	div.id = "mainC";
	document.body.appendChild(div); 
}


