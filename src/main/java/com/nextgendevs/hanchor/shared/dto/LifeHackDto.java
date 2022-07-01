package com.nextgendevs.hanchor.shared.dto;

public class LifeHackDto {
    private long id;
    private String lifeHack;
    private UserDto userDto;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLifeHack() {
        return lifeHack;
    }

    public void setLifeHack(String lifeHack) {
        this.lifeHack = lifeHack;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
