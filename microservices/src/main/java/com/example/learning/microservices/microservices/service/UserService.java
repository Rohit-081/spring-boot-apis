package com.example.learning.microservices.microservices.service;


import com.example.learning.microservices.microservices.controller.UserController;
import com.example.learning.microservices.microservices.model.User;
import com.example.learning.microservices.microservices.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import javax.crypto.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private final UserRepository UserRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    public User createUser(User user)  {
        try {
            String userPassword = user.getPassword();
            String encryptedPassword = encryptPassword(userPassword);
            logger.info("User Entered Password- "+userPassword+" Encrypted Password--"+encryptedPassword);
            user.setPassword(encryptedPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UserRepository.save(user);
    }
    public List<User> getAllUser(){return UserRepository.findAll();}

    public Optional<User> getOneUser(String id){
        return UserRepository.findById(id);
    }

    public List<User> getAllUserSorted(){
        return (List<User>) UserRepository.findAll((Sort.by(Sort.Direction.ASC, "firstName")));
    }

    public List<User> getUserPagination(Integer pageNo, Integer pageSize, String sortBy)
    {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.ASC,sortBy ));
        Page<User> pagedResult = UserRepository.findAll(paging);
        if(pagedResult.hasContent()) {
            return pagedResult.getContent();
        } else {
            return new ArrayList<User>();
        }
    }

    public Number countUserCollection(){
        return UserRepository.count();
    }

    public ResponseEntity<Object> deleteUser(String id){
        UserRepository.deleteById(id);
        return new ResponseEntity<>("User is deleted successfully", HttpStatus.OK);
    }

    public User updateUser(String userId, User User){
        logger.info("Updating User"+ userId + User);
        Optional<User> userData = UserRepository.findById(userId);
        if(userData.isPresent()){
            User _user = userData.get();
            _user.setUsername(User.getUsername());
            return UserRepository.save(_user);
        }
        return UserRepository.save(User);
    }
    public String encryptPassword(String Password){
        String EncryptionKey = "PasswordKey";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(EncryptionKey);
        String encryptedPassword = encryptor.encrypt(Password);
        return encryptedPassword;
    }
    public String decryptPassword(String encryptedPassword) {
        String EncryptionKey = "PasswordKey";
        StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
        decryptor.setPassword(EncryptionKey);
        String decryptedPassword = decryptor.decrypt(encryptedPassword);
        return decryptedPassword;
    }

    public String loginUser(String username, String password) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException {
        String returnMessage = "";
        try{
            List<User> userData = UserRepository.findByUsername(username);
            if(!userData.isEmpty()){
                logger.info("User Exists");
                String userName = ""; String Password = "";
                for (User dataset : userData) {
                    userName = dataset.getUsername();
                    Password = dataset.getPassword();
                    Password = decryptPassword(Password);
                    logger.info("userName "+userName+" Password "+Password);
                }
                if( Password.equals(password) && userName.equals(username) ){
                    logger.info("Login Successful");
                    returnMessage = "Login Successful";
                }
                else{
                    logger.error("Incorrect Credentials");
                    returnMessage = "Incorrect Credentials";
                }
            }else{
                returnMessage = "No user Exists in this User Name!!";
                logger.error("No user Exists in this User Name!!");
            }
        }catch(Exception e){
            logger.error("Exception"+e);
        }
        finally{
            return returnMessage;
        }

    }
    public ResponseEntity<Object>  updateUserPassword(String username,String newPassword){
        List<User> userData = UserRepository.findByUsername(username);
        User finalUpdatedData = null;
        try{
            if(!userData.isEmpty()){
                logger.info("User Exists");
                User data = userData.get(0);
                String encryptedPassword = encryptPassword(newPassword);
                data.setPassword(encryptedPassword);
                finalUpdatedData = UserRepository.save(data);
                logger.info("Updated Successfully");
            }else{
                logger.error("No User Exists in this User Name!!");
                finalUpdatedData = null;
                logger.error("Not Updated Successfully");
            }
        }catch (Exception e){
            logger.error("Exception Occurred--"+ e);
        }finally {
            if(finalUpdatedData == null){
                return new ResponseEntity("User Password is not updated", HttpStatus.BAD_REQUEST);
            }else {
                return new ResponseEntity("User Password is updated successfully", HttpStatus.OK);
            }
        }

    }


}
