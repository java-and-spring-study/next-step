package core.di.factory.example;

import core.annotation.Controller;
import core.annotation.Inject;

/**
 * Setter Dependency Injection
 */
@Controller
public class MyUserController {
    private MyUserService myUserService;

    @Inject
    public void setMyUserService(MyUserService myUserService) {
        this.myUserService =  myUserService;
    }

    public MyUserService getMyUserService() {
        return myUserService;
    }
}
