package in.raj.rest;

import in.raj.bindings.DashboardCard;
import in.raj.bindings.LoginForm;
import in.raj.bindings.UserAccountForm;
import in.raj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginForm loginForm){
        String status = userService.login(loginForm);
        if(status.equals("success")){
            //TODO => Login Success
            return "redirect:/dashboard?email="+loginForm.getEmail();
        }else{
            return status;
        }
    }
    @GetMapping("/dashboard")
    public ResponseEntity<DashboardCard> buildDashboard(@RequestParam("email") String email){
        UserAccountForm user = userService.getUserByEmail(email);
        DashboardCard dashboardCard = userService.fetchDashboardInfo();
        dashboardCard.setUser(user);
        return new ResponseEntity<>(dashboardCard, HttpStatus.OK);
    }
}
