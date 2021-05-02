package com.example.saymyname.types;

public enum RecordingMessage {
    WHERE_ARE_YOU("Where are You?"),
    SHOW_YOURSEF("Please <name> show Yourself"),
    COME_WITH_ME("Come with Me"),
    CALM_DOWN("Please calm down");

    String message;

    RecordingMessage(String message){
        this.message=message;
    }

    public String getMessage(){
        return message;
    }
}
