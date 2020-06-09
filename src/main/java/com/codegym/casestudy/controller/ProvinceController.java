package com.codegym.casestudy.controller;

import com.codegym.casestudy.model.Province;
import com.codegym.casestudy.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("province")
public class ProvinceController {
    @Autowired
    private ProvinceService provinceService;

    @GetMapping("/view")
    public ModelAndView searchAllProvince(){
        ModelAndView modelAndView = new ModelAndView("province/view");
        List<Province> provinces = provinceService.findAll();
        modelAndView.addObject("provinces",provinces);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createForm(){
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province",new Province());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView createProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        ModelAndView modelAndView = new ModelAndView("province/create");
        modelAndView.addObject("province",new Province());
        modelAndView.addObject("message","New province created successfully");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteForm(@PathVariable Long id){
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()){
            ModelAndView modelAndView = new ModelAndView("province/delete");
            modelAndView.addObject("province",province.get());
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("/delete")
    public String deleteProvince(@ModelAttribute("province") Province province){
        provinceService.remove(province.getId());
        return "redirect:/province/view";
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editForm(@PathVariable Long id){
        Optional<Province> province = provinceService.findById(id);
        if (province.isPresent()){
            ModelAndView modelAndView = new ModelAndView("province/edit");
            modelAndView.addObject("province",province.get());
            return modelAndView;
        }else{
            ModelAndView modelAndView = new ModelAndView("error");
            return modelAndView;
        }
    }

    @PostMapping("/edit")
    public String editProvince(@ModelAttribute("province") Province province){
        provinceService.save(province);
        return "redirect:/province/view";
    }
}
