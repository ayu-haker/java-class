# Student Registration System (JSP & Servlet)

A simple web application demonstrating the integration of **Java Servlets** and **JavaServer Pages (JSP)**. This project is built using the modern Jakarta EE specifications (Tomcat 11 compatible) and Maven for dependency management.

## 🚀 Features
- **MVC Architecture:** Separation of concerns using Servlet as the Controller and JSP as the View.
- **Form Handling:** Reads user input (Name, Email, Course, Age) from an HTML form.
- **Expression Language (EL):** Dynamically displays server-side data seamlessly in the JSP.
- **Modern Stack:** Built with Java 17 and `jakarta.servlet-api` 6.1.0.

## 📁 Project Structure
```text
jsp-servlet-student/
├── pom.xml
├── .gitignore
├── README.md
└── src/
    └── main/
        ├── java/com/servlet/HelloServlet.java    # Controller Logic (Request Processing)
        └── webapp/
            ├── student.jsp                       # Input Form (HTML to JSP)
            └── result.jsp                        # Result Display (using EL)
```

## 🛠️ Prerequisites
- **Java Development Kit (JDK):** Version 17 or higher
- **Apache Maven:** For building the project
- **Apache Tomcat:** Version 11 (Supports Jakarta EE 10/Servlet 6)

## ⚙️ Build and Deploy

1. **Navigate to the project folder:**
   ```bash
   cd jsp-servlet-student
   ```

2. **Build the project using Maven:**
   ```bash
   mvn clean package
   ```
   *This will generate a `servlet.war` file inside the `target/` directory.*

3. **Deploy to Tomcat:**
   - Copy the generated `target/servlet.war` file.
   - Paste it into the `webapps` folder of your Apache Tomcat installation directory.
   - Start the Tomcat server.

4. **Run the Application:**
   Open your browser and navigate to:
   ```text
   http://localhost:8080/servlet/student.jsp
   ```

## 🤝 Contribution
Feel free to fork this repository and submit pull requests if you want to add more features or improve the code!
