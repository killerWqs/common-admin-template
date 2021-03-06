package com.killer.demo.modules.main.service.impl;/**
 * @author killer
 * @date 2018/12/2 -  11:53
 **/

import com.killer.demo.modules.main.dao.*;
import com.killer.demo.modules.main.excetpion.*;
import com.killer.demo.modules.main.model.*;
import com.killer.demo.modules.main.service.MainService;
import com.killer.demo.utils.DateTimeUtils;
import com.killer.demo.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

/**
 * @author killer
 * @description
 * @date 2018/12/02 - 11:53
 */
@Service
public class MainServiceImpl implements MainService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final RoleMenuMapper roleMenuMapper;

    private final UserMapper userMapper;

    private final RoleMapper roleMapper;

    private final MenuMapper menuMapper;

    private final OperationMapper operationMapper;

    private final RoleOperationMapper roleOperationMapper;

    @Autowired
    public MainServiceImpl(UserMapper userMapper, RoleMapper roleMapper, RoleOperationMapper roleOperationMapper,
                           MenuMapper menuMapper, OperationMapper operationMapper, RoleMenuMapper roleMenuMapper) {
        this.userMapper = userMapper;
        this.roleMapper = roleMapper;
        this.menuMapper = menuMapper;
        this.operationMapper = operationMapper;
        this.roleMenuMapper = roleMenuMapper;
        this.roleOperationMapper = roleOperationMapper;
    }

    //    在@Transactional注解中如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,
