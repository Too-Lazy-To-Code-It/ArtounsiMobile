package GUIposts;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import models.PostLike;
import Service.PostService;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class LikeChartController implements Initializable {

    public BarChart<String, Integer> chart;
    public CategoryAxis xAxis;
    public NumberAxis yAxis;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            displayChart();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void displayChart() throws SQLException {
        // Get data for chart
        PostService postService = new PostService();
        List<PostLike> postLikes = postService.getAllPostLikesorder();

        // Create chart data series
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        for (PostLike postLike : postLikes) {
            series.getData().add(new XYChart.Data<>("Post " + postLike.getPostId(), postLike.getNumLikes()));
        }

        // Add series to chart
        chart.getData().add(series);

        // Set axis labels
        xAxis.setLabel("Post ID");
        yAxis.setLabel("Number of Likes");
    }
}