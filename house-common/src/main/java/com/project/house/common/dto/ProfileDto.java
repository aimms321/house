package com.project.house.common.dto;

import org.hibernate.validator.constraints.Length;

public class ProfileDto {
    @Length(min = 1, max = 20)
    private String name;

    @Length(min=1,max = 13)
    private String phone;

    @Length(min=1,max = 250)
    private String aboutme;

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAboutme() {
        return aboutme;
    }

    public void setAboutme(String aboutme) {
        this.aboutme = aboutme;
    }
}
