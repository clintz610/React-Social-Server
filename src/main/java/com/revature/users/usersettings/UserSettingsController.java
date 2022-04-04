package com.revature.users.usersettings;

import com.revature.users.dtos.UserSettingsDto;
import com.revature.users.usersettings.UserSettings;
import com.revature.users.UserRepository;
import com.revature.users.usersettings.UserSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userSettings")
public class UserSettingsController {

    private final UserSettingsService userSettingsService;
    private final UserRepository userRepo;

    @Autowired
    public UserSettingsController(UserSettingsService userSettingsService, UserRepository userRepo) {
        this.userSettingsService = userSettingsService;
        this.userRepo = userRepo;
    }

    //Save (Post)
    @PostMapping("/save")
    @ResponseStatus(value = HttpStatus.OK, reason = "User Settings Saved Successfully")
    public UserSettingsDto saveUserSettings(UserSettingsDto userSettingsDto) throws Exception{
            //If this throws exception handler will catch.
            UserSettings savedSettings = userSettingsService.saveUserSettings(userSettingsDto);
            return new UserSettingsDto(savedSettings);

    }

    //Retrieve(Get)
    @GetMapping("/Retrieve")
    @ResponseStatus(HttpStatus.OK)
    public UserSettingsDto retrieveUserSettings(UserSettingsDto userSettingsDto) throws Exception {
        UserSettings retrievedSettings = userSettingsService.retrieveUserSettings(userSettingsDto);
        return new UserSettingsDto(retrievedSettings);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void exceptionHandler(RuntimeException e){
        //TODO: Debug statement. Remove before presentation
            e.printStackTrace();
    }

}
