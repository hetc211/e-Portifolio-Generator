/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg.error;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import teg.view.EPortfolioGeneratorView;

/**
 *
 * @author HTC
 */
public class ErrorHandler {
    EPortfolioGeneratorView ui;
    public ErrorHandler(EPortfolioGeneratorView aThis) {
        ui = aThis;
    }

    public void processError(String failed_to_load_file) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(null);
        alert.setContentText(failed_to_load_file);

        alert.showAndWait();
    
    }
    
}
