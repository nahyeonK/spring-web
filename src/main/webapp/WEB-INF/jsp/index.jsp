<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>
    <%
        Connection conn = DriverManager.getConnection("jdbc:h2:mem:knu_2017", "sa", "");
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM WEATHER");
        ResultSet rs = pstmt.executeQuery();

        out.println("날씨 페이지 입니다.");
    %>
</h1>
<ul>
    <%
        while(rs.next()) {
            out.println(rs.getString("weather_date"));
        }
    %>
</ul>
</body>
</html>