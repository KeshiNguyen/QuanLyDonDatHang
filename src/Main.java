import Model.Model;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Model.getInstance().getViewFactory().showLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}