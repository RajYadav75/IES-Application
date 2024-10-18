package in.raj.services;

import in.raj.bindings.PlanForm;
import in.raj.entities.PlanEntity;
import in.raj.repositories.PlanRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanServiceImpl implements PlanService{
    @Autowired
    private PlanRepo planRepo;
    @Override
    public boolean createPlan(PlanForm planForm) {
        PlanEntity entity = new PlanEntity();
        BeanUtils.copyProperties(planForm,entity);
        return false;
    }

    @Override
    public List<PlanForm> fetchPlans() {
        List<PlanEntity> planEntities = planRepo.findAll();
        List<PlanForm> plans = new ArrayList<>();
        for (PlanEntity planEntity : planEntities){
            PlanForm plan = new PlanForm();
            BeanUtils.copyProperties(planEntities,plan);
            plans.add(plan);
        }
        return plans;
    }

    @Override
    public PlanForm getPlanById(Integer planId) {
        Optional<PlanEntity> optional = planRepo.findById(planId);
        if (optional.isPresent()){
            PlanEntity planEntity = new PlanEntity();
            PlanForm plan = new PlanForm();
            BeanUtils.copyProperties(planEntity,plan);
            return plan;
        }
        return null;
    }

    @Override
    public String changePlanStatus(Integer planId, String status) {
        int count = planRepo.changePlanStatus(planId, status);
        if(count>0){
            return "Status Changed";
        }else {
            return "Faild to change";
        }
    }
}
