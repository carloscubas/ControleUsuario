package br.cubas.usercontrol.controllers;

import br.cubas.usercontrol.beans.User;
import br.cubas.usercontrol.services.SecurityService;
import br.cubas.usercontrol.services.UserService;
import br.cubas.usercontrol.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public ModelAndView login() {
        return new ModelAndView("/login");
    }

    @GetMapping(value = "/registration")
    public ModelAndView registration() {

        return new ModelAndView("user/registration", "userForm", new User());
    }

    @PostMapping(value = "/registration")
    public ModelAndView registrationform(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
                                         Model model) {

        userValidator.validate(userForm, bindingResult);

        if (bindingResult.hasErrors()) {
            return new ModelAndView("user/registration");
        }

        String password = userForm.getPassword();
        userService.save(userForm);

        try {
            securityService.login(userForm.getUsername(), password);
            return new ModelAndView("redirect:/");
        } catch (Exception e) {
            return new ModelAndView("redirect:/user/login");
        }
    }
}
