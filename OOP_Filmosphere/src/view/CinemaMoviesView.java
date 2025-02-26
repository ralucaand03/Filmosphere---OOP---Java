package view;

import controller.CinemaController;
import model.Colors;
import model.MoviesModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CinemaMoviesView {
    private JFrame frame = new JFrame("CinemaMovies");
    private JLabel cinemaLabel;
    private DefaultTableModel movieTableModel;
    private JTable movieTable;
    // Define colors
    // Accessing the colors
    Color mainColor = Colors.MAIN_COLOR.getColor();
    Color darkColor = Colors.DARK_COLOR.getColor();
    Color lightColor = Colors.LIGHT_COLOR.getColor();


    public CinemaMoviesView(String cinemaName, CinemaController controller) {
        // Create components
        JLabel fLabel = new JLabel("\uD835\uDD3D\uD835\uDD5A\uD835\uDD5D\uD835\uDD5E\uD835\uDD60\uD835\uDD64\uD835\uDD61\uD835\uDD59\uD835\uDD56\uD835\uDD63\uD835\uDD56");
        fLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fLabel.setFont(fLabel.getFont().deriveFont(Font.PLAIN, 20));
        fLabel.setForeground(lightColor);

        cinemaLabel = new JLabel( cinemaName + ": Movies list");
        cinemaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cinemaLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 25));
        cinemaLabel.setForeground(darkColor);

        // Create table model and table
        movieTableModel = new DefaultTableModel(new Object[]{"Title", "Duration", "Release Date", "Description", "Poster URL"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        movieTable = new JTable(movieTableModel);
        movieTable.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        movieTable.setForeground(darkColor);
        movieTable.getTableHeader().setFont(new Font(Font.MONOSPACED, Font.PLAIN, 16));
        movieTable.getTableHeader().setForeground(darkColor);

        // Populate the table with movies using the controller
        List<MoviesModel> moviesList = controller.getMoviesForCinema(cinemaName);
        for (MoviesModel movie : moviesList) {
            movieTableModel.addRow(new Object[]{movie.getTitle(), movie.getDuration(), movie.getRelease_date(), movie.getDescription(), movie.getPosterUrl()});
        }

        // Create layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(mainColor);
        mainPanel.add(fLabel, BorderLayout.NORTH);
        mainPanel.add(cinemaLabel, BorderLayout.CENTER);

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(movieTable), BorderLayout.CENTER);

        frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
        frame.getContentPane().add(tablePanel, BorderLayout.CENTER);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setTitle("Movies at " + cinemaName);
        frame.setVisible(true);
    }

    public void setVisible(boolean isVisible){
        frame.setVisible(isVisible);
    }

}
