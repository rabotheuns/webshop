# Webshop Application

## Description

This is a vulnerable webshop application designed for educational purposes. The application simulates an online store that sells electronics. It allows customers to create accounts, log in, and shop for products. Admins can log in to view all customer details.

The project intentionally includes vulnerabilities from the OWASP Top 10 to help developers and security enthusiasts learn how to identify, exploit, and fix common security issues in web applications.

## Features

- Customers can:
    - Create an account.
    - Log in and browse the product catalog.
    - Add items to their cart and proceed to checkout.

- Admins can:
    - Log in to access the admin dashboard.
    - View all customer details.

## OWASP Top 10 Vulnerabilities

This application includes the following OWASP Top 10 vulnerabilities:

1. **SQL Injection**
Occurs when untrusted input is included in SQL queries without proper validation or parameterization.
   **Mitigation:** Use prepared statements or ORM frameworks like Hibernate to prevent direct SQL injection.

2. **Cross-Site Scripting (XSS)**
Happens when malicious scripts are injected into web pages viewed by other users.
   **Mitigation:** Sanitize and encode user input, and use libraries like OWASP Java Encoder.

3. **Cross-Site Request Forgery (CSRF)**
Exploits authenticated users to perform unwanted actions on a web application.
   **Mitigation:** Use CSRF tokens in forms and validate them on the server side.

4. **Insecure Deserialization**
Allows attackers to execute arbitrary code by exploiting deserialization of untrusted data.
   **Mitigation:** Avoid deserializing untrusted data and use libraries like Jackson with strict type validation.

5. **Broken Authentication**
Weak authentication mechanisms can allow attackers to compromise user accounts.
   **Mitigation:** Use strong password policies, multi-factor authentication, and secure session management.

6. **Sensitive Data Exposure**
Occurs when sensitive data (e.g., passwords, credit card numbers) is not properly protected.
   **Mitigation:** Use encryption (e.g., AES) for sensitive data and secure communication channels (e.g., HTTPS).

7. **Security Misconfiguration**
Insecure default configurations or improper server settings can expose vulnerabilities.
   **Mitigation:** Regularly review and harden configurations, disable unnecessary features, and keep dependencies updated.

8. **Broken Access Control**
Improper enforcement of access control policies can allow unauthorized access to resources.
   **Mitigation:** Implement role-based access control (RBAC) and validate permissions on the server side.

9. **Using Components with Known Vulnerabilities**
Outdated libraries or frameworks can introduce security risks.
   **Mitigation:** Regularly update dependencies and use tools like OWASP Dependency-Check.

10. **Insufficient Logging and Monitoring**
Lack of proper logging and monitoring can delay detection of security breaches.
    **Mitigation:** Implement comprehensive logging and use monitoring tools to detect suspicious activities.

## Objective

The goal of this project is to:

1. Identify and exploit the vulnerabilities present in the application.
2. Understand the impact of these vulnerabilities.
3. Fix the code to prevent these vulnerabilities from being exploited again.

## Disclaimer

This project is for educational purposes only. Do not deploy this application in a production environment or use it for malicious purposes.
