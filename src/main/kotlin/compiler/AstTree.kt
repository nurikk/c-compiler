package compiler

class AstTree (tokens : ArrayList<Token>) {
    val root = AstNode(null, null, null)
    init {
        for (token in tokens) {
            System.out.printf("token: %s", token)
        }
    }

    override fun toString(): String {
        return root.toString()
    }
}