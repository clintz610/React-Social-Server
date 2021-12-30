package com.revature.users.dtos;

import com.revature.users.usersettings.UserSettings;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserSettingsDto implements Serializable {

    private final String userId;
    private final boolean darkMode;

    public UserSettingsDto(UserSettings userSettings){
        this.darkMode = userSettings.isDarkMode();
        this.userId = userSettings.getUser().getId();
    }


}
