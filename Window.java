import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;

public class Window implements Runnable{

    JFrame frame;
    //JPanel buttonsPanel;
    Box[][] boxes;
    JButton button_start = new JButton("Start");
    JButton button_exit = new JButton("Exit");
    @Override
    public void run(){
        initFrame();
        button_start.addActionListener(e -> {
            initBoxes();
            initTimer();
            button_exit.addActionListener(x -> {
                System.exit(0);
            });
        });
    }

    void initFrame(){
        JPanel buttonsPanel = new JPanel();
        frame = new JFrame();
        frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);
        buttonsPanel.add(button_start);
        buttonsPanel.add(button_exit);
        frame.pack();
        frame.getContentPane().setLayout(null);
        frame.setSize( Config.SIZE * Config.WIDTH, Config.SIZE * Config.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Life Game");
        frame.setVisible(true);

    }

    void initBoxes(){
        boxes = new Box[Config.WIDTH][Config.HEIGHT];
        for (int x = 0; x < Config.WIDTH; x ++){
            for (int y = 0; y < Config.HEIGHT; y++){
                boxes [x][y] = new Box(x, y);
                frame.add(boxes[x][y]);
            }
        }
        for (int x = 0; x < Config.WIDTH; x ++) {
            for (int y = 0; y < Config.HEIGHT; y++) {
                for (int sx = -1; sx <= +1; sx++)
                    for (int sy = -1; sy <= +1; sy++)
                        if (!(sx == 0 && sy == 0))
                            boxes[x][y].cell.addNear(boxes
                                    [(x + sx + Config.WIDTH) % Config.WIDTH]
                                    [(y + sy + Config.HEIGHT) % Config.HEIGHT].cell);
            }
        }
        for (int x = 10; x < 15; x++) {
            boxes[x][10].cell.status = Status.LIVE;
            boxes[x][10].setColor();
        }
    }
    private void initTimer(){
        TimerListener t1 = new TimerListener();
        Timer timer = new Timer(Config.SLEEPMS, t1);
        timer.start();
    }
    private class TimerListener implements ActionListener{
        boolean flop = false;
        @Override
        public void actionPerformed(ActionEvent e){
            flop = !flop;
            for (int x = 0; x < Config.WIDTH; x ++) {
                for (int y = 0; y < Config.HEIGHT; y++) {
                    if (flop)
                        boxes[x][y].step1();
                    else
                        boxes[x][y] .step2();
                }
            }
        }
    }
}
