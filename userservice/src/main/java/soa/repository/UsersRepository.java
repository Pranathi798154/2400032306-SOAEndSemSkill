package soa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import soa.models.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {
	
	@Query("select U from Users U where U.username=:username and U.password=:password")
	public Users validateCredentials(@Param("username") String username,@Param("password") String password);
	
}
