package model;


import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    @Email
    private String email;

    @Min(18)
    private int age;

    private String phoneNumber;

    @ManyToOne(targetEntity = Province.class)
    private Province province;

    @ManyToOne(targetEntity = Comment.class)
    private Comment comment;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<com.codegym.casestudy.model.Role> roles;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<com.codegym.casestudy.model.Blog> blogs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<com.codegym.casestudy.model.Role> getRoles() {
        return roles;
    }

    public void setRoles(List<com.codegym.casestudy.model.Role> roles) {
        this.roles = roles;
    }

    public List<com.codegym.casestudy.model.Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<com.codegym.casestudy.model.Blog> blogs) {
        this.blogs = blogs;
    }
}
