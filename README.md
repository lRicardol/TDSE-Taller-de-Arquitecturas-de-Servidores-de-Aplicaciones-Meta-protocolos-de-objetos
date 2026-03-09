# TDSE-Taller-de-Arquitecturas-de-Servidores-de-Aplicaciones-Meta-protocolos-de-objetos

## Application Server Architectures Lab

**Meta Object Protocols • IoC Pattern • Java Reflection**

---

## Project Description

This project implements a **minimal Java Application Server** similar to Apache or a simplified Spring Boot environment.
The server is capable of:

* Serving **static files** such as HTML and PNG images.
* Processing **HTTP requests**.
* Loading **POJO-based controllers** dynamically.
* Using **Java Reflection** to discover annotated methods.
* Providing a **basic IoC (Inversion of Control) framework** to build web applications.

The project demonstrates the use of:

* **Meta-programming**
* **Reflection**
* **Custom Annotations**
* **Basic Web Server Architecture**

The goal is to build a **lightweight micro-framework inspired by Spring Boot**.

---

# Key Concepts Implemented

## Java Reflection

The framework dynamically inspects classes and methods at runtime to identify controllers and HTTP endpoints.

## Inversion of Control (IoC)

The framework is responsible for discovering and invoking application components (controllers).

## Custom Annotations

Annotations are used to declare HTTP routes and request parameters.

Implemented annotations:

* `@RestController`
* `@GetMapping`
* `@RequestParam`

## Embedded HTTP Server

A custom HTTP server built using **Java sockets** that handles incoming requests and returns responses.

---

#  Project Architecture

The project follows a modular architecture separating the HTTP server, framework logic, and application controllers.

```
src
 └─ main
     ├─ java
     │   └─ org.example
     │       ├─ MicroSpringBoot.java
     │       └─ framework
     │           ├─ MicroFramework.java
     │           ├─ Router.java
     │           ├─ HttpServer.java
     │           ├─ HttpRequestParser.java
     │           ├─ HttpResponseBuilder.java
     │           ├─ StaticFileHandler.java
     │           ├─ Request.java
     │           ├─ Response.java
     │           ├─ RouteHandler.java
     │           ├─ annotations
     │           │   ├─ GetMapping.java
     │           │   ├─ RequestParam.java
     │           │   └─ RestController.java
     │           ├─ controllers
     │           │   └─ GreetingController.java
     │           └─ ioc
     │               └─ ControllerLoader.java
     │
     └─ resources
         └─ webroot
             └─ index.html
```

---

#  Framework Components

## HttpServer

Handles socket connections and processes HTTP requests.

## HttpRequestParser

Parses incoming HTTP requests and extracts:

* Method
* Path
* Query parameters
* Headers

## HttpResponseBuilder

Constructs HTTP responses with proper headers and content.

## Router

Maps URL paths to controller methods.

## StaticFileHandler

Serves static resources such as:

* HTML
* CSS
* Images (PNG)

from the `webroot` directory.

---

#  IoC Framework

The framework implements a minimal IoC container.

Steps performed:

1. Load controller class dynamically
2. Verify if it contains `@RestController`
3. Inspect methods using reflection
4. Detect methods annotated with `@GetMapping`
5. Register routes in the framework router

---

#  Custom Annotations

### RestController

Marks a class as a web controller.

```java
@RestController
public class GreetingController {
}
```

---

### GetMapping

Defines an HTTP GET endpoint.

```java
@GetMapping("/greeting")
public String greeting() {
    return "Hello World";
}
```

---

### RequestParam

Extracts query parameters from the HTTP request.

```java
@GetMapping("/greeting")
public String greeting(@RequestParam(value="name", defaultValue="World") String name){
    return "Hola " + name;
}
```

---

#  Example Controller

The project includes a demonstration controller.

```java
@RestController
public class GreetingController {

    @GetMapping("/")
    public String index(){
        return "Servidor funcionando";
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(value="name", defaultValue="World") String name){
        return "Hola " + name;
    }

}
```

---

# Running the Server

## Compile the project

```
mvn clean install
```

---

## Run the application

```
java -cp target/classes org.example.MicroSpringBoot org.example.framework.controllers.GreetingController
```

---

#  Testing the Application

Once the server is running, open a browser:

### Home Page

```
http://localhost:8080/
```

### Greeting Endpoint

```
http://localhost:8080/greeting
```

### Greeting with Parameter

```
http://localhost:8080/greeting?name=Pedro
```

Example response:

```
Hola Pedro
```

---

#  Static Content

Static files are served from:

```
src/main/resources/webroot
```

Example:

```
index.html
```

The index page provides links to test the REST endpoints.

---

#  AWS Deployment

The application server can be deployed on an **AWS EC2 instance**.

Steps:

1. Launch an EC2 instance
2. Install Java
3. Clone the repository
4. Compile with Maven
5. Run the server

Example command:

```
java -cp target/classes org.example.MicroSpringBoot org.example.framework.controllers.GreetingController
```

Public access example:

```
http://<EC2_PUBLIC_IP>:8080
```

---

#  Technologies Used

* Java 17+
* Maven
* Java Sockets
* Java Reflection
* Custom Annotations
* HTTP Protocol

---

#  Features Implemented

 HTTP Web Server

 Static file serving (HTML / PNG)

 Dynamic route handling

 IoC-based controller loading

 Reflection-based method invocation

 Custom annotations

 Query parameter handling

 REST endpoint demonstration

---

# Laboratory Requirements

This project fulfills the requirements of the lab:

* Build a **Java Web Server**
* Implement **IoC framework**
* Use **Java Reflection**
* Support **@GetMapping**
* Support **@RequestParam**
* Serve **HTML and PNG**
* Load **POJO Controllers**

---

# Author

Ricardo Ayala - Application Server Architectures Course

---

# Final Notes

This project demonstrates how modern frameworks such as **Spring Boot** internally use:

* Reflection
* Annotations
* IoC containers
* Embedded servers

to simplify web application development.

This implementation provides a **minimal educational prototype** of those concepts.
