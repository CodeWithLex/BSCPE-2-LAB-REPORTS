package finalproject_2ndyear;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkOrangeIJTheme;
import javax.swing.JDialog;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import com.formdev.flatlaf.intellijthemes.FlatGradiantoDeepOceanIJTheme;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

// NEW IMPORTS for Data Structures
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 *
 * @author User
 */
public class MainPage extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainPage.class.getName());
    
    // Table model for movie table
    private DefaultTableModel tableModel;
    
    // Home table components
    private DefaultTableModel homeTableModel;
    private javax.swing.JTable homeTable;
    
    // Current logged-in username
    private String currentUsername;
    
    // Colors for button highlighting
    private final Color ACTIVE_COLOR = new Color(0, 102, 204);
    private final Color INACTIVE_COLOR = new Color(0, 51, 102);
    
    // ==================== DATA STRUCTURES ====================
    
    // 1. ArrayList - Stores all movies for sorting and searching
    private ArrayList<String[]> movieArrayList = new ArrayList<>();
    
    // 2. LinkedList - Watch history (recently viewed movies)
    private LinkedList<String> watchHistoryList = new LinkedList<>();
    
    // 3. Stack - Undo functionality for deleted movies
    private Stack<String[]> undoStack = new Stack<>();
    
    // 4. Queue - Watch queue (movies to watch next)
    private Queue<String> watchQueue = new LinkedList<>();
    
    // 5. HashMap - Genre statistics and caching
    private HashMap<String, Integer> genreCountMap = new HashMap<>();
    private HashMap<String, String[]> movieCache = new HashMap<>();

    /**
     * Creates new form MainPage
     * @param username The logged-in username
     */
    public MainPage(String username) {
        this.currentUsername = username;
        initComponents();
        setupTable();
        loadMoviesFromDatabase();
        loadMoviesToArrayList(); // Load movies into ArrayList
        updateGenreHashMap();    // Update genre statistics
        setupFavoritesTab();
        setupStatisticsTab();
        setupWatchLaterTab();
        highlightButton(homeB);
    }
    
    // ==================== ARRAYLIST OPERATIONS ====================
    
    /**
     * Load all movies from database into ArrayList
     * Demonstrates: ArrayList usage
     */
    private void loadMoviesToArrayList() {
        movieArrayList.clear();
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "SELECT title, genre, rating, status, favorite FROM movie_details WHERE username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, currentUsername);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String[] movie = new String[5];
                movie[0] = rs.getString("title");
                movie[1] = rs.getString("genre");
                movie[2] = rs.getString("rating");
                movie[3] = rs.getString("status");
                movie[4] = rs.getString("favorite");
                movieArrayList.add(movie);
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // ==================== SORTING ALGORITHMS ====================
    
    /**
     * Bubble Sort - Sort movies by title (A-Z)
     * Demonstrates: Sorting Algorithm
     */
    private void bubbleSortByTitle(ArrayList<String[]> movies) {
        int n = movies.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (movies.get(j)[0].compareToIgnoreCase(movies.get(j + 1)[0]) > 0) {
                    // Swap
                    String[] temp = movies.get(j);
                    movies.set(j, movies.get(j + 1));
                    movies.set(j + 1, temp);
                }
            }
        }
    }
    
    /**
     * Selection Sort - Sort movies by rating (highest first)
     * Demonstrates: Sorting Algorithm
     */
    private void selectionSortByRating(ArrayList<String[]> movies) {
        int n = movies.size();
        for (int i = 0; i < n - 1; i++) {
            int maxIdx = i;
            for (int j = i + 1; j < n; j++) {
                int rating1 = Integer.parseInt(movies.get(j)[2]);
                int rating2 = Integer.parseInt(movies.get(maxIdx)[2]);
                if (rating1 > rating2) {
                    maxIdx = j;
                }
            }
            // Swap
            String[] temp = movies.get(maxIdx);
            movies.set(maxIdx, movies.get(i));
            movies.set(i, temp);
        }
    }
    
    /**
     * Insertion Sort - Sort movies by genre
     * Demonstrates: Sorting Algorithm
     */
    private void insertionSortByGenre(ArrayList<String[]> movies) {
        int n = movies.size();
        for (int i = 1; i < n; i++) {
            String[] key = movies.get(i);
            int j = i - 1;
            while (j >= 0 && movies.get(j)[1].compareToIgnoreCase(key[1]) > 0) {
                movies.set(j + 1, movies.get(j));
                j = j - 1;
            }
            movies.set(j + 1, key);
        }
    }
    
    /**
     * Refresh table with sorted ArrayList
     */
    private void refreshTableFromArrayList() {
        tableModel.setRowCount(0);
        for (String[] movie : movieArrayList) {
            tableModel.addRow(movie);
        }
    }
    
    // ==================== SEARCHING ALGORITHMS ====================
    
    /**
     * Linear Search - Find movie by title
     * Demonstrates: Searching Algorithm
     */
    private int linearSearchByTitle(String title) {
        for (int i = 0; i < movieArrayList.size(); i++) {
            if (movieArrayList.get(i)[0].equalsIgnoreCase(title)) {
                return i;
            }
        }
        return -1; // Not found
    }
    
    /**
     * Binary Search - Find movie in sorted list
     * Demonstrates: Searching Algorithm
     * Note: ArrayList must be sorted by title first
     */
    private int binarySearchByTitle(String title) {
        // First sort the list
        ArrayList<String[]> sortedList = new ArrayList<>(movieArrayList);
        bubbleSortByTitle(sortedList);
        
        int left = 0;
        int right = sortedList.size() - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = sortedList.get(mid)[0].compareToIgnoreCase(title);
            
            if (comparison == 0) {
                return mid; // Found
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1; // Not found
    }
    
    // ==================== LINKEDLIST OPERATIONS ====================
    
    /**
     * Add movie to watch history (LinkedList)
     * Demonstrates: LinkedList usage
     */
    private void addToWatchHistory(String title) {
        // Remove if already exists to avoid duplicates
        watchHistoryList.remove(title);
        // Add to the front (most recent)
        watchHistoryList.addFirst(title);
        // Keep only last 10 items
        while (watchHistoryList.size() > 10) {
            watchHistoryList.removeLast();
        }
    }
    
    /**
     * Get recent watch history
     */
    private LinkedList<String> getWatchHistory() {
        return new LinkedList<>(watchHistoryList);
    }
    
    /**
     * Show watch history dialog
     */
    private void showWatchHistoryDialog() {
        StringBuilder history = new StringBuilder("Recently Viewed Movies:\n\n");
        int count = 1;
        for (String title : watchHistoryList) {
            history.append(count++).append(". ").append(title).append("\n");
        }
        if (watchHistoryList.isEmpty()) {
            history.append("No watch history yet.");
        }
        JOptionPane.showMessageDialog(this, history.toString(), "Watch History (LinkedList)", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ==================== STACK OPERATIONS ====================
    
    /**
     * Push deleted movie to undo stack
     * Demonstrates: Stack usage
     */
    private void pushToUndoStack(String[] movie) {
        undoStack.push(movie);
    }
    
    /**
     * Undo last deletion
     * Demonstrates: Stack usage
     */
    private void undoLastDelete() {
        if (undoStack.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nothing to undo!");
            return;
        }
        
        String[] movie = undoStack.pop();
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "INSERT INTO movie_details (username, title, genre, rating, status, favorite) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, currentUsername);
            pst.setString(2, movie[0]);
            pst.setString(3, movie[1]);
            pst.setString(4, movie[2]);
            pst.setString(5, movie[3]);
            pst.setString(6, movie[4]);
            pst.executeUpdate();
            pst.close();
            
            JOptionPane.showMessageDialog(this, "Restored: " + movie[0]);
            loadMoviesFromDatabase();
            loadMoviesToArrayList();
            updateGenreHashMap();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error restoring movie: " + e.getMessage());
        }
    }
    
    // ==================== QUEUE OPERATIONS ====================
    
    /**
     * Add movie to watch queue
     * Demonstrates: Queue usage
     */
    private void addToWatchQueue(String title) {
        if (!watchQueue.contains(title)) {
            watchQueue.offer(title);
            JOptionPane.showMessageDialog(this, "'" + title + "' added to watch queue!");
        } else {
            JOptionPane.showMessageDialog(this, "'" + title + "' is already in the queue!");
        }
    }
    
    /**
     * Get next movie from queue
     * Demonstrates: Queue usage
     */
    private String getNextFromQueue() {
        return watchQueue.poll();
    }
    
    /**
     * Show watch queue dialog
     */
    private void showWatchQueueDialog() {
        JDialog queueDialog = new JDialog(this, "Watch Queue (Queue Data Structure)", true);
        queueDialog.setSize(400, 400);
        queueDialog.setLocationRelativeTo(this);
        queueDialog.setLayout(null);
        queueDialog.getContentPane().setBackground(new Color(6, 26, 43));
        
        JLabel titleLbl = new JLabel("Your Watch Queue:");
        titleLbl.setFont(new java.awt.Font("Poppins", 1, 16));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setBounds(20, 10, 200, 30);
        queueDialog.add(titleLbl);
        
        DefaultListModel<String> queueModel = new DefaultListModel<>();
        int pos = 1;
        for (String title : watchQueue) {
            queueModel.addElement(pos++ + ". " + title);
        }
        
        JList<String> queueList = new JList<>(queueModel);
        queueList.setBackground(new Color(20, 40, 60));
        queueList.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(queueList);
        scrollPane.setBounds(20, 50, 340, 200);
        queueDialog.add(scrollPane);
        
        JButton watchNextBtn = new JButton("Watch Next");
        watchNextBtn.setBounds(20, 270, 150, 35);
        watchNextBtn.addActionListener(e -> {
            String next = getNextFromQueue();
            if (next != null) {
                JOptionPane.showMessageDialog(queueDialog, "Now watching: " + next);
                addToWatchHistory(next);
                queueModel.clear();
                int p = 1;
                for (String t : watchQueue) {
                    queueModel.addElement(p++ + ". " + t);
                }
            } else {
                JOptionPane.showMessageDialog(queueDialog, "Queue is empty!");
            }
        });
        queueDialog.add(watchNextBtn);
        
        JButton clearQueueBtn = new JButton("Clear Queue");
        clearQueueBtn.setBounds(190, 270, 150, 35);
        clearQueueBtn.addActionListener(e -> {
            watchQueue.clear();
            queueModel.clear();
            JOptionPane.showMessageDialog(queueDialog, "Queue cleared!");
        });
        queueDialog.add(clearQueueBtn);
        
        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(100, 320, 180, 35);
        closeBtn.addActionListener(e -> queueDialog.dispose());
        queueDialog.add(closeBtn);
        
        queueDialog.setVisible(true);
    }
    
    // ==================== HASHMAP OPERATIONS ====================
    
    /**
     * Update genre count HashMap
     * Demonstrates: HashMap usage
     */
    private void updateGenreHashMap() {
        genreCountMap.clear();
        
        for (String[] movie : movieArrayList) {
            String genre = movie[1];
            genreCountMap.put(genre, genreCountMap.getOrDefault(genre, 0) + 1);
        }
    }
    
    /**
     * Cache movie details in HashMap
     * Demonstrates: HashMap for caching
     */
    private void cacheMovie(String title, String[] details) {
        movieCache.put(title, details);
    }
    
    /**
     * Get cached movie
     */
    private String[] getCachedMovie(String title) {
        return movieCache.get(title);
    }
    
    /**
     * Show genre statistics dialog using HashMap
     */
    private void showGenreStatisticsDialog() {
        updateGenreHashMap();
        
        StringBuilder stats = new StringBuilder("Genre Statistics (HashMap):\n\n");
        
        // Sort by count
        ArrayList<Map.Entry<String, Integer>> entries = new ArrayList<>(genreCountMap.entrySet());
        entries.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        
        for (Map.Entry<String, Integer> entry : entries) {
            stats.append(String.format("%-20s : %d movies\n", entry.getKey(), entry.getValue()));
        }
        
        if (genreCountMap.isEmpty()) {
            stats.append("No movies added yet.");
        }
        
        JOptionPane.showMessageDialog(this, stats.toString(), "Genre Statistics", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // ==================== BONUS: RECURSION ====================
    
    /**
     * Recursive search for movies containing keyword
     * Demonstrates: Recursion
     */
    private ArrayList<String[]> recursiveSearch(ArrayList<String[]> movies, String keyword, int index, ArrayList<String[]> results) {
        // Base case
        if (index >= movies.size()) {
            return results;
        }
        
        // Check if current movie contains keyword
        if (movies.get(index)[0].toLowerCase().contains(keyword.toLowerCase())) {
            results.add(movies.get(index));
        }
        
        // Recursive call
        return recursiveSearch(movies, keyword, index + 1, results);
    }
    
    /**
     * Calculate total ratings recursively
     * Demonstrates: Recursion
     */
    private int recursiveTotalRating(ArrayList<String[]> movies, int index) {
        // Base case
        if (index >= movies.size()) {
            return 0;
        }
        
        // Recursive case
        int currentRating = Integer.parseInt(movies.get(index)[2]);
        return currentRating + recursiveTotalRating(movies, index + 1);
    }
    
    /**
     * Show Data Structures Demo Dialog
     */
    private void showDataStructuresDemo() {
        JDialog demoDialog = new JDialog(this, "Data Structures & Algorithms", true);
        demoDialog.setSize(500, 550);
        demoDialog.setLocationRelativeTo(this);
        demoDialog.setLayout(null);
        demoDialog.getContentPane().setBackground(new Color(6, 26, 43));
        
        JLabel titleLbl = new JLabel("Data Structures Demo");
        titleLbl.setFont(new java.awt.Font("Poppins", 1, 20));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setBounds(130, 10, 300, 30);
        demoDialog.add(titleLbl);
        
        int y = 50;
        int btnHeight = 40;
        int gap = 10;
        
        // ArrayList & Sorting
        JButton sortTitleBtn = new JButton("Sort by Title (Bubble Sort - ArrayList)");
        sortTitleBtn.setBounds(50, y, 380, btnHeight);
        sortTitleBtn.addActionListener(e -> {
            loadMoviesToArrayList();
            bubbleSortByTitle(movieArrayList);
            refreshTableFromArrayList();
            JOptionPane.showMessageDialog(demoDialog, "Sorted by Title using Bubble Sort!");
        });
        demoDialog.add(sortTitleBtn);
        
        y += btnHeight + gap;
        JButton sortRatingBtn = new JButton("Sort by Rating (Selection Sort - ArrayList)");
        sortRatingBtn.setBounds(50, y, 380, btnHeight);
        sortRatingBtn.addActionListener(e -> {
            loadMoviesToArrayList();
            selectionSortByRating(movieArrayList);
            refreshTableFromArrayList();
            JOptionPane.showMessageDialog(demoDialog, "Sorted by Rating using Selection Sort!");
        });
        demoDialog.add(sortRatingBtn);
        
        y += btnHeight + gap;
        JButton sortGenreBtn = new JButton("Sort by Genre (Insertion Sort - ArrayList)");
        sortGenreBtn.setBounds(50, y, 380, btnHeight);
        sortGenreBtn.addActionListener(e -> {
            loadMoviesToArrayList();
            insertionSortByGenre(movieArrayList);
            refreshTableFromArrayList();
            JOptionPane.showMessageDialog(demoDialog, "Sorted by Genre using Insertion Sort!");
        });
        demoDialog.add(sortGenreBtn);
        
        // Searching
        y += btnHeight + gap;
        JButton linearSearchBtn = new JButton("Linear Search (Array)");
        linearSearchBtn.setBounds(50, y, 380, btnHeight);
        linearSearchBtn.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(demoDialog, "Enter movie title to search:");
            if (title != null && !title.isEmpty()) {
                int index = linearSearchByTitle(title);
                if (index != -1) {
                    createMoviesTable.setRowSelectionInterval(index, index);
                    JOptionPane.showMessageDialog(demoDialog, "Found at index: " + index);
                } else {
                    JOptionPane.showMessageDialog(demoDialog, "Movie not found!");
                }
            }
        });
        demoDialog.add(linearSearchBtn);
        
        y += btnHeight + gap;
        JButton binarySearchBtn = new JButton("Binary Search (Sorted Array)");
        binarySearchBtn.setBounds(50, y, 380, btnHeight);
        binarySearchBtn.addActionListener(e -> {
            String title = JOptionPane.showInputDialog(demoDialog, "Enter exact movie title:");
            if (title != null && !title.isEmpty()) {
                int index = binarySearchByTitle(title);
                if (index != -1) {
                    JOptionPane.showMessageDialog(demoDialog, "Found using Binary Search at sorted index: " + index);
                } else {
                    JOptionPane.showMessageDialog(demoDialog, "Movie not found!");
                }
            }
        });
        demoDialog.add(binarySearchBtn);
        
        // LinkedList
        y += btnHeight + gap;
        JButton historyBtn = new JButton("View Watch History (LinkedList)");
        historyBtn.setBounds(50, y, 380, btnHeight);
        historyBtn.addActionListener(e -> showWatchHistoryDialog());
        demoDialog.add(historyBtn);
        
        // Stack
        y += btnHeight + gap;
        JButton undoBtn = new JButton("Undo Last Delete (Stack)");
        undoBtn.setBounds(50, y, 380, btnHeight);
        undoBtn.addActionListener(e -> {
            undoLastDelete();
        });
        demoDialog.add(undoBtn);
        
        // Queue
        y += btnHeight + gap;
        JButton queueBtn = new JButton("Manage Watch Queue (Queue)");
        queueBtn.setBounds(50, y, 380, btnHeight);
        queueBtn.addActionListener(e -> showWatchQueueDialog());
        demoDialog.add(queueBtn);
        
        // HashMap
        y += btnHeight + gap;
        JButton hashMapBtn = new JButton("Genre Statistics (HashMap)");
        hashMapBtn.setBounds(50, y, 380, btnHeight);
        hashMapBtn.addActionListener(e -> showGenreStatisticsDialog());
        demoDialog.add(hashMapBtn);
        
        // Recursion
        y += btnHeight + gap;
        JButton recursionBtn = new JButton("Recursive Search (Bonus)");
        recursionBtn.setBounds(50, y, 380, btnHeight);
        recursionBtn.addActionListener(e -> {
            String keyword = JOptionPane.showInputDialog(demoDialog, "Enter keyword to search recursively:");
            if (keyword != null && !keyword.isEmpty()) {
                ArrayList<String[]> results = recursiveSearch(movieArrayList, keyword, 0, new ArrayList<>());
                StringBuilder sb = new StringBuilder("Found " + results.size() + " movies:\n\n");
                for (String[] movie : results) {
                    sb.append("- ").append(movie[0]).append("\n");
                }
                JOptionPane.showMessageDialog(demoDialog, sb.toString());
            }
        });
        demoDialog.add(recursionBtn);
        
        // Close
        y += btnHeight + gap + 10;
        JButton closeBtn = new JButton("Close");
        closeBtn.setBounds(150, y, 180, btnHeight);
        closeBtn.addActionListener(e -> demoDialog.dispose());
        demoDialog.add(closeBtn);
        
        demoDialog.setVisible(true);
    }
    
    private void showSearchDialog() {
        // Create custom dialog
        JDialog searchDialog = new JDialog(this, "Search Movies/Series", true);
        searchDialog.setSize(600, 500);
        searchDialog.setLocationRelativeTo(this);
        searchDialog.setLayout(null);
        searchDialog.getContentPane().setBackground(new Color(6, 26, 43));
        
        // Search field
        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setFont(new java.awt.Font("Poppins", 0, 14));
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBounds(20, 20, 80, 30);
        searchDialog.add(searchLabel);
        
        JTextField searchField = new JTextField();
        searchField.setBounds(100, 20, 350, 35);
        searchDialog.add(searchField);
        
        JButton searchBtn = new JButton("Search");
        searchBtn.setFont(new java.awt.Font("Poppins", 0, 12));
        searchBtn.setBounds(460, 20, 100, 35);
        searchDialog.add(searchBtn);
        
        // Results list
        JLabel resultsLabel = new JLabel("Results:");
        resultsLabel.setFont(new java.awt.Font("Poppins", 0, 12));
        resultsLabel.setForeground(Color.WHITE);
        resultsLabel.setBounds(20, 65, 80, 25);
        searchDialog.add(resultsLabel);
        
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> resultsList = new JList<>(listModel);
        resultsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        resultsList.setBackground(new Color(20, 40, 60));
        resultsList.setForeground(Color.WHITE);
        JScrollPane listScroll = new JScrollPane(resultsList);
        listScroll.setBounds(20, 90, 540, 120);
        searchDialog.add(listScroll);
        
        // Edit section
        JLabel editLabel = new JLabel("Edit Selected Movie:");
        editLabel.setFont(new java.awt.Font("Poppins", 1, 14));
        editLabel.setForeground(Color.WHITE);
        editLabel.setBounds(20, 220, 200, 25);
        searchDialog.add(editLabel);
        
        // Title
        JLabel titleLabel = new JLabel("Title:");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(20, 255, 80, 25);
        searchDialog.add(titleLabel);
        
        JTextField editTitleField = new JTextField();
        editTitleField.setBounds(100, 255, 200, 30);
        searchDialog.add(editTitleField);
        
        // Genre
        JLabel genreLabel = new JLabel("Genre:");
        genreLabel.setForeground(Color.WHITE);
        genreLabel.setBounds(320, 255, 80, 25);
        searchDialog.add(genreLabel);
        
        javax.swing.JComboBox<String> editGenreBox = new javax.swing.JComboBox<>(new String[]{
            "Action/Thriller", "Action/Adventure", "Action/Sci-Fi", "Adventure/Fantasy",
            "Animation/Family", "Comedy", "Comedy/Romance", "Crime/Drama", "Documentary",
            "Drama", "Fantasy", "Horror", "Mystery/Thriller", "Romance", "Sci-Fi", "War", "Western"
        });
        editGenreBox.setBounds(380, 255, 150, 30);
        searchDialog.add(editGenreBox);
        
        // Rating
        JLabel ratingLabel = new JLabel("Rating:");
        ratingLabel.setForeground(Color.WHITE);
        ratingLabel.setBounds(20, 295, 80, 25);
        searchDialog.add(ratingLabel);
        
        javax.swing.JComboBox<String> editRatingBox = new javax.swing.JComboBox<>(new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"
        });
        editRatingBox.setBounds(100, 295, 80, 30);
        searchDialog.add(editRatingBox);
        
        // Status
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setBounds(200, 295, 80, 25);
        searchDialog.add(statusLabel);
        
        javax.swing.JComboBox<String> editStatusBox = new javax.swing.JComboBox<>(new String[]{
            "Watched", "Currently Watching", "Watch Later"
        });
        editStatusBox.setBounds(260, 295, 150, 30);
        searchDialog.add(editStatusBox);
        
        // Favorite
        JLabel favLabel = new JLabel("Favorite:");
        favLabel.setForeground(Color.WHITE);
        favLabel.setBounds(430, 295, 80, 25);
        searchDialog.add(favLabel);
        
        javax.swing.JComboBox<String> editFavBox = new javax.swing.JComboBox<>(new String[]{"Yes", "No"});
        editFavBox.setBounds(490, 295, 70, 30);
        searchDialog.add(editFavBox);
        
        // Buttons
        JButton saveBtn = new JButton("Save Changes");
        saveBtn.setFont(new java.awt.Font("Poppins", 0, 14));
        saveBtn.setBounds(100, 350, 140, 40);
        saveBtn.setEnabled(false);
        searchDialog.add(saveBtn);
        
        JButton addToQueueBtn = new JButton("Add to Queue");
        addToQueueBtn.setFont(new java.awt.Font("Poppins", 0, 14));
        addToQueueBtn.setBounds(250, 350, 140, 40);
        addToQueueBtn.setEnabled(false);
        searchDialog.add(addToQueueBtn);
        
        JButton cancelBtn = new JButton("Close");
        cancelBtn.setFont(new java.awt.Font("Poppins", 0, 14));
        cancelBtn.setBounds(400, 350, 140, 40);
        searchDialog.add(cancelBtn);
        
        // Store original title for update
        final String[] originalTitle = {""};
        
        // Search button action - Uses Linear Search
        searchBtn.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            listModel.clear();
            
            if (searchText.isEmpty()) {
                JOptionPane.showMessageDialog(searchDialog, "Please enter a search term!");
                return;
            }
            
            // Use recursive search from ArrayList
            ArrayList<String[]> results = recursiveSearch(movieArrayList, searchText, 0, new ArrayList<>());
            
            for (String[] movie : results) {
                String movieInfo = movie[0] + " | " + movie[1] + " | ★" + movie[2] + " | " + movie[3];
                listModel.addElement(movieInfo);
            }
            
            if (listModel.isEmpty()) {
                JOptionPane.showMessageDialog(searchDialog, "No movies found matching: " + searchText);
            }
        });
        
        // List selection listener
        resultsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && resultsList.getSelectedValue() != null) {
                String selected = resultsList.getSelectedValue();
                String title = selected.split(" \\| ")[0];
                originalTitle[0] = title;
                
                // Add to watch history (LinkedList)
                addToWatchHistory(title);
                
                // Check cache first (HashMap)
                String[] cached = getCachedMovie(title);
                if (cached != null) {
                    editTitleField.setText(cached[0]);
                    editGenreBox.setSelectedItem(cached[1]);
                    editRatingBox.setSelectedItem(cached[2]);
                    editStatusBox.setSelectedItem(cached[3]);
                    editFavBox.setSelectedItem(cached[4]);
                    saveBtn.setEnabled(true);
                    addToQueueBtn.setEnabled(true);
                    return;
                }
                
                // Load from database and cache
                try {
                    Connection con = Connector_MyMovieSeriesApp.getConnection();
                    String sql = "SELECT title, genre, rating, status, favorite FROM movie_details " +
                                 "WHERE username = ? AND title = ?";
                    PreparedStatement pst = con.prepareStatement(sql);
                    pst.setString(1, currentUsername);
                    pst.setString(2, title);
                    ResultSet rs = pst.executeQuery();
                    
                    if (rs.next()) {
                        String[] details = new String[5];
                        details[0] = rs.getString("title");
                        details[1] = rs.getString("genre");
                        details[2] = rs.getString("rating");
                        details[3] = rs.getString("status");
                        details[4] = rs.getString("favorite");
                        
                        // Cache it
                        cacheMovie(title, details);
                        
                        editTitleField.setText(details[0]);
                        editGenreBox.setSelectedItem(details[1]);
                        editRatingBox.setSelectedItem(details[2]);
                        editStatusBox.setSelectedItem(details[3]);
                        editFavBox.setSelectedItem(details[4]);
                        saveBtn.setEnabled(true);
                        addToQueueBtn.setEnabled(true);
                    }
                    rs.close();
                    pst.close();
                    
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        
        // Add to Queue button
        addToQueueBtn.addActionListener(e -> {
            String title = editTitleField.getText().trim();
            if (!title.isEmpty()) {
                addToWatchQueue(title);
            }
        });
        
        // Save button action
        saveBtn.addActionListener(e -> {
            String newTitle = editTitleField.getText().trim();
            
            if (newTitle.isEmpty()) {
                JOptionPane.showMessageDialog(searchDialog, "Title cannot be empty!");
                return;
            }
            
            try {
                Connection con = Connector_MyMovieSeriesApp.getConnection();
                String sql = "UPDATE movie_details SET title = ?, genre = ?, rating = ?, status = ?, favorite = ? " +
                             "WHERE username = ? AND title = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, newTitle);
                pst.setString(2, editGenreBox.getSelectedItem().toString());
                pst.setString(3, editRatingBox.getSelectedItem().toString());
                pst.setString(4, editStatusBox.getSelectedItem().toString());
                pst.setString(5, editFavBox.getSelectedItem().toString());
                pst.setString(6, currentUsername);
                pst.setString(7, originalTitle[0]);
                pst.executeUpdate();
                pst.close();
                
                // Update cache
                movieCache.remove(originalTitle[0]);
                
                // Also update extra_details if title changed
                if (!newTitle.equals(originalTitle[0])) {
                    String updateExtra = "UPDATE extra_details SET title = ? WHERE username = ? AND title = ?";
                    PreparedStatement pstExtra = con.prepareStatement(updateExtra);
                    pstExtra.setString(1, newTitle);
                    pstExtra.setString(2, currentUsername);
                    pstExtra.setString(3, originalTitle[0]);
                    pstExtra.executeUpdate();
                    pstExtra.close();
                }
                
                JOptionPane.showMessageDialog(searchDialog, "Movie updated successfully!");
                loadMoviesFromDatabase();
                loadMoviesToArrayList();
                updateGenreHashMap();
                
                // Clear and refresh search results
                listModel.clear();
                editTitleField.setText("");
                saveBtn.setEnabled(false);
                addToQueueBtn.setEnabled(false);
                originalTitle[0] = newTitle;
                
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(searchDialog, "Error updating: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        // Cancel button action
        cancelBtn.addActionListener(e -> searchDialog.dispose());
        
        // Enter key to search
        searchField.addActionListener(e -> searchBtn.doClick());
        
        searchDialog.setVisible(true);
    }

    private void setupFavoritesTab() {
        favorites.removeAll();
        favorites.setLayout(new java.awt.BorderLayout());
        favorites.setBackground(new Color(6, 26, 43));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(6, 26, 43));
        titlePanel.setPreferredSize(new Dimension(800, 60));
        JLabel titleLbl = new JLabel("MY FAVORITES");
        titleLbl.setFont(new java.awt.Font("Poppins", 1, 28));
        titleLbl.setForeground(Color.WHITE);
        titlePanel.add(titleLbl);
        favorites.add(titlePanel, java.awt.BorderLayout.NORTH);
        
        // Content panel for cards
        JPanel contentPanel = new JPanel(null);
        contentPanel.setBackground(new Color(6, 26, 43));
        
        int x = 30, y = 10, cardWidth = 160, cardHeight = 260, gap = 20, count = 0;
        int maxY = y;
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            if (con != null) {
                String sql = "SELECT m.title, m.genre, m.rating, e.poster_path " +
                             "FROM movie_details m LEFT JOIN extra_details e " +
                             "ON m.username = e.username AND m.title = e.title " +
                             "WHERE m.username = ? AND m.favorite = 'Yes'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, currentUsername);
                ResultSet rs = pst.executeQuery();
                
                while (rs.next()) {
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    String rating = rs.getString("rating");
                    String posterPath = rs.getString("poster_path");
                    
                    JPanel card = new JPanel(null);
                    card.setBounds(x, y, cardWidth, cardHeight);
                    card.setBackground(new Color(20, 40, 60));
                    
                    JLabel poster = new JLabel();
                    poster.setBounds(5, 5, 150, 180);
                    poster.setOpaque(true);
                    poster.setBackground(new Color(40, 60, 80));
                    poster.setHorizontalAlignment(SwingConstants.CENTER);
                    if (posterPath != null && !posterPath.isEmpty()) {
                        File f = new File(posterPath);
                        if (f.exists()) {
                            ImageIcon icon = new ImageIcon(posterPath);
                            Image img = icon.getImage().getScaledInstance(150, 180, Image.SCALE_SMOOTH);
                            poster.setIcon(new ImageIcon(img));
                        } else { poster.setText("No Poster"); poster.setForeground(Color.GRAY); }
                    } else { poster.setText("No Poster"); poster.setForeground(Color.GRAY); }
                    card.add(poster);
                    
                    JLabel tLbl = new JLabel("<html>" + title + "</html>");
                    tLbl.setBounds(5, 190, 150, 30);
                    tLbl.setFont(new java.awt.Font("Poppins", 1, 11));
                    tLbl.setForeground(Color.WHITE);
                    card.add(tLbl);
                    
                    JLabel gLbl = new JLabel(genre);
                    gLbl.setBounds(5, 215, 150, 20);
                    gLbl.setFont(new java.awt.Font("Poppins", 0, 10));
                    gLbl.setForeground(Color.LIGHT_GRAY);
                    card.add(gLbl);
                    
                    JLabel rLbl = new JLabel("Rating: " + rating);
                    rLbl.setBounds(5, 235, 150, 20);
                    rLbl.setFont(new java.awt.Font("Poppins", 0, 10));
                    rLbl.setForeground(new Color(255, 200, 0));
                    card.add(rLbl);
                    
                    contentPanel.add(card);
                    count++;
                    x += cardWidth + gap;
                    if (x > 650) { x = 30; y += cardHeight + gap; }
                    maxY = Math.max(maxY, y + cardHeight + gap);
                }
                rs.close(); pst.close();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        if (count == 0) {
            JLabel noLbl = new JLabel("                                                  No favorites yet!");
            noLbl.setBounds(250, 150, 400, 30);
            noLbl.setFont(new java.awt.Font("Poppins", 0, 16));
            noLbl.setForeground(Color.LIGHT_GRAY);
            contentPanel.add(noLbl);
            maxY = 300;
        }
        
        contentPanel.setPreferredSize(new Dimension(750, maxY));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(new Color(6, 26, 43));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        favorites.add(scrollPane, java.awt.BorderLayout.CENTER);
        
        favorites.revalidate();
        favorites.repaint();
    }

   
    private void highlightButton(JButton activeButton) {
        // Array of all sidebar buttons
        JButton[] sidebarButtons = {homeB, favoritesB, watchLaterB, statB};
        
        for (JButton btn : sidebarButtons) {
            if (btn == activeButton) {
                btn.setBackground(ACTIVE_COLOR);
            } else {
                btn.setBackground(INACTIVE_COLOR);
            }
        }
    }
    
    
    /**
     * Setup Statistics tab with summary data
     */
    private void setupStatisticsTab() {
        statistics.removeAll();
        statistics.setLayout(null);
        statistics.setBackground(new Color(6, 26, 43));
        
        JLabel titleLbl = new JLabel("MY STATISTICS");
        titleLbl.setFont(new java.awt.Font("Poppins", 1, 28));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setBounds(320, 20, 300, 40);
        statistics.add(titleLbl);
        
        int totalMovies = 0, totalFavorites = 0, totalWatched = 0, totalWatching = 0, totalPlanToWatch = 0;
        String topGenre = "N/A", topRatedMovie = "N/A", topRating = "N/A";
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            if (con != null) {
                PreparedStatement pst1 = con.prepareStatement("SELECT COUNT(*) as total FROM movie_details WHERE username = ?");
                pst1.setString(1, currentUsername);
                ResultSet rs1 = pst1.executeQuery();
                if (rs1.next()) totalMovies = rs1.getInt("total");
                rs1.close(); pst1.close();
                
                PreparedStatement pst2 = con.prepareStatement("SELECT COUNT(*) as total FROM movie_details WHERE username = ? AND favorite = 'Yes'");
                pst2.setString(1, currentUsername);
                ResultSet rs2 = pst2.executeQuery();
                if (rs2.next()) totalFavorites = rs2.getInt("total");
                rs2.close(); pst2.close();
                
                PreparedStatement pst3 = con.prepareStatement("SELECT status, COUNT(*) as cnt FROM movie_details WHERE username = ? GROUP BY status");
                pst3.setString(1, currentUsername);
                ResultSet rs3 = pst3.executeQuery();
                while (rs3.next()) {
                    String status = rs3.getString("status");
                    int cnt = rs3.getInt("cnt");
                    if ("Watched".equals(status)) totalWatched = cnt;
                    else if ("Currently Watching".equals(status)) totalWatching = cnt;
                    else if ("Watch Later".equals(status)) totalPlanToWatch = cnt;
                }
                rs3.close(); pst3.close();
                
                PreparedStatement pst4 = con.prepareStatement("SELECT genre, COUNT(*) as cnt FROM movie_details WHERE username = ? GROUP BY genre ORDER BY cnt DESC LIMIT 1");
                pst4.setString(1, currentUsername);
                ResultSet rs4 = pst4.executeQuery();
                if (rs4.next()) topGenre = rs4.getString("genre");
                rs4.close(); pst4.close();
                
                PreparedStatement pst5 = con.prepareStatement("SELECT title, rating FROM movie_details WHERE username = ? ORDER BY rating DESC LIMIT 1");
                pst5.setString(1, currentUsername);
                ResultSet rs5 = pst5.executeQuery();
                if (rs5.next()) { topRatedMovie = rs5.getString("title"); topRating = rs5.getString("rating"); }
                rs5.close(); pst5.close();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        int cardX = 80, cardY = 100, cardW = 200, cardH = 100, gap = 30;
        addStatCard(cardX, cardY, cardW, cardH, "Total Movies", String.valueOf(totalMovies), new Color(41, 128, 185));
        addStatCard(cardX + cardW + gap, cardY, cardW, cardH, "Favorites", String.valueOf(totalFavorites), new Color(231, 76, 60));
        addStatCard(cardX + (cardW + gap) * 2, cardY, cardW, cardH, "Watched", String.valueOf(totalWatched), new Color(39, 174, 96));
        addStatCard(cardX, cardY + cardH + gap, cardW, cardH, "Watching", String.valueOf(totalWatching), new Color(243, 156, 18));
        addStatCard(cardX + cardW + gap, cardY + cardH + gap, cardW, cardH, "Watch Later", String.valueOf(totalPlanToWatch), new Color(155, 89, 182));
        addStatCard(cardX + (cardW + gap) * 2, cardY + cardH + gap, cardW, cardH, "Top Genre", topGenre, new Color(52, 73, 94));
        
        JLabel topLabel = new JLabel("Recent Rating: " + topRatedMovie + " (" + topRating + ")");
        topLabel.setFont(new java.awt.Font("Poppins", 1, 16));
        topLabel.setForeground(new Color(255, 200, 0));
        topLabel.setBounds(cardX, cardY + (cardH + gap) * 2 + 20, 600, 30);
        statistics.add(topLabel);
        
        // Add Data Structures Demo Button
        JButton demoBtn = new JButton("MORE OPTIONS");
        demoBtn.setFont(new java.awt.Font("Poppins", 1, 14));
        demoBtn.setBounds(cardX + 200, cardY + (cardH + gap) * 2 + 60, 250, 40);
        demoBtn.addActionListener(e -> showDataStructuresDemo());
        statistics.add(demoBtn);
        
        statistics.revalidate();
        statistics.repaint();
    }
    
    private void addStatCard(int x, int y, int w, int h, String label, String value, Color bgColor) {
        JPanel card = new JPanel(null);
        card.setBounds(x, y, w, h);
        card.setBackground(bgColor);
        JLabel valueLbl = new JLabel(value);
        valueLbl.setFont(new java.awt.Font("Poppins", 1, 32));
        valueLbl.setForeground(Color.WHITE);
        valueLbl.setBounds(10, 10, w - 20, 40);
        card.add(valueLbl);
        JLabel labelLbl = new JLabel(label);
        labelLbl.setFont(new java.awt.Font("Poppins", 0, 14));
        labelLbl.setForeground(Color.WHITE);
        labelLbl.setBounds(10, 55, w - 20, 25);
        card.add(labelLbl);
        statistics.add(card);
    }

    /**
     * Setup Watch Later tab
     */
    private void setupWatchLaterTab() {
        watchLater.removeAll();
        watchLater.setLayout(null);
        watchLater.setBackground(new Color(6, 26, 43));
        
        JLabel titleLbl = new JLabel("             WATCH LATER");
        titleLbl.setFont(new java.awt.Font("Poppins", 1, 28));
        titleLbl.setForeground(Color.WHITE);
        titleLbl.setBounds(330, 20, 300, 40);
        watchLater.add(titleLbl);
        
        int x = 30, y = 80, cardWidth = 160, cardHeight = 260, gap = 20, count = 0;
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            if (con != null) {
                String sql = "SELECT m.title, m.genre, m.rating, e.poster_path " +
                             "FROM movie_details m LEFT JOIN extra_details e " +
                             "ON m.username = e.username AND m.title = e.title " +
                             "WHERE m.username = ? AND m.status = 'Watch Later'";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, currentUsername);
                ResultSet rs = pst.executeQuery();
                
                while (rs.next()) {
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    String rating = rs.getString("rating");
                    String posterPath = rs.getString("poster_path");
                    
                    JPanel card = new JPanel(null);
                    card.setBounds(x, y, cardWidth, cardHeight);
                    card.setBackground(new Color(20, 40, 60));
                    
                    JLabel poster = new JLabel();
                    poster.setBounds(5, 5, 150, 180);
                    poster.setOpaque(true);
                    poster.setBackground(new Color(40, 60, 80));
                    poster.setHorizontalAlignment(SwingConstants.CENTER);
                    if (posterPath != null && !posterPath.isEmpty()) {
                        File f = new File(posterPath);
                        if (f.exists()) {
                            ImageIcon icon = new ImageIcon(posterPath);
                            Image img = icon.getImage().getScaledInstance(150, 180, Image.SCALE_SMOOTH);
                            poster.setIcon(new ImageIcon(img));
                        } else { poster.setText("No Poster"); poster.setForeground(Color.GRAY); }
                    } else { poster.setText("No Poster"); poster.setForeground(Color.GRAY); }
                    card.add(poster);
                    
                    JLabel tLbl = new JLabel("<html>" + title + "</html>");
                    tLbl.setBounds(5, 190, 150, 30);
                    tLbl.setFont(new java.awt.Font("Poppins", 1, 11));
                    tLbl.setForeground(Color.WHITE);
                    card.add(tLbl);
                    
                    JLabel gLbl = new JLabel(genre);
                    gLbl.setBounds(5, 215, 150, 20);
                    gLbl.setFont(new java.awt.Font("Poppins", 0, 10));
                    gLbl.setForeground(Color.LIGHT_GRAY);
                    card.add(gLbl);
                    
                    JLabel rLbl = new JLabel("Rating: " + rating);
                    rLbl.setBounds(5, 235, 150, 20);
                    rLbl.setFont(new java.awt.Font("Poppins", 0, 10));
                    rLbl.setForeground(new Color(255, 200, 0));
                    card.add(rLbl);
                    
                    watchLater.add(card);
                    count++;
                    x += cardWidth + gap;
                    if (x > 700) { x = 30; y += cardHeight + gap; }
                }
                rs.close(); pst.close();
            }
        } catch (SQLException e) { e.printStackTrace(); }
        
        if (count == 0) {
            JLabel noLbl = new JLabel("                                    No movies in Watch Later!");
            noLbl.setBounds(280, 250, 400, 30);
            noLbl.setFont(new java.awt.Font("Poppins", 0, 16));
            noLbl.setForeground(Color.LIGHT_GRAY);
            watchLater.add(noLbl);
        }
        
        watchLater.revalidate();
        watchLater.repaint();
    }

    /**
     * Setup table model
     */
    private void setupTable() {
        tableModel = new DefaultTableModel(
            new String[]{"Title", "Genre", "Rating", "Status", "Favorite"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        createMoviesTable.setModel(tableModel);
        
        // Add selection listener to populate fields when row is clicked
        createMoviesTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && createMoviesTable.getSelectedRow() != -1) {
                int row = createMoviesTable.getSelectedRow();
                String title = tableModel.getValueAt(row, 0).toString();
                titleFiel.setText(title);
                genreBox.setSelectedItem(tableModel.getValueAt(row, 1).toString());
                ratingBox.setSelectedItem(tableModel.getValueAt(row, 2).toString());
                statusBox.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                favoritesBox.setSelectedItem(tableModel.getValueAt(row, 4).toString());
                
                // Add to watch history (LinkedList)
                addToWatchHistory(title);
            }
        });
    }
    
    
    /**
     * Load movies into home table
     */
    private void loadHomeTable() {
        homeTableModel.setRowCount(0);
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "SELECT title, genre, rating, status, favorite FROM movie_details WHERE username = ? ORDER BY title";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, currentUsername);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                homeTableModel.addRow(new Object[]{
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("rating"),
                    rs.getString("status"),
                    rs.getString("favorite")
                });
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Load movies from database into table
     */
    private void loadMoviesFromDatabase() {
        tableModel.setRowCount(0);
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "SELECT title, genre, rating, status, favorite FROM movie_details WHERE username = ? ORDER BY title";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, currentUsername);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("title"),
                    rs.getString("genre"),
                    rs.getString("rating"),
                    rs.getString("status"),
                    rs.getString("favorite")
                });
            }
            rs.close();
            pst.close();
            
        } catch (SQLException e) {
            createMoviesTableInDB();
        }
    }
    
    /**
     * Create movies table if it doesn't exist
     */
    private void createMoviesTableInDB() {
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "CREATE TABLE IF NOT EXISTS movie_details (" +
                         "id INT AUTO_INCREMENT PRIMARY KEY, " +
                         "username VARCHAR(100) NOT NULL, " +
                         "title VARCHAR(255) NOT NULL, " +
                         "genre VARCHAR(100), " +
                         "rating VARCHAR(10), " +
                         "status VARCHAR(50), " +
                         "favorite VARCHAR(10))";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Add movie to database
     */
    private void addMovie() {
        String title = titleFiel.getText().trim();
        String genre = genreBox.getSelectedItem().toString();
        String rating = ratingBox.getSelectedItem().toString();
        String status = statusBox.getSelectedItem().toString();
        String favorite = favoritesBox.getSelectedItem().toString();
        
        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a title!");
            return;
        }
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            
            // Check if movie already exists
            String checkSql = "SELECT COUNT(*) FROM movie_details WHERE username = ? AND title = ?";
            PreparedStatement checkPst = con.prepareStatement(checkSql);
            checkPst.setString(1, currentUsername);
            checkPst.setString(2, title);
            ResultSet rs = checkPst.executeQuery();
            
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(this, "Movie/Series already exists!");
                rs.close();
                checkPst.close();
                return;
            }
            rs.close();
            checkPst.close();
            
            // Insert new movie
            String sql = "INSERT INTO movie_details (username, title, genre, rating, status, favorite) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, currentUsername);
            pst.setString(2, title);
            pst.setString(3, genre);
            pst.setString(4, rating);
            pst.setString(5, status);
            pst.setString(6, favorite);
            pst.executeUpdate();
            pst.close();
            
            JOptionPane.showMessageDialog(this, "Movie/Series added successfully!");
            clearFields();
            loadMoviesFromDatabase();
            loadMoviesToArrayList();
            updateGenreHashMap();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding movie: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Edit selected movie in database
     */
    private void editMovie() {
        int selectedRow = createMoviesTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to edit!");
            return;
        }
        
        String oldTitle = tableModel.getValueAt(selectedRow, 0).toString();
        String newTitle = titleFiel.getText().trim();
        String genre = genreBox.getSelectedItem().toString();
        String rating = ratingBox.getSelectedItem().toString();
        String status = statusBox.getSelectedItem().toString();
        String favorite = favoritesBox.getSelectedItem().toString();
        
        if (newTitle.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a title!");
            return;
        }
        
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "UPDATE movie_details SET title = ?, genre = ?, rating = ?, status = ?, favorite = ? WHERE username = ? AND title = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, newTitle);
            pst.setString(2, genre);
            pst.setString(3, rating);
            pst.setString(4, status);
            pst.setString(5, favorite);
            pst.setString(6, currentUsername);
            pst.setString(7, oldTitle);
            pst.executeUpdate();
            pst.close();
            
            // Clear cache for updated movie
            movieCache.remove(oldTitle);
            
            JOptionPane.showMessageDialog(this, "Movie/Series updated successfully!");
            clearFields();
            loadMoviesFromDatabase();
            loadMoviesToArrayList();
            updateGenreHashMap();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating movie: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Remove selected movie from database
     * Modified to use Stack for undo functionality
     */
    private void removeMovie() {
        int selectedRow = createMoviesTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie to remove!");
            return;
        }
        
        String title = tableModel.getValueAt(selectedRow, 0).toString();
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to remove \"" + title + "\"?\n(You can undo this in Statistics > Data Structures Demo)",
            "Confirm Removal",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                // Save to undo stack before deleting (Stack usage)
                String[] movieData = new String[5];
                movieData[0] = tableModel.getValueAt(selectedRow, 0).toString();
                movieData[1] = tableModel.getValueAt(selectedRow, 1).toString();
                movieData[2] = tableModel.getValueAt(selectedRow, 2).toString();
                movieData[3] = tableModel.getValueAt(selectedRow, 3).toString();
                movieData[4] = tableModel.getValueAt(selectedRow, 4).toString();
                pushToUndoStack(movieData);
                
                Connection con = Connector_MyMovieSeriesApp.getConnection();
                String sql = "DELETE FROM movie_details WHERE username = ? AND title = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, currentUsername);
                pst.setString(2, title);
                pst.executeUpdate();
                pst.close();
                
                // Clear from cache
                movieCache.remove(title);
                
                JOptionPane.showMessageDialog(this, "Movie/Series removed successfully!\n(Press 'Undo' in Data Structures Demo to restore)");
                clearFields();
                loadMoviesFromDatabase();
                loadMoviesToArrayList();
                updateGenreHashMap();
                
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error removing movie: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Clear all input fields
     */
    private void clearFields() {
        titleFiel.setText("");
        genreBox.setSelectedIndex(0);
        ratingBox.setSelectedIndex(0);
        statusBox.setSelectedIndex(0);
        favoritesBox.setSelectedIndex(0);
        createMoviesTable.clearSelection();
    }
    
    /**
     * Show details dialog for the selected movie
     */
    private void showDetailsDialog() {
        int selectedRow = createMoviesTable.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a movie first!");
            return;
        }
        
        String movieTitle = tableModel.getValueAt(selectedRow, 0).toString();
        
        // Create dialog panel
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Plot/Description
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Plot/Description:"), gbc);
        
        JTextArea plotArea = new JTextArea(5, 30);
        plotArea.setLineWrap(true);
        plotArea.setWrapStyleWord(true);
        JScrollPane plotScroll = new JScrollPane(plotArea);
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(plotScroll, gbc);
        
        // Release Date
        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Release Date:"), gbc);
        
        JTextField releaseDateField = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(releaseDateField, gbc);
        
        // Poster Path
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Poster Path:"), gbc);
        
        JTextField posterPathField = new JTextField(20);
        posterPathField.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(posterPathField, gbc);
        
        // Browse Button
        JButton browseBtn = new JButton("Browse...");
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        panel.add(browseBtn, gbc);
        
        // Poster Preview
        JLabel posterPreview = new JLabel();
        posterPreview.setPreferredSize(new Dimension(150, 200));
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(posterPreview, gbc);
        
        // Browse button action
        browseBtn.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                posterPathField.setText(selectedFile.getAbsolutePath());
                ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                java.awt.Image img = icon.getImage().getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH);
                posterPreview.setIcon(new ImageIcon(img));
            }
        });
        
        // Load existing data if available
        loadExtraDetails(movieTitle, plotArea, releaseDateField, posterPathField, posterPreview);
        
        // Show dialog
        int option = JOptionPane.showConfirmDialog(this, panel, 
            "Details for: " + movieTitle, 
            JOptionPane.OK_CANCEL_OPTION, 
            JOptionPane.PLAIN_MESSAGE);
        
        if (option == JOptionPane.OK_OPTION) {
            saveExtraDetails(movieTitle, plotArea.getText(), 
                releaseDateField.getText(), posterPathField.getText());
        }
    }
    
    /**
     * Load extra details from database
     */
    private void loadExtraDetails(String title, JTextArea plotArea, 
            JTextField releaseDateField, JTextField posterPathField, JLabel posterPreview) {
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            String sql = "SELECT plot, release_date, poster_path FROM extra_details WHERE username = ? AND title = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, currentUsername);
            pst.setString(2, title);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String plot = rs.getString("plot");
                String releaseDate = rs.getString("release_date");
                String posterPath = rs.getString("poster_path");
                
                if (plot != null) plotArea.setText(plot);
                if (releaseDate != null) releaseDateField.setText(releaseDate);
                if (posterPath != null && !posterPath.isEmpty()) {
                    posterPathField.setText(posterPath);
                    File posterFile = new File(posterPath);
                    if (posterFile.exists()) {
                        ImageIcon icon = new ImageIcon(posterPath);
                        java.awt.Image img = icon.getImage().getScaledInstance(150, 200, java.awt.Image.SCALE_SMOOTH);
                        posterPreview.setIcon(new ImageIcon(img));
                    }
                }
            }
            rs.close();
            pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Save extra details to database
     */
    private void saveExtraDetails(String title, String plot, String releaseDate, String posterPath) {
        try {
            Connection con = Connector_MyMovieSeriesApp.getConnection();
            
            String checkSql = "SELECT COUNT(*) FROM extra_details WHERE username = ? AND title = ?";
            PreparedStatement checkPst = con.prepareStatement(checkSql);
            checkPst.setString(1, currentUsername);
            checkPst.setString(2, title);
            ResultSet rs = checkPst.executeQuery();
            
            boolean exists = rs.next() && rs.getInt(1) > 0;
            rs.close();
            checkPst.close();
            
            if (exists) {
                String sql = "UPDATE extra_details SET plot = ?, release_date = ?, poster_path = ? WHERE username = ? AND title = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, plot);
                pst.setString(2, releaseDate);
                pst.setString(3, posterPath);
                pst.setString(4, currentUsername);
                pst.setString(5, title);
                pst.executeUpdate();
                pst.close();
            } else {
                String sql = "INSERT INTO extra_details (username, title, plot, release_date, poster_path) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, currentUsername);
                pst.setString(2, title);
                pst.setString(3, plot);
                pst.setString(4, releaseDate);
                pst.setString(5, posterPath);
                pst.executeUpdate();
                pst.close();
            }
            
            JOptionPane.showMessageDialog(this, "Details saved successfully!");
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving details: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        tabPane = new javax.swing.JTabbedPane();
        movies = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        titleFiel = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        genreBox = new javax.swing.JComboBox<>();
        statusBox = new javax.swing.JComboBox<>();
        ratingBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        clearButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        createMoviesTable = new javax.swing.JTable();
        removeButton1 = new javax.swing.JButton();
        favoritesBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        seachButton = new javax.swing.JButton();
        moviesPage = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        secondTable = new javax.swing.JTable();
        favorites = new javax.swing.JPanel();
        watchLater = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        statistics = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        homeB = new javax.swing.JButton();
        favoritesB = new javax.swing.JButton();
        watchLaterB = new javax.swing.JButton();
        statB = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        logoutButton = new javax.swing.JButton();
        movieDetails = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PopCorn Trail");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(12, 16, 27));

        tabPane.setForeground(new java.awt.Color(0, 51, 102));
        tabPane.setTabPlacement(javax.swing.JTabbedPane.BOTTOM);

        movies.setBackground(new java.awt.Color(6, 26, 43));
        movies.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setBackground(new java.awt.Color(6, 26, 43));
        jLabel3.setFont(new java.awt.Font("Poppins", 0, 14)); // NOI18N
        jLabel3.setText("Manage Movie/Series List");
        movies.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 210, 60));

        jLabel4.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel4.setText("Title");
        movies.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));
        movies.add(titleFiel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 220, 40));

        jLabel5.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel5.setText("Genre");
        movies.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel6.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel6.setText("Status");
        movies.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        genreBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Action/Thriller", "Action/Adventure", "Action/Sci-Fi", "Adventure/Fantasy", "Animation/Family", "Comedy", "Comedy/Romance", "Crime/Drama", "Documentary", "Drama", "Fantasy", "Horror", "Mystery/Thriller", "Romance", "Sci-Fi", "War", "Western" }));
        movies.add(genreBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 210, 40));

        statusBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Watched", "Currently Watching", "Watch Later" }));
        statusBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusBoxActionPerformed(evt);
            }
        });
        movies.add(statusBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 280, 210, 40));

        ratingBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" }));
        movies.add(ratingBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, 210, 40));

        jLabel7.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel7.setText("Rating");
        movies.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, -1));

        clearButton.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        clearButton.setText("CLEAR");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        movies.add(clearButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 640, 210, 50));

        addButton.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        addButton.setText("ADD");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });
        movies.add(addButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, 210, 50));

        editButton.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        editButton.setText("EDIT");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });
        movies.add(editButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 460, 210, 50));

        createMoviesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Title", "Genre", "Rating", "Status", "Favorite"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        createMoviesTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(createMoviesTable);
        if (createMoviesTable.getColumnModel().getColumnCount() > 0) {
            createMoviesTable.getColumnModel().getColumn(0).setResizable(false);
            createMoviesTable.getColumnModel().getColumn(1).setResizable(false);
            createMoviesTable.getColumnModel().getColumn(2).setResizable(false);
            createMoviesTable.getColumnModel().getColumn(3).setResizable(false);
        }

        movies.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 700, 610));

        removeButton1.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        removeButton1.setText("REMOVE");
        removeButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButton1ActionPerformed(evt);
            }
        });
        movies.add(removeButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 520, 210, 50));

        favoritesBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        favoritesBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritesBoxActionPerformed(evt);
            }
        });
        movies.add(favoritesBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 350, 210, 40));

        jLabel8.setFont(new java.awt.Font("Poppins", 0, 12)); // NOI18N
        jLabel8.setText("Add to Favorite");
        movies.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        seachButton.setFont(new java.awt.Font("Montserrat", 0, 18)); // NOI18N
        seachButton.setText("SEARCH");
        seachButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seachButtonActionPerformed(evt);
            }
        });
        movies.add(seachButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 210, 50));

        tabPane.addTab("tab2", movies);

        moviesPage.setBackground(new java.awt.Color(6, 26, 43));

        secondTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        secondTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(secondTable);
        if (secondTable.getColumnModel().getColumnCount() > 0) {
            secondTable.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout moviesPageLayout = new javax.swing.GroupLayout(moviesPage);
        moviesPage.setLayout(moviesPageLayout);
        moviesPageLayout.setHorizontalGroup(
            moviesPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, moviesPageLayout.createSequentialGroup()
                .addContainerGap(499, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
        );
        moviesPageLayout.setVerticalGroup(
            moviesPageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(moviesPageLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 625, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        tabPane.addTab("tab1", moviesPage);

        favorites.setBackground(new java.awt.Color(6, 26, 43));

        javax.swing.GroupLayout favoritesLayout = new javax.swing.GroupLayout(favorites);
        favorites.setLayout(favoritesLayout);
        favoritesLayout.setHorizontalGroup(
            favoritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 950, Short.MAX_VALUE)
        );
        favoritesLayout.setVerticalGroup(
            favoritesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 723, Short.MAX_VALUE)
        );

        tabPane.addTab("tab3", favorites);

        watchLater.setBackground(new java.awt.Color(6, 26, 43));

        jLabel10.setText("tab 5");

        javax.swing.GroupLayout watchLaterLayout = new javax.swing.GroupLayout(watchLater);
        watchLater.setLayout(watchLaterLayout);
        watchLaterLayout.setHorizontalGroup(
            watchLaterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, watchLaterLayout.createSequentialGroup()
                .addContainerGap(616, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addGap(308, 308, 308))
        );
        watchLaterLayout.setVerticalGroup(
            watchLaterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(watchLaterLayout.createSequentialGroup()
                .addGap(218, 218, 218)
                .addComponent(jLabel10)
                .addContainerGap(489, Short.MAX_VALUE))
        );

        tabPane.addTab("tab5", watchLater);

        statistics.setBackground(new java.awt.Color(6, 26, 43));

        jLabel11.setText("tab4");

        javax.swing.GroupLayout statisticsLayout = new javax.swing.GroupLayout(statistics);
        statistics.setLayout(statisticsLayout);
        statisticsLayout.setHorizontalGroup(
            statisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, statisticsLayout.createSequentialGroup()
                .addContainerGap(521, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addGap(406, 406, 406))
        );
        statisticsLayout.setVerticalGroup(
            statisticsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statisticsLayout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(jLabel11)
                .addContainerGap(421, Short.MAX_VALUE))
        );

        tabPane.addTab("tab4", statistics);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(tabPane, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 0, 950, 720));

        jPanel1.setBackground(new java.awt.Color(21, 31, 55));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo popcorn trail.png"))); // NOI18N

        homeB.setBackground(new java.awt.Color(0, 51, 102));
        homeB.setFont(new java.awt.Font("Asimov", 0, 15)); // NOI18N
        homeB.setText("Home");
        homeB.setBorderPainted(false);
        homeB.setFocusPainted(false);
        homeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeBActionPerformed(evt);
            }
        });

        favoritesB.setBackground(new java.awt.Color(0, 51, 102));
        favoritesB.setFont(new java.awt.Font("Asimov", 0, 15)); // NOI18N
        favoritesB.setText("Favorites");
        favoritesB.setBorderPainted(false);
        favoritesB.setFocusPainted(false);
        favoritesB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                favoritesBActionPerformed(evt);
            }
        });

        watchLaterB.setBackground(new java.awt.Color(0, 51, 102));
        watchLaterB.setFont(new java.awt.Font("Asimov", 0, 15)); // NOI18N
        watchLaterB.setText("Watch Later");
        watchLaterB.setBorderPainted(false);
        watchLaterB.setFocusPainted(false);
        watchLaterB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                watchLaterBActionPerformed(evt);
            }
        });

        statB.setBackground(new java.awt.Color(0, 51, 102));
        statB.setFont(new java.awt.Font("Asimov", 0, 15)); // NOI18N
        statB.setText("Statistics");
        statB.setBorderPainted(false);
        statB.setFocusPainted(false);
        statB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statBActionPerformed(evt);
            }
        });

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/PopCorn Trail copy.png"))); // NOI18N

        logoutButton.setFont(new java.awt.Font("Montserrat", 0, 12)); // NOI18N
        logoutButton.setText("Logout");
        logoutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutButtonActionPerformed(evt);
            }
        });

        movieDetails.setFont(new java.awt.Font("Poppins", 0, 18)); // NOI18N
        movieDetails.setText("DETAILS");
        movieDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movieDetailsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(homeB, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
            .addComponent(favoritesB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(watchLaterB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(statB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jLabel2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(movieDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(9, 9, 9)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(homeB, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(favoritesB, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(watchLaterB, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(statB, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 233, Short.MAX_VALUE)
                .addComponent(movieDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(logoutButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void homeBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeBActionPerformed
       tabPane.setSelectedIndex(0);
        setupFavoritesTab();
        setupStatisticsTab();
        setupWatchLaterTab();
        highlightButton(homeB);
    }//GEN-LAST:event_homeBActionPerformed

    private void favoritesBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritesBActionPerformed
        tabPane.setSelectedIndex(2);
        highlightButton(favoritesB);
    }//GEN-LAST:event_favoritesBActionPerformed

    private void watchLaterBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_watchLaterBActionPerformed
         tabPane.setSelectedIndex(3);
         highlightButton(watchLaterB);
    }//GEN-LAST:event_watchLaterBActionPerformed

    private void statBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statBActionPerformed
         tabPane.setSelectedIndex(4);
         highlightButton(statB);
    }//GEN-LAST:event_statBActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        clearFields();
    }//GEN-LAST:event_clearButtonActionPerformed

    private void removeButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButton1ActionPerformed
        removeMovie();
    }//GEN-LAST:event_removeButton1ActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        addMovie();
    }//GEN-LAST:event_addButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        editMovie();
    }//GEN-LAST:event_editButtonActionPerformed

    private void favoritesBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favoritesBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_favoritesBoxActionPerformed

    private void statusBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statusBoxActionPerformed

    private void movieDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movieDetailsActionPerformed
        showDetailsDialog();
    }//GEN-LAST:event_movieDetailsActionPerformed

    private void logoutButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutButtonActionPerformed
       int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Logout Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        // Open LoginPage and close MainPage
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new LoginPage().setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Unable to open login screen: " + ex.getMessage());
            }
        });
        this.dispose();
    }//GEN-LAST:event_logoutButtonActionPerformed

    private void seachButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seachButtonActionPerformed
       showSearchDialog();
    }//GEN-LAST:event_seachButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
       FlatGradiantoDeepOceanIJTheme.setup();
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new MainPage("TestUser").setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JTable createMoviesTable;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel favorites;
    private javax.swing.JButton favoritesB;
    private javax.swing.JComboBox<String> favoritesBox;
    private javax.swing.JComboBox<String> genreBox;
    private javax.swing.JButton homeB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton logoutButton;
    private javax.swing.JButton movieDetails;
    private javax.swing.JPanel movies;
    private javax.swing.JPanel moviesPage;
    private javax.swing.JComboBox<String> ratingBox;
    private javax.swing.JButton removeButton1;
    private javax.swing.JButton seachButton;
    private javax.swing.JTable secondTable;
    private javax.swing.JButton statB;
    private javax.swing.JPanel statistics;
    private javax.swing.JComboBox<String> statusBox;
    private javax.swing.JTabbedPane tabPane;
    private javax.swing.JTextField titleFiel;
    private javax.swing.JPanel watchLater;
    private javax.swing.JButton watchLaterB;
    // End of variables declaration//GEN-END:variables
}
