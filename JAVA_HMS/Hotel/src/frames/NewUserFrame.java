package frames;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.regex.*;

public class NewUserFrame extends JFrame {

private JTextField guestNameField, emailField, phoneNoField;
private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
private static final Pattern PHONE_PATTERN = Pattern.compile("^[0-9]{10}$");

    public NewUserFrame() {
        setTitle("New User");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        ImageIcon icon = new ImageIcon("HMSICON.png");
        setIconImage(icon.getImage());

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                "User Details",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14)
        ));

        formPanel.add(new JLabel("Guest Name:"));
        guestNameField = new JTextField(30);
        guestNameField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(guestNameField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(30);
        emailField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Phone Number:"));
        phoneNoField = new JTextField(30);
        phoneNoField.setPreferredSize(new Dimension(120, 25));
        formPanel.add(phoneNoField);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JButton registerButton = new JButton("Register and Proceed to Book");
        registerButton.addActionListener(e -> {
            String name = guestNameField.getText().trim();
            String email = emailField.getText().trim();
            String phoneno = phoneNoField.getText().trim();
            StringBuilder errorMsg = new StringBuilder();
            if (email.isEmpty()) errorMsg.append("- Email is required.\n");
            if (name.isEmpty()) errorMsg.append("- Name is required.\n");
            if (phoneno.isEmpty()) errorMsg.append("- Phone No is required.\n");
            if(errorMsg.length() == 0) 
            {
                if(!EMAIL_PATTERN.matcher(email).matches())
                    errorMsg.append("- Invalid email format.\n");
                if(!PHONE_PATTERN.matcher(phoneno).matches())
                    errorMsg.append("- Invalid phone number format.\n");
            }
            if (errorMsg.length() > 0) {
                JOptionPane.showMessageDialog(this, errorMsg.toString(), "Missing or Invalid Information", JOptionPane.ERROR_MESSAGE);
            } else {
                //check if email is registered
                //if not
                //add to user table
                dispose();
                RoomBooking.main(new String[]{});
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            dispose();
            new BookingFrame().setVisible(true);
        });

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new NewUserFrame().setVisible(true));
    }
}
