package br.cubas.usercontrol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.cubas.usercontrol.beans.User;

@Repository
public interface UserRepository 
	extends JpaRepository<User, String> {
	
	public User findByUsername(String username);
	
}
