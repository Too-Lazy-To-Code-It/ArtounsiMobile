package Entity;

import java.sql.Blob;
import java.sql.Date;
import java.sql.Timestamp;

public class Post {
    private int id_post;
    private int id_user;
    private String title;
    private String description_p;
    private String imagePath;
    private Date date;
    private int likes;
    private Category category_p;
    private String  media;
    private String post_typ;
    private double score;
    private String path;
    



//    private String ImageP;
//    
//    private String Media;
//    private String post_typ;

    public Post() {}

    public Post(int id_post, int id_user, String title, String description_p, String imagePath, Date date, int likes, Category category_p, String media, String post_typ, double score, String path) {
        this.id_post = id_post;
        this.id_user = id_user;
        this.title = title;
        this.description_p = description_p;
        this.imagePath = imagePath;
        this.date = date;
        this.likes = likes;
        this.category_p = category_p;
        this.media = media;
        this.post_typ = post_typ;
        this.score = score;
        this.path = path;
    }

    public Post(int id_user, String title, String description_p, String imagePath, Date date, int likes, Category category_p, String media, String post_typ, double score, String path) {
        this.id_user = id_user;
        this.title = title;
        this.description_p = description_p;
        this.imagePath = imagePath;
        this.date = date;
        this.likes = likes;
        this.category_p = category_p;
        this.media = media;
        this.post_typ = post_typ;
        this.score = score;
        this.path = path;
    }

    
    public int getId_post() {
        return id_post;
    }

    public void setId_post(int id_post) {
        this.id_post = id_post;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription_p() {
        return description_p;
    }

    public void setDescription_p(String description_p) {
        this.description_p = description_p;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Category getCategory_p() {
        return category_p;
    }

    public void setCategory_p(Category category_p) {
        this.category_p = category_p;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getPost_typ() {
        return post_typ;
    }

    public void setPost_typ(String post_typ) {
        this.post_typ = post_typ;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    
    

   
    
    
    
    
    
    
}
