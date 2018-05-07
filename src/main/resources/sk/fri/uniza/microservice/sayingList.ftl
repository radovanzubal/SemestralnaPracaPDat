<#-- @ftlvariable name="" type="sk.fri.uniza.microservice.Saying" -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <#if !(saying??)>
      <#assign saying = {"id":-1,"content":"New Saying"}> 
      </#if>
      <title>Add new Saying</title>
      <link rel="stylesheet" type="text/css" href="/assets/view.css" media="all">
      <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.12/css/all.css" integrity="sha384-G0fIWCsCzJIMAVNQPfjH08cyYaUtMwjJwqiRKxxE/rx96Uroj1BtIQ6MLJuheaO9" crossorigin="anonymous">
      <script type="text/javascript" src="/assets/view.js"></script>
      <script type="text/javascript" src="/assets/jquery-3.3.1.min.js"></script>
      <script type="text/javascript">
        function deleteSaying(id){
           $.ajax({
            url: '/saying/'+id,
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
            <h2>List of all sayings</h2>
            <p></p>
         </div>
         <#list sayings>
         <ul>
            <#items as saying>
            <li><p>${saying.id}:${saying.content} | <a href="/saying/edit/${saying.id}"><i class="fas fa-pencil-alt"></i></a> <a href="javascript:void(0);" onclick="deleteSaying(${saying.id});"><i class="fas fa-trash-alt"></i></a></p></li>
            </#items>
         </ul>
         <#else>
         <p>No sayings
            </#list>
      </div>
        <a href="/saying/add"><i class="fas fa-plus-circle fa-3x"></i></a>
          


      <img id="bottom" src="/assets/bottom.png" alt="">
   </body>
</html>