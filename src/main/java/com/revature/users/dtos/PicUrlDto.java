package com.revature.security.props.users.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PicUrlDto {
    private final UUID profileId;
    private final String picUrl;

}
