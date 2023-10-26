package projectH.HistoricalDatabaseofCaptives.Security;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import projectH.HistoricalDatabaseofCaptives.Users.User;

@Entity
public class Token {
    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    public String token;

    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

//    @JdbcTypeCode(SqlTypes.JSON)
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinTable(name = "hdc_user")
//    @JoinColumn(name = "user_id")
//    public User user;
}



