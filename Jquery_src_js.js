
$(document).ready(function (){
  
  $("#hideParagraph").click(function(){
    
    $("p:first").hide(); 
  });
  
  $("#button1").click(function(){
    
    $("h1").fadeOut();
    $("h2").fadeOut();
   
  });
   //----------------------------------------
  $("#button2").click(function(){
    
    $("h1").fadeIn();
    $("h2").fadeIn();
    
  });
   //----------------------------------------
  $("#toogleButton").click(function(){
    
    $(".headerSection").toggle(1000);
  });
   //----------------------------------------
  $("#slide").click(function(){
    
    $("div").animate({left:'100'},"slow");
  });
   //----------------------------------------
  $("#slideBack").click(function(){
    
    $("div").animate({left:'0'},"slow");
  });
  //----------------------------------------
  $("#grow").click(function(){
    
    $("div").animate({ 
      
      height:'+=50px',
      width:'+=50px'
    },"slow");
  });
  
   //----------------------------------------
  
   $("#shrink").click(function(){ 
    
    $("div").animate({ 
      
      height:'-=50px',
      width:'-=50px'
    },"slow");
  });
});


