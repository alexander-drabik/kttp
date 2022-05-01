import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket

val methodRegex = """^\S+""".toRegex()
val pathRegex   = """/\S*""".toRegex()

internal actual interface KttpInterface {
    actual var routeList: ArrayList<Route>

    actual fun run(address: String, port: Int) {
        val server = ServerSocket(port, 50, InetAddress.getByName(address))
        while (true) {
            val client = server.accept()

            GlobalScope.launch {
                // Get request
                val input = BufferedReader(InputStreamReader(withContext(Dispatchers.IO) {
                    client.getInputStream()
                }))
                var line = withContext(Dispatchers.IO) {
                    input.readLine()
                } ?: String()

                // Get request's method
                val method = methodRegex.find(line)?.value
                if (method == null) {
                    withContext(Dispatchers.IO) {
                        client.close()
                    }
                    return@launch
                }

                // Find path
                val path = pathRegex.find(line)?.value ?: ""

                // Find request header
                while (line.isNotBlank()) {
                    line = withContext(Dispatchers.IO) {
                        input.readLine()
                    } ?: break
                }

                // Send response
                val response = PrintWriter(withContext(Dispatchers.IO) {
                    client.getOutputStream()
                }, true)
                response.println("HTTP/1.1 200 OK")
                response.println()
                response.println(methodResponse(method, path, routeList))

                withContext(Dispatchers.IO) {
                    client.close()
                }
            }
        }
    }
}