@Mapper
public interface UserMapper {
    @Insert("INSERT INTO users(email, password) VALUES(#{email}, #{password})")
    void insert(User user);

    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(Long id);
}