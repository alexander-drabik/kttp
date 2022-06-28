import java.awt.image.BufferedImageOp
import java.io.BufferedOutputStream
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.file.Files
import javax.imageio.ImageIO
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
        "png"  -> responseObject.headers.add("Content-Type: image/png")
    }

    // Get file size and put it into header
    val size = Files.size(Path("src/main/resources/$name"))
    responseObject.headers.add("Content-Length: $size")

    // Ad body to response object
    // TODO send by small chunks later
    val bufferedImageIO = ImageIO.read(File("src/main/resources/$name"))
    val byteArrayOutputStream = ByteArrayOutputStream()
    ImageIO.write(bufferedImageIO, extension, byteArrayOutputStream)
    val bytes = byteArrayOutputStream.toByteArray()
    responseObject.bodyByteArray = bytes

    return responseObject
}