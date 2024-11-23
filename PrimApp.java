package PrimProject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PrimApp extends Application {
    private Canvas graphCanvas;
    private Graph graph;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Prim's Algorithm");

        // Layouts
        BorderPane root = new BorderPane();
        VBox controlPanel = new VBox(10);
        controlPanel.setPadding(new Insets(15));
        controlPanel.setStyle("-fx-background-color: #2B2B2B;");

        // Canvas with grid
        graphCanvas = new Canvas(600, 600);
        drawGrid(graphCanvas);
        StackPane canvasPane = new StackPane(graphCanvas);
        canvasPane.setStyle("-fx-background-color: #1E1E1E;");
        root.setCenter(canvasPane);

        // Controls
        Label titleLabel = new Label("Prim's Algorithm");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        Button fileButton = new Button("Choose File");
        fileButton.setStyle("-fx-background-color: #3B3B3B; -fx-text-fill: white;");

        TextField startNodeField = new TextField();
        startNodeField.setPromptText("Enter Start Node");
        startNodeField.setStyle("-fx-background-color: #3B3B3B; -fx-text-fill: white;");

        Button drawGraphButton = new Button("Draw Graph");
        drawGraphButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        Button simulatePrimButton = new Button("Simulate Prim");
        simulatePrimButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");

        controlPanel.getChildren().addAll(titleLabel, fileButton, startNodeField, drawGraphButton, simulatePrimButton);
        root.setRight(controlPanel);

        // Actions
        fileButton.setOnAction(event -> chooseFile(primaryStage));
        drawGraphButton.setOnAction(event -> drawGraph());
        simulatePrimButton.setOnAction(event -> simulatePrim(startNodeField.getText()));

        // Scene
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add("https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap");
        primaryStage.setScene(scene);

        Image icon = new Image("C:\\Users\\moustaid\\Desktop\\javaFX course\\BroCodeCourse\\src\\images\\logo.png");
        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }

    private void drawGrid(Canvas canvas) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.RED);
        gc.setLineWidth(0.5);

        // Draw vertical grid lines
        for (int x = 0; x < canvas.getWidth(); x += 20) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }

        // Draw horizontal grid lines
        for (int y = 0; y < canvas.getHeight(); y += 20) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }

        gc.setStroke(Color.BLUE);
        gc.setLineWidth(0.25);

        // Secondary grid lines
        for (int x = 0; x < canvas.getWidth(); x += 10) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 0; y < canvas.getHeight(); y += 10) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }
    }

    private void chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Graph File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            loadGraphFromFile(file);
        }
    }

    private void loadGraphFromFile(File file) {
        graph = new Graph();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String start = parts[0];
                    String end = parts[1];
                    int weight = Integer.parseInt(parts[2]);

                    Node startNode = new Node(start);
                    Node endNode = new Node(end);
                    Edge edge = new Edge(startNode, endNode, weight);

                    graph.addNode(startNode);
                    graph.addNode(endNode);
                    graph.addEdge(edge);
                }
            }
            System.out.println(this.graph);
            System.out.println("Graph loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }

    /*
    * Draw the graph on the canvas :
    * 1. Check if the graph is loaded; if not, exit the method
    * 2. Get the GraphicsContext from the canvas for drawing
    * 3. Clear the canvas and redraw the grid background
    * 4. Generate positions for graph nodes and edges
    * 5. Draw edges first so nodes appear on top of edges
    * 6. Draw nodes
     */
    private void drawGraph() {
        // 1. Check if the graph is loaded; if not, exit the method
        if (graph == null) {
            System.err.println("No graph loaded!");
            return;
        }

        // 2. Get the GraphicsContext from the canvas for drawing
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();

        // 3. Clear the canvas and redraw the grid background
        double canvasWidth = graphCanvas.getWidth();
        double canvasHeight = graphCanvas.getHeight();
        gc.clearRect(0, 0, canvasWidth, canvasHeight); // Clear existing drawings
        drawGrid(graphCanvas); // Draw a grid as the background

        // 4. Generate positions for graph nodes and edges
        graph.generatePositions(canvasWidth, canvasHeight);

        // 5. Draw edges first so nodes appear on top of edges
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        for (Edge edge : graph.getEdges()) {
            gc.strokeLine(edge.getStartPositionX(), edge.getStartPositionY(),
                    edge.getEndPositionX(), edge.getEndPositionY());

            // 5.1 Draw edge weight at the midpoint of the edge
            double midX = (edge.getStartPositionX() + edge.getEndPositionX()) / 2;
            double midY = (edge.getStartPositionY() + edge.getEndPositionY()) / 2;
            gc.setFill(Color.BLUE);
            gc.fillText(String.valueOf(edge.getWeight()), midX, midY);
        }

        // 6. Draw nodes
        double nodeRadius = 35; // Define the radius of each node
        for (Node node : graph.getNodes()) {
            double x = node.getPositionX();
            double y = node.getPositionY();

            // 6.1 Draw the node circle
            gc.setFill(Color.YELLOW);
            gc.fillOval(x - nodeRadius / 2, y - nodeRadius / 2, nodeRadius, nodeRadius);

            // 6.2 Draw the node label
            gc.setFill(Color.BLACK);
            gc.fillText(node.getName(), x - 10, y + 5);
        }
    }

    private void simulatePrim(String startNodeName) {
        if (graph == null || startNodeName == null || startNodeName.isEmpty()) {
            System.err.println("Invalid start node or graph not loaded!");
            return;
        }
        System.out.println("Running Prim's Algorithm...");

        // Run Prim's algorithm
        Prim prim = new Prim(graph);
        prim.run(startNodeName);

        Graph primGraph = prim.getPrimGraph(); // Get the MST graph
        System.out.println("Prim's Algorithm Result:");
        System.out.println(primGraph);

        // Visualize Prim's result
        GraphicsContext gc = graphCanvas.getGraphicsContext2D();

        // Change edge colors dynamically
        for (Edge edge : primGraph.getEdges()) {
            // Highlight this edge
            highlightEdge(gc, edge, Color.GREEN);

            // Optional delay to simulate animation
            try {
                Thread.sleep(500); // 500 ms delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void highlightEdge(GraphicsContext gc, Edge edge, Color color) {
        gc.setStroke(color);
        gc.setLineWidth(3);

        // Draw the highlighted edge
        gc.strokeLine(edge.getStartPositionX(), edge.getStartPositionY(),
                edge.getEndPositionX(), edge.getEndPositionY());

        // Optional: redraw the weight with the new color
        double midX = (edge.getStartPositionX() + edge.getEndPositionX()) / 2;
        double midY = (edge.getStartPositionY() + edge.getEndPositionY()) / 2;
        gc.setFill(color);
        gc.fillText(String.valueOf(edge.getWeight()), midX, midY);


        // Redraw all ndoes so they are on top of the highlighted edge
        // 6. Draw nodes
        double nodeRadius = 35; // Define the radius of each node
        for (Node node : graph.getNodes()) {
            double x = node.getPositionX();
            double y = node.getPositionY();

            // 6.1 Draw the node circle
            gc.setFill(Color.YELLOW);
            gc.fillOval(x - nodeRadius / 2, y - nodeRadius / 2, nodeRadius, nodeRadius);

            // 6.2 Draw the node label
            gc.setFill(Color.BLACK);
            gc.fillText(node.getName(), x - 10, y + 5);
        }
    }
}
