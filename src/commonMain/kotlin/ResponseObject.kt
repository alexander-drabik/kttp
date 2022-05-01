// Object returned by route() function
class ResponseObject(
    // Type determines how server will respond to routes
    var type: ResponseType,
    var content: String
) {
    fun response(): String {
        return when (type) {
            ResponseType.Source -> content
            ResponseType.File -> return readFile(content)
        }
    }
}

enum class ResponseType {
    Source, File
}

public fun source(content: String): ResponseObject {
    return ResponseObject(ResponseType.Source, content)
}
public fun file(content: String): ResponseObject {
    return ResponseObject(ResponseType.File, content)
}

public expect fun readFile(name: String): String
