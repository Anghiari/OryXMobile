var url  = Android.getURL();
//var url = "tags2.xml";

var colorlist = ["#4bb3d3","#db4d4c","#eda637","#86aa69"];

var mainC = document.getElementById("mainC");
mainC.innerHTML = '';

createHTML(url);

function createHTML(url,callback){

		
		var urll = url;
		
	$.get(urll, function(xml){
	
		
		var outputjson = $.xml2json(xml);
		
		
		if(outputjson.info.homepage != 1){
			createNoneHomePage(outputjson);
		}
		else{
		
			createBlogHomePage(outputjson);
		}
		
		
	});
	
	
}

function createNoneHomePage(outputjson){

		//document.body.style.backgroundColor="#fff";
		
		var val = {};
		val['title'] = outputjson.info.pagetitle;
		val['fav'] = outputjson.info.favicon;
		new RSSHeader(val);
		
		var divs = outputjson.article.content.div;
		
		console.log(divs.length);
		
		for(var i = 0; i<divs.length;i++){
		
		var items = divs[i].item;
		
		if(items.length == 0){
			continue;
		}
		
		//if(items != null){
		
			var div = contentDiv();
			var img = contentImgs(div);
			var video = contentVideo(div);
			var p = contentP(div);
				
			var htmltext = "";
			
			
			
			for(var j = 0; j<items.length-1;j++){

				if(items[j]["tag"]=="text"){
					htmltext = htmltext+items[j]+" ";
				}	
				else if(items[j]["tag"]=="a"){
					htmltext = htmltext+"<a href='"+items[j].attrVal+"'>"+items[j].text+" "+"</a>";
				}
				else if(items[j]["tag"]=="b"){
					htmltext = htmltext+"<strong>"+items[j].text+"</strong>";
				}
				else if(items[j]["tag"]=="i"){
					htmltext = htmltext+"<i>"+items[j].text+"</i>";
				}
				else if(items[j]["tag"]=="img"){
					var val = {};
					val['src'] = items[j].attrVal;
					val['id'] = "img_"+i+"_"+j;
					new ImageView(img,val);
				}
				
				else if(items[j]["tag"]=="iframe"){
					var val = {};
					val['src'] = items[j].attrVal;
					new VideoView(video,val);
				}
				else{
						htmltext = htmltext+items[j].text+" ";
				}				
		}
		//}
		p.innerHTML = htmltext;	
		}
		
}

function createBlogHomePage(outputjson)
{

	var val = {};
	val['title'] = outputjson.info.pagetitle;
	val['fav'] = outputjson.info.favicon;
	new RSSHeader(val);
	
	var rssitems = outputjson.post;

	
	for(var i = 0; i<rssitems.item.length;i++){

		var val = {};
		val['id'] = i+"";
		val['date'] = rssitems.item[i].published;
		val['topic'] = rssitems.item[i].title;
		val['img'] = rssitems.item[i].image;
		val['link'] = rssitems.item[i].link;
		val['content'] = rssitems.item[i].summary;
		val['colour'] = colorlist[i%4];
		new RSSItem(val);
	
	}

}





