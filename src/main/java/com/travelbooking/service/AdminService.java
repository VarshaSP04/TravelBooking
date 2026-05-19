package com.travelbooking.service;

import com.travelbooking.dao.AdminDAO;
import com.travelbooking.model.Admin;
import com.travelbooking.util.PasswordUtil;
import org.bson.types.ObjectId;

public class AdminService {

    private final AdminDAO adminDAO = new AdminDAO();

    public Admin login(String username, String password) {
        Admin admin = adminDAO.findByUsername(username);
        if (admin != null && PasswordUtil.verify(password, admin.getPassword())) {
            return admin;
        }
        return null;
    }

    public Admin findById(String id) { return adminDAO.findById(id); }
    public long count() { return adminDAO.count(); }

    /** Creates the default admin if none exists. Call from a listener on startup. */
    public void ensureDefaultAdmin() {
        if (adminDAO.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(PasswordUtil.hash("admin123"));
            admin.setFullName("Super Admin");
            admin.setEmail("admin@travelbooking.com");
            admin.setRole("SUPER_ADMIN");
            adminDAO.insert(admin);
        }
    }
}
