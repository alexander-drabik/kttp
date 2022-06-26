import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.InetAddress
import java.net.ServerSocket

internal val methodRegex = """^\S+""".toRegex()
internal val pathRegex   = """\w+\s(/[^\s?]*)\??(\S*)""".toRegex()
internal val pathVariablesRegex = """&?([^&]+)=([^&]+)""".toRegex()

internal actual interface KttpInterface {
    actual var routeList: ArrayList<Route>

    actual fun run(address: String, port: Int) {
        // Start listening at given port and address
        val server = ServerSocket(port, 50, InetAddress.getByName(address))
        while (true) {
            val client = server.accept()

            // Check client request inside coroutine, so other clients can connect at the same time
            GlobalScope.launch {
                // Get request
                val input = BufferedReader(InputStreamReader(withContext(Dispatchers.IO) {
                    client.getInputStream()
                }))
                var line = withContext(Dispatchers.IO) {
                    input.readLine()
                } ?: String()

                // Get request's method
                val method = methodRegex.find(line)?.value ?: ""

                // Find path
                val entirePath = pathRegex.find(line)?.groups
                val path = findPath(entirePath)

                // Find path variables
                val pathVariablesCombined = entirePath?.get(2)?.value.toString()
                val pathVariables = findPathVariables(pathVariablesCombined)

                // Find request headers
                while (line.isNotBlank()) {
                    line = withContext(Dispatchers.IO) {
                        input.readLine()
                    } ?: break
                }

                // Generate Request
                val request = Request(
                    pathVariables
                )

                // Send response
                val response = PrintWriter(
                    withContext(Dispatchers.IO) {
                        client.getOutputStream()
                    },
                    true
                )
                response.println(methodResponse(method, path, routeList, request))

                // End connection
                withContext(Dispatchers.IO) {
                    client.close()
                }
            }
        }
    }

    private fun findPath(entirePath: MatchGroupCollection?): String {
        return entirePath?.get(1)?.value.toString()
    }

    private fun findPathVariables(pathVariablesCombined: String): HashMap<String, String> {
        val pathVariables = HashMap<String, String>()
        val matches = pathVariablesRegex.findAll(pathVariablesCombined)
        for (match in matches) {
            if (match.groups.size != 3) continue
            pathVariables[match.groups[1]?.value.toString()] = match.groups[2]?.value.toString()
        }
        return pathVariables
    }
}