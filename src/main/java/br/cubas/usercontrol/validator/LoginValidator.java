package br.cubas.usercontrol.validator;

import br.cubas.usercontrol.services.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.cubas.usercontrol.beans.User;
import br.cubas.usercontrol.services.UserService;
import org.springframework.web.servlet.ModelAndView;


@Component
public class LoginValidator implements Validator {
	
	@Autowired
	private UserService userService;

    @Autowired
    private SecurityService securityService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		
		User user = (User) o;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty");
		
		if (userService.findByUsername(user.getUsername()) == null) {
			errors.rejectValue("username", "NotExist.userForm.username");
		}

        try {
            securityService.login(user.getUsername(), user.getPassword());
        }catch (BadCredentialsException e){
            errors.rejectValue("password", "BadCredentials.userForm.username");
        }
		
	}
}