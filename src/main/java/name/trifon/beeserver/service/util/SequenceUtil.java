package name.trifon.beeserver.service.util;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

public class SequenceUtil {

	public static String evaluateSequenceName(Object entity, String sequenceName) {
		String result;
		ExpressionParser expressionParser = new SpelExpressionParser();
		Expression expression = expressionParser.parseExpression(sequenceName, ParserContext.TEMPLATE_EXPRESSION);
		result = expression.getValue(entity, String.class);

		return result;
	}
}
