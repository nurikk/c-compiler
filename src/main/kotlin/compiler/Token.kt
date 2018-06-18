package compiler

class Token(val token:String, val sequence:String) {
    override fun toString(): String {
        return "$token $sequence"
    }
}