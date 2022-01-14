package com.example.finalproject.controllers;

import com.example.finalproject.exceptions.PolicyDoesntExistException;
import com.example.finalproject.exceptions.UserDoesntExistException;
import com.example.finalproject.models.OfferMulticriteria;
import com.example.finalproject.models.Policy;
import com.example.finalproject.models.User;
import com.example.finalproject.services.MulticriteriaService;
import com.example.finalproject.services.PolicyService;
import com.example.finalproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class AdminController {


    private static final String POLICY_SUCCESSFULLY_REJECTED = "You successfully rejected policy with id: %s";
    private static final String MULTICRITERIA_UPDATE_SUCCESS = "Update Successful!";
    private static final String POLICY_SUCCESSFULLY_ACCEPTED = "You successfully accepted policy with id: %s";
    private static final String VALIDATION_FAILED = "Please check the data in Multicriteria table, again!";
    private UserService userService;
    private PolicyService policyService;
    private MulticriteriaService multicriteriaService;

    @Autowired
    public AdminController(UserService userService, PolicyService policyService, MulticriteriaService multicriteriaService) {
        this.userService = userService;
        this.policyService = policyService;
        this.multicriteriaService = multicriteriaService;
    }

    @GetMapping("/policies")
    public String showPolicies(Model model,
                               @RequestParam(value = "policyStatus", required = false) String policyStatus,
                               @RequestParam(value = "username", required = false) String username,
                               @RequestParam(name = "page", required = false) Integer page,
                               @RequestParam(name = "size", required = false) Integer size) {
        try {
            if (page == null) {
                page = 1;
            }

            if (size == null) {
                size = 10;
            }

            List<Policy> policies = policyService.getAllPolicies(policyStatus, username);

            Page<Policy> policyPage = policyService.findPaginated(PageRequest.of(page - 1, size), policies);

            model.addAttribute("policyPage", policyPage);

            int totalPages = policyPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            model.addAttribute("policies", policies);
            return "adminPolicies";

        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }


    @GetMapping("/policies/lastname")
    public String showPoliciesByEmail(Model model, RedirectAttributes redir) {
        try {
            model.addAttribute("policies", policyService.sortAlphabetically());
            return "adminPolicies";
        } catch (RuntimeException ex) {
            redir.addFlashAttribute("error", ex.getMessage());
            return "redirect:/policies";
        }
    }

    @GetMapping("/policies/date")
    public String showPoliciesByDate(Model model, RedirectAttributes redir) {
        try {
            model.addAttribute("policies", policyService.sortByDate());
            return "adminPolicies";
        } catch (RuntimeException ex) {
            redir.addFlashAttribute("error", ex.getMessage());
            return "redirect:/policies";
        }
    }

    @GetMapping("/policies/{id}")
    public String showPolicyById(@PathVariable int id, Model model) {
        try {
            model.addAttribute("policy", policyService.getPolicyById(id));
            return "adminPolicyDetails";
        } catch (PolicyDoesntExistException ex) {
            model.addAttribute("policies", policyService.getPendingPolicies());
            model.addAttribute("error", ex.getMessage());
            return "adminPolicies";
        }
    }

    @PostMapping("/policies/accept/{policyId}")
    public String acceptPolicy(@PathVariable int policyId, Model model) {
        try {
            policyService.changePolicyStatus(policyId, 2);
            model.addAttribute("policies", policyService.getPendingPolicies());
            model.addAttribute("success", String.format(POLICY_SUCCESSFULLY_ACCEPTED, policyId));
            return "adminPolicies";
        } catch (RuntimeException ex) {
            model.addAttribute("policies", policyService.getPendingPolicies());
            model.addAttribute("error", ex.getMessage());
            return "adminPolicies";
        }
    }

    @PostMapping("/policies/reject/{policyId}")
    public String rejectPolicy(@PathVariable int policyId, Model model) {
        try {
            policyService.changePolicyStatus(policyId, 3);
            model.addAttribute("policies", policyService.getPendingPolicies());
            model.addAttribute("success", String.format(POLICY_SUCCESSFULLY_REJECTED, policyId));
            return "adminPolicies";
        } catch (RuntimeException ex) {
            model.addAttribute("policies", policyService.getPendingPolicies());
            model.addAttribute("error", ex.getMessage());
            return "adminPolicies";
        }
    }

    @GetMapping("/users")
    public String getAllUsers(Model model,

                              @RequestParam(name = "page", required = false) Integer page,
                              @RequestParam(name = "size", required = false) Integer size) {
        try {
            if (page == null) {
                page = 1;
            }
            if (size == null) {
                size = 10;
            }

            List<User> users = userService.getAll();

            Page<User> userPage = userService.findPaginated(PageRequest.of(page - 1, size), users);

            model.addAttribute("userPage", userPage);

            int totalPages = userPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            model.addAttribute("users", users);
            return "adminUsers";
        } catch (RuntimeException ex) {
            model.addAttribute("error", ex.getMessage());
            return "index";
        }
    }

    @GetMapping("/users/profile/{id}")
    public String getUserById(@PathVariable int id, Model model) {
        try {
            User user = userService.getById(id);
            model.addAttribute("user", user);
            return "userProfileV2";
        } catch (UserDoesntExistException ex) {
            return "redirect:/users";
        }
    }

    @GetMapping("/multicriteria")
    public String showMulticriteriaPage(Model model, OfferMulticriteria offerMulticriteria) {
        model.addAttribute("multicriteria", offerMulticriteria);
        return "adminMulticriteria";
    }

    @PostMapping("/multicriteria")
    public String showMulticriteriaPage(@RequestParam("file") MultipartFile reapExcelDataFile, Model model) {
        try {
            multicriteriaService.update(reapExcelDataFile);
            model.addAttribute("success", MULTICRITERIA_UPDATE_SUCCESS);
            return "adminMulticriteria";
        } catch (RuntimeException ex) {
            model.addAttribute("error", VALIDATION_FAILED);
            return "adminMulticriteria";
        }
    }
}
