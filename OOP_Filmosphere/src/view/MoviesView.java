package view;

import controller.MoviesController;
import model.Colors;
import model.MoviesModel;
import model.SignUpModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MoviesView {
    private JFrame frame = new JFrame("Movies");
    private JComboBox<String> genreComboBox;
    private DefaultTableModel movieTableModel;
    private JTable movieTable;

    // Accessing the colors
    Color mainColor = Colors.MAIN_COLOR.getColor();
    Color darkColor = Colors.DARK_COLOR.getColor();
    Color lightColor = Colors.LIGHT_COLOR.getColor();

    public MoviesView(MoviesController controller, SignUpModel model) {
        // Create components
        JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56");
        fLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 20));
        fLabel.setForeground(lightColor);

        JLabel titleLabel = new JLabel("Movies");
        titleLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        genreComboBox = new JComboBox<>(new String[]{"ALL", "COMEDY", "DRAMA", "ROMANCE", "SF", "HORROR"});
        genreComboBox.addActionListener(e -> {
            // Handle genre selection, e.g., call controller method
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            List<MoviesModel> moviesList = controller.handleGenreSelection(selectedGenre);
            updateMovieTable(moviesList);
        });
        //Admin
        String email = model.getEmail();
        int option = 0;

        if (email.matches("^[a-zA-Z]+(\\.[a-zA-Z]+)?@admin\\.com$")) {
            option=1;
        }

        // Create buttons for ordering
        JButton orderTitleButton = new JButton("Order by Title");
        JButton orderReleaseDateButton = new JButton("Order by Release Date");

        // Create table model and table
        movieTableModel = new DefaultTableModel(new Object[]{"Title", "Duration", "Release Date", "Description", "Poster URL"}, 0);
        movieTable = new JTable(movieTableModel);
        JScrollPane tableScrollPane = new JScrollPane(movieTable);

        // Create "Go Back" button
        JButton goBackButton = new JButton("Go Back");
        goBackButton.setBackground(lightColor);
        goBackButton.addActionListener(e -> {
            try {
                controller.goBackButtonClicked();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

// Create layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Align button to the right
        JPanel comboPanel = new JPanel(new FlowLayout());
        comboPanel.add(genreComboBox);

// Search bar components
        JTextField searchBar = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> {
            // Handle search action, e.g., call controller method with the search query
            String searchQuery = searchBar.getText();
            List<MoviesModel> searchResult = controller.handleSearch(searchQuery);
            updateMovieTable(searchResult);
        });

// Add search bar components to a panel
        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Search by Title: "));
        searchPanel.add(searchBar);
        searchPanel.add(searchButton);
        JPanel centerPanel;
        if (option==0) {
            centerPanel = new JPanel(new GridLayout(5, 1));
        }
        else {
            centerPanel = new JPanel(new GridLayout(6, 1));
        }
// First row (titleLabel) with less vertical space
        JPanel firstRowPanel = new JPanel(new BorderLayout());
        firstRowPanel.add(titleLabel, BorderLayout.CENTER);
        firstRowPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Adjust the bottom border height

// Second row (comboPanel) with less vertical space
        JPanel secondRowPanel = new JPanel(new BorderLayout());
        secondRowPanel.add(comboPanel, BorderLayout.CENTER);
        secondRowPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Adjust the bottom border height

// Third row (searchPanel) for the search bar
        centerPanel.add(firstRowPanel);
        centerPanel.add(secondRowPanel);
        centerPanel.add(searchPanel);


        // Check if the user is an admin (option == 1)
        if (option == 1) {
            // Add "Insert Movie" and "Delete Movie" buttons
            JButton insertMovieButton = new JButton("Insert Movie");
            JButton deleteMovieButton = new JButton("Delete Movie");

            // Add action listeners for the buttons
            insertMovieButton.addActionListener(e -> {
                // Create a panel with text fields for user input
                JPanel inputPanel = new JPanel(new GridLayout(6, 2));  // Increased the grid size for the "Release Date" field
                JTextField titleField = new JTextField();
                JTextField durationField = new JTextField();
                JTextField releaseDateField = new JTextField();  // Added the "Release Date" field
                JTextField descriptionField = new JTextField();
                JTextField posterUrlField = new JTextField();
                JTextField genreField = new JTextField();

                inputPanel.add(new JLabel("Title:"));
                inputPanel.add(titleField);
                inputPanel.add(new JLabel("Duration:"));
                inputPanel.add(durationField);
                inputPanel.add(new JLabel("Release Date (YYYY-MM-DD):"));
                inputPanel.add(releaseDateField);
                inputPanel.add(new JLabel("Description:"));
                inputPanel.add(descriptionField);
                inputPanel.add(new JLabel("Poster URL:"));
                inputPanel.add(posterUrlField);
                inputPanel.add(new JLabel("Genre:"));
                inputPanel.add(genreField);

                // Show input dialog to get user input
                int result = JOptionPane.showConfirmDialog(null, inputPanel, "Enter Movie Details", JOptionPane.OK_CANCEL_OPTION);

                if (result == JOptionPane.OK_OPTION) {
                    // Retrieve user input and call handleInsertMovie
                    String title = titleField.getText();
                    int duration = Integer.parseInt(durationField.getText());
                    String releaseDateString = releaseDateField.getText();
                    String description = descriptionField.getText();
                    String posterUrl = posterUrlField.getText();
                    int genre = Integer.parseInt(genreField.getText());

                    try {
                        // Parse the release date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date releaseDate = dateFormat.parse(releaseDateString);

                        controller.handleInsertMovie(title, duration, releaseDate, description, posterUrl, genre);
                    } catch (ParseException | SQLException ex) {
                        // Handle exceptions, e.g., show an error message
                        ex.printStackTrace();
                    }
                }
            });

            deleteMovieButton.addActionListener(e -> {
                // Get the selected movie titles from the table
                int[] selectedRows = movieTable.getSelectedRows();
                List<String> selectedTitles = new ArrayList<>();

                for (int selectedRow : selectedRows) {
                    String title = (String) movieTableModel.getValueAt(selectedRow, 0);
                    selectedTitles.add(title);
                }

                // Call handleDeleteMovie with the selected titles
                if (!selectedTitles.isEmpty()) {
                    controller.handleDeleteMovie(selectedTitles);
                }
            });

            // Create a panel to hold the buttons
            JPanel adminButtonsPanel = new JPanel(new FlowLayout());
            adminButtonsPanel.add(insertMovieButton);
            adminButtonsPanel.add(deleteMovieButton);

            // Add the panel with admin buttons to centerPanel
            centerPanel.add(adminButtonsPanel);
        }

        // ... existing code ...
        // Add action listeners for the ordering buttons
        orderTitleButton.addActionListener(e -> {
            List<MoviesModel> orderedMovies = controller.handleGetMoviesOrderedByTitle();
            updateMovieTable(orderedMovies);
        });

        orderReleaseDateButton.addActionListener(e -> {
            List<MoviesModel> orderedMovies = controller.handleGetMoviesOrderedByReleaseDate();
            updateMovieTable(orderedMovies);
        });

        // Create a panel to hold the ordering buttons
        JPanel orderButtonsPanel = new JPanel(new FlowLayout());
        orderButtonsPanel.add(orderTitleButton);
        orderButtonsPanel.add(orderReleaseDateButton);

        // Add the ordering buttons panel to centerPanel
        centerPanel.add(orderButtonsPanel);

        centerPanel.add(tableScrollPane);

        mainPanel.add(fLabel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

// Add the panels to centerPanel
        buttonPanel.add(goBackButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.setBackground(mainColor);

        frame.getContentPane().add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);  // Adjust the size as needed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Method to update the table with new movie data
    public void updateMovieTable(List<MoviesModel> moviesList) {
        // Clear the existing table data
        movieTableModel.setRowCount(0);

        // Populate the table with the new data
        for (MoviesModel movie : moviesList) {
            movieTableModel.addRow(new Object[]{movie.getTitle(), movie.getDuration(), movie.getRelease_date(), movie.getDescription(), movie.getPosterUrl()});
        }
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }
}