import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class Javus {
    private static ScheduledExecutorService executor;
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
            
            private static DatagramSocket door;
        
            public static void Start(String ip, String PKSize, Integer Time, Integer Delay) throws IOException, InterruptedException {
                try {
                    door = new DatagramSocket();
                    byte[] buffer = "Hello From Javus :) 🥔🥔🥔🥔🥔🥔🥔🥔🥔🥔".getBytes();
                    InetAddress address;
                    if (ip.contains("%")) {
                        String[] parts = ip.split("%");
                        NetworkInterface nif = NetworkInterface.getByName(parts[1]);
                        if (nif != null) {
                            address = Inet6Address.getByAddress(null, InetAddress.getByName(parts[0]).getAddress(), nif.getIndex());
                        } else {
                            throw new SocketException("Network interface not found: " + parts[1]);
                        }
                    } else {
                        address = InetAddress.getByName(ip);
                    }
                    if (address instanceof Inet6Address && ((Inet6Address) address).isLinkLocalAddress()) {
                        NetworkInterface nif = NetworkInterface.getByInetAddress(address);
                        if (nif != null) {
                            address = Inet6Address.getByAddress(null, address.getAddress(), nif.getIndex());
                        } else {
                            throw new SocketException("Network interface not found for address: " + address);
                        }
                    }
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 42069); // Assuming port 12345
                    executor = Executors.newScheduledThreadPool(1);
                    Runnable task = () -> {
                        try {
                            long endTime = System.currentTimeMillis() + Time;
                            while (System.currentTimeMillis() < endTime && !executor.isShutdown()) {
                                door.send(packet);
                            }
                        } catch (IOException ex) {
                            System.out.println("Error on 66: " + ex);
                        }
                    };
                    executor.scheduleAtFixedRate(task, 0, Delay, TimeUnit.MILLISECONDS);
                    executor.awaitTermination(Time, TimeUnit.SECONDS);
                    executor.shutdown();
                } catch (SocketException ex) {
                    System.out.println("Error 64: " + ex);
                    System.out.println("\n\nPlease provide the above text and a screenshot of Javus here:\nhttps://github.com/TokynBlast/Javus/issues");
                } finally {}
            }

            public static void Stop() {
                if (executor != null && !executor.isShutdown()) {
                    executor.shutdownNow();
                }
                if (door != null && !door.isClosed()) {
                    door.close();
                }
            }

            public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Javus - Java Network Stressor - v0.0.1");
        window.setVisible(true);
        window.setResizable(false);
        window.setSize(500, 500);
        window.setLayout(null);

        JComboBox<String> attack = new JComboBox<>();
        JComboBox<String> Type = new JComboBox<>();

        JTextField IP = new JTextField();
        JTextField PacketSize = new JTextField();
        JTextField Time = new JTextField();
        JTextField Delay = new JTextField();

        JButton BeginEnd = new JButton();

        JLabel PacketSent = new JLabel();
        JLabel PacketReceived = new JLabel();
        JLabel PacketLoss = new JLabel();
        JLabel TimeLeft = new JLabel();

        Type.addItem("UDP");
        Type.addItem("TCP");
        Type.addItem("ICMP");

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

        BeginEnd.addActionListener((ActionEvent e) -> {
            if (BeginEnd.getText().equals("Begin")) {
                BeginEnd.setText("Stop");
                SwingUtilities.invokeLater(() -> {
                    try {
                        Start(IP.getText(), PacketSize.getText(), Integer.valueOf(Time.getText()), Integer.valueOf(Delay.getText()));
                    } catch (IOException ex) {
                        System.out.println("Error on line 115: " + ex);
                        System.out.println("\n\nPlease provide the above text and a screenshot of Javus here:\nhttps://github.com/TokynBlast/Javus/issues");
                    } catch (InterruptedException ex) {}
                });
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
                if (Type.getSelectedItem().equals("UDP")) {
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
                                            ipAddress = ipAddress.split("%")[0];
                                            ipAddress = ipAddress.replaceAll("[^0-9a-fA-F:]", ""); // Remove anything not a colon, number or character
                                        } else if (address instanceof Inet4Address) {
                                            ipAddress = ipAddress.replaceAll("[^0-9.]", ""); // Remove anything not a number or dot
                                        }
                                        IP.setText(ipAddress);
                                    }
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
        styleTextField(Delay,      295, 100, 70, 25,  "Delay between sending packets in milliseconds", "1000");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
        window.add(IP);
        
        window.add(attack);

        window.add(BeginEnd);
    }

    public static ScheduledExecutorService getExecutor() {
        return executor;
    }

    public static void setExecutor(ScheduledExecutorService executor) {
        Javus.executor = executor;
    }
}
