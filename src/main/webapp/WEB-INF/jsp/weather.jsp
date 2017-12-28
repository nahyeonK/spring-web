<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>날씨페이지 입니다.</h1>
<ul>
    <c:forEach items="${weathers}" var="weather">
        <li>${weather.weatherDate}</li>
    </c:forEach>
</ul>
</body>
</html>