package com.company.dto.payload;

import com.company.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPayload {

    private String name;
    private String email;

    public User toEntity(){
        return User.builder()
                .name(this.name)
                .email(this.email)
                .build();
    }
}
