package com.zei.shiro.shirojwt.mapper;

import com.zei.shiro.shirojwt.entity.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * 模拟数据环境
 */
@Repository
public class UserMapper {

    private static Map<Integer,User> users = null;

    static {
        users = new HashMap<Integer, User>();

        users.put(1,new User(1,"aaa","123456","admin","view,edit"));
        users.put(2,new User(2,"bbb","123456","user","view"));
        users.put(3,new User(3,"ccc","123456","user","view"));
    }

    private static Integer initId = 4;

    public Integer save(User user){
        if(user.getId()==null){
           user.setId(initId++);
        }
        users.put(initId,user);
        return 1;
    }

    public User getUser(String userName){
        for(int i = 1;i<users.size()+1;i++){
            if(userName.equals(users.get(i).getUsername())){
                return users.get(i);
            }
        }
        return null;
    }


}
