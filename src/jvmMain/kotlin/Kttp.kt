import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket

actual interface KttpInterface {
    actual var routeList: ArrayList<Route>

    actual fun run(address: String, port: Int) {
        val server = ServerSocket(port, 50, InetAddress.getByName(address))
        while (true) {
            val client = server.accept()

            // Get query
            val input = BufferedReader(InputStreamReader(client.getInputStream()))
            var query = String()
            var line: String
            do {
                line = input.readLine()
                query += line
            } while (line.isNotBlank())

            // Send response
            val response = PrintWriter(client.getOutputStream(), true)
            response.println("HTTP/1.1 200 OK")
            response.println()

            client.close()
        }
    }
}