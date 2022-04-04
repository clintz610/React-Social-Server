package com.revature.users.usersettings;

import com.revature.users.dtos.UserSettingsDto;

import com.revature.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSettingsService {


    private final UserSettingsRepository userSettingsRepo;
    private final UserRepository userRepository;

    @Autowired
    public UserSettingsService(UserSettingsRepository userSettingsRepo, UserRepository userRepository) {
        this.userSettingsRepo = userSettingsRepo;
        this.userRepository = userRepository;
    }

    //Save
    public UserSettings saveUserSettings(UserSettingsDto userSettingsDto) throws Exception{

        UserSettings currentSettings = userSettingsRepo.findByUserId(userSettingsDto.getUserId());

        if (currentSettings != null){
            currentSettings.setDarkMode(userSettingsDto.isDarkMode());
            return userSettingsRepo.save(currentSettings);

        }
        else {
            throw new RuntimeException("Error persisting user to datasource");
        }

    }


    //Retrieve
    public UserSettings retrieveUserSettings(UserSettingsDto userSettingsDto) throws Exception{
        //TODO: Need to test if this throws exception of if we need to explicitly do that here like above.
        return userSettingsRepo.findByUserId(userSettingsDto.getUserId());
    }
}
