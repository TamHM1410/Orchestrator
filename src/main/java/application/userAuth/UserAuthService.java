package application.userAuth;


public interface UserAuthService {
    void increaseFailLoginAttemptsWhenLoginFail(String username);
    UserAuth  createOrUpdateUserAuth(UserAuth userAuth);
}
