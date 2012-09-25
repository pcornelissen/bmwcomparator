<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Vergleich...</title>
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href="custom.css" rel="stylesheet">
</head>
<body>
<form action="<c:url value="/compare"/>" method="POST" class="form-inline">
    <label for="url">Adresse der Seite</label><input id="focusedInput" class="input-xxxlarge" type="text" name="url" value="${url}"/>
    <input type="submit"/>  <a href="#howto">Hinweise zur Benutzung</a>
</form>

<c:if test="${!hasData}">
    <p>Noch keine Ergebnisse vorhanden, bitte die Adresse der Seite eingeben.</p>
</c:if>
<c:if test="${hasData}">
    <div id="fullSize" style="display:none;position: absolute;"><img src="abc.de" id="fullSizeImg"/></div>
    <table class="table table-hover table-condensed table-bordered ">
        <thead>
        <tr>
            <th>
                Option
            </th>
            <c:forEach items="${autos}" var="auto" varStatus="varStatus">
                <th>  <a id="btnHide" onclick="$('td:nth-child(${varStatus.index+2}),th:nth-child(${varStatus.index+2})').fadeOut('slow')"><i class="icon-trash"></i></a>
                    <a href="${auto.link}" target="_blank">${auto.name} <br/>
                    <img class="preview" src="${auto.image}" width="90"/>  </a>
                </th>
            </c:forEach>
        </tr>
        </thead>
        <tbody>
        <tr>
            <th>Preis</th>
            <c:forEach items="${autos}" var="auto" >
                <td>
                        ${auto.price} EZ:${auto.soldOn}
                </td>
            </c:forEach>
        </tr>
        <tr>
            <th><i class="del icon-trash"></i>Farbe</th>
            <c:forEach items="${autos}" var="auto" >
                <td>
                        ${auto.color}
                </td>
            </c:forEach>
        </tr>
        <tr>
            <th><i class="del icon-trash"></i>Km</th>
            <c:forEach items="${autos}" var="auto" >
                <td>
                        ${auto.km}
                </td>
            </c:forEach>
        </tr>
        <c:forEach items="${alle}" var="option">
            <tr>
                <th><i class="del icon-trash"></i>${option.key}</th>
                <c:forEach items="${autos}" var="auto">
                    <c:set var="marker" value=""/>
                    <c:set var="color" value="A00000"/>
                    <c:forEach items="${auto.options}" var="o">
                        <c:if test="${o==option}">
                            <c:set var="marker" value="${o.value}"/>
                            <c:set var="color" value="green"/>
                        </c:if>
                    </c:forEach>
                    <td align="center" bgcolor="${color}">
                        &nbsp;
                            ${marker}
                        &nbsp;
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<a name="howto">Hinweise</a>
<p>Um diese Übersicht benutzen zu können, müssen Sie auf <a href=="http://www.carpresenter.de">http://www.carpresenter.de</a> die gewünschten Filter auf der linken Seite vornehmen. Dann die Adresse aus der Adressleiste des Browsers kopieren und in das Eingabefeld am Anfang dieser Seite eingeben und den Knopf betätigen.</p>
<p>Danach kann man die Übersicht genießen und es lassen sich überflüssige Zeilen oder Spalten per Papierkorb Symbol entfernen. Diese können nur durch Betätigen des Absenden Knopfes erneut angezeigt werden. (Kein Rückgängig vorhanden!)</p>
<script src="jquery.js" type="text/javascript"></script>
<script src="custom.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
