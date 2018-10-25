package se.netwomen.NetWomenBackend.controller.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import se.netwomen.NetWomenBackend.model.data.Post;

public class PostValidator implements SmartValidator {
    @Override
    public void validate(Object o, Errors errors, Object... objects) {
        this.validatePost(o, errors);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Post.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        this.validatePost(o, errors);
    }

    public void validatePost(Object o, Errors errors) {

        // example check
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "The post text can't be empty");

        Post post = (Post) o;

        // more checks here, for example collect something from db and compare etc


    }
}
