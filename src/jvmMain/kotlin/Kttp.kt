import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket
import kotlin.contracts.contract

internal actual interface KttpInterface {
    actual var routeList: ArrayList<Route>

    actual fun run(address: String, port: Int) {
        val methodRegex = """^\S+""".toRegex()
        val pathRegex   = """/\S*""".toRegex()

        val server = ServerSocket(port, 50, InetAddress.getByName(address))
        while (true) {
            val client = server.accept()

            // Get request
            val input = BufferedReader(InputStreamReader(client.getInputStream()))
            var line = input.readLine() ?: String()

            // Get request's method
            val method = methodRegex.find(line)?.value
            if(method == null) {
                client.close()
                continue
            }

            // Find path
            val path = pathRegex.find(line)?.value ?: ""

            // Find request header
            while (line.isNotBlank()) {
                line = input.readLine() ?: break
            }

            // Send response
            val response = PrintWriter(client.getOutputStream(), true)
            response.println("HTTP/1.1 200 OK")
            response.println()
            response.println(methodResponse(method, path, routeList))

            client.close()
        }
    }

}