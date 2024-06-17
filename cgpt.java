import java.util.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

class WaterLevelObserver extends JFrame {
    WaterLevelObserver(String title) {
        setTitle(title);
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
    }

    public void update(int waterLevel) {
        // Method to be overridden by subclasses
    }
}

class Splitter extends WaterLevelObserver {
    private JLabel lblSplitter;

    Splitter() {
        super("Splitter");
        lblSplitter = new JLabel("  ");
        lblSplitter.setFont(new Font("", Font.BOLD, 25));
        add(lblSplitter);
    }

    public void update(int waterLevel) {
        lblSplitter.setText(waterLevel >= 75 ? "Splitter ON" : "Splitter OFF");
    }
}

class Alarm extends WaterLevelObserver {
    private JLabel lblAlarm;

    Alarm() {
        super("Alarm");
        lblAlarm = new JLabel("  ");
        lblAlarm.setFont(new Font("", Font.BOLD, 25));
        add(lblAlarm);
    }

    public void update(int waterLevel) {
        lblAlarm.setText(waterLevel >= 50 ? "Alarm ON" : "Alarm OFF");
    }
}

class Display extends WaterLevelObserver {
    private JLabel lblDisplay;

    Display() {
        super("Display");
        lblDisplay = new JLabel("Water Level: 0");
        lblDisplay.setFont(new Font("", Font.BOLD, 25));
        add(lblDisplay);
    }

    public void update(int waterLevel) {
        lblDisplay.setText("Water Level: " + waterLevel);
    }
}

class SMSSender extends WaterLevelObserver {
    private JLabel lblSMS;

    SMSSender() {
        super("SMS Sender");
        lblSMS = new JLabel("  ");
        lblSMS.setFont(new Font("", Font.BOLD, 25));
        add(lblSMS);
    }

    public void update(int waterLevel) {
        lblSMS.setText(waterLevel >= 80 ? "SMS Sent" : "SMS Not Sent");
    }
}

class WaterLevelObservable {
    private ArrayList<WaterLevelObserver> observerList = new ArrayList<>();
    private int waterLevel;

    public void addWaterLevelObserver(WaterLevelObserver ob) {
        observerList.add(ob);
    }

    public void setWaterLevel(int waterLevel) {
        if (this.waterLevel != waterLevel) {
            this.waterLevel = waterLevel;
            notifyDevices();
        }
    }

    public void notifyDevices() {
        for (WaterLevelObserver ob : observerList) {
            ob.update(waterLevel);
        }
    }
}

class WaterTank extends JFrame {
    private JSlider waterLevelSlider;
    private WaterLevelObservable waterLevelObservable;

    WaterTank(WaterLevelObservable waterLevelObservable) {
        super("Water Tank");
        this.waterLevelObservable = waterLevelObservable;
        setSize(100, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        waterLevelSlider = new JSlider(JSlider.VERTICAL, 0, 100, 50);
        waterLevelSlider.setMajorTickSpacing(10);
        waterLevelSlider.setMinorTickSpacing(1);
        waterLevelSlider.setPaintTicks(true);
        waterLevelSlider.setPaintLabels(true);

        waterLevelSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent evt) {
                waterLevelObservable.setWaterLevel(waterLevelSlider.getValue());
            }
        });

        add(waterLevelSlider);
    }
}

class cgpt {
    public static void main(String args[]) {
        WaterLevelObservable waterLevelObservable = new WaterLevelObservable();
        
        Alarm alarm = new Alarm();
        Display display = new Display();
        SMSSender smsSender = new SMSSender();
        Splitter splitter = new Splitter();

        waterLevelObservable.addWaterLevelObserver(alarm);
        waterLevelObservable.addWaterLevelObserver(display);
        waterLevelObservable.addWaterLevelObserver(smsSender);
        waterLevelObservable.addWaterLevelObserver(splitter);

        WaterTank waterTank = new WaterTank(waterLevelObservable);
        
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                waterTank.setVisible(true);
                alarm.setVisible(true);
                display.setVisible(true);
                smsSender.setVisible(true);
                splitter.setVisible(true);
            }
        });
    }
}

