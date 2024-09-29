package in.raj.rest;

import in.raj.bindings.UserAccountForm;
import in.raj.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {
    @Autowired
    private AccountService accountService;
    @PostMapping("/user")
    public ResponseEntity<String> createAccount(@RequestBody UserAccountForm userAccountForm){
        boolean status = accountService.createUserAccount(userAccountForm);
        if (status){
            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);  //201
        }else {
            return new ResponseEntity<>("Account Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserAccountForm>> getUsers(){
        List<UserAccountForm> userAccountForms =
                accountService.fetchUserAccounts();
        return new ResponseEntity<>(userAccountForms,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAccountForm> getUser(@PathVariable("userId") Integer userId){
        UserAccountForm userAccById = accountService.getUserAccById(userId);
        return new ResponseEntity<>(userAccById,HttpStatus.OK);
    }
    @PutMapping("/user/{userId}/{status}")
    public ResponseEntity<List<UserAccountForm>> updateUserAccount(@PathVariable("userId")Integer userId,
                                                                   @PathVariable("status") String status){
        accountService.changeAccStatus(userId, status);
        List<UserAccountForm> userAccountForms = accountService.fetchUserAccounts();
        return new ResponseEntity<>(userAccountForms,HttpStatus.OK);
    }
}
