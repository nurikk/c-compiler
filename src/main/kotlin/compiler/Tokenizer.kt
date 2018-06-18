package compiler


import java.util.regex.Pattern

class Tokenizer {
    private val tokenInfos = ArrayList<TokenInfo>()
    private var tokens = ArrayList<Token>()


    private fun add(regex:Pattern, token:String) {
        tokenInfos.add(TokenInfo(regex, token))
    }
    fun tokenize(str:String):ArrayList<Token> {
        var s = str.trim({ it <= ' ' })
        tokens = ArrayList()
        while (s.isNotBlank())
        {
            var match = false
            for (info in tokenInfos)
            {
                val m = info.regex.matcher(s)
                if (m.find())
                {
                    match = true
                    val tok = m.group().trim({ it <= ' ' })
                    s = m.replaceFirst("").trim({ it <= ' ' })
                    tokens.add(Token(info.token, tok))
                    break
                }
            }
            if (!match) throw Exception("Unexpected character in input: $s")
        }
        return tokens
    }
    
    
    init {

        this.add(Pattern.compile("^([a-zA-Z]\\w*)"), "Identifier")

        this.add(Pattern.compile("^(\\()"), "Open parenthesis")
        this.add(Pattern.compile("^(\\))"), "Close parenthesis")

        this.add(Pattern.compile("^(\\{)"), "Open brace")
        this.add(Pattern.compile("^(})"), "Close brace")

        this.add(Pattern.compile("^(;)"), "Semicolon")
        this.add(Pattern.compile("^([=*])"), "Operator")

        this.add(Pattern.compile("^(int)"), "Keyword")
        this.add(Pattern.compile("^(return)"), "Keyword")

        this.add(Pattern.compile("^([0-9]+)"), "Constant")

        this.add(Pattern.compile("^(-)"), "Negation")
        this.add(Pattern.compile("^(~)"), "Bitwise complement")
        this.add(Pattern.compile("^(!)"), "Logical negation")

//







    }
}