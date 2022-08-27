package GUI.Helpers;

import java.util.Stack;

public class Navigator {
    private static Navigator inst;
    public  static Navigator instance(){
        if (inst==null){
            inst = new Navigator();

        }
        return inst;
    }
    Stack<NavigableFrame> navStack=new Stack<>();

    public void push(NavigableFrame newFrame){
        if (!navStack.empty()) {
            NavigableFrame currentFrame = navStack.peek();
            currentFrame.beforeHide();
            currentFrame.setVisible(false);
        }
        navStack.push(newFrame);
        newFrame.setVisible(true);
//        System.out.println("pushed "+newFrame.name());
    }

    public void pop(){
        if (navStack.empty()){
//            System.out.println("Empty stack");
            return;
        }
        NavigableFrame currFrame = navStack.pop();
//        System.out.println("popped "+currFrame.name());

        currFrame.setVisible(false);
        currFrame.dispose();
        if (!navStack.empty()){
            NavigableFrame parentFrame = navStack.peek();

            parentFrame.setVisible(true);
            parentFrame.refresh();
//            System.out.println("set parent visible "+parentFrame.name());

        }
    }
}
