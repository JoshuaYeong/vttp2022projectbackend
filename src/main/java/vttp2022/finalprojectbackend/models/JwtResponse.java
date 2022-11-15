package vttp2022.finalprojectbackend.models;

public class JwtResponse {

    private String token;
    private String username;

    public JwtResponse(String jwtToken, String username) {
        this.token = jwtToken;
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
