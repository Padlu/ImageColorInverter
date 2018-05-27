/**
 * Created by Abhishek on 21-10-2017.
 */

import javafx.application.Application;//for all app methods
import javafx.embed.swing.SwingFXUtils;//search
import javafx.geometry.Insets;//to set width and height
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;//to get image path i.e image
import javafx.scene.image.ImageView;//image paper on which image can be shown
import javafx.scene.layout.*;
import javafx.stage.*;//ImageInverter window
import javafx.geometry.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.File;
import java.io.IOException;


public class ImageInverter extends Application {
    Stage window;//variable window is our ImageInverter window

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Photo Inverter using MultiThreading");

        Pane backPane = new Pane();

        HBox upperBox = new HBox(10);
        upperBox.setMaxHeight(150);
        upperBox.setAlignment(Pos.CENTER);
        HBox lowerBox = new HBox(10);
        lowerBox.setMinHeight(500);
        lowerBox.setAlignment(Pos.CENTER);

        Scene primaryScene = new Scene(backPane, 1080, 720);

        Button openImage = new Button("Open");
        openImage.setId("openimage");
        openImage.setMinWidth(180);
        openImage.setMinHeight(50);
        Button invertImage = new Button("Invert");
        invertImage.setId("invert");
        invertImage.setMinWidth(180);
        invertImage.setMinHeight(50);

        Separator separator = new Separator();
        separator.setOrientation(Orientation.VERTICAL);
        separator.setMaxHeight(150);

        ImageView originalImage = new ImageView();
        originalImage.setFitWidth(500);
        originalImage.setFitHeight(500);
        originalImage.preserveRatioProperty();
        originalImage.setVisible(false);
        ImageView invertedImage = new ImageView();
        invertedImage.setFitWidth(500);
        invertedImage.setFitHeight(500);
        invertedImage.preserveRatioProperty();
        invertedImage.setVisible(false);

        upperBox.getChildren().addAll(openImage, separator, invertImage);
        lowerBox.getChildren().addAll(originalImage,invertedImage);
        backPane.getChildren().addAll(lowerBox,upperBox);

        final File[] img = new File[1];

        openImage.setOnAction(event -> {
            System.out.println("Open Clicked!");

            Stage win = new Stage();

            final FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));

            File file = fileChooser.showOpenDialog(win);
            img[0] = file;

            if (file != null) {

                try {
                    BufferedImage bufferedImage = ImageIO.read(file);
                    Image image1 = SwingFXUtils.toFXImage(bufferedImage,null);
                    originalImage.setImage(image1);
                    originalImage.setVisible(true);
                    originalImage.setPreserveRatio(true);
                } catch (IOException ex){
                    Logger.getLogger(ImageInverter.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        invertImage.setOnAction(event -> {
            BufferedImage bimg = new BufferedImage(10,10,BufferedImage.TYPE_INT_RGB);
            try {
                bimg = ImageIO.read(new File(img[0].getAbsolutePath()));
//                System.out.println(bimg.getWidth() + "," + bimg.getHeight());
                MatrixTest matrixTest = new MatrixTest(bimg);
                bimg = matrixTest.Invert();
            } catch (IOException e) {
                e.printStackTrace();
            }

            invertedImage.setImage(SwingFXUtils.toFXImage(bimg,null));
            invertedImage.setVisible(true);
            invertedImage.setPreserveRatio(true);
            System.out.println("Invert Clicked!!");

        });

        backPane.getStylesheets().add("ImageInverterCss.css");

        window.setScene(primaryScene);
        window.show();

    }



    public static void main(String[] args) {
        launch(args);
    }

}
