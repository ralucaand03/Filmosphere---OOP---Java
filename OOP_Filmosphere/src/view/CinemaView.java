package view;

import controller.CinemaController;
import model.CinemaModel;
import model.Colors;
import model.SignUpModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class CinemaView {
    private JFrame frame = new JFrame("Cinemas");
    private DefaultTableModel cinemaTableModel;
    private JTable cinemaTable;
    private JButton goBackButton;
    private JButton sortByNameButton;
    private JButton sortByLocationButton;
    private JButton insertCinemaButton;
    private JButton deleteCinemaButton;
    private CinemaController controller;
    private List<CinemaModel> sortedCinemas;  // Keep track of the current sorted order

    // Accessing the colors
    Color mainColor = Colors.MAIN_COLOR.getColor();
    Color darkColor = Colors.DARK_COLOR.getColor();
    Color lightColor = Colors.LIGHT_COLOR.getColor();


    public CinemaView(CinemaController controller, SignUpModel signUpModel) {
        this.controller = controller;

        JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56");
        fLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 20));
        fLabel.setForeground(mainColor);

        // Create table model and table
        cinemaTableModel = new DefaultTableModel(new Object[]{"Cinema Name", "Location", "Movies List"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2;
            }
        };
        cinemaTable = new JTable(cinemaTableModel);
        cinemaTable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        cinemaTable.setForeground(darkColor);
        // Set a custom editor for the "Action" column
        TableColumn actionColumn = cinemaTable.getColumnModel().getColumn(2);
        actionColumn.setCellEditor(new ActionCellEditor(new JTextField()));
        // Add cinemas to the table
        List<String> cinemaNames = controller.getCinemaNames();
        for (String cinemaName : cinemaNames) {
            // Retrieve the location for each cinema
            String location = controller.getCinemaLocation(cinemaName);

            cinemaTableModel.addRow(new Object[]{cinemaName, location, "Movies"});
        }

        // Create sort buttons
        sortByNameButton = new JButton("Sort by Name");
        sortByLocationButton = new JButton("Sort by Location");

        sortByNameButton.addActionListener(e -> {
            try {
                // Clear the existing rows in the table
                cinemaTableModel.setRowCount(0);

                // Fetch sorted cinema names
                List<String> cinemaNamesList = controller.handleSortByName();

                // Add sorted cinemas to the table
                for (String cinemaName : cinemaNamesList) {
                    // Retrieve the location for each cinema
                    String location = controller.getCinemaLocation(cinemaName);

                    // Add a new row to the table model
                    cinemaTableModel.addRow(new Object[]{cinemaName, location, "Movies"});
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        sortByLocationButton.addActionListener(e -> {
            try {
                // Clear the existing rows in the table
                cinemaTableModel.setRowCount(0);

                // Fetch cinemas sorted by location
                List<String> cinemaNamesList = controller.handleSortByLocation();

                // Add sorted cinemas to the table
                for (String cinemaName : cinemaNamesList) {
                    // Retrieve the location for each cinema
                    String location = controller.getCinemaLocation(cinemaName);

                    // Add a new row to the table model
                    cinemaTableModel.addRow(new Object[]{cinemaName, location, "Movies"});
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Create "Go Back" button
        goBackButton = new JButton("Go Back");
        goBackButton.setBackground(lightColor);
        goBackButton.addActionListener(e -> {
            try {
                controller.goBackButtonClicked();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Check if the user is an admin
        String email = signUpModel.getEmail();
        int option = 0;

        if (email.matches("^[a-zA-Z]+(\\.[a-zA-Z]+)?@admin\\.com$")) {
            option = 1;
        }

        // If the user is an admin, add "Insert Cinema" and "Delete Cinema" buttons
        if (option == 1) {
            insertCinemaButton = new JButton("Insert Cinema");
            deleteCinemaButton = new JButton("Delete Cinema");

            insertCinemaButton.setBackground(lightColor);
            deleteCinemaButton.setBackground(lightColor);
            // Add action listeners for the buttons
            insertCinemaButton.addActionListener(e -> {
                // Create a panel with text fields for user input
                JPanel inputPanel = new JPanel(new GridLayout(2, 2));
                JTextField cinemaNameField = new JTextField();
                JTextField locationField = new JTextField();

                inputPanel.add(new JLabel("Cinema Name:"));
                inputPanel.add(cinemaNameField);
                inputPanel.add(new JLabel("Location:"));
                inputPanel.add(locationField);

                // Show input dialog to get user input
                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Cinema Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // Retrieve user input and call handleInsertCinema
                    String cinemaName = cinemaNameField.getText();
                    String location = locationField.getText();

                    try {
                        controller.handleInsertCinema(cinemaName, location);
                    } catch (SQLException ex) {
                        // Handle exceptions, e.g., show an error message
                        ex.printStackTrace();
                    }
                }
            });

            deleteCinemaButton.addActionListener(e -> {
                // Get the selected cinema names from the table
                int[] selectedRows = cinemaTable.getSelectedRows();
                List<String> selectedCinemaNames = controller.getSelectedCinemas(selectedRows);

                // Call handleDeleteCinema with the selected cinema names
                if (!selectedCinemaNames.isEmpty()) {
                    controller.handleDeleteCinema(selectedCinemaNames);
                }
            });
        }
        sortByNameButton.setBackground(lightColor);
        sortByLocationButton.setBackground(lightColor);
        goBackButton.setBackground(lightColor);
        // Create layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(fLabel, BorderLayout.NORTH);

        // Directly add the JScrollPane to the cinemaTable
        JScrollPane cinemaTableScrollPane = new JScrollPane(cinemaTable);
        mainPanel.add(cinemaTableScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(sortByNameButton);
        buttonPanel.add(sortByLocationButton);

        if (option == 1) {
            buttonPanel.add(insertCinemaButton);
            buttonPanel.add(deleteCinemaButton);
        }

        buttonPanel.add(goBackButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(mainPanel);
        frame.setSize(400, 300);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void updateCinemaTable(List<CinemaModel> cinemas) {
        sortedCinemas = cinemas;  // Update the sortedCinemas list

        cinemaTableModel.setRowCount(0);

        for (CinemaModel cinema : sortedCinemas) {
            cinemaTableModel.addRow(new Object[]{cinema.getCinemaName(), cinema.getLocation(), "Movies"});
        }

        cinemaTable.repaint(); // Force the table to repaint after updating the data
    }

    public DefaultTableModel getCinemaTableModel() {
        return cinemaTableModel;
    }

    private class ActionCellEditor extends AbstractCellEditor implements TableCellEditor {
        private JButton button;

        public ActionCellEditor(JTextField textField) {
            button = new JButton();
            button.addActionListener(e -> {
                int selectedRow = cinemaTable.getSelectedRow();
                if (selectedRow != -1) {
                    String cinemaName = (String) cinemaTableModel.getValueAt(selectedRow, 0);
                    controller.handleMoviesButtonClick(cinemaName);
                }
                stopCellEditing();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText("Movies");
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public boolean stopCellEditing() {
            return true;
        }
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }
}
