package in.raj.rest;

import in.raj.service.CoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoBatchRestController {
    @Autowired
    private CoService service;
    @GetMapping("/notices")
    public String processNotices(){
        service.processPendingTriggers();
        return "success";
    }
}