//    加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
    @Transactional(rollbackFor = AddUserException.class)
    @Override
    public void addUser(User user) throws AddUserException {
        // 后台第二次校验，防止插入错误数据，前台不一定使用浏览器
        // 检测用户名是否唯一
        String username = user.getUsername();
        if (userMapper.checkUserNameUnique(username) != null) {
            throw new UserNameRedulicateException("用户名已存在！");
        }

        // 检测密码是否符合要求
        String password = user.getPassword();

        userMapper.insert(user);
    }

    @Transactional(rollbackFor = RemoveUserException.class)
    @Override
    public void removeUser(String username) throws RemoveUserException {
        // 判断用户是否存在，可能会有多个管理员同时删除，会有并发，虽然概率极小
        String uname = userMapper.checkUserNameUnique(username);
        if (uname != null) {
            int result = userMapper.deleteByUsername(username);
            if (result != 1) {
                throw new RemoveUserException("删除失败!");
            }
        }
    }

    @Override
    public List<User> getUserAll(String username, String roleId) {
        return userMapper.selectUserAll(username, roleId);
    }

    @Override
    public List<Role> getRolesAll(String rolename) {
        List<Role> rolesAll = roleMapper.getRolesAll(rolename);
        return rolesAll;
    }

    @Transactional(rollbackFor = AddRoleException.class)
    @Override
    public void addRole(Role role) throws AddRoleException {
        String uuid = RandomUtils.uuid();
        role.setId(uuid);
        LocalDateTime now = LocalDateTime.now();
        // 系统默认时区
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = now.atZone(zone).toInstant();
        java.util.Date time = Date.from(instant);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");

        role.setIntime(simpleDateFormat.format(time));
        role.setUpdatetime(simpleDateFormat.format(time));

        roleMapper.insert(role);
    }

    @Transactional(rollbackFor = RemoveRoleException.class)
    @Override
    public void removeRole(String[] roleIds) throws RemoveRoleException {
        roleMapper.batchDelete(roleIds);
    }

    @Override
    public List<Menu> getfMenusAll() {
        return menuMapper.selectfMenusAll();
    }

    @Override
    public List<Menu> getsMenusAll(String fid) {
        return menuMapper.selectsMenusAll(fid);
    }

    @Override
    public List<Operation> getOperationsAll(String menuId, String RoleId) {
        List<Operation> operations = operationMapper.selectByMenuId(menuId);

        if (RoleId == null) {
            return operations;
        }
        // 获取已经授权的操作
        List<RoleOperation> roleOperations = roleOperationMapper.selectByRoleId(RoleId);
        // 获取所有操作

        for (Operation operation : operations) {
            for (RoleOperation roleOperation : roleOperations) {
                if (roleOperation.getOperationId().equals(operation.getId())) {
                    operation.setAuthenticated(true);
                    break;
                }
            }
        }

        return operations;
    }

    @Override
    public void addMenu(Menu menu) {
        String uuid = RandomUtils.uuid();
        menu.setId(uuid);

//        LocalDateTime localDateTime = LocalDateTime.now();
//        ZoneId zone = ZoneId.systemDefault();
//        Instant instant = localDateTime.atZone(zone).toInstant();
//        java.util.Date date = Date.from(instant);

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date date = Date.from(instant);

        menu.setIntime(date);
        menu.setUpdatetime(date);
        menuMapper.addMenu(menu);
    }

    @Override
    public void addOperation(Operation operation) {
        String uuid = RandomUtils.uuid();
        operation.setId(uuid);

        LocalDateTime now = LocalDateTime.now();
        Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();
        java.util.Date date = Date.from(instant);

        operation.setIntime(date);
        operation.setUpdatetime(date);
        int result = operationMapper.insert(operation);
    }

    @Override
    public List<Menu> getSideMenus() {
        List<Menu> menus = menuMapper.selectfMenusAll();

        for (Menu menu : menus) {
            List<Menu> smenus = menuMapper.selectsMenusAll(menu.getId());
            if (smenus.size() != 0) {
                menu.getList().addAll(smenus);

                for (Menu smenu : smenus) {
                    List<Menu> fmenus = menuMapper.selectsMenusAll(smenu.getId());
                    if (fmenus.size() != 0) {
                        smenu.getList().addAll(fmenus);
                    }
                }
            }
        }

        return menus;
    }

    @Override
    public List<Menu> getAuthMenu(String id, List<Menu> sideMenus) {
        // 这就是传说中的垃圾代码吗？
        List<RoleMenu> authMenus = roleMenuMapper.selectMenusByRoleId(id);
//        for (RoleMenu aMenu : authMenus) {
//            String aMenuId = aMenu.getMenuId();
//            for (Menu sideMenu : sideMenus) {
//                List<Menu> sList = null;
//                if (sideMenu.getId().equals(aMenuId)) {
//                    sideMenu.setAuthenticated(true);
//                    break;
//                } else if (sideMenu.isHasChildren()) {
//                    sList = sideMenu.getList();
//
//                    List<Menu> tList = null;
//                    for (Menu menu : sList) {
//                        if (menu.getId().equals(aMenuId)) {
//                            menu.setAuthenticated(true);
//                            break;
//                        } else if (menu.isHasChildren()) {
//                            tList = menu.getList();
//
//                            for (Menu tMenu : tList) {
//                                if (tMenu.getId().equals(aMenuId)) {
//                                    tMenu.setAuthenticated(true);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }

        // 尝试使用递归完成程序
//        for (RoleMenu aMenu : authMenus) {
//            String aMenuId = aMenu.getMenuId();
//            for (Menu sideMenu : sideMenus) {
//                do{
//                    if(sideMenu.getId().equals(aMenuId)){
//                        sideMenu.setAuthenticated(true);
//                        break;
//                    } else {
//
//                    }
//                }while (sideMenu.isHasChildren());
//            }
//        }

        // 如果不使用递归这种遍历顺序应该是最好的 复杂的遍历一次，不复杂的遍历多次
        long now = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        for (Menu sideMenu : sideMenus) {
            String sMenuId = sideMenu.getId();
            for (RoleMenu aMenu : authMenus) {
                String aMenuId = aMenu.getMenuId();
                if (aMenuId.equals(sMenuId)) {
                    sideMenu.setAuthenticated(true);
                    // 如果当前一级菜单授权
                    if (sideMenu.isHasChildren()) {
                        List<Menu> list = sideMenu.getList();
                        for (Menu menu : list) {

                            for (RoleMenu aMenu1 : authMenus) {

                                if (menu.getId().equals(aMenu1.getMenuId())) {
                                    menu.setAuthenticated(true);

                                    // 最多遍历三层
                                    if (menu.isHasChildren()) {
                                        List<Menu> tList = sideMenu.getList();
                                        for (Menu tMenu : tList) {
                                            for (RoleMenu aMenu2 : authMenus) {
                                                if (tMenu.getId().equals(aMenu2.getMenuId())) {
                                                    tMenu.setAuthenticated(true);
                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    break;
                                }
                            }
                        }
                    }
                    break;
                    // 在这里应该继续上一层 continue
                }
            }
        }
        long now1 = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        ;
        logger.info("总计执行时间为：" + (now1 - now));

        return sideMenus;
    }

    @Override
    public void modifyMenu(Menu menu) {
        if (menu.isHasChildren() != null) {
            if (menu.isHasChildren() == false) {
                // 清除三级菜单
                menuMapper.deleteByFid(menu.getId());
            } else {
                // 清除操作
                operationMapper.deleteByMenuId(menu.getId());
            }
        }

        menu.setUpdatetime(DateTimeUtils.now());
        menuMapper.updateByPrimaryKey(menu);
    }

    @Override
    public void authenticate(String roleId, String[] menuAuth, String[] cancelMenuAuth, String[] operaAuth, String[] cancelOperaAuth) {
        if (menuAuth != null) {
            for (String m : menuAuth) {
                RoleMenu roleMenu = new RoleMenu();
                roleMenu.setMenuId(m);
                roleMenu.setId(RandomUtils.uuid());
                roleMenu.setRoleId(roleId);
                roleMenu.setIntime(DateTimeUtils.now());
                roleMenu.setUpdatetime(roleMenu.getIntime());
                roleMenuMapper.insert(roleMenu);
            }
        }

        if (cancelMenuAuth != null) {
            for (String s : cancelMenuAuth) {
                roleMenuMapper.deleteByMenuId(s);
            }
        }

        if (operaAuth != null) {
            for (String s : operaAuth) {
                RoleOperation roleOperation = new RoleOperation();
                roleOperation.setId(RandomUtils.uuid());
                roleOperation.setRoleId(roleId);
                roleOperation.setIntime(DateTimeUtils.now());
                roleOperation.setUpdatetime(roleOperation.getIntime());
                roleOperation.setOperationId(s);

                roleOperationMapper.insert(roleOperation);
            }
        }

        if (cancelOperaAuth != null) {
            for (String s : cancelOperaAuth) {
                roleOperationMapper.deleteByOperaId(s);
            }
        }
    }

    @Override
    public void deleteMenu(String[] ids) {
        // 需要删除子菜单 使用了外键关联，表fid关联自己的主键id
        for (String id : ids) {
            menuMapper.deleteByPrimaryKey(id);
            // 删除操作 还是要使用外键，使用外键是真的方便
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,3};
        Integer[] a = new Integer[nums.length];
        for(int i = 0; i < nums.length; i++) {
            a[i] = nums[i];
        }
        int target = 3;

        List<Integer> ints = Arrays.asList(a);
        int[] results = new int[2];

        for(int index = 0; index < nums.length; index++) {
            int rest = target - nums[index];
            if(ints.indexOf(rest) != -1 && ints.indexOf(rest) != index) {
                results[0] = nums[index];
                results[1] = rest;
                System.out.println(Arrays.toString(results));
                break;
            }
        }
    }

}
