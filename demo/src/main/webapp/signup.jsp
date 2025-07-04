<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 03-Jul-25
  Time: 11:48 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sign Up - QuizApp</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #eef2f3;
            padding: 50px;
        }
        .form-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            max-width: 400px;
            margin: auto;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        input[type="text"], input[type="email"], input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-top: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 6px;
        }
        button {
            width: 100%;
            padding: 10px;
            background: #007BFF;
            color: white;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        button:hover {
            background: #0056b3;
        }
        .error {
            color: red;
            margin-bottom: 15px;
        }
    </style>
</head>
<body>

<div class="form-container">
    <h2>Create Your Account</h2>

    <% if (request.getAttribute("error") != null) { %>
    <p class="error"><%= request.getAttribute("error") %></p>
    <% } %>

    <form method="post" action="signup">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Email (must end with @gmail.com)</label>
        <input type="email" name="email" required pattern=".*@gmail\.com">

        <label>Password</label>
        <input type="password" name="password" required>

        <button type="submit">Sign Up</button>
    </form>

    <p style="margin-top:15px;">Already have an account? <a href="login.jsp">Login here</a></p>
</div>

</body>
</html>

