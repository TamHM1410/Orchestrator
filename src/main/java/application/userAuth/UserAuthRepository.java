package application.userAuth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuth,Long> , JpaSpecificationExecutor<UserAuth> {
    UserAuth findDistinctFirstByUsername(String username);
}
