package com.herve.vms.utils.helpers;


import com.herve.vms.enums.EGender;
import com.herve.vms.exceptions.BadRequestException;
import com.herve.vms.services.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class Helper {

    private static IUserService userService;

    @Autowired
    public Helper(
            IUserService userService){
        Helper.userService = userService;
    }

    public SecurityProperties.User getLoggedInUser(HttpServletRequest request){
        return  null;
    }

    public static String getActor(){
        return "Done at : " + LocalDateTime.now() +  " By Email : " +  userService.getLoggedInUser().getEmail();
    }
    public static void logAction(String message){
        log.info(message);
        log.info(getActor());
    }
    public static EGender getGender(String gender){
        System.out.println("Gender : " + gender);
        switch (gender.toUpperCase()){
            case "MALE", "M":
                return EGender.MALE;
            case "FEMALE", "F":
                return EGender.FEMALE;
            case "OTHER":
                return  EGender.OTHER;
            default:
                throw new BadRequestException("The provided gender is invalid");
        }
    }

}
