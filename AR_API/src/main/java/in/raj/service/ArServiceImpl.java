package in.raj.service;
//5
import in.raj.binding.App;
import in.raj.constant.AppConstants;
import in.raj.entities.AppEntity;
import in.raj.entities.UserEntity;
import in.raj.exception.SSAWebException;
import in.raj.repositories.AppRepo;
import in.raj.repositories.UserRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
@Service
public class ArServiceImpl implements ArService{
    @Autowired
    private AppRepo appRepo;
    @Autowired
    private UserRepo userRepo;
    private static final String SSA_WEB_API_USR="https://ssa.web.app/{ssn}";

    @Override
    public String createApplication(App app) {
        try {
            WebClient webClient = WebClient.create();
            String stateName = webClient.get()
                    .uri(SSA_WEB_API_USR, app.getSsn())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            if (AppConstants.RI.equals(stateName)){
                //valid citiazen app
                UserEntity userEntity = userRepo.findById(app.getUserId()).get();

                AppEntity entity = new AppEntity();
                BeanUtils.copyProperties(app,entity);

                entity.setUser(userEntity);

                entity = appRepo.save(entity);
                return "App Created with Case Num : "+entity.getCaseNum();
            }
        }catch (Exception e){
            throw new SSAWebException(e.getMessage());
        }

        return AppConstants.INVALID_SSN;
    }

    @Override
    public List<App> fetchApps(Integer userId) {
        UserEntity userEntity = userRepo.findById(userId).get();
        Integer roleId = userEntity.getRoleId();
        List<AppEntity> appEntities = null;
        if (1 == roleId){
            appEntities = appRepo.fetchUserApps();
        }else {
            appEntities = appRepo.fetchCwApps(userId);
        }
        List<App> apps = new ArrayList<>();
        for (AppEntity entity : appEntities){
            App app = new App();
            BeanUtils.copyProperties(entity,apps);
            apps.add(app);
        }
        return apps;
    }
}
