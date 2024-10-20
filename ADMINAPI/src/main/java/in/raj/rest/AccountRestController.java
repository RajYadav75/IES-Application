package in.raj.rest;

import in.raj.bindings.UserAccountForm;
import in.raj.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountRestController {
    private Logger logger = LoggerFactory.getLogger(AccountRestController.class);
    @Autowired
    private AccountService accountService;
    @PostMapping("/user")
    public ResponseEntity<String> createAccount(@RequestBody UserAccountForm userAccountForm){
        logger.debug("Account Creation Process Started...");
        boolean status = accountService.createUserAccount(userAccountForm);
        logger.debug("Account Creation Process Completed...");
        if (status){
            logger.info("Account Created Successfully...");
            return new ResponseEntity<>("Account Created", HttpStatus.CREATED);  //201
        }else {
            logger.info("Account Creation Failed...");
            return new ResponseEntity<>("Account Creation Failed", HttpStatus.INTERNAL_SERVER_ERROR); //500
        }
    }
    @GetMapping("/users")
    public ResponseEntity<List<UserAccountForm>> getUsers(){
        logger.debug("Fetching User Account process Started..");
        List<UserAccountForm> userAccountForms =
                accountService.fetchUserAccounts();
        logger.debug("Fetching User Account process Completed..");
        logger.info("User Accounts Fetched Success...");
        return new ResponseEntity<>(userAccountForms,HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserAccountForm> getUser(@PathVariable("userId") Integer userId){
        UserAccountForm userAccById = accountService.getUserAccById(userId);
        logger.info("User Account Fetched Successfuly..");
        return new ResponseEntity<>(userAccById,HttpStatus.OK);
    }
    @PutMapping("/user/{userId}/{status}")
    public ResponseEntity<List<UserAccountForm>> updateUserAccount(@PathVariable("userId")Integer userId,
                                                                   @PathVariable("status") String status){
        logger.debug("User Account update process Started..");
        accountService.changeAccStatus(userId, status);
        logger.debug("User Account update process Completed..");
        logger.info("User Account Status Updated Successfully..");
        List<UserAccountForm> userAccountForms = accountService.fetchUserAccounts();
        return new ResponseEntity<>(userAccountForms,HttpStatus.OK);
    }
}
