package kendokoodi.warehouseapplication;

import java.io.File;
import java.io.FileWriter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * kendokoodi.warehouseapplication.App contains main method and launches
 * application.
 * @author mika
 * @author Mika.1.virtala@edu.karelia.fi
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        
        // remember to switch to welcomescreen before final tests
        scene = new Scene(loadFXML("welcomescreen"), 800, 640);
        stage.setScene(scene);
        stage.show();
    }
    

    /**
     * Sets a view to scene.
     * @param fxml name of the view without .fxml extension (String).
     * @throws IOException 
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml) );
    }
    
    /**
     * Loads FXML view.
     * @param fxml String, name of the view without .fxml extension.
     * @return object hierarchy from a FXML document
     * @throws IOException 
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void writeErrorToFile(String error){
        try{
            File errorFile = new File ( "errorLog.txt" );
            if (!errorFile.exists()){
                errorFile.createNewFile();
            }
        } catch (IOException e){ e.printStackTrace(); }
        
        try{
            FileWriter errorFileWriter = new FileWriter ( "errorLog.txt", true );
            errorFileWriter.write( error );
            errorFileWriter.close();
        } catch (IOException e){ e.printStackTrace(); }
    }

    public static void main(String[] args) {
        launch();
    }

}
