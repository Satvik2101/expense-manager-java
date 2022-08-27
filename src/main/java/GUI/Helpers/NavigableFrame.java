package GUI.Helpers;

import javax.swing.*;

public abstract class NavigableFrame extends JFrame {
//    protected JButton backButton;

    public String name(){
        return "NavFrame";
    }
    public NavigableFrame(String title,int width, int height
                          ) {
//        setContentPane(mainPanel);
        setTitle(title);
        setSize(width,height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    //called when the frame is set visible again after a child is closed
    public void beforeHide(){};
    public void refresh(){};



}
