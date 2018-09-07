package sample.model;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.io.*;
import java.net.Socket;

public class SolverClient {

    public static void solve(String ip, int port, PGBModel board) {
        try {
            String line;
            Socket theServer = new Socket(ip, port);
            PrintWriter out = new PrintWriter(theServer.getOutputStream());
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(theServer.getOutputStream());
            char[][] data = board.result;
            ///send board size
            int size=data.length*data[0].length;
            objectOutputStream.writeDouble(size);
            //out.println(String.valueOf(size)+'\n');
            //out.flush();
            objectOutputStream.flush();


            for(char[] dataline:data)
            {
                out.println(new String(dataline));
                out.flush();
            }
            out.println("done");
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
            while (!(line = in.readLine()).equals("done")) {
                int i = Integer.parseInt(line.split(",")[0]);
                int j = Integer.parseInt(line.split(",")[1]);
                int times = Integer.parseInt(line.split(",")[2]);
                board.switchCell(i, j, times);
            }
            in.close();
            out.close();

            theServer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            DialogError(e);
        }
    }


    private static void DialogError(Exception ex)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText(ex.getMessage());
        //alert.setContentText(ex.getMessage());

        // Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
