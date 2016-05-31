package baccarat;

import javafx.scene.text.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PlayscreenController {

    public Text console;

    public void consoleMsg(String msg){
        String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String curr = console.getText();
        String[] lines = curr.split("\n");
        String nw = "";
        nw += msg + "\n";
        if(lines.length>6) {
            for (int i = 0; i < lines.length-1; i++) {
                nw += lines[i]+"\n";
            }
        }else{
            for (int i = 0; i < lines.length; i++) {
                nw += lines[i]+"\n";
            }
        }
        console.setText(timeStamp+" - "+nw);
    }

    public void buttonPress(){
        consoleMsg("test");
    }

}