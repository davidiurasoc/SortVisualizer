import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    private static JPanel chartPanel;
    private static Integer[] array;
    private static final int BAR_WIDTH = 30;
    private static final int MAX_HEIGHT = 400;
    private static final int X_MARGIN = 10;
    private static GridBagConstraints gbc;

    public static void main(String[] args) {

        JFrame frame = new JFrame("Sort Visualizer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        JPanel titlePanel = createTitlePanel();
        JPanel controlPanel = createControlPanel();
        chartPanel = createChartPanel();

        frame.getContentPane().add(chartPanel, gbc);

        array = new Integer[29];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1;
        }

        frame.getContentPane().setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        frame.getContentPane().add(titlePanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.5;
        frame.getContentPane().add(controlPanel, gbc);

        gbc.gridy = 2;
        gbc.weighty = 5.0;
        frame.getContentPane().add(chartPanel, gbc);

        frame.setVisible(true);
    }

    private static JPanel createTitlePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.red);
        panel.setLayout(new GridBagLayout());

        JLabel titleLabel = new JLabel("Sorting Algorithms Visualizer");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50));
        titleLabel.setForeground(Color.black);

        panel.add(titleLabel);

        return panel;
    }

    private static JPanel createControlPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.blue);
        panel.setLayout(new GridBagLayout());

        JButton startButton = new JButton("Start");
        JButton shuffleButton = new JButton("Shuffle");
        String[] algorithms = {"Bubble Sort", "Selection Sort", "Quicksort"}; // Add more algorithms as needed
        JComboBox<String> algorithmDropdown = new JComboBox<>(algorithms);

        Dimension buttonSize = new Dimension(385, 50);
        startButton.setPreferredSize(buttonSize);
        shuffleButton.setPreferredSize(buttonSize);
        algorithmDropdown.setPreferredSize(buttonSize);

        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedAlgorithm = (String) algorithmDropdown.getSelectedItem();
                switch (selectedAlgorithm) {
                    case "Bubble Sort":
                        bubbleSort(array);
                        break;
                    case "Selection Sort":
                        selectionSort(array);
                        break;
                    case "Quicksort":
                        quicksort(array, 0, array.length - 1);
                        break;
                }
            }
        });

        shuffleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shuffleArray(array);
                drawArray(array);
            }
        });

        panel.add(startButton);
        panel.add(shuffleButton);
        panel.add(algorithmDropdown);

        return panel;
    }

    private static JPanel createChartPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                int x = X_MARGIN;
                for (int i = 0; i < array.length; i++) {
                    int height = (int) (((double) array[i] / array.length) * MAX_HEIGHT);
                    int y = MAX_HEIGHT - height + 10;

                    g2d.setColor(Color.lightGray);
                    g2d.fillRect(x, 10, BAR_WIDTH, y - 10);

                    g2d.setColor(Color.blue);
                    g2d.fillRect(x, y, BAR_WIDTH, height);

                    x += BAR_WIDTH + 10;
                }
            }
        };
        panel.setBackground(Color.lightGray);
        panel.setPreferredSize(new Dimension(1200, MAX_HEIGHT + 20));

        return panel;
    }

    private static void shuffleArray(Integer[] array) {
        Collections.shuffle(Arrays.asList(array));
    }

    private static void drawArray(Integer[] array) {
        chartPanel.removeAll();
        chartPanel.revalidate();
        chartPanel.repaint();

        Graphics2D g2d = (Graphics2D) chartPanel.getGraphics();
        int x = X_MARGIN;
        for (int i = 0; i < array.length; i++) {
            int height = (int) (((double) array[i] / array.length) * MAX_HEIGHT);
            int y = MAX_HEIGHT - height + 10;
            g2d.setColor(Color.blue);
            g2d.fillRect(x, y, BAR_WIDTH, height);
            g2d.setColor(Color.lightGray);
            g2d.drawRect(x, y, BAR_WIDTH, height);
            x += BAR_WIDTH + 10;
        }
    }

    private static void bubbleSort(Integer[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    highlightBars(j, j + 1);
                    drawArray(array);
                    delay(50); // Delay for visualization
                }
            }
        }
    }

    private static void selectionSort(Integer[] array) {
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[minIdx]) {
                    minIdx = j;
                }
            }
            swap(array, i, minIdx);
            highlightBars(i, minIdx);
            drawArray(array);
            delay(50); // Delay for visualization
        }
    }

    private static void quicksort(Integer[] array, int low, int high) {
        if (low < high) {
            int pi = partition(array, low, high);
            quicksort(array, low, pi - 1);
            quicksort(array, pi + 1, high);
        }
    }

    private static int partition(Integer[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
                highlightBars(i, j);
                drawArray(array);
                delay(50); // Delay for visualization
            }
        }
        swap(array, i + 1, high);
        highlightBars(i + 1, high);
        drawArray(array);
        delay(50); // Delay for visualization
        return i + 1;
    }

    private static void swap(Integer[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private static void highlightBars(int index1, int index2) {
        Graphics2D g2d = (Graphics2D) chartPanel.getGraphics();

        int x1 = X_MARGIN + (BAR_WIDTH + 10) * index1;
        int x2 = X_MARGIN + (BAR_WIDTH + 10) * index2;

        int height1 = (int) (((double) array[index1] / array.length) * MAX_HEIGHT);
        int height2 = (int) (((double) array[index2] / array.length) * MAX_HEIGHT);

        int y1 = MAX_HEIGHT - height1 + 10;
        int y2 = MAX_HEIGHT - height2 + 10;

        g2d.setColor(Color.lightGray);
        g2d.fillRect(x1, 10, BAR_WIDTH, y1 - 10);
        g2d.fillRect(x2, 10, BAR_WIDTH, y2 - 10);

        g2d.setColor(Color.green);

        g2d.fillRect(x1, y1, BAR_WIDTH, height1);
        g2d.fillRect(x2, y2, BAR_WIDTH, height2);
    }

    private static void delay(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
