package vttp2022.finalprojectbackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import vttp2022.finalprojectbackend.models.AppUser;

import static vttp2022.finalprojectbackend.repositories.SQL.*;

import java.util.Optional;

@Repository
public class AppUserRepository {

    private PasswordEncoder encoder;

    @Autowired
    private JdbcTemplate template;

    @Autowired
    public AppUserRepository(@Lazy PasswordEncoder passwordEncoder) {
        this.encoder = passwordEncoder;
    }

    public Optional<AppUser> findUserByUsername(String username) {

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_USER_BY_USERNAME, username);
        if (!rs.next()) {
            return Optional.empty();
        } else {
            AppUser appUser = new AppUser();
            appUser.setUsername(rs.getString("username"));
            appUser.setPassword(rs.getString("password"));
            return Optional.of(appUser);
        }
    }

    public Boolean existsByUsername(String username) {

        SqlRowSet rs = template.queryForRowSet(SQL_SELECT_USER_BY_USERNAME, username);
        if (rs.next()) {
            return true;
        }
        return false;
    }

    public Boolean insertNewUser(String username, String password) {

        int added = template.update(SQL_INSERT_INTO_USERS,
                username,
                encoder.encode(password));

        return 1 == added;
    }

}
