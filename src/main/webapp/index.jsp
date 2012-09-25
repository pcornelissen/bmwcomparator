<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BMW Konfigurator</title>
        <meta http-equiv="refresh" content="0; URL=<c:url value="/compare"/>">
    </head>
    <body>
        <p>Die Webseite von carpresenter.de ist suboptimal. Daher kann man hier eleganter sich einen Überblick verschaffen...</p>
         <form action="compare">
             <label for="url">Adresse der Seite</label><input type="text" name="url"/>
             <input type="submit"/>
         </form>
    </body>
</html>
