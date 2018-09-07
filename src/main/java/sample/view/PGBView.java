package sample.view;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import sample.viewmodel.ViewModel;

import java.io.File;
import java.util.Observable;
import java.util.Observer;


public class PGBView implements Observer{


    private ViewModel vm;
    @FXML Canvas canvas;
    @FXML TextField ip;
    @FXML TextField port;

    @FXML
    private Label Steps;

    Image image=new Image("tiles/tile1.jpg");
    Image imageOval=new Image("tiles/oval.png");

    public void setViewModel(ViewModel vm) {
        this.vm=vm;
        vm.ip.bind(ip.textProperty());
        vm.port.bind(port.textProperty());
        Steps.textProperty().bind(vm.counterSteps);
        ip.setText("127.0.0.1");
        port.setText("6400");

        resizeCanvas();
        draw();
    }

    private void resizeCanvas() {
        Scene scene=canvas.getScene();
        MenuBar menuBar=(MenuBar)((BorderPane)scene.getRoot()).getTop();
        scene.heightProperty().addListener(
                (observable, oldValue, newValue) ->
                        canvas.setHeight(newValue.doubleValue()
                        -(menuBar.getHeight())));
        scene.widthProperty().addListener(
                (observable, oldValue, newValue) ->
                        canvas.setWidth(newValue.doubleValue()));
        canvas.heightProperty().addListener((observable, oldValue, newValue) -> draw());
        canvas.widthProperty().addListener((observable, oldValue, newValue) -> draw());
    }

    public void onClick(MouseEvent event)
    {
        double H = canvas.getHeight();
        double W = canvas.getWidth();
        double h = (H / (double)vm.data.get().length);
        double w = (W / (double)vm.data.get()[0].length);
        double mx = event.getX();
        double my = event.getY();
        int i = (int)(my / h);
        int j = (int)(mx / w);
        vm.switchCell(i,j,1);
    }

    private void draw() {

        GraphicsContext g=canvas.getGraphicsContext2D();
        double H = canvas.getHeight();
        double W = canvas.getWidth();
        g.clearRect(0,0, W, H);
        int h = (int)(H / (double)vm.data.get().length);
        int w = (int)(W / (double)vm.data.get()[0].length);


        for (int i=0;i < vm.data.get().length;i++) {
            for (int j=0;j < vm.data.get()[i].length;j++) {
                g.rect(j * w, i * h, w, h);
                switch (vm.data.get()[i][j]) {
                    case '-': {
                        //g.fillRect(j * w, i * h + h / 4, w, h / 2);
                        g.drawImage(image,j * w, i * h + h / 4, w, h / 2);

                        break;
                    }
                    case '7': {
                        //g.fillRect(j * w, i * h + h / 4, 3 * w / 4, h / 2);
                        //g.fillRect(j * w + w / 4, i * h + 3 * h / 4, w / 2, h / 4);
                        g.drawImage(image,j * w, i * h + h / 4, 3 * w / 4, h / 2);
                        g.drawImage(image,j * w + w / 4, i * h + 3 * h / 4, w / 2, h / 4);
                        break;
                    }
                    case 'L': {
                        //g.fillRect(j * w + w / 4, i * h, w / 2, h / 4);
                        //g.fillRect(j * w + w / 4, i * h + h / 4, 3 * w / 4, h / 2);
                        g.drawImage(image,j * w + w / 4, i * h, w / 2, h / 4);
                        g.drawImage(image,j * w + w / 4, i * h + h / 4, 3 * w / 4, h / 2);
                        break;
                    }
                    case '|': {
                        //g.fillRect(j * w + w / 4, i * h, w / 2, h);
                        g.drawImage(image,j * w + w / 4, i * h, w / 2, h);

                        break;
                    }
                    case 'J': {
                        //g.fillRect(j * w + w / 4, i * h, w / 2, h / 4);
                        //g.fillRect(j * w, i * h + h / 4, 3 * w / 4, h / 2);
                        g.drawImage(image,j * w + w / 4, i * h, w / 2, h / 4);
                        g.drawImage(image,j * w, i * h + h / 4, 3 * w / 4, h / 2);
                        break;
                    }
                    case 'F': {
                        //g.fillRect(j * w + w / 4, i * h + h / 4, 3 * w / 4, h / 2);
                        //g.fillRect(j * w + w / 4, i * h + 3 * h / 4, w / 2, h / 4);
                        g.drawImage(image,j * w + w / 4, i * h + h / 4, 3 * w / 4, h / 2);
                        g.drawImage(image,j * w + w / 4, i * h + 3 * h / 4, w / 2, h / 4);
                        break;
                    }
                    case 's': {
                        //g.fillOval(j * w + w / 4, i * h + h / 4, w / 2, h / 2);
                        g.drawImage(imageOval,j * w + w / 4, i * h + h / 4, w / 2, h / 2);

                        break;
                    }
                    case 'g': {
                        //g.fillOval(j * w + w / 6, i * h + h / 6, 4 * w / 6, 4 * h / 6);
                        //g.fillOval(j * w + w / 4, i * h + h / 4, w / 2, h / 2);
                        g.drawImage(imageOval,j * w + w / 6, i * h + h / 6, 4 * w / 6, 4 * h / 6);

                    }
                }
            }
        }
    }

    public void solve()
    {
       vm.solve();
    }

    public void load(){
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Select level");
        fileChooser.setInitialDirectory(new File("./src/main/java/resources/"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Board Files", "*.brd"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file=fileChooser.showOpenDialog(null);
        vm.setData(file);
        draw();
    }

    public void changeStylesheets(ActionEvent event)
    {
        MenuItem menuItem=(MenuItem)event.getSource();
        Scene scene= canvas.getScene();
        scene.getStylesheets().clear();
        switch (menuItem.getText())
        {
            case "DarkTheme":
            {
                scene.getStylesheets().add("styles/DarkTheme.css");
                break;
            }
            default:
            {
                break;
            }
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        if(vm==o)
        {
            draw();
        }
    }
}
