package oaktrees.db.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import oaktrees.data.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	@Query(value="select case when count(1)> 0 then true else false end from users c where login = :login",
			nativeQuery=true)
	int exist(String login); // 0=false, 1=true
	
	User findByLogin(String login);
	
	User findById(long id);

}
