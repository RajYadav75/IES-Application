package in.raj.services;

import in.raj.bindings.UnlockAccountForm;
import in.raj.bindings.UserAccountForm;
import in.raj.entities.UserEntity;
import in.raj.repositories.UserRepo;
import in.raj.utils.EmailUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
public class AccountServiceImpl implements AccountService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtil emailUtil;

    @Override
    public boolean createUserAccount(UserAccountForm accForm) {
        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(accForm,entity);
        //TODO-> Set Random Password
        entity.setPwd(generatePwd());
        //TODO-> Set Account Status
        entity.setAccStatus("LOCKED");
        entity.setActiveSw("Y");
        userRepo.save(entity);
        //TODO-> Send Email
        String subject  = "User Registration";
        String body = readEmailBody("REG_EMAIL_BODY.txt",entity);
        return emailUtil.sendEmail(subject, body, accForm.getEmail());
    }

    @Override
    public List<UserAccountForm> fetchUserAccounts() {
        List<UserEntity> userEntities = userRepo.findAll();
        List<UserAccountForm> users = new ArrayList<>();
        for (UserEntity userEntity : userEntities){
            UserAccountForm user = new UserAccountForm();
            BeanUtils.copyProperties(userEntity,user);
            users.add(user);
        }
        return users;
    }

    @Override
    //TODO => Edit Button
    public UserAccountForm getUserAccById(Integer accId) {
        Optional<UserEntity> optional = userRepo.findById(accId);
        if (optional.isPresent()){
            UserEntity userEntity = optional.get();
            UserAccountForm user = new UserAccountForm();
            BeanUtils.copyProperties(userEntity,user);
            return user;
        }
        return null;
    }

    @Override
    public String changeAccStatus(Integer userId, String status) {
        int count = userRepo.updateAccStatus(userId, status);
        if(count>0){
            return "Status Changed";
        }
        return "Failed To Change";
    }

    @Override
    public String unlockUserAccount(UnlockAccountForm unlockAccountForm) {
        UserEntity entity = userRepo.findByEmail(unlockAccountForm.getEmail());
        entity.setPwd(unlockAccountForm.getNewPwd());
        entity.setAccStatus("UNLOCKED");
        userRepo.save(entity);
        return "Account Unlocked";
    }
    //TODO => This is for generate random password
    private String generatePwd(){
        String upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        // combine all strings
        String alphaNumeric = upperAlphabet + lowerAlphabet + numbers;
        //create Random String Builder
        StringBuilder sb = new StringBuilder();
        //Create an object of Random class
        Random random = new Random();
        //Specify length of random string
        int length = 6;

        for (int i = 0; i < length; i++) {
            //generate random index number
            int index = random.nextInt(alphaNumeric.length());
            //Get Character specified by index
            // From the String
            char randomChar = alphaNumeric.charAt(index);
            //append the character to string builder
            sb.append(randomChar);
        }
        return sb.toString();
    }

    private String readEmailBody(String filename, UserEntity user){
        StringBuilder sb = new StringBuilder();
        try(Stream<String> lines = Files.lines(Paths.get(filename))){
            lines.forEach(line -> {
                line = line.replace("${FNAME}", user.getFullName());
                line = line.replace("${TEMP_PWD}", user.getPwd());
                line = line.replace("${EMAIL}", user.getEmail());
                sb.append(line);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
