package infinum.pokemonapp.user;


public class CurrentUser {

    private static final CurrentUser ourInstance = new CurrentUser();

    private String authorizationToken;
    private String email;
    private String username;

    public static CurrentUser getInstance() {
        return ourInstance;
    }

    private CurrentUser() {
    }

    public  String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthorizationString(){
        return "Token token=" + authorizationToken + ", email=" + email;
    }
}
