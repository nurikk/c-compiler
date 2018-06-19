package compiler


import java.lang.Character.*
import java.util.*
import kotlin.collections.ArrayList

class Tokenizer {

    private var tokens = Stack<Token>()
    private var lastChar = ' '

    private val singleCharTokens = Arrays.asList('(', ')', '{', '}', '*', '/', '<', '>', ';', '=', '-', '+', '~', ',')
    private val doubleCharTokens = Arrays.asList("==", ">=", "<=", "*=", "++")
    private val keywords = Arrays.asList("void", "int", "return", "unsigned", "long", "if")



    private fun getToken(strItr: ListIterator<Char>): Token? {
        // Skip all spaces
        while (isWhitespace(lastChar) && strItr.hasNext()) {
            lastChar = strItr.next()
        }
        when {
            isLetter(lastChar) -> {
                var token = lastChar.toString()
                while ({ lastChar = strItr.next(); isLetterOrDigit(lastChar) }()) {
                    token += lastChar
                }
                val isToken = keywords.contains(token)

                return Token((if (isToken) "Keyword" else "Identifier"), token)
            }

            isDigit(lastChar) -> {
                var token = lastChar.toString()
                while ({ lastChar = strItr.next(); isDigit(lastChar) }()) {
                    token += lastChar
                }
                return Token("Constant", token)
            }

            singleCharTokens.contains(lastChar) -> {

                val token = lastChar.toString()
                lastChar = try {
                    strItr.next()
                } catch (e: NoSuchElementException) {
                    0.toChar()
                }

                val doubleTok = token + lastChar
                return if (doubleCharTokens.contains(doubleTok)) {
                    val t = Token("Token", doubleTok)
                    lastChar = strItr.next()
                    t
                } else {
                    Token("Token", token)
                }

            }
            lastChar == '"' -> {
                var token = lastChar.toString()
                try {
                    while ({ lastChar = strItr.next(); lastChar !=  '"'}()) {
                        token += lastChar
                    }
                } catch (e: NoSuchElementException) {}
                token+= lastChar

                lastChar = try {
                    strItr.next()
                } catch (e:NoSuchElementException) {
                    ' '
                }
                return Token("String", token)
            }

            else -> {
                return null
            }
        }
    }

    fun tokenize(str: String): Stack<Token> {
        tokens = Stack()
        val strItr = str.toList().listIterator()


        var tok: Token? = null

        while ({ tok = getToken(strItr); tok }() != null) {
            tokens.add(tok!!)
        }


        return tokens
    }
}