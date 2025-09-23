import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class restaurant_reservation {
    public static void main(String args[]){
  
            JFrame frame = new JFrame("Restaurant Reservation");
            frame.setSize(600, 600);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setLayout(null);
            frame.setLocationRelativeTo(null);
            frame.setResizable(false);
            ImageIcon icon = new ImageIcon("HMSICON.png");
        frame.setIconImage(icon.getImage());

            JLabel label = new JLabel("Restaurant Reservation ");
            label.setBounds(150, 30, 300, 30);
            frame.add(label);
            label.setFont(new Font("Arial", Font.BOLD, 24));

            JPanel panel = new JPanel();
            panel.setBounds(37, 90, 500, 430);
            panel.setLayout(null);
            panel.setBackground(new Color(220, 220, 220));
            frame.add(panel);

            JLabel nameLabel = new JLabel("Name:");
            nameLabel.setBounds(50, 50, 100, 30);
            panel.add(nameLabel);
            JTextField nameField = new JTextField();
            nameField.setBounds(150, 50, 200, 30);
            panel.add(nameField);
            JLabel dateLabel = new JLabel("Date (DD/MM/YYYY):");
            dateLabel.setBounds(50, 100, 150, 30);
            panel.add(dateLabel);
            JTextField dateField = new JTextField();
            dateField.setBounds(200, 100, 150, 30);
            panel.add(dateField);
            JLabel timeLabel = new JLabel("Time (HH:MM):");
            timeLabel.setBounds(50, 150, 100, 30);
            panel.add(timeLabel);
            JTextField timeField = new JTextField();
            timeField.setBounds(150, 150, 200, 30);
            panel.add(timeField);
            JLabel guestsLabel = new JLabel("Number of Guests:");
            guestsLabel.setBounds(50, 200, 150, 30);
            panel.add(guestsLabel);
            JSpinner guestsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
            guestsSpinner.setBounds(200, 200, 150, 30);
            panel.add(guestsSpinner);
            JButton submitButton = new JButton("Submit");
            submitButton.setBounds(200, 300, 100, 30);
            panel.add(submitButton);
            submitButton.addActionListener(e -> {
                String name = nameField.getText().trim();
                String date = dateField.getText().trim();
                String time = timeField.getText().trim();
                int guests = (int) guestsSpinner.getValue();
                StringBuilder errorMsg = new StringBuilder();
                if (name.isEmpty()) errorMsg.append("- Name is required.\n");
                if (date.isEmpty()) errorMsg.append("- Date is required.\n");
                if (time.isEmpty()) errorMsg.append("- Time is required.\n");
                if (guests < 1) errorMsg.append("- Number of guests must be at least 1.\n");
                java.time.LocalDateTime reservationDateTime = null;
                if (errorMsg.length() == 0) {
                    try {
                        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                        reservationDateTime = java.time.LocalDateTime.parse(date + " " + time, formatter);
                        java.time.LocalDateTime now = java.time.LocalDateTime.now();
                        java.time.LocalDateTime maxDate = now.plusDays(5);
                        if (reservationDateTime.isBefore(now)) {
                            errorMsg.append("- Reservation time cannot be in the past.\n");
                        } else if (reservationDateTime.isAfter(maxDate)) {
                            errorMsg.append("- Reservation can only be made up to 5 days in advance.\n");
                        }
                    } catch (Exception ex) {
                        errorMsg.append("- Invalid date or time format. Use DD/MM/YYYY for date and HH:MM for time.\n");
                    }
                }
                if (errorMsg.length() > 0) {
                    JOptionPane.showMessageDialog(frame, errorMsg.toString(), "Missing or Invalid Information", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                        "Reservation made for " + name + " on " + date + " at " + time + " for " + guests + " guests.",
                        "Reservation Confirmed", JOptionPane.INFORMATION_MESSAGE);
                }
            });

            frame.setVisible(true);
       
    }
}

