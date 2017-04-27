<#-- @ftlvariable name="" type="sk.fri.uniza.microservice.SayingView" -->
<html>
    <body>
        <!-- calls getSaying().getContent() and sanitizes it -->
        <h1>Saying: ${saying.content?html}!</h1>
    </body>
</html>
