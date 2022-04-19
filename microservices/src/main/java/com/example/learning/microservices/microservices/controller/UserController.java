package com.example.learning.microservices.microservices.controller;

import com.example.learning.microservices.microservices.model.User;
import com.example.learning.microservices.microservices.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UserController {
        private final UserService userService;
        private static final Logger logger = LoggerFactory.getLogger(UserController.class);
        @PostMapping("/createUser")
        public User createUser (@RequestBody User user){
                logger.info("Creating New User");
                return userService.createUser(user);
        }
        @GetMapping("/getAllUsers")
        public List<User> getAllUser(){
                logger.info("Listing all the Users");
                return userService.getAllUser();
        }
        @GetMapping("/getUser/{id}")
        public Optional<User> getOneUser(@PathVariable("id") String id){
                Optional<User> userDetails = userService.getOneUser(id);
                logger.info("Returning Single User Value:"+ String.valueOf(userDetails));
                return userDetails;
        }
        @GetMapping("/countUserCollection")
        public Number countUserCollection(){
                Number count = userService.countUserCollection();
                logger.info("Counting the records in User Collection"+"\n"+"Number of Users in the collection: "+count);
                return count;
        }
        @GetMapping("/getAllUserSorted")
        public List<User> getAllUserSorted(){
                logger.info("Listing all the Users in Sorted order");
                return userService.getAllUserSorted();
        }
        @DeleteMapping("deleteUser/{id}")
        public ResponseEntity<Object> deleteUser(@PathVariable("id") String id){
                logger.info("Deleting the User"+id);
                userService.deleteUser(id);
                return new ResponseEntity<>("User is deleted successfully", HttpStatus.OK);
        }
        @PutMapping("/updateUser/{id}")
        public User updateUser(@PathVariable("id") String id, @RequestBody User User){
                logger.info("Updating User"+ id + User);
                return userService.updateUser(id,User);
        }
        @GetMapping("/getUserPagination")
        public ResponseEntity<List<User>> getUserPagination(
                @RequestParam(defaultValue = "0") Integer pageNo,
                @RequestParam(defaultValue = "2") Integer pageSize,
                @RequestParam(defaultValue = "userId") String sortBy)
        {
                List<User> list = userService.getUserPagination(pageNo, pageSize, sortBy);
                logger.info("Listing all the Users in Pagintion format");
                return new ResponseEntity<List<User>>(list, new HttpHeaders(), HttpStatus.OK);
        }
        @GetMapping("/loginUser")
        public ResponseEntity<List<User>> loginUser(
                @RequestParam String username,
                @RequestParam String password) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
                String userData = userService.loginUser(username, password);
                logger.info(username+" Trying to Login");
                return new ResponseEntity(userData, HttpStatus.OK);
        }
        @GetMapping("/updateUserPassword")
        public ResponseEntity<List<User>>  updateUserPassword(
                @RequestParam String username,
                @RequestParam String newPassword)
        {
                logger.info(username+" Trying to change the Password");
                ResponseEntity<Object> list = userService.updateUserPassword(username, newPassword);
                String status = String.valueOf(list.getStatusCode());
                if(status.equals("400 BAD_REQUEST")){
                        return new ResponseEntity(list, HttpStatus.BAD_REQUEST);
                }else{
                        return new ResponseEntity(list, HttpStatus.OK);
                }
        }
}
