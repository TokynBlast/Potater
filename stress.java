import javax.swing.*;

public class Main {

    public static void styleTextField(JTextField textField, Integer x, Integer y, Integer width, Integer height, String tipText) {
        textField.setBounds(x, y, width, height);
        textField.setBackground(new java.awt.Color(43, 70, 204));
        textField.setForeground(java.awt.Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(java.awt.Color.BLACK));
        textField.setCaretColor(java.awt.Color.WHITE);
        textField.setSelectionColor(java.awt.Color.YELLOW);
        textField.setToolTipText(tipText);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Javus - Java Network Stressor - v0.0.1");
        window.setVisible(true);
        window.setSize(650, 500);

        BoxLayout box = new BoxLayout( window.getContentPane(), BoxLayout.X_AXIS);
        window.setLayout(null);

        JTextField IP = new JTextField();
        JTextField PacketSize = new JTextField();
        JTextField Time = new JTextField();
        JTextField Delay = new JTextField();

        styleTextField(IP,         20,  150, 140, 25, "IP Address");
        styleTextField(PacketSize, 170, 150, 140, 25, "Packet Size");
        styleTextField(Time,       320, 150, 140, 25, "Time (Seconds)");
        styleTextField(Delay,      470, 150, 140, 25, "Delay (Milliseconds)");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(IP);
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
    }
}
