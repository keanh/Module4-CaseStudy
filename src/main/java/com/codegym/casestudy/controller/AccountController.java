package com.codegym.casestudy.controller;

import com.codegym.casestudy.model.Account;
import com.codegym.casestudy.model.Province;
import com.codegym.casestudy.service.AccountService;
import com.codegym.casestudy.service.BlogService;
import com.codegym.casestudy.service.ProvinceService;
import com.codegym.casestudy.validate.PhoneValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import sun.management.VMOptionCompositeData;

import java.util.List;
import java.util.Optional;

//@Controller
@RestController
public class AccountController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ProvinceService provinceService;

    @ModelAttribute("provinces")
    public List<Province> provinces(){
        return provinceService.findAll();
    }

    public String getPrincipal(){
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails){
            username =((UserDetails) principal).getUsername();
        }else {
            username = principal.toString();
        }
        return username;
    }

    @GetMapping("")
    public String home(){

        return "redirect:blog/view";
    }

    @GetMapping("/login")
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView("account/login");
        return modelAndView;
    }

    @GetMapping(value = "Access_Denied")
    public String accessDeniedPage(ModelMap model) {
        model.addAttribute("account", getPrincipal());
        return "account/accessDenied";
    }

    @GetMapping("account/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("account/registration");
        modelAndView.addObject("account",new Account());
        return modelAndView;
    }

    @PostMapping("account/create")
    public String create(@ModelAttribute("account") Account account, BindingResult bindingResult){
        new PhoneValidate().validate(account,bindingResult);
        if (bindingResult.hasFieldErrors()){
            return "account/registration";
        }else {
            accountService.save(account);
            return "redirect:/";
        }
    }

//    @PostMapping(value = "account/createRestful",produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Void> createAccountRestful(@RequestBody Account account){
//        accountService.save(account);
//        HttpHeaders headers = new HttpHeaders();
//        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
//    }

    @GetMapping("user/profile/{id}")
    public ModelAndView showProfile(@PathVariable Long id){
        Optional<Account> account = accountService.findById(id);
        if (account.isPresent()){
            ModelAndView modelAndView = new ModelAndView("account/profile");
            modelAndView.addObject("account",account.get());
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

//    @GetMapping("accountInfoRestful/{id}")
//    public ResponseEntity<Account> infoRestful(@PathVariable Long id){
//        Optional<Account> account = accountService.findById(id);
//        Account account1 = account.get();
//        if (account1 == null){
//            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
//        }else {
//            return new ResponseEntity<Account>(account1,HttpStatus.OK);
//        }
//    }

//    @DeleteMapping("deleteAccountRestful/{id}")
//    public ResponseEntity<Account> deleteAccountRestful(@PathVariable Long id){
//        Optional<Account> account = accountService.findById(id);
//        if (account == null){
//            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
//        }else {
//            accountService.remove(id);
//            return new ResponseEntity<Account>(HttpStatus.NO_CONTENT);
//        }
//    }

    @GetMapping("user/edit-profile/{id}")
    public ModelAndView editForm(@PathVariable Long id){
        Optional<Account> account = accountService.findById(id);
        if (account.isPresent()){
            ModelAndView modelAndView = new ModelAndView("account/edit");
            modelAndView.addObject("account",account.get());
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("user/edit-profile")
    public ModelAndView editUser(@ModelAttribute("account") Account account){
        Optional<Account> account1 = accountService.findById(account.getId());
        Account account2 = account1.get();
        account2.setFirstName(account.getFirstName());
        account2.setLastName(account.getLastName());
        account2.setAge(account.getAge());
        account2.setEmail(account.getEmail());
        account2.setProvince(account.getProvince());
        account2.setPhoneNumber(account.getPhoneNumber());
        if (account2 != null){
            accountService.save(account2);
            ModelAndView modelAndView = new ModelAndView("account/edit");
            modelAndView.addObject("account",account2);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

//    @PutMapping("editAccountRestful/{id}")
//    public ResponseEntity<Account> editAccountRestful(@RequestBody Account account,@PathVariable Long id){
//        Optional<Account> account1 = accountService.findById(account.getId());
//        Account account2 = account1.get();
//        if (account2 != null){
//            account2.setFirstName(account.getFirstName());
//            account2.setLastName(account.getLastName());
//            account2.setAge(account.getAge());
//            account2.setEmail(account.getEmail());
//            account2.setProvince(account.getProvince());
//            account2.setPhoneNumber(account.getPhoneNumber());
//            accountService.save(account2);
//            return new ResponseEntity<Account>(account2,HttpStatus.OK);
//        }else {
//            return new ResponseEntity<Account>(HttpStatus.NOT_FOUND);
//        }
//    }
}
