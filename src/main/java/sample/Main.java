package sample;


import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import sample.model.PGBModel;
import sample.view.PGBView;
import sample.viewmodel.ViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

public class Main extends Application {
//
    @Override
    public void start(Stage primaryStage) throws Exception{

        FileInputStream serviceAccount =
                new FileInputStream(
                        new File(Objects.requireNonNull(getClass().getClassLoader()
                                .getResource("ptm2-firebase-adminsdk.json")).toURI()));

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://ptm2-67441.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);





        /////
        FXMLLoader fxl=new FXMLLoader();
        BorderPane root= fxl.load(Objects.requireNonNull(getClass().getClassLoader()
                .getResource("activities/pipegameboardview.fxml")).openStream());
        primaryStage.setTitle("Pipe_Game");
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        PGBModel m=new PGBModel(); // model
        ViewModel vm=new ViewModel(m); // view-model
        PGBView mwc=fxl.getController(); // view
        m.addObserver(vm);
        vm.addObserver(mwc);
        mwc.setViewModel(vm);

        /////






        //FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
        /*firebaseAuth.createUser(new UserRecord.CreateRequest()
            .setEmail("ziv@gmail.com")
            .setPassword("zivizv")
            .setPhoneNumber("+972054-1234567"));
        System.out.println(firebaseAuth.getUserByEmail("ziv@gmail.com").getPhoneNumber());
        UserRecord userRecord= firebaseAuth.getUserByEmail("ziv@gmail.com");
        firebaseAuth.revokeRefreshTokens(userRecord.getUid());*/


        /*UserRecord userRecord= firebaseAuth.getUserByEmail("ggg@gmail.com");
        HashMap<String,String> map=new HashMap<>();
        map.put("Uid",userRecord.getUid());
        map.put("Email",userRecord.getEmail());
        map.put("PhoneNumber",userRecord.getPhoneNumber());
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("users")
                .child(userRecord.getUid())
                .setValue(map, (error, ref) -> {
                    if(error!=null)
                    {
                        System.out.println("setVale successes");
                    }
                    else
                    {
                        System.out.println("setVale failed");
                    }
                });*/



    }


    public static void main(String[] args) {
        launch(args);
    }
}
