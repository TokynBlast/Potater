import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Main {

    public static void styleTextField(JTextField textField, Integer x, Integer y, Integer width, Integer height, String tipText, String placeHolder) {
        textField.setBounds(x, y, width, height);
        textField.setBackground(new java.awt.Color(43, 70, 204));
        textField.setForeground(java.awt.Color.GRAY);
        textField.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
        textField.setCaretColor(java.awt.Color.WHITE);
        textField.setSelectionColor(java.awt.Color.YELLOW);
        textField.setText(placeHolder);
        textField.setToolTipText(tipText);

        textField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeHolder)) {
                    textField.setForeground(java.awt.Color.WHITE);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().equals("")) {
                    textField.setForeground(java.awt.Color.GRAY);
                    textField.setText(placeHolder);
                }
            }
        });
    }

    public static void Start(String ip, String PKSize, Integer Time, Integer Delay) {
        // Start the network stress test
    }

    public static void Stop() {
        // Stop the network stress test
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Javus - Java Network Stressor - v0.0.1");
        window.setVisible(true);
        window.setResizable(false);
        window.setSize(500, 500);
        window.setLayout(null);

        JTextField IP = new JTextField();
        JTextField PacketSize = new JTextField();
        JTextField Time = new JTextField();
        JTextField Delay = new JTextField();

        JButton BeginEnd = new JButton();

        BeginEnd.setText("Begin");
        BeginEnd.setBounds(385, 100, 90, 25);

        BeginEnd.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (BeginEnd.getText().equals("Begin")) {
                    BeginEnd.setText("Stop");
                    Start(IP.getText(), PacketSize.getText(), Integer.parseInt(Time.getText()), Integer.parseInt(Delay.getText()));
                }
                else if (BeginEnd.getText().equals("Stop")) {
                    BeginEnd.setText("Begin");
                    Stop();
                }
            }
        });
        
        styleTextField(IP,         10,  100, 140, 25, "IPv4 adress to send packets", "192.168.1.1");
        styleTextField(PacketSize, 165, 100, 50, 25,  "Size of packets to send in kB", "65");
        styleTextField(Time,       230, 100, 50, 25,  "Amount of time to send packets for in seconds", "60");
        styleTextField(Delay,      295, 100, 70, 25,  "Delay between packets in seconds", "1000");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(IP);
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
        
        window.add(BeginEnd);
    }
}
