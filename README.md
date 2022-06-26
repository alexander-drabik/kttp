# KTTP - a kotlin web library
## Getting started
```kotlin
fun main() {
  val app = Kttp()
  
  // Respond to requests at '/'
  app.route("/") {
    return@route source("Hello World!")
  }
  
  // Start app at localhost on port 8080
  app.run("localhost:8080")
}
```
## Examples
### Respond with an html file
```kotlin
fun main() {
  val app = Kttp()
  
  app.route("/") {
    // Respond with 'index.html' from resources directory
    return@route file("index.html")
  }
  
  app.run("localhost:8080")
}
```
### Custom 'page not found' route
```kotlin
fun main() {
  val app = Kttp()
  
  // Respond to every not recognized subpage 
  app.route {
    return@route source("<b> Page Not Found! </b>")
  }
  
  app.route("/") {
    return@route file("index.html")
  }
  
  app.route("/data") {
    return@route file("data.html")
  }
  
  app.run("localhost:8080")
}
```
### Get path variables
```kotlin
fun main() {
  val app = Kttp()
  
  route("/hello") { request ->
    val (name, surname) = request.getPathVariables("name", "surname")
    // For url '/hello?name=Adam&surname=Smith' output is "Hello Adam Smith!"
    return@route source("Hello $name $surname!")
  }
  
  app.run("localhost:8080")
}
```
