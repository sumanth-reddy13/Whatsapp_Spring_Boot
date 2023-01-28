package com.driver;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WhatsappService {

    WhatsappRepository whatsappRepository = new WhatsappRepository();
    public String createUser(String name, String mobile) throws Exception{
        String response = "";
        try {
            response = whatsappRepository.createUser(name, mobile);
        }
        catch(Exception e) {
            throw e;
        }
        return response;
    }

    public Group createGroup(List<User> users){
        return whatsappRepository.createGroup(users);
    }

    public int createMessage(String content){
        return whatsappRepository.createMessage(content);
    }

    public int sendMessage(UpdateRequest updateRequest) throws Exception{
        int response = 0;
        try {
            response = whatsappRepository.sendMessage(updateRequest);
        }
        catch(Exception e) {
            throw e;
        }
        return response;
    }

    public String changeAdmin(ChangeAdmin changeAdmin) throws Exception{
        String response = "";
        try {
            response = whatsappRepository.changeAdmin(changeAdmin);
        }
        catch(Exception e) {
            throw e;
        }
        return response;
    }

//    public int removeUser(User user) throws Exception{
//        return whatsappRepository.removeUser(user);
//    }

//    public String findMessage(Date start, Date end, int K) throws Exception{
//        return whatsappRepository.findMessage(start, end, K);
//    }
}
