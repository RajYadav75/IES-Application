package in.raj.services;

import in.raj.bindings.DashboardCard;
import in.raj.bindings.LoginForm;
import in.raj.bindings.UserAccountForm;
import in.raj.entities.EligibilityEntity;
import in.raj.entities.UserEntity;
import in.raj.repositories.EligRepo;
import in.raj.repositories.PlanRepo;
import in.raj.repositories.UserRepo;
import in.raj.utils.EmailUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtil emailUtils;
    @Autowired
    private PlanRepo planRepo;
    @Autowired
    private EligRepo eligRepo;

    @Override
    public String login(LoginForm loginForm) {
        UserEntity entity = userRepo.findByEmailAndPwd(loginForm.getEmail(), loginForm.getPwd());
        if (entity == null) {
            return "Invalid Credentials";
        }
        if ("Y".equals(entity.getActiveSw()) && "UNLOCKED".equals(entity.getAccStatus())) {
            return "success";
        } else {
            return "Account Locked/In-Active";
        }
    }

    @Override
    public boolean recoverPassword(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        if (null == userEntity) {
            return false;
        } else {
            String subject = "Recover Pwd";
            String body = readEmailBody("FORGOT_PWD_EMAIL_BODY.txt",userEntity);
            return emailUtils.sendEmail(subject, body, email);
        }
    }

    @Override
    public DashboardCard fetchDashboardInfo() {
        long plansCount = planRepo.count();
        List<EligibilityEntity> eligList = eligRepo.findAll();
        long approvedCnt =
                eligList.stream().filter(ed -> ed.getPlanStatus().equals("AP")).count();
        long deniedCnt =
                eligList.stream().filter(ed -> ed.getPlanStatus().equals("DN")).count();
        double total = eligList.stream().mapToDouble(ed -> ed.getBenefitAmt()).sum();

        DashboardCard card = new DashboardCard();

        card.setPlansCnt(plansCount);
        card.setApprovedCnt(approvedCnt);
        card.setDeniedCnt(deniedCnt);
        card.setBenifitAmtGiven(total);

        return card;
    }

    @Override
    public UserAccountForm getUserByEmail(String email) {
        UserEntity userEntity = userRepo.findByEmail(email);
        UserAccountForm user = new UserAccountForm();
        BeanUtils.copyProperties(userEntity,user);
        return user;
    }

    private String readEmailBody(String filename, UserEntity user){
        StringBuilder sb = new StringBuilder();
        try(Stream<String> lines = Files.lines(Paths.get(filename))){
            lines.forEach(line -> {
                line = line.replace("${FNAME}", user.getFullName());
                line = line.replace("${PWD}", user.getPwd());
                line = line.replace("${EMAIL}", user.getEmail());
                sb.append(line);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
