import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.net.*;

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

    public static void Start(String ip, String PKSize, Integer Time, Integer Delay) { // Change to hold text for the stats
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

        JComboBox attack = new JComboBox< >();

        // JLabel RealIP = new JLabel(); /// If IP is WiFi or Self, it will get the IP here.

        JTextField IP = new JTextField();
        JTextField PacketSize = new JTextField();
        JTextField Time = new JTextField();
        JTextField Delay = new JTextField();

        JButton BeginEnd = new JButton();

        JLabel PacketSent = new JLabel();
        JLabel PacketReceived = new JLabel();
        JLabel PacketLoss = new JLabel();
        JLabel TimeLeft = new JLabel();

        PacketSent.setText("Packets Sent: 0");
        PacketReceived.setText("Packets Received: 0");
        PacketLoss.setText("Packet Loss: 0%");
        TimeLeft.setText("Time Left: --:--:--");


        BeginEnd.setText("Begin");
        BeginEnd.setBounds(385, 100, 90, 25);

        
        attack.setBounds(0, 0, 90, 40);
        attack.addItem("Custom");
        attack.addItem("Self");
        attack.addItem("WiFi");

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

        attack.addActionListener(new ActionListener()  {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (attack.getSelectedItem().equals("Custom")) {
                    IP.setEditable(true);
                    IP.setText("192.168.1.1");
                }

                else if (attack.getSelectedItem().equals("WiFi")) {
                    IP.setEditable(true);
                    IP.setText("WiFi");
                    IP.setEditable(false);
                }

                else if (attack.getSelectedItem().equals("Self")) {
                    IP.setEditable(true);
                    IP.setText("127.0.0.1");
                    IP.setEditable(false);
                }
            }
        });


        styleTextField(IP,         10,  100, 140, 25, "IPv4 adress to send packets to", "192.168.1.1");
        styleTextField(PacketSize, 165, 100, 50, 25,  "Size of packets to send in kB", "65");
        styleTextField(Time,       230, 100, 50, 25,  "Amount of time to send packets for in seconds", "60");
        styleTextField(Delay,      295, 100, 70, 25,  "Delay between sending packets in seconds", "1000");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
        window.add(IP);
        
        window.add(attack);

        window.add(BeginEnd);
    }
}
