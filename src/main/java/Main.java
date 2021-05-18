import java.io.File;
import java.io.FileInputStream;

import com.kieferlam.javafxblur.Blur;

import Model.SpecialDays;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static void main(String[] args) {
        SpecialDays.Init();
        Blur.loadBlurLibrary();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                new File(System.getProperty("user.dir") + "/src/main/java/Pages/MainPage.fxml").toURI().toURL());
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        primaryStage.setScene(scene);

        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.show();

        primaryStage.getIcons().add(new Image(
                new FileInputStream(new File(System.getProperty("user.dir") + "/src/main/java/Images/Logo.png"))));

        Blur.applyBlur(primaryStage, Blur.BLUR_BEHIND);
    }
}
