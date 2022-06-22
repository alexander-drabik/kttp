// Object returned by route() function
class ResponseObject(var headers: ArrayList<String>, var body: String = "", var statusCode: String = "200 OK") {
    fun response(): String {
        var output = "HTTP/1.1 $statusCode"
        for (header in headers) output += "\n" + header
        output += "\n$body"
        return output
    }
}

public fun source(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Content-Type: text/html\n"), content)
}
public fun file(content: String): ResponseObject {
    return ResponseObject(arrayListOf(""), readFile(content))
}
public fun redirect(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Location: $content"), "", "307 Temporary Redirect")
}

public expect fun readFile(name: String): String
