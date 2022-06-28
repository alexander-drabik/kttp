// Object for responding to HTML requests
class ResponseObject(var headers: ArrayList<String> = ArrayList(), var body: String = "", var statusCode: String = "200 OK") {
    var bodyByteArray: ByteArray? = null
    fun response(): Pair<String, ByteArray> {
        var output = "HTTP/1.1 $statusCode\n"
        for (header in headers) output += header + '\n'
        return Pair(output, bodyByteArray ?: body.encodeToByteArray())
    }
}

// Create HTML response from String
public fun source(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Content-Type: text/html"), content)
}

// Create redirect from string
public fun redirect(content: String): ResponseObject {
    return ResponseObject(arrayListOf("Location: $content"), "", "307 Temporary Redirect")
}

// Create HTML response from file
expect fun file(name: String, chunkSize: Int = 1024): ResponseObject

public expect fun readFile(name: String): String
