/*
 * Copyright (c) 2012, Liushangkun520@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.lianjita.validation.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.lianjita.validation.ValidateResult;
import com.lianjita.validation.ValidateService;
import com.lianjita.validation.annotation.DoubleRange;
import com.lianjita.validation.annotation.Email;
import com.lianjita.validation.annotation.IntRange;
import com.lianjita.validation.annotation.Required;
import com.lianjita.validation.annotation.StringLength;
import com.lianjita.validation.annotation.StringRegex;
import com.lianjita.validation.annotation.URL;
import com.lianjita.validation.annotation.Validation;
import com.lianjita.validation.validator.CustomValidator;
import com.lianjita.validation.validator.Validator;

/**
 * @author shangkun.liusk
 */
public class GenericValidateService implements ValidateService {

    private static final Map<Class<?>, CustomValidator<?>> GlobalValidators = new HashMap<Class<?>, CustomValidator<?>>();

    static {
        GlobalValidators.put(Required.class, new RequiredValidator());
        GlobalValidators.put(IntRange.class, new IntRangeValidator());
        GlobalValidators.put(DoubleRange.class, new DoubleRangeValidator());

        GlobalValidators.put(StringLength.class, new StringLengthValidator());
        GlobalValidators.put(StringRegex.class, new StringRegexValidator());
        GlobalValidators.put(Email.class, new EmailValidator());
        GlobalValidators.put(URL.class, new URLValidator());
    }

    private Map<Class<?>, CustomValidator<?>>              mValidatorMap    = new HashMap<Class<?>, CustomValidator<?>>();

    private Map<String, Validator>                         mNamedValidators = new HashMap<String, Validator>();

    public GenericValidateService() {
        mValidatorMap.put(Validation.class, new ValidationParameterValidator());
    }

    public ValidateResult validate(String name, Object value) throws IllegalArgumentException {
        Validator v = getValidator(name);
        if (v == null)
            return null;

        ValidateResult result = new ValidateResult();
        v.validate(value, result);
        return result;
    }

    @SuppressWarnings("unchecked")
    public ValidateResult validate(Method method, Object[] arguments) throws IllegalArgumentException {
        Annotation[][] pas = method.getParameterAnnotations();

        if (pas.length != arguments.length)
            throw new IllegalArgumentException("Arguments not enough.");

        ValidateResult result = new ValidateResult();
        for (int i = 0; i < pas.length; i++) {
            Annotation[] as = pas[i];
            for (int j = 0; j < as.length; j++) {
                CustomValidator<Annotation> validator = (CustomValidator<Annotation>) getParameterValidator(as[j]
                        .annotationType());
                if (validator != null)
                    validator.validate(arguments[i], as[j], result);
            }
        }
        return result;
    }

    public void addValidator(CustomValidator<?> pv) {
        mValidatorMap.put(pv.support(), pv);
    }

    public void addValidator(String name, Validator validator) {
        mNamedValidators.put(name, validator);
    }

    protected Validator getValidator(String name) {
        return mNamedValidators.get(name);
    }

    @SuppressWarnings("unchecked")
    protected <T extends Annotation> CustomValidator<T> getParameterValidator(Class<T> at) {
        CustomValidator<T> ret = (CustomValidator<T>) mValidatorMap.get(at);
        if (ret == null)
            return (CustomValidator<T>) GlobalValidators.get(at);
        return ret;
    }

    public class ValidationParameterValidator implements CustomValidator<Validation> {
        public Class<Validation> support() {
            return Validation.class;
        }

        public void validate(Object value, Validation an, ValidateResult result) {
            String name = an.value();
            Validator validator = getValidator(name);
            if (validator != null)
                validator.validate(value, result);
        }
    }

    public static class RequiredValidator implements CustomValidator<Required> {
        public Class<Required> support() {
            return Required.class;
        }

        public void validate(Object value, Required an, ValidateResult result) {
            String msg = an.message();
            if (value == null)
                result.addError(msg);
        }
    }

    public static class IntRangeValidator implements CustomValidator<IntRange> {
        public Class<IntRange> support() {
            return IntRange.class;
        }

        public void validate(Object value, IntRange an, ValidateResult result) {
            String msg = an.message();
            if (value == null || (value instanceof Integer == false)) {
                result.addError(msg);
                return;
            }

            int min = an.min(), max = an.max();
            Integer val = (Integer) value;
            if (val.intValue() > max || val.intValue() < min)
                result.addError(msg);
        }
    }

    public static class DoubleRangeValidator implements CustomValidator<DoubleRange> {
        public Class<DoubleRange> support() {
            return DoubleRange.class;
        }

        public void validate(Object value, DoubleRange an, ValidateResult result) {
            String msg = an.message();
            if (value == null || (value instanceof Double == false)) {
                result.addError(msg);
                return;
            }

            double min = an.min(), max = an.max();
            Double val = (Double) value;
            if (val.doubleValue() > max || val.doubleValue() < min)
                result.addError(msg);
        }
    }

    public static class StringLengthValidator implements CustomValidator<StringLength> {
        public Class<StringLength> support() {
            return StringLength.class;
        }

        public void validate(Object value, StringLength an, ValidateResult result) {
            String msg = an.message();
            if (value == null || (value instanceof String == false)) {
                result.addError(msg);
                return;
            }

            int min = an.minLength(), max = an.maxLength();
            String val = (String) value;
            if (val.length() > max || val.length() < min)
                result.addError(msg);
        }
    }

    public static class StringRegexValidator implements CustomValidator<StringRegex> {
        public Class<StringRegex> support() {
            return StringRegex.class;
        }

        public void validate(Object value, StringRegex an, ValidateResult result) {
            String msg = an.message();
            if (value == null)
                return;

            if (value instanceof String == false) {
                result.addError(msg);
                return;
            }

            String val = (String) value;
            if (!val.matches(an.regex()))
                result.addError(msg);
        }
    }

    public static class EmailValidator implements CustomValidator<Email> {
        public Class<Email> support() {
            return Email.class;
        }

        public void validate(Object value, Email an, ValidateResult result) {
            String msg = an.message();
            if (value == null)
                return;

            if (value instanceof String == false) {
                result.addError(msg);
                return;
            }

            String val = (String) value;
            if (!val.matches("[A-Za-z0-9]+(\\.[_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*"))
                result.addError(msg);
        }
    }

    public static class URLValidator implements CustomValidator<URL> {
        public Class<URL> support() {
            return URL.class;
        }

        public void validate(Object value, URL an, ValidateResult result) {
            String msg = an.message();
            if (value == null)
                return;

            if (value instanceof String == false) {
                result.addError(msg);
                return;
            }

            String val = (String) value;
            if (!val.matches("[a-zA-Z]+://[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*"))
                result.addError(msg);
        }
    }

}
