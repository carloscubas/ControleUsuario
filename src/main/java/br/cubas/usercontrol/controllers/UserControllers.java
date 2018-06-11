package br.cubas.usercontrol.controllers;

import br.cubas.usercontrol.beans.User;
import br.cubas.usercontrol.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserControllers {

	@Autowired
	private UserService userService;

	@GetMapping("/list")
	public ModelAndView list() {
		List<User> users = userService.findAll();
		return new ModelAndView("/user/list", "users", users);
	}

	@GetMapping("/listadmin")
	public ModelAndView listadmin(User user) {
		List<User> users = userService.findAll();
		return new ModelAndView("/user/listadmin", "users", users);
	}
}
