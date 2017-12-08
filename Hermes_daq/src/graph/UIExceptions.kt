package graph

class UINotFoundStateException(message: String) : Exception() {
    private var  msg = if (message.isEmpty())"Not found state" else message
    override val message: String?
        get() = msg
}

class UIAutomatonInvalidException(message: String) : Exception() {
    private var  msg = if (message.isEmpty())"Not found automaton" else message
    override val message: String?
        get() = msg
}