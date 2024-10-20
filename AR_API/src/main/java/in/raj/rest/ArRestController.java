package in.raj.rest;

import in.raj.binding.App;
import in.raj.service.ArService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//7
@RestController
public class ArRestController {

    @Autowired
    private ArService arService;

    @PostMapping("/app")
    public ResponseEntity<String> createApp(@RequestBody App app){
        String status = arService.createApplication(app);

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/apps/{userId}")
    public List<App> getApps(@PathVariable Integer userId){
        return arService.fetchApps(userId);
    }

}
