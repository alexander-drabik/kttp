import java.net.ServerSocket

actual interface KttpInterface {
    actual fun run(address: String, port: Int) {
        var socket = ServerSocket()

    }
}