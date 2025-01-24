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
                if (textField.getText().equals(placeHolder))) {
                    textField.setForeground(java.awt.Color.WHITE);
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(""))) {
                    textField.setForeground(java.awt.Color.GRAY);
                    textField.setText(placeHolder);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame window = new JFrame();
        window.setTitle("Javus - Java Network Stressor - v0.0.1");
        window.setVisible(true);
        window.setSize(500, 500);
        window.setLayout(null);

        JTextField IP = new JTextField();
        JTextField PacketSize = new JTextField();
        JTextField Time = new JTextField();
        JTextField Delay = new JTextField();

        styleTextField(IP,         10,  100, 140, 25, "IPv4 Address", "192.168.1.1");
        styleTextField(PacketSize, 165, 100, 50, 25, "Packet Size (Kilabytes)", "65");
        styleTextField(Time,       230, 100, 50, 25, "Time (Seconds)", "60");
        styleTextField(Delay,      295, 100, 70, 25, "Delay (Milliseconds)", "1000");

        window.getContentPane().setBackground(new java.awt.Color(247, 183, 21));
        window.add(IP);
        window.add(PacketSize);
        window.add(Time);
        window.add(Delay);
    }
}
