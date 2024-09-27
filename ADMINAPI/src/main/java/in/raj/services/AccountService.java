package in.raj.services;

import in.raj.bindings.UserAccountForm;

import java.util.List;

public interface AccountService {

    public boolean createUserAccount(UserAccountForm accForm);

    public List<UserAccountForm> fetchUserAccounts();
}
