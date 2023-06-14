@Service
public class UserService {
    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public void createUser(User user) {
        userMapper.insert(user);
    }

    public User findUserById(Long id) {
        return userMapper.findById(id);
    }

    public long getSaveTime(int num){
        long startTime = System.currentTimeMillis();

        for(int i=0;i<num;i++){
            User user = new User(i, "biiin"+i, "Biiiiin" + i);

            userMapper.insert(user);
        }

        long endTime = System.currentTimeMillis();

        long timeDiff = (endTime - startTime) / 1000;

        return timeDiff;
    }


    public long getFindTime(int num){
        long startTime = System.currentTimeMillis();

        for(int i=0;i<num;i++){
            findUserById(i);
        }

        long endTime = System.currentTimeMillis();

        long timeDiff = (endTime - startTime) / 1000;

        return timeDiff;
    }
}