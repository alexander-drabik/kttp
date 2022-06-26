// Object for responding to HTML requests
class ResponseObject(var headers: ArrayList<String>, var body: String = "", var statusCode: String = "200 OK") {
    fun response(): String {
        var output = "HTTP/1.1 $statusCode\n"
        for (header in headers) output += header + '\n'
        output += "\n$body"
        return output
    }
}

// Create HTML response from String
public fun source(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Content-Type: text/html"), content)
}

// Create HTML response from file
public fun file(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Content-Type: text/html"), readFile(content))
}

// Create redirect from string
public fun redirect(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Location: $content"), "", "307 Temporary Redirect")
}

public expect fun readFile(name: String): String
