package com.killer.demo.modules.main.excetpion;

/**
 * TODO 管理员添加用户时，如果角色已经被删除，则抛出此异常
 *
 * @author wqs
 * @date 2018-12-10 15:14
 */
public class RoleMissException extends AddUserException {
    public RoleMissException(String message) {
        super(message);
    }

    public static void main(String[] args) {
        // 反射机制
    }
}
