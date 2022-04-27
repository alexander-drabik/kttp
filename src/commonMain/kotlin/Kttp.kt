expect interface KttpInterface {
    public open fun run(address: String, port: Int)
}

class Kttp : KttpInterface

