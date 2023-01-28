package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<String, Group> groupMap;
    private HashMap<String, List<User>> groupUserMap;
    private HashMap<String, List<Message>> groupMessageMap;
    private HashMap<String, List<Message>> senderMap;
    private HashMap<String, User> adminMap;
    private HashSet<String> userMobile;
    private int groupCount;
    private int messageId;
    private List<String> listOfGroups;
    private HashMap<String, String> userMap;
    private List<User> allUsers;

    public WhatsappRepository(){
        this.groupMap = new HashMap<>();
        this.groupMessageMap = new HashMap<String, List<Message>>();
        this.groupUserMap = new HashMap<String, List<User>>();
        this.senderMap = new HashMap<String, List<Message>>();
        this.adminMap = new HashMap<String, User>();
        this.userMobile = new HashSet<>();
        this.groupCount = 0;
        this.messageId = 0;
        this.userMap = new HashMap<>();
        this.listOfGroups = new ArrayList<>();
        this.allUsers = new ArrayList<>();
    }

    public String createUser(String name, String mobile) throws Exception{
        try {
            if (userMobile.contains(mobile)) {
                throw new Exception("User already exists");
            }
        }
        catch(Exception e) {
            throw e;
        }
        User user = new User(name, mobile);
        userMobile.add(mobile);
        allUsers.add(user);
        return "user added to the database";
    }

    public Group createGroup(List<User> users){
//        private HashMap<Group, User> adminMap;

        User admin = users.get(0);
        if (users.size() == 2) {
            String name = users.get(1).getName();
            Group g = new Group(name, 2);
            groupUserMap.put(name, users);
            adminMap.put(name, admin);
            listOfGroups.add(name);
            groupMap.put(name, g);
            return g;
        }
        groupCount++;
        String name = "Group " + groupCount;
        Group g1 = new Group(name, users.size());
        groupUserMap.put(name, users);
        listOfGroups.add(name);
        adminMap.put(name, admin);
        groupMap.put(name, g1);
        return g1;
    }

    public int createMessage(String content){
        messageId++;
//        Date date = new Date("28-01-2023");
        String date = "28-01-2023";
        Message message = new Message(messageId,content,date);

        return messageId;
    }

    public int sendMessage(UpdateRequest updateRequest) throws Exception{
        Group group = updateRequest.getGroup();
        User sender = updateRequest.getSender();
        Message message = updateRequest.getMessage();

        String groupName = group.getName();
        List<User> users = new ArrayList<>();
        boolean isPre = false;

        for (String gName : groupUserMap.keySet()) {
            if (gName.equals(groupName)) {
                users = groupUserMap.get(gName);
                isPre = true;
                break;
            }
        }
        try {
            if (isPre == false) {
                throw new Exception("Group does not exist");
            }
        }
        catch(Exception e) {
            throw e;
        }

//        List<User> users = groupUserMap.get(groupName);

        boolean isPresent = false;
        for (User user : users) {
            if (user.equals(sender)) {
                isPresent = true;
                break;
            }
        }

        try {
            if (isPresent == false) {
                throw new Exception("You are not allowed to send message");
            }
        }
        catch (Exception e) {
            throw e;
        }

        List<Message> messages = new ArrayList<>();
        if(groupMessageMap.containsKey(groupName)) {
            messages = groupMessageMap.get(groupName);
        }
        messages.add(message);
        groupMessageMap.put(groupName, messages);

        List<Message> senderMessages = new ArrayList<>();
        if (senderMap.containsKey(sender.getMobile())) {
            senderMessages = senderMap.get(sender);
        }
        senderMessages.add(message);
        senderMap.put(sender.getMobile(), senderMessages);

        return messages.size();
    }

    public String changeAdmin(ChangeAdmin changeAdmin) throws Exception{
//        String groupName = group.getName();
        Group group = changeAdmin.getGroup();
        User approver = changeAdmin.getAdmin();
        User user = changeAdmin.getUser();
        String groupName = group.getName();

        try {
            if (!listOfGroups.contains(groupName)) {
                throw new Exception("Group does not exist");
            }
        }
        catch(Exception e) {
            throw e;
        }

        try {
            if (!approver.getMobile().equals(adminMap.get(groupName).getMobile())) {
                throw new Exception("Approver does not have rights");
            }
        }
        catch(Exception e) {
            throw e;
        }

        List<User> users = groupUserMap.get(groupName);

        boolean isPresent = false;
        for (User u : users) {
            if (user.equals(u)) {
                isPresent = true;
                break;
            }
        }

        try {
            if (isPresent == false) {
                throw new Exception("You are not allowed to send message");
            }
        }
        catch (Exception e) {
            throw e;
        }
//        try {
//            if (!users.contains(user)) {
//                throw new Exception("User is not a participant");
//            }
//        }
//        catch(Exception e) {
//            throw e;
//        }
        adminMap.put(groupName, user);
        return "SUCCESS";
    }

//    @DeleteMapping("/remove-user")
//    public int removeUser(User user) throws Exception{
//        //This is a bonus problem and does not contains any marks
//        //A user belongs to exactly one group
//        //If user is not found in any group, throw "User not found" exception
//        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
//        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
//        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
//
//        Group group;
//        boolean isPresent = false;
//        for (Group g : groupUserMap.keySet()) {
//            if (groupUserMap.get(g).contains(user)) {
//                isPresent = true;
//                group = g;
//                break;
//            }
//        }
//
//        try {
//            if (!isPresent) {
//                throw new Exception("User not found");
//            }
//        }
//        catch(Exception e) {
//            throw e;
//        }
//
//        if (adminMap.get(group).equals(user)) throw new Exception("Cannot remove admin");
//        groupUserMap.get(group).remove(user);
//
//        Set<Message> set = new HashSet<>();
//        List<Message> listOfMessages = senderMap.get(user);
//        for (Message message : groupMessageMap.get(groupNaam)) {
//            if (listOfMessages.contains(message)) {
//                set.add(message);
//            }
//        }
//        List<Message> messagesList = groupMessageMap.get(groupNaam);
//        for (Message message : set) {
//            messagesList.remove(message);
//        }
//        Group group = groupMap.get(groupNaam);
//        group.setNumberOfParticipants(group.getNumberOfParticipants() - 1);
//        int noOfUser = group.getNumberOfParticipants();
//        messageId -= set.size();
//        return noOfUser + messagesList.size() + messageId;
//    }

//    public String findMessage(Date start, Date end, int K) throws Exception{
//        //This is a bonus problem and does not contains any marks
//        // Find the Kth latest message between start and end (excluding start and end)
//        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception
//
//
//        return "hi";
//    }

}
