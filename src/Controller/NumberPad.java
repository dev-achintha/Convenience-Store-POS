package Controller;

import static View.MainWindow.jButton_0;
import static View.MainWindow.jButton_1;
import static View.MainWindow.jButton_2;
import static View.MainWindow.jButton_3;
import static View.MainWindow.jButton_4;
import static View.MainWindow.jButton_5;
import static View.MainWindow.jButton_6;
import static View.MainWindow.jButton_7;
import static View.MainWindow.jButton_8;
import static View.MainWindow.jButton_9;
import static View.MainWindow.jButton_BackSpace;
import static View.MainWindow.jButton_Delete;
import static View.MainWindow.jButton_Dot;
import static View.MainWindow.jButton_Enter;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import static View.MainWindow.SearchBox;

public class NumberPad {
    private static Robot robot = null;
    static { 
        try {
            robot = new Robot();
        }catch(AWTException ex) { }
    }
    
    public static void typeKey(int keycode) {
        pressKey(keycode);
        releaseKey(keycode);
    }
    
    public static void pressKey(int keycode) {
        if(robot == null)
            throw new UnsupportedOperationException("This platform doesn't supports low level keyboard activities.");
        robot.keyPress(keycode);
    }
    
    public static void releaseKey(int keycode) {
        if(robot == null)
            throw new UnsupportedOperationException("This platform doesn't supports low level keyboard activities.");
        robot.keyRelease(keycode);
    }
    
    public static void changeBorderColor_Pressed(int key) {
        String x = Integer.toString(key);
        JButton temp = null;
        temp.setName(x);
        temp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
    }
    public static void changeBorderColor_Released(int key) {
        String x = Integer.toString(key);
        JButton temp = null;
        temp.setName(x);
        temp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
    }
    
    public static void keyPressedPolicy(java.awt.event.KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_0:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_4:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_5:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_6:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD7:
            case KeyEvent.VK_7:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_8:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD9:
            case KeyEvent.VK_9:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_PERIOD:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_BACK_SPACE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_DELETE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            SearchBox.setText("");
            break;
            case KeyEvent.VK_ENTER:

            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 5));
            SearchBox.setText("");
            break;
            default:
            break;
        }
    }
    public static void keyReleasedPolicy(java.awt.event.KeyEvent evt) {
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_0:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD1:
            case KeyEvent.VK_1:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD2:
            case KeyEvent.VK_2:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD3:
            case KeyEvent.VK_3:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD4:
            case KeyEvent.VK_4:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD5:
            case KeyEvent.VK_5:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD6:
            case KeyEvent.VK_6:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD7:
            case KeyEvent.VK_7:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD8:
            case KeyEvent.VK_8:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_NUMPAD9:
            case KeyEvent.VK_9:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_PERIOD:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_BACK_SPACE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            break;
            case KeyEvent.VK_DELETE:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            SearchBox.setText("");
            break;
            case KeyEvent.VK_ENTER:
            jButton_0.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_BackSpace.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Delete.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Dot.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            jButton_Enter.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 102, 0), 0));
            
            
            break;
            default:
            break;
        }
    }
}
