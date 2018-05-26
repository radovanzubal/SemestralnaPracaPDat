<#-- @ftlvariable name="" type="sk.fri.uniza.microservice.Zariadenie" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <#if !(zariadenie??)>
      <#assign zariadenie = {"id":-1,"content":"New Zariadenie"}> 
      </#if>
      <title>List zariadeni</title>
      <link rel="stylesheet" type="text/css" href="/assets/view.css" media="all">
      <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.12/css/all.css" integrity="sha384-G0fIWCsCzJIMAVNQPfjH08cyYaUtMwjJwqiRKxxE/rx96Uroj1BtIQ6MLJuheaO9" crossorigin="anonymous">
      <script type="text/javascript" src="/assets/view.js"></script>
      <script type="text/javascript" src="/assets/jquery-3.3.1.min.js"></script>
      <script type="text/javascript">
        function deleteZariadenie(id){
           $.ajax({
            url: '/zariadenie/'+id,
            type: 'DELETE',
            success: function(result) {
                alert('Item Deleted');
                location.reload();
            }
        }); 
        }
      </script>

    </head>
   <body id="main_body" >
      <img id="top" src="/assets/top.png" alt="">
      <div id="form_container">
         <h1><a>Form</a></h1>
         <div class="form_description">
            <h2>List vsetkych zariadeni</h2>
            <p></p>
         </div>
         <#list zariadenia>
         <ul>
            <#items as zariadenie>
            <li><p>${zariadenie.id}:${zariadenie.content} | <a href="/zariadenie/edit/${zariadenie.id}"><i class="fas fa-pencil-alt"></i></a> <a href="javascript:void(0);" onclick="deleteZariadenie(${zariadenie.id});"><i class="fas fa-trash-alt"></i></a></p></li>
            </#items>
         </ul>
         <#else>
         <p>Ziadne zariadenia
            </#list>
      </div>
        <a href="/zariadenie/add"><i class="fas fa-plus-circle fa-3x"></i></a>
          


      <img id="bottom" src="/assets/bottom.png" alt="">
   </body>
</html>