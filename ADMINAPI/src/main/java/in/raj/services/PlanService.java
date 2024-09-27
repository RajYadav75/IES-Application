package in.raj.services;

import in.raj.bindings.PlanForm;

import java.util.List;

public interface PlanService {

    public boolean createPlan(PlanForm planForm);

    public List<PlanForm> fetchPlans();
}
