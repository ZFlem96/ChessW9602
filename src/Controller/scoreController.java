package Controller;


import javax.swing.*;
import java.awt.*;

public class scoreController extends JPanel{
    private JPanel northScore;
    private JPanel southScore;

    private final Dimension SCORE_CONTROLLER_DIMENSION = new Dimension(50, 80);

    public scoreController() {
        super(new BorderLayout());
        northScore = new JPanel(new BorderLayout());
        southScore = new JPanel(new BorderLayout());
        this.add(northScore, BorderLayout.NORTH);
        this.add(southScore, BorderLayout.SOUTH);
        setPreferredSize(SCORE_CONTROLLER_DIMENSION);
    }
}
