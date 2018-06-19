package compiler

import org.junit.Test

import kotlin.test.assertEquals

class TokenizerTest {
    @Test
    fun simple(){
        val tokenizer = Tokenizer()

        val code = """ void main() {     int answer = 6 * 7;   }   """


        val tokens = tokenizer.tokenize(code)
        assertEquals("Keyword void\n" +
                "Identifier main\n" +
                "Token (\n" +
                "Token )\n" +
                "Token {\n" +
                "Keyword int\n" +
                "Identifier answer\n" +
                "Token =\n" +
                "Constant 6\n" +
                "Token *\n" +
                "Constant 7\n" +
                "Token ;\n" +
                "Token }", tokens.joinToString("\n"))


        val tokens2 = tokenizer.tokenize("int main() { return 2; }")
        assertEquals("Keyword int\n" +
                "Identifier main\n" +
                "Token (\n" +
                "Token )\n" +
                "Token {\n" +
                "Keyword return\n" +
                "Constant 2\n" +
                "Token ;\n" +
                "Token }", tokens2.joinToString("\n"))

    }

    @Test
    fun negotiation(){
        val tokenizer = Tokenizer()
        assertEquals("Keyword int\n" +
                "Identifier main\n" +
                "Token (\n" +
                "Token )\n" +
                "Token {\n" +
                "Keyword return\n" +
                "Constant 1\n" +
                "Token -\n" +
                "Constant 3\n" +
                "Token }",tokenizer.tokenize("int main() { return 1 - 3 }").joinToString("\n") )
    }


    @Test
    fun bitwiseComplement(){
        val tokenizer = Tokenizer()
        assertEquals("Keyword int\n" +
                "Identifier main\n" +
                "Token (\n" +
                "Token )\n" +
                "Token {\n" +
                "Keyword return\n" +
                "Constant 1\n" +
                "Token ~\n" +
                "Constant 3\n" +
                "Token }",tokenizer.tokenize("int main() { return 1 ~ 3 }").joinToString("\n") )
    }

    @Test
    fun factorial(){
        val tokenizer = Tokenizer()
        val factorialCode = """
            int main()
                {

                    int n = 10;
                    unsigned long long factorial = 1;




                    if (n < 0)
                        printf("Error! Factorial of a negative number doesn't exist.");

                    else
                    {
                        for(int i=1; i<=n; ++i)
                        {
                            factorial *= i;
                        }
                        printf("Factorial of %d = %llu", n, factorial);
                    }

                    return 0;
                }
        """.trimIndent()
        assertEquals("Keyword int\n" +
                "Identifier main\n" +
                "Token (\n" +
                "Token )\n" +
                "Token {\n" +
                "Keyword int\n" +
                "Identifier n\n" +
                "Token =\n" +
                "Constant 10\n" +
                "Token ;\n" +
                "Keyword unsigned\n" +
                "Keyword long\n" +
                "Keyword long\n" +
                "Identifier factorial\n" +
                "Token =\n" +
                "Constant 1\n" +
                "Token ;\n" +
                "Keyword if\n" +
                "Token (\n" +
                "Identifier n\n" +
                "Token <\n" +
                "Constant 0\n" +
                "Token )\n" +
                "Identifier printf\n" +
                "Token (\n" +
                "String \"Error! Factorial of a negative number doesn't exist.\"\n" +
                "Token )\n" +
                "Token ;\n" +
                "Identifier else\n" +
                "Token {\n" +
                "Identifier for\n" +
                "Token (\n" +
                "Keyword int\n" +
                "Identifier i\n" +
                "Token =\n" +
                "Constant 1\n" +
                "Token ;\n" +
                "Identifier i\n" +
                "Token <=\n" +
                "Identifier n\n" +
                "Token ;\n" +
                "Token ++\n" +
                "Identifier i\n" +
                "Token )\n" +
                "Token {\n" +
                "Identifier factorial\n" +
                "Token *=\n" +
                "Identifier i\n" +
                "Token ;\n" +
                "Token }\n" +
                "Identifier printf\n" +
                "Token (\n" +
                "String \"Factorial of %d = %llu\"\n" +
                "Token ,\n" +
                "Identifier n\n" +
                "Token ,\n" +
                "Identifier factorial\n" +
                "Token )\n" +
                "Token ;\n" +
                "Token }\n" +
                "Keyword return\n" +
                "Constant 0\n" +
                "Token ;\n" +
                "Token }", tokenizer.tokenize(factorialCode).joinToString("\n"))
    }

}

