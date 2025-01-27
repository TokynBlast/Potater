import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Javus {

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

    public static void UDP(String ip, String PKSize, Integer Time, Integer Delay, String Type) throws IOException, InterruptedException {
        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            InetAddress localAddress = networkInterface.getInetAddresses().nextElement();
            try (DatagramSocket door = new DatagramSocket(0, localAddress)) {
                byte[] buffer = "Hello From Javus :) 🥔🥔🥔🥔🥔🥔".getBytes();
                InetAddress address;
                address = InetAddress.getByName(ip);
                if (address instanceof Inet6Address ipv6Address) {
                    if (ipv6Address.isLinkLocalAddress() && !ip.contains("%")) {
                        NetworkInterface ni = NetworkInterface.getByInetAddress(address);
                        if (ni != null) {
                            address = Inet6Address.getByAddress(null, address.getAddress(), ni.getIndex());
                        } else {
                            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                            if (interfaces.hasMoreElements()) {
                                ni = interfaces.nextElement();
                                address = Inet6Address.getByAddress(null, address.getAddress(), ni.getIndex());
                            }
                        }
                    }
                } else if (address != null && address.isLinkLocalAddress()) {
                    NetworkInterface ni = NetworkInterface.getByInetAddress(address);
                    if (ni != null) {
                        if (address instanceof Inet6Address) {
                            address = Inet6Address.getByAddress(null, address.getAddress(), ni.getIndex());
                        } else {
                            address = InetAddress.getByAddress(null, address.getAddress());
                        }
                    } else {
                        // Try to find any suitable network interface
                        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
                        if (interfaces.hasMoreElements()) {
                            ni = interfaces.nextElement();
                            address = InetAddress.getByAddress(ni.getName(), address.getAddress());
                        } else {
                            throw new SocketException("No suitable network interface found for link-local address");
                        }
                    }
                }
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 42069);
                ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
                Runnable task = () -> {
                    try {
                        long endTime = System.currentTimeMillis() + Time;
                        while (System.currentTimeMillis() < endTime) {
                            door.send(packet);
                        }
                    } catch (IOException ex) {
                        System.out.println("Error on 55: " + ex);
                    }
                };
                if (Delay >= 0) {
                    executor.scheduleAtFixedRate(task, 0, Delay, TimeUnit.MILLISECONDS);
                    executor.awaitTermination(Time + (Delay / 1000), TimeUnit.SECONDS);
                } else {
                    throw new IllegalArgumentException("Delay must be non-negative");
                }
                executor.shutdown();
            } finally {}

        } catch (SocketException ex) {
            System.out.println("Error 64: " + ex);
            System.out.println("\n\nPlease provide the above text and a screenshot of Javus here:\nhttps://github.com/TokynBlast/Javus/issues");
        } finally {}
    }

    public static void TCP(String ip, String PKSize, Integer Time, Integer Delay, String Type){
        
    }

    public static void ICMP(String ip, String PKSize, Integer Time, Integer Delay, String Type){
        
    }
    
    public static void Start(String ip, String PKSize, Integer Time, Integer Delay, String Type) throws IOException, InterruptedException {
        if (type.equals("UDP")) {
            UDP(ip, PKSize, Time, Delay, Type)
        }
        else if (type.equals("TCP")) {
            TCP(ip, PKSize, Time, Delay, Type)
        }
        else if (type.equals("ICMP")) {
            ICMP(ip, PKSize, Time, Delay, Type)
        }
    }

    public static void Stop() {
        // Stop the network stress test
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Javus - Java Network Stressor - v0.1.0");
        window.setVisible(true);
        window.setResizable(false);
        window.setSize(500, 500);
        window.setLayout(null);

        JComboBox<String> attack = new JComboBox<>();
        JComboBox<String> dataType = new JComboBox<>();

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

        dataType.setBounds(0, 0, 90, 40);
        dataType.addItem("UDP");
        dataType.addItem("TCP");
        dataType.addItem("ICMP");

        BeginEnd.addActionListener((ActionEvent e) -> {
            if (BeginEnd.getText().equals("Begin")) {
                BeginEnd.setText("Stop");
                try {
                    Start(IP.getText(), PacketSize.getText(), Integer.valueOf(Time.getText()), Integer.valueOf(Delay.getText()), dataType.getSelectedItem().toString());
                } catch (IOException ex) {
                    System.out.println("Error on line 115: " + ex);
                   System.out.println("\n\nPlease provide the above text and a screenshot of Javus here:\nhttps://github.com/TokynBlast/Javus/issues");
                } catch (InterruptedException ex) {}
            }
            else if (BeginEnd.getText().equals("Stop")) {
                BeginEnd.setText("Begin");
                Stop();
            }
        });

        attack.addActionListener((ActionEvent e) -> {
            if (attack.getSelectedItem().equals("Custom")) {
                IP.setEditable(true);
                IP.setText("192.168.1.1");
            }
            
            else if (attack.getSelectedItem().equals("WiFi")) {
                try {
                    Enumeration<NetworkInterface> Net = NetworkInterface.getNetworkInterfaces();
                    while (Net.hasMoreElements()) {
                        NetworkInterface currentInterface = Net.nextElement();
                        Enumeration<InetAddress> addresses = currentInterface.getInetAddresses();
                        if (currentInterface.isUp() && !currentInterface.isLoopback() && !currentInterface.isVirtual() && !currentInterface.isPointToPoint()) {
                            String displayName = currentInterface.getDisplayName().toLowerCase();
                            if (!displayName.contains("virtual") && !displayName.contains("vmware") && !displayName.contains("vbox")) {
                                if (addresses.hasMoreElements()) {
                                    InetAddress address = addresses.nextElement();
                                    String ipAddress = address.getHostAddress();
                                    if (address instanceof Inet6Address) {
                                        ipAddress = ipAddress.split("%")[0]; // Remove anything after %
                                        ipAddress = ipAddress.replaceAll("[^0-9a-fA-F:]", ""); // Remove anything not a colon, number or character
                                    } else if (address instanceof Inet4Address) {
                                        ipAddress = ipAddress.replaceAll("[^0-9.]", ""); // Remove anything not a number or dot
                                    }
                                    IP.setText(ipAddress);
                                }
                            }
                        }
                    }
                    
                    IP.setEditable(false);

                } catch (SocketException ex) {
                    IP.setText("No WiFi");
                    IP.setEditable(false);
                } finally {}
            }
            else if (attack.getSelectedItem().equals("Self")) {
                IP.setText("127.0.0.1");
                IP.setEditable(false);
            }
        });


        styleTextField(IP,         10,  100, 140, 25, "IPv4 adress to send packets to", "192.168.1.1");
        styleTextField(PacketSize, 165, 100, 50, 25,  "Size of packets to send in kB", "65"); // NEED TO ADD java.net.getMTU() TO PREVENT FRAGMENTING!! (With option for fragmenting if user wants it)
        styleTextField(Time,       230, 100, 50, 25,  "Amount of time to send packets for in seconds", "60");
        styleTextField(Delay,      295, 100, 70, 25,  "Delay between sending packets in seconds", "1000");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
        window.add(IP);
        
        window.add(attack);
        window.add(dataType);

        window.add(BeginEnd);
    }
}
