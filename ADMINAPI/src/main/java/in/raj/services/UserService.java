package in.raj.services;

import in.raj.bindings.DashboardCard;
import in.raj.bindings.LoginForm;

public interface UserService {

    public String login(LoginForm loginForm);

    public boolean recoverPassword(String email);

    public DashboardCard fetchDashboardInfo();

}
