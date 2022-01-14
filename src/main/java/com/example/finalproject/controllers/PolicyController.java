package com.example.finalproject.controllers;

import com.example.finalproject.models.Policy;
import com.example.finalproject.models.PolicyDTO;
import com.example.finalproject.models.User;
import com.example.finalproject.services.PolicyService;
import com.example.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PolicyController {

    private static final String POLICY_REQUEST_SUCCESS = "Policy request successful!";
    private static final String CANCEL_POLICY_SUCCESS = "You successfully canceled your Policy request!";
    private PolicyService policyService;
    private UserService userService;

    @Autowired
    public PolicyController(PolicyService policyService, UserService userService) {
        this.policyService = policyService;
        this.userService = userService;
    }

    @GetMapping("/policy")
    public String showPolicyCreationPage(Model model) {
        try {
            model.addAttribute("policyDTO", new PolicyDTO());
            model.addAttribute("currentUser", userService.getCurrentUser());
            return "createPolicy";
        } catch (Exception ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }

    }


    @PostMapping("/policy")
    public String createNewPolicy(@RequestParam("imageFile") MultipartFile imageFile, @Valid @ModelAttribute PolicyDTO policyDTO, BindingResult bindingResult, Model model) {
        try {
            policyService.createPolicy(policyDTO, imageFile);
            model.addAttribute("success", POLICY_REQUEST_SUCCESS);
            return "index";
        } catch (RuntimeException ex) {
            model.addAttribute("policyDTO", new PolicyDTO());
            model.addAttribute("currentUser", userService.getCurrentUser());
            model.addAttribute("error", ex.getMessage());
            return "createPolicy";
        }
    }

    @GetMapping("/mypolicies")
    public String showCurrentpolicies(Model model,
                                      @RequestParam(value = "policyStatus", required = false) String policyStatus,
                                      @RequestParam(name = "page", required = false) Integer page,
                                      @RequestParam(name = "size", required = false) Integer size) {
        try {
            if (page == null) {
                page = 1;
            }

            if (size == null) {
                size = 10;
            }

            List<Policy> policies = policyService.getCurrentUserPolicies(policyStatus);

            Page<Policy> myPolicyPage = policyService.findPaginated(PageRequest.of(page - 1, size), policies);

            model.addAttribute("myPolicyPage", myPolicyPage);

            int totalPages = myPolicyPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            model.addAttribute("policies", policies);
            return "policies";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }

    @GetMapping("/mypolicies/{id}")
    public String getMyPolicy(@PathVariable int id, Model model,
                              @RequestParam(value = "policyStatus", required = false) String policyStatus) {
        try {
            model.addAttribute("policy", policyService.getPolicyForUser(id));
            return "policydetails";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("policies", policyService.getCurrentUserPolicies(policyStatus));
            return "policies";
        }
    }

    @PostMapping("/mypolicies/{id}")
    public String cancelMyPolicy(@PathVariable int id, RedirectAttributes redir) {
        try {

            policyService.cancelMyPolicy(id);
            redir.addFlashAttribute("success", CANCEL_POLICY_SUCCESS);
            return "redirect:/mypolicies";
        } catch (RuntimeException ex) {
            redir.addFlashAttribute("error", ex.getMessage());
            return "redirect:/mypolicies";
        }
    }

    @GetMapping("/mypolicies/date")
    public String showPoliciesByDate(Model model, RedirectAttributes redir) {
        try {
            User user = userService.getCurrentUser();
            model.addAttribute("policies", policyService.sortByDateUser(user.getId()));
            return "policies";
        } catch (RuntimeException ex) {
            redir.addFlashAttribute("error", ex.getMessage());
            return "redirect:/mypolicies";
        }
    }
}
