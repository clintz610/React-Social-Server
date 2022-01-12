package com.revature.users.profiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.users.User;
import com.revature.users.UserService;
import com.revature.users.dtos.ProfileRequest;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import com.revature.ReverbApplication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@SpringBootTest(classes = ReverbApplication.class)
@ActiveProfiles("test")
public class TestProfileControllerIntegration {

    private MockMvc mockMvc;
    private final WebApplicationContext context;
    private final ObjectMapper mapper;

    @Autowired
    public TestProfileControllerIntegration(WebApplicationContext context, ObjectMapper mapper) {
        this.context = context;
        this.mapper = mapper;
    }

    @BeforeEach
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testFindProfileByIdGivenNull() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/profile/d921e5f2-86cb-4a0f-abd9-b9ec4aafa3d5").contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }
    @Test
    public void testFindProfileById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/profile/d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5").contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
        assertEquals("application/json", result.getResponse().getContentType());
    }

    @Test
    public void testFindProfileByAuthorGivenInvalidId() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/profile/getByAuthor/d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c6").contentType("application/json"))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }


    @Test
    public void testCheckProfileOwnership()throws Exception{

    }


    @Test
    public void testUpdateProfileWithInvalidInput() throws Exception {
        Profile updateTarget = new Profile();
        User u=new User("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","dev4@dev.com", null,null, null);
        updateTarget.setUser(u);
        ProfileRequest profile = new ProfileRequest();
        profile.setId("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5");
        profile.setFirst_name("test");
        profile.setLast_name("tester");
        profile.setProfile_img("http://hello.com");
        profile.setHeader_img("http://hello.com");
        profile.setBirthday("A Mystery...");
        profile.setHobby("Programming, surely");
        profile.setLocation("Earth");
        profile.setAbout_me("I just joined Reverb!");

        String requestPayload1= mapper.writeValueAsString(profile);
        String requestPayload2= mapper.writeValueAsString(u);

        MvcResult result = mockMvc.perform(put("/api/profile/update").contentType("application/json").content(requestPayload1))
                .andDo(print())
                .andExpect(status().is(403))
                .andReturn();
    }

    @Test
    public void testFindThisUsersProfileGivenInvalidUser() throws Exception {
        User u=new User();
        String requestPayload = mapper.writeValueAsString(u);
        MvcResult result = mockMvc.perform(get("/api/profile/getUsersProfile").contentType("application/json").content(requestPayload))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }



    @Test
    public void testCheckProfileOwnershipGivenInValidUser() throws Exception {
        User u=new User("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","dev4@dev.com", null,null, null);
        String requestPayload = mapper.writeValueAsString(u);
        MvcResult result = mockMvc.perform(get("/api/profile/checkProfileOwnership/d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c6")
                        .contentType("application/json")
                        .content(requestPayload))
                .andDo(print())
                .andExpect(status().is(404))
                .andReturn();
    }
    @Test
    public void testCheckProfileOwnershipGivenValidUser() throws Exception {
        User u=new User("d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5","dev4@dev.com", null,null, null);
        String requestPayload = mapper.writeValueAsString(u);
        MvcResult result = mockMvc.perform(get("/api/profile/checkProfileOwnership/d921e5f2-86cb-4a0f-abd9-b9ec4aafa3c5")
                        .contentType("application/json")
                        .content(requestPayload))
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
    }

}