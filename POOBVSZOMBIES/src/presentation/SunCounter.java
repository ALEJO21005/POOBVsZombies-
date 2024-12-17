package src.presentation;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SunCounter {
    private int count;
    private Timer timer;
    private Runnable onUpdate;

    public SunCounter(Runnable onUpdate) {
        this.count = 0;
        this.onUpdate = onUpdate;

        // Configura el temporizador
        this.timer = new Timer(10000, e -> {
            count += 25;
            if (onUpdate != null) {
                onUpdate.run();
            }
        });
    }


    public void updatecounter(){

    }
    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void reset() {
        this.count = 0;
    }
}
