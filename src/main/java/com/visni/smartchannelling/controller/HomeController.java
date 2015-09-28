package com.visni.smartchannelling.controller;

import java.net.BindException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    
    
    @RequestMapping("/home")
    public ModelAndView home(HttpServletRequest request,HttpServletResponse response, Object command, BindException errors)throws Exception {
 

        return new ModelAndView("index");
    }
    
   @RequestMapping(value = "/userlogin", method = RequestMethod.GET)
    public ModelAndView showError(HttpServletRequest request){
 

        return new ModelAndView("error");
    }
   
   @RequestMapping(value = "/systemerror", method = RequestMethod.GET)
   public ModelAndView showSystemError(HttpServletRequest request){


       return new ModelAndView("systemError");
   }
}
