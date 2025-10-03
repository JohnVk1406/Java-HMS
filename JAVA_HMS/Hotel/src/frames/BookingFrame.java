package frames;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import model.User;
import dao.UserDAO;
import util.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class BookingFrame extends JFrame {

    private JTable bookingTable;
    private JButton btnBack, btnNewBooking, btnExtend, btnCancel;

    public BookingFrame() {
        setTitle("Bookings");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
        ImageIcon icon = new ImageIcon("assets/HMSICON.png");
        setIconImage(icon.getImage());

        // Table Coloumns
        String[] columns = {"Booking ID", "Guest", "Room", "Check In", "Check Out", "Status"};
        Object data[][] = {{"XYZH001", "Sarath", 101, "06/09/2025", "10/09/2025", "Active"}, {"XYZH002", "Jonas", 202, "2025-09-07", "2025-09-09", "Cancelled"}}; // Dummy data for testing purposes -- Need to connect DB 
        //Bookinf ID = XYZH + table id (3 digits)
        //Guest name from user id and users table
        //Room number from room table
        //for the time being printing the users

        /* try(Connection conn = util.Utils.getConnection()){
            UserDAO ud = new UserDAO();
            List<User> users = ud.findAll(conn);
            for (User user : users) {
                System.out.println(user);
            }
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        } */


        DefaultTableModel model = new DefaultTableModel(data, columns) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
            }
        };

        bookingTable = new JTable(model);
        bookingTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(bookingTable), BorderLayout.CENTER);


        // Buttons for further actions
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnNewBooking = new JButton("New Booking");
        btnExtend = new JButton("Extend Booking");
        btnCancel = new JButton("Cancel Booking");

        buttonPanel.add(btnNewBooking);
        buttonPanel.add(btnExtend);
        buttonPanel.add(btnCancel);
        add(buttonPanel, BorderLayout.SOUTH);

        // Back button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnBack = new JButton("← Back to Dashboard");
        topPanel.add(btnBack);
        add(topPanel, BorderLayout.NORTH);


        // Button actions
        btnNewBooking.addActionListener(e -> newBooking());
        btnExtend.addActionListener(e -> handleExtendBooking());
        btnCancel.addActionListener(e -> handleCancelBooking());
        btnBack.addActionListener(e -> goBack());

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void newBooking() {
        dispose();
        RoomBooking.main(new String[]{});
    }
    private void handleExtendBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow >= 0) {
            String bookingIdmod = bookingTable.getValueAt(selectedRow, 0).toString().substring(4);
            int bookingId = (int) Integer.parseInt(bookingIdmod);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bookingTable);
            extendBooking(frame, bookingId);
        } else {
            centeringDialog("Select a booking to extend", "Alert");
        }
    }
    private void extendBooking(JFrame parent, int bookingId) {
        JTextField newDateField = new JTextField(10);
        JOptionPane optionPane = new JOptionPane(
            new Object[] {
                "Extend booking ID: " + bookingId,
                "Enter new checkout date (DD/MM/YYYY):", newDateField
            },
            JOptionPane.QUESTION_MESSAGE,
            JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog(parent, "Extend Booking");
        dialog.setSize(400, 200);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
        Object selectedValue = optionPane.getValue();
            if (selectedValue != null && (int) selectedValue == JOptionPane.OK_OPTION) {
                StringBuilder errorMsg = new StringBuilder();
                String checkIn = bookingTable.getValueAt(bookingTable.getSelectedRow(), 3).toString();
                String checkOut = bookingTable.getValueAt(bookingTable.getSelectedRow(), 4).toString();
                String newDate = newDateField.getText().trim();
                if (newDate.isEmpty()) errorMsg.append("- New date is required.\n");
                java.time.LocalDate checkInDate = null, checkOutDate = null, newCheckOutDate = null;
                try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        checkInDate = java.time.LocalDate.parse(checkIn, formatter);
                        checkOutDate = java.time.LocalDate.parse(checkOut, formatter);
                        newCheckOutDate = java.time.LocalDate.parse(newDate, formatter);
                        java.time.LocalDate now = java.time.LocalDate.now();
                        if (!newCheckOutDate.isAfter(checkOutDate)) {
                            errorMsg.append("- New date cannot be before current check-out date.\n");
                        }
                    } catch (Exception ex) {
                        errorMsg.append("- Invalid date format. Use DD/MM/YYYY for date.\n");
                        System.out.println(ex.getMessage());
                    }
                if (errorMsg.length() > 0) {
                    centeringDialog(errorMsg.toString(), "Alert");
                    return;
                }
                else {
                    JOptionPane infoPane = new JOptionPane(
                            "Booking ID " + bookingId + " has been extended to " + newDate,
                            JOptionPane.INFORMATION_MESSAGE);

                    JDialog infoDialog = infoPane.createDialog(parent, "Extended");
                    infoDialog.setSize(450, 200);
                    infoDialog.setLocationRelativeTo(parent);
                    infoDialog.setVisible(true);
                }
            }
    }

    private void handleCancelBooking() {
        int selectedRow = bookingTable.getSelectedRow();
        if (selectedRow >= 0) {
            String bookingIdmod = bookingTable.getValueAt(selectedRow, 0).toString().substring(4);
            int bookingId = (int) Integer.parseInt(bookingIdmod);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(bookingTable);
            cancelBooking(frame, bookingId);
        } else {
            centeringDialog("Select a booking to cancel", "Alert");
        }
    }
    private void cancelBooking(JFrame parent, int bookingId) {
        JOptionPane optionPane = new JOptionPane(
            "Cancel booking ID: " + bookingId + "?",
            JOptionPane.WARNING_MESSAGE,
            JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(parent, "Cancel Booking");
        dialog.setSize(400, 200);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);

        Object selectedValue = optionPane.getValue();
        if (selectedValue != null && (int) selectedValue == JOptionPane.YES_OPTION) {
            JOptionPane infoPane = new JOptionPane(
                    "Booking ID " + bookingId + " has been cancelled.",
                    JOptionPane.INFORMATION_MESSAGE);

            JDialog infoDialog = infoPane.createDialog(parent, "Cancelled");
            infoDialog.setSize(400, 200);
            infoDialog.setLocationRelativeTo(parent);
            infoDialog.setVisible(true);
        }
    }
    private void goBack() {
        dispose();
        
    }

    private void centeringDialog(String message, String title) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.INFORMATION_MESSAGE);
        JDialog dialog = optionPane.createDialog(this, title);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingFrame().setVisible(true));
    }
}
