package georgi.com.BlogApp.Configs;


// All of the server urls.
public class ServerURLs {

    private static final String IP_ADDRESS = "http://192.168.0.103:8080";
    public static final String SERVER_URL = IP_ADDRESS + "/rest";

    public static final String LOGIN_URL = SERVER_URL + "/login";
    public static final String REGISTER_URL = SERVER_URL + "/register";
    public static final String LOGOUT_URL = SERVER_URL + "/logout";
    public static final String AUTHENTICATION_URL = SERVER_URL + "/authentication";

    public static final String ACCOUNT_URL = SERVER_URL + "/account";

    public static final String POSTS_URL = SERVER_URL + "/posts";
    public static final String POSTS_AUTH_USER_URL = ACCOUNT_URL + "/posts";
    public static final String CREATE_POST_URL = POSTS_URL + "/create";

    public static final String DELETE_COMMENT_URL = SERVER_URL + "/comment/delete";
    public static final String DELETE_REPLY_URL = SERVER_URL + "/reply/delete";

    public static final String USER_IMAGES_URL = IP_ADDRESS + "/res/images/";
    public static final String POSTS_IMAGES_URL = IP_ADDRESS + "/res/image-post/";

    public static final String DEFAULT_USER_IMG = IP_ADDRESS + "/res/images/default-image.png";
    public static final String DEFAULT_POST_IMG = IP_ADDRESS + "/res/image-post/default-image.png";

}
