package com.qdd.designmall.common.constraint.validator;

import com.qdd.designmall.common.constraint.ZPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

public class ZPatternValidator implements ConstraintValidator<ZPattern, Object> {
    private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());

    private java.util.regex.Pattern pattern;
    private String escapedRegexp;

    @Override
    public void initialize(ZPattern parameters) {
        ZPattern.Flag[] flags = parameters.flags();
        int intFlag = 0;
        for (ZPattern.Flag flag : flags) {
            intFlag = intFlag | flag.getValue();
        }

        try {
            pattern = java.util.regex.Pattern.compile(parameters.regexp(), intFlag);
        } catch (PatternSyntaxException e) {
            throw LOG.getInvalidRegularExpressionException(e);
        }

        escapedRegexp = InterpolationHelper.escapeMessageParameter(parameters.regexp());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return true;
        }

        if (constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
            constraintValidatorContext.unwrap(HibernateConstraintValidatorContext.class).addMessageParameter("regexp", escapedRegexp);
        }

        Matcher m = pattern.matcher(value.toString());
        return m.matches();
    }
}
