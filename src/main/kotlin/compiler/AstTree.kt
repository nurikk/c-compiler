package compiler

import java.util.*
import kotlin.collections.ArrayList

open class Expression

class IntVar(val name:String, val value: Int) :Expression() {
    override fun toString(): String {
        return "\tint $name=$value"
    }
}


class FunctionDefinition(val name:String, val body: AstTree, val type: String) :Expression() {
    override fun toString(): String {
        return "$type $name() {\n${body}\n}"
    }
}

class AstTree (val tokens : Stack<Token>) {
    private val root = ArrayList<Expression>()

    fun consume(type: String, value: String):Token {
        val t = tokens.pop()
        if (t.type != type && t.value != value) {
            throw Exception("parse error, expected $type, got ${t.type}")
        }
        return t

    }


    init {
        while (tokens.isNotEmpty()) {
            val token = tokens.pop()


            when (token.type){
                "Keyword" -> {
                    when(token.value) {
                        "int", "void" -> {
                            val name = tokens.pop()
                            val nextToken = tokens.pop()
                            when (nextToken.type) {
                                "Token" -> {
                                    when (nextToken.value) {
                                        "=" -> {
                                            val valueToken = tokens.pop()
                                            consume("Token", ";")

                                            when(token.value) {
                                                "int" -> root.add(IntVar(name.value, valueToken.value.toInt()))
                                            }

                                        }

                                        "(" -> { //function declaration begin
                                            //Assume no function arguments
                                            consume("Token", ")")
                                            consume("Token", "{")

                                            var openedBraces = 0
                                            val bodyTokens = Stack<Token>()
                                            var funBodyTok = tokens.pop()
                                            do {

                                                bodyTokens.push(funBodyTok)
                                                if (tokens.isNotEmpty()){
                                                    funBodyTok = tokens.pop()
                                                }

                                            } while (tokens.isNotEmpty() && funBodyTok.toString() != "Token }" && openedBraces == 0)

                                            bodyTokens.reverse()
                                            root.add(FunctionDefinition(name.value, AstTree(bodyTokens), token.value))


                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                "Token" -> {

                }

                "Constant" -> {

                }

                "Identifier" -> {

                }
            }
        }

    }

    override fun toString(): String {
        return root.joinToString("\t\n")
    }
}