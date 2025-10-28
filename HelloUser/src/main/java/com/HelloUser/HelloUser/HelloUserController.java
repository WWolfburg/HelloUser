package com.HelloUser.HelloUser;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import java.io.File;










@Controller
public class HelloUserController {

    private void saveToJson() {
    try {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter()         //fick tips om detta från chatgpt
              .writeValue(new File("src/main/resources/members.json"), membersList);
        System.out.println("✅ Successfully saved " + membersList.size() + " members to JSON!");
    } catch (Exception e) {
        System.out.println("❌ Error saving to JSON: " + e.getMessage());
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
            File jsonFile = new File("src/main/resources/members.json");
            
            if (jsonFile.exists()) {
                
                List<Members> loadedMembers = mapper.readValue(jsonFile, 
                    new TypeReference<List<Members>>() {});
                membersList.addAll(loadedMembers);
            } else {
                System.out.println("(no JSON file found)");
            }
        } catch (Exception e) {
            System.out.println("JSON file empty" + e.getMessage());

        }
    }

    @PostMapping("/new-member")
    public String postNewMember(@ModelAttribute Members newMember) {
    membersList.add(newMember);
    saveToJson();
    return "redirect:/members";
}

    @GetMapping("/home")
    public String getHome() {
        return "home";
    }
    
    @GetMapping("/members")
    public String getMembers(Model model) {
    model.addAttribute("members", membersList);   
        return "members";
    }

    @GetMapping("/add-member")
    public String getAddMember(Model model) {
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
    
   
    
    
}
