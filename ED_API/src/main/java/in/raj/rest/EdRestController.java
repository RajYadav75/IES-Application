package in.raj.rest;

import in.raj.bindings.EdResponse;
import in.raj.service.EligService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EdRestController {
    @Autowired
    private EligService eligService;
    @GetMapping("/elig-determine/{caseNum}")
    public ResponseEntity<EdResponse> determineElig(@PathVariable Long caseNum){
        EdResponse response = eligService.determineEligibility(caseNum);
        return new ResponseEntity<EdResponse>(response, HttpStatus.OK);
    }
    @GetMapping("/co/{caseNum}")
    public ResponseEntity<String> generateCo(Long caseNum){
        boolean status = eligService.generateCo(caseNum);
        if(status){
            return new ResponseEntity<>("Notice Generated ",HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Notice Not Generated ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
