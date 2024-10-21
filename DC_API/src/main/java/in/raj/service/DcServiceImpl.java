package in.raj.service;

import in.raj.binding.*;
import in.raj.entities.*;
import in.raj.repo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DcServiceImpl implements DcService{

    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private PlanSelRepo planSelRepo;
    @Autowired
    private IncomeRepo incomeRepo;
    @Autowired
    private EducationRepo educationRepo;
    @Autowired
    private KidRepo kidRepo;
    @Autowired
    private AppRepo appRepo;

    @Override
    public Map<Integer, String> getPlans() {
        Map<Integer,String> plansMap = new HashMap<>();
        List<PlanEntity> entities = planRepo.findAll();
        for (PlanEntity entity: entities){
            plansMap.put(entity.getPlanId(),entity.getPlanName());
        }
        return plansMap;
    }

    @Override
    public boolean savePlanSelection(PlanSelection planSel) {
        PlanSelEntity entity = new PlanSelEntity();
        BeanUtils.copyProperties(planSel,entity);
        entity = planSelRepo.save(entity);
        return entity.getPlanSelId()>0;
    }

    @Override
    public boolean saveIncome(Income income) {
        IncomeEntity entity = new IncomeEntity();
        BeanUtils.copyProperties(income,entity);
        entity = incomeRepo.save(entity);
        return entity.getIncomeId() > 0;
    }

    @Override
    public boolean saveEducation(Education education) {
        EducationEntity entity = new EducationEntity();
        BeanUtils.copyProperties(education,entity);
        entity = educationRepo.save(entity);
        return entity.getEduId() > 0;
    }

    @Override
    public boolean saveKids(Kids kids) {
        List<Kid> kidsList = kids.getKidsList();
        Integer caseNum = kids.getCaseNum();
        for (Kid kid:kidsList){
            KidEntity entity =  new KidEntity();
            BeanUtils.copyProperties(kid,entity);
            kidRepo.save(entity);
        }
        return true;
    }

    @Override
    public DcSummary fetchSummaryInfo(Long caseNum) {
        DcSummary summary = new DcSummary();
        Optional<AppEntity> findById = appRepo.findById(caseNum);
        if (findById.isPresent()){
            AppEntity appEntity = findById.get();

            IncomeEntity income = appEntity.getIncome();
            EducationEntity education = appEntity.getEducation();
            List<KidEntity> kids = appEntity.getKid();
            // TODO Set Data to summary object
        }
        return summary;
    }
}
