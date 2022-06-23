// Read file's content from resources folder
public actual fun readFile(name: String): String {
    val reader = {}.javaClass.getResourceAsStream(name)?.bufferedReader() ?: error("Cannot find $name!")
    return reader.readText()
}