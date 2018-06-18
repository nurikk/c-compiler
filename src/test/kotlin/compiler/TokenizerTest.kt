package compiler

import org.junit.Test
import kotlin.test.assertEquals

class TokenizerTest {
    @Test
    fun simple(){
        val tokenizer = Tokenizer()

        val code = """ void main() {     int answer = 6 * 7;     }   """


        val tokens = tokenizer.tokenize(code)
        assertEquals("Identifier void\n" +
                "Identifier main\n" +
                "Open parenthesis (\n" +
                "Close parenthesis )\n" +
                "Open brace {\n" +
                "Identifier int\n" +
                "Identifier answer\n" +
                "Operator =\n" +
                "Constant 6\n" +
                "Operator *\n" +
                "Constant 7\n" +
                "Semicolon ;\n" +
                "Close brace }", tokens.joinToString("\n"))


        val tokens2 = tokenizer.tokenize("int main() { return 2; }")
        assertEquals("Identifier int\n" +
                "Identifier main\n" +
                "Open parenthesis (\n" +
                "Close parenthesis )\n" +
                "Open brace {\n" +
                "Identifier return\n" +
                "Constant 2\n" +
                "Semicolon ;\n" +
                "Close brace }", tokens2.joinToString("\n"))

    }

    @Test
    fun negotiation(){
        val tokenizer = Tokenizer()
        assertEquals("Identifier int\n" +
                "Identifier main\n" +
                "Open parenthesis (\n" +
                "Close parenthesis )\n" +
                "Open brace {\n" +
                "Identifier return\n" +
                "Constant 1\n" +
                "Negation -\n" +
                "Constant 3\n" +
                "Close brace }",tokenizer.tokenize("int main() { return 1 - 3 }").joinToString("\n") )
    }


    @Test
    fun BitwiseComplement(){
        val tokenizer = Tokenizer()
        assertEquals("Identifier int\n" +
                "Identifier main\n" +
                "Open parenthesis (\n" +
                "Close parenthesis )\n" +
                "Open brace {\n" +
                "Identifier return\n" +
                "Constant 1\n" +
                "Bitwise complement ~\n" +
                "Constant 3\n" +
                "Close brace }",tokenizer.tokenize("int main() { return 1 ~ 3 }").joinToString("\n") )
    }

}

