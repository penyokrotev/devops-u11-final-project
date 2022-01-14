package com.example.finalproject.controllers;

import com.example.finalproject.models.Offer;
import com.example.finalproject.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class OfferController {

    private OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/offer")
    public String showOfferPage(Model model) {
        try {
            model.addAttribute("offer", new Offer());
            model.addAttribute("carBrands", offerService.getAllCarBrands());
            model.addAttribute("carModels", offerService.getAllCarModels());
            return "createOffer";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }

    @PostMapping("/offer")
    public String createOffer(@Valid @ModelAttribute Offer offer, Model model) {
        try {
            //TODO
//            if (authenticationMediator.isUserLoggedIn()) {
//                offer = offerService.calculateOffer(offer);
//                model.addAttribute("GUID", offer.getGuid());
//                model.addAttribute("policyDTO", new PolicyDTO());
//                model.addAttribute("currentUser", userService.getCurrentUser());
//                return "createPolicy";
//            } else {
                offer = offerService.calculateOffer(offer);
                model.addAttribute("offerGUID", offer.getGuid());
                model.addAttribute("totalPremium", offer.getTotalPremium().doubleValue());
                return "readyOffer";
//            }
        } catch (RuntimeException ex) {
            model.addAttribute("offer", new Offer());
            model.addAttribute("carBrands", offerService.getAllCarBrands());
            model.addAttribute("carModels", offerService.getAllCarModels());
            model.addAttribute("error", ex.getMessage());
            return "createOffer";
        }
    }
}
