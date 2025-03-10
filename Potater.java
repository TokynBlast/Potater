import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

// Error formatting: e[error #][exception initials]


public class Potater {

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

    public static void UDP(String ip, Integer PKSize, Integer Time, Integer Delay, JLabel Terminal) throws IOException, InterruptedException {

        try {
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
            InetAddress localAddress = networkInterface.getInetAddresses().nextElement();
            try (DatagramSocket door = new DatagramSocket(0, localAddress)) {
                byte[] buffer = "Hello From Potater :) 🥔🥔🥔🥔".getBytes();
                InetAddress address;
                address = InetAddress.getByName(ip);
                if (address instanceof Inet6Address) {
                    Inet6Address ipv6Address = (Inet6Address) address;
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
                            addText(Terminal, "Sent packet!");
                        }
                    } catch (IOException ex) {
                        addText(Terminal, "Error e1IO: " + ex + "<br>Please fill out a bug report here:<br>github.com/TokynBlast/Potater/issues");
                    }
                };
                if (Delay >= 0) {
                    executor.scheduleAtFixedRate(task, 0, Delay, TimeUnit.MILLISECONDS);
                    executor.awaitTermination(Time + (Delay / 1000), TimeUnit.SECONDS);
                } else {
                    throw new IllegalArgumentException("Delay must be non-negative and non-zero");
                }
                executor.shutdown();
            } finally {}
        } catch (SocketException ex) {

            addText(Terminal, "Error e2SE: " + ex + "<br>Please fill out a bug report here:<br>github.com/TokynBlast/Potater/issues");
        } finally {}
    }

    public static void TCP(String ip, Integer PKSize, Integer Time, Integer Delay, JLabel Terminal) {
    try {
        // Create a socket to connect to the specified IP and port
        try (Socket socket = new Socket(ip, 42069)) {
            byte[] buffer = new byte[PKSize * 1024]; // Convert kB to bytes
            ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
            Runnable task = () -> {
                try {
                    long endTime = System.currentTimeMillis() + Time * 1000; // Convert seconds to milliseconds
                    OutputStream outputStream = socket.getOutputStream();
                    while (System.currentTimeMillis() < endTime) {
                        outputStream.write(buffer);
                        outputStream.flush();
                        addText(Terminal, "Sent TCP packet!");
                        Thread.sleep(Delay);
                    }
                } catch (IOException ex) {
                    addText(Terminal, "Error e3IO: " + ex + "<br>Please fill out a bug report here:<br>github.com/TokynBlast/Potater/issues");
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt(); // Restore interrupted status
                }
            };
            executor.schedule(task, 0, TimeUnit.MILLISECONDS);
            executor.awaitTermination(Time + (Delay * 1000), TimeUnit.MILLISECONDS);
            executor.shutdown();
        }
    } catch (IOException ex) {
        addText(Terminal, "Error e2IO: " + ex + "<br>Please fill out a bug report here:<br>github.com/TokynBlast/Potater/issues");
    }
}

    public static void ICMP(String ip, Integer PKSize, Integer Time, Integer Delay, JLabel Terminal){

    }

    public static void Start(String ip, Integer PKSize, Integer Time, Integer Delay, String Type, JLabel Terminal) throws IOException, InterruptedException {
        addText(Terminal, "Beginning attack.");
        if (Type.equals("UDP")) {
            UDP(ip, PKSize, Time, Delay, Terminal);
        }
        else if (Type.equals("TCP")) {
            TCP(ip, PKSize, Time, Delay, Terminal);
        }
        else if (Type.equals("ICMP")) {
            ICMP(ip, PKSize, Time, Delay, Terminal);

        }
    }

    public static void Stop() {
        // Stop the network stress test
    }

    private static ArrayList<String> textLines = new ArrayList<>();

    public static void addText(JLabel label, String newText) {
        textLines.add(newText);
        while (textLines.size() > 11) {
            textLines.remove(0);
        }
        StringBuilder sb = new StringBuilder("<html>");
        for (String line : textLines) {
            sb.append(line).append("<br>");
        }
        sb.append("</html>");
        label.setText(sb.toString());
    }


    public static void main(String[] args) {
        System.out.println("Creating Window");
        JFrame window = new JFrame();
        window.setTitle("Potater - Java Network Stressor - v0.1.1");

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.setResizable(false);
        window.setSize(500, 500);
        window.setLayout(null);

        System.out.println("Creating GUI");

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

        JCheckBox Fragging = new JCheckBox();
        JCheckBox TermDebugging = new JCheckBox(); // Print lines to the terminal, as they go inside the terminal inside the Potater window.

        JLabel term = new JLabel();

        System.out.println("Styling GUI");
        
        term.setOpaque(true);
        term.setBounds(50, 244, 400, 194);
        term.setBackground(Color.BLACK);
        term.setForeground(Color.WHITE);
        term.setFont(new Font("Monospaced", Font.PLAIN, 14));
        term.setVerticalAlignment(SwingConstants.BOTTOM);
        term.setHorizontalAlignment(SwingConstants.LEFT);


        Fragging.setText("Allow fraggmenting?");

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

        dataType.setBounds(100, 0, 90, 40);
        dataType.addItem("UDP");
        dataType.addItem("TCP");
        // dataType.addItem("ICMP");

        styleTextField(IP,         10,  100, 140, 25, "IP address to send packets to", "192.168.1.1");
        styleTextField(PacketSize, 165, 100, 50, 25,  "Size of packets to send in kB", "65"); // NEED TO ADD java.net.getMTU() TO PREVENT FRAGMENTING!! (With option for fragmenting if user wants it)
        styleTextField(Time,       230, 100, 50, 25,  "Amount of time to send packets for in seconds", "60");
        styleTextField(Delay,      295, 100, 70, 25,  "Delay between sending packets in seconds", "1000");

        System.out.println("Adding actions to GUI");

        BeginEnd.addActionListener((ActionEvent e) -> {
            if (BeginEnd.getText().equals("Begin")) {
                BeginEnd.setText("Stop");

                SwingWorker<Void, Void> worker = new SwingWorker<>() {
                    @Override
                    protected Void doInBackground() throws Exception {
                        try {
                            Start(IP.getText(), Integer.parseInt(PacketSize.getText()),
                                  Integer.valueOf(Time.getText()), Integer.valueOf(Delay.getText()),
                                  dataType.getSelectedItem().toString(), term);
                        } catch (IOException ex) {
                            addText(term, "Error e4IO: " + ex + "<br>Please fill out a bug report here:<br>github.com/TokynBlast/Potater/issues");
                        } catch (InterruptedException ex) {
                            addText(term, "Error e5IE: " + ex + "<br>Please fill out a bug report here:<br>github.com/TokynBlast/Potater/issues");
                        }
                        return null;
                    }

                    @Override
                    protected void done() {
                        BeginEnd.setText("Begin");
                        addText(term, "Attack over.");
                    }
                    
                };

                worker.execute();
            } else if (BeginEnd.getText().equals("Stop")) {
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
                    // String name_ = "";
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
                                    // name_ = currentInterface.getName(); // Needs to get WiFi Name, not type.
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
                    addText(term, "Successfully Grabbed WiFi IP: "); // + name_

                } catch (SocketException ex) {
                    IP.setText("No WiFi");
                    IP.setEditable(false);
                    addText(term, "Couldn't find WiFi IP.");
                } finally {}
            }
            else if (attack.getSelectedItem().equals("Self")) {
                IP.setText("::1");
                IP.setEditable(false);
            }
        });

         System.out.println("Applying GUI");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
        window.add(IP);

        window.add(attack);
        window.add(dataType);

        window.add(BeginEnd);

        window.add(term);

        addText(term, "Successfully started Potater!");

        window.setVisible(true);
        System.out.println("Potater started! Have fun! :)");
    }
}
