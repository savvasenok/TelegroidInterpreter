import data.Token
import data.TokenType
import kotlin.RuntimeException

class Parser(private val tokens: List<Token>) {

    private var expectedTokenStack = ExpectedTokenStack()

    fun parse(): Boolean = try {
        var lastToken: Token

        for (token in tokens) {

            if (expectedTokenStack.isEmpty()) {
                expectedTokenStack.put(token)
                continue
            }

            lastToken = expectedTokenStack.peek()

            if (lastToken.expectedType() == token.type) {
                expectedTokenStack.pop()
                expectedTokenStack.put(token)
            } else {
                throw RuntimeException("Expected ${lastToken.expectedType()} after $lastToken, but received $token")
            }
        }

        true
    } catch (e: Exception) {
        println("Exception: $e")
        false
    }


    private fun Token.expectedType(): TokenType = when (this.type) {
        TokenType.INT -> TokenType.OP
        TokenType.OP -> TokenType.INT
        else -> throw RuntimeException("Unexpected TokenType.${this.type}")
    }

    private inner class ExpectedTokenStack {

        private val stack = mutableListOf<Token>()

        fun pop(): Token {
            if (isEmpty()) throw RuntimeException("StackUnderFlow")
            return stack.removeAt(stack.lastIndex)
        }

        fun peek(): Token {
            if (isEmpty()) throw RuntimeException("StackUnderFlow")
            return stack[stack.lastIndex]
        }

        fun put(item: Token) {
            stack.add(item)
        }

        fun isEmpty(): Boolean = stack.isEmpty()
    }
}