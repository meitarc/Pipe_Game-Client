package sample.viewmodel;

import javafx.beans.property.*;
import sample.model.PGBModel;
import sample.model.SolverClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

public class ViewModel extends Observable implements Observer {

    private PGBModel m;
    public ObjectProperty<char[][]> data;
    public StringProperty ip;
    public StringProperty port;
    public StringProperty counterSteps;
    public int count;

    public ViewModel(PGBModel m)
    {
        this.m=m;
        data= new SimpleObjectProperty<>();
        ip=new SimpleStringProperty();
        port=new SimpleStringProperty();
        counterSteps=new SimpleStringProperty();
        this.data.setValue(m.getResult());
        count=0;
        counterSteps.setValue("num of steps :"+count);
    }

    public void switchCell(int i,int j,int s){
        m.switchCell(i,j,s);
        count++;
        counterSteps.setValue("num of steps :"+count);
        //System.out.println(counterSteps);
    }
    public void solve()
    {
        new Thread(() -> SolverClient.solve(ip.get(),Integer.parseInt(port.get()),m)).start();
        //Platform.runLater(() -> );
    }
    public void setData(File file)
    {
        if(file!=null)
            try {
                List<String> allLines = Files.readAllLines(file.toPath());
                char[][] data = new char[allLines.size()][allLines.get(0).length()];
                for (int i = 0; i < data.length; i++) {
                    System.arraycopy(allLines.get(i).toCharArray(), 0, data[i], 0, data[0].length);
                }
                m.setData(data);
                this.data.setValue(m.getResult());
            } catch (IOException ignored) {
            }
    }


    @Override
    public void update(Observable o, Object arg) {
        if(o==m)
        {
            setChanged();
            notifyObservers();
        }
    }
}
