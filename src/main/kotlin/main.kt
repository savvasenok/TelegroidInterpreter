fun main() {
    val code = "6 /  2*   3+   1-     10" // 0

    val lexer = Lexer(code)
    val tokens = lexer.generateTokens()

    val parser = Parser(tokens)
    parser.parse()
}