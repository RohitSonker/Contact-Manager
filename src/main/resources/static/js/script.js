console.log("rohit");
/*const toggleSidebar=()=>{
	console.log("rohit");
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","0%");
	}
	else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%%");
		
	}
}*/
const toggleSidebar=()=> {
  var x = document.getElementById("sidebar");
 var y = document.getElementById("content");

  if (x.style.display === "none") {
    x.style.display = "block";
y.style.marginLeft="2%";
  } else {
    x.style.display = "none";
y.style.marginLeft="2%";
  }
}