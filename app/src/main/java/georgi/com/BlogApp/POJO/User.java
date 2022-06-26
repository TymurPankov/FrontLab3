package georgi.com.BlogApp.POJO;


import static georgi.com.BlogApp.Configs.ServerURLs.DEFAULT_USER_IMG;
import static georgi.com.BlogApp.Configs.ServerURLs.USER_IMAGES_URL;

public class User {

    private String userUrl;

    private String firstName;

    private String lastName;

    private String email;

    private String profile_picture;

    public User() {}

    public User(String userUrl, String firstName, String lastName, String email) {
        this.userUrl = userUrl;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getUserUrl() {
        return userUrl;
    }

    public void setUserUrl(String userUrl) {
        this.userUrl = userUrl;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }


    // Creating the needed URL for the user profile picture.
    public String getProfPicUrl() {

        // Checking if profile picture equals "no".
        // That means that there is not profile picture set.
        // So we are returning the default server profile picture url.
        if(profile_picture.equals("no")) return DEFAULT_USER_IMG;

        // There is profile picture so creating the url that we need for the picture.
        // Url is created like that : /res/images/{HERE IS USER URL}/{HERE PROFILE PICTURE NAME}.
        else return USER_IMAGES_URL + userUrl + "/" + profile_picture;
    }

}
