package in.raj.services;

import in.raj.bindings.DashboardCard;
import in.raj.bindings.LoginForm;
import in.raj.bindings.UserAccountForm;

public interface UserService {

    public String login(LoginForm loginForm);

    public boolean recoverPassword(String email);

    public DashboardCard fetchDashboardInfo();

    public UserAccountForm getUserByEmail(String email);

}
