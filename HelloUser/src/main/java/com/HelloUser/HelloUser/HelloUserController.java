package com.HelloUser.HelloUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;


    @Controller
        public class HelloUserController {



        private void saveToJson() {
        try {
            
            ObjectMapper mapper = new ObjectMapper();
            
            File projectRoot = new File(System.getProperty("user.dir"));
            File resourcesDir = new File(projectRoot, "HelloUser/src/main/resources");
            
            if (!resourcesDir.exists()) {
                boolean created = resourcesDir.mkdirs();
            } else {
            }
            
            File jsonFile = new File(resourcesDir, "members.json");
            
            mapper.writerWithDefaultPrettyPrinter()
                .writeValue(jsonFile, membersList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }

        private static final List<Members> membersList = new ArrayList<>();
        static {
            loadMembersFromJson();
        }

        private static void loadMembersFromJson() {
            try {
                ObjectMapper mapper = new ObjectMapper();
                
                File projectRoot = new File(System.getProperty("user.dir"));
                File resourcesDir = new File(projectRoot, "HelloUser/src/main/resources");
                File jsonFile = new File(resourcesDir, "members.json");
                
                if (jsonFile.exists()) {
                    List<Members> loadedMembers = mapper.readValue(jsonFile, 
                        new TypeReference<List<Members>>() {});
                    membersList.addAll(loadedMembers);
                } else {
                    
                    try {
                        File classpathFile = new File("src/main/resources/members.json");
                        if (classpathFile.exists()) {
                            List<Members> loadedMembers = mapper.readValue(classpathFile, 
                                new TypeReference<List<Members>>() {});
                            membersList.addAll(loadedMembers);
                        } else {
                        }
                    } catch (Exception e2) {
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    @PostMapping("/new-member")
    public String postNewMember(@ModelAttribute Members newMember) {
        
        if (newMember.getId() == null) {
            newMember.setId(java.util.UUID.randomUUID());
        }
        
        newMember.setFnLname(newMember.getName() + " " + newMember.getLname());
        
        membersList.add(newMember);
        saveToJson();
        return "redirect:/members";
    }

    @GetMapping("/home")
    public String getHome(HttpSession session, Model model) {
      
        boolean isAdmin = session.getAttribute("isAdmin") != null;
        model.addAttribute("isAdmin", isAdmin);
        return "home";
    }
    
    @GetMapping("/members")
    public String getMembers(HttpSession session, Model model) {
        model.addAttribute("members", membersList);
       
        boolean isAdmin = session.getAttribute("isAdmin") != null;
        model.addAttribute("isAdmin", isAdmin);
        return "members";
    }

    @GetMapping("/add-member")
    public String getAddMember(HttpSession session, Model model) {
        
        if (session.getAttribute("isAdmin") == null) {
            return "redirect:/login";
        }
        model.addAttribute("members", membersList);
        model.addAttribute("newMember", new Members("", "", "", ""));
        return "add-member";
    }
    
    @GetMapping("/member-detail/{memberId}")
    public String getMovieDetails(@PathVariable UUID memberId , Model model) {
        for (Members member : membersList) {
            if (member.getId().equals(memberId)) {
                model.addAttribute("member", member);
                break;
            }
        }

        return "member-detail";
    }
    
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    
    @PostMapping("/login") 
    public String postLogin(@RequestParam String username, 
                           @RequestParam String password, 
                           HttpSession session,
                           Model model) {
        if ("admin".equals(username) && "admin".equals(password)) {
            session.setAttribute("isAdmin", true);
            return "redirect:/members";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
    
    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.removeAttribute("isAdmin");
        return "redirect:/home";
    }
    
    @PostMapping("/delete-member/{memberId}")
    public String deleteMember(@PathVariable UUID memberId, HttpSession session) {
        
        if (session.getAttribute("isAdmin") == null) {
            return "redirect:/login";
        }
        
        
        boolean removed = membersList.removeIf(member -> member.getId().equals(memberId));
        if (removed) {
            saveToJson();
        }
        
        return "redirect:/members";
    }
    
    
    @GetMapping("/delete-member-get/{memberId}")
    public String deleteMemberGet(@PathVariable UUID memberId, HttpSession session) {
        
        if (session.getAttribute("isAdmin") == null) {
            return "redirect:/login";
        }
        
        
        boolean removed = membersList.removeIf(member -> {
            boolean matches = member.getId().equals(memberId);
            if (matches) {
            }
            return matches;
        });
        
        if (removed) {
            saveToJson();
        } else {
        }
        
        return "redirect:/members";
    }
    
}
