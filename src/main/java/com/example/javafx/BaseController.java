package com.example.javafx;

import javafx.stage.Stage;

public class BaseController {
    protected Stage usingStage;

    public void setUsingStage(Stage stage) {
        this.usingStage = stage;
    }

    public Stage getUsingStage() {
        return usingStage;
    }
}
