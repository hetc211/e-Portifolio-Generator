/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teg;

import javafx.application.Application;
import javafx.stage.Stage;
import teg.view.EPortfolioGeneratorView;

/**
 *
 * @author HTC
 */
public class TheEportfolioGenerator extends Application {
    
    EPortfolioGeneratorView ui = new EPortfolioGeneratorView();
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        String appTitle = "the ePortfolio Generator";

        ui.startUI(primaryStage, appTitle);
       
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	launch(args);
    }
}
