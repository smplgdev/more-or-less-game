package utils;

import javax.swing.*;
import java.awt.*;

public class Utils {
    private Utils() {
        // This class is not for creating instances.
        // It contains only static methods
    }

    public static void centerFrameOnScreen(JFrame frame) {
        // just centers the game frame in the center of the screen
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

}
