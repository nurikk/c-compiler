package compiler

import org.junit.Test
import java.util.*

import kotlin.test.assertEquals


class AstTest {
    @Test
    fun intDefinition(){
        val tokens = Stack<Token>()
        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "foo"))
        tokens.push(Token("Token", "="))
        tokens.push(Token("Constant", "2"))
        tokens.push(Token("Token", ";"))
        tokens.reverse()
        val tree = AstTree(tokens)
        assertEquals("\tint foo=2", tree.toString())
    }


    @Test
    fun voidFunctionDefinition(){
        val tokens = Stack<Token>()
        tokens.push(Token("Keyword", "void"))
        tokens.push(Token("Identifier", "main"))
        tokens.push(Token("Token", "("))
        tokens.push(Token("Token", ")"))
        tokens.push(Token("Token", "{"))
        tokens.push(Token("Token", "}"))
        tokens.reverse()
        val tree = AstTree(tokens)
        assertEquals("void main() {\n" +
                "\n" +
                "}", tree.toString())
    }


    @Test
    fun intFunctionDefinition(){
        val tokens = Stack<Token>()
        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "main"))
        tokens.push(Token("Token", "("))
        tokens.push(Token("Token", ")"))
        tokens.push(Token("Token", "{"))
        tokens.push(Token("Token", "}"))
        tokens.reverse()
        val tree = AstTree(tokens)
        assertEquals("int main() {\n" +
                "\n" +
                "}", tree.toString())
    }


    @Test
    fun intFunctionDefinitionWithIntVariableInside(){
        val tokens = Stack<Token>()
        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "main"))
        tokens.push(Token("Token", "("))
        tokens.push(Token("Token", ")"))
        tokens.push(Token("Token", "{"))

        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "foo"))
        tokens.push(Token("Token", "="))
        tokens.push(Token("Constant", "2"))
        tokens.push(Token("Token", ";"))

        tokens.push(Token("Token", "}"))
        tokens.reverse()
        val tree = AstTree(tokens)
        assertEquals("int main() {\n" +
                "\tint foo=2\n" +
                "}", tree.toString())
    }


    @Test
    fun intFunctionDefinitionWithTwoIntVariableInside(){
        val tokens = Stack<Token>()
        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "main"))
        tokens.push(Token("Token", "("))
        tokens.push(Token("Token", ")"))
        tokens.push(Token("Token", "{"))

        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "foo"))
        tokens.push(Token("Token", "="))
        tokens.push(Token("Constant", "2"))
        tokens.push(Token("Token", ";"))


        tokens.push(Token("Keyword", "int"))
        tokens.push(Token("Identifier", "bar"))
        tokens.push(Token("Token", "="))
        tokens.push(Token("Constant", "10"))
        tokens.push(Token("Token", ";"))

        tokens.push(Token("Token", "}"))
        tokens.reverse()
        val tree = AstTree(tokens)
        assertEquals("int main() {\n" +
                "\tint foo=2\t\n" +
                "\tint bar=10\n" +
                "}", tree.toString())
    }
}