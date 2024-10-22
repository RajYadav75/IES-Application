package in.raj.service;


import in.raj.bindings.EdResponse;



public interface EligService {

    public EdResponse determineEligibility(Long caseNum);

    public boolean generateCo(Long caseNum);
}
