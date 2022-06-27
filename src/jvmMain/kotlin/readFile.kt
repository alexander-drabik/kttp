import java.nio.file.Files
import kotlin.io.path.Path

// Read file's content from resources folder
public actual fun readFile(name: String): String {
    val reader = {}.javaClass.getResourceAsStream(name)?.bufferedReader() ?: error("Cannot find $name!")
    return reader.readText()
}

actual fun file(name: String, chunkSize: Int): ResponseObject {
    val extension = name.substringAfterLast('.', "")

    val responseObject = ResponseObject()
    when (extension) {
        "html" -> responseObject.headers.add("Content-Type: text/html")
    }

    // Get file size and put it into header
    val size = Files.size(Path("src/main/resources/$name"))
    responseObject.headers.add("Content-Length: $size")

    // Ad body to response object
    // TODO send by small chunks later
    responseObject.body = readFile(name)

    return responseObject
}